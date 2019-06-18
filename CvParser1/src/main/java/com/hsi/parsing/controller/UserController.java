package com.hsi.parsing.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hsi.parsing.model.UploadDetails;
import com.hsi.parsing.service.CSVReader;
import com.hsi.parsing.service.IFileService;
import com.hsi.parsing.service.ParserForDoc;

@RestController

public class UserController {

@Autowired
	IFileService fileService;
@Autowired
	CSVReader read;
	
	@RequestMapping(value = ("/processFiles"), method = RequestMethod.POST)
	public UploadDetails fileUpload(@RequestParam(name = "File") MultipartFile[] files) {

		new ParserForDoc().processFiles(files);
		return null;
	}
	@RequestMapping(value = ("/getCandidateDetails"), method = RequestMethod.GET)
	public List<UploadDetails> getCandidateDetails() throws IOException{
		return read.startProcessing();
	}
	
	
}
