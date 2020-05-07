package com.anjbo.utils.signature;

import com.anjbo.bean.user.UserDto;
import com.anjbo.common.Constants;
import com.anjbo.common.RespDataObject;
import com.anjbo.common.RespHelper;
import com.anjbo.common.RespStatusEnum;
import com.anjbo.service.signature.PlatformSignService;
import com.anjbo.service.signature.SignatureService;
import com.anjbo.utils.ConfigUtil;
import com.anjbo.utils.HttpUtil;
import com.anjbo.utils.StringUtil;
import com.anjbo.utils.yntrust.Abutment;
import com.anjbo.utils.yntrust.BASE64;
import com.anjbo.utils.yntrust.ConfigUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timevale.esign.sdk.tech.bean.OrganizeBean;
import com.timevale.esign.sdk.tech.bean.PersonBean;
import com.timevale.esign.sdk.tech.bean.PosBean;
import com.timevale.esign.sdk.tech.bean.result.AddAccountResult;
import com.timevale.esign.sdk.tech.bean.result.AddSealResult;
import com.timevale.esign.sdk.tech.bean.result.FileDigestSignResult;
import com.timevale.esign.sdk.tech.bean.result.Result;
import com.timevale.esign.sdk.tech.impl.constants.OrganRegType;
import com.timevale.esign.sdk.tech.impl.constants.SignType;
import com.timevale.esign.sdk.tech.service.EsignsdkService;
import com.timevale.esign.sdk.tech.service.factory.EsignsdkServiceFactory;
import com.timevale.tech.sdk.bean.HttpConnectionConfig;
import com.timevale.tech.sdk.bean.ProjectConfig;
import com.timevale.tech.sdk.bean.SignatureConfig;
import net.sf.json.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2018/3/29.
 */
public class ElectronSignatureAbutment {
    private Logger log = Logger.getLogger(getClass());
    /**
     *登录账号
     */
    public static String SIGNATURE_ACCOUNT;
    public static String SIGNATURE_ACCOUNT_KEY = "base.signature.account";
    /**
     *登录密码
     */
    public static String SIGNATURE_PASSWORD;
    public static String SIGNATURE_PASSWORD_KEY = "base.signature.password";
    /**
     * 签署密码
     */
    public static String SIGNATURE_SIGN_PASSWORD;
    public static String SIGNATURE_SIGN_PASSWORD_KEY = "base.signature.sign.password";
    /**
     * e签宝url
     */
    private static String SIGNATURE_URL;
    private static String SIGNATURE_URL_KEY = "base.signature.url";
    /**
     * 项目id
     */
    private static String SIGNATURE_PROJECTID;
    private static String SIGNATURE_PROJECTID_KEY = "base.signature.projectid";
    /**
     * 项目密钥
     */
    private static String PROJECT_SECRET;
    private static String PROJECT_SECRET_KEY = "base.signature.project.secret";
    private static ElectronSignatureAbutment electronSignatureAbutment;

    private RestTemplate restTemplate = null;

    private EsignsdkService SDK = null;

    private Gson gson = null;

    private PlatformSignService platformSignService;

    private SignatureService signatureService;

    private UserDto user;

    private ElectronSignatureAbutment(){}

    public static Object object = new Object();
    public static ElectronSignatureAbutment getInstance(){
        if(null==electronSignatureAbutment){
            synchronized (object){
                if(null==electronSignatureAbutment){
                    electronSignatureAbutment = new ElectronSignatureAbutment();
                    electronSignatureAbutment.init();
                }
            }
        }
        return electronSignatureAbutment;
    }

