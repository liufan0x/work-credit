package com.anjbo.util;

import com.anjbo.utils.HttpUtil;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lichao on 2018/1/25.
 */
public class BoxUtil {

    private static final Logger logger = Logger.getLogger(BoxUtil.class);

    private static final String openUrl ="http://cos.wondware.com/mq/device/service";
    private static final String statusUrl ="http://cos.wondware.com/mq/device/getBoxStatus";
    //线上
    private static final String uqKey ="4F1DB5340DAD314F81EA1C07";
    //测试
    //private static final String uqKey ="TEST_KUAIGE";


    /**
     * 远程开锁接口
     * @param deviceId 设备ID
     * @param lockAddress 锁地址
     * @return
     * @throws Exception
     */
    public static String openBox(String deviceId,String lockAddress) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("deviceId", deviceId);//设备ID
        map.put("lockAddress", lockAddress);//	锁地址(每个要开的锁为4位数，前两位表示开的锁板，后2位表示开的锁号)
        map.put("lockTotalNum", "1");//开锁数量
        map.put("commandType", "openOne");//开箱的命令 (openAll:打开全部 ,openOne:打开一个(或多个))
        map.put("sourceType", "O");//开箱类型(G:借/取,:S:还/存，O:开,接到标志为“O”都会执行开)
        map.put("uqKey", uqKey);//用户密钥
        logger.info("开箱请求:"+map);
        return HttpUtil.post(openUrl, map);
    }

    /**
     * 查看箱子状态
     * @param deviceId 设备ID
     * @param lockAddress 锁地址
     * @return
     * @throws Exception
     */
    public static String getBoxStatus(String deviceId,String lockAddress) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("deviceId", deviceId);
        map.put("lockAddress", lockAddress);
        map.put("lockTotalNum", "1");
        map.put("uqKey", uqKey);
        logger.info("箱子状态请求:"+map);
        return HttpUtil.post(statusUrl, map);
    }
    
    public static void main(String[] args) throws Exception {
    	System.out.println(BoxUtil.openBox("0000010353","0905"));
	}
}
