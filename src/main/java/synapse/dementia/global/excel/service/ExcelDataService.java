package synapse.dementia.global.excel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.global.excel.model.ExcelData;
import synapse.dementia.global.excel.reader.ExcelReader;
import synapse.dementia.global.excel.repository.ExcelDataRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class ExcelDataService {

    private final ExcelReader excelReader;
    private final ExcelDataRepository excelDataRepository;

    public ExcelDataService(ExcelReader excelReader, ExcelDataRepository excelDataRepository) {
        this.excelReader = excelReader;
        this.excelDataRepository = excelDataRepository;
    }

    @Transactional
    public void saveExcelData(InputStream inputStream) throws IOException {
        List<ExcelData> excelDataList = excelReader.mapTopicsToAnswers(inputStream);
        excelDataRepository.saveAll(excelDataList);
    }
}
