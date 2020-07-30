package tools.lombok;


import lombok.*;

/**
 * Created by Niki on 2018/5/22 9:22
 */
@Data
//@RequiredArgsConstructor()
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LombokDemo {
//    @NonNull
    private String name;


//    @NonNull
    private int age;

//    private void setName(String name) {
//        this.name = name == null ? null : name.trim();
//    }

    public static void main(String[] args) {
        LombokDemo demo = new LombokDemo();
        demo.setName("                        name                           ");
        System.out.println(demo.getName());
    }
}
