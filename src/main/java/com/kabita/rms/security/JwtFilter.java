package com.kabita.rms.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestToken = request.getHeader("Authorization");
		String token = null;
		String username = null;

//		getting the authorization header and extracting the token from it
		if (requestToken != null && requestToken.startsWith("Bearer ")) {
			token = requestToken.substring(7);
			try {
				username = jwtTokenHelper.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.out.println("jwt token has expired");
			} catch (MalformedJwtException e) {
				System.out.println("Invalid jwt");
			}
		} else {
			System.out.println("Jwt token does not start with bearer");
		}

//		validating token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

			if (this.jwtTokenHelper.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

			} else {
				System.out.println("Invalid jwt token");
			}
		} else {
			System.out.println("Username is null or context is not null");
		}

		filterChain.doFilter(request, response);
	}

}
