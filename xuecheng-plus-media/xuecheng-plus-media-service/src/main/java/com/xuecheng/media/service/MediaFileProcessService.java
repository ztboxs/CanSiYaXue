package com.xuecheng.media.service;

import com.xuecheng.media.model.po.MediaProcess;

import java.util.List;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/12/03/16:32
 * @Description: 媒资文件处理业务方法
 */
public interface MediaFileProcessService {

    /**
     * @description 获取待处理任务
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param count 获取记录数
     */
     List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    /**
     * 开启一个任务
     * @param id 任务 id
     * @return true 开启任务成功，false 开启任务失败
     */
     boolean startTask(long id);

    /**
     * @description 保存任务结果
     * @param taskId 任务 id
     * @param status 任务状态
     * @param fileId 文件 id
     * @param url url
     * @param errorMsg 错误信息
     * @return void
     * @author Mr.M
     * @date 2022/10/15 11:29
     */
    void saveProcessFinishStatus(Long taskId,String status,String fileId,String url,String errorMsg);

}
