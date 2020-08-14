package com.myclass.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
		super(authenticationManager);
		this.userDetailsService = userDetailsService;
	}

	// Chức năng kiểm tra Token

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// B1: lấy Token từ header
		String tokenHeader = request.getHeader("Authorization");
		System.out.println(tokenHeader);

		// B2: Loại bỏ tiền tố 'Bearer ' để lấy JWT token
		if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
			try {
				String token = tokenHeader.replace("Bearer ", "");
				System.out.println(token);

				// B3: Giải mã token lấy email gán trong subject của token.
				String email = Jwts.parser().setSigningKey("tanthinh").parseClaimsJws(token).getBody().getSubject();

				System.out.println(email);

				// B4: sử dụng email vừa lấy, và truy vấn db để lấy thông tin user
				UserDetails userDetails = userDetailsService.loadUserByUsername(email);

				Authentication authenticiation = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());

				// B5: Set thông tin user vào securityContext
				SecurityContextHolder.getContext().setAuthentication(authenticiation);
			} catch (Exception e) {
				response.sendError(401, "Token không đúng định dạng");
			}
		}else {
			response.sendError(400,"Vui lòng đăng nhập");
		}
		// Cho phép request đi tiếp vào filter tiếp theo hoặc vào controller
		chain.doFilter(request, response);

	}

}
