package tools.jsonrpc4j;

/**
 * Created by Niki on 2020/11/6 20:03
 */
public interface UserService {
    User createUser(int userId, String name, int age);

    User getUser(int userId);

    String getUserName(int userId);

    int getUserId(String name);

    void deleteAll();
}
