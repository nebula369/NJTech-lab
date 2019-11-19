package com.easybasic.edu.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.component.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/manage/edu")
public class EduIndexController extends BaseController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(Model model)
    {
        String re = setPageCommonModel(model, "edu","/manage/edu/index", true);
        if(!StringUtils.isEmpty(re))
        {
            return "redirect:" + re;
        }
        else {
            model.addAttribute("msg","");
            return "/error/error";
        }
    }
}
