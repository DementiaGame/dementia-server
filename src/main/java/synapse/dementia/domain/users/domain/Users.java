package synapse.dementia.domain.users.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Builder(toBuilder = true)
public class Users extends BaseEntity {
	@Id
	@Column(name = "users_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usersSeq;

	@Column(name = "birth_year", nullable = false)
	private Integer birthYear;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "nick_name", nullable = false)
	private String nickName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "deleted", nullable = false)
	private Boolean deleted = false;

	@Column(name = "face_date", nullable = true, columnDefinition = "TEXT")
	@Lob
	private String faceData;

	@Column(name = "profile_image", nullable = true)
	private String profileImage;
}