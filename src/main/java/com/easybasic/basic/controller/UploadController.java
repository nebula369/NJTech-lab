package com.easybasic.basic.controller;

import com.easybasic.basic.dto.UploadResult;
import com.easybasic.component.BaseController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.handler.ImageHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping("/manage/upload")
public class UploadController extends BaseController {

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public UploadResult fileUpload(HttpServletRequest request) throws IOException
    {
        UploadResult result = new UploadResult();
        result.setCode(0);
        result.setMsg("");
        result.setData("");
        CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request))
        {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            //获取multiRequest 中所有的文件名
            MultipartFile file = null;
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                //一次遍历所有文件
                file = multiRequest.getFile(iter.next().toString());
                if(file!=null)
                {
                    break;
                }
            }
            if(file == null)
            {
                return result;
            }
            String directory = request.getParameter("directory");
            String ext = ToolsUtil.getFileExtension(new File(file.getOriginalFilename())).trim().toLowerCase();
            if(!isSecureExt(ext))
            {
                result.setMsg("上传文件非法");
                return result;
            }
            else {
                if(directory.indexOf("{time}")>0)
                {
                    Calendar calendar = ToolsUtil.getCalendarInstance(new Date());
                    directory = directory.replace("{time}",calendar.get(Calendar.YEAR)+"/" + String.format("%02d",calendar.get(Calendar.MONTH)+1)+"/"+String.format("%02d",calendar.get(Calendar.DATE)) + "/" + calendar.get(Calendar.HOUR)+calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND)+calendar.get(Calendar.MILLISECOND) + ToolsUtil.getRandom(1,100));
                }
                String path = ToolsUtil.getUploadPath("/" + directory);
                if (!ToolsUtil.existsDirectory(path)) {
                    ToolsUtil.forceDirectory(path);
                }
                String fileName = file.getOriginalFilename().toLowerCase();
                fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                String name = ToolsUtil.randomFileName(fileName);
                if (request.getParameter("nametype") !=null && request.getParameter("nametype").equalsIgnoreCase("real"))//真名保存
                {
                    name = file.getOriginalFilename();
                }
                else if (request.getParameter("nametype")!=null && request.getParameter("nametype").equalsIgnoreCase("fix"))//传入名称保存
                {
                    name = request.getParameter("name") + ext;
                }
                if (file != null && file.getSize() > 0) {
                    if (!ToolsUtil.existsDirectory(path)) {
                        ToolsUtil.forceDirectory(path);
                    }
                    // flash 会自动发送文件名到 Request.Form["fileName"]
                    String savePath = path  + "/" + name;
                    if(path.endsWith("/"))
                    {
                        savePath = path + name;
                    }
                    file.transferTo(new File(savePath));
//                    if(request.getParameter("type") !=null && request.getParameter("type").equalsIgnoreCase("image")) {
//                        ImageHandler.generateThumbnail(savePath, 200);
//                    }
                }
                result.setCode(1);
                result.setData(name);
                result.setPath(directory);
                result.setFilename(fileName);
                return result;
            }
        }
        return result;
    }


    private Boolean isSecureExt(String ext)
    {
        String filter = "asp|asa|cdx|cer|htr|php|aspx|ascx|asmx|asax|cs|idc";
        return !isExt(ext, filter);

    }

    private Boolean isExt(String ext, String value)
    {
        Boolean result = false;
        ext = ext.substring(1, ext.length() - 1);
        String[] values = value.split("|");
        for (String v : values)
        {
            if (ext.equalsIgnoreCase(v))
            {
                result = true;
                break;
            }
        }
        return result;
    }

    @RequestMapping(value = "/thumbnail", method = RequestMethod.POST)
    @ResponseBody
    public String thumbnail(String path, String imageName, int limit)
    {
        try
        {
            ImageHandler.generateThumbnail(ToolsUtil.getUploadPath("/" + path) + "/" + imageName, limit);
            return "1";
        }
        catch(Exception ex)
        {
            logger.error("生成缩略图错误“"+path+"/"+ imageName+"”", ex);
        }
        return "0";
    }
    @RequestMapping(value = "/delfile", method = RequestMethod.POST)
    @ResponseBody
    public String delfile(String dirpath, String filename) throws IOException
    {
        try{
            if(dirpath=="") return "0";
            String path=ToolsUtil.getUploadPath(dirpath);
            if(filename!="")//删除文件
            {
                String fileName=ToolsUtil.getUploadPath(path+"/"+filename);
                ToolsUtil.deleteFile(fileName);
            }else {
                ToolsUtil.deleteDirectory(path);
            }
            return "1";
        }catch (Exception ex)
        {
            logger.error("删除文件夹或文件失败：“"+dirpath+"/"+ filename+"”", ex);
        }
        return "0";
    }
}
