package encryption;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Niki on 2020/5/13 14:28
 */
public class AESDemo {
    /**
     * 秘钥区
     */
    final private static String MI_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDJRO+bvoPd0I/yrzp1wO5DWx/FkOlLFG5JHhekycA0gG490wD9keoewfMgFJXsdZS9PM2VKfLes1BzHC8fMErBpXdaEFX7ZCPhZvAux3fmC0RN4H/EgT+r/kzwJPupDREYl06bXZEEa+WPkF8BYYq7DmVB84/vXPIRQiRxDDpWQQIDAQAB";
    final private static String MI_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMlE75u+g93Qj/KvOnXA7kNbH8WQ6UsUbkkeF6TJwDSAbj3TAP2R6h7B8yAUlex1lL08zZUp8t6zUHMcLx8wSsGld1oQVftkI+Fm8C7Hd+YLRE3gf8SBP6v+TPAk+6kNERiXTptdkQRr5Y+QXwFhirsOZUHzj+9c8hFCJHEMOlZBAgMBAAECgYAwW3G+V73TE4Miadt/40Tbxg8SJskGkvgUdTh1DUwy0RCrpgC868v2a98APDHxSCbeeLdkOVnzC94uaf0gXzf2dau4gCoprl35CrGpUGt3YE9wWrTwglQ5sGlcQWmxSDUbYsxQoR7OPSVj27qyZMT6I7uhTQtPTrth/Lbd6R0CYQJBAOpAPKfzQmvQIrCtM3rWD7ZUvJYnk5PQKv6ZtWNgeM9YKM0C8epBq+WMESK0Hl+j21JAoMKS7IMN0e54E9w8xW0CQQDb9Mdv0jDImVbYKiOePDHmqP8gs+ZIPclN8xSaKjdEJ3+UEDtLdEtr64H8qitdWP20DOBDBl69wPz1sAc3CBOlAkBMx7tZ7WEDB5inQk4SYCGMSN/7c4NquputEeKjbRaMF8Bf2ixC9T2LospnDpS6EnIr1qUawZ+mIRyDRnZ0BLNNAkBaN+eJvRwXW5XcakRbxfLt2klzH3cIEJe+KkM8DK7IfK7+iz3W8CdzZ5DUkvPggPzm2hUuSLGZgUwAdigvcVelAkEAxdf2hKCK08CG19E8avSHFjawO5+zR2vinPXulGx3AJ9jxVeYpiuRQHuMM90h7QWrtoYq6+HhTXCvCBemZJ3exg==";

    final private static String PARTNER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDVegsTjSJKb7PpjdhJXMKghlQ8\n" +
            "gm10pgyjJP2DN/SFTBtPsUYKWJJyml0cZcIaPpD2xDrLzqADP6nj1nEmn3vlRZbu\n" +
            "Jt57Yfw/mYYWOqad2jF37MGTWnbSmXuCcSPkvMi1oOAcXymzjAwKcIccDS/D6GFI\n" +
            "I7N3m+WOcJzXErFPXwIDAQAB\n";
    final private static String PARTNER_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANV6CxONIkpvs+mN\n" +
            "2ElcwqCGVDyCbXSmDKMk/YM39IVMG0+xRgpYknKaXRxlwho+kPbEOsvOoAM/qePW\n" +
            "cSafe+VFlu4m3nth/D+ZhhY6pp3aMXfswZNadtKZe4JxI+S8yLWg4BxfKbOMDApw\n" +
            "hxwNL8PoYUgjs3eb5Y5wnNcSsU9fAgMBAAECgYEAi0SSY0/otg0Hn2+lUb8NTfI0\n" +
            "lEE2rSoDBpAEP43KnQeYrbXmDqvX5qIj6nnMVpjIs+Qyw3FqXJQ0/mmQ6fSTuz9Q\n" +
            "7GvTKoBIsqXS9UP3HX8L16qkaG1gf7ZqcpVv2uNp0S5xqR9/9KyPAq/EaruB2h2i\n" +
            "DtDvpvTUp5hs8BmPVHkCQQDr7QGQVg7rbSC2oJsEHsTW6G2TtYE5DO6u6QepPPzH\n" +
            "CFHjPr1Vp59edZ6huHAfE9at5neBBRK5Zov/iuCr+aPjAkEA56QLwUfnwdWC5m50\n" +
            "BDDKijIrY/ldeUzU+MSQ4DWCaJn8z/D/OoET7fQGHBa0mcIaV5WeaVdUN+ZPpTaI\n" +
            "GcGXVQJBANnY4D/J5z7mZnWcOWeFjdavijcBO1BVq7Pgw003XmXePdbq4ONNw2/y\n" +
            "hcboysc3h1zOWXzOFWsS0KTnNOw3uGcCQQCTNzPtU7ib/QkjT+FV5HhkmfvvnsTk\n" +
            "WYskyzBT/k3yEqOUuFJNKUm0OhlD8r421iCNt14lDsZMfIfztROMYve9AkAd8PPj\n" +
            "YLOwlLptxLd7s+AVSlD+MaKPP4wGT2v+VI2UdfBGdrCHftUk+KoA+g8zFbJ7LEgJ\n" +
            "KegdluL6Vbu9re79\n";


