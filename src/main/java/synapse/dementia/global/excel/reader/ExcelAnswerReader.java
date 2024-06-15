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

import static synapse.dementia.global.excel.reader.ExcelQuestionReader.getLists;

@Component
public class ExcelAnswerReader {
    private final ExcelCellValueExtractor cellValueExtractor;

    public ExcelAnswerReader(ExcelCellValueExtractor cellValueExtractor) {
        this.cellValueExtractor = cellValueExtractor;
    }

    public List<List<String>> readAnswers(XSSFWorkbook workbook) {
        List<List<String>> answerList = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(ExcelDataConstants.SHEET_NUMBER);
        return getLists(answerList, sheet, cellValueExtractor);
    }
}