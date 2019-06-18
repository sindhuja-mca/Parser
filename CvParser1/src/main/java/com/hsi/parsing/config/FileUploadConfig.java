package com.hsi.parsing.config;

import com.hsi.parsing.service.IFileService;
import com.hsi.parsing.service.ParserForDoc;
import com.hsi.parsing.service.ParserForDocx;
import com.hsi.parsing.service.ParserForPdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileUploadConfig {


  @Autowired
  ParserForPdf pdfParser;

  @Autowired
  ParserForDoc docParser;

  @Autowired
  ParserForDocx docxParser;


  @Bean
  @Qualifier("pdfservice")
  public IFileService getPDFService() {
    return pdfParser;
  }
  
  @Bean
  @Qualifier("docservice")
  public IFileService getDOCService() {
    return docParser;
  }
  
  @Bean
  @Qualifier("docxservice")
  public IFileService getDOCXService() {
    return docxParser;
  }
}
