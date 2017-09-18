package com.iirtech.brats.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.iirtech.brats.common.FileUtil;
import com.iirtech.brats.common.InfoUtil;

@Service("constructureCheckService")
public class ConstructureCheckServiceImpl implements ConstructureCheckService {

	@Override
	public ArrayList<ArrayList<String>> lackConstructrueCheck(String folderName, String nextPath) {
		
		String path = InfoUtil.path.folderPath_IIR + "/" +folderName + nextPath;
		String fileName = "";
		
		File[] fileList = FileUtil.getFileList(path);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		for(File file : fileList) {
			if(file.isFile()) {
				ArrayList<String[]> content = new ArrayList<>();
				
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData2(FileUtil.getFileData(path, fileName));
					
					for(int i = 0; i < content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("E")) {
							ArrayList<String> lackOfEntity = new ArrayList<>();
							if(content.get(i).length < 7) {
								lackOfEntity.add(fileName.substring(0, fileName.length()-4));
								String[] contentLine = FileUtil.getContentLineByEntityNo(content.get(i)[2], content);
								lackOfEntity.add(FileUtil.getLine(path, fileName, contentLine));
								lackOfEntity.add(content.get(i)[1]);
								String word = "";
								for(int j = 4; j<contentLine.length; j++   ) {
									word = word + contentLine[j] +" ";
								}
								lackOfEntity.add(word);
								result.add(lackOfEntity);
							} else {
								if(content.get(i)[1].equals("Announce")) {
									HashSet<String> co = new HashSet<>();
									
									for(int j = 3; j<content.get(i).length; j++) {
										co.add(content.get(i)[j]);
									}
									
									if(!co.contains("Event") || !co.contains("Speaker")) {
										lackOfEntity.add(fileName.substring(0, fileName.length()-4));
										String[] contentLine = FileUtil.getContentLineByEntityNo(content.get(i)[2], content);
										lackOfEntity.add(FileUtil.getLine(path, fileName, contentLine));
										lackOfEntity.add(content.get(i)[1]);
										String word = "";
										for(int j = 4; j<contentLine.length; j++   ) {
											word = word + contentLine[j] +" ";
										}
										lackOfEntity.add(word);
										result.add(lackOfEntity);
									}
								} else {
									HashSet<String> co = new HashSet<>();
									
									for(int j = 3; j<content.get(i).length; j+=2) {
										co.add(content.get(i)[j]);
									}
									
									if( !co.contains("Disease")) {
										lackOfEntity.add(fileName.substring(0, fileName.length()-4));
										String[] contentLine = FileUtil.getContentLineByEntityNo(content.get(i)[2], content);
										lackOfEntity.add(FileUtil.getLine(path, fileName, contentLine));
										lackOfEntity.add(content.get(i)[1]);
										String word = "";
										for(int j = 4; j<contentLine.length; j++   ) {
											word = word + contentLine[j] +" ";
										}
										lackOfEntity.add(word + "(Dis 없음)" );
										result.add(lackOfEntity);
									}
								}
							}
						}
					}
										
				}
			}
		}
		System.out.println(result);
		return result;
	}

	@Override
	public ArrayList<ArrayList<String>> mismatchConstructureCheck(String folderName, String nextPath) {
		
		String path = InfoUtil.path.folderPath_IIR + "/" +folderName + nextPath;
		String fileName = "";
		
		File[] fileList = FileUtil.getFileList(path);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		for(File file : fileList) {
			if(file.isFile()) {
				ArrayList<String[]> content = new ArrayList<>();
				
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData2(FileUtil.getFileData(path, fileName));
					
					for(int i = 0; i < content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("E") && content.get(i).length > 6) {
							for(int j = 1; j<content.get(i).length; j+=2) {
								ArrayList<String> mismatchEntity = new ArrayList<>();
								String word = content.get(i)[j];
								String[] line = FileUtil.getContentLineByEntityNo(content.get(i)[(j+1)], content);
								if(word.equals("Victim")) {
									if( !line[1].equals("PER") && !line[1].equals("NPER")) {
										mismatchEntity.add(fileName.substring(0, fileName.length()-4));
										mismatchEntity.add(FileUtil.getLine(path, fileName, line));
										mismatchEntity.add(line[1]);
										mismatchEntity.add(word);
										String word2 = "";
										for(int k = 4; k<line.length; k++) {
											word2 = word2 + line[k] + " ";
										}
										mismatchEntity.add(word2);
										result.add(mismatchEntity);
									}
								} else if(word.contains("Time") || word.contains("Time-to") || word.contains("Time-from")) {
									if( !line[1].equals("TIME") ) {
										mismatchEntity.add(fileName.substring(0, fileName.length()-4));
										mismatchEntity.add(FileUtil.getLine(path, fileName, line));
										mismatchEntity.add(line[1]);
										mismatchEntity.add(word);
										String word2 = "";
										for(int k = 4; k<line.length; k++) {
											word2 = word2 + line[k] + " ";
										}
										mismatchEntity.add(word2);
										result.add(mismatchEntity);
									}
								} else if (word.contains("Disease")) {
									if( !line[1].equals("DIS") && !line[1].equals("PGEN")) {
										mismatchEntity.add(fileName.substring(0, fileName.length()-4));
										mismatchEntity.add(FileUtil.getLine(path, fileName, line));
										mismatchEntity.add(line[1]);
										mismatchEntity.add(word);
										String word2 = "";
										for(int k = 4; k<line.length; k++) {
											word2 = word2 + line[k] + " ";
										}
										mismatchEntity.add(word2);
										result.add(mismatchEntity);
									}
								} else if(word.contains("Place")) {
									if( !line[1].equals("GPE") && !line[1].equals("LOC") && !line[1].equals("FAC")) {
										mismatchEntity.add(fileName.substring(0, fileName.length()-4));
										mismatchEntity.add(FileUtil.getLine(path, fileName, line));
										mismatchEntity.add(line[1]);
										mismatchEntity.add(word);
										String word2 = "";
										for(int k = 4; k<line.length; k++) {
											word2 = word2 + line[k] + " ";
										}
										mismatchEntity.add(word2);
										result.add(mismatchEntity);
									}
								} else if(word.contains("Suffer")) {
									if( !line[1].equals("SYMP")) {
										mismatchEntity.add(fileName.substring(0, fileName.length()-4));
										mismatchEntity.add(FileUtil.getLine(path, fileName, line));
										mismatchEntity.add(line[1]);
										mismatchEntity.add(word);
										String word2 = "";
										for(int k = 4; k<line.length; k++) {
											word2 = word2 + line[k] + " ";
										}
										mismatchEntity.add(word2);
										result.add(mismatchEntity);
									}
								} else if(word.contains("Speaker")) {
									if( !line[1].equals("PER") && !line[1].equals("ORG")) {
										mismatchEntity.add(fileName.substring(0, fileName.length()-4));
										mismatchEntity.add(FileUtil.getLine(path, fileName, line));
										mismatchEntity.add(line[1]);
										mismatchEntity.add(word);
										String word2 = "";
										for(int k = 4; k<line.length; k++) {
											word2 = word2 + line[k] + " ";
										}
										mismatchEntity.add(word2);
										result.add(mismatchEntity);
									}
								}
							}
						}
					}
				}
			}
		}
		
		return result;
	}

}
