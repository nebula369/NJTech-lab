package com.easybasic.edu.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.edu.model.Subject;
import com.easybasic.edu.service.SubjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manage/edu/subject")
public class SubjectController extends BaseController {

    @Resource
    private SubjectService subjectService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String subjectList(Model model)
    {
        String re = setPageCommonModel(model, "edu","/manage/edu/subject", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/edu/set/subject/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getSubjectList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Subject> getSubjectList(JqGridPageRequest pageRequest,String searchName)
    {
        List<Subject> list = subjectService.getAllList();
        if(!StringUtils.isEmpty(searchName))
        {
            list = list.stream().filter(x->x.getName().indexOf(searchName)>=0).collect(Collectors.toList());
        }
        list = list.stream().sorted(Comparator.comparing(Subject::getSortnum)).collect(Collectors.toList());
        JqGridPageResponse<Subject> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String subjectAdd(Model model)
    {
        return "manage/edu/set/subject/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int subjectDoAdd(Subject subject)
    {
        if(subject.getCode() == 0)
        {
            return 0;
        }
        Subject temp = subjectService.getByCode(subject.getCode());
        if(temp!=null)
        {
            return -1;
        }
        subjectService.insert(subject);
        setOpLog("edu","学科课程管理", "增加学科课程数据：" + subject.getName());
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String subjectEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                           Model model)
    {
        Subject subject = subjectService.getByPkId(TypeConverter.strToInt(id));
        if(subject==null)
        {
            model.addAttribute("msg","要编辑的学科课程信息不存在");
            return "/error/error";
        }
        model.addAttribute("subject", subject);
        return "manage/edu/set/subject/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int subjectDoEdit(Subject editData)
    {
        Subject subject = subjectService.getByPkId(editData.getPkid());
        if(subject!=null) {
            subject.setName(editData.getName());
            subject.setSortnum(editData.getSortnum());
            subjectService.update(subject);
            setOpLog("edu","学科课程管理", "编辑学科课程数据：" + subject.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int subjectDoDel(int id)
    {
        Subject subject = subjectService.getByPkId(id);
        if(subject!=null) {
            subjectService.delete(id);
            setOpLog("edu","学科课程管理", "删除学科课程数据：" + subject.getName());
        }
        return 1;
    }
}
