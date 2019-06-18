package com.hsi.parsing.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IFileService{
	
	
	void processFiles(MultipartFile[] files );
	
		
}
