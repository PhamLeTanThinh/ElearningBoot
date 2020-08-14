package com.myclass.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.entity.Category;
import com.myclass.repository.CategoryRepository;
import com.myclass.service.CategoryService;

@RestController
@RequestMapping("api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	@CrossOrigin
	public Object index() {
		try {
			List<Category> categories = categoryService.getAll();
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Khong lay duoc danh sach", HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Object add(@RequestBody Category category) {
		try {
			categoryService.add(category);
			return new ResponseEntity<String>("Them moi thanh cong", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Them moi that bai", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public Object put(@PathVariable int id,@RequestBody Category category) {
		try {
			category.setId(id);
			categoryService.update(category);
			return new ResponseEntity<String>("update thanh cong", HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<String>("Update that bai", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public Object delete(@PathVariable int id) {
		try {
			categoryService.delete(id);
			return new ResponseEntity<String>("Xoa thanh cong", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Xoa that bai", HttpStatus.BAD_REQUEST);
		}
	}
}
