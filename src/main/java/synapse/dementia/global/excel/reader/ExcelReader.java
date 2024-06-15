package synapse.dementia.global.excel.reader;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelReader {
    private final ExcelTopicReader topicReader;
    private final ExcelAnswerReader answerReader;

    public ExcelReader(ExcelTopicReader topicReader, ExcelAnswerReader answerReader) {
        this.topicReader = topicReader;
        this.answerReader = answerReader;
    }

    public Map<String, List<String>> mapTopicsToAnswers(InputStream inputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            List<String> topics = topicReader.readTopics(workbook);
            List<List<String>> answers = answerReader.readAnswers(workbook);

            Map<String, List<String>> topicAnswerMap = new HashMap<>();
            for (int i = 0; i < topics.size(); i++) {
                List<String> columnAnswers = new ArrayList<>();
                for (List<String> answerRow : answers) {
                    if (i < answerRow.size()) {
                        columnAnswers.add(answerRow.get(i));
                    }
                }
                topicAnswerMap.put(topics.get(i), columnAnswers);
            }
            return topicAnswerMap;
        }
    }
}