package com.iirtech.brats.model.service;

import java.io.File;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.iirtech.brats.common.FileUtil;
import com.iirtech.brats.common.InfoUtil;

@Service("concordanceService")
public class ConcordanceServiceImpl implements ConcordanceService {

	@Override
	public ArrayList<ArrayList<String>> textConcordance(String text) {
		
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		File[] fileList = FileUtil.getFileList(path);
		
		for(File file : fileList) {
			if(file.isFile()) {
				fileName = file.getName();
				if (fileName.contains("txt")) {
					ArrayList<String> lines = new ArrayList<>();
					ArrayList<String> cons = new ArrayList<>();
					
					lines = FileUtil.getFileData(path, fileName);
					for(int i = 0; i<lines.size(); i++) {
						if(lines.get(i).contains(text)) {
							cons.add(fileName.substring(0, fileName.length()-4));
							cons.add(String.valueOf(i+1));
							cons.add(lines.get(i));
							result.add(cons);
						}
					}
				}
			}
		}	
		return result;
	}

	@Override
	public ArrayList<ArrayList<String>> entityConcordance(String type, String text) {

		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		
		File[] fileList = FileUtil.getFileList(path);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		for(File file : fileList) {
			if(file.isFile()) {
				ArrayList<String[]> content = new ArrayList<>();
				ArrayList<String> cons = new ArrayList<>();
				
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData2(FileUtil.getFileData(path, fileName));
					
					for(int i =0; i<content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("T") && content.get(i)[1].equals(type)) {
							String word = "";
							for(int j = 4; j<content.get(i).length; j++) {
								word = word + content.get(i)[j];
								if(j != content.get(i).length - 1) {
									word = word + " ";
								}
							}
							if (word.toLowerCase().equals(text.toLowerCase())) {
								cons.add(fileName.substring(0, fileName.length() - 4));
								cons.add(FileUtil.getLine(path, fileName, content.get(i)));
								cons.add(FileUtil.getContentLine(path, fileName, content.get(i)));
								result.add(cons);
							}
						}
					}

				}
			}
		}
		return result;
	}

	@Override
	public ArrayList<ArrayList<String>> eventConcordance(String type, String text) {
		
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		
		File[] fileList = FileUtil.getFileList(path);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		for(File file : fileList) {
			if(file.isFile()) {
				ArrayList<String[]> content = new ArrayList<>();
				ArrayList<String> cons = new ArrayList<>();
				
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData2(FileUtil.getFileData(path, fileName));
					
					for(int i =0; i<content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("T") && content.get(i)[1].equals(type)) {
							String word = "";
							for(int j = 4; j<content.get(i).length; j++) {
								word = word + content.get(i)[j];
								if(j != content.get(i).length - 1) {
									word = word + " ";
								}
							}
							if (word.toLowerCase().equals(text.toLowerCase())) {
								cons.add(fileName.substring(0, fileName.length() - 4));
								cons.add(FileUtil.getLine(path, fileName, content.get(i)));
								cons.add(FileUtil.getContentLine(path, fileName, content.get(i)));
								result.add(cons);
							}
						}
					}

				}
			}
		}
		return result;
	}
	
}