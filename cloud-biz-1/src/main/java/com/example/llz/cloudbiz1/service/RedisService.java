package com.example.llz.cloudbiz1.service;

import com.example.llz.cloudbiz1.entity.Person;
import com.example.llz.cloudbiz1.iService.IRedisService;
import com.example.llz.cloudbiz1.utils.RedisUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * SpringBoot 整合 Redis，常见的两种使用方式：redisTemplate 和 @Cacheable 注解。
 * redisTemplate 是常见 spring-data-redis 提供的用法，一般我们会封装一个 RedisUtil 工具类来方便我们调用。
 * @Cacheable 和 @CacheEvict 注解也是 Spring 提供的注解，我们知道注解一般是基于 AOP 实现，通过在注解方法前后进行一些操作，简化开发者的代码。

 * (1) 查询操作：先查询 Redis，如果Redis没有则再查询数据库，并添加到 Redis
 *
 * (2) 删除操作：需要先删除数据库数据，再删除Redis中的
 *
 * (3) 更新操作:  更新数据库数据，删除 Redis 数据，当然也可以再添加 Redis 记录
 */

@Service("redisService")
public class RedisService implements IRedisService {

    private static int ExpireTime = 60;   // redis中存储的过期时间60s
    /**
     *  @Resource 的作用相当于@Autowired，只不过@Autowired按byType自动注入，而@Resource默认按byName自动注入罢了。
     *  @Resource 有两个属性是比较重要的，分是name和type，Spring将@Resource注解的name属性解析为bean的名字，
     *  而type属性则解析为bean的类型。所以如果使用name属性，则使用byName的自动注入策略，而使用type属性时,
     *  则使用byType自动注入策略。如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。
     *  @Resource装配顺序 1. 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常 
     *                  2. 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常 　　
     *                  3. 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常 　　
     *                  4. 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；
     *                     如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配； 
     */

    /*** 1、以下使用RedisTemplate实现***/
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean redisTemplateSet(String key, String value) {
        Person person =new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("llz");
        person.setAge(String.valueOf(20));
        person.setSex("male");
        return redisUtil.set(key, person);
    }

    @Override
    public Object redisTemplateGet(String key) {
        return redisUtil.get(key);
    }

    @Override
    public boolean redisTemplateExpire(String key) {
        return redisUtil.expire(key, ExpireTime);
    }

    /*** 1、以下使用@Cacheable 注解方式实现***/

    /**
     * @Cacheable 注解是先根据 key 去查询 Redis 中是否有这个 key，如果有则直接返回。
     * 如果没有则执行方法体，最后将方法返回内容添加到 Redis 中，key 为上面那个key
     * @CacheEvict 注解是先执行方法体，然后根据 key 去 Redis 中删除
     * 其实还有一个 @CachePut 注解，这里觉得没必要用，用这两个注解足矣
     */
    
    @Cacheable(value = "person", key = "#key")
    @Override
    public Object annotationGet(String key){
        Person person =new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("llz");
        person.setAge(String.valueOf(20));
        person.setSex("male");
        return person;
    }
    
    @CacheEvict(value = "person", key = "#person.name")
    @Override
    public void annotationSet(Person person){
        System.out.println(person);
    }

}
