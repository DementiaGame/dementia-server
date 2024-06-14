package synapse.dementia.domain.initialgame.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.initialgame.domain.InitialGameTopic;
import synapse.dementia.domain.initialgame.repository.InitialQuestionRepository;
import synapse.dementia.domain.initialgame.repository.InitialTopicRepository;
import synapse.dementia.domain.initialgame.service.InitialGameQuestionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InitialGameQuestionServiceImpl implements InitialGameQuestionService {

    private final InitialTopicRepository topicRepository;
    private final InitialQuestionRepository questionRepository;

    public InitialGameQuestionServiceImpl(InitialTopicRepository topicRepository, InitialQuestionRepository questionRepository) {
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void saveQuestions(Map<String, List<String>> topicAnswerMap) throws IOException {
        List<InitialGameQuestion> questionsToSave = new ArrayList<>();

        // 모든 주제를 데이터베이스에서 한 번에 가져오기
        List<String> topicNames = new ArrayList<>(topicAnswerMap.keySet());
        List<InitialGameTopic> existingTopics = topicRepository.findByTopicNameIn(topicNames);

        // 기존 주제를 맵으로 변환
        Map<String, InitialGameTopic> existingTopicMap = existingTopics.stream()
                .collect(Collectors.toMap(InitialGameTopic::getTopicName, topic -> topic));

        // 주제 및 질문 준비
        for (Map.Entry<String, List<String>> entry : topicAnswerMap.entrySet()) {
            String topicName = entry.getKey();
            InitialGameTopic topic = existingTopicMap.get(topicName);

            // 주제가 없으면 새로 생성
            if (topic == null) {
                topic = new InitialGameTopic(topicName);
                topic = topicRepository.save(topic); // 새로운 주제를 저장
                existingTopicMap.put(topicName, topic);
            }

            List<String> answers = entry.getValue();
            for (String answer : answers) {
                InitialGameQuestion question = InitialGameQuestion.builder()
                        .topic(topic)
                        .answerWord(answer)
                        .consonantQuiz("") // 초성퀴즈 문제는 추가 로직이 필요할 수 있음
                        .build();
                questionsToSave.add(question);
            }
        }

        // 한 번에 모든 질문 저장
        questionRepository.saveAll(questionsToSave);
    }
}
