package com.iirtech.brats.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.stereotype.Service;

import com.iirtech.brats.common.FileUtil;
import com.iirtech.brats.common.InfoUtil;

@Service("countingService")
public class CountingServiceImpl implements CountingService {

	@Override
	public int getTotalDocNo() {
		
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		int result = 0;
		
		File[] fileList = FileUtil.getFileList(path);
		
		for(File file : fileList) {
			if(file.isFile()) {
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					result++;
				}
			}
		}
		return result;
	}

	@Override
	public int getNotDocNo() {
		
		ArrayList<String[]> content = new ArrayList<>();
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		int no = 0;
		int result = 0;
		
		File[] fileList = FileUtil.getFileList(path);
		
		for(File file : fileList) {
			if(file.isFile()) {
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData(FileUtil.getFileData(path, fileName));
					no = getEachDoc(content);
					result = result + no;
				}
			}
		}

		return result;	
	}

	@Override
	public int getEventNo() {
		
		ArrayList<String[]> content = new ArrayList<>();
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		int result = 0;
		
		File[] fileList = FileUtil.getFileList(path);
		
		for(File file : fileList) {
			if(file.isFile()) {
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData(FileUtil.getFileData(path, fileName));

					for(int i = 0; i<content.size(); i++)  {
						if(content.get(i)[0].substring(0, 1).equals("E")) {
							result++;
						}
					}
				}
			}
		}

		return result;	
	}

	@Override
	public int getDistinctEventNo() {

		ArrayList<String[]> content = new ArrayList<>();
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		int result = 0;
		int no = 0;
		
		File[] fileList = FileUtil.getFileList(path);
		
		for(File file : fileList) {
			if(file.isFile()) {
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData(FileUtil.getFileData(path, fileName));
					no = getEachEventNo(content);
					result = result + no;
					}
				}
			}
		return result;	
	}

	@Override
	public int getEntityNo() {
		
		ArrayList<String[]> content = new ArrayList<>();
		String path = InfoUtil.path.allFilePath;
		String fileName = "";
		int result = 0;
		
		File[] fileList = FileUtil.getFileList(path);
		
		for(File file : fileList) {
			if(file.isFile()) {
				fileName = file.getName();
				if(fileName.contains(".ann")) {
					content = FileUtil.getSplitData(FileUtil.getFileData(path, fileName));

					for(int i = 0; i<content.size(); i++)  {
						if(content.get(i)[0].substring(0, 1).equals("T")) {
							result++;
						}
					}
				}
			}
		}

		return result;	
	}
	
	public int getEachEventNo(ArrayList<String[]> content) {
		
		HashSet<String> eventSet = new HashSet<>(); 
		
		for(int i = 0; i<content.size(); i++) {
			if(content.get(i)[0].substring(0, 1).equals("E")) {
				eventSet.add(content.get(i)[1]);
			}
		}
		
		return eventSet.size();
	}
	
	public int getEachDoc(ArrayList<String[]> content) {
		
		for(int i = 0; i<content.size(); i++) {
			if(content.get(i)[0].substring(0, 1).equals("E")) {
				return 0;
			} 
		}
		return 1;
	}
}