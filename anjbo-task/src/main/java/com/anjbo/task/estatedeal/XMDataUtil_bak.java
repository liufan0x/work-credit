package com.anjbo.task.estatedeal;


import com.anjbo.bean.estatedeal.XMDealDto;
import com.anjbo.common.DateUtil;
import com.anjbo.common.HttpUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 厦门抓取数据初始方案，上线后可以删除该类
 */
public class XMDataUtil_bak {

    /*厦门一手房*/
    private static final String oneHandUrl="http://cloud.xm.gov.cn:88/xmzf/zf/newspfj.jsp";

    /**
     * 厦门一手房数据
     * @return
     * @throws Exception
     */
    public static java.util.List<XMDealDto> getXMOneHandData() throws Exception {
        InputStream inputStream = XMDataUtil_bak.class.getResourceAsStream("/background.png");
        InputStream inputStream2 = getPNG();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        addHeadImgToBackground(inputStream2,inputStream,byteArrayOutputStream);
        String imageStr = BaiduImageUtil.imageRequest(byteArrayOutputStream.toByteArray());
        ArrayList<Date> dateList = getDateList(imageStr);
        java.util.List<XMDealDto> dealData = getDealData();
        for(XMDealDto dataDto:dealData){
            dataDto.setStartDate(dateList.get(0));
            dataDto.setEndDate(dateList.get(1));
        }
        return dealData;
    }

    private static java.util.List<XMDealDto> getDealData(){
        ArrayList<XMDealDto> list = new ArrayList<XMDealDto>();
        String[] areaNames = new String[6];
        String[] houses = new String[6];
        String[] totalNums = new String[6];
        HttpUtil httpUtil = new HttpUtil();
        String s = httpUtil.get("http://cloud.xm.gov.cn:88/xmzf/zf/newspfj.jsp", null);
        Document doc = Jsoup.parse(s);
        Elements trs = doc.select("table tr");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            if(i==0){
                for (int j = 1; j < tds.size(); j++) {
                    areaNames[j-1]=tds.get(j).text();
                }
            }else if(i==1){
                for (int j = 1; j < tds.size(); j++) {
                    houses[j-1]=tds.get(j).text();
                }
            }else if(i==3){
                for (int j = 1; j < tds.size(); j++) {
                    totalNums[j-1]=tds.get(j).text();
                }
            }

        }
        for (int i = 0; i < 6; i++) {
            XMDealDto dataDto = new XMDealDto();
            dataDto.setAreaName(areaNames[i]);
            dataDto.setHouseNum(Integer.valueOf(houses[i]));
            dataDto.setTotalNum(Integer.valueOf(totalNums[i]));
            list.add(dataDto);
        }
        return list;
    }

    private static ArrayList<Date> getDateList(String imageStr){
        ArrayList<Date> list = new ArrayList<Date>();
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(imageStr);
        while (matcher.find()){
            String s = matcher.group(0);
            Date date = DateUtil.parse(s, "yyyy-MM-dd");
            list.add(date);
        }
        if(list.size()<2){
             pattern = Pattern.compile("\\d{6}-\\d{2}");
             matcher = pattern.matcher(imageStr);
            if (matcher.find()){
                String s = matcher.group(0);
                Date date = DateUtil.parse(s, "yyyyMM-dd");
                list.add(date);
            }
        }
        if(list.size()==1){
            Date date = list.get(0);
            Date date2 = DateUtils.addDays(date, 7);
            list.add(date2);
        }
        return list;
    }

    private static InputStream getPNG() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.xmjydj.com/BothImg/SPFJSIMG?id=1488782741035");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        InputStream inputStream = entity.getContent();
        return inputStream;
    }

    private static void addHeadImgToBackground(InputStream headImg,InputStream bg,OutputStream output) throws Exception {
        //读取背景图片
        BufferedInputStream backBIS = new BufferedInputStream(bg);
        Image backImage = ImageIO.read(backBIS);

        //读取头像图片
        BufferedInputStream headBIS = new BufferedInputStream(headImg);
        Image headImage = ImageIO.read(headBIS);

        if (backImage == null)
            System.out.println("haha null");
        int alphaType = BufferedImage.TYPE_INT_RGB;
        if (hasAlpha(backImage)) {
            alphaType = BufferedImage.TYPE_INT_ARGB;
        }

        //画图
        BufferedImage backgroundImage = new BufferedImage(backImage.getWidth(null), backImage.getHeight(null), alphaType);
        Graphics2D g = backgroundImage.createGraphics();
        g.drawImage(backImage, 0, 0, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
        g.drawImage(headImage, 0, 0, headImage.getWidth(null), headImage.getHeight(null), null);

        //输出
        byte[] imageInByte = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(backgroundImage, "png", baos);
        imageInByte = baos.toByteArray();
        baos.close();

        BufferedOutputStream target = new BufferedOutputStream(output);
        target.write(imageInByte);
        target.close();
    }

    //在背景上添加图片
    private static void addHeadImgToBackground(File headImg,File bg,File output) throws Exception {
        addHeadImgToBackground(new FileInputStream(headImg),new FileInputStream(bg),new FileOutputStream(output));
    }

    /**
     * 是否开启alpha通道
     */
    private static boolean hasAlpha(Image image) throws InterruptedException {
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        pg.grabPixels();
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }


    public static void main(String[] args) throws Exception {
        java.util.List<XMDealDto> xiaMenOneHandData = getXMOneHandData();
        System.out.println(xiaMenOneHandData.size());
    }
}
