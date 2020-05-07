package com.anjbo.service.signature.impl;

import com.anjbo.dao.signature.SignatureMapper;
import com.anjbo.service.signature.PlatformSignService;
import com.anjbo.utils.signature.BorrowWayEnum;
import com.timevale.esign.sdk.tech.bean.*;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.EventCertResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.bean.seal.OrganizeTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.PersonTemplateType;
import com.timevale.esign.sdk.tech.bean.seal.SealColor;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import com.timevale.esign.sdk.tech.service.*;
import com.timevale.esign.sdk.tech.service.factory.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by ruanwu on 2017/8/25 0025.
 */
@Service
public class PlatformSignServiceImpl implements PlatformSignService {

    private final static Logger logger = LoggerFactory.getLogger(PlatformSignServiceImpl.class);

    @Resource
    private SignatureMapper signatureMapper;

    @Override
    public AddAccountResult addPersonAccount(PersonBean personBean) {
        // 邮箱地址,可空
        // personBean.setEmail(null);
        // 手机号码,可空
//		personBean.setMobile("152****4800");
        // 姓名
        personBean.setName(personBean.getName());
        // 身份证号/护照号
        personBean.setIdNo(personBean.getIdNo());
        // 个人归属地：0-大陆，1-香港，2-澳门，3-台湾，4-外籍，默认0
        personBean.setPersonArea(0);
        // 所属公司,可空
        // personBean.setOrgan("客户的企业");
        // 职位,可空
        // personBean.setTitle("部门经理");
        // 常用地址,可空
        // personBean.setAddress("XXX街道");

        logger.info("----开始创建个人账户...");
        AccountService accountService = AccountServiceFactory.instance();
        AddAccountResult addAccountResult = accountService.addAccount(personBean);
        if (0 != addAccountResult.getErrCode()) {
            logger.info("创建个人账户失败，errCode=" + addAccountResult.getErrCode() + " msg=" + addAccountResult.getMsg()
            +"账户信息--姓名---"+personBean.getName()+"--身份证id---"+personBean.getIdNo());
        } else {
            logger.info("创建个人账户成功！accountId = " + addAccountResult.getAccountId());

        }
        return addAccountResult;
    }

    @Override
    public AddAccountResult addOrganizeAccount(OrganizeBean organizeBean) {
        // 邮箱地址,可空
        // organizeBean.setEmail(null);
        // 手机号码,可空
//		organizeBean.setMobile("188****0787");
        // 企业名称
        organizeBean.setName(organizeBean.getName());
        // 单位类型，0-普通企业，1-社会团体，2-事业单位，3-民办非企业单位，4-党政及国家机构，默认0
        organizeBean.setOrganType(organizeBean.getOrganType());
        // 企业注册类型，NORMAL:组织机构代码号，MERGE：多证合一，传递社会信用代码号,REGCODE:企业工商注册码,默认NORMALMERGE
        organizeBean.setRegType(organizeBean.getRegType());
        // 组织机构代码号、社会信用代码号或工商注册号
        organizeBean.setOrganCode(organizeBean.getOrganCode());
        // 公司地址,可空
       // organizeBean.setAddress("杭州城落霞峰7号");
        // 经营范围,可空
        // organizeBean.setScope("");

        // 注册类型，1-代理人注册，2-法人注册，默认1
       // organizeBean.setUserType(1);

        // 代理人姓名，当注册类型为1时必填
        //organizeBean.setAgentName("艾利");
        // 代理人身份证号，当注册类型为1时必填
        //organizeBean.setAgentIdNo("220301198705170035");

        // 法定代表姓名，当注册类型为2时必填
        // organizeBean.setLegalName("天云");
        // 法定代表人归属地,0-大陆，1-香港，2-澳门，3-台湾，4-外籍，默认0
        // organizeBean.setLegalArea(0);
        // 法定代表身份证号/护照号，当注册类型为2时必填
        // organizeBean.setLegalIdNo("220301198705170019");

        logger.info("----开始创建企业账户...");
        AccountService accountService = AccountServiceFactory.instance();
        AddAccountResult addAccountResult = accountService.addAccount(organizeBean);

        if (0 != addAccountResult.getErrCode()) {
            logger.info("创建企业账户失败，errCode=" + addAccountResult.getErrCode() + " msg=" + addAccountResult.getMsg());
        } else {
            logger.info("创建企业账户成功！accountId = " + addAccountResult.getAccountId());
        }
        return addAccountResult;
    }

