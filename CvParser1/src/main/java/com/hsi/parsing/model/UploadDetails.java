package com.hsi.parsing.model;

import java.util.Date;

public class UploadDetails {
	private String candidateName;
	private String candidateEmail;
	private String candidatePhone;
	private String uploadedFileName;
	private String qualification;
	private String experience;
	private String primarySkill;
	private String secondarySkill;

	
	public UploadDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UploadDetails(String candidateName, String candidateEmail, String candidatePhone, String uploadedFileName,
			String qualification, String experience, String primarySkill, String secondarySkill, Date uploadedDate) {
		super();
		this.candidateName = candidateName;
		this.candidateEmail = candidateEmail;
		this.candidatePhone = candidatePhone;
		this.uploadedFileName = uploadedFileName;
		this.qualification = qualification;
		this.experience = experience;
		this.primarySkill = primarySkill;
		this.secondarySkill = secondarySkill;
		this.uploadedDate = uploadedDate;
	}

	

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public Date getUploadedDate() {
		return uploadedDate;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getPrimarySkill() {
		return primarySkill;
	}

	public void setPrimarySkill(String primarySkill) {
		this.primarySkill = primarySkill;
	}

	public String getSecondarySkill() {
		return secondarySkill;
	}

	public void setSecondarySkill(String secondarySkill) {
		this.secondarySkill = secondarySkill;
	}

	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	private Date uploadedDate;

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	/**
	 * getCandidatePhone.
	 * 
	 * @return the candidatePhone
	 */
	public String getCandidatePhone() {
		return candidatePhone;
	}

	/**
	 * {enclosing_method}.
	 * 
	 * @param candidatePhone
	 *            the candidatePhone to set
	 */
	public void setCandidatePhone(String candidatePhone) {
		this.candidatePhone = candidatePhone;
	}

	@Override
	public String toString() {
		return candidateName + "," + candidateEmail + "," + candidatePhone + "," + uploadedFileName + ","
				+ qualification + "," + experience + "," + primarySkill + "," + secondarySkill + "," + uploadedDate;
	}

}
