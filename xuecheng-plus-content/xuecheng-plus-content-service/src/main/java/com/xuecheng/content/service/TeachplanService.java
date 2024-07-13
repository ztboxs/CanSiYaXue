package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.TeachplanMedia;

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

    /**
     * 逻辑删除课程计划
     * @param id 课程id
     * @return
     */
     Boolean deleteTeachplan(Long id);

    /**
     * @description 教学计划绑定媒资
     * @param bindTeachplanMediaDto
     * @return com.xuecheng.content.model.po.TeachplanMedia
     */
    TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);


}