    @Override
    public PosBean setXYPosBean(String page, int x, int y) {
        PosBean posBean = new PosBean();
        // 定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效。
        posBean.setPosType(0);
        // 签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
        posBean.setPosPage(page);
        // 签署位置X坐标，默认值为0，以pdf页面的左下角作为原点，控制横向移动距离，单位为px
        posBean.setPosX(x);
        // 签署位置Y坐标，默认值为0，以pdf页面的左下角作为原点，控制纵向移动距离，单位为px
        posBean.setPosY(y);
        // 印章展现宽度，将以此宽度对印章图片做同比缩放。详细查阅接口文档的15 PosBean描述
        posBean.setWidth(159);
        return posBean;
    }
    @Override
    public PosBean setXYPosBean(String page, int x, int y, Integer borrowWay) {
        PosBean posBean = new PosBean();
        // 定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效。
        posBean.setPosType(0);
        // 签署页码，若为多页签章，支持页码格式“1-3,5,8“，若为坐标定位时，不可空
        posBean.setPosPage(page);
        // 签署位置X坐标，默认值为0，以pdf页面的左下角作为原点，控制横向移动距离，单位为px
        posBean.setPosX(x);
        // 签署位置Y坐标，默认值为0，以pdf页面的左下角作为原点，控制纵向移动距离，单位为px
        if(borrowWay.equals(BorrowWayEnum.enterprise.getCode())){
            posBean.setPosY(y-45);
        }else{
            posBean.setPosY(y);
        }
        // 印章展现宽度，将以此宽度对印章图片做同比缩放。详细查阅接口文档的15 PosBean描述
        posBean.setWidth(80);
        return posBean;
    }
    @Override
    public PosBean setKeyPosBean(String key) {
        PosBean posBean = new PosBean();
        // 定位类型，0-坐标定位，1-关键字定位，默认0，若选择关键字定位，签署类型(signType)必须指定为关键字签署才会生效。
        posBean.setPosType(1);
        // 关键字签署时不可空 */
        posBean.setKey(key);
        // 关键字签署时会对整体pdf文档进行搜索，故设置签署页码无效
        // posBean.setPosPage("1");
        // 签署位置X坐标，以关键字所在位置为原点进行偏移，默认值为0，控制横向移动距离，单位为px
        posBean.setPosX(0);
        // 签署位置Y坐标，以关键字所在位置为原点进行偏移，默认值为0，控制纵向移动距离，单位为px
        posBean.setPosY(0);
        // 印章展现宽度，将以此宽度对印章图片做同比缩放。详细查阅接口文档的15 PosBean描述
        posBean.setWidth(159);
        return posBean;
    }

    @Override
    public SignPDFStreamBean setSignPDFStreamBean(byte[] pdfFileStream) {
        SignPDFStreamBean signPDFStreamBean = new SignPDFStreamBean();
        // 待签署文档本地二进制数据
        signPDFStreamBean.setStream(pdfFileStream);
        // 文档名称，e签宝签署日志对应的文档名，若为空则取文档路径中的名称
        // signPDFStreamBean.setFileName("pdf文件名");
        // 文档编辑密码，当目标PDF设置权限密码保护时必填 */
        // signPDFStreamBean.setOwnerPassword(null);
        return signPDFStreamBean;
    }

    @Override
    public SignPDFFileBean setSignPDFFileBean(String srcPdfFile, String signedPdfFile) {
        return null;
    }

