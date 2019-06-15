package com.hsi.parsing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hsi.parsing.model.UploadDetails;
import com.hsi.parsing.service.IFileService;
//import com.hsi.parsing.service.IUserService;

@RestController

public class UserController {

	//
	// @Autowired
	// IUserService userService;

	@Autowired
	IFileService fileService;

	// @RequestMapping(value=("/registration"),method = RequestMethod.POST)

	// public UserDetails registration(@RequestBody UserDetails sd) {
	// return userService.create(sd);
	// }
	// @RequestMapping(value = ("/login"), method = RequestMethod.POST)
	// public Token login(@RequestHeader("user") String username,
	// @RequestHeader("password") String password) {
	// return userService.login(username, password);
	// }
	//
	// @RequestMapping(value = ("/getMyDetails"), method = RequestMethod.GET)
	// public UserDetails getMyDetails(@RequestHeader("accessToken") String token) {
	// return userService.getMyDetails(token);
	// }
	@RequestMapping(value = ("/FileUpload"), method = RequestMethod.POST)
	public UploadDetails fileUpload(@RequestParam("File") MultipartFile file) {
		// validate(token);
		return fileService.fileUpload(file);
	}
	// private UserDetails validate(String token) {
	// return getMyDetails(token);
	//
	// }
	// @RequestMapping(value=("/logout"),method=RequestMethod.POST)
	// public void logOut(@RequestHeader("accessToken") String token) {
	// userService.logout(token);
	// }
	// }
}
