package synapse.dementia.global.base;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity {

	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime modifiedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CommonStatus status=CommonStatus.ACTIVE;

	public void active(){
		this.status=CommonStatus.ACTIVE;
	}

	public void inActive(){
		this.status=CommonStatus.INACTIVE;
	}
	public void delete(){
		this.status=CommonStatus.DELETED;
	}
}
