package synapse.dementia.domain.initialgame.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import synapse.dementia.domain.initialgame.dto.response.InitialGameTopicResponse;
import synapse.dementia.global.excel.repository.ExcelDataRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameTopicService {

    private final ExcelDataRepository excelDataRepository;

    @Autowired
    public GameTopicService(ExcelDataRepository excelDataRepository) {
        this.excelDataRepository = excelDataRepository;
    }

    @Transactional(readOnly = true)
    public List<InitialGameTopicResponse> getAllTopics() {
        return excelDataRepository.findDistinctTopics()
                .stream()
                .map(InitialGameTopicResponse::new)
                .collect(Collectors.toList());
    }
}
