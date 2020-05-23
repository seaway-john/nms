package com.oem.nms.common.swagger.util;

import com.oem.nms.common.entity.db.admin.RoleType;
import com.oem.nms.common.entity.request.RequestInfo;
import com.oem.nms.common.util.Constants;
import com.oem.nms.common.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author Seaway John
 */
public class HttpUtil {

    private HttpUtil() {
    }

    public static RequestInfo convert(HttpServletRequest request) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setIp(request.getHeader(Constants.HEADER_IP));
        requestInfo.setUsername(request.getHeader(Constants.HEADER_USERNAME));

        Set<String> roles = JsonUtil.fromJson(request.getHeader(Constants.HEADER_AUTHORITIES), Set.class);
        if (roles != null) {
            roles.forEach(role -> requestInfo.getRoles().add(RoleType.valueOf(role)));
        }

        return requestInfo;
    }

}
