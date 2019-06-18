/**
 * 
 */
package com.hsi.parsing.model;

/**
 * @author VKuma09
 *
 */
public enum FileType {

  PDF("pdf"), DOCX("docx"), DOC("doc");

  private String type;

  private FileType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}