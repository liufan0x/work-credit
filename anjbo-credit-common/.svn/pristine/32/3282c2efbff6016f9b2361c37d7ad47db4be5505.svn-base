package com.anjbo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Rocca
 *
 */
public class WordPdfUtils {

    protected static final Logger logger = LoggerFactory.getLogger(WordPdfUtils.class);

    public boolean wordConverterToPdf(String docxPath) throws IOException {
        File file = new File(docxPath);
        String path = file.getParent();
        try {
            String osName = System.getProperty("os.name");
            String command = "";
            if (osName.contains("Windows")) {
                //soffice --convert-to pdf  -outdir E:/test.docx
                command = "soffice --convert-to pdf  -outdir " + path + " " + docxPath;
            } else {
                command = "doc2pdf --output=" + path + File.separator + file.getName().replaceAll(".(?i)docx", ".pdf") + " " + docxPath;
            }
            String result = executeCommand(command);
//          LOGGER.info("result==" + result);
            System.out.println("生成pdf的result==" + result);
            if (result.equals("") || result.contains("writer_pdf_Export")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            p.destroy();//如果有语法错误请改下，这是要销毁这个进程
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStreamReader);
        }
        System.out.println(output.toString());
        return output.toString();

    }

    //测试用
    public static void main(String[] args) {
        try {
            new WordPdfUtils().wordConverterToPdf("D:/temp/123.docx");
        } catch (IOException e) {
            System.out.println("word转换成pdf时出错");
            e.printStackTrace();
        }
    }
}