package synapse.dementia.domain.admin.initialGame.service;

import java.util.List;

import synapse.dementia.domain.admin.excel.model.ExcelData;
import synapse.dementia.domain.admin.initialGame.dto.request.AddInitialGameDataReq;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;

public interface InitialGameService {
	List<ExcelData> getAllInitialGameDataByTopic(String topic);
	void addInitialGameData(AddInitialGameDataReq addInitialGameDataReq);
	List<InitialGameQuestion> getAllInitialGameData();
}
