package rateLimit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class CounterLimiterWithGuava {
    public static void main(String[] args) throws ExecutionException {
        LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.SECONDS)
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long second) throws Exception {
                    return new AtomicLong(0);
                }
            });
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();
        while (counter.get(1l).incrementAndGet() > 1000) {
            System.out.println("被限流了");;
        }
    }
}
