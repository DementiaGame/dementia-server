package synapse.dementia.domain.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.initialgame.dto.response.InitialGameQuestionResponse;
import synapse.dementia.domain.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.global.excel.repository.ExcelDataRepository;
import synapse.dementia.global.excel.model.ExcelData;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class InitialGameQuestionService {

    private final InitialGameQuestionRepository initialGameQuestionRepository;
    private final ExcelDataRepository excelDataRepository;

    @Autowired
    public InitialGameQuestionService(InitialGameQuestionRepository initialGameQuestionRepository, ExcelDataRepository excelDataRepository) {
        this.initialGameQuestionRepository = initialGameQuestionRepository;
        this.excelDataRepository = excelDataRepository;
    }

    @Transactional(readOnly = true)
    public List<InitialGameQuestionResponse> getRandomQuestionsByTopic(Long userId, SelectGameTopicRequest request) {
        List<ExcelData> excelDataList = excelDataRepository.findByTopic(request.topicName());
        Random random = new Random();

        // 모든 문제를 가져와서 셔플하고 10개를 선택
        List<ExcelData> shuffledExcelDataList = excelDataList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected, random);
                    return collected.stream().limit(10).collect(Collectors.toList());
                }));

        // 무작위로 선택된 문제들을 InitialGameQuestion으로 변환
        List<InitialGameQuestion> initialGameQuestions = shuffledExcelDataList.stream()
                .map(excelData -> InitialGameQuestion.builder()
                        .excelData(excelData)
                        .consonantQuiz(excelData.getQuestion())
                        .answerWord(excelData.getAnswer())
                        .hintImage(null) // hintImage는 쿠폰 사용 시 설정
                        .build())
                .collect(Collectors.toList());

        // 문제를 저장하여 ID를 생성
        List<InitialGameQuestion> savedQuestions = initialGameQuestionRepository.saveAll(initialGameQuestions);

        // InitialGameQuestionResponse로 변환하여 반환
        return savedQuestions.stream()
                .map(initialGameQuestion -> new InitialGameQuestionResponse(
                        initialGameQuestion.getQuestionIdx(),
                        initialGameQuestion.getExcelData().getIdx(),
                        initialGameQuestion.getConsonantQuiz(),
                        initialGameQuestion.getAnswerWord(),
                        initialGameQuestion.getHintImage()))
                .collect(Collectors.toList());
    }
}
