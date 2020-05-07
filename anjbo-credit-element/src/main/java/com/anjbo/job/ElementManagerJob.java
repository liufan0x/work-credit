package com.anjbo.job;

import com.anjbo.manager.ElementManager;
import com.anjbo.service.BoxBaseWebService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 2018/1/8.
 */
@Component
public class ElementManagerJob {

    protected static final Log logger = LogFactory.getLog(ElementManagerJob.class);

    @Autowired
    private BoxBaseWebService boxBaseWebService;

    @Scheduled(cron="0 0/30 *  * * ? ")
    public void checkElement() {
        try {
            ElementManager.getInstance().checkElementEffective();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Scheduled(cron="0 0/20 *  * * ? ")
    public void viewOffLineBox() {
        try {
            boxBaseWebService.viewOffLineBox();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
