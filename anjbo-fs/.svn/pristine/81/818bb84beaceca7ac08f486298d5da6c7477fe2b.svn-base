package com.anjbo.utils;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import org.w3c.dom.Element;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

@SuppressWarnings("restriction")
public class PDFToImg {
	
	public static void main(String[] args) {
		PDFToImg img = new PDFToImg();
		img.changePdfToImg("E:\\房屋买卖合同(自行交易版).pdf", "E:\\");
	}
	
	public void changePdfToImg(String instructiopath,String picturepath) {
		int countpage =0;
		try {
			File file = new File(instructiopath);
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			FileChannel channel = raf.getChannel();  
            MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,  
                    0, channel.size());  
            PDFFile pdffile = new PDFFile(buf);  
            //创建图片文件夹  
            File dirfile = new File(picturepath);  
            if(!dirfile.exists()){  
            	dirfile.mkdirs();  
            }  
            countpage = pdffile.getNumPages();  
            for (int i = 1; i <= pdffile.getNumPages(); i++) {  
                PDFPage page = pdffile.getPage(i);  
                Rectangle rect = new Rectangle(0, 0, ((int) page.getBBox().getWidth()), ((int) page.getBBox().getHeight()));  
                int n = 2;  
                Image img = page.getImage(rect.width * n, rect.height * n,  
                        rect, /** 放大pdf到n倍，创建图片。 */  
                        null, /** null for the ImageObserver */  
                        true, /** fill background with white */  
                        true /** block until drawing is done */  
                );
                BufferedImage tag = new BufferedImage(rect.width * n,  
                        rect.height * n, BufferedImage.TYPE_INT_RGB);  
                tag.getGraphics().drawImage(img, 0, 0, rect.width * n,  
                        rect.height * n, null);  
                FileOutputStream out = new FileOutputStream(picturepath+"/" + i + ".png");  
                /** 输出到文件流 */
                saveAsJPEG(100, tag, 1f, out);
                out.close();  
            }  
            channel.close();  
            raf.close();  
            unmap(buf);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void unmap(final Object buffer) {  
        AccessController.doPrivileged(new PrivilegedAction() {  
			public Object run() {  
                try {  
                    Method getCleanerMethod = buffer.getClass().getMethod(  
                            "cleaner", new Class[0]);  
                    getCleanerMethod.setAccessible(true);  
                    sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod  
                            .invoke(buffer, new Object[0]);  
                    cleaner.clean();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
                return null;  
            }  
        });  
    }  
	
	/**
     * 以JPEG编码保存图片
     * @param dpi  分辨率
     * @param image_to_save  要处理的图像图片
     * @param JPEGcompression  压缩比
     * @param fos 文件输出流
     * @throws IOException
     */
    @SuppressWarnings("restriction")
	public static void saveAsJPEG(Integer dpi ,BufferedImage image_to_save, float JPEGcompression, FileOutputStream fos) throws IOException {
        // Image writer
        JPEGImageWriter imageWriter  =  (JPEGImageWriter) ImageIO.getImageWritersBySuffix("jpg").next();
        ImageOutputStream ios  =  ImageIO.createImageOutputStream(fos);
        imageWriter.setOutput(ios);
        //and metadata
        IIOMetadata imageMetaData  =  imageWriter.getDefaultImageMetadata(new ImageTypeSpecifier(image_to_save), null);
        
        
    	if(dpi !=  null && !dpi.equals("")){
    		
    		 //old metadata
            //jpegEncodeParam.setDensityUnit(com.sun.image.codec.jpeg.JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
            //jpegEncodeParam.setXDensity(dpi);
            //jpegEncodeParam.setYDensity(dpi);
     
            //new metadata
            Element tree  =  (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif  =  (Element)tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(dpi) );
            jfif.setAttribute("Ydensity", Integer.toString(dpi));
            
    	}
        if(JPEGcompression >= 0 && JPEGcompression <= 1f){
            // new Compression
            JPEGImageWriteParam jpegParams  =  (JPEGImageWriteParam) imageWriter.getDefaultWriteParam();
            jpegParams.setCompressionMode(JPEGImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(JPEGcompression);
     
        }
     
        //new Write and clean up
        imageWriter.write(imageMetaData, new IIOImage(image_to_save, null, null), null);
        ios.close();
        imageWriter.dispose();
     
    }
}
