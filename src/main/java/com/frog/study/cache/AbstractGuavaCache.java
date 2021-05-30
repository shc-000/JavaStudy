package com.frog.study.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/5/30 4:50 下午
 */
@Slf4j
public abstract class AbstractGuavaCache<K, V> {
    //public final static Logger logger = LoggerFactory.getLogger(AbstractGuavaCache.class);

    // 缓存自动刷新周期
    protected int refreshDuration = 10;
    // 缓存刷新周期时间格式
    protected TimeUnit refreshTimeunit = TimeUnit.MINUTES;
    // 缓存过期时间（可选择）
    protected int expireDuration = -1;
    // 缓存刷新周期时间格式
    protected TimeUnit expireTimeunit = TimeUnit.HOURS;
    // 缓存最大容量
    protected int maxSize = 4;
    // 数据刷新线程池
    protected static ListeningExecutorService refreshPool = MoreExecutors
            .listeningDecorator(Executors.newFixedThreadPool(20));
    //缓存的数据源类型
   // protected StatisicTypeEnum dbType;

    private LoadingCache<K, V> cache = null;

//    public StatisicTypeEnum getDbType() {
//        return dbType;
//    }

//    public void setDbType(StatisicTypeEnum dbType) {
//        this.dbType = dbType;
//    }

    /**
     * @Description: 用于manager启动时初始化Tree缓存值
     * @version：v1.3.0
     * @author: shaohaichao
     * @date: 2018年7月11日 下午6:05:53
     */
    public abstract void loadValueWhenStarted();

    /**
     * @Description: 从数据源中拿出数据
     * @param: key
     * @return：value
     * @throws：Exception @version：v1.3.0
     * @author: shaohaichao
     * @date: 2018年7月11日 下午6:08:10
     */
    protected abstract V getValueWhenExpired(K key);


    /**
     * @Description: 从缓存中拿数据（如果没有则访问数据源）
     * @param: key
     * @return：value
     * @throws：Exception @version：v1.3.0
     * @author: shaohaichao
     * @date: 2018年7月11日 下午6:09:34
     */
    public synchronized V getValue(K key) {
        V v = null;
        try {
            v = getCache().get(key);
        } catch (Exception e) {
            log.error("从内存缓存中获取内容时发生异常，key: " + key, e);
            e.printStackTrace();
        }
        return v;
    }

    public V getValueOrDefault(K key, V defaultValue) {
        try {
            return getCache().get(key);
        } catch (Exception e) {
            // logger.error("从内存缓存中获取内容时发生异常，key: " + key, e);
            return defaultValue;
        }
    }

    /**
     * 设置基本属性
     */
    public AbstractGuavaCache<K, V> setRefreshDuration(int refreshDuration) {
        this.refreshDuration = refreshDuration;
        return this;
    }

    public AbstractGuavaCache<K, V> setRefreshTimeUnit(TimeUnit refreshTimeunit) {
        this.refreshTimeunit = refreshTimeunit;
        return this;
    }

    public AbstractGuavaCache<K, V> setExpireDuration(int expireDuration) {
        this.expireDuration = expireDuration;
        return this;
    }

    public AbstractGuavaCache<K, V> setExpireTimeUnit(TimeUnit expireTimeunit) {
        this.expireTimeunit = expireTimeunit;
        return this;
    }

    public AbstractGuavaCache<K, V> setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public void clearAll() {
        this.getCache().invalidateAll();
    }

    /**
     * @Description: 获取cache实例
     * @version：v1.3.0
     * @author: shaohaichao
     * @date: 2018年7月11日 下午6:12:45
     */
    private LoadingCache<K, V> getCache() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(maxSize);

                    if (refreshDuration > 0) {
                        cacheBuilder = cacheBuilder.refreshAfterWrite(refreshDuration, refreshTimeunit);
                    }
                    if (expireDuration > 0) {
                        cacheBuilder = cacheBuilder.expireAfterWrite(expireDuration, expireTimeunit);
                    }

                    cache = cacheBuilder.build(new CacheLoader<K, V>() {
                        public V load(K key) throws Exception {
                            return getValueWhenExpired(key);
                        }

                        @Override
                        public ListenableFuture<V> reload(final K key, V oldValue) throws Exception {
                            return refreshPool.submit(new Callable<V>() {
                                public V call() throws Exception {
                                    return getValueWhenExpired(key);
                                }
                            });
                        }
                    });
                }
            }
        }
        return cache;
    }
}
