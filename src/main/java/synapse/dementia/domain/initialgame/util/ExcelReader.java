package synapse.dementia.domain.initialgame.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import synapse.dementia.domain.initialgame.constant.InitialGameTopicConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReader {

    public static Map<String, List<String>> mapTopicsToAnswers(InputStream inputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            List<String> topics = readTopics(workbook);
            List<List<String>> answers = readAnswers(workbook);

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

    public static List<String> readTopics(XSSFWorkbook workbook) {
        List<String> topicsList = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(InitialGameTopicConstants.TOPIC_SHEET_NUMBER);
        Row row = sheet.getRow(InitialGameTopicConstants.TOPIC_START_ROW);
        if (row != null) {
            for (int index = InitialGameTopicConstants.TOPIC_START_COLUMN; index <= InitialGameTopicConstants.TOPIC_END_COLUMN; index++) {
                Cell cell = row.getCell(index);
                if (cell != null) {
                    String value = getCellValue(cell);
                    topicsList.add(value);
                }
            }
        }
        return topicsList;
    }

    public static List<List<String>> readAnswers(XSSFWorkbook workbook) {
        List<List<String>> answerList = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(1);
        for (int rowIndex = 2; rowIndex <= 105; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                List<String> rowData = new ArrayList<>();
                for (int colIndex = 1; colIndex <= 13; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    if (cell != null) {
                        String value = getCellValue(cell);
                        rowData.add(value);
                    }
                }
                answerList.add(rowData);
            }
        }
        return answerList;
    }

    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case BLANK:
            default:
                return "(empty)";
        }
    }
}
