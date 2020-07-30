package data_structure.json;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Niki on 2019/6/10 10:08
 */
@Data
public class Person {
    private String name;
    private FullName fullName;
    private int age;
    private Date birthday;
    private List<String> hobbies;
    private Map<String, String> clothes;
    private List<Person> friends;
}
