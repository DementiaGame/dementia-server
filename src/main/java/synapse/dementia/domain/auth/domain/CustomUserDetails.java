//package synapse.dementia.domain.auth.domain;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import synapse.dementia.domain.users.domain.Role;
//import synapse.dementia.domain.users.domain.Users;
//
//@Entity
//public class CustomUserDetails implements UserDetails {
//
//	@Id
//	private Long usersIdx;
//	@Column
//	private String nickName;
//	@Column
//	private String password;
//
//	@Column
//	private Role role;
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		List<GrantedAuthority> roles = new ArrayList<>();
//		roles.add(new SimpleGrantedAuthority(role.name()));
//		return roles;
//		//return List.of();
//	}
//
//	@Override
//	public String getPassword() {
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return nickName;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return false;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return false;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return false;
//	}
//
//	/**
//	 * 계정 만료 여부
//	 * ture: 만료 안됨
//	 * false: 만료됨
//	 * @return
//	 */
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof CustomUserDetails) {
//			return this.nickName.equals(((CustomUserDetails)obj).nickName);
//		}
//		return false;
//	}
//
//	@Override
//	public int hashCode() {
//		return this.nickName.hashCode();
//	}
//
//	public CustomUserDetails(Users user) {
//		this.usersIdx = user.getUsersIdx();
//		this.nickName = user.getNickName();
//		this.password = user.getPassword();
//		this.role = user.getRole();
//	}
//}
