package com.myclass.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myclass.entity.Role;
import com.myclass.service.RoleService;

@Controller
@RequestMapping("admin/role")
public class AdminRoleController {
	@Autowired
	private RoleService roleSevice;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model) {
		
		List<Role> listRole = roleSevice.getAll();
		model.addAttribute("roles", listRole);
		return "role/index";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("role", new Role());
		return "role/add";
	}
	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("role") Role role, BindingResult errors) {
//		//kiểm tra xem người dùng nhập đúng thông tin không
//		if(role.getName() == null || role.getName().length() < 4) {
//			errors.rejectValue("name", "role", "Vui lòng nhập tên có ít nhất 4 ký tự");
//		}
//		
//		if(role.getDescription().isEmpty()) {
//			errors.rejectValue("description", "role", "Vui lòng nhập mô tả");
//		}
//		
//		
		if(errors.hasErrors()) {
			return "role/add";
		}
		
		roleSevice.add(role);
		return "redirect:/admin/role";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") int id, Model model) {
		Role role = roleSevice.getById(id);
		model.addAttribute("role", role);
		return "role/edit";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@Valid @ModelAttribute("role") Role role, BindingResult errors) {
		
		if (errors.hasErrors()) {
			return "role/edit";
		}
		roleSevice.update(role);
		return "redirect:/admin/role";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(@RequestParam("id") int id) {
		roleSevice.delete(id);
		return "redirect:/admin/role";
	}
	
	

}
