package synapse.dementia.global.excel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.global.excel.model.ExcelData;
import synapse.dementia.global.excel.reader.ExcelReader;
import synapse.dementia.global.excel.repository.ExcelDataRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map<String, List<String>> topicAnswerMap = excelReader.mapTopicsToAnswers(inputStream);

        List<ExcelData> excelDataEntities = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : topicAnswerMap.entrySet()) {
            String topic = entry.getKey();
            List<String> answers = entry.getValue();
            for (String answer : answers) {
                ExcelData excelDataEntity = ExcelData.builder()
                        .topic(topic)
                        .answer(answer)
                        .question("") // 필요시 추가 로직 구현
                        .build();
                excelDataEntities.add(excelDataEntity);
            }
        }
        excelDataRepository.saveAll(excelDataEntities);
    }
}