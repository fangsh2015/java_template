package pool;

import lombok.Data;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * 军营。从军营派兵， 士兵结束任务回到军营修整
 * 类比池， 从池中获取对象，或者回收对象
 * Created by Niki on 2020/8/6 17:15
 */
@Data
public class Barracks extends GenericObjectPool<Solider> {
    /**
     * 兵营名称
     */
    private String name;

    /**
     * @param factory 招兵工厂
     */
    public Barracks(PooledObjectFactory<Solider> factory, String name) {
        super(factory);
        this.name = name;
    }

    /**
     * @param factory 招兵工厂
     * @param config  军营配置
     * @param name    军营名称
     */
    public Barracks(PooledObjectFactory<Solider> factory, GenericObjectPoolConfig config, String name) {
        super(factory, config);
        this.name = name;
    }

    /**
     * 新建一个军营
     * @param name
     * @param barracksConfig
     * @return
     */
    public static Barracks createBarracks(String name, GenericObjectPoolConfig barracksConfig) {
        // 新建一个招兵工厂
        RecruitFactory factory = new RecruitFactory();
        return new Barracks(factory, barracksConfig, name);
    }

}
