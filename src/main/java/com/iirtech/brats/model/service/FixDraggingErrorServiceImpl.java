package com.iirtech.brats.model.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.iirtech.brats.common.FileUtil;
import com.iirtech.brats.common.InfoUtil;

/**
 * 
 * @Package   : com.iirtech.brats.model.service
 * @FileName  : FixDraggingErrorServiceImpl.java
 * @작성일       : 2017. 9. 19. 
 * @작성자       : choikino
 * @explain : 드래그 오류를 수정하는 실제 로직이 구현된 서비스 페이지 
 */
@Service("fixDraggingErrorService")
public class FixDraggingErrorServiceImpl implements FixDraggingErrorService{

	//error type enum 정의 DE00:정상 DE01:양끝특수문자, DE02:약어 , DE03:중간에짤림후보(정상일 수도 있음), DE99:수정이 잘못되어 원문정보와 안맞음
	public enum DraggingErrorTypes {
		
		DE00("normal")
		,DE01("specialLetter")
		,DE02("abbreviation")
		,DE03("incompleteWord")
		,DE99("fixError");
		
		final private String draggingErrorName;
		
		private DraggingErrorTypes(String name) {
			this.draggingErrorName = name;
		}
		public String getName() {
			return draggingErrorName;
		}
	}
	
