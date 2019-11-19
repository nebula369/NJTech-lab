package com.easybasic.service.controller;

import com.easybasic.basic.model.OpLog;
import com.easybasic.basic.model.ThirdApp;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.OpLogService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.ServiceController;
import com.easybasic.component.Utils.JsonUtils;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import com.easybasic.profile.model.Inbox;
import com.easybasic.profile.model.Outbox;
import com.easybasic.profile.service.InboxService;
import com.easybasic.profile.service.OutboxService;
import com.easybasic.service.dto.ResponseCode;
import com.easybasic.service.dto.ResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Controller
@RequestMapping("/service")
public class CommonServiceController extends ServiceController {

    @Resource
    private OpLogService opLogService;
    @Resource
    private InboxService inboxService;
    @Resource
    private OutboxService outboxService;
    @Resource
    private UserService userService;

    /**
     * 设置用户操作日志
     * @param appKey 第三方应用唯一标识
     * @param auth MD5(appSecret+appKey)
     * @param opModule 操作模块
     * @param opRemark 操作日志详细
     * @param opIp 日志操作者IP
     * @param opUser 日志操作者（登录名）
     * @return
     */
    @RequestMapping(value = "/opLog/setOpLog", method = RequestMethod.POST)
    public ResponseDTO setOpLog(String appKey, String auth, String opModule, String opRemark, String opIp, String opUser)
    {
        try
        {
            User user = userService.getUserForCache(opUser);
            ResponseDTO responseDTO = checkThirdAppValidate(appKey);
            if(responseDTO.getCode() != 1000) return responseDTO;
            ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
            String validateAuth = ToolsUtil.getMd5(thirdApp.getAppsecret() + thirdApp.getAppkey());
            if(!auth.equalsIgnoreCase(validateAuth))
            {
                return ResponseCode.buildResponseData(ResponseCode.USERID_NOT_NULL, null);
            }
            OpLog opLog = new OpLog();
            opLog.setType(1);
            opLog.setCode(appKey);
            opLog.setOpapp(thirdApp.getName());
            opLog.setOpmodule(opModule);
            opLog.setOpremark(opRemark);
            opLog.setOpip(opIp);
            opLog.setUserid(user.getPkid());
            opLog.setUsername(opUser);
            opLog.setCreatetime(new Date());
            opLog.setStatus(1);
            opLogService.insert(opLog);
            return ResponseCode.buildResponseData(ResponseCode.RESPONSE_CODE_SUCCESS, null);
        }
        catch(Exception ex)
        {
            logger.error("调用服务“setOpLog”异常", ex);
            return ResponseCode.buildResponseData(ResponseCode.EXCEPTION, null);
        }
    }

    /**
     * 发送站内消息
     * @param appKey 第三方应用唯一标识
     * @param auth MD5(appSecret+appKey)
     * @param type 消息类型：0为站内用户间发送；1为系统消息发送
     * @param fromUserName 发件人用户名
     * @param toUserNames 收件人用户名列表，多个用,分隔
     * @param subject 消息主题
     * @param content 消息内容
     * @return
     */
    @RequestMapping(value = "/msg/sendMsg", method = RequestMethod.POST)
    public ResponseDTO sendMsg(String appKey, String auth, int type, String fromUserName, String toUserNames, String subject, String content)
    {
        try
        {
            User fromUser = userService.getUserForCache(fromUserName);
            if(fromUser==null)
            {
                return ResponseCode.buildResponseData(ResponseCode.MSGSEND_FROMUSER_NOT_EXIST, null);
            }
            ResponseDTO responseDTO = checkThirdAppValidate(appKey);
            if(responseDTO.getCode() != 1000) return responseDTO;
            ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
            String validateAuth = ToolsUtil.getMd5(thirdApp.getAppsecret() + thirdApp.getAppkey());
            if(!auth.equalsIgnoreCase(validateAuth))
            {
                return ResponseCode.buildResponseData(ResponseCode.USERID_NOT_NULL, null);
            }
            boolean isHaveToUserNotExist = false;
            List<String> toUserIdList = new ArrayList<>();
            String[] toUserNameList = toUserNames.split(",");
            for (String s : toUserNameList) {
                User user = userService.getUserForCache(s.trim());
                if(user!=null)
                {
                    toUserIdList.add(user.getPkid().toString());
                }
                else
                {
                    isHaveToUserNotExist = true;
                }
            }
            int outboxId = 0;
            if(type==0) {
                Outbox outbox = new Outbox();
                outbox.setTitle(subject);
                outbox.setContent(content);
                outbox.setUserid(fromUser.getPkid());
                outbox.setTouserids(String.join(",", toUserIdList));
                outbox.setFromtype(1);
                outbox.setCreatetime(new Date());
                outboxService.insert(outbox);
                outboxId = outbox.getPkid();
            }
            for (String s : toUserIdList) {
                int toUserId = TypeConverter.strToInt(s);
                Inbox inbox = new Inbox();
                inbox.setTitle(subject);
                inbox.setContent(content);
                inbox.setType(type);
                inbox.setUserid(toUserId);
                inbox.setFromuserid(fromUser.getPkid());
                inbox.setStatus(0);
                inbox.setCreatetime(new Date());
                inbox.setOutboxid(outboxId);
                inbox.setReadtime(new Date());
                inbox.setFromtype(1);
                inboxService.insert(inbox);
            }
            if(isHaveToUserNotExist)
            {
                return ResponseCode.buildResponseData(ResponseCode.MSGSEND_TOUSER_NOT_EXIST, null);
            }
            return ResponseCode.buildResponseData(ResponseCode.RESPONSE_CODE_SUCCESS, null);
        }
        catch(Exception ex)
        {
            logger.error("调用服务“sendMsg”异常", ex);
            return ResponseCode.buildResponseData(ResponseCode.EXCEPTION, null);
        }
    }

