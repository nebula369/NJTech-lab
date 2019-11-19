package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
        import com.easybasic.basic.model.Unit;
        import com.easybasic.basic.service.UnitService;
        import com.easybasic.component.BaseController;
        import com.easybasic.component.Utils.JsonUtils;
        import com.easybasic.component.Utils.ToolsUtil;
        import com.easybasic.component.Utils.TypeConverter;
        import com.easybasic.component.jqgrid.JqGridPageRequest;
        import com.easybasic.component.jqgrid.JqGridPageResponse;
        import com.easybasic.component.jwt.LoginUtil;
        import com.easybasic.eclassbrand.model.*;
        import com.easybasic.eclassbrand.service.*;
        import com.easybasic.kaoqin.controller.FaceAttendanceServiceController;
        import com.github.pagehelper.PageHelper;
        import com.github.pagehelper.PageInfo;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;
        import com.easybasic.basic.model.*;
        import com.easybasic.basic.service.*;
        import org.springframework.web.multipart.MultipartFile;

        import javax.annotation.Resource;
        import javax.annotation.Resource;
        import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
        import java.util.List;
        import java.util.UUID;

@Controller
@RequestMapping("/manage/eclassbrand/examroom")
public class ExamRoomController extends BaseController {
    @Resource
    private ExamRoomService examroomservice;
    @Resource
    private SpaceService spaceService;
    @Resource
    private SpaceTypeService spaceTypeService;
    @Resource
    private UnitUserService unitUserService;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String examroomList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/examroom", false);
        if(!StringUtils.isEmpty(re))
        {
            return "manage/eclassbrand/exam/room/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    public class RoomInfo implements Serializable {
         public  int spaceid;
        public  String spacename;
        public  int pkid;
        public  String name;
        public  int order;
        public  int sortnum;
        public int type;
    }
    @RequestMapping(value = "/getExamRoomListForType", method = RequestMethod.POST)
    @ResponseBody
    public List<RoomInfo> getExamRoomListForType(String typeid)
    {
        List<RoomInfo> roomlist =new ArrayList<RoomInfo>();
        List<Space> spaceList = spaceService.getListBySearch("  typeid="+TypeConverter.strToInt(typeid),"pkid desc");
        for ( Space space :spaceList)
        {
            RoomInfo roominfo=new  RoomInfo();
            roominfo.spaceid=space.getPkid();
            roominfo.spacename=space.getName();
            roominfo.pkid=0; roominfo.name="";
            roominfo. order=0; roominfo. sortnum=0; roominfo.type=1;
            ExamRoom examroom =examroomservice.getBySpaceId (space.getPkid());
            if(examroom!=null)
            {
                roominfo.pkid=examroom.getPkid();
                roominfo.name=examroom.getName();
                roominfo.order=examroom.getOrder();
                roominfo.sortnum=examroom.getSortnum();
                roominfo.type=examroom.getType();
            }
            roomlist.add(roominfo);
        }
        return  roomlist;
    }


