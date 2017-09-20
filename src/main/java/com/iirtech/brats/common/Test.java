package com.iirtech.brats.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iirtech.brats.model.service.FixDraggingErrorServiceImpl.DraggingErrorTypes;

public class Test {

	/**
	 * 
	 * @Method   : main
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain : 자바 로직 테스트 위한 클래스 생성 
	 * @param :
	 * @return :
	 */
	public static void main(String[] args) {
		
		//enum test
//		String errorType = "DE01";
//		DraggingErrorTypes enumDet = DraggingErrorTypes.valueOf(errorType);
//		System.out.println(enumDet.getName());
//		System.out.println(enumDet);
		
		//regex test
		//DE01 = 양끝에 특수문자 포함,  regex = (^\W*)|(\W*$) 양끝 특수문자만 찾음
		//DE02 = char.char.char.의 형태를 띄고있음, DE01 안에서 양끝 특수문자 제거하고 다시 DE02 찾음  regex = (\w.){2,} char.char. *2 부터만 찾음
		//DE03 = 양끝 +-1 범위에 일반문자가 존재 DE01 이 아닌 것들 중에서 인덱스 한개씩 확장하면서 다음 인덱스가 특수문자인 시점에서 정지
		String orginalWord = "West Coast of the U.S...";
		System.out.println(orginalWord);
		//String regex1 = "(^+\\W)|(\\W+$)";
		String regex1 = "(\\w\\.){2,}";
		Pattern pattern = Pattern.compile(regex1);
		Matcher matcher = pattern.matcher(orginalWord);
		int startOffset = 10;
		int endOffset = 10 + orginalWord.length();
		System.out.println(startOffset + ":" + endOffset);
		System.out.println("=============================="+matcher.find()+"==============================");
		//String fixedWord = matcher.replaceAll("");
		System.out.println(matcher.start() + ":" + matcher.end());
		String fixedWord = orginalWord.substring(0,matcher.end());
		System.out.println(fixedWord);
		int fixedStartOffset = startOffset + orginalWord.indexOf(fixedWord);
		int fixedEndOffset = fixedStartOffset + fixedWord.length();
		System.out.println(fixedStartOffset + ":" + fixedEndOffset);
		
//		String fileText = "123-abcdef ";
//		String word = "12";
//		int startIdx = 0;
//		int endIdx = 1;
//		String nextWord = word;
//		String startRegex = "(^+\\W)";
//		int fixedStartIdx = 0;
//		for(int i = startIdx; i >= 0; i--) {
//			nextWord = fileText.substring(i, endIdx);
//			Pattern startPattern = Pattern.compile(startRegex);
//			Matcher startMatcher = startPattern.matcher(nextWord);
//			if(startMatcher.find()) {
//				fixedStartIdx = i+1;
//				break;
//			}else {
//				startIdx = 0;
//			}
//		}
//		
//		String endRegex = "(\\W+$)";
//		int fixedEndIdx = 0;
//		for(int i = endIdx; i <= fileText.length(); i++) {
//			nextWord = fileText.substring(startIdx, i);
//			Pattern endPattern = Pattern.compile(endRegex);
//			Matcher endMatcher = endPattern.matcher(nextWord);
//			if(endMatcher.find()) {
//				fixedEndIdx = i-1;
//				break;
//			}else {
//				fixedEndIdx = fileText.length();
//			}
//		}
//		
//		String fixedWord = fileText.substring(fixedStartIdx, fixedEndIdx);
//		System.out.println(fixedWord + ", " + fixedStartIdx + ", " + fixedEndIdx);
	}

}
