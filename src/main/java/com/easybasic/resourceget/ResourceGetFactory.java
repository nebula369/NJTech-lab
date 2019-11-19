package com.easybasic.resourceget;

import com.easybasic.resourceget.NullResourceGet;
import com.easybasic.resourceget.IResourceGet;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ResourceGetFactory {

    @Resource
    private ApplicationContext applicationContext;

    public IResourceGet getResourceGetInstance(String resourceType)
    {
        resourceType = "resourceget_" + resourceType;
        IResourceGet response = null;
        if(applicationContext.containsBean(resourceType.toLowerCase())) {
            response = (IResourceGet) applicationContext.getBean(resourceType.toLowerCase());
        }
        if(response == null)
        {
            response = new NullResourceGet();
        }
        return response;
    }
}

