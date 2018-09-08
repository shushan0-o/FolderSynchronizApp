/**
 * 
 */
package com.myclass.utils;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author SHUSHAND
 *
 */
public class DirCheckingActions {

	public static Logger LOG = Logger.getLogger(DirCheckingActions.class);
	private final File dir;
	public static List<File> list;

	// When creating an object, the dir is initialized
	public DirCheckingActions(File pathSource) {
		dir = pathSource;
		LOG.info("Entering  constructor for " + pathSource.getName());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDirExist() {
		LOG.info("Entering  isDirExist method.");
		if (dir.exists() && dir.isDirectory()) {
			return true;
		} else {
			LOG.error("ERROR: This address is incorrect or does not contain a folder");
			return false;
		}

	}

	/**
	 * 
	 * @param array
	 * @param isDir
	 * @return
	 */
	public boolean isListComplete(File[] array, boolean isDir) {
		LOG.info("Entering  isListComplete method.");
		if (isDir) {
			if (array.length > 0) {
				return true;
			} else {
				LOG.info("This folder does not contain any files");
			}
		}
		return false;
	}

	/**
	 * 
	 * @param file
	 * @param ext
	 * @return
	 */
	public List<File> listFilter(File file, String ext) {
		LOG.info("Entering  listFilter method for " + ext + " files");
		File[] array = file.listFiles();

		for (File fileArr : array) {
			if (fileArr.isFile() && fileArr.getName().endsWith(ext)) {
				list.add(fileArr);
			} else if (fileArr.isDirectory()) {
				listFilter(fileArr, ext);
			}
		}

		return list;

	}

}
