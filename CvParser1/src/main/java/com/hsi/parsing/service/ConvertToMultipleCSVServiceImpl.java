package com.hsi.parsing.service;

import com.hsi.parsing.parsers.AbstractParser;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertToMultipleCSVServiceImpl implements ConvertToMultipleCSVService {

  @Autowired
  AbstractParser docParser;
  @Autowired
  AbstractParser docxParser;
  @Autowired
  AbstractParser pdfParser;

  public String convertToCSV(String filePath) throws IOException {
    docParser.convert(filePath);
    docxParser.convert(filePath);
    pdfParser.convert(filePath);

    return filePath;
  }

}