    @RequestMapping(value = "/getExamRoomListForSpaceId", method = RequestMethod.POST)
    @ResponseBody
    public List<ExamRoom> getExamRoomListForSpaceId(String spaceTypeId)
    {
        String searchStr = "  pkid>0";
//        if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())//单位管理员
//        {
//            searchStr+=" and userid IN (SELECT userid FROM `basic_unituser` WHERE  unitid="+LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId()+")";
//        }
//        else{//普通老师,所管理的班级
//            searchStr+=" and  userid ="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() ;
//        }
        searchStr+= " and spaceid IN (SELECT pkid FROM  `basic_space` WHERE typeid="+ TypeConverter.strToInt(spaceTypeId)+")";
        List<ExamRoom> list=examroomservice.getListBySearch(searchStr,"sortnum asc");
       for (ExamRoom examroom : list)
        {
            Space space=spaceService.getByPkId(examroom.getSpaceid());
            if(space.getName()!="")
            {
                String name=examroom.getName()+" | "+ space.getName()+"";
                examroom.setName(name);
            }
        }
        return  list;
    }
    @RequestMapping(value = "/getExamRoomListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<ExamRoom> getExamRoomListForPage(JqGridPageRequest pageRequest, Integer unitId, String searchKey)
    {
         String searchStr = "  pkid>0";
    /*    if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())//单位管理员
        {
            searchStr+=" and userid IN (SELECT userid FROM `basic_unituser` WHERE  unitid="+LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId()+")";
        }
        else{//普通老师,所管理的班级
            searchStr+=" and  userid ="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() ;
        }*/
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ExamRoom> list = examroomservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<ExamRoom> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<ExamRoom> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    /**
     * @Description: 新增页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String examroomAdd(Model model)
    {
        List<UnitUser> unitUsers = unitUserService.getListByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        List<SpaceType> spaceTypeList = spaceTypeService.getListByUnitId(unitUsers.get(0).getUnitid());
        model.addAttribute("spaceTypeList",spaceTypeList);
       // List<RoomInfo>  examRoomList=new ArrayList<RoomInfo>();
        int spacetypeid=0;
        if(spaceTypeList.size()>0)
        {
          spacetypeid=spaceTypeList.get(0).getPkid();
            //   examRoomList=getExamRoomListForType(spacetypeid);
        }
        model.addAttribute("spacetypeid",spacetypeid);
   //     model.addAttribute("spaceList",examRoomList);
//        int examroomid=0;
//        if(examRoomList.size()>0)
//        {
//            examroomid=examRoomList.get(0).pkid;
//        }
//        model.addAttribute("examroomid",examroomid);
        return "manage/eclassbrand/exam/room/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int examroomDoAdd(ExamRoom examroom)
    {

        examroom.setCreatetime(new Date());
        examroom.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        examroomservice.insert(examroom);
        setOpLog("eclassbrand","考场", "增加考场：" + examroom.getName());
        return 1;
    }
    /**
     * @Description: 编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String examroomEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                               @RequestParam(value = "spaceid", required = false, defaultValue = "0")String spaceid,
                               Model model)
    {
        ExamRoom examroom =new ExamRoom();
        if(TypeConverter.strToInt(id)==0)
        {   examroom.setPkid(0);
            examroom.setName("");
            examroom.setType(1);
            examroom.setSortnum(1);
            examroom.setOrder(1);
            examroom.setSpaceid(TypeConverter.strToInt(spaceid));
            examroom.setCreatetime(new Date());
            examroom.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
            model.addAttribute("isedit", "0");
        }
        else{
            examroom = examroomservice.getByPkId(TypeConverter.strToInt(id));
            model.addAttribute("isedit", "1");
            if(examroom==null)
            {
                model.addAttribute("msg","要编辑的考场不存在");
                return "/error/error";
            }
        }
        model.addAttribute("examroom", examroom);
        return "manage/eclassbrand/exam/room/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int examroomtDoEdit(ExamRoom examroomData)
    {
        ExamRoom  examroom = new ExamRoom();
        if(examroomData.getPkid()==0)//新增
        {
            examroom.setName(examroomData.getName());
            examroom.setType(examroomData.getType());
            examroom.setSortnum(examroomData.getSortnum());
            examroom.setOrder(examroomData.getOrder());
            examroom.setSpaceid(examroomData.getSpaceid());
            examroom.setCreatetime(new Date());
            examroom.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
            examroomservice.insert(examroom);
            setOpLog("eclassbrand","考场", "增加考场：" + examroom.getName());
            return 1;
        }
        else{//修改
             examroom = examroomservice.getByPkId(examroomData.getPkid());
            if(examroom!=null) {
                examroom.setOrder(examroomData.getOrder());
                examroom.setName(examroomData.getName());
                examroom.setType(examroomData.getType());
                examroom.setSortnum(examroomData.getSortnum());
                examroomservice.update(examroom);
                setOpLog("eclassbrand","考场", "编辑考场：" + examroom.getPkid());
                return 1;
            }
            else{
                return 0;
            }
        }
    }
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int examroomDoDel(int id)
    {
        ExamRoom examroom = examroomservice.getByPkId(id);
        if(examroom!=null) {
            examroomservice.delete(id);
            setOpLog("eclassbrand","考场", "删除考场：" + examroom.getPkid());
        }
        return 1;
    }
}