	/**
	 * 
	 * @Method   : checkFixTypeOfErrors
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain : 드래깅오류수정 메인페이지에서 선택한 파일경로의 파일들을 대상으로 error type에 따라 서로 다른 로직을 태워 수정 결과만 미리 보여줌
	 * @param param folderName, nextPath
	 * @return resultMap (fixedInfoListPerFile, writeDataListPerDir)
	 */
	@Override
	public Map<String, Object> checkFixTypeOfErrors(String folderName, String nextPath) {
		//return param outside
		Map<String, Object> resultMap = new HashMap<>();
		try {
			//화면에 뿌릴 수정내역 정보 담는 리스트 객체
			ArrayList<ArrayList<String>> fixedInfoListPerFile = new ArrayList<>();
			//새로운 ann파일 생성할때 사용할 정보 담는 리스트 객체 
			Map<String,ArrayList<String>> writeDataListPerDir = new HashMap<>();
			
			String path = InfoUtil.path.folderPath_IIR + "/" + folderName + nextPath;
			String fileName = "";
			File[] fileList = FileUtil.getFileList(path);
			//txt 파일 읽어서 파일명:파일내용 형태로 map 객체에 저장 아래 ann파일에서 사용해야함
			Map<String, String> fileTextMapData = new HashMap<>();
			for(File file : fileList ) {
				//return param inside
				if(file.isFile()) {
					fileName = file.getName();
					if(fileName.contains(".txt") ) {
						FileUtil fu = new FileUtil();
						String fileText = fu.getFileTextAtOnce(path, fileName);
						fileTextMapData.put(fileName, fileText);
					}
				}
			}
			
			//ann 파일 읽으면서 실제로 작업
			for(File file : fileList ) {
				//return param inside
				if(file.isFile()) {
					ArrayList<String> writeDataListPerFile = new ArrayList<>();
					ArrayList<String> content = new ArrayList<>();
					fileName = file.getName();
					if(fileName.contains(".ann") ) {
						String fileText = fileTextMapData.get(fileName.replace(".ann", ".txt"));
						content = FileUtil.getFileData(path, fileName);
						/* example
							  A46	MentionType T72 NOM
							  T82	Infect 8202 8208	caught
							  E21	Infect:T82 Victim:T72 Disease:T24 Disease2:T31 Place:T83
						 */
						for(int i = 0; i < content.size(); i++) {
							//파일 쓰기용 라인 스트링 저장 변수
							String writeContentStr = "";
							String[] contentElmnt = content.get(i).split("\t");
							
							ArrayList<String> fixedInfoListPerLine = new ArrayList<>();
							fixedInfoListPerLine.add(fileName);//****** add fileName [0]
							Map<String, String> switchParamMap = new HashMap<>();

							if(contentElmnt[0].startsWith("T")) {
								writeContentStr += contentElmnt[0] + "\t";//파일 쓰기용 라인 스트링 저장 변수
								String[] valueElmnt = contentElmnt[1].split(" ");//Infect 8202 8208
								//orgnl data setting
								String orgnlWord = contentElmnt[2];
								fixedInfoListPerLine.add(orgnlWord);//****** add orgnlWord [1]
								String type = valueElmnt[0];
								fixedInfoListPerLine.add(type);//****** add type [2]
								writeContentStr += type + " ";//파일 쓰기용 라인 스트링 저장 변수
								String orgnlStartOffset = valueElmnt[1];
								String orgnlEndOffset = valueElmnt[2];
								fixedInfoListPerLine.add(orgnlStartOffset);//****** add orgnlStartOffset [3]
								fixedInfoListPerLine.add(orgnlEndOffset);//****** add orgnlEndOffset [4]
								
								switchParamMap.put("orgnlWord", orgnlWord);
								switchParamMap.put("orgnlStartOffset", orgnlStartOffset);
								switchParamMap.put("orgnlEndOffset", orgnlEndOffset);
								switchParamMap.put("fileText", fileText);
								
								//수정 로직은 오류 유형에 따라 달라진다. 오류타입은 정규표현식에 의해 결정되어짐 
								//DE01 = 양끝에 특수문자 포함,  regex = (^+\\W)|(\\W+$) 양끝 특수문자만 찾음
								String regexDE01 = "(^+\\W)|(\\W+$)";
								Pattern patternDE01 = Pattern.compile(regexDE01);
								Matcher matcherDE01 = patternDE01.matcher(orgnlWord);
								//DE02 = char.char.char.의 형태를 띄고있음, DE01 안에서 양끝 특수문자 제거하고 다시 DE02 찾음  regex = (\w.){2,} char.char. *2 부터만 찾음
								String regexDE02 = "(\\w\\.){2,}";
								Pattern patternDE02 = Pattern.compile(regexDE02);
								Matcher matcherDE02 = patternDE02.matcher(orgnlWord);
								String errorType = "";//오류타입 담을 변수
								if(matcherDE01.find()) {
									if(matcherDE02.find()) {
										//System.out.println(">>>>>>>>>>>>"+orgnlWord+":abbreviation");
										errorType = "DE02";
									}else {
										//System.out.println(">>>>>>>>>>>>"+orgnlWord+":specialLetter");
										errorType = "DE01";
									}
								}else {
									//DE00 일단 정상으로 보이지만 앞뒤 idx 체크를 통해 중간에 잘린 문자열인지를 다시 체크하여 DE03을 가려내야 함 
									//양끝 +-1 범위에 일반문자가 존재 DE01 이 아닌 것들 중에서 인덱스 한개씩 확장하면서 다음 인덱스가 특수문자인 시점에서 정지
									//System.out.println(">>>>>>>>>>>>"+orgnlWord+":incompleteWord");
									errorType = "DE00";
								}
								
								DraggingErrorTypes enumDET = DraggingErrorTypes.valueOf(errorType);
								//SPECIAL_LETTER:양끝특수문자, ABBREVIATION:약어, INCOMPLETE:중간에짤림
								switch (enumDET.getName()) {
								//fixedInfoListPerLine 객체에 아래 메소드들 실행 후 fixedWord,fixedOffsetStart,fixedOffsetEnd 값들이 순차적으로 add되어야 함 
								case "specialLetter":
									//양끝 특수문자만 찾을 수 있기 때문에 제거한다.
									switchParamMap = this.fixSpecialLetter(switchParamMap);
									break;
								case "abbreviation":
									switchParamMap = this.fixAbbreviationSpecialLetter(switchParamMap);
									break;
								case "normal":
									switchParamMap = this.fixIncompleteWord(switchParamMap);
									break;
								default:
									break;
								}//switch-case
								String fixedWord = switchParamMap.get("fixedWord");
								String fixedStartOffset = switchParamMap.get("fixedStartOffset");
								String fixedEndOffset = switchParamMap.get("fixedEndOffset");
								writeContentStr += fixedStartOffset + " ";//파일 쓰기용 라인 스트링 저장 변수
								writeContentStr += fixedEndOffset + "\t";//파일 쓰기용 라인 스트링 저장 변수
								writeContentStr += fixedWord;//파일 쓰기용 라인 스트링 저장 변수
								errorType = switchParamMap.get("errorType");
								fixedInfoListPerLine.add(fixedWord);//****** add fixedWord [5]
								fixedInfoListPerLine.add(fixedStartOffset);//****** add fixedStartOffset [6]
								fixedInfoListPerLine.add(fixedEndOffset);//****** add fixedEndOffset [7]
								fixedInfoListPerLine.add(errorType);//****** add errorType [8] enum 참고 
								fixedInfoListPerFile.add(fixedInfoListPerLine);
								
								writeDataListPerFile.add(writeContentStr);
								
							}else {
								//T seq 아니더라도 신규 ann 파일을 쓰기 위해서는 데이터 저장 필요 
								/* example
								  A46	MentionType T72 NOM
								  T82	Infect 8202 8208	caught
								  E21	Infect:T82 Victim:T72 Disease:T24 Disease2:T31 Place:T83
								 */
								writeDataListPerFile.add(content.get(i));
							}
						}//content loop
						writeDataListPerDir.put(fileName, writeDataListPerFile);
					}//ann
				}//is file
			}//file loop
			resultMap.put("fixedInfoListPerFile", fixedInfoListPerFile);
			resultMap.put("writeDataListPerDir", writeDataListPerDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 
	 * @param fileText 
	 * @Method   : fixIncompleteWord
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain :중간에 잘린 문자열을 수정하여 새로운 오프셋과 단어를 fixedInfoListPerLine 에 담아 리턴함
	 * @param :switchParamMap
	 * @return :switchParamMap
	 */
	private Map<String, String> fixIncompleteWord(Map<String, String> switchParamMap) {
		String orgnlWord = switchParamMap.get("orgnlWord");
		String startOffsetStr = switchParamMap.get("orgnlStartOffset");
		String endOffsetStr = switchParamMap.get("orgnlEndOffset");
		String fileText = switchParamMap.get("fileText");
		int startOffset = Integer.parseInt(startOffsetStr);
		int endOffset = Integer.parseInt(endOffsetStr);
		//System.out.println("old>>>"+orgnlWord);
		//System.out.println("old>>>"+startOffset + ":" + endOffset);
		
		//태깅된 단어가 문서의 시작 혹은 끝 단어일때 주의
		String nextWord = orgnlWord;
		String startRegex = "(^+\\W)";
		int fixedStartIdx = 0;
		for(int i = startOffset; i >= 0; i--) {
			nextWord = fileText.substring(i, endOffset);
			Pattern startPattern = Pattern.compile(startRegex);
			Matcher startMatcher = startPattern.matcher(nextWord);
			if(startMatcher.find()) {
				fixedStartIdx = i+1;
				break;
			}else {
				fixedStartIdx = 0;//문장의 시작일때
			}
		}
		String endRegex = "(\\W+$)";
		int fixedEndIdx = 0;
		for(int i = endOffset; i <= fileText.length(); i++) {
			nextWord = fileText.substring(startOffset, i);
			Pattern endPattern = Pattern.compile(endRegex);
			Matcher endMatcher = endPattern.matcher(nextWord);
			if(endMatcher.find()) {
				fixedEndIdx = i-1;
				break;
			}else {
				fixedEndIdx = fileText.length();//문장의 끝일때 
			}
		}
		
		String fixedWord = fileText.substring(fixedStartIdx, fixedEndIdx);
		String fixedStartOffsetStr = String.valueOf(fixedStartIdx);
		String fixedEndOffsetStr = String.valueOf(fixedEndIdx);
		//System.out.println("new>>>"+fixedWord);
		//System.out.println("new>>>"+fixedStartIdx + ":" + fixedEndIdx);
		
		String errorType = "DE00";
		if((fileText.substring(fixedStartIdx, fixedEndIdx).equals(fixedWord))
				&&(!orgnlWord.equals(fixedWord))) {
			//System.out.println(">>>>>>>>>>>>수정된 어휘.>>>>>>>>>>>>");
			errorType = "DE03";
		}else if(!fileText.substring(fixedStartIdx, fixedEndIdx).equals(fixedWord)){
			errorType = "DE99";
		}
		
		switchParamMap.put("fixedWord",fixedWord);//****** add fixedWord [5]
		switchParamMap.put("fixedStartOffset", fixedStartOffsetStr);//****** add fixedStartOffsetStr [6]
		switchParamMap.put("fixedEndOffset",fixedEndOffsetStr);//****** add fixedEndOffsetStr [7]
		switchParamMap.put("errorType",errorType);//****** add errorType [8]

		return switchParamMap;
	}

	/**
	 * 
	 * @param fileText 
	 * @Method   : fixAbbreviationSpecialLetter
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain :약어형태의 문자열 중 특수문자가 양끝에 포함된 케이스를 수정하여 새로운 오프셋과 단어를 fixedInfoListPerLine 에 담아 리턴함
	 * @param :switchParamMap
	 * @return :switchParamMap
	 */
	private Map<String, String> fixAbbreviationSpecialLetter(Map<String, String> switchParamMap) {
		String orgnlWord = switchParamMap.get("orgnlWord");
		String startOffsetStr = switchParamMap.get("orgnlStartOffset");
		String endOffsetStr = switchParamMap.get("orgnlEndOffset");
		String fileText = switchParamMap.get("fileText");
		int startOffset = Integer.parseInt(startOffsetStr);
		int endOffset = Integer.parseInt(endOffsetStr);
		//System.out.println("old>>>"+orgnlWord);
		//System.out.println("old>>>"+startOffset + ":" + endOffset);
		
		String regexDE02 = "(\\w\\.){2,}";
		Pattern patternDE02 = Pattern.compile(regexDE02);
		Matcher matcherDE02 = patternDE02.matcher(orgnlWord);
		System.out.println("isTarget:" +matcherDE02.find());
		//System.out.println(matcherDE02.start() + "," + matcherDE02.end());
		String fixedWord = orgnlWord.substring(matcherDE02.start(),matcherDE02.end());
		int fixedStartOffset = startOffset + orgnlWord.indexOf(fixedWord);
		int fixedEndOffset = fixedStartOffset + fixedWord.length();
		String fixedStartOffsetStr = String.valueOf(fixedStartOffset);
		String fixedEndOffsetStr = String.valueOf(fixedEndOffset);
		//System.out.println("new>>>"+fixedWord);
		//System.out.println("new>>>"+fixedStartOffset + ":" + fixedEndOffset);
		
		String errorType = "DE00";
		if(fileText.substring(fixedStartOffset, fixedEndOffset).equals(fixedWord)) {
			//System.out.println(">>>>>>>>>>>>원문정보와 일치한다.>>>>>>>>>>>>");
			errorType = "DE02";
		}else {
			errorType = "DE99";
		}
		
		switchParamMap.put("fixedWord",fixedWord);//****** add fixedWord [5]
		switchParamMap.put("fixedStartOffset", fixedStartOffsetStr);//****** add fixedStartOffsetStr [6]
		switchParamMap.put("fixedEndOffset",fixedEndOffsetStr);//****** add fixedEndOffsetStr [7]
		switchParamMap.put("errorType",errorType);//****** add errorType [8]

		return switchParamMap;
	}

	/**
	 * 
	 * @param fileText 
	 * @Method   : fixSpecialLetter
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain :일반 문자열 중 특수문자가 양끝에 포함된 케이스를 수정하여 새로운 오프셋과 단어를 fixedInfoListPerLine 에 담아 리턴함
	 * @param :switchParamMap
	 * @return :switchParamMap
	 */
	private Map<String, String> fixSpecialLetter(Map<String, String> switchParamMap) {
		
		String orgnlWord = switchParamMap.get("orgnlWord");
		String startOffsetStr = switchParamMap.get("orgnlStartOffset");
		String endOffsetStr = switchParamMap.get("orgnlEndOffset");
		String fileText = switchParamMap.get("fileText");
		int startOffset = Integer.parseInt(startOffsetStr);
		int endOffset = Integer.parseInt(endOffsetStr);
		//System.out.println("old>>>"+orgnlWord);
		//System.out.println("old>>>"+startOffset + ":" + endOffset);
		
		String regexDE01 = "(^+\\W)|(\\W+$)";
		Pattern patternDE01 = Pattern.compile(regexDE01);
		Matcher matcherDE01 = patternDE01.matcher(orgnlWord);
		//System.out.println("isTarget:" +matcherDE01.find());

		String fixedWord = matcherDE01.replaceAll("");
		int fixedStartOffset = startOffset + orgnlWord.indexOf(fixedWord);
		int fixedEndOffset = fixedStartOffset + fixedWord.length();
		String fixedStartOffsetStr = String.valueOf(fixedStartOffset);
		String fixedEndOffsetStr = String.valueOf(fixedEndOffset);
		//System.out.println("new>>>"+fixedWord);
		//System.out.println("new>>>"+fixedStartOffset + ":" + fixedEndOffset);
		
		String errorType = "DE00";
		if(fileText.substring(fixedStartOffset, fixedEndOffset).equals(fixedWord)) {
			//System.out.println(">>>>>>>>>>>>원문정보와 일치한다.>>>>>>>>>>>>");
			errorType = "DE01";
		}else {
			errorType = "DE99";
		}
		
		switchParamMap.put("fixedWord",fixedWord);//****** add fixedWord [5]
		switchParamMap.put("fixedStartOffset", fixedStartOffsetStr);//****** add fixedStartOffsetStr [6]
		switchParamMap.put("fixedEndOffset",fixedEndOffsetStr);//****** add fixedEndOffsetStr [7]
		switchParamMap.put("errorType",errorType);//****** add errorType [8]

		return switchParamMap;
	}

	/**
	 * 
	 * @Method   : mkFixedAnnFiles
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain :교정된 정보를 가지고 실제로 신규 ann 파일을 생성하는 단계
	 * @param :String nextPath, String folderName, ArrayList<ArrayList<String>> workedData
	 * @return :String result
	 */
	@Override
	public String mkFixedAnnFiles(String nextPath, String folderName, Map<String, ArrayList<String>> writeDataListPerDir) {
		String result = "";
		String path = InfoUtil.path.folderPath_IIR + "/" + folderName + nextPath + "/fixed";
		try {
			FileUtil fu = new FileUtil();
			for ( Map.Entry<String, ArrayList<String>> entry : writeDataListPerDir.entrySet()) {
			    String key = entry.getKey();
			    ArrayList<String> value = entry.getValue();
			    String writeStr = "";
			    for (int j = 0; j < value.size(); j++) {
					writeStr += value.get(j) + "\n";
				}
				//파일 생성
				fu.writeAnnFile(path, key, writeStr);
			}
			
			
			for (int i = 0; i < writeDataListPerDir.size(); i++) {
				ArrayList<String> fileData = writeDataListPerDir.get(i);
				
				
			}
			result = "succsess";
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		return result;
	}

}
