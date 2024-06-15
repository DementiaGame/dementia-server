package synapse.dementia.global.excel.reader;

import jakarta.persistence.Column;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import synapse.dementia.global.excel.constant.ExcelDataConstants;
import synapse.dementia.global.excel.util.ExcelCellValueExtractor;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelAnswerReader {
    private final ExcelCellValueExtractor cellValueExtractor;

    public ExcelAnswerReader(ExcelCellValueExtractor cellValueExtractor) {
        this.cellValueExtractor = cellValueExtractor;
    }

    public List<List<String>> readAnswers(XSSFWorkbook workbook) {
        List<List<String>> answerList = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(ExcelDataConstants.SHEET_NUMBER);
        for (int rowIndex = ExcelDataConstants.ANSWER_START_ROW; rowIndex <= ExcelDataConstants.ANSWER_END_ROW; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                List<String> rowData = new ArrayList<>();
                for (int colIndex = 1; colIndex <= 13; colIndex++) {
                    String value = cellValueExtractor.getCellValue(row.getCell(colIndex));
                    if (value != null) {
                        rowData.add(value);
                    }
                }
                answerList.add(rowData);
            }
        }
        return answerList;
    }
}