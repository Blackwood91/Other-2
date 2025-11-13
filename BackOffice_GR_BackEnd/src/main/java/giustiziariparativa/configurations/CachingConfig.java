package giustiziariparativa.configurations;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("cacheVE");
    }
    
    
//    @Bean
//    public addUserCache {
//        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
//        List<Cache> caches = new ArrayList<>();
//
//        //Failure after 5 minutes of caching
//        caches.add(new GuavaCache(CacheConfig.USER_CACHE,
//                CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build()));
//
//        simpleCacheManager.setCaches(caches);
//
//        return simpleCacheManager;
//
//    }
    
}
