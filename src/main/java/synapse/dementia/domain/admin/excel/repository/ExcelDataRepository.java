package synapse.dementia.domain.admin.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synapse.dementia.domain.admin.excel.model.ExcelData;

import java.util.List;

@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData,Long> {
    @Query("SELECT DISTINCT e.topic FROM ExcelData e")
    List<String> findDistinctTopics();
    List<ExcelData> findByTopic(String topic);


}
