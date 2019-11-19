package com.easybasic.basic.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.SchoolClass;
import com.easybasic.basic.model.Space;
import com.easybasic.basic.model.SpaceType;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.SchoolClassService;
import com.easybasic.basic.service.SpaceService;
import com.easybasic.basic.service.SpaceTypeService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.Login;
import com.easybasic.component.jwt.LoginUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/basic/space")
public class SpaceController extends BaseController {

    @Resource
    private SpaceService spaceService;
    @Resource
    private SpaceTypeService spaceTypeService;
    @Resource
    private SchoolClassService schoolClassService;
    @Resource
    private UnitService unitService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String spaceTypeList(Model model)
    {
        String re = setPageCommonModel(model, "basic","/manage/basic/space", false);
        if(!StringUtils.isEmpty(re))
        {
            List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
            initUnitList(list);
            model.addAttribute("unitList", list);
            return "manage/basic/unitset/space/type/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getSpaceTypeList", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<SpaceType> getSpaceTypeList(JqGridPageRequest pageRequest,Integer unitId)
    {
        List<SpaceType> list = spaceTypeService.getListByUnitId(unitId);
        JqGridPageResponse<SpaceType> response = new JqGridPageResponse<>();
        response.setRows(list);
        return response;
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String spaceTypeAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        return "manage/basic/unitset/space/type/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int spaceTypeDoAdd(SpaceType spaceType)
    {
        spaceType.setCreatetime(new Date());
        spaceTypeService.insert(spaceType);
        setOpLog("basic","场地设置", "新增场地类型：" + spaceType.getName());
        return 1;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String spaceTypeEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                           Model model)
    {
        SpaceType spaceType = spaceTypeService.getByPkId(TypeConverter.strToInt(id));
        if(spaceType==null)
        {
            model.addAttribute("msg","要编辑的场地类型信息不存在");
            return "/error/error";
        }
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("unitList", list);
        model.addAttribute("spaceType", spaceType);
        return "manage/basic/unitset/space/type/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int spaceTypeDoEdit(SpaceType editData)
    {
        SpaceType spaceType = spaceTypeService.getByPkId(editData.getPkid());
        if(spaceType!=null) {
            spaceType.setName(editData.getName());
            spaceType.setSortnum(editData.getSortnum());
            spaceType.setRemark(editData.getRemark());
            spaceTypeService.update(spaceType);
            setOpLog("basic","场地设置", "编辑场地类型数据：" + spaceType.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int spaceTypeDoDel(int id)
    {
        SpaceType spaceType = spaceTypeService.getByPkId(id);
        if(spaceType!=null) {
            List<Space> list = spaceService.getListBySearch("typeid=" + spaceType.getPkid(),"pkid desc");
            if(list.size()>0)
            {
                return 0;
            }
            spaceTypeService.delete(id);
            setOpLog("basic","场地设置", "删除场地类型数据：" + spaceType.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String spaceList(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                            Model model)
    {
        SpaceType spaceType = spaceTypeService.getByPkId(TypeConverter.strToInt(id));
        if(spaceType == null)
        {
            model.addAttribute("msg","场地类型不存在");
            return "/error/error";
        }
        String re = setPageCommonModel(model, "basic","/manage/basic/space", false);
        if(!StringUtils.isEmpty(re))
        {
            model.addAttribute("id", TypeConverter.strToInt(id));
            model.addAttribute("spaceTypeName", spaceType.getName());
            return "manage/basic/unitset/space/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getSpaceListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Space> getSpaceList(JqGridPageRequest pageRequest, int typeId, String searchKey)
    {
        String searchStr = "typeid=" + typeId;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and (name like '%"+searchKey+"%' or address like '%"+searchKey+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Space> list = spaceService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(Space space : list)
        {
            SchoolClass schoolClass = schoolClassService.getByPkId(space.getClassid());
            if(schoolClass!=null)
            {
                space.setClassName(schoolClass.getGradeName()+schoolClass.getName());
            }
        }
        PageInfo<Space> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Space> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/spaceAdd", method = RequestMethod.GET)
    public String spaceAdd(@RequestParam(value = "id", required = false, defaultValue = "0")String id,Model model)
    {
        model.addAttribute("typeId", TypeConverter.strToInt(id));
        return "manage/basic/unitset/space/add";
    }

    @RequestMapping(value = "/spaceDoAdd", method = RequestMethod.POST)
    @ResponseBody
    public int spaceDoAdd(Space space)
    {
        SpaceType spaceType = spaceTypeService.getByPkId(space.getTypeid());
        space.setCreatetime(new Date());
        space.setClassid(0);
        space.setUnitid(spaceType.getUnitid());
        spaceService.insert(space);
        setOpLog("basic","场地设置", "新增场地：" + space.getName());
        return 1;
    }

    @RequestMapping(value = "/spaceEdit", method = RequestMethod.GET)
    public String spaceEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                                Model model)
    {
        Space space = spaceService.getByPkId(TypeConverter.strToInt(id));
        if(space==null)
        {
            model.addAttribute("msg","要编辑的场地信息不存在");
            return "/error/error";
        }
        model.addAttribute("space", space);
        return "manage/basic/unitset/space/edit";
    }

    @RequestMapping(value = "/spaceDoEdit", method = RequestMethod.POST)
    @ResponseBody
    public int spaceDoEdit(Space editData)
    {
        Space space = spaceService.getByPkId(editData.getPkid());
        if(space!=null) {
            space.setName(editData.getName());
            space.setAddress(editData.getAddress());
            space.setSortnum(editData.getSortnum());
            space.setRemark(editData.getRemark());
            space.setCapacity(editData.getCapacity());
            spaceService.update(space);
            setOpLog("basic","场地设置", "编辑场地数据：" + space.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/spaceDoDel", method = RequestMethod.POST)
    @ResponseBody
    public int spaceDoDel(int id)
    {
        Space space = spaceService.getByPkId(id);
        if(space!=null) {
            spaceService.delete(id);
            setOpLog("basic","场地设置", "删除场地数据：" + space.getName());
        }
        return 1;
    }
}
