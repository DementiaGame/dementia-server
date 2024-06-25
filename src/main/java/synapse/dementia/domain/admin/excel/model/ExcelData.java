package synapse.dementia.domain.admin.excel.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.global.base.BaseEntity;

@Entity
@Table(name = "EXCEL_DATA")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExcelData extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "excel_id")
    private Long idx;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "question", nullable = false)
    private String question;

    @Builder
    public ExcelData(String topic, String answer, String question) {
        this.topic = topic;
        this.answer = answer;
        this.question = question;
    }
}
