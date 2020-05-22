package com.oem.nms.gateway.util;

import com.oem.nms.common.entity.response.Response;
import com.oem.nms.common.entity.response.ResponseCode;
import com.oem.nms.common.util.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import java.nio.charset.StandardCharsets;

/**
 * @author Seaway John
 */
public class JwtUtil {

    private JwtUtil() {
    }

    public static Response<Claims> parse(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.JWT_SIGNATURE_KEY.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();

            return new Response<>(claims);
        } catch (ExpiredJwtException e) {
            return new Response<>(ResponseCode.ERROR_USER_EXPIRED_TOKEN);
        } catch (Exception e) {
            return new Response<>(ResponseCode.ERROR_USER_INVALID_TOKEN);
        }
    }

}
