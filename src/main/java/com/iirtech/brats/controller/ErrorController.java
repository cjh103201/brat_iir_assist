package com.iirtech.brats.controller;


import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iirtech.brats.model.service.ConstructureCheckService;
import com.iirtech.brats.model.service.FixDraggingErrorService;
import com.iirtech.brats.model.service.MentionTypeCheckService;
import com.iirtech.brats.model.service.FixDraggingErrorServiceImpl.DraggingErrorTypes;


@Controller
@RequestMapping(value="/error")
public class ErrorController {

	@Autowired
	@Qualifier("mentionTypeCheckService")
	private MentionTypeCheckService mentionTypeCheckService;
	
	@Autowired
	@Qualifier("constructureCheckService")
	private ConstructureCheckService constructureCheckService ;
	
	@Autowired
	@Qualifier("fixDraggingErrorService")
	private FixDraggingErrorService fixDraggingErrorService;
	
	@RequestMapping(value="mentionType.action", method = RequestMethod.GET)
	public String findMentionTypeError(HttpSession session) {
		
		String userType = session.getAttribute("userType").toString();
		ArrayList<String> folderList = new ArrayList<>();

		folderList = mentionTypeCheckService.getFolderList_IIR("");

		session.setAttribute("folderList", folderList);
		return "error/mentionType";
	}
	