    @Override
    public FileDigestSignResult platformSignByStreamm(byte[] pdfFileStream, PosBean posBean, SignType signType) {
        // 设置文件流签署的PDF文档信息
        SignPDFStreamBean signPDFStreamBean = setSignPDFStreamBean(pdfFileStream);
        // 设置坐标定位签署的PosBean，坐标定位方式支持单页签章、多页签章和骑缝章，但对关键字签章指定页码无效；
//        PosBean posBean = setXYPosBean("1",170,714);
        // 设置签署类型为 单页签章，坐标定位方式支持单页签章、多页签章和骑缝章
        // 设置签署印章，www.tsign.cn官网设置的默认签名sealId = 0
        int sealId = 0;

        logger.info("----开始平台自身PDF摘要签署...");
        SelfSignService selfSignService = SelfSignServiceFactory.instance();
        FileDigestSignResult fileDigestSignResult = selfSignService.localSignPdf(signPDFStreamBean, posBean, sealId,
                signType);
        if (0 != fileDigestSignResult.getErrCode()) {
            logger.info("平台自身PDF摘要签署（文件流）失败，errCode=" + fileDigestSignResult.getErrCode() + " msg="
                    + fileDigestSignResult.getMsg());
        } else {
            logger.info("----平台自身PDF摘要签署成功！SignServiceId = " + fileDigestSignResult.getSignServiceId());
        }
        return fileDigestSignResult;
    }

    @Override
    public FileDigestSignResult platformSignByFile(String srcPdfFile, String signedPdfFile) {
        return null;
    }

    @Override
    public FileDigestSignResult userPersonSignByStream(byte[] pdfFileStream, String accountId, String sealData, PosBean posBean, SignType signType) {
        // 设置文件流签署的PDF文档信息
        SignPDFStreamBean signPDFStreamBean = setSignPDFStreamBean(pdfFileStream);
        // 设置坐标定位签署的PosBean，坐标定位方式支持单页签章、多页签章和骑缝章，但对关键字签章指定页码无效；
//        posBean = setKeyPosBean("");
        // 设置签署类型为 关键字签章

        logger.info("----开始平台个人客户的PDF摘要签署...");
        UserSignService userSignService = UserSignServiceFactory.instance();
        FileDigestSignResult fileDigestSignResult = userSignService.localSignPDF(accountId, sealData, signPDFStreamBean,
                posBean, signType);
        if (0 != fileDigestSignResult.getErrCode()) {
            logger.info("平台个人客户的PDF摘要签署失败，errCode=" + fileDigestSignResult.getErrCode() + " msg="
                    + fileDigestSignResult.getMsg());
        } else {
            logger.info("平台个人客户的PDF摘要签署成功！SignServiceId = " + fileDigestSignResult.getSignServiceId());
        }
        return fileDigestSignResult;
    }

    @Override
    public FileDigestSignResult userPersonSignByFile(String srcPdf, String signedPdf, String accountId, String sealData) {
        return null;
    }

    @Override
    public FileDigestSignResult userOrganizeSignByStream(byte[] pdfFileStream, String accountId, String sealData) {
        return null;
    }

    @Override
    public void deleteAccount(String accountId) {

    }

    @Override
    public void updatePersonAccount(String accountId, String mobile) {

    }

    @Override
    public void updateOrganizeAccount(String accountId, String mobile) {

    }

