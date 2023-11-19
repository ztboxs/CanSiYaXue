package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/18/15:05
 * @Description: 修改ID课程封装
 */
@Data
@ApiModel(value="EditCourseDto", description="修改课程基本信息")
public class EditCourseDto extends AddCourseDto{

    @ApiModelProperty(value = "课程Id",required = true)
    private Long id;

}
