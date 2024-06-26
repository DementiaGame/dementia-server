package synapse.dementia.domain.admin.excel.util;

import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Component;

@Component
public class ExcelCellValueExtractor {

    //엑셀 데이터 추출하는 메서드
    public String getCellValue(Cell cell) {
        if (cell == null) {
            return null;  // 또는 "(empty)"와 같은 기본 값을 반환
        }
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