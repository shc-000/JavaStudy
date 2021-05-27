package com.frog.study.design;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/3/25
 */
@Slf4j
public class XyzService {
    /**
     * High level逻辑封装在抽象父类AbsUpdateFromMQ中，形成一个业务逻辑的模板；
     * 子类（一个匿名内部类）像填充模板一样实现Low level逻辑；
     * 父子类都作为外层Service组件的内部类存在，因此把这种对Template Method模式的特定使用风格形象的称为NestedBusinessTemplat；
     */
    abstract static class AbsUpdateFromMq {
        public final void doProcess(String jsonStr) {
            try {
                JSONObject json = doParseAndValidate(jsonStr);
                cache2Redis(json);
                saveJsonStr2CrawingTask(json);
                updateZmScore4Dperson(json);
            } catch (Exception e) {
                log.error("更新my MQ授权信息失败", e);
                //throw new AppException(e.getMessage(), e);
            }
        }

        protected abstract void updateZmScore4Dperson(JSONObject json);

        protected abstract void saveJsonStr2CrawingTask(JSONObject json);

        protected abstract void cache2Redis(JSONObject json);

        protected abstract JSONObject doParseAndValidate(String json);
    }

    public static void main(String[] args) {
        XyzService service = new XyzService();
        service.doSome("json----test");
    }

    public void doSome(String jsonStr) {
        new AbsUpdateFromMq() {
            @Override
            protected void updateZmScore4Dperson(JSONObject json) {
                System.out.println("updateZmScore4Dperson-----");
            }

            @Override
            protected void saveJsonStr2CrawingTask(JSONObject json) {
                System.out.println("saveJsonStr2CrawingTask-----");
            }

            @Override
            protected void cache2Redis(JSONObject json) {
                System.out.println("cache2Redis-----");
            }

            @Override
            protected JSONObject doParseAndValidate(String json) {
                System.out.println("doParseAndValidate-----");
                return null;
            }
        }.doProcess(jsonStr);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    public void processAuthResultDataCallback(String compress) {
        new AbsUpdateFromMq() {
            @Override
            protected void updateZmScore4Dperson(JSONObject json) {
                //保存芝麻分到xyz
                if ("3".equals(json.getString("type"))) {
                    String data = json.getString("data");
                    Integer zmf = JSON.parseObject(data).getJSONObject("taobao_user_info").getInteger("zm_score");
//                    Map param = new HashMap();
//                    param.put("phoneNumber", json.getString("mobile"));
                    /*List<Dperson> list1 = personBaseDaoI.find("from xyz where phoneNumber=:phoneNumber", param);
                    if (list1 !=null){
                        for (Dperson dperson:list1){
                            dperson.setZmScore(zmf);
                            personBaseDaoI.saveOrUpdate(dperson);
                            AppFlowUtil.updateAppUserInfo(dperson.getToken(),null,null,zmf);
                        }
                    }*/
                }
            }

            @Override
            protected void saveJsonStr2CrawingTask(JSONObject json) {
                /*Map map = new HashMap();
                map.put("type",CrawlingTaskType.get(json.getInteger("type")));
                map.put("mobile", json.getString("mobile"));
                List<CrawlingTask> list = baseDAO.find("from crt c where c.phoneNumber=:mobile and c.taskType=:type", map);
                CrawlingTask crawlingTask = null;
                // providType:（0：xx，1yy支付宝，2：zz淘宝,3:tt淘宝）
                if (CollectionUtils.isNotEmpty(list)){
                    crawlingTask = list.get(0);
                    crawlingTask.setJsonStr(json.getString("data"));
                }else{
                    //新增
                    crawlingTask = new CrawlingTask(UUID.randomUUID().toString(), json.getString("data"),
                            json.getString("mobile"), CrawlingTaskType.get(json.getInteger("type")));
                    crawlingTask.setNeedUpdate(true);
                }
                baseDAO.saveOrUpdate(crawlingTask);*/
            }

            @Override
            protected void cache2Redis(JSONObject json) {
//                redisClientTemplate.set(json.getString("mobile") + "_" + json.getString("type"),CompressUtil.compress( json.getString("data")));
//                redisClientTemplate.expire(json.getString("mobile") + "_" + json.getString("type"), 2*24*60*60);
            }

            @Override
            protected JSONObject doParseAndValidate(String json) {
                JSONObject object = JSON.parseObject(json);
//                if (StringUtils.isBlank(object.getString("type")) || StringUtils.isBlank(object.getString("mobile")) || StringUtils.isBlank(object.getString("data"))){
//                    throw new AppException("MQ返回参数异常");
//                }
//                logger.info(object.getString("mobile")+"<<<<<<<<<获取来自MQ的授权数据>>>>>>>>>"+object.getString("type"));
                return object;
            }
        }.doProcess(compress);
    }
}
