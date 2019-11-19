package com.easybasic.component;

import com.easybasic.basic.service.SiteConfigService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.basic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartInit implements ServletContextListener {

    @Autowired
    private SiteConfigService siteConfigService;
    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //加上这句可以使Autowired注解生效，不然为null
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            siteConfigService.initSiteConfig();
        }
        catch(Exception ex)
        {
        }
        try {
            userService.initAdminUser();
        }catch (Exception ex)
        {}
        try {
            unitService.initTopUnit();
        }catch (Exception ex)
        {}
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
