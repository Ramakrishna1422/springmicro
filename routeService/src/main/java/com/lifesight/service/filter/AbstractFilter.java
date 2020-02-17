package com.lifesight.service.filter;

import com.lifesight.service.constants.RateLimitType;
import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public abstract class AbstractFilter extends ZuulFilter {

    public final static String SEPERATOR = "_";

    protected String getKey(HttpServletRequest request, boolean isApiLevel, boolean isMethodIncluded) {
        String[] tokens = request.getServletPath().split("/");
        StringBuilder key = new StringBuilder();

        key.append(tokens[1]).append(SEPERATOR);
        if(isApiLevel) {
            key.append(tokens[2]);
        } else {
            key.append(RateLimitType.GLOBAL);
        }

        if(isMethodIncluded) {
            key.append(SEPERATOR).append(request.getMethod());
        }
        return key.toString();
    }
}
