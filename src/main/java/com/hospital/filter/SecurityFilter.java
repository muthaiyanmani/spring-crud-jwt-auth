package com.hospital.filter;

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

import com.hospital.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil util;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader("Authorization");
		if (token != null) {
			String user = util.getSubject(token);
			if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails usr = userDetailsService.loadUserByUsername(user);

				if (util.validateToken(token, usr.getUsername())) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
							usr.getPassword(), usr.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}

			}
		}
		filterChain.doFilter(request, response);

	}

}