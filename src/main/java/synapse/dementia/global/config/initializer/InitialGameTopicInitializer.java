package synapse.dementia.global.config.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import synapse.dementia.domain.initialgame.domain.InitialGameTopic;
import synapse.dementia.domain.initialgame.service.InitialGameTopicService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//스프링부트를 실행하자마자 db에 저장하기 위한 초기화 클래스
@Component
public class InitialGameTopicInitializer implements CommandLineRunner {

    private final InitialGameTopicService initialGameTopicService;

    @Autowired
    public InitialGameTopicInitializer(InitialGameTopicService initialGameTopicService) {
        this.initialGameTopicService = initialGameTopicService;
    }

    @Override
    public void run(String... args) throws Exception {
        saveInitialTopics();
    }

    private void saveInitialTopics() throws IOException {
        // 엑셀 파일을 클래스패스에서 읽어오기
        InputStream inputStream = new ClassPathResource("excel/initial_game_data.xlsx").getInputStream();

        // 엑셀 파일로부터 주제 저장
        List<InitialGameTopic> savedTopics = initialGameTopicService.saveTopicsFromExcel(inputStream);

    }
}
