package pool.sftp;

import com.jcraft.jsch.ChannelSftp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * SFTP连接工厂
 * Created by Niki on 2020/7/23 11:09
 */
@Slf4j
@Data
public class ChannelSftpFactory extends BasePooledObjectFactory<ChannelSftp> {
    /**
     * sftp连接参数
     */
    private SFTPProperties properties;

    public ChannelSftpFactory(SFTPProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建sftp连接
     * @return
     */
    @Override
    public ChannelSftp create() {
        return SFTPOperationHelper.login(this.properties);
    }

    /**
     * 获取SFTP连接池
     * @param channelSftp
     * @return
     */
    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    /**
     * 销毁SFTP连接对象
     * @param p
     */
    @Override
    public void destroyObject(PooledObject<ChannelSftp> p) {
        ChannelSftp channelSftp = p.getObject();
        channelSftp.disconnect();
    }

    /**
     * 判断连接池中的ChannelSftp是否有效
     * @param p
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<ChannelSftp> p) {
        final ChannelSftp channelSftp = p.getObject();
        return !channelSftp.isClosed();
    }
}
