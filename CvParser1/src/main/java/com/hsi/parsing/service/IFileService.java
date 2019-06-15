package com.hsi.parsing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hsi.parsing.model.UploadDetails;

@Service
public interface IFileService {
	

			

			UploadDetails fileUpload(MultipartFile file);

		
}
