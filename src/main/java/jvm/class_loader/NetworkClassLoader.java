package jvm.class_loader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Niki on 2020/6/11 17:08
 */
public class NetworkClassLoader extends ClassLoader {
    private String rootUrl;

    public NetworkClassLoader(ClassLoader parent, String rootUrl) {
        super(parent);
        this.rootUrl = rootUrl;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        }

        return defineClass(name, classData, 0, classData.length);
    }

    private byte[] getClassData(String name) {
        String path = classNameToPath(name);
        URL url = null;
        try {
            url = new URL(path);
            InputStream in = url.openStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];
            int readNum = 0;
            while ((readNum = in.read(buffer)) != -1) {
                baos.write(buffer, 0, readNum);
            }
            return baos.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String name) {
        return rootUrl + "/" + name.replaceAll(".", "/") + ".class";
    }

}
