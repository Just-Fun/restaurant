package com.serzh.cache;

import com.google.common.cache.CacheBuilder;
import com.serzh.repository.UserRepository;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CachingConfig {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CachingConfig() {
        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                        // Number of objects in each cache entry
                        ResourcePoolsBuilder.heap(1_000))
//                        ResourcePoolsBuilder.heap(2))
                        // By default objects stay 1 hour in the cache
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(3600)))
                        .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, UserRepository.USERS_BY_NAME_CACHE);
            createCache(cm, UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, UserRepository.USERS_CACHE);
           /* createCache(cm, domain.User.class.getName());
            createCache(cm, domain.Authority.class.getName());
            createCache(cm, domain.User.class.getName() + ".authorities");
            createCache(cm, domain.User.class.getName() + ".persistentTokens");*/
            // needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

    /*@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("books");
    }*/

    /*@Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(
                new ConcurrentMapCache("directory"),
                new ConcurrentMapCache("addresses")));
        return cacheManager;
    }*/

    //    @Bean("habrCacheManager")
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCache(
                        name,
                        CacheBuilder.newBuilder()
                                .expireAfterWrite(1, TimeUnit.SECONDS)
                                .build().asMap(),
                        false);
            }
        };
    }

   /* @Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("categoryCache");
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        cacheConfiguration.setMaxEntriesLocalHeap(1000);


        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        //config.addCache(cacheConfiguration);
        config.defaultCache(cacheConfiguration);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }*/

}