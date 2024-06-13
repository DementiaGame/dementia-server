package synapse.dementia.domain.initialgame.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import synapse.dementia.domain.initialgame.constant.InitialGameTopicConstants;
import synapse.dementia.domain.initialgame.domain.InitialGameTopic;
import synapse.dementia.domain.initialgame.repository.InitialTopicRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class InitialGameTopicService {
    private final InitialTopicRepository initialTopicRepository;

    public InitialGameTopicService(InitialTopicRepository initialTopicRepository) {
        this.initialTopicRepository = initialTopicRepository;
    }

    public List<InitialGameTopic> saveTopicsFromExcel(InputStream inputStream) throws IOException {
        List<String> topicsList = readExcelFile(inputStream);
        List<InitialGameTopic> savedTopics = new ArrayList<>();

        for (String topicName : topicsList) {
            InitialGameTopic topic = new InitialGameTopic(topicName);
            savedTopics.add(initialTopicRepository.save(topic));
        }
        return savedTopics;
    }

    private List<String> readExcelFile(InputStream inputStream) throws IOException {
        List<String> topicsList = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(InitialGameTopicConstants.TOPIC_SHEET_NUMBER);

            Row row = sheet.getRow(InitialGameTopicConstants.TOPIC_START_ROW);
            if (row != null) {
                for (int index = InitialGameTopicConstants.TOPIC_START_COLUMN; index <= InitialGameTopicConstants.TOPIC_END_COLUMN; index++) {
                    Cell cell = row.getCell(index);
                    if (cell != null) {
                        String value = "";
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                value = String.valueOf(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                value = cell.getCellFormula();
                                break;
                            case ERROR:
                                value = String.valueOf(cell.getErrorCellValue());
                                break;
                            case BLANK:
                            default:
                                value = "(empty)";
                        }
                        topicsList.add(value);
                    }
                }
            }
        }
        return topicsList;
    }
}
