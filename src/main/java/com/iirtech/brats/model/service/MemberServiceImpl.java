package com.iirtech.brats.model.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.iirtech.brats.common.FileUtil;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Override
	public String getLoginInformation(String id, String password) {
		
		ArrayList<String> contentList = FileUtil.getFileReadData("http://106.255.230.162:61114/brat/","login.txt");
		
		ArrayList<String[]> content = new ArrayList<>();
		String[] split;
		for(String line : contentList) {
			split = line.split("\t");
			content.add(split);
		}
		
		for(int i = 0; i<content.size(); i++) {
			if(content.get(i)[0].equals(id) && content.get(i)[1].equals(password)) {
				return content.get(i)[2];
			} 
		}
		
		return "false";
	}
	
}