package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/14/14:54
 * @Description: 课程基本信息管理业务接口
 */
public interface CourseBaseInfoService {

    /**
     * 课程查询接口
     * @param pageParams
     * @param queryCourseParamsDto
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    /**
     * 新增基础课程
     * @param companyId 机构ID
     * @param addCourseDto 课程信息
     * @return
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
}
