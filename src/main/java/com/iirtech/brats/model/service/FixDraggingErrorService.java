package com.iirtech.brats.model.service;

import java.util.ArrayList;
import java.util.Map;

public interface FixDraggingErrorService {

	Map<String, Object> checkFixTypeOfErrors(String folderName, String nextPath);

	String mkFixedAnnFiles(String nextPath, String folderName, Map<String, ArrayList<String>> writeDataListPerDir);

}
