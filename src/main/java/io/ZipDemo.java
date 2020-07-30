package io;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * java io操作zip包demo
 * Created by Niki on 2018/5/15 9:53
 */
public class ZipDemo {
    private static final Logger logger = LoggerFactory.getLogger(ZipDemo.class);

    private static final String PROPERTIES_NAME = "patch.properties";

    public static void main(String[] args) throws IOException {
        decopressZip("E:\\class.zip");
    }

    /**
     * 解压文件
     *
     * @param orgPath
     */
    private static void decopressZip(String orgPath) throws IOException {
        File file = new File(orgPath);
        if (!isZIP(file)) {
            return;
        }

        String tempDir = createTempDir();
        File $tempDir = new File(tempDir);
        if (!$tempDir.exists()) {
            $tempDir.mkdirs();
        }

        ZipFile zipFile = new ZipFile(file);
        System.out.println(zipFile.size());
        ZipEntry zipEntry;
        //判断版本是否与原版本一致
        boolean canUpdate = judgeUpdate(tempDir,zipFile);
        if (!canUpdate) {
            return ;
        }

        Enumeration<ZipEntry> zipEntrys = (Enumeration<ZipEntry>) zipFile.entries();
        while (zipEntrys.hasMoreElements()) {
            zipEntry = zipEntrys.nextElement();
            String zipName = zipEntry.getName();
            String filePath = tempDir + File.separator + zipName;
            File tmpFile = new File(filePath);
            if (zipEntry.isDirectory()) {
                if (!tmpFile.exists()) {
                    tmpFile.mkdirs();
                }
                continue;
            }

            if (tmpFile.exists()) {
                tmpFile.delete();
            }
            tmpFile.createNewFile();
            InputStream inputStream = zipFile.getInputStream(zipEntry);
            OutputStream outputStream = new FileOutputStream(tmpFile);
            IOUtils.copyLarge(inputStream, outputStream);
            outputStream.close();
            inputStream.close();
        }
    }

    public static boolean isZIP(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        byte[] head = new byte[4];
        if (-1 == inputStream.read(head)) {
            return false;
        }
        inputStream.close();
        int headHex = 0;
        for (byte b : head) {
            headHex <<= 8;
            headHex |= b;
        }
        switch (headHex) {
            case 0x504B0304:
                return true;
            default:
                return false;
        }
    }

    private static String createTempDir() throws IOException {
        ZipDemo.class.getResource("ZipDemo.class")
                .toString();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        rootPath = ZipDemo.class.getResource("ZipDemo.class").toString();
        String tempDir = rootPath + File.separator + "tmp" + File.separator + "patch";
        return tempDir;
    }

    /**
     * 判断是否可以更新补丁
     *
     * @param tmpDir
     * @param zipFile
     * @return
     * @throws IOException
     */
    private static boolean judgeUpdate(String tmpDir, ZipFile zipFile) throws IOException {
        Enumeration<ZipEntry> zipEntrys = (Enumeration<ZipEntry>) zipFile.entries();
        while (zipEntrys.hasMoreElements()) {
            ZipEntry zipEntry = zipEntrys.nextElement();
            String name = zipEntry.getName();
            if (name.contains(PROPERTIES_NAME)) {
                String path = tmpDir + File.separator + name;
                //该文件为配置文件
                File file = new File(tmpDir + File.separator + name);

                if (file.exists()) {
                    //存在之前的配置文件，判断该补丁有效
                    Properties oldProperties = getProperties(path);
                    Properties newProperties = getProperties(zipFile.getInputStream(zipEntry));
                    String oldVersion = oldProperties.getProperty("version");
                    String newVersion = newProperties.getProperty("version");
                    if (oldVersion.equals(newVersion)) {
                        logger.info("新补丁版本：{}与老版本补丁：{}一致，不更新该补丁！", newVersion, oldVersion);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static Properties getProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(path));
            properties.getProperty("version");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    private static Properties getProperties(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
