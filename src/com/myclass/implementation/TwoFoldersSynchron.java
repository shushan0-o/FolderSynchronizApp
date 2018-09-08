/**
 * 
 */
package com.myclass.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.myclass.common.FileManagerConstants;
import com.myclass.inheritance.FileManagmentAbstract;
import com.myclass.utils.FileUtil;

/**
 * @author SHUSHAND
 *
 */
public class TwoFoldersSynchron extends FileManagmentAbstract {

	public static Logger LOG = Logger.getLogger(TwoFoldersSynchron.class);
	private static boolean exist;

	/**
	 * 
	 */
	@Override
	public void fileManag(String pathSource, String pathDest, String option) throws IOException {
		LOG.info("Entering fileManag method for " + pathSource + "," + pathDest + "," + option);
		if (option != null && !option.equals("")) {
			if (option.equals("ALL")) {
				Map<String, List<File>> filesMapSource = FileUtil.sortDirFiles(pathSource);
				Map<String, List<File>> filesMapDest = FileUtil.sortDirFiles(pathDest);
				if (filesMapSource != null && !filesMapSource.isEmpty() && filesMapDest != null) {
					String folder;
					if (!filesMapDest.isEmpty()) {
						for (String readABB : FileManagerConstants.readableABB) {
							folder = pathDest + readABB + "\\";
							synchrIfNotEmpty(filesMapSource.get(readABB), filesMapDest.get(readABB), folder);
						}
					} else {
						for (String readABB : FileManagerConstants.readableABB) {
							folder = pathDest + readABB + "\\";
							for (File file : filesMapSource.get(readABB)) {
								new File(folder).mkdirs(); // nor folder enq
															// stexcum
								Files.copy(file.toPath(), (new File(folder + file.getName())).toPath());
								LOG.info(file.getName() + " : file is not exist and is copyed");
							}
						}
					}

				}

			} else if (Arrays.asList(FileManagerConstants.readableABB).contains(option)) {
				List<File> filesListSource = FileUtil.getList(pathSource, "." + option.toLowerCase());
				List<File> filesListDest = FileUtil.getList(pathDest, "." + option.toLowerCase());

				if (filesListSource != null && !filesListSource.isEmpty() && filesListDest != null) {
					String folder;
					if (!filesListDest.isEmpty()) {
						folder = pathDest + option + "\\";
						synchrIfNotEmpty(filesListSource, filesListDest, folder);

					} else {

						folder = pathDest + option + "\\";
						for (File file : filesListSource) {
							new File(folder).mkdirs(); // nor folder enq stexcum
							Files.copy(file.toPath(), (new File(folder + file.getName())).toPath());
							LOG.info(file.getName() + " : file is not exist and is copyed");
						}

					}

				}
			} else {
				LOG.error("ERROR: This file type is not synchronize");
			}
		} else {
			LOG.error("ERROR: File type is missing or equaly null");
		}

	}

	/**
	 * 
	 * @param source
	 * @param dest
	 * @param folder
	 * @throws IOException
	 */
	private void synchrIfNotEmpty(List<File> source, List<File> dest, String folder) throws IOException {
		LOG.info("Entering synchr method.");
		if (source != null && !source.isEmpty()) {

			for (File sourceFile : source) {
				exist = false;
				for (File destFile : dest) {
					if ((sourceFile.getName()).equals(destFile.getName())) {
						exist = true;

						if (sourceFile.lastModified() == destFile.lastModified()) {
							LOG.info(sourceFile.getName() + " : file is not synchronized");
						} else {
							Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
							LOG.info(sourceFile.getName() + " : file is copyed");
						}
						break;
					}
				}

				if (!exist) {
					if (!(new File(folder).exists())) {
						new File(folder).mkdirs();
					}
					Files.copy(sourceFile.toPath(), (new File(folder + sourceFile.getName())).toPath());
					LOG.info(sourceFile.getName() + " : file is not exist and is copyed");
				}
			}
		}
	}

}
