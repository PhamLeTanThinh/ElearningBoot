package com.myclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.entity.Role;
import com.myclass.service.RoleService;

@RestController
@RequestMapping("api/role")
public class RoleController {
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Object index() {
		try {
			//Gọi service để lấy data
			List<Role> roles = roleService.getAll();
			//Trả data + httpstattus code về cho client.
			return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Khong lay dc du lieu", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object add(@RequestBody Role role) {
		try {
			//Gọi service để thêm data
			roleService.add(role);
			//Trả data + httpstattus code về cho client.
			return new ResponseEntity<String>("thêm mới thành công", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Thêm mới thất bại", HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Object put(@PathVariable int id, @RequestBody Role role) {
		try {
			role.setId(id);
			//Gọi service để cập nhập
			roleService.update(role);
			//Trả data + httpstattus code về cho client.
			return new ResponseEntity<String>("Cập nhật thành công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Cập nhật thất bại", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object put(@PathVariable int id) {
		try {
			//Gọi service để cập nhập
			roleService.delete(id);
			//Trả data + httpstattus code về cho client.
			return new ResponseEntity<String>("Xóa thành công", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Xóa thất bại", HttpStatus.BAD_REQUEST);
		}
	}
}
