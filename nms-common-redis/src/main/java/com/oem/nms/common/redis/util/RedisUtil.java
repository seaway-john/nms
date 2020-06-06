package com.oem.nms.common.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Seaway John
 */
@Slf4j
@Component
public class RedisUtil {

    private final ReactiveStringRedisTemplate redisTemplate;

    @Autowired
    public RedisUtil(ReactiveStringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Mono<Boolean> hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Mono<Long> getExpireSeconds(String key) {
        return redisTemplate.getExpire(key)
                .map(duration -> duration.get(ChronoUnit.SECONDS));
    }

    public Mono<Boolean> expire(String key, long expireSeconds) {
        return redisTemplate.expire(key, Duration.ofSeconds(expireSeconds));
    }

    public Mono<String> valueGet(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> valueSet(String key, String value, long expireSeconds) {
        return redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(expireSeconds));
    }

    /**
     * setNX
     */
    public Mono<Boolean> valueSetIfAbsent(String key, String value, long expireSeconds) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(expireSeconds));
    }

    public Mono<Boolean> valueSetIfPresent(String key, String value, long expireSeconds) {
        return redisTemplate.opsForValue().setIfPresent(key, value, Duration.ofSeconds(expireSeconds));
    }

    public Mono<Long> valueIncrement(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Mono<Long> valueDecrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public Mono<Long> listPush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public Mono<Long> listPush(String key, Collection<String> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * @return stack pop
     */
    public Mono<String> listPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @return queue poll
     */
    public Mono<String> listPoll(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public Mono<Object> hashGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Mono<List<Object>> hashMultiGet(String key, Collection<Object> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    public Mono<Boolean> hashPut(String key, String hashKey, String value) {
        return redisTemplate.opsForHash().put(key, hashKey, value);
    }

    public Mono<Boolean> hashPut(String key, Map<String, Object> map) {
        return redisTemplate.opsForHash().putAll(key, map);
    }

    public Mono<Boolean> setContains(String key, String value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Mono<Long> setAdd(String key, String value) {
        return redisTemplate.opsForSet().add(key, value);
    }

    public Mono<Long> setAdd(String key, Collection<String> values) {
        return redisTemplate.opsForSet().add(key, toArray().apply(values));
    }

    public Mono<Long> setRemove(String key, Collection<String> values) {
        return redisTemplate.opsForSet().remove(key, toArray().apply(values));
    }

    public Mono<Long> zSetGetRank(String key, String value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    public Mono<Double> zSetGetScore(String key, String value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Flux<String> zSetGetRange(String key, Range<Long> range) {
        return redisTemplate.opsForZSet().range(key, range);
    }

    public Mono<Boolean> zSetAdd(String key, String value, int score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Mono<Double> zSetScoreIncrement(String key, String value, int scoreAdd) {
        return redisTemplate.opsForZSet().incrementScore(key, value, scoreAdd);
    }

    public Mono<Long> zSetRemove(String key, Collection<String> values) {
        return redisTemplate.opsForZSet().remove(key, toArray().apply(values));
    }

    private Function<Collection<String>, String[]> toArray() {
        return values -> values.toArray(new String[values.size()]);
    }

    private Function<Collection<String>, Objects[]> toObjectArray() {
        return values -> values.toArray(new Objects[values.size()]);
    }

}
