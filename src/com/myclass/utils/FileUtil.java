package com.myclass.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.myclass.common.FileManagerConstants;

public class FileUtil {

	public static Logger LOG = Logger.getLogger(FileUtil.class);

	private static File dir;
	private static Map<String, List<File>> filesMap;
	private static DirCheckingActions obj;
	public static Long size = 0L;

	/**
	 * 
	 * @param pathSource
	 * @return
	 */

	public static Map<String, List<File>> sortDirFiles(String pathSource) {
		LOG.info("Entering sortDirFiles method for " + pathSource);
		filesMap = new HashMap<String, List<File>>();

		if (isCheckingPassed(pathSource)) {
			for (int i = 0, j = 0; i < FileManagerConstants.ABBREV.length
					&& j < FileManagerConstants.EXT.length; i++, j++) {
				DirCheckingActions.list = new ArrayList<File>();
				filesMap.put(FileManagerConstants.ABBREV[i], obj.listFilter(dir, FileManagerConstants.EXT[j]));

			}
		}
		return filesMap;

	}

	/**
	 * 
	 * @param pathSource
	 * @param ext
	 * @return
	 */
	public static List<File> getList(String pathSource, String ext) {
		LOG.info("Entering getList metod for " + pathSource + " & " + ext);
		List<File> list = new ArrayList<File>();
		if (isCheckingPassed(pathSource)) {
			list = obj.listFilter(dir, ext);
		}
		return list;

	}

	/**
	 * 
	 * @param pathSource
	 * @return
	 */
	public static boolean isCheckingPassed(String pathSource) {
		LOG.info("Entering isOK method for " + pathSource);
		if (pathSource != null && !pathSource.equals("")) {
			LOG.info("Source isn't empty or equally null");
			dir = new File(pathSource);
			obj = new DirCheckingActions(dir); // for Dir Checking

			boolean isDir = obj.isDirExist();
			File[] array = dir.listFiles();
			boolean isListNotEmpty = obj.isListComplete(array, isDir); // if
																		// exist
																		// a
																		// file
																		// in
																		// the
																		// folder
			return (isDir && isListNotEmpty);

		} else {
			LOG.error("ERROR: Adress line is empty or equally null");
			return false;
		}

	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static Long listFilter(File file) {
		LOG.info("Entering  listFilter method for ");
		File[] array = file.listFiles();
		if (array.length != 0) {
			for (File fileArr : array) {
				if (fileArr.isFile()) {
					size += fileArr.length();
				} else if (fileArr.isDirectory()) {
					listFilter(fileArr);
				}
			}
		} else {
			size += 0;
		}

		return size;

	}

}
