package com.myclass.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myclass.service.CategoryService;

@Controller
@RequestMapping("admin/category")
public class AdminCategoryController {

	@Autowired
	private CategoryService categoryService;
}
