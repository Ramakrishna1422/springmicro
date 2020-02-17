package com.lifesight.service.filter;

import com.lifesight.service.config.RateLimiter;
import com.lifesight.service.manager.RateLimiterManager;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class LimitPreFilter extends AbstractFilter {

    @Autowired
    private RateLimiterManager manager;

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

        //Global check
        RateLimiter rateLimiter = manager.getRateLimiter(getKey(request, false, true));
        if(rateLimiter != null) {
            System.out.println("Global .......................................>"+ rateLimiter);
            if(!rateLimiter.consume()) {
                ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
                ctx.setResponseBody("Ratelimit exceeded. Please try after sometime.");
                return null;
            }
        }

        //API Level check
        rateLimiter = manager.getRateLimiter(getKey(request, true, true));
        if(rateLimiter != null) {
            System.out.println("API : ===========================> " + rateLimiter.toString());
            if(!rateLimiter.consume()) {
                ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
                ctx.setResponseBody("Ratelimit exceeded. Please try after sometime.");
                return null;
            }
        }

        return null;
    }
}
