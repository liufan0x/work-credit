package com.anjbo.utils;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class ImageMetaDataUtil {
	/**
	 * 图片是否ps
	 * @param imgPath
	 * @return 0 未修改 1已修改
	 */
	 public static int isPs(File imageFile){
		try {
			  Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
			  for (Directory directory : metadata.getDirectories())
			  {
			     for (Tag tag : directory.getTags())
			     {
			        if(tag.toString().toLowerCase().contains("photoshop")){
			        	return 1;
			        }
			     }
			  }
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return 0;
	  }
	 public static void main(String[] args) throws ImageProcessingException, IOException {
		 System.out.println(isPs(new File("D:/照片滤镜.jpg")));
	}
}
