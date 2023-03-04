package com.kabita.rms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kabita.rms.security.CustomUserDetailService;
import com.kabita.rms.security.JwtAuthenticationEntryPoint;
import com.kabita.rms.security.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	JwtFilter jwtFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeHttpRequests().antMatchers("/api/auth/login/").permitAll()
				.antMatchers("/api/users/get").permitAll().antMatchers("/api/products/get").permitAll()
				.antMatchers("/api/users/").permitAll().antMatchers("/api/tables/get").permitAll()
				.antMatchers("/api/category/").permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
