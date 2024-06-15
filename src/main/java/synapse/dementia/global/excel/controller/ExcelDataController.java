package synapse.dementia.global.excel.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synapse.dementia.global.excel.service.ExcelDataService;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/excel")
public class ExcelDataController {

    private final ExcelDataService excelDataService;

    @Autowired
    public ExcelDataController(ExcelDataService excelDataService){
        this.excelDataService=excelDataService;
    }

    //시작하자마자 DB에 담기기 위한 postConstruct 어노테이션 생성
    @PostConstruct
    public void init(){
        try{
            ClassPathResource path=new ClassPathResource("excel/initial_game_data.xlsx");
            InputStream inputStream=path.getInputStream();
            excelDataService.saveExcelData(inputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
