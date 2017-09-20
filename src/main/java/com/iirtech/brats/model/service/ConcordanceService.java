package com.iirtech.brats.model.service;

import java.util.ArrayList;

public interface ConcordanceService {
	
	ArrayList<ArrayList<String>> textConcordance(String text);
	
	ArrayList<ArrayList<String>> entityConcordance(String type, String text);
	
	ArrayList<ArrayList<String>> eventConcordance(String type, String text);
	
}
