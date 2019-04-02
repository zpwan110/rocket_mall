package com.zpwan.ssoserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zpwan.appcommon.model.dto.UserDto;
import com.zpwan.ssoserver.model.UserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Map;

/**
 * @describe:JWT 使用场景:一种情况是webapi,另一种情况是多web服务器下实现无状态分布式身份验证。 JWT是JSON Web Token的缩写，即JSON Web令牌。JSON Web令牌（JWT）是一种紧凑的、URL安全的方式， 用来表示要在双方之间传递的“声明”。JWT中的声明被编码为JSON对象，用作JSON Web签名（JWS）结构的有效内容或JSON Web加密（JWE）结构的明文，使得声明能够被：数字签名、 或利用消息认证码（MAC）保护完整性、加密。
 * @author:houkai
 * @Date: 2018/3/26 16:49
 */
public class JWTUtils {

    public static Key generatorKey(){
        SignatureAlgorithm saa= SignatureAlgorithm.HS256;
        byte[] bin = DatatypeConverter.parseBase64Binary("209ajdkjasdha90ws9878970edsds");
        Key key = new SecretKeySpec(bin,saa.getJcaName());
        return key;
    }
    public static String generatorToken(Map<String,Object> payLoad) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           return Jwts.builder().setPayload((objectMapper.writeValueAsString(payLoad)))
                    .signWith(SignatureAlgorithm.HS256,generatorKey()).compact();
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return null;
    }

    public static Claims phaseToken(String token){
        Jws<Claims> claimsJws =Jwts.parser().setSigningKey(generatorKey()).parseClaimsJws(token);
        return claimsJws.getBody();
    }


    public static void main(String[] args) throws Exception {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJyZWFsTmFtZSI6IuadjuaZk-WzsCIsIm1vYmlsZSI6IjE4NTU3NTE4ODk5IiwiaGVhZGVyVXJsIjpudWxsLCJleHAiOjE1NTI0NjkzNDUsInVzZXJJZCI6Mn0.pMb7qJDymjakTkZtrtzz2It7pYog1md4h2DS2ZJiA2E";
        Claims claims = JWTUtils.phaseToken(token);
        System.out.print("claims"+claims.toString());
    }

    public String getUsernameFromToken(String auth_token) {

        return null;
    }

    public UserDetail getUserFromToken(String auth_token) {

        return null;
    }

    public boolean validateToken(String auth_token, UserDetail user) {

        return false;
    }
}
