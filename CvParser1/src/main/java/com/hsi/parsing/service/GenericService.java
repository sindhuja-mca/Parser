/**
 * 
 */
package com.hsi.parsing.service;

import com.hsi.parsing.exception.CVParsingException;
import com.hsi.parsing.model.FileType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author VKuma09
 *
 */
@Service
public class GenericService {

  @Autowired
  @Qualifier("docservice")
  IFileService docService;

  @Autowired
  @Qualifier("docxservice")
  IFileService docxService;

  @Autowired
  @Qualifier("pdfservice")
  IFileService pdfService;

  public String startProcess(MultipartFile[] files, FileType type) {

    String returnMsg = "";
    switch (type) {
      case DOC:
        returnMsg = docService.processFiles(files);
        break;

      case DOCX:
        returnMsg = docxService.processFiles(files);
        break;

      case PDF:
        returnMsg = pdfService.processFiles(files);
        break;

      default:
        throw new CVParsingException("INVALID FILE TYPE!");
    }
    return returnMsg;
  }

}
