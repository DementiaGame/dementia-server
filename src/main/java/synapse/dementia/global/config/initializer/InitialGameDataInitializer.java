package synapse.dementia.global.config.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import synapse.dementia.domain.initialgame.service.InitialGameQuestionService;
import synapse.dementia.domain.initialgame.service.InitialGameTopicService;
import synapse.dementia.domain.initialgame.util.ExcelReader;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public class InitialGameDataInitializer implements CommandLineRunner {

    private final InitialGameTopicService topicService;
    private final InitialGameQuestionService questionService;

    public InitialGameDataInitializer(InitialGameTopicService topicService, InitialGameQuestionService questionService) {
        this.topicService = topicService;
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 엑셀 파일을 읽어옴
        ClassPathResource resource = new ClassPathResource("excel/initial_game_data.xlsx");
        try (InputStream inputStream = resource.getInputStream()) {
            // 엑셀 파일에서 주제와 답변을 매핑
            Map<String, List<String>> topicAnswerMap = ExcelReader.mapTopicsToAnswers(inputStream);

            // 주제를 DB에 저장
            for (String topicName : topicAnswerMap.keySet()) {
                topicService.saveTopic(topicName);
            }

            // 질문을 DB에 저장
            questionService.saveQuestions(topicAnswerMap);
        }
    }
}