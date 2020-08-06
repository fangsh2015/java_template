package pool.sftp;

import com.jcraft.jsch.ChannelSftp;

import java.io.InputStream;

/**
 * sftp操作工具类
 * Created by Niki on 2020/7/23 18:14
 */
public class SFTPPoolOperationHelper extends SFTPOperationHelper {

    /**
     * 下载文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @return 文件字节数组
     */
    public static byte[] downloadToBytes(String dir, String name, ChannelSftpPool pool) throws Exception {
        ChannelSftp sftp = ChannelSftpPoolHelper.borrowObject(pool);
        try {
            return downloadToBytes(dir, name, sftp);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 上传文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     * @param in   输入流
     */
    public static void upload(String dir, String name, InputStream in, ChannelSftpPool pool) throws Exception {
        ChannelSftp sftp = pool.borrowObject();
        try {
            upload(dir, name, in, sftp);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 删除文件
     *
     * @param dir  远程目录
     * @param name 远程文件名
     */
    public static void delete(String dir, String name, ChannelSftpPool pool) throws Exception {
        ChannelSftp sftp = pool.borrowObject();
        try {
            delete(dir, name, sftp);
        } finally {
            pool.returnObject(sftp);
        }
    }

    /**
     * 递归创建多级目录
     *
     * @param dir 多级目录
     */
    private static void mkdirs(String dir, ChannelSftpPool pool) throws Exception {
        ChannelSftp sftp = pool.borrowObject();
        try {
            mkdirs(dir, sftp);
        } finally {
            pool.returnObject(sftp);
        }
    }
}
