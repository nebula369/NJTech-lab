package com.easybasic.service.controller;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.model.OnlineUser;
import com.easybasic.basic.model.ThirdApp;
import com.easybasic.basic.model.User;
import com.easybasic.basic.service.OnlineUserService;
import com.easybasic.basic.service.UserService;
import com.easybasic.component.ServiceController;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.service.dto.ResponseCode;
import com.easybasic.service.dto.ResponseDTO;
import com.easybasic.service.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/service")
public class AccountServiceController extends ServiceController {

    @Resource
    private OnlineUserService onlineUserService;
    @Resource
    private UserService userService;

    /**
     * 单点登录验证ticket
     * @param appKey 第三方应用唯一标识
     * @param ticket 当前登录用户的token
     * @param auth MD5(appSecret+appKey+ticket)
     * @return
     */
    @RequestMapping(value = "/checkTicket", method = RequestMethod.POST)
    public ResponseDTO checkTicket(String appKey, String ticket, String auth)
    {
        try
        {
            //logger.info("checkTicket参数：appKey="+ appKey + "   ticket=" + ticket + "  auth=" + auth);
            if(StringUtils.isEmpty(ticket))
            {
                return ResponseCode.buildResponseData(ResponseCode.TICKET_NOT_NULL, null);
            }
            OnlineUser onlineUser = onlineUserService.getByCode(ticket);
            if(onlineUser == null)
            {
                return ResponseCode.buildResponseData(ResponseCode.USER_NOT_LOGIN, null);
            }
            ResponseDTO responseDTO = checkThirdAppValidate(appKey, onlineUser.getUserid());
            if(responseDTO.getCode() != 1000) return responseDTO;
            ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
            String validateAuth = ToolsUtil.getMd5(thirdApp.getAppsecret() + thirdApp.getAppkey() + ticket);
            if(!auth.equalsIgnoreCase(validateAuth))
            {
                return ResponseCode.buildResponseData(ResponseCode.AUTH_NOT_UNAUTHORIZED, null);
            }
            User user = userService.getUserForCache(onlineUser.getUserid());
            if(user == null)
            {
                return ResponseCode.buildResponseData(ResponseCode.USER_NOT_EXIST, null);
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setLoginName(user.getLoginname());
            userDTO.setMobile(user.getMobile());
            userDTO.setEmail(user.getEmail());
            userDTO.setSex(user.getSex());
            userDTO.setUserType(user.getUsertype());
            return ResponseCode.buildResponseData(ResponseCode.RESPONSE_CODE_SUCCESS, userDTO);
        }
        catch(Exception ex)
        {
            logger.error("调用服务“checkTicket”异常", ex);
            return ResponseCode.buildResponseData(ResponseCode.EXCEPTION, null);
        }
    }

    /**
     * 验证用户登录
     * @param appKey 第三方应用唯一标识
     * @param loginName 需要验证登录的用户登录名
     * @param auth MD5(appSecret+appKey+loginName+password),其中password为真实密码的MD5加密
     * @return
     */
    @RequestMapping(value = "/clientLogin", method = RequestMethod.POST)
    public ResponseDTO clientLogin(String appKey, String loginName, String auth)
    {
        try
        {
            if(StringUtils.isEmpty(loginName))
            {
                return ResponseCode.buildResponseData(ResponseCode.USER_NOT_EXIST, null);
            }
            User user = userService.getUserForCache(loginName);
            if(user == null)
            {
                return ResponseCode.buildResponseData(ResponseCode.USER_NOT_EXIST, null);
            }
            if(user.getStatus() ==0)
            {
                return ResponseCode.buildResponseData(ResponseCode.FORBIDDEN_USER_LOGIN, null);
            }
            ResponseDTO responseDTO = checkThirdAppValidate(appKey, user.getPkid());
            if(responseDTO.getCode() != 1000) return responseDTO;
            ThirdApp thirdApp = thirdAppService.getThirdAppForCache(appKey);
            String validateAuth = ToolsUtil.getMd5(thirdApp.getAppsecret() + thirdApp.getAppkey() + loginName + user.getPassword());
            if(!auth.equalsIgnoreCase(validateAuth))
            {
                return ResponseCode.buildResponseData(ResponseCode.USERID_NOT_NULL, null);
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setLoginName(user.getLoginname());
            userDTO.setMobile(user.getMobile());
            userDTO.setEmail(user.getEmail());
            userDTO.setSex(user.getSex());
            userDTO.setUserType(user.getUsertype());
            return ResponseCode.buildResponseData(ResponseCode.RESPONSE_CODE_SUCCESS, userDTO);
        }
        catch(Exception ex)
        {
            logger.error("调用服务“clientLogin”异常", ex);
            return ResponseCode.buildResponseData(ResponseCode.EXCEPTION, null);
        }
    }
}
