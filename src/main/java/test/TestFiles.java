package test;

import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;

/**
 * Created by Niki on 2018/4/10 16:01
 */
public class TestFiles {
    public static void main(String[] args) throws IOException {
        byte[] bytes = Files.toByteArray(new File("E:\\fsh_workspace\\code\\java-template\\target\\classes\\jvm\\dynamic_load\\NewTest.class"));
        System.out.println(bytes.length);

    }
}
