package com.anjbo.utils;

import com.alibaba.druid.support.logging.LogFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**压缩工具
 * Created by Administrator on 2017/7/4.
 */
public class ZIPUtil {

    /**
     * @desc 将源文件/文件夹生成指定格式的压缩文件,格式zip
     * @param resourcesPath 源文件/文件夹
     * @param targetPath  目的压缩文件保存路径
     * @return void
     * @throws Exception
     */
    public String compressedFile(String resourcesPath,String targetPath,String targetName) throws Exception{
        String name = "";
        ZipOutputStream out = null;
        FileOutputStream outputStream = null;
        String targetFilePath = "";
        try {
            if(isBank(resourcesPath)){
                return "";
            }

            name = subString(File.separator ,resourcesPath);
            File resourcesFile = new File(resourcesPath);     //源文件
            if(!resourcesFile.exists())return "";

            File targetFile = new File(targetPath);           //目的
            //如果目的路径不存在，则新建
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            targetName = targetName + ".zip";
            targetFilePath = targetPath + File.separator + targetName;
            outputStream = new FileOutputStream(targetFilePath);
            out = new ZipOutputStream(new BufferedOutputStream(outputStream));
            createCompressedFile(out, resourcesFile, name);

        }  catch (Exception e){
            throw new Exception(e);
        } finally {
            try{
                if(null!=out){
                    out.close();
                }
                if(null!=outputStream){
                    outputStream.close();
                }
            } catch (Exception e){
                throw new Exception(e);
            }
        }
        return targetFilePath;

    }

    /**
     * @desc 将源文件生成指定格式的压缩文件,格式zip
     * @param resourcesNameList 源文件集合
     * @param resourcesPath 源文件路径
     * @param targetPath  目的压缩文件保存路径
     * @param targetName  目的压缩文件保存名称
     * @return String     输出文件路径
     * @throws Exception
     */
    public String compressedFile(List<String> resourcesNameList,String resourcesPath, String targetPath, String targetName) throws Exception{
        ZipOutputStream out = null;
        FileOutputStream outputStream = null;
        File resourcesFile = null;
        String targetFilePath = "";
        try {
            if(isBank(resourcesPath)){
                return "";
            }
            File targetFile = new File(targetPath);           //目的
            //如果目的路径不存在，则新建
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            targetName = targetName + ".zip";
            targetFilePath = targetPath + File.separator + targetName;
            outputStream = new FileOutputStream(targetFilePath);
            out = new ZipOutputStream(new BufferedOutputStream(outputStream));

            for (String name:resourcesNameList) {
                resourcesFile = new File(resourcesPath+File.separator+name);     //源文件
                createCompressedFile(out, resourcesFile, name);
            }

        }  catch (Exception e){
            throw new Exception(e);
        } finally {
            try{
                if(null!=out){
                    out.close();
                }
                if(null!=outputStream){
                    outputStream.close();
                }
            } catch (Exception e){
                throw new Exception(e);
            }
        }
        return targetFilePath;

    }

    /**
     * @desc 将源文件生成指定格式的压缩文件,格式zip
     * @param resourcesNameList 源文件集合
     * @param targetPath  目的压缩文件保存路径
     * @param targetName  目的压缩文件保存名称
     * @return void
     * @throws Exception
     */
    public String compressedFile(List<File> resourcesNameList, String targetPath, String targetName) throws Exception{
        ZipOutputStream out = null;
        FileOutputStream outputStream = null;
        String targetFilePath = "";
        try {
            File targetFile = new File(targetPath);           //目的
            //如果目的路径不存在，则新建
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            targetName = targetName + ".zip";
            targetFilePath = targetPath + File.separator + targetName;
            outputStream = new FileOutputStream(targetFilePath);

            out = new ZipOutputStream(new BufferedOutputStream(outputStream));
            String name = "";
            for (File f:resourcesNameList) {
                if(!f.exists())continue;
                name = subString(File.separator,f.getName());
                createCompressedFile(out, f, name);
            }
            out.finish();
            LogFactory.getLog(ZIPUtil.class).info("-----生成ZIP文件end -----" + targetFilePath);
        }  catch (Exception e){
            throw new Exception(e);
        } finally {
            try{
                if(null!=out){
                    out.close();
                }
                if(null!=outputStream){
                    outputStream.close();
                }
            } catch (Exception e){
                throw new Exception(e);
            }
        }
        return targetName;

    }

    /**
     * @desc 生成压缩文件。
     *                  如果是文件夹，则使用递归，进行文件遍历、压缩
     *       如果是文件，直接压缩
     * @param out  输出流
     * @param file 目标文件
     * @param dir  打包到压缩包里面的名字
     * @return void
     * @throws Exception
     */
    public void createCompressedFile(ZipOutputStream out,File file,String dir) throws Exception{
        FileInputStream fis = null;
        try{
            if(!file.exists()){
                return;
            }
            fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(dir));
            int j =  0;
            byte[] buffer = new byte[4096];
            while((j = fis.read(buffer)) > 0){
                out.write(buffer,0,j);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(null!=fis){
                    fis.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        ZIPUtil compressedFileUtil = new ZIPUtil();

        try {
            /*
            List<String> list = new ArrayList<String>();
            list.add("1.jpg");
            list.add("2.jpg");
            compressedFileUtil.compressedFile(list,"D:\\", "D:\\","1");
            */
            List<File> list = new ArrayList<File>();
            File f1 = new File("D:\\1.jpg");
            File f2 = new File("D:\\2.jpg");
            list.add(f1);
            list.add(f2);
            String path =  compressedFileUtil.compressedFile(list, "D:\\","1");
            System.out.println(path);
            System.out.println("压缩文件已经生成...");
        } catch (Exception e) {
            System.out.println("压缩文件生成失败...");
            e.printStackTrace();
        }
    }

    public boolean isBank(String str){
        if("".equals(str)||str.length()<=0){
            return true;
        }
        return false;
    }
    public <T> boolean isBank(List<T> list){
        if(null==list||list.size()<=0){
            return true;
        }
        return false;
    }
    public boolean isBank(String[] str){
        if(null==str||str.length<=0){
            return true;
        }
        return false;
    }

    /**
     * 按规则截取名称
     * @param rule 规则
     * @param substr 原名称
     * @return
     */
    public String subString(String rule,String substr){
        String tmp = substr;
        if(isBank(tmp)){
            return tmp;
        }
        int index = tmp.lastIndexOf(rule);
        if(index!=-1&&(index+1)<substr.length()){
            tmp = tmp.substring(index+1,tmp.length());
        }
        return tmp;
    }

    /**
     * 按规则截取路径
     * @param rule
     * @param substr
     * @return
     */
    public String susFileDir(String rule,String substr){
        String tmp = substr;
        if(isBank(tmp)){
            return tmp;
        }
        int index = tmp.indexOf(rule);
        if(index!=-1){
            tmp = tmp.substring(index+3,tmp.length());
        }
        index = tmp.indexOf("/");
        if(index!=-1){
            tmp = tmp.substring(index+1,tmp.length());
        }
        return tmp;
    }
}
