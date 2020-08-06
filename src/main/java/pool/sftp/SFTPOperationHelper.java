package pool.sftp;

import com.fenqile.tql.business.global.sftp.vo.SFTPProperties;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * sftp操作工具类
 * Created by Niki on 2020/7/23 18:18
 */
@Slf4j
public class SFTPOperationHelper {

    /**
     * 登录ftp服务器，获取连接
     * @param properties
     * @return
     */
    public static ChannelSftp login(SFTPProperties properties) {
        try {
            JSch jsch = new JSch();
            if (StringUtils.hasText(properties.getPrivateKey())) {
                // 设置私钥
                jsch.addIdentity("idname", properties.getPrivateKey().getBytes(), null, null);
            }
            Session sshSession = jsch.getSession(properties.getUsername(), properties.getHost(), properties.getPort());
            if (StringUtils.hasText(properties.getPassword())) {
                sshSession.setPassword(properties.getPassword());
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            // 跳过Kerberos username身份验证
            sshConfig.put("PreferredAuthentications","publickey,keyboard-interactive,password");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            ChannelSftp channel = (ChannelSftp) sshSession.openChannel("sftp");
            channel.connect();
            return channel;
        } catch (JSchException e) {
            log.error("连接SFTP服务器={}异常", properties, e);
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @return 文件字节数组
     */
    public static InputStream downloadToStream(String dir, String name, ChannelSftp sftp) {
        try {
            sftp.cd(dir);
            return sftp.get(name);
        } catch (SftpException e) {
            log.error("SFTP下载文件={}{}异常", dir, name, e);
        }
        return null;
    }

    public static byte[] downloadToBytes(String dir, String name, ChannelSftp sftp) {
        try {
            sftp.cd(dir);
            InputStream inputStream = sftp.get(name);
            return IOUtils.toByteArray(inputStream);
        } catch (SftpException | IOException e) {
            log.error("SFTP下载文件={}{}异常", dir, name, e);
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @param in   输入流
     */
    public static void upload(String dir, String name, InputStream in, ChannelSftp sftp) throws SftpException {
        mkdirs(dir, sftp);
        sftp.cd(dir);
        sftp.put(in, name);
    }

    /**
     * 删除文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     */
    public static void delete(String dir, String name, ChannelSftp sftp) throws SftpException {
        sftp.cd(dir);
        sftp.rm(name);

    }

    /**
     * 递归创建多级目录
     *
     * @param dir 多级目录
     */
    public static void mkdirs(String dir, ChannelSftp sftp) throws SftpException {
        String[] folders = dir.split("/");
        sftp.cd("/");
        for (String folder : folders) {
            if (folder.length() > 0) {
                try {
                    sftp.cd(folder);
                } catch (Exception e) {
                    sftp.mkdir(folder);
                    sftp.cd(folder);
                }
            }
        }
    }
}
