package com.myclass.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.LoginDto;
import com.myclass.entity.User;
import com.myclass.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService userSerice;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("")
	@CrossOrigin
	public Object userRegister(@RequestBody User user) {
		try {
			userSerice.add(user);
			return new ResponseEntity<String>("dang ky thanh cong", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Dang ky that bai", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("login")
	@CrossOrigin
	public Object Login(@RequestBody LoginDto loginDto) {
		try {
			//kiểm tra đăng nhập
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
			Date dateNow = new Date();
			//Tạo token
			String token = Jwts.builder()
					.setSubject(loginDto.getEmail()) //Lưu email
					.setIssuedAt(dateNow)	//Ngày tạo
					.setExpiration(new Date(dateNow.getTime() + 864000000L)) //ngày hết hạn
					.signWith(SignatureAlgorithm.HS512, "tanthinh") //Mã hóa thông tin
					.compact();
			return new ResponseEntity<String>(token ,HttpStatus.OK);
		} catch (BadCredentialsException e) {
			//nếu không xảy ra exception tức là thông tin không hợp lệ
			return new ResponseEntity<String>("Sai thông tin đăng nhập",HttpStatus.BAD_REQUEST);
		}
		catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
}
