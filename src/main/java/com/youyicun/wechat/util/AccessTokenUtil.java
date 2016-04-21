package com.youyicun.wechat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import sun.security.pkcs11.P11Util;

import java.io.IOException;
import java.util.Map;

/**
 * Created by johnny on 16/4/8.
 */
public class AccessTokenUtil {
    public static ObjectMapper mapper = new ObjectMapper();
    public static final String APPID = "wxbcb7908a2a4a0cb0";
    public static final String APPSECRET = "388a3fbbf105b12affb6809f4160cc51";
    public static final String CLIENT_CREDENTIAL_GET = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String USER_INFO_ACCESS_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    private static final String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    public static String token = null;
    public static Long expired = 0L;

    public static String getToken() throws IOException {
        if (token == null || System.currentTimeMillis() > expired) {
            String url = CLIENT_CREDENTIAL_GET.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
            Map<String,Object> map = mapper.readValue(HttpClientUtil.doGet(url),Map.class);
            token = (String) map.get("access_token");
            expired = System.currentTimeMillis() + (Integer)map.get("expires_in");
        }
        return token;
    }


    public static Map<String,Object> getUserInfoAccess(String code) throws IOException {
        String url = USER_INFO_ACCESS_URL.replace("APPID",APPID).replace("SECRET",APPSECRET).replace("CODE",code);
        Map<String,Object> map = mapper.readValue(HttpClientUtil.doGet(url),Map.class);
        return map;
    }

    public static Map<String,Object> getUserInfo(String openId) throws IOException{
        String url = USER_INFO_URL.replace("ACCESS_TOKEN",getToken()).replace("OPENID",openId);
        Map<String,Object> map = mapper.readValue(HttpClientUtil.doGet(url),Map.class);
        return map;
    }
}
