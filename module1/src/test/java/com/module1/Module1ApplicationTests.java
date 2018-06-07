package com.module1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Module1ApplicationTests {
    @Autowired
    @Qualifier("redisTemplate")
    private StringRedisTemplate template;
//    @Autowired
//    private Jedis jedis;
    @Test
    public void contextLoads() {
//        template.opsForValue().set("test","test");
        RedisConnectionFactory c = template.getConnectionFactory();
        RedisConnectionFactory c2 = template.getRequiredConnectionFactory();
        template.opsForValue().set("db1","db1");
        System.out.println("======");
//        jedis.getSet("test","ttt");
    }

}