	@RequestMapping(value="folderList.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getNextFolderList(HttpServletResponse resp, String folderName) {
		
		ArrayList<String> folderList = mentionTypeCheckService.getFolderList_IIR(folderName);
		
		if(folderList.size() == 0) {
			folderList.add("====================");
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(folderList);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}
		
	@RequestMapping(value="missingTypeCheck.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getResultMentionTypeCheck(HttpServletResponse resp, String folderName, String nextPath) {
		
		if(nextPath.equals("") || nextPath.equals("====================")) {
			nextPath = "/3rd";
		} else {
			nextPath = "/" + nextPath;
		}
		
		if(folderName.contains("Final")) {
			nextPath = "";
		}
		
		ArrayList<ArrayList<String>> result = mentionTypeCheckService.missingMentionTypeCheck(folderName, nextPath);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json =gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}
	
	@RequestMapping(value="addedTypeCheck.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getResultMentionTypeCheck2(HttpServletResponse resp, String folderName, String nextPath) {
		
		if(nextPath.equals("") || nextPath.equals("====================")) {
			nextPath = "/3rd";
		} else {
			nextPath = "/" + nextPath;
		}
		
		if(folderName.contains("Final")) {
			nextPath = "";
		}
		
		ArrayList<ArrayList<String>> result = mentionTypeCheckService.addedMentionTypeCheck(folderName, nextPath);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json =gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}

	@RequestMapping(value="constructure.action", method = RequestMethod.GET)
	public String constructureCheck(HttpSession session) {
		
		String userType = session.getAttribute("userType").toString();
		ArrayList<String> folderList = new ArrayList<>();

		folderList = mentionTypeCheckService.getFolderList_IIR("");

		session.setAttribute("folderList", folderList);
		
		return "error/constructure";
	}
	
	@RequestMapping(value="lackConstructureCheck.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getResultConstructCheck(HttpServletResponse resp, String folderName, String nextPath) {
		
		if(nextPath.equals("") || nextPath.equals("====================")) {
			nextPath = "/3rd";
		} else {
			nextPath = "/" + nextPath;
		}
		
		if(folderName.contains("Final")) {
			nextPath = "";
		}
		System.out.println(nextPath);
		
		ArrayList<ArrayList<String>> result = constructureCheckService.lackConstructrueCheck(folderName, nextPath);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json =gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}

	@RequestMapping(value="mismatchConstructureCheck.action", method = RequestMethod.GET, produces = "applications/json;charset=utf-8")
	@ResponseBody
	public String getResultConstructCheck2(HttpServletResponse resp, String folderName, String nextPath) {
		
		if(nextPath.equals("") || nextPath.equals("====================")) {
			nextPath = "/3rd";
		} else {
			nextPath = "/" + nextPath;
		}
		
		if(folderName.contains("Final")) {
			nextPath = "";
		}
		System.out.println(nextPath);
		
		ArrayList<ArrayList<String>> result = constructureCheckService.mismatchConstructureCheck(folderName, nextPath);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json =gson.toJson(result);
		
		resp.setContentType("application/json;charset=utf-8");
		
		return json;
	}
	
	
	/**
	 * 
	 * @Method   : goFixDraggingError
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain : 드래깅 오류 수정 페이지로 이동하는 컨트롤러
	 * @param session(userType)
	 * @return page redirect
	 */
	@RequestMapping(value="goFixDraggingError.action", method = RequestMethod.GET)
	public String goFixDraggingError(HttpSession session) {
		
		ArrayList<String> folderList = new ArrayList<>();

		folderList = mentionTypeCheckService.getFolderList_IIR("");

		session.setAttribute("folderList", folderList);
		
		return "error/fixDraggingError";
	}

	/**
	 * 
	 * @Method   : checkFixTypeOfErrors
	 * @작성일     : 2017. 9. 19. 
	 * @작성자     : choikino
	 * @explain : 드래깅오류수정 메인페이지에서 선택한 파일경로의 파일들을 대상으로 error type에 따라 서로 다른 로직을 태워 수정작업 진행
	 * @param param String folderName, String nextPath 
	 * @return json
	 */
	@RequestMapping(value="checkFixTypeOfErrors.action")
	@ResponseBody
	public String checkFixTypeOfErrors(HttpServletResponse resp, @RequestParam Map<String,Object> param) {
		
		String nextPath = param.get("nextPath").toString();
		String folderName = param.get("folderName").toString();
		
		if(nextPath.equals("") || nextPath.equals("====================")) {
			nextPath = "/3rd";
		} else {
			nextPath = "/" + nextPath;
		}
		
		if(folderName.contains("Final")) {
			nextPath = "";
		}
		System.out.println(nextPath);
		
		//resultMap (fixedInfoListPerFile, writeDataListPerDir)
		Map<String, Object> result = fixDraggingErrorService.checkFixTypeOfErrors(folderName, nextPath);
		ArrayList<ArrayList<String>> fixedInfoListPerFile = (ArrayList<ArrayList<String>>) result.get("fixedInfoListPerFile");
		Map<String, ArrayList<String>> writeDataListPerDir = (Map<String, ArrayList<String>>) result.get("writeDataListPerDir");
		
		//화면에 뿌릴 형태로 가공 fixedInfoListPerFile 
		//[filename, orgnlWord, type, orgnlStartOffset, orgnlEndOffset, fixedWord, fixedStartOffset, fixedEndOffset, errorType]
		//위 데이터들 중에 필요한 것은 errorType 이 DE00 이 아닌 것들 중에서 filename(0), orgnlWord(1), fixedWord(5), errorType(8) 이렇게~
		ArrayList<ArrayList<String>> viewResultList = new ArrayList<>();
		for (int i = 0; i < fixedInfoListPerFile.size(); i++) {
			ArrayList<String> tempList = new ArrayList<>();
			String errorType = fixedInfoListPerFile.get(i).get(8);
			DraggingErrorTypes enumDET = DraggingErrorTypes.valueOf(errorType);
			if(!enumDET.getName().equals("normal")) {
				tempList.add(fixedInfoListPerFile.get(i).get(0).replace(".ann",""));
				tempList.add(fixedInfoListPerFile.get(i).get(1));
				tempList.add(fixedInfoListPerFile.get(i).get(5));
				tempList.add(enumDET.getName());
				viewResultList.add(tempList);
			}
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json =gson.toJson(viewResultList);
		resp.setContentType("application/json;charset=utf-8");
		
		//신규 ann 파일 생성 writeDataListPerDir
		fixDraggingErrorService.mkFixedAnnFiles(nextPath, folderName, writeDataListPerDir);
		
		return json;
	}
	
	
}