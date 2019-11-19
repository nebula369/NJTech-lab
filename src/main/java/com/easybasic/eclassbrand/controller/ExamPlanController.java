package com.easybasic.eclassbrand.controller;


        import com.easybasic.basic.model.*;
        import com.easybasic.basic.service.*;
        import com.easybasic.component.BaseController;
        import com.easybasic.component.Utils.ToolsUtil;
        import com.easybasic.component.Utils.TypeConverter;
        import com.easybasic.component.Utils.ZipUtil;
        import com.easybasic.component.handler.ImageHandler;
        import com.easybasic.component.jqgrid.JqGridPageRequest;
        import com.easybasic.component.jqgrid.JqGridPageResponse;
        import com.easybasic.component.jwt.LoginUtil;
        import com.easybasic.kaoqin.model.UserPhoto;
        import com.easybasic.kaoqin.service.UserPhotoService;
        import com.github.pagehelper.PageHelper;
        import com.github.pagehelper.PageInfo;
        import org.apache.commons.fileupload.disk.DiskFileItem;
        import org.apache.commons.io.FileUtils;
        import org.apache.poi.ss.usermodel.Cell;
        import org.apache.poi.ss.usermodel.CellStyle;
        import org.apache.poi.ss.usermodel.DataFormatter;
        import org.apache.poi.xssf.usermodel.XSSFCell;
        import org.apache.poi.xssf.usermodel.XSSFRow;
        import org.apache.poi.xssf.usermodel.XSSFSheet;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;
        import com.alibaba.druid.util.StringUtils;
        import javax.annotation.Resource;
        import java.io.*;
        import java.text.DecimalFormat;
        import java.text.SimpleDateFormat;
        import java.util.*;
        import java.util.stream.Collectors;
        import com.easybasic.eclassbrand.model.*;
        import com.easybasic.eclassbrand.service.*;
        import org.springframework.web.multipart.MultipartFile;
        import org.springframework.web.multipart.MultipartHttpServletRequest;
        import org.springframework.web.multipart.commons.CommonsMultipartFile;

        import javax.servlet.ServletContext;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

        import static com.easybasic.component.Utils.ToolsUtil.getFileNameWithoutExt;

