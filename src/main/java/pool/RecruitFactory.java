package pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * 招兵处，用来招收士兵，补充军营
 * 获取池化对象的工厂,用来获取池化对象
 * Created by Niki on 2020/8/6 13:00
 */
public class RecruitFactory extends BasePooledObjectFactory<Solider> {
    /**
     * 当池中创建新对象时调用该方法创建，招募士兵
     * @return
     * @throws Exception
     */
    @Override
    public Solider create() throws Exception {
        Solider solider = new Solider();
        solider.enlist();
        return solider;
    }

    /**
     * 获取士兵的连接池，兵营
     * @param solider
     * @return
     */
    @Override
    public PooledObject<Solider> wrap(Solider solider) {
        return new DefaultPooledObject<>(solider);
    }

    /**
     * 销毁池中的对象，士兵退伍
     * @param p
     * @throws Exception
     */
    @Override
    public void destroyObject(PooledObject<Solider> p) throws Exception {
        super.destroyObject(p);
        Solider solider = p.getObject();
        // 退伍
        solider.retired();
    }

    /**
     * 校验士兵是否在服役期
     * @param p
     * @return
     */
    @Override
    public boolean validateObject(PooledObject<Solider> p) {
        final Solider solider = p.getObject();
        return solider.isService();
    }
}
