package com.hsi.parsing.parsers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractParser {

	private static final String NAME = "^[\\p{L} .'-]+$";
	private static final String SKILLS = "\\b(C|C++|Java|PHP|Python|Testing|Automation|Perl|.Net|Unix|Windows)\\b";
	private static final String EXPERIENCE = "\\b(Experience|EXPERIENCE)\\b\\s(d{2})[\\t]\\b(years|Yrs)\\b";
	private static final String EMAIL = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";
	private static final String EDUCATION = "\\b(Diploma|BSc|MSc|BE|Bachelor|Engineer(ing?)|BTech|Master|MTech|MBA|MCA|BCA|BCom|PhD)\\b";
	private static final String PHONE_NUMBER = "([+]([0-9]{2}))[-]?[\\s]?([0-9]{9})";

	private void handleEducation(String[] fileData, FileWriter fw, int i) throws IOException {
		String education;
		education = findEducation(fileData[i]);
		if (education != "[]") {

			fw.write(education);
			fw.write(",");
			System.out.println(education);

		}
	}

	private void handleExp(String[] fileData, FileWriter fw, int i) throws IOException {
		String yearExp;
		yearExp = findYearsOfxperience(fileData[i]);
		if (yearExp != "[]") {

			fw.write(yearExp);
			fw.write(",");
			System.out.println(yearExp);

		}
	}

	private void handlePrimarySkills(String[] primeSkills, String[] fileData, FileWriter fw, int i) throws IOException {
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

	public void handleName(StringBuilder sb, String[] fileData, int i) throws IOException {
		if (sb.length() == 0)
			writeToSB(sb, findName(fileData[i]));
	}

	public void handlePhone(StringBuilder sb, String[] fileData, int i) throws IOException {
		writeToSB(sb, findPhoneNumber(fileData[i]));
	}

	public void handleMail(StringBuilder sb, String[] fileData, int i) throws IOException {
		writeToSB(sb, findEmail(fileData[i]));
	}

	private void writeToSB(StringBuilder sb, String var) throws IOException {
		if (var != null && !"[]".equals(var) && !"".equals(var) && !" ".equals(var)) {
			sb.append(var).append("|");
			System.out.println(sb.toString());
		}
	}

	private String findName(String line) {
		return findPattern(line, Pattern.compile(NAME.toString(), Pattern.CASE_INSENSITIVE));
	}

	private String findPhoneNumber(String line) {
		return findPattern(line, Pattern.compile(PHONE_NUMBER.toString(), Pattern.MULTILINE | Pattern.DOTALL));
	}

	private String findEmail(String line) {
		return findPattern(line, Pattern.compile(EMAIL.toString(), Pattern.MULTILINE));
	}

	private String findPattern(String line, Pattern pattern) {

		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	private String findPrimarySkills(String line) {
		List<String> primeSkills = new ArrayList<>();

		Pattern pattern = Pattern.compile(SKILLS.toString(), Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			primeSkills.add(matcher.group());
		}

		Set<String> ps = new LinkedHashSet<String>(primeSkills);

		return ps.toString();

	}

	private String findYearsOfxperience(String line) {
		List<String> yearExp = new ArrayList<>();

		Pattern pattern = Pattern.compile(EXPERIENCE.toString(), Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			yearExp.add(matcher.group());
		}
		return yearExp.toString();
	}

	private String findEducation(String details) {
		List<String> education = new ArrayList<>();

		Pattern pattern = Pattern.compile(EDUCATION.toString(), Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(details);
		while (matcher.find()) {
			education.add(matcher.group());
		}
		return education.toString();
	}

	public abstract String convert(String dirPath) throws IOException;

}
