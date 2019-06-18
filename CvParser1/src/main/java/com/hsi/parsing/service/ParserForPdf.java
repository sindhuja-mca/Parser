/**
 * 
 */
package com.hsi.parsing.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author VKuma09
 *
 */
@Service
public class ParserForPdf implements IFileService {

  /*
   * (non-Javadoc)
   * 
   * @see com.hsi.parsing.service.IFileService#processFiles(org.springframework.web.multipart.
   * MultipartFile[])
   */
  @Override
  public String processFiles(MultipartFile[] files) {
    // TODO Auto-generated method stub
    return null;
  }

}
