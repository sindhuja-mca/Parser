package com.hsi.parsing.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IFileService{
	
	
	public String processFiles(MultipartFile[] files );
	
		
}
