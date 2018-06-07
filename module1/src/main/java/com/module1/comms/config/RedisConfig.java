package com.module1.comms.config;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
    @Bean("redisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Value("${myRedis.host}")String hostName,
                                                   @Value("${myRedis.port}")int port,
                                                   @Value("${myRedis.password}")String password,
                                                   @Value("${myRedis.max-idle}")int maxIdle,
                                                   @Value("${myRedis.index}")int index
                                                   ){
        StringRedisTemplate temple = new StringRedisTemplate();
        temple.setConnectionFactory(connectionFactory(hostName, port, password,
                maxIdle,  index));
        return temple;
    }

    public RedisConnectionFactory connectionFactory(String hostName, int port,
                                                    String password, int maxIdle,int index
                                                     ) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        RedisStandaloneConfiguration config = jedis.getStandaloneConfiguration();
        config.setHostName(hostName);
        config.setPort(port);
        if (!StringUtils.isEmpty(password)) {
            RedisPassword p = RedisPassword.of(password);
            config.setPassword(p);
        }
        if (index != 0) {
            config.setDatabase(index);
        }
        jedis.getClientConfiguration().getPoolConfig().get().setMaxIdle(maxIdle);
//        jedis.getClientConfiguration().getPoolConfig().get().setMaxTotal(maxTotal);
//        jedis.getClientConfiguration().getPoolConfig().get().setMaxWaitMillis(maxWaitMillis);
//        jedis.getClientConfiguration().getPoolConfig().get().setTestOnBorrow(testOnBorrow);
//        jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis,
//                testOnBorrow));
        // 初始化连接pool
        jedis.afterPropertiesSet();
        RedisConnectionFactory factory = jedis;

        return factory;
    }
//    public JedisPoolConfig poolCofig(int maxIdle, int maxTotal,
//                                     long maxWaitMillis, boolean testOnBorrow) {
//        JedisPoolConfig poolCofig = new JedisPoolConfig();
//        poolCofig.setMaxIdle(maxIdle);
//        poolCofig.setMaxTotal(maxTotal);
//        poolCofig.setMaxWaitMillis(maxWaitMillis);
//        poolCofig.setTestOnBorrow(testOnBorrow);
//        return poolCofig;
//    }
}
