package com.easybasic.basic.service;

import com.alibaba.druid.util.StringUtils;
import com.easybasic.basic.dao.IUserDao;
import com.easybasic.basic.model.*;
import com.easybasic.component.Utils.Pinyin4jUtil;
import com.easybasic.component.Utils.RedisCache;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private IUserDao userDao;
    @Resource
    private UnitUserService unitUserService;
    @Resource
    private DeptUserService deptUserService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;
    @Resource
    private SchoolClassUserService schoolClassUserService;

    public void initAdminUser()
    {
        User user = getByLoginName("admin");
        if(user==null)
        {
            user = new User();
            user.setName("总管理员");
            user.setLoginname("admin");
            user.setPassword(ToolsUtil.getMd5("admin999"));
            user.setStatus(1);
            user.setCreatetime(new Date());
            user.setRegisterip("127.0.0.1");
            user.setLastlogintime(new Date());
            user.setLastloginip("127.0.0.1");
            user.setLogintime(new Date());
            user.setLogincount(0);
            user.setUsertype(0);
            user.setMobile("");
            user.setEmail("");
            user.setSex(1);
            user.setFpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName().substring(0,1)));
            user.setSpinyin(Pinyin4jUtil.converterToFirstSpell(user.getName()));
            insert(user);
            Teacher teacher = new Teacher();
            teacher.setUserid(user.getPkid());
            teacher.setGradeids("");
            teacher.setSubjects("");
            teacherService.insert(teacher);
        }
    }

    @Transactional
    public int createTeacher(User user, int unitId, String deptIds, String gradeIds, String subjectIds) {
        insert(user);
        Teacher teacher = new Teacher();
        teacher.setUserid(user.getPkid());
        teacher.setGradeids(gradeIds);
        teacher.setSubjects(subjectIds);
        teacherService.insert(teacher);
        //加入单位用户
        createUnitUser(user, unitId);
        createDeptUser(user, unitId, deptIds);
        return user.getPkid();
    }

    @Transactional
    public int createStudent(User user, int schoolId, int stageId, int gradeId, int classId, String stuId, String stuNum) {
        insert(user);
        Student student = new Student();
        student.setUserid(user.getPkid());
        student.setStuid(stuId);
        student.setStunum(stuNum);
        studentService.insert(student);
        //加入单位用户
        createUnitUser(user, schoolId);
        createSchoolClassUserForStudent(user, schoolId, classId);
        return user.getPkid();
    }

    public void createUnitUser(User user, int unitId) {
        if (unitId == 0) return;
        List<UnitUser> unitUsers = unitUserService.getListByUserId(user.getPkid());
        if(unitUsers.size()>0)
        {
            UnitUser temp = unitUsers.get(0);
            if(temp.getUnitid() != unitId)
            {
                temp.setUnitid(unitId);
                unitUserService.update(temp);
            }
        }
        if (!unitUserService.isUnitUserExist(unitId, user.getPkid())) {
            UnitUser unitUser = new UnitUser();
            unitUser.setUnitid(unitId);
            unitUser.setUserid(user.getPkid());
            unitUserService.insert(unitUser);
        }
    }

    public void createSchoolClassUserForStudent(User user, int schoolId, int classId)
    {
        if(schoolId == 0) return;
        SchoolClassUser schoolClassUser = schoolClassUserService.getStudentByUserId(user.getPkid());
        if(schoolClassUser == null)
        {
            schoolClassUser = new SchoolClassUser();
            schoolClassUser.setUnitid(schoolId);
            schoolClassUser.setClassid(classId);
            schoolClassUser.setUserid(user.getPkid());
            schoolClassUser.setPosition("");
            schoolClassUser.setUsertype(1);
            schoolClassUserService.insert(schoolClassUser);
        }
        else
        {
            schoolClassUser.setUnitid(schoolId);
            schoolClassUser.setClassid(classId);
            schoolClassUserService.update(schoolClassUser);
        }
    }

    public void createDeptUser(User user, int unitId, String deptIds) {
        if (unitId == 0) return;
        List<DeptUser> deptUsers = deptUserService.getListByUserId(user.getPkid());
        String[] deptList = deptIds.split(",");
        for (String deptIdStr : deptList) {
            int deptId = TypeConverter.strToInt(deptIdStr);
            int index = findDeptUserIndex(deptUsers, deptId);
            if (index==-1) {
                DeptUser deptUser = new DeptUser();
                deptUser.setUnitid(unitId);
                deptUser.setDeptid(deptId);
                deptUser.setUserid(user.getPkid());
                deptUser.setPosition("");
                deptUser.setSubject("");
                deptUserService.insert(deptUser);
            }
            else
            {
                deptUsers.remove(index);
            }
        }
        for(DeptUser deptUser : deptUsers)
        {
            deptUserService.delete(deptUser.getPkid());
        }
    }

    private int findDeptUserIndex(List<DeptUser> list, int deptId)
    {
        int index = -1;
        for(int i=0;i<list.size(); i++)
        {
            if(list.get(i).getDeptid().intValue() == deptId)
            {
                index = i;
                break;
            }
        }
        return index;
    }


    public User getUserForCache(int userId)
    {
        String key = RedisCache.CachePrex + "_getUserForCache_" + userId;
        User data = (User) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByUserId(userId);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public User getUserForCache(String loginName)
    {
        String key = RedisCache.CachePrex + "_getUserForCache_" + loginName;
        User data = (User) RedisCache.getInstance().getObject(key);
        if (data == null)
        {
            data = getByLoginName(loginName);
            if (data != null)
            {
                RedisCache.getInstance().putObject(key, data);
            }

        }

        return data;
    }

    public void clearUserCache(int userId)
    {
        String key = RedisCache.CachePrex + "_getUserForCache_" + userId;
        RedisCache.getInstance().removeObject(key);
    }

    public void clearUserCache(String loginName)
    {
        String key = RedisCache.CachePrex + "_getUserForCache_" + loginName;
        RedisCache.getInstance().removeObject(key);
    }

    public Boolean validatePassword(String inputPwd, String dataPwd)
    {
        if (dataPwd.toLowerCase().equals(ToolsUtil.getMd5(inputPwd).toLowerCase()))
        {
            return true;
        }
        return false;
    }

    public User getByUserId(Integer userId)
    {
        try
        {
            return userDao.selectByPrimaryKey(userId);
        }
        catch (Exception ex)
        {
            logger.error("“UserService”类执行方法“getByUserId”错误", ex);
            throw ex;
        }
    }

    public User getByLoginName(String loginName)
    {
        try
        {
            return userDao.selectByLoginName(loginName);
        }
        catch (Exception ex)
        {
            logger.error("“UserService”类执行方法“getByLoginName”错误", ex);
            throw ex;
        }
    }

    public int insert(User user)
    {
        try {
            return userDao.insert(user);
        }
        catch (Exception ex){
            logger.error("“UserService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(User user)
    {
        try {
            int re = userDao.updateByPrimaryKey(user);
            clearUserCache(user.getPkid());
            clearUserCache(user.getLoginname());
            return re;
        }
        catch (Exception ex){
            logger.error("“UserService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int userId)
    {
        try {
            User user = getByUserId(userId);
            if(user!=null) {
                int re = userDao.deleteByPrimaryKey(userId);
                clearUserCache(user.getPkid());
                clearUserCache(user.getLoginname());
                return re;
            }
            return 0;
        }
        catch (Exception ex){
            logger.error("“UserService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<User> getAllList()
    {
        try {
            return userDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“UserService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<User> getListBySearch(String searchStr, String orderStr)
    {
        try {
            if (!ToolsUtil.isSafeSqlString(searchStr)) return new ArrayList<>();
            return userDao.selectListBySearch(searchStr, orderStr);
        }
        catch (Exception ex)
        {
            logger.error("“UserService”类执行方法“getListBySearch”错误", ex);
            throw ex;
        }
    }

    public User getBySchoolIdAndName(int schoolId, String name)
    {
        try {
            return userDao.selectBySchoolIdAndName(schoolId, name);
        }
        catch (Exception ex)
        {
            logger.error("“UserService”类执行方法“getBySchoolIdAndName”错误", ex);
            throw ex;
        }
    }

    public User getByMobile(String mobile)
    {
        try {
            return userDao.selectByMobile(mobile);
        }
        catch (Exception ex)
        {
            logger.error("“UserService”类执行方法“getByMobile”错误", ex);
            throw ex;
        }
    }

    public String initUserLoginName(String name)
    {
        String loginName = Pinyin4jUtil.converterToFirstSpell(name);
        while (true)
        {
            User user = getByLoginName(loginName);
            if(user!=null)
            {
                loginName = loginName +"1";
            }
            else
            {
                break;
            }
        }
        return loginName;
    }
}

