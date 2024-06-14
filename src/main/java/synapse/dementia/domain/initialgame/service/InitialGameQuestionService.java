package synapse.dementia.domain.initialgame.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface InitialGameQuestionService {
    void saveQuestions(Map<String, List<String>> topicAnswerMap) throws IOException;

}