    /**
     * 用于生成测试连接
     * @throws UnsupportedEncodingException
     */
    public static void generateUrl() throws UnsupportedEncodingException {
        Map<String, String> bizParams = Maps.newHashMap();

        bizParams.put("idNum", "110");
        bizParams.put("name", "张三");
        bizParams.put("mobile", "120");
        bizParams.put("openId", "2.0:nGbYvrWoqYiwpL3li9GStHqcDgY=");


        String key = AESUtils.generateAESKey();
        String encryptedParam = AESUtils.encrypt(JSON.toJSONString(bizParams), key);
        String encryptedKey = RSAUtils.encryptByPublicKey(key, PARTNER_PUBLIC_KEY);

        Map<String, String> signMaps = Maps.newTreeMap();
        signMaps.put("key", encryptedKey);
        signMaps.put("params", encryptedParam);

        String toSign = signMaps.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));

        System.out.println("sign content:" + toSign);
        String sign = RSAUtils.signByPrivateKey(toSign, MI_PRIVATE_KEY);
        String url = "https://deadend.mi.com?channel=xxxx&"
                + "params=" + URLEncoder.encode(encryptedParam, Charsets.UTF_8.name())
                + "&sign=" + URLEncoder.encode(sign, Charsets.UTF_8.name())
                + "&key=" + URLEncoder.encode(encryptedKey, Charsets.UTF_8.name());
        System.out.println("url:" + url);
    }


//   test url
//   url:https://deadend.mi.com?channel=xxxx&params=EUQavO9efnm4IG6aKPikCdgZa1s2E9%2BBFVOLnSxrX8wJekMhTnUql7eA%2By0geA%2BIPPrI5yGcmbYT%0D%0AUrOwZ2s9ZJsshRwHcqB77CMp%2BNksFgYQUiRcZfh10GdvV8aWnrOr%0D%0A&sign=DVrnhWYI9xxtHegUbuy9vAKZDae3Lv8hHoHsdMN9Qj1QoWSldGDmWukOjcBA2-58aQAPFX_HyP5R0ST4hE7xybISSGM6SIInXhcR4tT8HY_nATLxSchxW9PMSVbSpz31hm31nT7yME0MAdPLPg486omETHX6AxAM9WNd9ZIWy1Q&key=meAseRWy9kC5rV0QFKeBQkfQsvy7Xr2yFQv%2FyT4OdtvN0KGda1Hwgw%2Bm8NHMjnegqtVmzIPSwo%2BU%0D%0APT2tGaovRFhAwg6vXXGJvKW8Smc5lrd1%2Fy8g3jzT08PjDeiVx9xwzBcGdTv4Vxyu%2FNSUMGDrzgf2%0D%0AINICSbQKIBFwFz8MMEE%3D%0D%0A

    public static void parseUrl() throws UnsupportedEncodingException {
        Map<String, String> urlParams = Maps.newHashMap();
        urlParams.put("params", "EUQavO9efnm4IG6aKPikCdgZa1s2E9%2BBFVOLnSxrX8wJekMhTnUql7eA%2By0geA%2BIPPrI5yGcmbYT%0D%0AUrOwZ2s9ZJsshRwHcqB77CMp%2BNksFgYQUiRcZfh10GdvV8aWnrOr%0D%0A");
        urlParams.put("sign", "DVrnhWYI9xxtHegUbuy9vAKZDae3Lv8hHoHsdMN9Qj1QoWSldGDmWukOjcBA2-58aQAPFX_HyP5R0ST4hE7xybISSGM6SIInXhcR4tT8HY_nATLxSchxW9PMSVbSpz31hm31nT7yME0MAdPLPg486omETHX6AxAM9WNd9ZIWy1Q");
        urlParams.put("key", "meAseRWy9kC5rV0QFKeBQkfQsvy7Xr2yFQv%2FyT4OdtvN0KGda1Hwgw%2Bm8NHMjnegqtVmzIPSwo%2BU%0D%0APT2tGaovRFhAwg6vXXGJvKW8Smc5lrd1%2Fy8g3jzT08PjDeiVx9xwzBcGdTv4Vxyu%2FNSUMGDrzgf2%0D%0AINICSbQKIBFwFz8MMEE%3D%0D%0A");

        for (Map.Entry<String, String> key : urlParams.entrySet()) {
            key.setValue(URLDecoder.decode(key.getValue(), Charsets.UTF_8.name()));
        }

        Map<String, String> signMaps = Maps.newTreeMap();
        signMaps.put("params", urlParams.get("params"));
        signMaps.put("key", urlParams.get("key"));
        String toSign = signMaps.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining("&"));

        boolean verifyResult = RSAUtils.verifySignByPublicKey(toSign, urlParams.get("sign"), MI_PUBLIC_KEY);

        String aesKey = RSAUtils.decryptByPrivateKey(urlParams.get("key"), PARTNER_PRIVATE_KEY);
        String paramsStr = AESUtils.decrypt(urlParams.get("params"), aesKey);
        System.out.println("verifyResult: " + verifyResult);
        System.out.println("key: " + aesKey);
        System.out.println("params: " + paramsStr);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        parseUrl();
    }
}
