package com.hsi.parsing.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.stereotype.Service;

@Service
public class ConvertToMultipleCSVServiceImpl implements ConvertToMultipleCSVService {
  @Override
  public String convertToCSV(String dirPath) throws IOException {
    System.out.println("Auto *****");
    try { // Please propertise files for the constants (we can use spring to read values from
          // properties file)
          // Make this class as @Service or @Component class(Spring annotated class)
          // Use user defined exceptions
          // Write Junits for positve and negitive scenarios
      // String dirPath = "C://Users//Keerti.Hakki//Desktop//MultipleDocs";
      File dir = new File(dirPath);
      File[] files = dir.listFiles();
      long fileDate;
      String name;
      String email;
      String[] primeSkills;
      String secondSkills;
      String yearExp;
      String postDate;
      String phoneNumbers;
      String education;

      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

      FileWriter fw = null;
      if (files.length == 0) {
        System.out.println("The directory is empty");
        // use loggers log.debug
      } else {

        /*
         * for (String fileName : files) { System.out.println(fileName);
         */

        // use 1.8 features wherever required
        for (int cnt = 0; cnt < files.length; cnt++) {
          String fileName = files[cnt].getName();
          System.out.println(fileName);
          fileDate = files[cnt].lastModified();
          String[] split = fileName.split("\\.");
          if (split.length > 0 && !"doc".equals(split[split.length - 1])) {
            System.out.println(fileName + " is not a doc, so skipping!");
            continue;
          }
          System.out.println(" Processing " + fileName + "!");

          System.out.println("fileDate=" + sdf.format(fileDate));

          File file = null;
          WordExtractor extractor = null;// use the properties files...dont use static paths use
                                         // construct path and use dynamically
          // file = new File("C://Users//Keerti.Hakki//Desktop//MultipleDocs//"+fileName);
          file = new File(dirPath + "//" + fileName);
          FileInputStream fis = new FileInputStream(file.getAbsolutePath());
          HWPFDocument document = new HWPFDocument(fis);
          extractor = new WordExtractor(document);
          String[] fileData = extractor.getParagraphText();

          // attach a file to FileWriter

          String csvfileName = fileName.replace(".doc", ".csv");

          File csvPath = new File(dirPath + "//Temp");
          if (!csvPath.exists()) {
            csvPath.mkdirs();
            File csvFile = new File(dirPath + "//Temp" + csvfileName);
            if (!csvFile.exists()) {
              csvFile.createNewFile();
            }
          }

          fw = new FileWriter(dirPath + "//Temp//" + csvfileName);
          // nameFlag = false;
          // phoneFlag = false;
          // emailFlag = false;

          primeSkills = new String[fileData.length];

          // fw.write(fileDate);

          for (int i = 0; i < fileData.length; i++) {
            if (fileData[i] != null) {
              // System.out.println(fileData[i]);
              // fw.write(fileData[i]);

              /* extract Name */
              name = findName(fileData[i]);
              phoneNumbers = findPhoneNumber(fileData[i]);
              email = findEmail(fileData[i]);
              primeSkills[i] = findPrimarySkills(fileData[i]);
              // System.out.println(primeSkills[i]);
              // if (i==('.̣.length)-1){

              /*
               * convert array to list and then add all elements to LinkedHashSet. LinkedHashSet
               * will automatically remove all duplicate elements.
               */
              LinkedHashSet<String> lhSetColors = new LinkedHashSet<String>(
                  Arrays.asList(primeSkills));

              // create array from the LinkedHashSet
              String[] newArray = lhSetColors.toArray(new String[lhSetColors.size()]);

              // System.out.println("Array after removing duplicates: " +
              // Arrays.toString(newArray));

              // old fw.write(primeSkills);
              fw.write(Arrays.toString(newArray));
              // fw.write(",");
              System.out.println(Arrays.toString(newArray));

            }

            /*
             * secondary skills secondSkills = findSecondarySkills(fileData[i]); if (secondSkills !=
             * "[]"){
             * 
             * fw.write(secondSkills); fw.write(","); System.out.println(secondSkills); continue; }
             */

            /* Years of experience */
            yearExp = findYearsOfxperience(fileData[i]);
            if (yearExp != "[]") {

              fw.write(yearExp);
              fw.write(",");
              System.out.println(yearExp);

            }

            /* Resume posted date */
            /*
             * postDate = findResumePostedDate(fileData[i]); if (postDate != "[]"){
             * 
             * fw.write(postDate); fw.write(","); System.out.println(postDate);
             * 
             * }
             */

            /* extract education */
            education = findEducation(fileData[i]);
            if (education != "[]") {

              fw.write(education);
              fw.write(",");
              System.out.println(education);

            }

          }
        }

        System.out.println("Writing successful");
        // close the file
        fw.close();
      }

    } catch (

    Exception exep) {
      exep.printStackTrace();
    }
    return " ";
  }

