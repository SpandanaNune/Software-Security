package sbs.web.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;

public class Compressor {

	List<String> fileList;
	private  String sourceFolder;

	public Compressor() {
		fileList = new ArrayList<String>();
	}

	public  void compressFiles(String outputFile,String sourceFolder1) {
		sourceFolder = sourceFolder1;
		generateFileList(new File(sourceFolder));
		zipIt(outputFile);
	}
	
//	public static void main(String args[])
//	{
//		String outputFile = FilenameUtils.separatorsToSystem(System.getProperty("catalina.home") + "/temp/logs_" + System.currentTimeMillis() + ".zip");
//		String sourceFolder = FilenameUtils.separatorsToSystem(System.getProperty("catalina.home") + "/logs");
//		compressFiles(outputFile, sourceFolder);
//	}

	public void zipIt(String zipFile) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFile);
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (String file : this.fileList) {

				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(sourceFolder + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				in.close();
			}
			zos.closeEntry();
			zos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void generateFileList(File node) {

		// add file only
		if (node.isFile()) {
			fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
		}

		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}

	}

	private String generateZipEntry(String file) {
		return file.substring(sourceFolder.length() + 1, file.length());
	}

}
