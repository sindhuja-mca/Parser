package com.hsi.parsing.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import com.hsi.parsing.model.UploadDetails;

@Service
public class CSVReader{
	

	public static String dirPath = "E://personel//resume//old//CSV";

	public List<UploadDetails> startProcessing() throws IOException {
		File dir = new File(dirPath);
		File[] listFiles = dir.listFiles();
		List<UploadDetails> uploadList = new ArrayList<>();
		for (File f : listFiles) {
			if (f.isDirectory()) {
				continue;
			}
			uploadList.add(processIt(Paths.get(f.getAbsolutePath())));
		}
		return uploadList;
	}

	private UploadDetails processIt(Path p) throws IOException {
		System.out.println(p);
		try (Reader reader = Files.newBufferedReader(p);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			List<CSVRecord> records = csvParser.getRecords();

			// Accessing Values by Column Index
			CSVRecord csvRecord = records.get(0);
			String name = "";
			String email = "";

			String phone = "";

			if (csvRecord.size() > 0)
				name = csvRecord.get(0);
			if (csvRecord.size() > 1)
				phone = csvRecord.get(1);
			if (csvRecord.size() > 2)
				email = csvRecord.get(2);
			

			System.out.println("---------------");
			System.out.println("Name : " + name);
			System.out.println("Email : " + email);
			System.out.println("Phone : " + phone);
			System.out.println("---------------\n\n");

			return new UploadDetails(name, email, phone, null, null, null, null, null, null);

		}
	}
}
