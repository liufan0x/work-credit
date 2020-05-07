package com.anjbo.service.signature;

import com.timevale.esign.sdk.tech.bean.*;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.EventCertResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import org.springframework.stereotype.Service;

/**
 * Created by runwu on 2017/8/25 0025.
 */
@Service
public interface PlatformSignService {
    /**
     * 创建个人账户
     * @param personBean
     * @return
     */
    public AddAccountResult addPersonAccount(PersonBean personBean);

    /**
     * 创建企业账户
     * @param organizeBean
     * @return
     */
    public AddAccountResult addOrganizeAccount(OrganizeBean organizeBean);


    /***
     * 坐标定位签署的PosBean
     */
    public PosBean setXYPosBean(String page, int x, int y);
    /***
     * 坐标定位签署的PosBean
     */
    public PosBean setXYPosBean(String page, int x, int y, Integer borrowWay);

    /***
     * 关键字定位签署的PosBean
     */
    public PosBean setKeyPosBean(String key);

    /***
     * 文件流签署的PDF文档信息
     */
    public SignPDFStreamBean setSignPDFStreamBean(byte[] pdfFileStream) ;

    /***
     * 文件路径签署的PDF文档信息
     */
    public SignPDFFileBean setSignPDFFileBean(String srcPdfFile, String signedPdfFile);

    /***
     * 平台自身PDF摘要签署（文件二进制流）； 盖章位置通过坐标定位； 使用到接口：SelfSignServiceFactory.instance();
     * selfSignService.localSignPdf(signPDFStreamBean, posBean, sealId,
     * SignType.Single);
     */
    public FileDigestSignResult platformSignByStreamm(byte[] pdfFileStream, PosBean posBean, SignType signType);

    /***
     * 平台自身PDF摘要签署（文件）； 盖章位置通过坐标定位； 使用到接口：SelfSignServiceFactory.instance();
     * selfSignService.localSignPdf(signPDFStreamBean, posBean, sealId,
     * SignType.Single);
     */
    public FileDigestSignResult platformSignByFile(String srcPdfFile, String signedPdfFile) ;

    /***
     * 平台下个人用户PDF摘要签署（文件二进制流）；盖章位置通过关键字定位； 使用到接口：UserSignServiceFactory.instance();
     * userSignService.localSignPDF(accountId,addSealResult.getSealData(),
     * signPDFStreamBean, posBean, SignType.Single);
     */
    public FileDigestSignResult userPersonSignByStream(byte[] pdfFileStream, String accountId,
                                                       String sealData, PosBean posBean, SignType signType);

    /***
     * 平台下个人用户PDF摘要签署（文件）；盖章位置通过关键字定位； 使用到接口：UserSignServiceFactory.instance();
     * userSignService.localSignPDF(accountId,addSealResult.getSealData(),
     * signPDFStreamBean, posBean, SignType.Single);
     */
    public FileDigestSignResult userPersonSignByFile(String srcPdf, String signedPdf, String accountId,
                                                     String sealData);

    /***
     * 平台下企业用户PDF摘要签署（文件二进制流）；盖章位置通过关键字定位； 使用到接口：UserSignServiceFactory.instance();
     * userSignService.localSignPDF(accountId,addSealResult.getSealData(),
     * signPDFStreamBean, posBean, SignType.Single);
     */
    public FileDigestSignResult userOrganizeSignByStream(byte[] pdfFileStream, String accountId,
                                                         String sealData);
    /***
     * 通过accountId注销账户
     * @param accountId
     */
    public  void deleteAccount(String accountId);
    /***
     * 更新个人账户信息
     * @param accountId
     */
    public  void updatePersonAccount(String accountId, String mobile);

    /***
     * 更新企业账户信息
     * @param accountId
     */
    public  void updateOrganizeAccount(String accountId, String mobile);

    /***
     * 创建个人账户的印章； 使用到接口：sealService.addTemplateSeal(accountId,
     * PersonTemplateType.SQUARE, SealColor.RED);
     */
    public AddSealResult addPersonTemplateSeal(String accountId);

    /***
     * 创建企业账户的印章,该企业账户印章是一个相对概念。可以理解成是你们公司的客户企业印章；
     * 使用到接口：sealService.addTemplateSeal(accountId, OrganizeTemplateType.STAR,
     * SealColor.RED, "合同专用章", "下弦文");
     */
    public AddSealResult addOrganizeTemplateSeal(String accountId, String hText, String qText);

    /***
     * 上传印章图片制作SealData；
     * 使用到接口：Apache Commons Codec的org.apache.commons.codec.binary.Base64
     * 该方法属于 第三方jar实现，并非快捷签SDK提供；
     */
    public  String getSealDataByImage(String imgFilePath);
    /***
     * 保存签署后的文件流
     */
    public boolean saveSignedByStream(byte[] signedStream, String signedFolder, String signedFileName) ;

    /**
     * 创建事件证书
     * @return
     */
    public EventCertResult createEventCert(EventBean event);

    /**
     * 	事件证书PDF摘要签署（文件流）
     * @param certId
     * @param stream
     * @return
     */
    public FileDigestSignResult localSignPDFByEvent(String certId, PosBean posBean, String sealData, SignPDFStreamBean stream);
}