  public static String findName(String line) {
    List<String> name = new ArrayList<>();
    Pattern pattern = Pattern.compile(("^[\\p{L} .'-]+$").toString(), Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      name.add(matcher.group());
    }
    return name.toString();
  }

  public static String findPrimarySkills(String line) {
    List<String> primeSkills = new ArrayList<>();
    // Pattern pattern = Pattern.compile(("\\b(Skills|SKILLS|Software)\\b").toString(),
    // Pattern.CASE_INSENSITIVE );
    Pattern pattern = Pattern.compile(
        ("\\b(C|C++|Java|PHP|Python|Testing|Automation|Perl|.Net|Unix|Windows)\\b").toString(),
        Pattern.CASE_INSENSITIVE);
    // (C|C++|Java|PHP|Python|Testing|Automation|Perl|.Net|Unix|Windows)
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      primeSkills.add(matcher.group());
    }

    Set<String> ps = new LinkedHashSet<String>(primeSkills);
    // System.out.println("in findps func--"+ps);
    return ps.toString();
    // return primeSkills.toString();
  }

  /*
   * public static String findSecondarySkills(String line) { List<String> secondSkills = new
   * ArrayList<>(); Pattern pattern = Pattern.compile(("\\b\\b").toString(),
   * Pattern.CASE_INSENSITIVE ); Matcher matcher = pattern.matcher(line); while (matcher.find()) {
   * secondSkills.add(matcher.group()); } return secondSkills.toString(); }
   */

  public static String findYearsOfxperience(String line) {
    List<String> yearExp = new ArrayList<>();
    // Pattern pattern =
    // Pattern.compile(("\\b(Experience|EXPERIENCE)\\b\\(d{2})[\\t]\\b(years|Yrs)\\b").toString(),
    // Pattern.CASE_INSENSITIVE );
    Pattern pattern = Pattern.compile(
        ("\\b(Experience|EXPERIENCE)\\b\\s(d{2})[\\t]\\b(years|Yrs)\\b").toString(),
        Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      yearExp.add(matcher.group());
    }
    return yearExp.toString();
  }

  /*
   * public static String findResumePostedDate(String line) { List<String> postDate = new
   * ArrayList<>(); //Pattern pattern =
   * Pattern.compile(("^(\\d{2})[-.](\\d{2})[-.](\\d{4})$").toString(), Pattern.CASE_INSENSITIVE );
   * Pattern pattern = Pattern.compile(("^(\\d{2})[-.](\\d{2})[-.](\\d{4})$").toString(),
   * Pattern.CASE_INSENSITIVE ); Matcher matcher = pattern.matcher(line); while (matcher.find()) {
   * postDate.add(matcher.group()); } return postDate.toString(); }
   */

  public static String findPhoneNumber(String line) {
    List<String> phoneNumbers = new ArrayList<>();
    Pattern pattern = Pattern.compile(("([+]([0-9]{2}))[-]?[\\s]?([0-9]{9})").toString(),
        Pattern.MULTILINE | Pattern.DOTALL);
    Matcher matcher = pattern.matcher(line);
    while (matcher.find()) {
      phoneNumbers.add(matcher.group());
    }
    return phoneNumbers.toString();
  }

  public static String findEducation(String details) {
    List<String> education = new ArrayList<>();
    // Pattern pattern = Pattern.compile(("\\b(Education|Qualification)\\b").toString(),
    // Pattern.MULTILINE);
    Pattern pattern = Pattern.compile(
        ("\\b(Diploma|BSc|MSc|BE|Bachelor|Engineer(ing?)|BTech|Master|MTech|MBA|MCA|BCA|BCom|PhD)\\b")
            .toString(),
        Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(details);
    while (matcher.find()) {
      education.add(matcher.group());
    }
    return education.toString();
  }

  public static String findEmail(String details) {
    List<String> emailList = new ArrayList<>();
    Pattern pattern = Pattern
        .compile(("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").toString(), Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(details);
    while (matcher.find()) {
      emailList.add(matcher.group());
    }
    return emailList.toString();
  }

}