    /**
     * 签章
     * @param result
     * @param list
     */
    public void stamp(RespDataObject<Map<String,Object>> result,List<Map<String,Object>> list,String url)throws Exception{
        //先判断用户是否在本地游保存签章,如果有则去本地,如果没有则调用e签宝
        //本地有则跳过过去签章
        RespDataObject<String> resultUrl = getCustomerCardNumber(list);

        if(RespStatusEnum.FAIL.getCode().equals(resultUrl.getCode())){
            result.setCode(resultUrl.getCode());
            result.setMsg(resultUrl.getMsg());
            return;
        }

        String customerCardNumber = resultUrl.getData();
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("customerCardNumber",customerCardNumber);
//        List<Map<String, Object>> data = signatureService.list(param);
//        if(null!=data&&data.size()>0){
//            compare(result,list,data);
//            if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
//        } else {
        createSignature(result,list);
//        }
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        //将获取到签章加章在pdf上面
        List<Map<String,Object>> byteList = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("url",url);
        byteList.add(map);
        List<Map<String, Object>> fileBytes = getFileBytes(byteList, result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        map = fileBytes.get(0);
        String bytearr = MapUtils.getString(map,"bytearr");
        byte[] pdf = BASE64.decodeBase64(bytearr);
        byteList = getFileBytes(list,result);
        if(RespStatusEnum.FAIL.getCode().equals(result.getCode()))return;
        userPersonSignByStream(result,pdf,byteList,url);
    }

    /**
     * 签章
     * @param pdf
     * @param byteList
     */
    public void userPersonSignByStream(RespDataObject<Map<String,Object>> result,byte[] pdf,List<Map<String,Object>> byteList,String fileName)throws Exception{
        int x = -30;
        int y = -260;
        int index = 1;
        PosBean posBean = null;
        FileDigestSignResult tmp = null;
        String sealData = null;
        for (Map<String,Object> m:byteList){
        	
            posBean = platformSignService.setKeyPosBean("本页系签章页，由合同各方签字签章");
            posBean.setPosPage("9");
            posBean.setPosX(x);
            posBean.setPosY(y);
            if(1==MapUtils.getInteger(m,"customerType")) {
                posBean.setWidth(100);
            }
            sealData = MapUtils.getString(m,"bytearr");
            tmp = platformSignService.userPersonSignByStream(pdf, MapUtils.getString(m,"accountId"),sealData, posBean, SignType.Key);
            if(null==tmp.getStream()){
                log.info("=============为pdf签章失败:"+tmp.getMsg()+"==============");
                RespHelper.setFailDataObject(result,null,RespStatusEnum.FAIL.getMsg());
                return;
            }
            pdf = tmp.getStream();
            index++;
            if(index==5) {
            	x += 200;
            	y = -260;
            }else {
            	y -= 100;
            }
        }
        RespDataObject<Map<String, Object>> upload = upload(pdf,fileName);
        if(RespStatusEnum.FAIL.getCode().equals(upload.getCode())){
            result.setMsg(upload.getMsg());
            result.setCode(upload.getCode());
            return;
        }
        RespHelper.setSuccessDataObject(result,upload.getData());
    }

    /**
     * 创建签章
     * @param resultMap
     * @param list
     */
    public void createSignature(RespDataObject<Map<String,Object>> resultMap,List<Map<String,Object>> list)throws Exception{
        PersonBean p = null;
        OrganizeBean organizeBean = null;
        String accountId;
        AddAccountResult addAccountResult;
        AddSealResult addSealResult = null;
        for(Map<String,Object> m:list){
            accountId = "";
            if(1==MapUtils.getInteger(m,"customerType")) {
                p = new PersonBean();
                p.setName(MapUtils.getString(m, "customerName"));
                p.setIdNo(MapUtils.getString(m, "customerCardNumber"));
                addAccountResult = platformSignService.addPersonAccount(p);
                if (0 != addAccountResult.getErrCode()) {
                    RespHelper.setFailDataObject(resultMap,null,"获取签章失败,原因: 姓名:"+MapUtils.getString(m,"customerName")+",身份证:"+MapUtils.getString(m,"customerCardNumber")+";"+addAccountResult.getMsg());
                    return;
                } else {
                    accountId = addAccountResult.getAccountId();
                }
            } else if(2==MapUtils.getInteger(m,"customerType")) {
                organizeBean = new OrganizeBean();
                organizeBean.setName(MapUtils.getString(m,"customerName"));
                if(1==MapUtils.getInteger(m,"customerCardType")){
                    organizeBean.setRegType(OrganRegType.REGCODE);
                } else if(2==MapUtils.getInteger(m,"customerCardType")){
                    organizeBean.setRegType(OrganRegType.NORMAL);
                } else if(3==MapUtils.getInteger(m,"customerCardType")){
                    organizeBean.setRegType(OrganRegType.MERGE);
                }
                organizeBean.setOrganCode(MapUtils.getString(m,"customerCardNumber"));
                addAccountResult = platformSignService.addOrganizeAccount(organizeBean);
                if (0 != addAccountResult.getErrCode()) {
                    RespHelper.setFailDataObject(resultMap,null,"获取签章失败,原因: 姓名:"+MapUtils.getString(m,"customerName")+",身份证:"+MapUtils.getString(m,"customerCardNumber")+";"+addAccountResult.getMsg());
                    return;
                } else {
                    accountId = addAccountResult.getAccountId();
                }
            } else {
                log.info(MapUtils.getString(m,"customerName")+"获取签章失败");
                RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败,原因:借款人类型为空");
                return;
            }
            if(StringUtil.isBlank(accountId)){
                log.info(MapUtils.getString(m,"customerName")+"获取签章失败");
                RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败");
                return;
            } else {
                m.put("accountId",accountId);
                m.put("createUid",user.getUid());
                m.put("updateUid",user.getUid());
                if(1==MapUtils.getInteger(m,"customerType")) {
                    addSealResult = platformSignService.addPersonTemplateSeal(accountId);
                } else {
                    addSealResult = platformSignService.addOrganizeTemplateSeal(accountId,"","");
                }

                if(0 != addSealResult.getErrCode()){
                    RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败,原因:"+addSealResult.getMsg());
                    return;
                } else {
                    byte[] bytes = BASE64.decodeBase64(addSealResult.getSealData());
                    RespDataObject<Map<String, Object>> upload = upload(bytes,"");
                    if(RespStatusEnum.SUCCESS.getCode().equals(upload.getCode())){
                        Map<String, Object> imgData = upload.getData();
                        m.put("url",MapUtils.getString(imgData,"url"));
                    } else {
                        RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败,原因:"+upload.getMsg());
                        return;
                    }
                    signatureService.insert(m);
                }
            }
        }
        //signatureService.batchInsert(list);
    }

    /**
     * 是否有生成过保存在本地数据库
     * @param result
     * @param list
     * @param data
     * @throws Exception
     */
    public void compare(RespDataObject<Map<String,Object>> result,List<Map<String,Object>> list,List<Map<String,Object>> data) throws Exception {
        List<Map<String,Object>> tmp = new ArrayList<Map<String,Object>>();
        Iterator<Map<String, Object>> iterator = list.iterator();
        boolean exist = false;
        String customerCardNumber = "";
        String tmpCustomerCardNumber = "";
        while (iterator.hasNext()){
            exist = false;
            Map<String,Object> m = iterator.next();
            customerCardNumber = MapUtils.getString(m,"customerCardNumber");

            Iterator<Map<String, Object>> dataIterator = data.iterator();
            while (dataIterator.hasNext()){
                Map<String, Object> t = dataIterator.next();
                tmpCustomerCardNumber = MapUtils.getString(t,"customerCardNumber");
                if(customerCardNumber.equals(tmpCustomerCardNumber)
                        &&StringUtil.isNotBlank(MapUtils.getString(t,"url"))){
                    m.put("accountId",MapUtils.getString(t,"accountId"));
                    m.put("url",MapUtils.getString(t,"url"));
                    exist = true;
                    dataIterator.remove();
                    break;
                }
            }
            if(!exist){
                tmp.add(m);
                iterator.remove();
            }
        }
        if(tmp.size()>0){
            createSignature(result,tmp);
            //signatureService.batchInsert(tmp);
            if(null==list||list.size()<=0){
                list = tmp;
            } else {
                list.addAll(tmp);
            }
        }
    }

    /**
     * 获取证件号
     * @param list
     * @return
     */
    public RespDataObject<String> getCustomerCardNumber(List<Map<String,Object>> list){
        RespDataObject<String> respDataObject = new RespDataObject<String>();
        String customerCardNumber = "";
        String error = "";
        for (Map<String,Object> m:list){
            if(StringUtil.isBlank(MapUtils.getString(m,"customerName"))
                &&StringUtil.isBlank(MapUtils.getString(m,"customerCardNumber"))){
                RespHelper.setFailDataObject(respDataObject,null,"身份证与姓名不能为空");
                break;
            }
            if(StringUtil.isBlank(MapUtils.getString(m,"customerName"))){
                error += MapUtils.getString(m,"customerCardNumber")+":姓名不能为空;\n";
            } else if(StringUtil.isBlank(MapUtils.getString(m,"customerCardNumber"))){
                error += MapUtils.getString(m,"customerName")+":证件号不能为空;\n";
            } else if(null==MapUtils.getInteger(m,"customerType",null)){
                error += "借款人类型不能为空;\n";
            } else if(null!=MapUtils.getInteger(m,"customerType")
                    &&2==MapUtils.getInteger(m,"customerType")
                    &&null==MapUtils.getInteger(m,"customerCardType",null)){
                error += "证件类型不能为空;\n";
            }
            customerCardNumber += "'"+MapUtils.getString(m,"customerCardNumber")+"',";
        }
        if(StringUtil.isBlank(error)){
            customerCardNumber = customerCardNumber.substring(0,customerCardNumber.length()-1);
            RespHelper.setSuccessDataObject(respDataObject,customerCardNumber);
        } else {
            RespHelper.setFailDataObject(respDataObject,null,error);
        }
        return respDataObject;
    }

    public void init(){
        if(StringUtil.isBlank(SIGNATURE_ACCOUNT)) {
         this.SIGNATURE_ACCOUNT = ConfigUtils.INSTANCE.getStringValue(SIGNATURE_ACCOUNT_KEY, Abutment.PROPERTIES_NAME);
        }
        if(StringUtil.isBlank(SIGNATURE_PASSWORD)){
            this.SIGNATURE_PASSWORD = ConfigUtils.INSTANCE.getStringValue(SIGNATURE_PASSWORD_KEY, Abutment.PROPERTIES_NAME);
        }
        if(StringUtil.isBlank(SIGNATURE_SIGN_PASSWORD)){
            this.SIGNATURE_SIGN_PASSWORD = ConfigUtils.INSTANCE.getStringValue(SIGNATURE_SIGN_PASSWORD_KEY, Abutment.PROPERTIES_NAME);
        }
        if(StringUtil.isBlank(SIGNATURE_URL)){
            this.SIGNATURE_URL = ConfigUtils.INSTANCE.getStringValue(SIGNATURE_URL_KEY, Abutment.PROPERTIES_NAME);
        }
        if(StringUtil.isBlank(SIGNATURE_PROJECTID)){
            this.SIGNATURE_PROJECTID = ConfigUtils.INSTANCE.getStringValue(SIGNATURE_PROJECTID_KEY, Abutment.PROPERTIES_NAME);
        }
        if(StringUtil.isBlank(PROJECT_SECRET)){
            this.PROJECT_SECRET = ConfigUtils.INSTANCE.getStringValue(PROJECT_SECRET_KEY, Abutment.PROPERTIES_NAME);
        }
        if(null==restTemplate){
            restTemplate = new RestTemplate();
        }
        if(null==gson){
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }

    }
    public String initSdk(boolean isInit){
        String resultCode = RespStatusEnum.SUCCESS.getCode();
        try {
            if (null == this.SDK || isInit) {
                log.info("e-签宝初始化开始");
                this.SDK = EsignsdkServiceFactory.instance();
                ProjectConfig projectConfig = new ProjectConfig();
                projectConfig.setItsmApiUrl(SIGNATURE_URL);
                projectConfig.setProjectId(SIGNATURE_PROJECTID);
                projectConfig.setProjectSecret(PROJECT_SECRET);
                log.info("测试环境---PROJECTID{" + SIGNATURE_PROJECTID + "},PROJECTSECRET{" + PROJECT_SECRET + "},ITSMAPIURL{" + SIGNATURE_URL + "}");
                HttpConnectionConfig httpConfig = new HttpConnectionConfig();
                SignatureConfig signConfig = new SignatureConfig();
                Result result = this.SDK.init(projectConfig, httpConfig, signConfig);
                if (0 != result.getErrCode()) {
                    resultCode = RespStatusEnum.FAIL.getCode();
                    log.info("--e-签宝初始化失败：errCode={" + result.getErrCode() + "},msg{" + result.getMsg() + "}");
                } else {
                    log.info("----e-签宝初始化成功！errCode{" + result.getErrCode() + "},msg{" + result.getMsg() + "}");
                }
                log.info("e-签宝初始化结束");
            }
        } catch (Exception e){
            resultCode = RespStatusEnum.FAIL.getCode();
            log.error("签章签章初始化第三方接口异常:",e);
        }
        return resultCode;
    }

    public void initService(PlatformSignService platformSignService
                            ,SignatureService signatureService
                            ,UserDto user){
        if(null==this.platformSignService){
            this.platformSignService = platformSignService;
        }
        if(null==this.signatureService){
            this.signatureService = signatureService;
        }
        if(null==this.user){
            this.user = user;
        }
    }

    /**
     * 读取源文件内容
     * @param filename String 文件路径
     * @throws IOException
     * @return byte[] 文件内容
     */
    public static byte[] readFile(String filename) throws IOException {

        File file = new File(filename);
        if (filename == null || filename.equals("")) {
            throw new NullPointerException("无效的文件路径");
        }
        int len = 0;
        byte[] bytes = new byte[4096];

        FileInputStream fi = null;
        ByteArrayOutputStream output = null;
        try {
            fi = new FileInputStream(file);
            output = new ByteArrayOutputStream();
            while ((len=fi.read(bytes))!=-1){
                output.write(bytes,0,len);
            }
            output.flush();
            return output.toByteArray();
        } finally {
            if(null!=output){
                output.close();
                output = null;
            }
            if(null!=fi){
                fi.close();
                fi = null;
            }
        }

    }

    /**
     * 将数据写入文件
     * @param data byte[]
     * @throws IOException
     */
    public static void writeFile(byte[] data,String filename) throws IOException{
        File file =new File(filename);
        file.getParentFile().mkdirs();
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream =  new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(data);
            bufferedOutputStream.flush();

        } finally {
            if(null!=bufferedOutputStream){
                bufferedOutputStream.close();
                bufferedOutputStream = null;
            }
        }
    }

