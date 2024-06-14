package synapse.dementia.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DataMappingTest {

    // 엑셀 파일을 읽어와 주제 행을 읽어오는 메서드
    public static List<String> readTopics(MultipartFile file) throws IOException {
        List<String> topicsList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(1);

            // 두 번째 행 (index 1)
            Row row = sheet.getRow(1);
            if (row != null) {
                for (int i = 1; i <= 13; i++) {  // B2부터 N2까지 (index 1 to 13)
                    Cell cell = row.getCell(i);
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

    // 엑셀 파일을 읽어 정답 데이터를 리스트로 반환하는 메서드
    public static List<List<String>> readAnswers(MultipartFile file) throws IOException {
        List<List<String>> answerList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(1);

            // B3부터 N106까지의 데이터를 읽어오기
            for (int rowIndex = 2; rowIndex <= 105; rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    List<String> rowData = new ArrayList<>();
                    for (int colIndex = 1; colIndex <= 13; colIndex++) {
                        Cell cell = row.getCell(colIndex);
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
                            rowData.add(value);
                        }
                    }
                    answerList.add(rowData);
                }
            }
        }

        return answerList;
    }

    // 엑셀 파일을 읽어 주제와 정답을 매핑하는 메서드
    public static Map<String, List<String>> mapTopicsToAnswers(MultipartFile file) throws IOException {
        List<String> topics = readTopics(file);
        List<List<String>> answers = readAnswers(file);

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

    @Test
    void testMapTopicsToAnswers() throws Exception {
        // Given: main 리소스 폴더에 있는 엑셀 파일을 읽어오기
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("excel/initial_game_data.xlsx");
        assertNotNull(inputStream, "엑셀파일 없음");
        MultipartFile file = new MockMultipartFile("initial_game_data.xlsx", inputStream);

        // When: 엑셀 파일을 읽고, 주제와 정답을 매핑
        Map<String, List<String>> topicAnswerMap = mapTopicsToAnswers(file);

        // Then: 매핑된 주제와 정답을 출력
        topicAnswerMap.forEach((topic, answers) -> {
            System.out.println("Topic: " + topic);
            answers.forEach(answer -> System.out.println(" - Answer: " + answer));
        });

        assertFalse(topicAnswerMap.isEmpty(), "매핑된 주제와 정답이 비어 있습니다.");
        assertEquals(13, topicAnswerMap.size(), "주제의 수가 13개가 아닙니다.");
    }
}
