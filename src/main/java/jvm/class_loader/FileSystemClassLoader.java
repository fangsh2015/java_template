package jvm.class_loader;


import java.io.*;

/**
 * Created by Niki on 2020/6/11 16:12
 */
public class FileSystemClassLoader extends ClassLoader {
    // 加载路径
    private String rootDir;

    public FileSystemClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {

        }

        return defineClass(name, classData, 0, classData.length);
    }


    private byte[] getClassData(String className) {
        String path = classNameToPath(className);
        try {
            InputStream in = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int byteNumRead = 0;
            while ((byteNumRead = in.read(buffer)) != -1) {
                baos.write(buffer, 0, byteNumRead);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className) {
        return null;
    }


}
