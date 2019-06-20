package com.hsi.parsing.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

/**
 * Created by Siyona on 6/18/2019.
 */
@Service
public interface ConvertToMultipleCSVService {
	String convertToCSV(String dirPath) throws IOException;
}