    public InputStream getInputStream(byte[] bytes)throws IOException{
        ByteArrayInputStream by = null;
        try {
            by = new ByteArrayInputStream(bytes);
            return by;
        } finally {
            if(null!=by){
                by.close();
                by = null;
            }

        }
    }

    /**
     * 获取文件的字节流
     * @param list
     * @param result
     * @return
     */
    public List<Map<String,Object>> getFileBytes(List<Map<String,Object>> list, RespDataObject<Map<String,Object>> result){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<List> requestEntity = new HttpEntity<List>(list, headers);
        String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK);
        RespDataObject<List<Map<String,Object>>> tmp = restTemplate.postForObject(FS_URL+"/fs/yntrust/getFileBytes", requestEntity, RespDataObject.class);
        result.setMsg(tmp.getMsg());
        result.setCode(tmp.getCode());
        return tmp.getData();
    }

    /**
     * 上传到服务器
     * @param bytes
     * @return
     * @throws IOException
     */
    public RespDataObject<Map<String,Object>> upload(byte[] bytes,String fileName) throws IOException {
        InputStream inputStream = getInputStream(bytes);
        if(StringUtil.isBlank(fileName)) {
            return upload(inputStream);
        }
        JSONObject jsonObject = filePostJSONObject(inputStream,fileName);
        RespDataObject<Map<String,Object>> respDataObject = null;
        if(null!=jsonObject){
            respDataObject  = gson.fromJson(jsonObject.toString(), RespDataObject.class);
        } else {
            respDataObject = new RespDataObject<Map<String,Object>>();
            RespHelper.setFailDataObject(respDataObject,null,"上传失败");
        }
        return respDataObject;
    }

    /**
     * 上传到服务器
     * @param in
     * @return
     */
    public RespDataObject<Map<String,Object>> upload(InputStream in){
        Map<String,Object> map = new HashMap<String,Object>() ;
        String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK);
        JSONObject jsonObject = HttpUtil.filePostJSONObject(FS_URL+"/fs/file/upload", map, in);
        RespDataObject<Map<String,Object>> respDataObject = null;
        if(null!=jsonObject){
            respDataObject  = gson.fromJson(jsonObject.toString(), RespDataObject.class);
        } else {
            respDataObject = new RespDataObject<Map<String,Object>>();
            RespHelper.setFailDataObject(respDataObject,null,"上传失败");
        }
        return respDataObject;
    }

    public JSONObject filePostJSONObject(InputStream fis,String fileName) {
        String tmp = fileName;
        tmp = tmp.replace("/",File.separator);
        int index = tmp.lastIndexOf(File.separator);
        if(index!=-1){
            tmp = tmp.substring(index+1,tmp.length());
        }
        JSONObject json = null;
        String FS_URL = ConfigUtil.getStringValue(Constants.LINK_FS_URL,ConfigUtil.CONFIG_LINK);
        Map<String,Object> map = new HashMap<String,Object>();
        DataOutputStream dos = null;
        DataInputStream in = null;
        BufferedReader br = null;
        HttpURLConnection conn = null;
        try {
            String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113";
            String Enter = "\r\n";
            URL url = new URL(FS_URL+"/fs/file/upload");
            conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.connect();
            dos = new DataOutputStream(conn.getOutputStream());
            String part1 = "--" + boundary + Enter + "Content-Disposition: form-data; filename="+tmp+"; name=\"file\"" + Enter + "Content-Type: application/pdf" + Enter + Enter;
            String part2 = Enter + "--" + boundary + Enter + "Content-Type: text/plain" + Enter + "Content-Disposition: form-data; name=\"path\"" + Enter + Enter +MapUtils.getString(map,"path") + Enter + "--" + boundary + "--";
            byte[] xmlBytes = new byte[fis.available()];
            fis.read(xmlBytes);
            dos.writeBytes(part1);
            dos.write(xmlBytes);
            dos.writeBytes(part2);
            dos.flush();

            in = new DataInputStream(conn.getInputStream());
            br = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
            }

            String result = sb.toString();
            json = JSONObject.fromObject(result);
        } catch (IOException var17) {
            var17.printStackTrace();
        } finally {
            try{
                if(null!=br){
                    br.close();
                    br = null;
                }
                if(null!=in){
                    in.close();
                    in = null;
                }
                if(null!=conn){
                    conn.disconnect();
                }
                if(null!=dos){
                    dos.close();
                    dos = null;
                }
            } catch (Exception e){
                log.error("关闭上传文件IO流异常:",e);
            }
        }
        return json;
    }

    /**
     * 获取签章并返回
     * @param resultMap
     * @param list
     */
    public void createAndReturnSignatureUrl(RespDataObject<Map<String,Object>> resultMap,List<Map<String,Object>> list)throws Exception{
        PersonBean p = null;
        OrganizeBean organizeBean = null;
        String accountId;
        AddAccountResult addAccountResult;
        AddSealResult addSealResult = null;
        for(Map<String,Object> m:list){
            accountId = "";
            if(1==MapUtils.getInteger(m,"customerType")) {
                p = new PersonBean();
                p.setName(MapUtils.getString(m, "customerName"));
                p.setIdNo(MapUtils.getString(m, "customerCardNumber"));
                addAccountResult = platformSignService.addPersonAccount(p);
                if (0 != addAccountResult.getErrCode()) {
                    RespHelper.setFailDataObject(resultMap,null,"获取签章失败,原因: 姓名:"+MapUtils.getString(m,"customerName")+",身份证:"+MapUtils.getString(m,"customerCardNumber")+";"+addAccountResult.getMsg());
                    return;
                } else {
                    accountId = addAccountResult.getAccountId();
                }
            } else if(2==MapUtils.getInteger(m,"customerType")) {
                organizeBean = new OrganizeBean();
                organizeBean.setName(MapUtils.getString(m,"customerName"));
                if(1==MapUtils.getInteger(m,"customerCardType")){
                    organizeBean.setRegType(OrganRegType.REGCODE);
                } else if(2==MapUtils.getInteger(m,"customerCardType")){
                    organizeBean.setRegType(OrganRegType.NORMAL);
                } else if(3==MapUtils.getInteger(m,"customerCardType")){
                    organizeBean.setRegType(OrganRegType.MERGE);
                }
                organizeBean.setOrganCode(MapUtils.getString(m,"customerCardNumber"));
                addAccountResult = platformSignService.addOrganizeAccount(organizeBean);
                if (0 != addAccountResult.getErrCode()) {
                    RespHelper.setFailDataObject(resultMap,null,"获取签章失败,原因: 姓名:"+MapUtils.getString(m,"customerName")+",身份证:"+MapUtils.getString(m,"customerCardNumber")+";"+addAccountResult.getMsg());
                    return;
                } else {
                    accountId = addAccountResult.getAccountId();
                }
            } else {
                log.info(MapUtils.getString(m,"customerName")+"获取签章失败");
                RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败,原因:借款人类型为空");
                return;
            }
            if(StringUtil.isBlank(accountId)){
                log.info(MapUtils.getString(m,"customerName")+"获取签章失败");
                RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败");
                return;
            } else {
                m.put("accountId",accountId);
                m.put("createUid",user.getUid());
                m.put("updateUid",user.getUid());
                if(1==MapUtils.getInteger(m,"customerType")) {
                    addSealResult = platformSignService.addPersonTemplateSeal(accountId);
                } else {
                    addSealResult = platformSignService.addOrganizeTemplateSeal(accountId,"","");
                }

                if(0 != addSealResult.getErrCode()){
                    RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败,原因:"+addSealResult.getMsg());
                    return;
                } else {
                    byte[] bytes = BASE64.decodeBase64(addSealResult.getSealData());
                    RespDataObject<Map<String, Object>> upload = upload(bytes,"");
                    if(RespStatusEnum.SUCCESS.getCode().equals(upload.getCode())){
                        Map<String, Object> imgData = upload.getData();
                        m.put("url",MapUtils.getString(imgData,"url"));
                        resultMap.setData(m);
                    } else {
                        RespHelper.setFailDataObject(resultMap,null,MapUtils.getString(m,"customerName")+"获取签章失败,原因:"+upload.getMsg());
                        return;
                    }
                    signatureService.insert(m);
                }
            }
        }
        //signatureService.batchInsert(list);
    }
  
}


