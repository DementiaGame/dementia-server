package synapse.dementia.global.excel.reader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import synapse.dementia.global.excel.constant.ExcelDataConstants;
import synapse.dementia.global.excel.util.ExcelCellValueExtractor;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelQuestionReader {
    private final ExcelCellValueExtractor excelCellValueExtractor;

    public ExcelQuestionReader(ExcelCellValueExtractor excelCellValueExtractor) {
        this.excelCellValueExtractor = excelCellValueExtractor;
    }

    //중복 로직 메서드화
    public List<List<String>> readQuestions(XSSFWorkbook workbook) {
        List<List<String>> questionList = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(ExcelDataConstants.QUESTION_SHEET_NUMBER);
        return getLists(questionList, sheet, excelCellValueExtractor);
    }

    static List<List<String>> getLists(List<List<String>> questionList, XSSFSheet sheet, ExcelCellValueExtractor excelCellValueExtractor) {
        for (int rowIndex = ExcelDataConstants.ANSWER_START_ROW; rowIndex <= ExcelDataConstants.ANSWER_END_ROW; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                List<String> rowData = new ArrayList<>();
                for (int colIndex = ExcelDataConstants.START_COLUMN; colIndex <= ExcelDataConstants.END_COLUMN; colIndex++) {
                    String value = excelCellValueExtractor.getCellValue(row.getCell(colIndex));
                    if (value != null) {
                        rowData.add(value);
                    }
                }
                questionList.add(rowData);
            }
        }
        return questionList;
    }
}
