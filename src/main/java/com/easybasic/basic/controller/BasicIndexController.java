package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.component.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manage/basic")
public class BasicIndexController extends BaseController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/index", true);
        if(!StringUtils.isEmpty(re))
        {
            return "redirect:" + re;
        }
        else {
            model.addAttribute("msg","您没有权限访问该应用");
            return "/error/error";
        }
        //return "/manage/index";
    }
}
