package com.myclass.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myclass.entity.User;
import com.myclass.repository.UserRepositoy;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepositoy userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//truy vấn database để lấy thông tin user
		User user = userRepository.findByEmail(email);
		if(user == null) throw new UsernameNotFoundException("Không tìm thấy");
		
		//Join đến bangr role
		String roleName = user.getRole().getName();
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();	
		authorities.add(new SimpleGrantedAuthority(roleName));
		
		//Lưu thông tin user vào trong CustomUserDetail để security quản lý
		CustomUserDetails userDetails = new CustomUserDetails(email, user.getPassword(), authorities);
		return userDetails;
	}
	
}
