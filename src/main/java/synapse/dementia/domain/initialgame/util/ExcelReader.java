package synapse.dementia.domain.initialgame.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import synapse.dementia.domain.initialgame.dto.request.InitialGameTopicRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static List<InitialGameTopicRequest> readExcelFile(InputStream inputStream) throws IOException {
        List<InitialGameTopicRequest> topics = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // header row
                    continue;
                }
                String topicName = row.getCell(0).getStringCellValue();
                topics.add(new InitialGameTopicRequest(topicName));
            }
        }

        return topics;
    }
}
