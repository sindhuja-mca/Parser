package com.hsi.parsing.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.hsi.parsing.exception.ResumeParsingException;
import com.hsi.parsing.model.CandidateDetails;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;

@Service
public class CSVReaderServiceImpl implements CSVReaderService{

@Override
	public List<CandidateDetails> getCandidateDetailsFromCSVFile(String dirPath) throws ResumeParsingException,IOException{
	File dir = new File(dirPath);
		File[] listFiles = dir.listFiles();
		List<CandidateDetails> candidateDetailsList = new ArrayList<CandidateDetails>();
		for (File f : listFiles) {
			if (f.isDirectory()) {
				continue;
			}
			candidateDetailsList.add(getCandidateDetails(Paths.get(f.getAbsolutePath())));
		}
		return candidateDetailsList;
	}

	private CandidateDetails getCandidateDetails(Path p) throws IOException {
		System.out.println(p);
		try (Reader reader = Files.newBufferedReader(p);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			List<CSVRecord> records = csvParser.getRecords();
			CandidateDetails candidateDetail = new CandidateDetails();

			// Accessing Values by Column Index
			CSVRecord csvRecord = records.get(0);
			String name = "";
			String email = "";

			String phone = "";
			String skills = "";

			if (csvRecord.size() > 0)
				name = csvRecord.get(0);
			if (csvRecord.size() > 1)
				phone = csvRecord.get(1);
			if (csvRecord.size() > 2)
				email = csvRecord.get(2);
			if(csvRecord.size()>3)
				skills = csvRecord.get(3);				

			System.out.println("---------------");
			System.out.println("Name : " + name);
			System.out.println("Email : " + email);
			System.out.println("Phone : " + phone);
			System.out.println("skills : " + skills);
			System.out.println("---------------\n\n");
			candidateDetail.setCandidateName(name);
			candidateDetail.setCandidateEmail(email);
			candidateDetail.setCandidatePhone(phone);
			candidateDetail.setPrimarySkill(skills);
			return candidateDetail;
		}
	}
}