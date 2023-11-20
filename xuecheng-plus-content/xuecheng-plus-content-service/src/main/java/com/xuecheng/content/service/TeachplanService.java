package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/19/21:18
 * @Description:
 */
public interface TeachplanService {

    /**
     * 查询课程计划树型结构
     * @param courseId 课程Id
     * @return
     */
     List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * 保存课程计划
     * @param saveTeachplanDto 课程计划信息
     */
     void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

}
