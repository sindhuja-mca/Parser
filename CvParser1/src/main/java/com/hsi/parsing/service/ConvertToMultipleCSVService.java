package com.hsi.parsing.service;

import com.hsi.parsing.model.CandidateDetails;

import java.io.IOException;
import java.util.List;

/**
 * Created by Siyona on 6/18/2019.
 */
interface ConvertToMultipleCSVService {
    public String convertToCSV(String dirPath) throws IOException;
}
