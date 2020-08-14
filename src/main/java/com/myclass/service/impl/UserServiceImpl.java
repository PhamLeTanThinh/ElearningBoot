package com.myclass.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepositoy;
import com.myclass.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepositoy userRepository;

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}
	
	

	@Override
	public User getById(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void add(User user) {
		// kiem tra email ton tai chua
		User entity = userRepository.findByEmail(user.getEmail());
		if(entity == null) {
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		}
		
		// Mã hóa mật khẩu
		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		// Sửa lại mật khẩu thành mật khẩu đã mã hóa trước khi lưu
		user.setPassword(hashed);
		userRepository.save(user);
	}

	@Override
	public void update(User user) {
		// lay thong tin hien tai dang luu trong database
		User entity = userRepository.findById(user.getId()).get();

		// cap nhap lai thon tin truyen tu view len
		if (entity != null) {
			entity.setEmail(user.getEmail());
			entity.setFullname(user.getFullname());
			entity.setAvatar(user.getAvatar());
			entity.setPhone(user.getPhone());
			entity.setRoleId(user.getRoleId());
			userRepository.save(entity);
		}
	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);
	}



	@Override
	public List<UserDto> getAllDto() {

		return userRepository.getAll();
	}



	@Override
	public void doiMatKhau(User user) {
		User entity = userRepository.findByEmail(user.getEmail());
		if(entity != null) {
			// Mã hóa mật khẩu
			String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
			user.setPassword(hashed);
			//doi mat khau
			entity.setPassword(user.getPassword());
			userRepository.save(entity);
		}
	}

}
