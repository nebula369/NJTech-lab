package com.easybasic.basic.service;

import com.easybasic.basic.dao.ISchoolYearTermDao;
import com.easybasic.basic.model.SchoolYearTerm;
import com.easybasic.component.Utils.ToolsUtil;
import com.easybasic.component.Utils.TypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("schoolYearTermService")
public class SchoolYearTermService {

    private static Logger logger = LoggerFactory.getLogger(SchoolYearTermService.class);

    @Resource
    private ISchoolYearTermDao schoolYearTermDao;

    public void setSchoolCurrentYearTerm(int schoolId)
    {
        Calendar cal = ToolsUtil.getCalendarInstance(new Date());
        String name = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.YEAR) + 1);
        //第一学期
        setSchoolYearTerm(schoolId, name, 1);
        //第二学期
        setSchoolYearTerm(schoolId, name, 2);
        name = (cal.get(Calendar.YEAR) - 1) + "-" + cal.get(Calendar.YEAR);
        //第一学期
        setSchoolYearTerm(schoolId, name, 1);
        //第二学期
        setSchoolYearTerm(schoolId, name, 2);
    }

    private void setSchoolYearTerm(int schoolId, String name, int schoolTerm)
    {
        SchoolYearTerm schoolYearTermInfo = getBySchoolIdAndNaneAndSchoolTerm(schoolId, name, schoolTerm);
        if(schoolYearTermInfo == null)
        {
            Date[] result = initSchoolYearTermDate(name, schoolTerm);
            schoolYearTermInfo.setSchoolid(schoolId);
            schoolYearTermInfo.setName(name);
            schoolYearTermInfo.setSchoolterm(schoolTerm);
            schoolYearTermInfo.setWeekdays(0);
            schoolYearTermInfo.setCourselen(0);
            schoolYearTermInfo.setMorningsessions(4);
            schoolYearTermInfo.setAfternoonsessions(4);
            schoolYearTermInfo.setEveningsessions(0);
            schoolYearTermInfo.setWorkenddate(result[0]);
            schoolYearTermInfo.setWorkenddate(result[1]);
            schoolYearTermInfo.setLearnstartdate(result[0]);
            schoolYearTermInfo.setLearnenddate(result[1]);
            schoolYearTermInfo.setCreatetime(new Date());
            insert(schoolYearTermInfo);
        }
    }

    private static Date[] initSchoolYearTermDate(String name, int schoolTerm)
    {
        Date[] result = new Date[2];
        String[] ss = name.split("-");
        int yearS = TypeConverter.strToInt(ss[0]);
        int yearE = TypeConverter.strToInt(ss[1]);
        if(schoolTerm==1)
        {
            //第一学期
            result[0] = TypeConverter.stringToDate(yearS + "-09-01", "yyyy-MM-dd");
            result[1] = TypeConverter.stringToDate(yearE + "-01-20", "yyyy-MM-dd");
        }
        else
        {
            //第二学期
            result[0] = TypeConverter.stringToDate(yearE + "-02-20","yyyy-MM-dd");
            result[1] = TypeConverter.stringToDate(yearE + "-07-10","yyyy-MM-dd");
        }
        return result;
    }

    public boolean isSchoolYearTermExist(int schoolId, String name, int schoolTerm)
    {
        SchoolYearTerm yearTerm = getBySchoolIdAndNaneAndSchoolTerm(schoolId, name, schoolTerm);
        if(yearTerm == null)
        {
            return false;
        }
        return true;
    }

    public SchoolYearTerm getByPkId(Integer pkId)
    {
        try
        {
            return schoolYearTermDao.selectByPrimaryKey(pkId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolYearTermService”类执行方法“getByPkId”错误", ex);
            throw ex;
        }
    }

    public int insert(SchoolYearTerm schoolYearTerm)
    {
        try {
            return schoolYearTermDao.insert(schoolYearTerm);
        }
        catch (Exception ex){
            logger.error("“SchoolYearTermService”类执行方法“insert”错误", ex);
            throw ex;
        }
    }

    public int update(SchoolYearTerm schoolYearTerm)
    {
        try {
            int re = schoolYearTermDao.updateByPrimaryKey(schoolYearTerm);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolYearTermService”类执行方法“update”错误", ex);
            throw ex;
        }
    }

    public int delete(int pkId)
    {
        try {
            int re = schoolYearTermDao.deleteByPrimaryKey(pkId);
            return re;
        }
        catch (Exception ex){
            logger.error("“SchoolYearTermService”类执行方法“delete”错误", ex);
            throw ex;
        }
    }

    public List<SchoolYearTerm> getAllList()
    {
        try {
            return schoolYearTermDao.selectAll();
        }
        catch (Exception ex)
        {
            logger.error("“SchoolYearTermService”类执行方法“getAllList”错误", ex);
            throw ex;
        }
    }

    public List<SchoolYearTerm> getListBySchoolId(int schoolId)
    {
        try {
            return schoolYearTermDao.selectListBySchoolId(schoolId);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolYearTermService”类执行方法“getListBySchoolId”错误", ex);
            throw ex;
        }
    }

    public List<SchoolYearTerm> getListBySchoolIdAndYear(int schoolId, String year)
    {
        try {
            return schoolYearTermDao.selectListBySchoolIdAndName(schoolId, year);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolYearTermService”类执行方法“getListBySchoolIdAndYear”错误", ex);
            throw ex;
        }
    }

    public SchoolYearTerm getBySchoolIdAndNaneAndSchoolTerm(int schoolId, String name, int schoolTerm)
    {
        try {
            return schoolYearTermDao.selectBySchoolIdAndNameAndSchoolTerm(schoolId, name, schoolTerm);
        }
        catch (Exception ex)
        {
            logger.error("“SchoolYearTermService”类执行方法“getBySchoolIdAndNaneAndSchoolTerm”错误", ex);
            throw ex;
        }
    }

    public SchoolYearTerm getBySchoolCurrentYearTerm(int schoolId)
    {
        try {
            SchoolYearTerm yearTerm = schoolYearTermDao.selectBySchoolCurrentYearTerm(schoolId);
            if(yearTerm==null)
            {
                //如果不存在，则添加
                setSchoolCurrentYearTerm(schoolId);
                yearTerm = schoolYearTermDao.selectBySchoolCurrentYearTerm(schoolId);
            }
            return yearTerm;
        }
        catch (Exception ex)
        {
            logger.error("“SchoolYearTermService”类执行方法“getBySchoolCurrentYearTerm”错误", ex);
            throw ex;
        }
    }

}

