package test;

public class StringTest {
    public static void main(String[] args) {
        String test = "tes{},tese2{}.wwer{}";
        test = test.replace("{}", "fsh");
        System.out.println(test);
    }
}
