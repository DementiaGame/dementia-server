package synapse.dementia.global.excel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synapse.dementia.global.excel.model.ExcelData;

@Repository
public interface ExcelDataRepository extends JpaRepository<ExcelData,Long> {
}
