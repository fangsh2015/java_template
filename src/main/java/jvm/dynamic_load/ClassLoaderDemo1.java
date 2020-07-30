package jvm.dynamic_load;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

/**
 * Created by Niki on 2018/4/17 20:00
 */
class ClassLoaderDemo extends ClassLoader {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException {
        new ClassLoaderDemo().loadClassTest();
//        String name = "jvm.dynamic_load.User";
//        getClassBytes(name);
        System.out.println("============================================================================================");
        new ClassLoaderDemo().defineClassTest();
    }

    private void loadClassTest() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class defaultClass = User.class;
        Class loadClass = loadClass("jvm.dynamic_load.User");
        System.out.println("java默认加载器加载类" + defaultClass.getName());
        System.out.println("自定义加载器加载类" + loadClass.getName());

        System.out.println("java默认加载器" + defaultClass.getClassLoader());
        System.out.println("自定义加载器" + loadClass.getClassLoader());

        User.code = 11;
        System.out.println("java默认加载器加载类" + getStaticField(defaultClass));
        System.out.println("自定义加载器加载类" + getStaticField(loadClass));

        System.out.println("两个类对象是否相等：" + (defaultClass == loadClass));
    }

    private void defineClassTest() throws IOException, NoSuchFieldException, IllegalAccessException {
        Class defaultClass = User.class;
        byte[] classBytes = ClassLoaderDemo.getClassBytes(User.class.getName());
        Class loadClass = defineClass(User.class.getName(), classBytes, 0, classBytes.length);
        System.out.println("java默认加载器加载类" + defaultClass.getName());
        System.out.println("自定义加载器加载类" + loadClass.getName());

        System.out.println("java默认加载器" + defaultClass.getClassLoader());
        System.out.println("自定义加载器" + loadClass.getClassLoader());

        User.code = 11;
        System.out.println("java默认加载器加载类" + getStaticField(defaultClass));
        System.out.println("自定义加载器加载类" + getStaticField(loadClass));

        System.out.println("两个类对象是否相等：" + (defaultClass == loadClass));
    }

    private Object getStaticField(Class clazz) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField("code");
        return field.get(null);

    }

    private static byte[] getClassBytes(String name) throws IOException {

        String root = ClassLoaderDemo.class.getClassLoader().getResource("").getPath();
//        System.out.println(root);
//        String root2 = ClassLoaderDemo1.class.getClassLoader().getResource("").getFile();
//        System.out.println(root2);
//        System.out.println(File.separator);
        //模拟外部class路径
        String classPath = root + name.replace(".", File.separator) + ".class";
        File classFile = new File(classPath);
//        System.out.println(classFile.exists());
        InputStream inputStream = new FileInputStream(classFile);
        byte[] buffer = new byte[inputStream.available()];
        IOUtils.readFully(inputStream, buffer);
        IOUtils.closeQuietly(inputStream);
        return buffer;
    }
}
