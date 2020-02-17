package com.lifesight.service.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Getter
@Setter
@Slf4j
public class RateLimiter {
    private int limit;
    private volatile double available;
    private long interval;
    private long lastTimeStamp;

    public RateLimiter(int limit, long interval) {
        this.limit = limit;
        this.interval = interval;
        this.available = 0;
        this.lastTimeStamp = System.currentTimeMillis();
    }

    public boolean consume() {
        long now = System.currentTimeMillis();
        double diff = ((now - lastTimeStamp) / 1000);
        if( diff < interval) {
            if(available < limit) {
                synchronized(this) {
                    available++;
                    lastTimeStamp = now;
                    return true;
                }
            } else {
                return false;
            }
        } else {
            available = 0;
            lastTimeStamp = now;
            available++;
            return true;
        }
    }

    @Override
    public String toString() {
        return "RateLimiter{" + super.hashCode() +
                ", limit=" + limit +
                ", available=" + available +
                ", interval=" + interval +
                ", lastTimeStamp=" + new Date(lastTimeStamp) +
                '}';
    }
}
