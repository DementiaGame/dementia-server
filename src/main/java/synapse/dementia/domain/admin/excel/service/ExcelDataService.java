package synapse.dementia.domain.admin.excel.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import synapse.dementia.domain.admin.excel.model.ExcelData;
import synapse.dementia.domain.admin.excel.reader.ExcelReader;
import synapse.dementia.domain.admin.excel.repository.ExcelDataRepository;

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
		excelDataRepository.deleteAll();

		List<ExcelData> excelDataList = excelReader.mapTopicsToAnswers(inputStream);
		excelDataRepository.saveAll(excelDataList);
	}
}
