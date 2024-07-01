package synapse.dementia.domain.admin.initialGame.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import synapse.dementia.domain.admin.excel.model.ExcelData;
import synapse.dementia.domain.admin.excel.repository.ExcelDataRepository;
import synapse.dementia.domain.admin.initialGame.dto.request.AddInitialGameDataReq;
import synapse.dementia.domain.admin.initialGame.repository.InitialGameRepository;
import synapse.dementia.global.exception.ConflictException;
import synapse.dementia.global.exception.ErrorResult;

@Service
@RequiredArgsConstructor
public class InitialGameServiceImpl implements InitialGameService {
	private final InitialGameRepository initialGameRepository;
	private final ExcelDataRepository excelDataRepository;

	@Override
	@Transactional(readOnly = true)
	public List<ExcelData> getAllInitialGameDataByTopic(String topic) {
		return initialGameRepository.findAllByTopic(topic);
	}

	@Override
	@Transactional
	public void addInitialGameData(AddInitialGameDataReq addInitialGameDataReq) {
		excelDataRepository.findExcelDataByQuestionAndAnswerAndTopic(
			addInitialGameDataReq.topic(),
			addInitialGameDataReq.answer(),
			addInitialGameDataReq.question()
		).ifPresent(data -> {
			throw new ConflictException(ErrorResult.DTO_Request_BAD_REQUEST);
		});

		ExcelData excelData = ExcelData.builder()
			.topic(addInitialGameDataReq.topic())
			.question(addInitialGameDataReq.question())
			.answer(addInitialGameDataReq.answer())
			.build();

		excelDataRepository.save(excelData);
	}
}
