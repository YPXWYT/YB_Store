package com.tna.yb_store.utils.redis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private static Logger logger = LogManager.getLogger(RedisUtil.class.getName());

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    /**
     * 写入缓存
     *  单个K-V
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){

        boolean result = false;
        try {
            logger.info("开始写入缓存");
            ValueOperations<Object, Object> objectValueOperations = redisTemplate.opsForValue();
            objectValueOperations.set(key,value);
            result = true;
        } catch (Exception e) {
            logger.error("缓存出错");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            logger.info("开始写入缓存");
            ValueOperations<Object, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            logger.error("缓存出错");
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 读取缓存
     *
     * @param key key
     * @return 结果
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Object, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys  多个值
     */
    public void remove(final String... keys) {
        logger.info("开始移除Key");
        for (String key : keys) {
            remove(key);
        }
    }



    /**
     * 删除对应的value
     *
     * @param key  单个 key
     */
    public void remove(final String key) {
        logger.info("开始移除Keys");
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key key
     * @return 是否
     */
    private boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

}