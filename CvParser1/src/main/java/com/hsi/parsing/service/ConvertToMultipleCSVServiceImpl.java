package com.hsi.parsing.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.hsi.parsing.parsers.AbstractParser;
import com.hsi.parsing.parsers.DocParser;
import com.hsi.parsing.parsers.DocxParser;
import com.hsi.parsing.parsers.PdfParser;

@Service
public class ConvertToMultipleCSVServiceImpl implements ConvertToMultipleCSVService {
	
	AbstractParser docParser = new DocParser();
	AbstractParser docxParser = new DocxParser();
	AbstractParser pdfParser = new PdfParser();
	
	public String convertToCSV(String filePath) throws IOException {
		docParser.convert(filePath);
		docxParser.convert(filePath);
		pdfParser.convert(filePath);
		
		return filePath;
	}

}