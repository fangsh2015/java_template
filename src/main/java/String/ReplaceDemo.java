package String;

/**
 * logback中将{}替换成参数的方法
 */
public class ReplaceDemo {
    private static final String PLACEHOLDER = "{}";
    public static String loggerFormat(){
        long beginTs = System.currentTimeMillis();

        return null;
    }

    /**
     * 通过计算{}位置，替换为参数值，并且放到stringbuilder中重新拼接一个新的字符串
     * @param msg
     * @param params
     * @return
     */
    public static String formatMsg(String msg, Object... params){
        StringBuilder result = new StringBuilder((msg.length()+(5*params.length)));//先设定stringbuilder的长度，减少扩展的概率。stringbuilder默认16
        for(int i=0; i<params.length; i++){
            int len = msg.length();
            int index = msg.indexOf(PLACEHOLDER);
            result.append(msg, 0, index);
            result.append(params[i]);
            msg = msg.substring(index+2, len);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String test = "方法产生异常{}，参数为：{}";
        int index = test.indexOf("{}");
        System.out.println(index);

        String msg = formatMsg(test, "空指针","null");
        System.out.println(msg);
    }
}
