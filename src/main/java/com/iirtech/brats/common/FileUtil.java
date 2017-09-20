package com.iirtech.brats.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class FileUtil {
	// url file server에서 데이터 읽
	
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
	
	//local 파일 리스트 읽기
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

	//split하기 전 데이터 
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
	
	public static String getContentLine(String path, String fileName, String[] content ) {
		
		BufferedReader br;

		String contentLine = "";
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
				contentLine = line_content.get(i);
			}
		}
		return contentLine;
	}
	
		
	public static String[] getContentLineByEntityNo(String entityNo, ArrayList<String[]> content) {
		
		String[] result = new String[]{};
		for(int i = 0; i < content.size(); i++) {
			if(content.get(i)[0].equals(entityNo)) {
				result = content.get(i);
			}
		}
		
		return result;
	}
	
	/**
	 * 
	 * @Method   : getFileTextAtOnce
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain :line by line 아니고 파일 내용 한번에 읽기
	 * @param :String path, String fileName
	 * @return :String fileText
	 */
	public String getFileTextAtOnce(String path, String fileName) {
		String fileText = "";
		try {
			File file = new File(path, fileName);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			fileText = new String(data, "UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fileText;
	}

	/**
	 * 
	 * @Method   : writeAnnFile
	 * @작성일     : 2017. 9. 20. 
	 * @작성자     : choikino
	 * @explain : 파일명까지 포함된 최종경로와 쓸 데이터를 인자로 받아 새로운 ann 파일을 생성한다.
	 * @param :String fileFullPath, String writeStr
	 * @return :
	 */
	public void writeAnnFile(String path, String fileName, String writeStr) {
        BufferedWriter bw = null;
        try{
        		File dir = new File(path);
        		if(!dir.exists()) {
        			dir.mkdirs();
        		}
        		String fileFullPath = path + "/" + fileName;
            bw = new BufferedWriter(new FileWriter(fileFullPath));
            bw.write(writeStr);
            bw.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(bw != null) {
            		try {
            			bw.close(); 
            		} catch (Exception e) {
            			e.printStackTrace();
            		}
            }
        }
	}
	
	
}
