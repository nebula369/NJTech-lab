package com.easybasic.component.timer;

import com.easybasic.basic.service.LoginLogService;
import com.easybasic.basic.service.OpLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TimerTask  {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private OpLogService opLogService;
    @Resource
    private LoginLogService loginLogService;

    @Scheduled(cron = "0 0 23 ? * *")//每天晚上23点触发
    //@Scheduled(cron = "0/5 * * * * ?")//每隔5秒触发
    public void clearLogData(){
        try{
            opLogService.deleteByUnValidate();
        }
        catch(Exception ex)
        {
            logger.error("定期清空操作日志数据错误", ex);
        }
        try{
            loginLogService.deleteByUnValidate();
        }
        catch(Exception ex)
        {
            logger.error("定期清空登录日志数据错误", ex);
        }
    }

}
