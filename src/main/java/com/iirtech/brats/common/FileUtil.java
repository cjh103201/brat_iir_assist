package com.iirtech.brats.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.DefaultMessageCodesResolver.Format;

public class FileUtil {

	public static ArrayList<String> getFileReadData(String path, String fileName) {
		
		ArrayList<String> contentLines = new ArrayList<>();
		
		BufferedReader br;
		URL url = null;
		
		String lines;
		
		try {
			url = new URL(path + fileName);
			URLConnection conn = url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			while((lines = br.readLine()) != null) {
				contentLines.add(lines);
			}
			
			br.close();
		} catch(Exception ex) {
 			ex.printStackTrace();
		}
		return contentLines;
	}
	
	public static ArrayList<String[]> getSplitData(ArrayList<String> contentLines) {
		
		ArrayList<String[]> content = new ArrayList<>();
		String[] split;
		for(String line : contentLines) {
			split = line.split(" |\t");
			content.add(split);
		}
		
		return content;
	}
	
	public static ArrayList<String[]> getSplitData2(ArrayList<String> contentLines) {
		
		ArrayList<String[]> content = new ArrayList<>();
		String[] split;
		for(String line : contentLines) {
			split = line.split(" |\t|:");
			content.add(split);
		}
		
		return content;
	}
	
	public static File[] getFileList(String path) {
		
		File dir = new File(path);
		File[] fileList = dir.listFiles();
		
		return fileList;
	}
	
	public static ArrayList<String> getFileList2(String path) {
		
		ArrayList<String> fileList = new ArrayList<>();
		
		File dir = new File(path);
		String[] list = dir.list();
		
		for(String l : list) {
			if(l.contains(".txt")) {
				fileList.add(l.substring(0, l.length()-4));
			}
		}
		return fileList;
	}

	public static ArrayList<String> getFileData(String path, String fileName) {
		
		BufferedReader br;
		File file = new File(path, fileName);
		
		ArrayList<String> contentLines = new ArrayList<>();
		String lines;
		
		try {
			br = new BufferedReader(new FileReader(file));
			
			while((lines = br.readLine()) != null) {
				contentLines.add(lines);
			}
			br.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return contentLines;
	}

	public static ArrayList<String> getFolderList(String path) {
		
		ArrayList<String> folderList = new ArrayList<>();
		
		File dir = new File(path);
		String[] list = dir.list();
		
		for(String l : list) {
			if(!l.contains(".")) {
				folderList.add(l);
			}
		}
		return folderList;
	}

	public static String getLine(String path, String fileName, String[] content ) {
		
		BufferedReader br;

		int line = 0;
		String name = fileName.substring(0, fileName.length()-3) + "txt";

		File file = new File(path, name);
		
		ArrayList<String> line_content = new ArrayList<>();
		String lines;
		
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new FileReader(file));
			while( (lines = br.readLine()) != null) {
				sb.append(lines + "\n");
				line_content.add(lines);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		String value = "";
		int startIdx = Integer.parseInt(content[2]);
		int endIdx = Integer.parseInt(content[3]);
		
		if(!sb.substring(startIdx-10, startIdx).contains("\n") && !sb.substring(startIdx-10, startIdx).contains(".")) {
			startIdx -= 10;
		} 
		if(!sb.substring(endIdx, endIdx+6).contains(".") && !sb.substring(endIdx, endIdx+6).contains("\n")) {
			endIdx += 6;
		}
		
		value = sb.substring(startIdx, endIdx);
		//System.out.println(value);

		for(int i = 0; i < line_content.size(); i++ ) {
			if(line_content.get(i).contains(value)) {
				line = i+1;
			}
		}
		return String.valueOf(line);
	}
}
