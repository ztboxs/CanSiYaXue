package com.xuecheng.media.service.jobhander;

import com.xuecheng.base.utils.Mp4VideoUtil;
import com.xuecheng.media.model.po.MediaProcess;
import com.xuecheng.media.service.MediaFileProcessService;
import com.xuecheng.media.service.MediaFileService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/12/04/19:37
 * @Description:
 */
@Slf4j
@Component
public class VideoTask {

    @Autowired
    MediaFileService mediaFileService;
    @Autowired
    MediaFileProcessService mediaFileProcessService;

    @Value("${videoprocess.ffmpegpath}")
    String ffmpegpath;

    @XxlJob("videoJobHandler")
    public void videoJobHandler()  throws Exception {
        //分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        List<MediaProcess> mediaProcessList = null;
        int ShardingNumber = 0;
        try {
            //取出cpu核心数作为一次处理数据的条数
            int processors = Runtime.getRuntime().availableProcessors();
            //一次处理视频数量不要超过cpu的核心数
            mediaProcessList = mediaFileProcessService.getMediaProcessList(shardIndex, shardTotal, processors);
            ShardingNumber = mediaProcessList.size();
            log.debug("取出待处理视频任务{}条", ShardingNumber);
            if (ShardingNumber <= 0) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //启动ShardingNumber个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(ShardingNumber);
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(ShardingNumber);
        //将处理的任务加入线程池中
        mediaProcessList.forEach(mediaProcess -> {
            threadPool.execute(() ->{
                //任务id
                Long taskId = mediaProcess.getId();
                //抢占任务
                boolean b = mediaFileProcessService.startTask(taskId);
                if (!b) {
                    return;
                }
                log.debug("开始执行任务:{}",mediaProcess);
                //===下边是处理逻辑==
                //桶
                String bucket = mediaProcess.getBucket();
                //储存的路径
                String filePath = mediaProcess.getFilePath();
                //原始视频的md5值
                String fileId = mediaProcess.getFileId();
                //原始文件的名称
                String filename = mediaProcess.getFilename();
                //将要处理的文件下载到服务器上
                File originalFile = mediaFileService.downloadFileFromMinIO(mediaProcess.getBucket(), mediaProcess.getFilename());
                if (originalFile == null) {
                    log.error("下载待处理文件失败，originalFile：{}",mediaProcess.getBucket().concat(mediaProcess.getFilePath()));
                    mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(),"3",fileId,null,"下载待处理文件失败");//---3---为文件状态
                    return;
                }
                //处理下载的视频文件
                File mp4File = null;
                try {
                    mp4File = File.createTempFile("mp4",".mp4");
                } catch (IOException e) {
                    log.error("创建mp4临时文件失败");
                    mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(),"3",fileId,null,"创建mp4临时文件失败");
                    return;
                }
                //视频处理结果
                String result = null;
                try {
                    //开始处理视频
                    Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(ffmpegpath, originalFile.getAbsolutePath(), mp4File.getName(), mp4File.getAbsolutePath());
                    //开始视频转换成功返回success
                    result = mp4VideoUtil.generateMp4();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("处理视频文件:{},出错:{}",mediaProcess.getFilePath(), e.getMessage());
                }
                if (!result.equals("success")) {
                    //记录错误信息
                    log.error("处理视频失败，视频地址：{}，错误信息:{}",bucket + filePath, result);
                    mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(), "3", fileId, null, result);
                    return;
                }
                //===将mp4文件上传到minio中===
                //MP4在minio中的储存路径
                String objectName = getFilePath(fileId, ".mp4");
                String url = "/" + bucket + "/" + objectName;
                try {
                    mediaFileService.addMediaFilesToMinIO(mp4File.getAbsolutePath(),"video/mp4",bucket,objectName);
                    //将url储存到数据库,并更新状态为成功，将待处理视频记录删除存入历史
                    mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(),"2",fileId, url,null);//===2===状态修改为2
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("上传视频失败或者入库失败,视频地址:{}", bucket + objectName, e.getMessage());
                    //最终还是失败
                    mediaFileProcessService.saveProcessFinishStatus(mediaProcess.getId(),"3",fileId, null, "处理视频上传或者入库失败");
                } finally {
                    countDownLatch.countDown();
                }
            });
        });
        //等待，给一个充裕的超时时间，防止无限等待，到达超时时间还没有处理完成则结束任务
        countDownLatch.await(30, TimeUnit.MINUTES);
    }

    //md5拼接路径
    private String getFilePath(String fileMd5, String fileExt) {
        return fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" + fileExt;
    }

}
