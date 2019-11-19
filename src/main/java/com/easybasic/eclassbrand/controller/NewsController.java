package com.easybasic.eclassbrand.controller;


import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.SchoolClass;
import com.easybasic.basic.model.Unit;
import com.easybasic.basic.service.SchoolClassService;
import com.easybasic.basic.service.UnitService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.eclassbrand.model.News;
import com.easybasic.eclassbrand.model.ClassNews;
import com.easybasic.eclassbrand.model.NewsType;
import com.easybasic.eclassbrand.service.ClassNewsService;
import com.easybasic.eclassbrand.service.NewsService;
import com.easybasic.eclassbrand.service.NewsTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/eclassbrand/news")
public class NewsController extends BaseController {
    @Resource
    private UnitService unitService;
    @Resource
    private NewsService newsservice;
    @Resource
    private ClassNewsService classnewsservice;
    @Resource
    private NewsTypeService newstypeservice;
    @Resource
    private SchoolClassService schoolClassService;
    /**
     * @Description: 网址列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String List(Model model)
    {
        String re = setPageCommonModel(model, "eclassbrand","/manage/eclassbrand/News", false);
        if(!StringUtils.isEmpty(re))
        {
            List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
            initUnitList(list);
            model.addAttribute("unitList", list);
            return "manage/eclassbrand/news/list";
        }
        else {
            model.addAttribute("msg","您没有权限访问该页面");
            return "/error/error";
        }
    }



    @RequestMapping(value = "/getNewsListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<News> getNewsListForPage(JqGridPageRequest pageRequest,Integer newstypeid, String searchKey)
    {
        String searchStr = " newstype = "+newstypeid;
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and title like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<News> list = newsservice.getNewsListForPage(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        PageInfo<News> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<News> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    /**
     * @Description: 新增新闻页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/newsadd", method = RequestMethod.GET)
    public String newsAdd(Model model,@RequestParam(value = "newstypeid", required = false, defaultValue = "0")String newstypeid)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("newstypeid",  newstypeid);
        return "manage/eclassbrand/news/newsadd";
    }
    @RequestMapping(value = "/doAdd", method = RequestMethod.POST)
    @ResponseBody
    public int newsDoAdd(Integer newsType,String intro,String title,String author,Integer sortnum,String content,String photo) throws ParseException {
        News activity=new News();
        activity.setContent(content);
        activity.setTitle(title);
        activity.setAuthor(author);
        activity.setCreatetime(new Date());
        activity.setIsschool(0);
        activity.setNewstype(newsType);
        activity.setIntro(intro);
        activity.setSortnum(sortnum);
        activity.setPhoto(photo);

        newsservice.insert(activity);

        setOpLog("eclassbrand","新闻名称", "增加新闻名称：" + activity.getTitle());
        return 1;
    }
    /**
     * @Description: 编辑页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/newsedit", method = RequestMethod.GET)
    public String newsEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        News news = newsservice.getByPkId(TypeConverter.strToInt(id));
        if(news==null)
        {
            model.addAttribute("msg","要编辑的新闻不存在");
            return "/error/error";
        }

        model.addAttribute("newstypeid",  news.getNewstype());
        model.addAttribute("news", news);
        return "manage/eclassbrand/news/newsedit";
    }
    @RequestMapping(value = "/doEdit", method = RequestMethod.POST)
    @ResponseBody
    public int newsDoEdit(Integer pkid,Integer newsType,String intro,String title,String author,Integer sortnum,String content,String photo) throws ParseException {
        News website = newsservice.getByPkId(pkid);
        if (website != null) {
            website.setAuthor(author);
            website.setTitle(title);
            website.setContent(content);
            website.setSortnum(sortnum);
            website.setIntro(intro);
            website.setNewstype(newsType);
            website.setPhoto(photo);

            newsservice.update(website);

            setOpLog("eclassbrand", "新闻内容", "编辑新闻内容：" + website.getTitle());
        }
        return 1;
    }
    @RequestMapping(value = "/doDel", method = RequestMethod.POST)
    @ResponseBody
    public int newsDoDel(String ids) {
        if (ids == "" || ids == null) {
            return 1;
        }
        String[] idstr = ids.split(",");
        for (String id : idstr) {
            News website = newsservice.getByPkId(Integer.parseInt(id));
            if (website != null) {
                newsservice.delete(Integer.parseInt(id));
                setOpLog("eclassbrand", "新闻", "删除新闻：" + website.getTitle());
            }
        }
        return 1;
    }

    /**
     * @Description: 获取班级
     * @param: []
     * @return: java.util.List<com.easybasic.basic.model.SchoolClass>
     * @auther: tangy
     * @date: 2019/5/28 0028 8:59
     */
    @RequestMapping(value = "/getSchoolClassList", method = RequestMethod.POST)
    @ResponseBody
    public  List<SchoolClass>  getSchoolClass()
    {
        String searchStr="pkid IN (SELECT  classid FROM  `edu_schoolclasscurriculum`  WHERE teacherid="+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid()+")";
        List<SchoolClass> classlist =  schoolClassService.getListBySearch(searchStr,"pkid desc");
        return classlist;
    }

