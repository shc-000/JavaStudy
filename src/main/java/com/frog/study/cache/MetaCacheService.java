package com.frog.study.cache;


import java.util.concurrent.TimeUnit;

/**
 * @author shaohaichao
 * @version 2.0.0
 * @since 2021/5/30 4:53 下午
 */
public class MetaCacheService extends AbstractGuavaCache {

    private MetaCacheService() {
        // 缓存过期时间不设置
        this.setExpireDuration(-1);
        // 缓存刷新周期时间格式
        this.setExpireTimeUnit(TimeUnit.MINUTES);
        // 这里10分钟刷新
        this.setRefreshDuration(1);
        // 缓存最大容量
        this.setMaxSize(1000);
    }

//    public StatisicService getStatisicService() {
//        return statisicService;
//    }

//    public static MetaCacheService getInstance() {
//        if (metaCacheService == null) {
//            synchronized (MetaCacheService.class) {
//                if (metaCacheService == null) {
//                    metaCacheService = new MetaCacheService();
//                }
//            }
//        }
//        return metaCacheService;
//    }

    @Override
    public void loadValueWhenStarted() {
        // TODO Auto-generated method stub
//        getValue(dbType.getKey());
    }

    @Override
    protected Object getValueWhenExpired(Object key) {
        Object result = null;
        /*if (key != null) {
            metaCacheService.setDbType(StatisicTypeEnum.valueOf((String) key));
        }
        switch (dbType) {
            case appPre:
                try {
                    result = statisicService.getappPre();
                } catch (Exception e) {
                    logger.error("获取左侧展示数据出错：" + e.getMessage(), e);
                    e.printStackTrace();
                }
                break;
            case dbInfos:
                try {
                    result = statisicService.getDbInfos();
                } catch (Exception e) {
                    logger.error("获取数据资产总揽出错：" + e.getMessage(), e);
                    e.printStackTrace();
                }
                break;
            case statusManage:
                try {
                    result = statisicService.getStatusManage();
                } catch (Exception e) {
                    logger.error("获取数据逻辑视图出错：" + e.getMessage(), e);
                    e.printStackTrace();
                }
                break;
            case treeInfos:
                try {
                    result = statisicService.getTreeInfos();
                } catch (Exception e) {
                    logger.error("获取数据逻辑视图出错：" + e.getMessage(), e);
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }*/
        return result;
    }
}
