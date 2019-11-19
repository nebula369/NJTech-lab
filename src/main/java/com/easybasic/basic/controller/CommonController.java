package com.easybasic.basic.controller;

import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
@RequestMapping("/manage/basic/common")
public class CommonController extends BaseController {

    @Resource
    private UnitService unitService;

    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    public String selectUser(@RequestParam(value = "type", required = false, defaultValue = "0")String type,
                             @RequestParam(value = "isSingle", required = false, defaultValue = "0")String isSingle,
                             Model model)
    {
        Unit unit = unitService.getUnitForCache(1);
        if(unit!=null)
        {
            model.addAttribute("topUnitName", unit.getName());
        }
        else
        {
            model.addAttribute("topUnitName","未知");
        }
        model.addAttribute("isSingle", TypeConverter.strToInt(isSingle));
        if(TypeConverter.strToInt(type)==0) {
            return "/manage/basic/common/selectteacher";
        }
        else
        {
            return "/manage/basic/common/selectstudent";
        }
    }

}
