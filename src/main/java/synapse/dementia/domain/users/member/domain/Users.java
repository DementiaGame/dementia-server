package synapse.dementia.domain.users.member.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Setter;

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
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameQuestion;
import synapse.dementia.domain.users.game.initialgame.domain.InitialGameResult;
import synapse.dementia.domain.users.game.initialgame.domain.SelectedGameTopic;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameResult;
import synapse.dementia.domain.users.game.multiplayergame.domain.MultiGameUser;
import synapse.dementia.global.base.BaseEntity;

@Entity
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
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

	@Column(name = "nick_name", unique = true, nullable = false)
	private String nickName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "deleted", nullable = false)
	private Boolean deleted = false;

	@Column(name = "face_data", nullable = true, columnDefinition = "TEXT")
	@Lob
	private String faceData;

	@Column(name = "profile_image", nullable = true)
	private String profileImage;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SelectedGameTopic> selectedGameTopics;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InitialGameResult> initialGameResults;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InitialGameQuestion> initialGameQuestions;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MultiGameResult> multiGameResults;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MultiGameUser> multiGameUsers;

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

	public void updateUsers(Integer birthYear, Gender gender, String nickName) {
		this.birthYear = birthYear;
		this.gender = gender;
		this.nickName = nickName;
	}
}