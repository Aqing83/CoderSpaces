package life.qing.community.provider;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import life.qing.community.exception.CustomizeErrorCode;
import life.qing.community.exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class OssClientAuthorization {
    @Value("${aliyun.ufile.BUCKET_NAME}")
    private String bucketName ;

    @Value("${aliyun.ufile.END_POINT}")
    private String endPoint ;

    @Value("${aliyun.ufile.AccessKey_ID}")
    private String accessKeyId ;

    @Value("${aliyun.ufile.AccessKey_Secret}")
    private String accessKeySecret ;

    public String upload(InputStream inputStream, String fileName) throws IOException {
        String generatedFileName;
        // .属于正则表达式,＋\\才能表示.
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        ossClient.putObject(bucketName, generatedFileName, inputStream);

        Long dateTime = 1962631209000L;
        Date expiration = new Date(dateTime);
        System.out.println(expiration);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucketName, generatedFileName, expiration);
        if (url != null) {
            // 关闭OSSClient。
            ossClient.shutdown();
            return url.toString();
        } else {
            throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAILURE);
        }

    }
}
