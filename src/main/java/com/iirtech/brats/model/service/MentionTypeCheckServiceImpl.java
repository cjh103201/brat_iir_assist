package com.iirtech.brats.model.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.iirtech.brats.common.FileUtil;
import com.iirtech.brats.common.InfoUtil;

@Service("mentionTypeCheckService")
public class MentionTypeCheckServiceImpl implements MentionTypeCheckService {

	@Override
	public ArrayList<String> getFolderList_IIR(String next) {
		
		ArrayList<String> folderList = new ArrayList<>();
		folderList = FileUtil.getFolderList(InfoUtil.path.folderPath_IIR + "/" + next);
		return folderList;
	}
	
	@Override
	public ArrayList<String> getFileList(String folderName, String nextPath) {
		
		ArrayList<String> fileList = new ArrayList<>();
		fileList = FileUtil.getFileList2(InfoUtil.path.folderPath_IIR + "/" + folderName +"/" + nextPath);

		return fileList;
	}


	
	
}