    /**
     * @Description: 单张上传图片
     * @param: [file:文件, filepath：上传路径]
     * @return: java.lang.String
     * @auther: tangy
     * @date: 2019/6/4 0004 9:21
     */
    @RequestMapping(value = "/upload/uploadsinglepic", method = RequestMethod.POST)
    @ResponseBody
    public String uploadsinglepic(MultipartFile file, String filepath)
    {
        FileInfo resultfile=new FileInfo();resultfile.msg="格式不支持";resultfile.state="ERROR";
        String path = ToolsUtil.getUploadPath("/"+filepath);
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        suffix = suffix.toLowerCase();
        if(suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png") || suffix.equals(".gif")){
            String fileName = UUID.randomUUID().toString()+suffix;
            File targetFile = new File(path, fileName);
            if(!targetFile.getParentFile().exists()){
                targetFile.getParentFile().mkdirs();
            }
            long size = 0;
            //保存
            try {
                file.transferTo(targetFile);
               size = file.getSize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            resultfile=new FileInfo();
            resultfile.msg="成功";
            resultfile.fileUrl=path+fileName;
            resultfile.url=filepath+"/"+fileName;
            resultfile.state="SUCCESS";
            resultfile.title=fileName;
            resultfile.original=fileName;
            resultfile.type=suffix;
            resultfile.size=size;
            return JsonUtils.toJson(resultfile);
        }else{
            return JsonUtils.toJson(resultfile);
        }
    }
    public  class  FileInfo implements Serializable {
        public  String fileUrl;
        public  String url;
        public  String state;
        public  String title;
        public  String original;
        public  String type;
        public  long size;
        public  String msg;
    }


    /***
     * 文件下载
     * 基于SpringMVC提供的ResponseEntity
     * 以流的方式写入
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping("/fileDownload")
    public ResponseEntity<byte[]> fileDownload(String fileName,HttpServletRequest Request) throws IOException
    {
        //从前端获取文件名
       // String path=Request.getSession().getServletContext().getRealPath("/img");
        String path = ToolsUtil.getUploadPath("/downloadtemplate/");
        //拼接文件路径
        String pathAndFilename=path+"\\"+fileName;
        //创建文件对象以供后面流传输
        File file=new File(pathAndFilename);
        byte body[]=null;
        ResponseEntity<byte[]> entity=null;
        try {
            //将文件读到byte数组
            InputStream input=new FileInputStream(file);
            body=new byte[input.available()];
            input.read(body);
            //响应头
            HttpHeaders header = new HttpHeaders();
            //下载显示的中文名，解决中文名称乱码问题
            String downloadFileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
            //application/octet-stream:二进制流数据
            header.setContentDispositionFormData("attachment",downloadFileName);
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //状态
            HttpStatus statusCode = HttpStatus.OK;
            //定义ResponseEntity封装返回信息
            entity = new ResponseEntity<byte[]>(body, header, statusCode);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return entity;
    }

    @RequestMapping(value = "/upload/newuploadsinglepic", method = {RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public Map<String, Object> newuploadsinglepic(@RequestParam(value = "croppedImage",required = false)MultipartFile file, HttpServletRequest request) throws Exception {

        Map<String, Object> result = new HashMap<String, Object>();
        String filename = request.getParameter("fileName");  //获取文件的名字
        String filePath = request.getParameter("filePath");  //上传路径
        String path = ToolsUtil.getUploadPath("/"+filePath);//文件路径
        /* filename = new String(filename.getBytes("UTF-8"), "UTF-8");*/
        String suffix = filename.substring(filename.lastIndexOf("."));
        // filename = System.currentTimeMillis() + "." + suffix;
        String fileName = UUID.randomUUID().toString()+suffix;
        if(file != null && !file.isEmpty()){
            try {
                copyFileRename(file.getInputStream(), fileName,path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        result.put("result", "success");
        result.put("url", filePath+"/"+fileName);
        return result;
    }

    public static void copyFileRename(InputStream in, String fileName,String myFilePath) throws IOException {
        FileOutputStream fs = new FileOutputStream(myFilePath + fileName);
        byte[] buffer = new byte[1024 * 1024];
        int byteread = 0;
        while ((byteread = in.read(buffer)) != -1) {
            fs.write(buffer, 0, byteread);
            fs.flush();
        }
        fs.close();
        in.close();
    }

}
