package com.iirtech.brats.model.service;

import java.util.ArrayList;

public interface ConstructureCheckService {
	
	ArrayList<ArrayList<String>> lackConstructrueCheck(String folderName, String nextPath);
	
	ArrayList<ArrayList<String>> mismatchConstructureCheck(String folderName, String nextPath);
}
