package com.hsi.parsing.config;

import com.hsi.parsing.parsers.AbstractParser;
import com.hsi.parsing.parsers.DocParser;
import com.hsi.parsing.parsers.DocxParser;
import com.hsi.parsing.parsers.PdfParser;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResumeParserConfig {

  @Bean
  @Qualifier("docParser")
  public AbstractParser docParser() {
    return new DocParser();
  }

  @Bean
  @Qualifier("docxParser")
  public AbstractParser docxParser() {
    return new DocxParser();
  }

  @Bean
  @Qualifier("pdfParser")
  public AbstractParser pdfParser() {
    return new PdfParser();
  }

}
