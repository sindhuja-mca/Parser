package com.hsi.parsing.service;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hsi.parsing.model.UploadDetails;
import com.opencsv.CSVWriter;

@Service
public class FileServiceImpl implements IFileService {

	private static Map<String, UploadDetails> map = new HashMap<String, UploadDetails>();

	@Override
	public UploadDetails fileUpload(MultipartFile file) {

		String content = "";
		List<XWPFParagraph> paragraphs = null;
		try {
			XWPFDocument document = new XWPFDocument(file.getResource().getInputStream());
			XWPFWordExtractor we = new XWPFWordExtractor(document);
			content = we.getText();
			paragraphs = document.getParagraphs();

			we.close();
			document.close();

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UploadDetails uploadDetails = new UploadDetails();
		uploadDetails.setUploadedFileName(file.getOriginalFilename());
		uploadDetails.setUploadedDate(new Date());
		boolean  profile=false; 

		if(!profile) {
		profile = true;
		uploadDetails.setCandidateName(paragraphs.get(0).getText());
		}


		for (XWPFParagraph p : paragraphs) {
		System.out.println( p.getText());
		System.out.println("================");
		
	}
		
//		for (XWPFParagraph p : paragraphs) {
//			content = p.getParagraphText();
			String findEmail = findEmail(content);
			String findPhone = findPhone(content);

			
			
			if (notNull(findEmail)) {
				uploadDetails.setCandidateEmail(findEmail);
			}

			if (notNull(findPhone)) {
				uploadDetails.setCandidatePhone(findPhone);
			}
			String findApplicantSkills = findApplicantSkills(content);
			if (notNull(findApplicantSkills)) {
				uploadDetails.setPrimarySkill(findApplicantSkills);
			}

			// uploadDetails.setCandidateName(findProfile(content));

			String findEducations = findEducations(content);

			if (notNull(findEducations)) {
				uploadDetails.setQualification(findEducations);
			}

			String experience = findWorkExperiences(content);

			if (notNull(experience)) {
				uploadDetails.setExperience(experience);
			}

//		}

		writeToCSV(uploadDetails.toString().split(","));

		return map.put(uploadDetails.getCandidateEmail(), uploadDetails);
	}

	private boolean notNull(String details) {
		return details != null && !"[]".equals(details) && !"".equals(details);
	}

	private static final String CSV_DATA_PATH = "D:\\candidate\\public\\candidates.csv";

	public void writeToCSV(String[] input) {
		StringWriter writer = new StringWriter();
		try {
			CSVWriter csvWriter = new CSVWriter(writer);
			csvWriter.writeNext(input);
			csvWriter.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		byte[] data = writer.toString().getBytes();
		Path p = Paths.get(CSV_DATA_PATH);
		try (OutputStream out = new BufferedOutputStream(
				Files.newOutputStream(p, java.nio.file.StandardOpenOption.APPEND))) {
			out.write(data, 0, data.length);
		} catch (IOException | NullPointerException x) {
			System.err.println(x.getMessage());
			x.printStackTrace();
		}
	}

	private String findApplicantSkills(String line) {
		ParserHelper parser = new ParserHelper();
		int indexOfSkillsSection = parser.getIndexOfThisSection(RegEx.SKILLS, line);
		if (indexOfSkillsSection != -1) {
			List<Integer> sectionIndexes = parser.getAllSectionIndexes(line);
			String texts = line.replaceFirst(RegEx.SKILLS.toString(), "");
			return helper.getSectionContent(indexOfSkillsSection, sectionIndexes, texts);
		}
		return null;
	}
	private String findWorkExperiences(String line) {
        ParserHelper parser = new ParserHelper();
       
        int indexOfExperience = parser.getIndexOfThisSection(RegEx.EXPERIENCE, line);
        if (indexOfExperience != -1) {
            List<Integer> listOfSectionIndexes = parser.getAllSectionIndexes(line);
            String texts = line.replaceFirst(RegEx.EXPERIENCE.toString(), "");
            return helper.getSectionContent(indexOfExperience, listOfSectionIndexes, texts);
        }
        return null;}
	private String findEmail(String details) {
		List<String> emailList = new ArrayList<>();
		Pattern pattern = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(details);
		System.out.println(matcher);
		while (matcher.find()) {
			emailList.add(matcher.group());
		}
		return emailList.toString();
	}

//	private String findProfile(String line) {
//		ParserHelper helper = new ParserHelper();
//		List<Integer> indexes = helper.getAllSectionIndexes(line);
//		int beginIndex = 0;
//		int endIndex = indexes.get(0);
//		return line.substring(beginIndex, endIndex);
//	}

	public static String findPhone(String line) {
		List<String> phoneNumbers = new ArrayList<>();
		Pattern pattern = Pattern.compile(
				("(?:\\s+|)((0|(?:(\\+|)91))(?:\\s|-)*(?:(?:\\d(?:\\s|-)*\\d{9})|(?:\\d{2}(?:\\s|-)*\\d{8})|(?:\\d{3}(?:\\s|-)*\\d{7}))|\\d{10})(?:\\s+|)")
						.toString(),
				Pattern.MULTILINE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			phoneNumbers.add(matcher.group());
		}
		return phoneNumbers.toString();
	}

	ParserHelper helper = new ParserHelper();

	private String findEducations(String line) {
		ParserHelper parser = new ParserHelper();
		int indexOfEducation = parser.getIndexOfThisSection(RegEx.EDUCATION, line);
		if (indexOfEducation != -1) {
			List<Integer> listOfSectionIndexes = parser.getAllSectionIndexes(line);
			System.out.println("SECTION INDEXES: {}" + listOfSectionIndexes);
			String texts = line.replaceFirst(RegEx.EDUCATION.toString(), "");
			return helper.getSectionContent(indexOfEducation, listOfSectionIndexes, texts);
		}
		return null;
	}

	
}
