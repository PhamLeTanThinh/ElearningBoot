package com.myclass.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/file")
public class FileController {
	
	
	//inject đường dẫn thư mục upload từ file application.properties
	@Value("${file.upload-dir}")
	private String uploadFolder;
	
	@Value("${file.upload-result}")
	private String uploadReturn;
	
	@PostMapping("upload")
	public Object upload(@RequestBody MultipartFile file) {
		try {
			String rootPath = System.getProperty("user.dir");
			System.out.println(rootPath);
			String folderPath = rootPath + uploadFolder;
			System.out.println(folderPath);
			//kiem tra xem thu muc da ton tai chua
			File dir = new File(folderPath);
			if(!dir.exists()) {
				dir.mkdir();
			}
			
			//duong dan file
			File filePath = new File(folderPath + file.getOriginalFilename());
			System.out.println(filePath);
			//Luu file
			file.transferTo(filePath);	
			String pathResult = uploadReturn + file.getOriginalFilename();		
			return new ResponseEntity<String>(pathResult,HttpStatus.OK);
					
		} catch (Exception e) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
	}
}
