package synapse.dementia.domain.admin.excel.reader;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import synapse.dementia.domain.admin.excel.model.ExcelData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelReader {
    private final ExcelTopicReader topicReader;
    private final ExcelAnswerReader answerReader;
    private final ExcelQuestionReader questionReader;

    public ExcelReader(ExcelTopicReader topicReader, ExcelAnswerReader answerReader, ExcelQuestionReader questionReader) {
        this.topicReader = topicReader;
        this.answerReader = answerReader;
        this.questionReader = questionReader;
    }

    //주제랑 정답 매칭하는 메서드
    public List<ExcelData> mapTopicsToAnswers(InputStream inputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            List<String> topics = topicReader.readTopics(workbook);
            List<List<String>> answers = answerReader.readAnswers(workbook);
            List<List<String>> questions = questionReader.readQuestions(workbook);

            List<ExcelData> excelDataList = new ArrayList<>();

            for (int colIndex = 0; colIndex < topics.size(); colIndex++) {
                String topic = topics.get(colIndex);

                for (int rowIndex = 0; rowIndex < answers.size(); rowIndex++) {
                    if (colIndex < answers.get(rowIndex).size() && colIndex < questions.get(rowIndex).size()) {
                        String answer = answers.get(rowIndex).get(colIndex);
                        String question = questions.get(rowIndex).get(colIndex);

                        if (topic != null && !topic.isEmpty() && answer != null && !answer.isEmpty() && question != null && !question.isEmpty()) {
                            ExcelData excelData = ExcelData.builder()
                                    .topic(topic)
                                    .answer(answer)
                                    .question(question)
                                    .build();

                            excelDataList.add(excelData);
                        }
                    }
                }
            }

            return excelDataList;
        }
    }
}
