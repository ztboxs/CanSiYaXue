package com.xuecheng.media.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/24/14:55
 * @Description:
 */
@Data
@ToString
public class UploadFileParamsDto {
    /**
     * 文件名称
     */
    @ApiModelProperty("文件名称")
    private String filename;

    /**
     * 文件类型（文档，音频，视频）
     */
    @ApiModelProperty("文件类型（文档，音频，视频）")
    private String fileType;
    /**
     * 文件大小
     */
    @ApiModelProperty("文件大小")
    private Long fileSize;

    /**
     * 标签
     */
    @ApiModelProperty("标签")
    private String tags;

    /**
     * 上传人
     */
    @ApiModelProperty("上传人")
    private String username;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    private String remark;

}
