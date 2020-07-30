package runtime;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Niki on 2018/5/8 13:58
 */
public class ExecDemo {
    public static void main(String[] args) {
        demo();
    }

    private static void demo(){
        Runtime runtime = Runtime.getRuntime();
//        System.out.println(runtime.availableProcessors());

        String strcmd = "cmd /c  D:\\server\\apache-tomcat-8.5.16\\bin\\shutdown.bat & D:\\server\\apache-tomcat-8.5.16\\bin\\startup.bat";
        try {
            Map<String,String> envs = System.getenv();
            List<String> envList = new ArrayList<>();
            for (Map.Entry<String, String> env : envs.entrySet()) {
                envList.add(String.format("%s=%s", env.getKey(), env.getValue()));
            }
            envList.add("CATALINA_HOME=D:\\server\\apache-tomcat-8.5.16");

            Process process = runtime.exec(strcmd, envList.toArray(new String[envList.size()]));

            InputStream is = process.getInputStream();
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
            String line = null ;
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void stopTomcat() {
        String strcmd = "D:\\Program Soft\\apache-tomcat-8.5.16\\bin\\shutdown.bat";
        try {
            Runtime.getRuntime().exec(strcmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startTomcat() {
        String strcmd = "cmd /c ionic -v D:\\Program Soft\\apache-tomcat-8.5.16\\bin\\startup.bat";
        try {
            Runtime.getRuntime().exec(strcmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
