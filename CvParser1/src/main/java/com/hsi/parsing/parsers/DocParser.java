package com.hsi.parsing.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class DocParser extends AbstractParser{

	@Override
	public String convert(String dirPath) throws IOException {
		System.out.println("Auto *****");
		try { // Please propertise files for the constants (we can use spring to read values
				// from
				// properties file)
				// Make this class as @Service or @Component class(Spring annotated class)
				// Use user defined exceptions
				// Write Junits for positve and negitive scenarios
			// String dirPath = "C://Users//Keerti.Hakki//Desktop//MultipleDocs";
			File dir = new File(dirPath);
			File[] files = dir.listFiles();
			long fileDate;
			
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
					}
					File csvFile = new File(dirPath + "//Temp//" + csvfileName);

					if (csvFile.exists()) {
						fw = new FileWriter(csvFile, true);// if file exists append to file. Works fine.
					} else {
						csvFile.createNewFile();
						fw = new FileWriter(csvFile);
					}

					StringBuilder nameB = new StringBuilder();
					StringBuilder emailB = new StringBuilder();
					StringBuilder phoneB = new StringBuilder();

					for (int i = 0; i < fileData.length; i++) {
						/* extract Name */
						if (fileData[i] != null) {
							// System.out.println(fileData[i]);
							// fw.write(fileData[i]);

							/* extract Name */
							handleName(nameB, fileData, i);
							/* extract phone numbers */
							handlePhone(phoneB, fileData, i);
							/* extract email */
							handleMail(emailB, fileData, i);

							// /* Years of experience */
							// handleExp(fileData, fw, i);
							//
							// /* extract education */
							// handleEducation(fileData, fw, i);

						}

					}
					if (nameB.length() > 0)
						nameB.deleteCharAt(nameB.length() - 1);
					if (emailB.length() > 0)
						emailB.deleteCharAt(emailB.length() - 1);
					if (phoneB.length() > 0)
						phoneB.deleteCharAt(phoneB.length() - 1);

					fw.write(nameB.toString());
					fw.write(",");
					fw.write(phoneB.toString());
					fw.write(",");
					fw.write(emailB.toString());

					// System.out.println("Writing successful");
					// close the file
					fw.close();
				}
			}
		} catch (Exception exep) {
			exep.printStackTrace();
		}
		return "Writing successfull";
	}

	
	
}
