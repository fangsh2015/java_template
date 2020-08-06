package pool.sftp;

import com.jcraft.jsch.ChannelSftp;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * sftp连接池
 * Created by Niki on 2020/7/23 11:26
 */

@Slf4j
@Data
public class ChannelSftpPool extends GenericObjectPool<ChannelSftp> {
    /**
     * 连接池名称
     */
    private String name;

    /**
     * 创建sftp连接池
     *
     * @param factory ChannelSftp 工厂类
     */
    public ChannelSftpPool(PooledObjectFactory<ChannelSftp> factory) {
        super(factory);
    }

    /**
     * 创建sftp连接池
     *
     * @param factory ChannelSftp 工厂类
     * @param config  连接池配置对象
     */
    public ChannelSftpPool(PooledObjectFactory<ChannelSftp> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

}
