package synapse.dementia.domain.admin.excel.reader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import synapse.dementia.domain.admin.excel.constant.ExcelDataConstants;
import synapse.dementia.domain.admin.excel.util.ExcelCellValueExtractor;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelTopicReader {

    private final ExcelCellValueExtractor cellValueExtractor;

    public ExcelTopicReader(ExcelCellValueExtractor cellValueExtractor) {
        this.cellValueExtractor = cellValueExtractor;
    }

    public List<String> readTopics(XSSFWorkbook workbook) {
        List<String> topicsList = new ArrayList<>();
        XSSFSheet sheet = workbook.getSheetAt(ExcelDataConstants.SHEET_NUMBER);
        Row row = sheet.getRow(ExcelDataConstants.TOPIC_START_ROW);
        if (row != null) {
            for (int index = ExcelDataConstants.START_COLUMN; index <= ExcelDataConstants.END_COLUMN; index++) {
                String value = cellValueExtractor.getCellValue(row.getCell(index));
                topicsList.add(value);
            }
        }
        return topicsList;
    }
}