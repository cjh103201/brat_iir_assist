package com.iirtech.brats.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

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

	@Override
	public ArrayList<ArrayList<String>> missingMentionTypeCheck(String folderName, String nextPath) {
		
		String path = InfoUtil.path.folderPath_IIR + "/" + folderName + nextPath;
		String fileName = "";
		
		File[] fileList = FileUtil.getFileList(path);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		for(File file : fileList ) {
			if(file.isFile()) {
				ArrayList<String[]> content = new ArrayList<>();
				HashSet<String> entitySet = new HashSet<>();
				HashSet<String> empty = new HashSet<>();

				fileName = file.getName();
				if(fileName.contains(".ann") ) {
					content = FileUtil.getSplitData2(FileUtil.getFileData(path, fileName));
					
					for(int i = 0; i < content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("T")) {
							if(content.get(i)[1].equals("DIS") || content.get(i)[1].equals("PER") || content.get(i)[1].equals("PGEN") || content.get(i)[1].equals("ORG") 
			            			|| content.get(i)[1].equals("GPE") || content.get(i)[1].equals("LOC") || content.get(i)[1].equals("FAC")) {
								entitySet.add(content.get(i)[0]);
							}
						}
					}
					
					for(int i = 0; i<content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("A")) {
							for(String entity : entitySet) {
								if(content.get(i)[2].equals(entity)) {
									empty.add(entity);
								}
							}
						}
					}
					
					for(String emp : empty) {
						entitySet.remove(emp);
					}
					
					if(entitySet.size() != 0) {
						for(int i = 0; i<content.size(); i++) {
							for(String n : entitySet) {
								if(content.get(i)[0].equals(n)) {
									ArrayList<String> cont = new ArrayList<>();
									cont.add(fileName.substring(0, fileName.length()-4));
									//line 찾기
									cont.add(FileUtil.getLine(path, fileName, content.get(i)));
									cont.add(content.get(i)[1]);
									String word  = "";
									for(int j = 4;j<content.get(i).length; j++) {
										word = word + content.get(i)[j] + " ";
									}
									cont.add(word);
									result.add(cont);
								}
							}
						}
					}
				} 
			} // end of if file exist
		}//end of for
		
		return result;
	}

	public ArrayList<ArrayList<String>> addedMentionTypeCheck(String folderName, String nextPath) {
		
		String path = InfoUtil.path.folderPath_IIR + "/" + folderName + nextPath;
		String fileName = "";
		
		File[] fileList = FileUtil.getFileList(path);
		
		ArrayList<ArrayList<String>> result = new ArrayList<>();
		
		for(File file : fileList ) {
			if(file.isFile()) {
				ArrayList<String[]> content = new ArrayList<>();
				HashSet<String> entitySet = new HashSet<>();
				HashSet<String> added = new HashSet<>();

				fileName = file.getName();
				if(fileName.contains(".ann") ) {
					content = FileUtil.getSplitData2(FileUtil.getFileData(path, fileName));
					
					for(int i = 0; i < content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("T")) {
							if(content.get(i)[1].equals("NPER") || content.get(i)[1].equals("SYMP") || content.get(i)[1].equals("TIME")) {
								entitySet.add(content.get(i)[0]);
							}
						}
					}
					
					for(int i = 0; i<content.size(); i++) {
						if(content.get(i)[0].substring(0, 1).equals("A")) {
							for(String entity : entitySet) {
								if(content.get(i)[2].equals(entity)) {
									added.add(entity);
								}
							}
						}
					}
					
					if(added.size() != 0) {
						for(int i = 0; i<content.size(); i++) {
							for(String a : added) {
								if(content.get(i)[0].equals(a)) {
									ArrayList<String> cont = new ArrayList<>();
									cont.add(fileName.substring(0, fileName.length()-4));
									//line 찾기
									cont.add(FileUtil.getLine(path, fileName, content.get(i)));
									cont.add(content.get(i)[1]);
									String word  = "";
									for(int j = 4;j<content.get(i).length; j++) {
										word = word + content.get(i)[j] + " ";
									}
									cont.add(word);
									result.add(cont);
								}
							}
						}
					}
				} 
			} // end of if file exist
		}//end of for
		return result;
	}

}
