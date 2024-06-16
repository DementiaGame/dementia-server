package synapse.dementia.global.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import synapse.dementia.global.excel.model.ExcelData;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData,Long> {
    @Query("SELECT DISTINCT e.topic FROM ExcelData e")
    List<String> findDistinctTopics();

}
