package com.trafiklab.bus.lines.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Component that clears the data from cache, so the subsequent requests can fetch fresh data into the cache.
 * Trafiklab refreshes it's data between 00:00 and 2:00 hours, so this component is scheduled to run at 3 am.
 */
@Component
public class CacheRefresher {

    @Autowired
    CacheManager cacheManager;

    /**
     * clears all the caches in the application
     */
    @Scheduled(cron = "0 3 * * * *") // runs at 3 am daily
    public void refreshAllCaches() {
        cacheManager.getCacheNames()
                .forEach(cacheName ->
                        Optional.ofNullable(cacheManager.getCache(cacheName))
                                .ifPresent(Cache::clear));
    }
}
