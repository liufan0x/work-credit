package com.anjbo.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/3.
 */
public class ImgToPdf {

    private static ImgToPdf imgToPdf;

    private ImgToPdf(){}

    public static ImgToPdf getInstance(){
        if (null==imgToPdf){
            imgToPdf = new ImgToPdf();
        }
        return imgToPdf;
    }

    /**
     *
     * @param imageUrllist 图片路径集合
     * @param targetPath 生成的pdf路径
     * @param outputPdfFileName 生成的pdf名称
     * @return
     */
    public File toPdf(List<String> imageUrllist,String targetPath, String outputPdfFileName) {
        Document doc = getDocument(PageSize.A4, 20, 20, 20, 20);
        String targetFilePath = "";
        try {
            targetFilePath = targetPath+File.separator+outputPdfFileName;
            PdfWriter.getInstance(doc, new FileOutputStream(targetFilePath));
            docAddListImgUrl(doc,imageUrllist);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File mOutputPdfFile = new File(targetFilePath);
        if (!mOutputPdfFile.exists()) {
            mOutputPdfFile.deleteOnExit();
            return null;
        }
        return mOutputPdfFile;
    }

    /**
     *
     * @param imgurl 图片路径
     * @param mOutputPdfFileName 生成pdf路径
     * @return
     */
    public File toPdf(String imgurl, String mOutputPdfFileName) {
        Document doc = getDocument(PageSize.A4, 20, 20, 20, 20);
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mOutputPdfFileName));
            docAddListImgUrl(doc,imgurl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File mOutputPdfFile = new File(mOutputPdfFileName);
        if (!mOutputPdfFile.exists()) {
            mOutputPdfFile.deleteOnExit();
            return null;
        }
        return mOutputPdfFile;
    }

    public File toPdf(String imgurl,String pdfPath,String pdfName){
        return toPdf(imgurl,pdfPath+File.separator+pdfName);
    }

    public boolean isBank(String str){
        if("".equals(str)||str.length()<=0){
            return true;
        }
        return false;
    }

    public void docAddListImgUrl(Document doc, List<String> list) throws IOException, DocumentException {
        if(null==list||list.size()<=0)return;
        doc.open();
        for (String img:list){
            doc.newPage();
            Image png = Image.getInstance(img);
            float heigth = png.getHeight();
            float width = png.getWidth();
            int percent = getPercent2(heigth, width);
            png.setAlignment(Image.MIDDLE); //图像对齐
            png.scalePercent(percent+3); // 表示是原来图像的比例;
            doc.add(png);
        }
        doc.close();
    }

    public void docAddListImgUrl(Document doc, String imgUrl) throws IOException, DocumentException {
        doc.open();
            doc.newPage();
            Image png = Image.getInstance(imgUrl);
            float heigth = png.getHeight();
            float width = png.getWidth();
            int percent = getPercent2(heigth, width);
            png.setAlignment(Image.MIDDLE); //图像对齐
            png.scalePercent(percent+3); // 表示是原来图像的比例;
            doc.add(png);
        doc.close();
    }
    /**
     *
     * @param pageSize 纸张类型
     * @param marginLeft 左边的页边空白 默认36
     * @param marginRight 右边的页边空白 默认36
     * @param marginTop 上边的页边空白 默认36
     * @param marginBottom 下边的页边空白 默认36
     * @return
     */
    public Document getDocument(Rectangle pageSize, float marginLeft, float marginRight,
                                float marginTop, float marginBottom){
        if (marginLeft<0)marginLeft = 36;
        if (marginRight<0)marginRight = 36;
        if (marginTop<0)marginTop = 36;
        if (marginBottom<0)marginBottom = 36;
        Document doc = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
        return doc;
    }

    /**
     * 第一种解决方案 在不改变图片形状的同时，判断，如果h>w，则按h压缩，否则在w>h或w=h的情况下，按宽度压缩
     *
     * @param h
     * @param w
     * @return
     */

    public static int getPercent(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        if (h > w) {
            p2 = 297 / h * 100;
        } else {
            p2 = 210 / w * 100;
        }
        p = Math.round(p2);
        return p;
    }

    /**
     * 第二种解决方案，统一按照宽度压缩 这样来的效果是，所有图片的宽度是相等的，
     *
     * @param
     */
    public static int getPercent2(float h, float w) {
        int p = 0;
        float p2 = 0.0f;
        p2 = 530 / w * 100;
        p = Math.round(p2);
        return p;
    }

    public static void main(String[] args) {
        ArrayList<String> imageUrllist = new ArrayList<String>();
        imageUrllist.add("D:\\1.jpg");
        imageUrllist.add("D:\\2.jpg");
        String pdfUrl = "D:\\";
        String pdfName = "1491890451952.pdf";
        File file = ImgToPdf.getInstance().toPdf(imageUrllist,pdfUrl,pdfName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
