package com.platform.system.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Configuration
@ConfigurationProperties(prefix = FilePath.FILE_CONF, ignoreUnknownFields =true)
public class FilePath {

	 /** 对应配置文件里的配置键 */
    public final static String FILE_CONF = "file";
    
    /** 图片物理路径 */
    private String imgPath;

    /** 图片访问路径 */
    private String imgMapping;

    /** 视频物理路径 */
    private String videoPath;

    /** 视频访问路径 */
    private String videoMapping;
    
    /** 其它文件物理路径 */
    private String filePath;

    /** 其它文件访问路径 */
    private String fileMapping;
    
    /** 文件下载路径 */
    private String fileDownPath;
    
    /** 返回前缀*/
    private String preUrl;
    
    /** 文件访问路径  */
    private String fileVisitPath;
    
    /** 文件流下载路径  */
    private String fileStreamDownPath;
    
}
