package com.easybasic.profile.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.component.jqgrid.JqGridPageRequest;
import com.easybasic.component.jqgrid.JqGridPageResponse;
import com.easybasic.component.jwt.LoginUtil;
import com.easybasic.profile.model.Inbox;
import com.easybasic.profile.model.Outbox;
import com.easybasic.profile.service.InboxService;
import com.easybasic.profile.service.OutboxService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/manage/profile")
public class MessageController extends BaseController {

    @Resource
    private InboxService inboxService;
    @Resource
    private OutboxService outboxService;
    @Resource
    private UserService userService;

    @RequestMapping(value = "/sendMsg", method = RequestMethod.GET)
    public String sendMsg(Model model)
    {
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/sendMsg", false);
        return "/manage/profile/msg/sendmsg";
    }

    @RequestMapping(value = "/doSendMsg", method = RequestMethod.POST)
    @ResponseBody
    public int doSendMsg(String toUserIds, String subject, String content)
    {
        Outbox outbox = new Outbox();
        outbox.setTitle(subject);
        outbox.setContent(content);
        outbox.setUserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
        outbox.setTouserids(toUserIds);
        outbox.setFromtype(0);
        outbox.setCreatetime(new Date());
        outboxService.insert(outbox);
        String[] toUserIdList = toUserIds.split(",");
        for (String s : toUserIdList) {
            int toUserId = TypeConverter.strToInt(s);
            Inbox inbox = new Inbox();
            inbox.setTitle(subject);
            inbox.setContent(content);
            inbox.setType(0);
            inbox.setUserid(toUserId);
            inbox.setFromuserid(LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid());
            inbox.setStatus(0);
            inbox.setCreatetime(new Date());
            inbox.setOutboxid(outbox.getPkid());
            inbox.setReadtime(new Date());
            inbox.setFromtype(0);
            inboxService.insert(inbox);
        }
        setOpLog("profile", "发送消息", "用户（"+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getName()+"）发达邮件“"+subject+"”");
        return 1;
    }

    @RequestMapping(value = "/outbox", method = RequestMethod.GET)
    public String outbox(Model model)
    {
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/outbox", false);
        return "/manage/profile/msg/outbox";
    }

    @RequestMapping(value = "/getOutboxListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Outbox> getOutboxListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = "userid=" + LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid();
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and (title like '%"+searchKey+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Outbox> list = outboxService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for (Outbox outbox : list) {
            User user = userService.getUserForCache(outbox.getUserid());
            outbox.setUserName(user.getName());
        }
        PageInfo<Outbox> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Outbox> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/doDelOutbox", method = RequestMethod.POST)
    @ResponseBody
    public int doDelOutbox(String ids)
    {
        String[] idList = ids.split(",");
        for (String s : idList) {
             Outbox outbox = outboxService.getByPkId(TypeConverter.strToInt(s));
             if(outbox!=null)
             {
                 outboxService.delete(outbox.getPkid());
                 setOpLog("profile", "已发消息", "用户（"+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getName()+"）删除已发消息“"+outbox.getTitle()+"”");
             }
        }
        return 1;
    }

    @RequestMapping(value = "/viewOubBox", method = RequestMethod.GET)
    public String viewOutbox(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                             Model model)
    {
        Outbox outbox = outboxService.getByPkId(TypeConverter.strToInt(id));
        if(outbox==null)
        {
            model.addAttribute("msg","已发送消息内容为空");
            return "/error/error";
        }
        model.addAttribute("outbox", outbox);
        String[] toUserIdList = outbox.getTouserids().split(",");
        List<String> toUserNameList = new ArrayList<>();
        for (String s : toUserIdList) {
            User user = userService.getUserForCache(TypeConverter.strToInt(s));
            if(user!=null)
            {
                toUserNameList.add(user.getName());
            }
        }
        model.addAttribute("toUserNames", String.join("，", toUserNameList));
        return "/manage/profile/msg/viewoutbox";
    }

    @RequestMapping(value = "/inbox", method = RequestMethod.GET)
    public String inbox(Model model)
    {
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/inbox", false);
        return "/manage/profile/msg/inbox";
    }

    @RequestMapping(value = "/getInboxListForPage", method = RequestMethod.POST)
    @ResponseBody
    public JqGridPageResponse<Inbox> getInboxListForPage(JqGridPageRequest pageRequest, String searchKey)
    {
        String searchStr = "userid=" + LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid();
        if(!StringUtils.isEmpty(searchKey))
        {
            searchStr += " and (title like '%"+searchKey+"%')";
        }
        PageHelper.startPage(pageRequest.getPage(), pageRequest.getRows());
        List<Inbox> list = inboxService.getListBySearch(searchStr,pageRequest.getSidx() +" " + pageRequest.getSord());
        for (Inbox inbox : list) {
            User user = userService.getUserForCache(inbox.getFromuserid());
            inbox.setFromUserName(user.getName());
        }
        PageInfo<Inbox> pageInfo = new PageInfo<>(list);
        JqGridPageResponse<Inbox> response = new JqGridPageResponse<>();
        response.setTotal(pageInfo.getPages());
        response.setPage(pageInfo.getPageNum());
        response.setRecords(pageInfo.getTotal());
        response.setRows(pageInfo.getList());
        return response;
    }

    @RequestMapping(value = "/doDelInbox", method = RequestMethod.POST)
    @ResponseBody
    public int doDelInbox(String ids)
    {
        String[] idList = ids.split(",");
        for (String s : idList) {
            Inbox inbox = inboxService.getByPkId(TypeConverter.strToInt(s));
            if(inbox!=null)
            {
                inboxService.delete(inbox.getPkid());
                setOpLog("profile", "收件箱", "用户（"+LoginUtil.getCurrentLoginUserProperty().CurrentUser.getName()+"）删除收件箱消息“"+inbox.getTitle()+"”");
            }
        }
        return 1;
    }

    @RequestMapping(value = "/viewMsg", method = RequestMethod.GET)
    public String viewMsg(@RequestParam(value = "id", required = false, defaultValue = "0")String id,
                             Model model)
    {
        setPageCommonModelForNoAuth(model, "profile","/manage/profile/inbox", false);
        Inbox inbox = inboxService.getByPkId(TypeConverter.strToInt(id));
        if(inbox==null)
        {
            model.addAttribute("msg","收件箱消息内容为空");
            return "/error/error";
        }
        model.addAttribute("inbox", inbox);
        User user =userService.getUserForCache(inbox.getFromuserid());
        model.addAttribute("fromUserName", user.getName());
        inbox.setStatus(1);
        inbox.setReadtime(new Date());
        inboxService.update(inbox);
        return "/manage/profile/msg/viewmsg";
    }

    @RequestMapping(value = "/getUnReadMsgList", method = RequestMethod.POST)
    @ResponseBody
    public List<Inbox> getUnReadMsgList()
    {
        String searchStr = "userid=" + LoginUtil.getCurrentLoginUserProperty().CurrentUser.getPkid() + " and status=0";
        List<Inbox> list = inboxService.getListBySearch(searchStr,"pkid desc");
        for (Inbox inbox : list) {
            User user = userService.getUserForCache(inbox.getFromuserid());
            inbox.setFromUserName(user.getName());
        }
        return list;
    }
}
