package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author zt
 * @version 1.0
 * @description 课程查询条件模型类
 * @date 2023/2/11 15:37
 */
@Data
@ToString
public class QueryCourseParamsDto {

    //审核状态
    @ApiModelProperty("审核状态")
    private String auditStatus;
    //课程名称
    @ApiModelProperty("课程名称")
    private String courseName;
    //发布状态
    @ApiModelProperty("发布状态")
    private String publishStatus;

}
