package com.lifesight.service.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lifesight.service.config.RateLimiter;
import com.lifesight.service.constants.RateLimitType;
import com.lifesight.service.filter.AbstractFilter;
import com.lifesight.service.generated.ApiLimit;
import com.lifesight.service.generated.Config;
import com.lifesight.service.generated.Configuration;
import com.lifesight.service.generated.Methods;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import javax.inject.Singleton;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Singleton
@Repository
public class RateLimiterManager {

    private Map<String, RateLimiter> data = new ConcurrentHashMap<>();

    public RateLimiterManager() {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("ratelimit-configuration.json")) {
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            init(result);
        } catch (Exception e) {
            log.error("Error while loading config", e);
        }
    }

    public boolean init(String jsonData) {
        if(jsonData != null && !jsonData.isEmpty()) {
            data = new ConcurrentHashMap<>();
            Gson gson = new Gson();
            List<Configuration> configurations = gson.fromJson(jsonData,new TypeToken<List<Configuration>>() {}.getType());
            for(Configuration configuration : configurations) {
                String host = configuration.getService();
                if(configuration.getGlobalLimits() != null) {
                    Methods methods = configuration.getGlobalLimits();
                    if(methods != null) {
                        findAndAddMethod(host + AbstractFilter.SEPERATOR + RateLimitType.GLOBAL, data, methods);
                    }
                }
                for(ApiLimit apiLimit : configuration.getApiLimits()) {
                    Methods methods = apiLimit.getMethods();
                    if(methods != null && apiLimit.getApi() != null) {
                        findAndAddMethod(host + AbstractFilter.SEPERATOR + apiLimit.getApi(), data, methods);
                    }
                }
            }
        }

        log.info(data.toString());
        return false;
    }

    private void findAndAddMethod(String rootKey, Map<String, RateLimiter> data, Methods methods) {
        Config config = null;
        if(methods.getPost() != null) {
            config = methods.getPost();
            long interval = getInterval(config.getGranularity());
            data.put(rootKey + AbstractFilter.SEPERATOR + "POST", new RateLimiter(config.getLimit(), interval));
        }
        if(methods.getGet() != null) {
            config = methods.getGet();
            long interval = getInterval(config.getGranularity());
            data.put(rootKey + AbstractFilter.SEPERATOR + "GET", new RateLimiter(config.getLimit(), interval));
        }
        if(methods.getDelete() != null) {
            config = methods.getDelete();
            long interval = getInterval(config.getGranularity());
            data.put(rootKey + AbstractFilter.SEPERATOR + "DELETE", new RateLimiter(config.getLimit(), interval));
        }
    }

    private long getInterval(String granularity) {
        switch (granularity) {
            case "second" :
                return 1;
            case "minute" :
                return 60;
            case "hour" :
                return 60 * 60;
        }
        return 0;
    }

    public Map<String, RateLimiter> getData() {
        return data;
    }

    public RateLimiter getRateLimiter(String key) {
        return data.get(key);
    }
}
