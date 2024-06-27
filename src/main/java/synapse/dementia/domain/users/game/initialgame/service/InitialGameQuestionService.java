package synapse.dementia.domain.users.game.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.game.initialgame.dto.request.SelectGameTopicRequest;
import synapse.dementia.domain.users.game.initialgame.dto.response.InitialGameQuestionResponse;
import synapse.dementia.domain.users.game.initialgame.dto.response.SelectedGameTopicResponse;
import synapse.dementia.domain.users.game.initialgame.repository.InitialGameQuestionRepository;
import synapse.dementia.domain.users.game.initialgame.repository.SelectedGameTopicRepository;
import synapse.dementia.domain.users.member.domain.Users;
import synapse.dementia.domain.users.member.repository.UsersRepository;
import synapse.dementia.domain.admin.excel.repository.ExcelDataRepository;
import synapse.dementia.domain.admin.excel.model.ExcelData;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class InitialGameQuestionService {

    private final InitialGameQuestionRepository initialGameQuestionRepository;
    private final ExcelDataRepository excelDataRepository;
    private final UsersRepository usersRepository;
    private final SelectedGameTopicRepository selectedGameTopicRepository;

    @Autowired
    public InitialGameQuestionService(InitialGameQuestionRepository initialGameQuestionRepository, ExcelDataRepository excelDataRepository, UsersRepository usersRepository, SelectedGameTopicRepository selectedGameTopicRepository) {
        this.initialGameQuestionRepository = initialGameQuestionRepository;
        this.excelDataRepository = excelDataRepository;
        this.usersRepository = usersRepository;
        this.selectedGameTopicRepository = selectedGameTopicRepository;
    }

    @Transactional(readOnly = true)
    public List<InitialGameQuestionResponse> getRandomQuestionsByTopic(Long userIdx, SelectGameTopicRequest request) {
        Users user = usersRepository.findById(userIdx).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        List<SelectedGameTopic> selectedGameTopics = selectedGameTopicRepository.findByUserAndTopicName(user, request.topicName());

        if (selectedGameTopics.isEmpty()) {
            throw new IllegalArgumentException("Selected topic not found");
        }

        SelectedGameTopic selectedGameTopic = selectedGameTopics.get(0); // 여러 레코드 중 첫 번째를 사용

        List<ExcelData> excelDataList = excelDataRepository.findByTopic(request.topicName());
        Random random = new Random();

        List<ExcelData> shuffledExcelDataList = excelDataList.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected, random);
                    return collected.stream().limit(10).collect(Collectors.toList());
                }));

        List<InitialGameQuestion> initialGameQuestions = shuffledExcelDataList.stream()
                .map(excelData -> InitialGameQuestion.builder()
                        .user(user)
                        .selectedGameTopic(selectedGameTopic)
                        .consonantQuiz(excelData.getQuestion())
                        .answerWord(excelData.getAnswer())
                        .hintImage(null)
                        .correct(false)
                        .gameScore(0)
                        .build())
                .collect(Collectors.toList());

        List<InitialGameQuestion> savedQuestions = initialGameQuestionRepository.saveAll(initialGameQuestions);

        return savedQuestions.stream()
                .map(initialGameQuestion -> new InitialGameQuestionResponse(
                        initialGameQuestion.getQuestionIdx(),
                        initialGameQuestion.getConsonantQuiz(),
                        initialGameQuestion.getAnswerWord(),
                        initialGameQuestion.getHintImage()))
                .collect(Collectors.toList());
    }
}