    @RequestMapping(value = "/getNewsTypeList", method = RequestMethod.POST)
    @ResponseBody
    public  List<NewsType>  getNewsTypeList()
    {
        String searchStr="";
        List<NewsType> classlist =  newstypeservice.getNewsListForPage(searchStr,"pkid desc");
        return classlist;
    }


    /**
     * @Description: 新闻列表页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/newslist", method = RequestMethod.GET)
    public String NewsList(@RequestParam(value = "newstypeid", required = false, defaultValue = "0")Integer newstypeid, Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("newstypeid", newstypeid);
        return "manage/eclassbrand/news/newslist";
    }

    /**
     * @Description: 编辑分类页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/typeedit", method = RequestMethod.GET)
    public String TypeEdit(@RequestParam(value = "id", required = false, defaultValue = "0")String id, Model model)
    {
        NewsType news = newstypeservice.getByPkId(TypeConverter.strToInt(id));
        if(news==null)
        {
            model.addAttribute("msg","要编辑的新闻分类不存在");
            return "/error/error";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("newstype", news);
        model.addAttribute("classList",  getSchoolClass());
        return "manage/eclassbrand/news/typeedit";
    }

    /**
     * @Description: 新增分类页面
     * @param: [model]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/5/21 0021 15:43
     */
    @RequestMapping(value = "/typeadd", method = RequestMethod.GET)
    public String newsTypeAdd(Model model)
    {
        List<Unit> list = unitService.getOrderedUnitList(LoginUtil.getCurrentLoginUserProperty().getCurrentUserManageUnitId());
        initUnitList(list);
        model.addAttribute("classList",  getSchoolClass());
        model.addAttribute("typelist",  getSchoolClass());
        return "manage/eclassbrand/news/typeadd";
    }

    @RequestMapping(value = "/doTypeAdd", method = RequestMethod.POST)
    @ResponseBody
    public int newsTypeDoAdd(Integer classid,Integer isSchool,String name) throws ParseException {
        NewsType activity = new NewsType();
        activity.setClassid(classid);
        activity.setIsschool(isSchool);
        activity.setName(name);
        activity.setCreatetime(new Date());
        activity.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());

        newstypeservice.insert(activity);
        setOpLog("eclassbrand", "新闻分类名称", "增加新闻分类名称：" + activity.getName());
        return 1;
    }

    @RequestMapping(value = "/doTypeEdit", method = RequestMethod.POST)
    @ResponseBody
    public int newsTypeDoEdit(Integer pkid,Integer classid,Integer isSchool,String name) throws ParseException {
        NewsType newstype = newstypeservice.getByPkId(pkid);
        if (newstype != null) {
            newstype.setClassid(classid);
            newstype.setIsschool(isSchool);
            newstype.setName(name);

            newstypeservice.update(newstype);
            setOpLog("eclassbrand", "新闻分类内容", "编辑新闻分类内容：" + newstype.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/doTypeDel", method = RequestMethod.POST)
    @ResponseBody
    public int newsTypeDoDel(int id)
    {
        NewsType website = newstypeservice.getByPkId(id);
        if(website!=null) {
            newstypeservice.delete(id);
            setOpLog("eclassbrand","新闻分类", "删除新闻分类：" + website.getName());
        }
        return 1;
    }

    @RequestMapping(value = "/getNewsTypeListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<NewsType> getNewsTypeListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = " pkid >0 ";
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and name like '%"+searchKey+"%'";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<NewsType> list = newstypeservice.getNewsListForPage(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for(NewsType honorcategory: list)
        {
            String classNams=schoolClassService.getByPkId(honorcategory.getClassid()).getName();
            honorcategory.setClassName(classNams);
        }
        PageInfo<NewsType> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<NewsType> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }
}
