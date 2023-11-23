package com.ssafy.member.model.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class MemberDto implements UserDetails, Serializable {

	private static final long serialVersionUID = 1L;

	private String userEmail; //PK (이메일)

	private String name; //이름 (userName으로 설정하면 UserDetails에 있는 메소드 때문에 userEmail 값 들어감)

	private String userPass; //BCryptEncoder 암호화

	private String joinDate;

	private String role; //권한

	private String token; //jwt 토큰

	private Collection<? extends GrantedAuthority> authorities;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return userPass;
	}

	@Override
	public String getUsername() {
		return userEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
