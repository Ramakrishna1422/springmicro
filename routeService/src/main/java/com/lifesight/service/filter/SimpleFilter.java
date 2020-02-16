package com.lifesight.service.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.internal.LoadingCache;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

public class SimpleFilter extends  ZuulFilter {

    private LoadingCache<String, String, Integer> counterCache;
    private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);
    int counter = 0;



    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s request to %s", request.getContextPath(), request.getMethod(), request.getRequestURL().toString()));
        if(counter >= 10) {
            ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            ctx.put("rateLimitExceeded", "true");
            ctx.setSendZuulResponse(false);
        }
        counter++;

        return null;
    }
}
