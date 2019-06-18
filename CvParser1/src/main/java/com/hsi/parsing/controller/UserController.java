package com.hsi.parsing.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hsi.parsing.exception.HandleException;
import com.hsi.parsing.model.UploadDetails;
import com.hsi.parsing.service.CSVReader;
import com.hsi.parsing.service.IFileService;

@RestController

public class UserController {

@Autowired
	IFileService fileService;
@Autowired
	CSVReader read;
	
	@RequestMapping(value = ("/processFiles"), method = RequestMethod.POST)
	public String fileUpload(HttpServletRequest request, HttpServletResponse response,@RequestParam(name = "File") MultipartFile[] files) throws IOException {
		
		try {
			fileService.processFiles(files);
		}catch(HandleException e) {
			response.sendError(409, e.getMessage());
		}catch(Exception e) {
			response.sendError(410, "File not supported");
		}
		return "File status";
	}
	@RequestMapping(value = ("/getCandidateDetails"), method = RequestMethod.GET)
	public List<UploadDetails> getCandidateDetails() throws IOException{
		return read.startProcessing();
	}
	
	
}