@Controller
@RequestMapping("/manage/eclassbrand/examplan")
public class ExamPlanController extends BaseController {
    @Resource
    private UnitService unitService;
    @Resource
    private ExamPlanService examplanservice;
    @Resource
    private ExamRoomService examroomservice;
    @Resource
    private SpaceService spaceService;
    @Resource
    private SpaceTypeService spaceTypeService;
    @Resource
    private UnitUserService unitUserService;
    @Resource
    private ExamineeService examineeService;
    @Resource
    private ExamPlanMineeService examPlanMineeService;
    @Resource
    private UserPhotoService userPhotoService;
    @Resource
    private  UserService userService;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String examplanList(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/examplan", false);
        if(!StringUtils.isEmpty(re))
        {
            List<UnitUser> unitUsers = unitUserService.getListByUserId(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
            List<SpaceType> spaceTypeList = spaceTypeService.getListByUnitId(unitUsers.get(0).getUnitid());
            model.addAttribute("spaceTypeList",spaceTypeList);
            List<ExamRoom> spaceList=new ArrayList<ExamRoom>();
            int spacetypeid=0;
            if(spaceTypeList.size()>0)
            {
                spacetypeid=spaceTypeList.get(0).getPkid();
                String searchStr = "  pkid>0";
              /*  if(LoginUtil.getCurrentLoginUserProperty().isUnitManager())//单位管理员
                {
                    searchStr+=" and userid IN (SELECT userid FROM `basic_unituser` WHERE  unitid="+LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId()+")";
                }
                else{//普通老师,所管理的班级
                    searchStr+=" and  userid ="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() ;
                }*/
                searchStr+= " and spaceid IN (SELECT pkid FROM  `basic_space` WHERE typeid="+spacetypeid+")";
                spaceList= examroomservice.getListBySearch(searchStr,"sortnum asc");
                for (ExamRoom examroom : spaceList)
                {
                    Space space=spaceService.getByPkId(examroom.getSpaceid());
                    if(space.getName()!="")
                    {
                        String name=examroom.getName()+" | "+ space.getName()+"";
                        examroom.setName(name);
                    }
                }
            }
            model.addAttribute("spaceList",spaceList);
            int examroomid=0;
            if(spaceList.size()>0)
            {
                examroomid=spaceList.get(0).getPkid();
            }
            model.addAttribute("spacetypeid",spacetypeid);
            model.addAttribute("examroomid",examroomid);
            return "manage/eclassbrand/exam/plan/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }

    @RequestMapping(value = "/getExamPlanListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<ExamPlan> getExamPlanListForPage(JqGridPageRequest pageRequest, Integer examRoomId, String searchKey)
    {
        String searchStr = "examRoomId=" + examRoomId;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and subject like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<ExamPlan> list = examplanservice.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for (ExamPlan examplan: list)
        {
            examplan.setExamDate(TypeConverter.dateToString(examplan.getStarttime(),"MM月dd日"));
            examplan.setExamTime(TypeConverter.dateToString(examplan.getStarttime(),"HH:mm")+"--"+TypeConverter.dateToString(examplan.getEndtime(),"HH:mm"));
            List<ExamPlanMinee> examplanmineeList=examPlanMineeService.getListBySearch(" examplanid="+examplan.getPkid()," pkid desc");
            examplan.setUsercount(examplanmineeList.size());
        }
        PageInfo<ExamPlan> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<ExamPlan> response = new JqGridPageResponse<>();
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
    public String examplanAdd(@RequestParam(value = "examroomid", required = false, defaultValue = "0")String examroomid,Model model)
    {
        model.addAttribute("examroomid", examroomid);
        return "manage/eclassbrand/exam/plan/add";
    }

    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int examplanDoAdd(ExamPlan examplan,String startDate,String endDate )
    {
        examplan.setStarttime(TypeConverter.stringToDate(startDate,"yyyy-MM-dd HH:mm:ss"));
        examplan.setEndtime(TypeConverter.stringToDate(endDate,"yyyy-MM-dd HH:mm:ss"));
        examplan.setCreatetime(new Date());
        examplan.setIsimport(1);
        examplan.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        examplanservice.insert(examplan);
        setOpLog("eclassbrand","考场计划", "增加考场计划：" + examplan.getPkid());
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
    public String examplanEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        ExamPlan examplan = examplanservice.getByPkId(TypeConverter.strToInt(id));
        if(examplan==null)
        {
            model.addAttribute("msg","要编辑的网址不存在");
            return "/error/error";
        }
        model.addAttribute("examplan", examplan);
        model.addAttribute("startDate", TypeConverter.dateToString(examplan.getStarttime(),"yyyy-MM-dd HH:mm:ss"));
        model.addAttribute("endDate", TypeConverter.dateToString(examplan.getEndtime(),"yyyy-MM-dd HH:mm:ss"));
        return "manage/eclassbrand/exam/plan/edit";
    }

    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int examplanDoEdit(ExamPlan examplanData,String startDate,String endDate )
    {
        ExamPlan examplan = examplanservice.getByPkId(examplanData.getPkid());
        if(examplan!=null) {
            examplan.setStarttime(TypeConverter.stringToDate(startDate,"yyyy-MM-dd HH:mm:ss"));
            examplan.setEndtime(TypeConverter.stringToDate(endDate,"yyyy-MM-dd HH:mm:ss"));
            examplan.setSubject(examplanData.getSubject());
            examplan.setGrade(examplanData.getGrade());
            examplan.setExaminer(examplanData.getExaminer());
            examplan.setSortnum(examplanData.getSortnum());
            examplan.setTicket(examplanData.getTicket());
            examplanservice.update(examplan);
            setOpLog("eclassbrand","考场计划", "编辑考场计划：" + examplan.getPkid());
        }
        return 1;
    }

    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int  examplanDoDel(String ids)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
            ExamPlan examplan = examplanservice.getByPkId(id);
            if(examplan!=null) {
                examplanservice.delete(id);
                setOpLog("eclassbrand","考场计划", "删除考场计划：" + examplan.getPkid());
            }
        }
        return 1;
    }

    /**
     * @Description:
     * @param: [id, model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 17:02
     */
    @RequestMapping(value = "/relationuser", method = RequestMethod.GET)
    public String RelationUser(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        ExamPlan examplan  = examplanservice.getByPkId(TypeConverter.strToInt(id));
        if(examplan==null)
        {
            model.addAttribute("msg","考场计划不存在");
            return "/error/error";
        }
        model.addAttribute("examplanId", examplan.getPkid());
        return "manage/eclassbrand/exam/plan/relationuser";
    }

    /**
     * @Description://获取考生信息
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/getUserListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Examinee> getUserListForPage(JqGridPageRequest pageRequest, String examPlanId, String searchKey)
    {
        String searchStr = "  pkid in (select examineeid from  exam_examplanminee where examplanid="+TypeConverter.strToInt(examPlanId)+") " ;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Examinee> list = examineeService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<Examinee> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Examinee> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }
    //关联考生
    @RequestMapping(value = "/DoExamPlanMineeAdd", method = RequestMethod.POST)
    @ResponseBody
    public int examplanmineeAdd(String ids, int examPlanId)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
           User user=userService.getByUserId(id);
           UserPhoto userPhoto=userPhotoService.getByUserId(user.getPkid());
           //添加考生
           addExaminee("type=1 and studentid="+id,examPlanId,user.getPkid(),user.getSex(),user.getName(),"",userPhoto.getPhoto(),1);
        }
        return 1;
    }
    //删除考生
    @RequestMapping(value = "/DoExamPlanMineeDel", method = RequestMethod.POST)
    @ResponseBody
    public int DoExamPlanMineeDel(String ids, int examPlanId)
    {
        String[] idStrList = ids.split(",");
        for(String idStr : idStrList)
        {
            int id = TypeConverter.strToInt(idStr);
            if(id == 0)continue;
            List<Examinee> examineelist = examineeService.getListBySearch(" pkid="+id,"pkid desc");
            if(examineelist.size()>0)//添加
            {
                int examineeid=examineelist.get(0).getPkid();
                //删除考场计划与考生信息关联
                ExamPlanMinee  examplanminee =examPlanMineeService.getByPlanIdAndMineeId(examPlanId,examineeid);
                if(examplanminee!=null)
                {
                    examPlanMineeService.delete(examplanminee.getPkid());
                    setOpLog("eclassbrand","考生信息", "删除考场计划与考生信息关联：" + examplanminee.getPkid());
                }
                //删除考生信息
                List<ExamPlanMinee> examPlanMineelist = examPlanMineeService.getListBySearch("  examineeid="+examineeid,"pkid desc");
                if(examPlanMineelist.size()==0)
                {
                    Examinee examinee=examineeService.getByPkId(examineeid);
                    if(examinee.getPhoto()!=null)//删除照片
                    {
                        String filepath= ToolsUtil.getUploadPath("/"+examinee.getPhoto());
                        ToolsUtil.deleteFile(filepath);
                    }
                    examineeService.delete(examineeid);
                    setOpLog("eclassbrand","考生信息", "删除考生信息：" + examplanminee.getPkid());
                }
            }
        }
        return 1;
    }

    /**
     * @Description:
     * @param: [id, model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 17:02
     */
    @RequestMapping(value = "/examimport", method = RequestMethod.GET)
    public String examimport(@RequestParam(value = "examroomid", required = false, defaultValue = "0")String examroomid, Model model)
    {
        ExamRoom examroom  = examroomservice.getByPkId(TypeConverter.strToInt(examroomid));
        if(examroom==null)
        {
            model.addAttribute("msg","考场不存在");
            return "/error/error";
        }
        model.addAttribute("examroomId", examroom.getPkid());
        return "manage/eclassbrand/exam/plan/import/examimport";
    }


    /**
     * 导入考试计划
     * @param file
     * @param examroomId
     * @return
     * @throws
     */
    @RequestMapping (value = "/importExamPlan", method = RequestMethod.POST)
    @ResponseBody
    public String importExamPlan(@RequestParam MultipartFile file,String examroomId) throws IOException{

        XSSFWorkbook   workbook =null;
        //创建Excel，读取文件内容
         workbook = new XSSFWorkbook(file.getInputStream());
        //获取第一个工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //获取sheet中第一行行号
        int firstRowNum = sheet.getFirstRowNum();
        //获取sheet中最后一行行号
        int lastRowNum = sheet.getLastRowNum();
        try {
            //循环插入数据
            for(int i=firstRowNum+1;i<=lastRowNum;i++){
                XSSFRow row = sheet.getRow(i);
                ExamPlan examplan = new ExamPlan();
                examplan.setIsimport(2);//excel数据导入
                examplan.setExamroomid(TypeConverter.strToInt(examroomId));//考场编号
                examplan.setCreatetime(new Date());//创建时间
                examplan.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());//创建用户
                examplan.setSortnum(i);//排序

                XSSFCell starttime = row.getCell(0);  //考试开始时间
                if(starttime!=null){
                    starttime.setCellType(Cell.CELL_TYPE_NUMERIC);
                    double value = starttime.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    if(date!=null)
                    {
                        examplan.setStarttime(date);
                    }
                    else{
                        continue;
                    }
                }
                else{
                    continue;
                }

                XSSFCell endtime = row.getCell(1);//考试结束时间
                if(endtime!=null){
                    endtime.setCellType(Cell.CELL_TYPE_NUMERIC);
                    double value = endtime.getNumericCellValue();
                    Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    if(date!=null)
                    {
                        examplan.setEndtime(date);
                    }
                    else{
                        continue;
                    }
                }  else{
                    continue;
                }

                XSSFCell subject = row.getCell(2);//考试科目
                if(subject!=null){
                    subject.setCellType(Cell.CELL_TYPE_STRING);
                    examplan.setSubject(subject.getStringCellValue());
                }  else{
                    continue;
                }

                XSSFCell grade = row.getCell(3);//年级专业
                if(grade!=null){
                    grade.setCellType(Cell.CELL_TYPE_STRING);
                    examplan.setGrade(grade.getStringCellValue());
                }  else{
                    continue;
                }

                XSSFCell examiner = row.getCell(4);//监考人员
                if(examiner!=null){
                    examiner.setCellType(Cell.CELL_TYPE_STRING);
                    examplan.setExaminer(examiner.getStringCellValue());
                }  else{
                    continue;
                }

                XSSFCell usercount = row.getCell(5);//人数
                if(usercount!=null){
                    usercount.setCellType(Cell.CELL_TYPE_STRING);
                    examplan.setUsercount(TypeConverter.strToInt(usercount.getStringCellValue()));
                }
                XSSFCell ticket = row.getCell(6);//准考证起止号码
                if(ticket!=null){
                    ticket.setCellType(Cell.CELL_TYPE_STRING);
                    examplan.setTicket(ticket.getStringCellValue());
                }
                examplanservice.insert(examplan);//往数据库插入数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
        return "";
    }

    @RequestMapping(value = "/examineeimport", method = RequestMethod.GET)
    public String examineeimport(
            @RequestParam(value = "examroomid", required = false, defaultValue = "0")String examroomid,
            @RequestParam(value = "ids", required = false, defaultValue = "0")String ids, Model model)
    {
        ExamRoom examroom  = examroomservice.getByPkId(TypeConverter.strToInt(examroomid));
        if(examroom==null)
        {
            model.addAttribute("msg","考场不存在");
            return "/error/error";
        }
        model.addAttribute("examroom", examroom);
        model.addAttribute("examplanids", ids);
        return "manage/eclassbrand/exam/plan/import/examineeimport";
    }
    /**
     * @Description: 方法是上传头像包
     * @param: 
     * @return: 
     * @auther: tangy
     * @date: 2019/6/14 0014 15:41
     */
    @RequestMapping (value = "/importExamUserPhotoPack", method = RequestMethod.POST)
    @ResponseBody
    public String importExamUserPhotoPack(MultipartFile file,String examplanids)
    {
        int code=0;String msg="上传成功";
        try{
            for (String examPlanId:examplanids.split(","))
            {
                int examplanid=TypeConverter.strToInt(examPlanId);
                String savepackpath = ToolsUtil.getUploadPath("/upload/exam/userphotopack/"+examplanid+"/");
                //   String fileName =TypeConverter.dateToString(new Date(),"yyyyMMddHHmmss");//保存的年月日
                File targetFile = new File(savepackpath, file.getOriginalFilename());
                if(!targetFile.getParentFile().exists()){
                    targetFile.getParentFile().mkdirs();
                }
                try {//保存文件
                    file.transferTo(targetFile);
                    String  userphoto="upload/exam/userphoto/"+examplanid+"/";
                    String photopath = ToolsUtil.getUploadPath("/"+userphoto);
                    new  ZipUtil().unZip(savepackpath+file.getOriginalFilename(),photopath,false);//解压文件
                    //遍历相册文件
                    File photofile = new File(photopath);
                    if (photofile.exists()) {
                        File[] fileslist = photofile.listFiles();//获取文件列表
                        if (fileslist.length == 0) {
                            code=1;
                            msg= "文件夹是空的";
                            continue;
                        } else {
                            for (File fileitem : fileslist) {
                                if (!fileitem.isDirectory()) {//添加
                                 String filenamewithoutext=getFileNameWithoutExt(fileitem);
                                int  result= addExamUserPhoto(filenamewithoutext,examPlanId,userphoto+fileitem.getName());
                                if(result==0)
                                {
                                    msg += "学员:"+filenamewithoutext+"照片命名错误;";
                                }
                             }
                          }
                        }
                    } else {
                        code=1;
                        msg= "文件不存在，解压文件失败";
                        continue;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception ex)
        {
            code=1;msg= "上传失败！异常报错";
        }
         return "{\"code\":\""+code+"\",\"msg\":\""+msg+"\"}";
    }
    @RequestMapping (value = "/addExamUserPhoto", method = RequestMethod.POST)
    @ResponseBody
    public  int  addExamUserPhoto(String filename,String examplanid,String userphoto)
    {
        String [] fileitemName=filename.split("_");
        if(fileitemName.length>0)
        {
            String number="";//获取学号
            String username="";//姓名
            int sex=2;//性别
            for(int i=0;i<fileitemName.length;i++){
                if(i==0)
                {
                    number=fileitemName[0];//获取学号
                }
                if(i==1)
                {
                    username=fileitemName[1];//获取姓名
                }
                if(i==2)
                {
                    if(fileitemName[2]=="男")
                    {
                        sex=1;
                    }
                }
           }
            if(number!=""&&username!="")
            {
                addExaminee(" type=2 and number='"+number+"'",TypeConverter.strToInt(examplanid) ,0,sex,username,number,userphoto,2);
                return 1;
            }
            return 0;
        }
        else {
              return 0;
        }
    }
    private  void  addExaminee(String sql,int examPlanId,int userId,int sex,String userName,String number,String userPhoto,int type)
    {
        if(userId!=0||number!="")
        {
            //   List<Examinee> examineelist = examineeService.getListBySearch(" type=1 and studentid="+id,"pkid desc");
            List<Examinee> examineelist = examineeService.getListBySearch(sql,"pkid desc");
            int examineeid=0;
            if(examineelist.size()==0)//添加
            {
                Examinee examinee =new Examinee();
                examinee.setType(type);//基础库
                examinee.setStudentid(userId);
                examinee.setSex(sex);
                examinee.setName(userName);
                examinee.setNumber(number);
                examinee.setPhoto(userPhoto);
                examinee.setCreatetime(new Date());
                examineeService.insert(examinee);
                setOpLog("eclassbrand","考生信息", "新增考生信息：" + examinee.getPkid());
                examineeid=examinee.getPkid();
            }
            if (examineeid==0)//获取用户关联表
            {
                examineeid=examineelist.get(0).getPkid();
            }
            ExamPlanMinee  examplanminee =examPlanMineeService.getByPlanIdAndMineeId(examPlanId,examineeid);
            if(examplanminee==null)
            {
                examplanminee=new ExamPlanMinee();
                examplanminee.setExamineeid(examineeid);
                examplanminee.setExamplanid(examPlanId);
                examPlanMineeService.insert(examplanminee);
                setOpLog("eclassbrand","考生信息", "新增考试计划信息与考生信息关联：" + examplanminee.getPkid());
            }
        }
    }
}
