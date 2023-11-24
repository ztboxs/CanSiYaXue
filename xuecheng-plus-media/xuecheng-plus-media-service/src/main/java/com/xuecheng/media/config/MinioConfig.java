package com.xuecheng.media.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 天天进步
 *
 * @Author: ztbox
 * @Date: 2023/11/24/14:19
 * @Description: minio配置
 */
@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient () {
        MinioClient minioClient = MinioClient.builder()
                                             .endpoint(endpoint)
                                             .credentials(accessKey, secretKey)
                                             .build();
        return minioClient;
    }

}
