package synapse.dementia.domain.admin.excel.reader;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import synapse.dementia.domain.admin.excel.constant.ExcelDataConstants;
import synapse.dementia.domain.admin.excel.util.ExcelCellValueExtractor;

import java.util.ArrayList;
import java.util.List;

import static synapse.dementia.domain.admin.excel.reader.ExcelQuestionReader.getLists;

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