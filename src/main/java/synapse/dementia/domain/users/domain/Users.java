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
import lombok.Getter;
import synapse.dementia.global.domain.BaseEntity;

@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
@Getter
public class Users extends BaseEntity {
	@Id
	@Column(name = "users_idx")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usersIdx;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	protected Users() {
	}
	
	public Users(String nickName, String password, Integer birthYear, Gender gender, Boolean deleted, Role role) {
		this.nickName = nickName;
		this.password = password;
		this.birthYear = birthYear;
		this.gender = gender;
		this.deleted = deleted;
		this.role = role;
	}
}