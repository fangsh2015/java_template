package algorithm.timewhell;

/**
 * Created by Niki on 2020/8/14 16:45
 */
public interface ExpirationListener <E> {

    void expired(E expiredObject);
}
