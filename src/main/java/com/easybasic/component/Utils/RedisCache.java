package com.easybasic.component.Utils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;

/*
 * 使用第三方缓存服务器，处理二级缓存
 */
public class RedisCache implements Cache {

    public static String CachePrex = PropertyUtil.getProperty("cacheprex");
    private static RedisCache instance;
    public static synchronized RedisCache getInstance(){
        if (instance == null) {
            instance = new RedisCache("EasyDS");
        }
        return instance;
    }

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    public RedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;

    }

    public String getId() {
        return this.id;
    }

    public void putObject(Object key, Object value) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            jedis.set(SerializeUtil.serialize(key.toString()),
                    SerializeUtil.serialize(value));
        }
        catch(Exception ex)
        {

        }
        finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public void putObject(Object key, Object value, int expireSeconds) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            jedis.set(SerializeUtil.serialize(key.toString()),
                    SerializeUtil.serialize(value));
            jedis.expire(SerializeUtil.serialize(key.toString()), expireSeconds);
        }
        catch(Exception ex)
        {

        }
        finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public Object getObject(Object key) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            Object value = SerializeUtil.unserialize(jedis.get(
                    SerializeUtil.serialize(key.toString())));
            return value;
        }
        catch(Exception ex)
        {
            return null;
        }
        finally {
            if(jedis != null)
            {
                jedis.close();
            }
        }
    }

    public Object removeObject(Object key) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            return jedis.expire(
                    SerializeUtil.serialize(key.toString()), 0);
        }
        catch (Exception ex)
        {
            return null;
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
    }

    public void clear() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            jedis.flushDB();
        }
        catch(Exception ex)
        {

        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
    }

    public int getSize() {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            return Integer.valueOf(jedis.dbSize().toString());
        }
        catch(Exception ex)
        {
            return 0;
        }
        finally {
            if(jedis!=null)
            {
                jedis.close();
            }
        }
    }

    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}