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
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExcelReaderTest {

    // 엑셀 파일을 읽어와 주제 행을 읽어오는지 테스트하기 위한 메서드
    public static List<String> readExcelFile(MultipartFile file) throws IOException {
        List<String> topicsList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {//XSSFWorkbook을 사용해서 엑셀 파일을 읽어옴
            XSSFSheet sheet = workbook.getSheetAt(1);//두번째 시트를 가져온다.

            // 두 번째 행 (index 1)
            Row row = sheet.getRow(1);
            if (row != null) {
                for (int i = 1; i <= 13; i++) {  // B2부터 N2까지 (index 1 to 13)
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        String value = "";
                        //셀 타입에 따라서 각자 다른 형식으로 가져오기 위한 switch문 작성
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
                        topicsList.add(value);//읽어온 값들을 리스트에 추가
                    }
                }
            }
        }

        return topicsList;
    }

    //정답 데이터들을 잘 읽어오는지 확인하기 위한 메서드
    public static List<String> readAnswerData(MultipartFile file) throws IOException {
        List<String> answerList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(1); // 두번째 시트를 가져온다.

            // B3부터 N106까지의 데이터를 읽어오기
            for (int rowIndex = 2; rowIndex <= 105; rowIndex++) { // 행 범위: 3행부터 106행까지 (index는 0부터 시작)
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    for (int colIndex = 1; colIndex <= 13; colIndex++) { // 열 범위: B부터 N까지 (index는 1부터 시작)
                        Cell cell = row.getCell(colIndex);
                        if (cell != null) {
                            String value = "";
                            // 셀 타입에 따라서 각자 다른 형식으로 가져오기 위한 switch문 작성
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
                            answerList.add(value); // 읽어온 값들을 리스트에 추가
                        }
                    }
                }
            }
        }

        return answerList;
    }


    @Test
    void testReadExcelFile() throws Exception {
        // Given: main 리소스 폴더에 있는 엑셀 파일을 읽어오기
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("excel/initial_game_data.xlsx");
        assertNotNull(inputStream, "엑셀파일 없음");
        MultipartFile file = new MockMultipartFile("initial_game_data.xlsx", inputStream);

        // When: 엑셀 파일을 읽고, 주제 이름을 리스트로 반환
        List<String> topics = readExcelFile(file);

        // Then: 주제 이름을 출력
        topics.forEach(topic -> System.out.println("Topic Name: " + topic));

        // Assert that we have the expected number of topics
        assertEquals(13, topics.size(), "13개의 주제를 올바르게 읽어옴");
    }

    @Test
    void testReadAnswerData() throws Exception {
        // Given: main 리소스 폴더에 있는 엑셀 파일을 읽어오기
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("excel/initial_game_data.xlsx");
        assertNotNull(inputStream, "엑셀파일 없음");
        MultipartFile file = new MockMultipartFile("initial_game_data.xlsx", inputStream);

        // When: 엑셀 파일을 읽고, 정답 데이터를 리스트로 반환
        List<String> answers = readAnswerData(file);

        // Then: 정답 데이터가 잘 읽혀졌는지 확인 (콘솔에 출력)
        assertNotNull(answers, "정답 리스트가 null입니다.");
        assertFalse(answers.isEmpty(), "정답 리스트가 비어 있습니다.");

        // 전체 정답 데이터 출력
        answers.forEach(System.out::println);
    }
}
