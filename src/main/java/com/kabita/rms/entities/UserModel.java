package com.kabita.rms.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserModel implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String fullName;
	@Email
	private String email;
	private String password;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private Cart cart;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Orders> order;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Booking> booking;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Billing> billing;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user", referencedColumnName = "userId"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "roleId"))
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = this.roles.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authorities;
	}

	@Override
	public String getUsername() {
		return this.email;
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
