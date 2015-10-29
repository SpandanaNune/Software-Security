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

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Compressor {

	private  String sourceFolder;
    static ArrayList<File> filesToAdd = new ArrayList<File>();

	public Compressor() {
	}

	public  void compressFiles(String outputFile,String sourceFolder1) {
		sourceFolder = sourceFolder1;
		generateFileList(new File(sourceFolder));
		try {
			zipIt2(outputFile);
		} catch (ZipException e) {
			e.printStackTrace();
		}
	}
	
	 public void zipIt2(String outputfile) throws ZipException
	    {
         ZipFile zipFile = new ZipFile(outputfile);

	    	ZipParameters parameters = new ZipParameters();
         parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
        
         parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
          
         //Set the encryption flag to true
         parameters.setEncryptFiles(true);
          
         //Set the encryption method to AES Zip Encryption
         parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
          
      
         parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
          
         //Set password
         parameters.setPassword("tooltips123");
          
         //Now add files to the zip file
         zipFile.addFiles(filesToAdd, parameters);
	    }
	public void generateFileList(File node) {

		// add file only
		if (node.isFile()) {
			  filesToAdd.add(new File(node.getAbsoluteFile().toString()));

		}
		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileList(new File(node, filename));
			}
		}

	}

}
