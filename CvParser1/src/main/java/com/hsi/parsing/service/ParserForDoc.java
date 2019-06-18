package com.hsi.parsing.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParserForDoc implements IFileService {
	
	public static String dirPath = "E://personel//resume//old";



	public void processFiles(MultipartFile[] files ) {

		try {

		String[] primeSkills;
		boolean nameFlag = false;
		boolean phoneFlag = false;
		boolean emailFlag = false;

		if (files.length == 0) {
			System.out.println("The directory is empty");
		} else {

			
			for (int cnt = 0; cnt < files.length; cnt++) {

				String fileName = files[cnt].getOriginalFilename();
				System.out.println(fileName);
				if (!fileName.endsWith(".doc")) {
					throw new RuntimeException( "This file extension is not supported yet!");
				}

				WordExtractor extractor = null;

				HWPFDocument document = new HWPFDocument(files[cnt].getResource().getInputStream());
				extractor = new WordExtractor(document);
				String[] fileData = extractor.getParagraphText();
				extractor.close();
				

				String csvfileName = fileName.replace(".doc", ".csv");

				FileWriter fw = null;
				String csvPath = dirPath + "//CSV";
				File csvFile = new File(csvPath + "//" + csvfileName);
				File csvPathDir = new File(csvPath);
				if (!csvPathDir.exists()) {
					csvPathDir.mkdirs();
				}

				if (csvFile.exists()) {
					fw = new FileWriter(csvFile, true);// if file exists append to file. Works fine.
				} else {
					csvFile.createNewFile();
					fw = new FileWriter(csvFile);
				}

				nameFlag = false;
				phoneFlag = false;
				emailFlag = false;

				primeSkills = new String[fileData.length];

				// fw.write(fileDate);

				for (int i = 0; i < fileData.length; i++) {

					if (fileData[i] != null) {
						// System.out.println(fileData[i]);
						// fw.write(fileData[i]);

						/* extract Name */
						handleName(nameFlag, fileData, fw, i);
						/* extract phone numbers */
						handlePhone(phoneFlag, fileData, fw, i);

						/* extract email */
						handleMail(emailFlag, fileData, fw, i);

						/* extract Primary skills */
						handlePrimarySkills(primeSkills, fileData, fw, i);

						/* Years of experience */
						handleExp(fileData, fw, i);

						/* extract education */
						handleEducation(fileData, fw, i);
						
					}

				}

				System.out.println("Writing successful");
				// close the file
				fw.close();
			}
		}
} catch (Exception exep) {
		exep.printStackTrace();
}
	}

	private static void handleEducation(String[] fileData, FileWriter fw, int i) throws IOException {
		String education;
		education = findEducation(fileData[i]);
		if (education != "[]") {

			fw.write(education);
			fw.write(",");
			System.out.println(education);

		}
	}

	private static void handleExp(String[] fileData, FileWriter fw, int i) throws IOException {
		String yearExp;
		yearExp = findYearsOfxperience(fileData[i]);
		if (yearExp != "[]") {

			fw.write(yearExp);
			fw.write(",");
			System.out.println(yearExp);

		}
	}

	private static void handlePrimarySkills(String[] primeSkills, String[] fileData, FileWriter fw, int i)
			throws IOException {
		primeSkills[i] = findPrimarySkills(fileData[i]);
		
		if (i == (fileData.length) - 1) {
			LinkedHashSet<String> lhSetColors = new LinkedHashSet<String>(Arrays.asList(primeSkills));

			// create array from the LinkedHashSet
			String[] newArray = lhSetColors.toArray(new String[lhSetColors.size()]);
			fw.write(Arrays.toString(newArray));
			// fw.write(",");
			System.out.println(Arrays.toString(newArray));

		}
	}

	private static void handleMail(boolean emailFlag, String[] fileData, FileWriter fw, int i) throws IOException {
		String email;
		email = findEmail(fileData[i]);
		if (email != "[]" && emailFlag != true) {

			fw.write(email);
			fw.write("\n");
			System.out.println(email);
			emailFlag = true;
		}
	}

	private static void handlePhone(boolean phoneFlag, String[] fileData, FileWriter fw, int i) throws IOException {
		String phoneNumbers;
		phoneNumbers = findPhoneNumber(fileData[i]);
		if (phoneNumbers != "[]" && phoneFlag != true) {

			fw.write(phoneNumbers);
			fw.write(",");
			System.out.println(phoneNumbers);
			phoneFlag = true;
		}
	}

	private static void handleName(boolean nameFlag, String[] fileData, FileWriter fw, int i) throws IOException {
		String name;
		name = findName(fileData[i]);
		if (name != "[]" && nameFlag != true) {

			fw.write(name);
			fw.write(",");
			System.out.println(name);
			nameFlag = true;

		}
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
	
		Pattern pattern = Pattern.compile(
				("\\b(C|C++|Java|PHP|Python|Testing|Automation|Perl|.Net|Unix|Windows)\\b").toString(),
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			primeSkills.add(matcher.group());
		}

		Set<String> ps = new LinkedHashSet<String>(primeSkills);
	
		return ps.toString();
		
	}

		public static String findYearsOfxperience(String line) {
		List<String> yearExp = new ArrayList<>();
	
		Pattern pattern = Pattern.compile(("\\b(Experience|EXPERIENCE)\\b\\s(d{2})[\\t]\\b(years|Yrs)\\b").toString(),
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			yearExp.add(matcher.group());
		}
		return yearExp.toString();
	}



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
		
		Pattern pattern = Pattern
				.compile(("\\b(Diploma|BSc|MSc|BE|Bachelor|Engineer(ing?)|BTech|Master|MTech|MBA|MCA|BCA|BCom|PhD)\\b")
						.toString(), Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(details);
		while (matcher.find()) {
			education.add(matcher.group());
		}
		return education.toString();
	}

	public static String findEmail(String details) {
		List<String> emailList = new ArrayList<>();
		Pattern pattern = Pattern.compile(("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").toString(),
				Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(details);
		while (matcher.find()) {
			emailList.add(matcher.group());
		}
		return emailList.toString();
	}

}