    @Override
    public AddSealResult addPersonTemplateSeal(String accountId) {
        /*
		 * hText 生成印章中的横向文内容 如“合同专用章、财务专用章” qText 生成印章中的下弦文内容 公章防伪码（一串13位数字）
		 * 如91010086135601
		 */

        // 印章模板类型：标准公章
        PersonTemplateType personTemplateType = PersonTemplateType.RECTANGLE;
        // 印章颜色：红色
        SealColor sealColor = SealColor.RED;
        // 横向文字
//        String hText = "合同专用章";
        // 下弦文字
//        String qText = "91010086135601";
        logger.info("----开始创建个人账户的印章...");
        SealService sealService = SealServiceFactory.instance();
        AddSealResult addSealResult = sealService.addTemplateSeal(accountId, personTemplateType, sealColor);
        if (0 != addSealResult.getErrCode()) {
            logger.info("创建个人模板印章失败，errCode=" + addSealResult.getErrCode() + " msg=" + addSealResult.getMsg());
        } else {
//            UploadFileParam entity = new UploadFileParam();
//            entity.setFileName(accountId);
//            entity.setFunc(EUploadFileFunc.GAT_ACTIVE_CGT);
//            entity.setInputStream(SFtpTool.BaseToInputStream(addSealResult.getSealData()));
//            entity.setTag(true);
//            entity.setType(EUploadFileType.IMAGE);
//            UploadFileResult uploadFileResult = null;
//            try {
//                uploadFileResult = SFtpTool.uploadFile(entity);
//            } catch (IOException e) {
//                logger.error("创建个人模板生成成功。上传ftp异常",  e);
//            }
            logger.info("创建个人模板印章成功！SealData = " + addSealResult.getSealData());
        }
        return addSealResult;
    }

    @Override
    public AddSealResult addOrganizeTemplateSeal(String accountId, String hText, String qText ) {
        /*
		 * hText 生成印章中的横向文内容 如“合同专用章、财务专用章” qText 生成印章中的下弦文内容 公章防伪码（一串13位数字）
		 * 如91010086135601
		 */

        // 印章模板类型：标准公章
        OrganizeTemplateType organizeTemplateType = OrganizeTemplateType.STAR;
        // 印章颜色：红色
        SealColor sealColor = SealColor.RED;
        // 横向文字
//        String hText = "合同专用章";
        // 下弦文字
//        String qText = "91010086135601";
        logger.info("----开始创建企业账户的印章...");
        SealService sealService = SealServiceFactory.instance();
        AddSealResult addSealResult = sealService.addTemplateSeal(accountId, organizeTemplateType, sealColor, hText,
                qText);
        if (0 != addSealResult.getErrCode()) {
            logger.info("创建企业模板印章失败，errCode=" + addSealResult.getErrCode() + " msg=" + addSealResult.getMsg());
        } else {
            logger.info("创建企业模板印章成功！SealData = " + addSealResult.getSealData());
        }
        return addSealResult;
    }

    @Override
    public String getSealDataByImage(String imgFilePath) {
        return null;
    }

    @Override
    public boolean saveSignedByStream(byte[] signedStream, String signedFolder, String signedFileName) {
        return false;
    }

    @Override
    public EventCertResult createEventCert(EventBean event) {
        EventCertResult eventCertResult = new EventCertResult();
        EventCertService eventCertService =  EventCertServiceFactory.instance();
        eventCertResult  = eventCertService.addEventCert(event);
        if (0 != eventCertResult.getErrCode()){
            logger.info("创建事件证书失败，errCode=" + eventCertResult.getErrCode() + " msg=" + eventCertResult.getMsg());
        }else{
            logger.info("创建事件证书成功！--- 证书id ---  certId = " + eventCertResult.getCertId());
        }
        return eventCertResult;
    }

    @Override
    public FileDigestSignResult localSignPDFByEvent(String certId, PosBean posBean, String sealData, SignPDFStreamBean stream) {

//        PosBean posBean = setXYPosBean("6",160,320);
//        posBean.setWidth(80);
                EventSignService eventSignService =  EventSignServiceFactory.instance();
        // 设置签署类型为 关键字签章
        SignType signType = SignType.Single;
        FileDigestSignResult fileDigestSignResult =
                eventSignService.localSignPDFByEvent(certId,sealData,stream,posBean,signType);


        if (0 != fileDigestSignResult.getErrCode()) {
            logger.info("事件证书PDF摘要签署（文件流）失败--errCode=" + fileDigestSignResult.getErrCode() + " msg="
                    + fileDigestSignResult.getMsg());
        } else {
            logger.info("事件证书PDF摘要签署（文件流）！成功 --SignServiceId = " + fileDigestSignResult.getSignServiceId());
        }
        return fileDigestSignResult;
    }
}
