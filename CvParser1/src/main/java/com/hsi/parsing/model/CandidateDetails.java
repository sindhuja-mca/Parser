

package com.hsi.parsing.model;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CandidateDetails {
	private String candidateName;
	private String candidateEmail;
	private String candidatePhone;
	private String uploadedFileName;
	private String qualification;
	private String experience;
	private String primarySkill;
	private String secondarySkill;
}

