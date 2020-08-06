package pool.sftp;

import lombok.Data;

/**
 * sftp连接属性
 * sftp连接池的属性
 * Created by Niki on 2020/7/23 11:17
 */
@Data
public class SFTPProperties {
    /**
     * FTP 登录用户名
     */
    private String username;

    /**
     * FTP 登录密码
     */
    private String password;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * FTP 服务器地址IP地址
     */
    private String host;

    /**
     * FTP 端口
     */
    private Integer port;

    /**
     * 最大连接数量
     */
    private Integer maxTotal;

    /**
     * 最大空闲
     */
    private Integer maxIdle;

    /**
     * 最小空闲
     */
    private Integer minIdle;
}
