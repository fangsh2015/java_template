package tools.jsonrpc4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2020/11/6 20:03
 */
public class UserServiceImpl implements UserService {
    // 管理所有User对象
    private List<User> users = new ArrayList<>();

    @Override
    public User createUser(int userId, String name, int age) {
        System.out.println("createUser method");
        User user = new User();
        user.setUserId(userId);
        user.setName(name);
        user.setAge(age);
        users.add(user); // 创建User对象并添加到users集合中
        return user;
    }

    @Override

    public User getUser(int userId) {
        System.out.println("getUser method");
        // 根据userId从users集合中查询对应的User对象
        return users.stream().filter(u -> u.getUserId() == userId).findAny().get();
    }

    @Override
    public String getUserName(int userId) {
        System.out.println("getUserName method");
        // 根据userId从users集合中查询对应的User对象之后，获取该User的name
        return getUser(userId).getName();
    }

    @Override
    public int getUserId(String name) {
        System.out.println("getUserId method");
        // 根据name从users集合中查询对应的User对象，然后获取该User的id
        return users.stream().filter(u -> u.getName().equals(name)).findAny().get().getUserId();
    }

    @Override
    public void deleteAll() {
        System.out.println("deleteAll");
        users.clear(); // 清空users集合
    }
}
