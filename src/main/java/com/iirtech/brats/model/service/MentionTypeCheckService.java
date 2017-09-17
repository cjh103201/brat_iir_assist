package com.iirtech.brats.model.service;

import java.util.ArrayList;

public interface MentionTypeCheckService {
	
	ArrayList<String> getFolderList_IIR(String next);
	
	ArrayList<String> getFileList(String folderName, String nextPath);
	
	ArrayList<ArrayList<String>> missingMentionTypeCheck(String folderName, String nextPath);

	ArrayList<ArrayList<String>> addedMentionTypeCheck(String folderName, String nextPath);

}
