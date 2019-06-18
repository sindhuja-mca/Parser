package com.hsi.parsing.controller;

import com.hsi.parsing.exception.CVParsingException;
import com.hsi.parsing.model.FileType;
import com.hsi.parsing.model.UploadDetails;
import com.hsi.parsing.service.CSVReader;
import com.hsi.parsing.service.GenericService;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class FileUploadController {

  @Autowired
  GenericService genericService;
  
  @Autowired
  CSVReader read;

  @RequestMapping(value = ("/processFiles"), method = RequestMethod.POST)
  public String fileUpload(HttpServletRequest request, HttpServletResponse response,
      @RequestHeader(name = "filetype") FileType type,
      @RequestParam(name = "File") MultipartFile[] files) throws IOException {

    String responseMessage = "";
    try {
      responseMessage = genericService.startProcess(files,type);
    } catch (CVParsingException e) {
      response.sendError(415, e.getMessage());
    } catch (Exception e) {
      response.sendError(400, e.getMessage());
    }
    return responseMessage;
  }

  @RequestMapping(value = ("/getCandidateDetails"), method = RequestMethod.GET)
  public List<UploadDetails> getCandidateDetails(HttpServletResponse response) throws IOException {
    try {
      return read.startProcessing();
    } catch (Exception e) {
      response.sendError(400, e.getMessage());
    }
    return null;
  }

}
