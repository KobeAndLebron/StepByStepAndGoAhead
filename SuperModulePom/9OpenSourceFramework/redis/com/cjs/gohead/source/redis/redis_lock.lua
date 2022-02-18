-- 可重入锁的lua脚本.
-- KEYS[1] 锁的名称
-- ARG[2] randomValue, 随机值, 防止客户端A释放B的锁.
-- ARG[1] 锁的时间, 默认30S.
if (redis.call('exists', KEYS[1]) == 0) then
    redis.call('hset', KEYS[1], ARGV[2], 1); -- 首次获取锁成功
    redis.call('pexpire', KEYS[1], ARGV[1]);
    return nil;
end ;
if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then
    redis.call('hincrby', KEYS[1], ARGV[2], 1); -- 可重入次数加1
    redis.call('pexpire', KEYS[1], ARGV[1]);
    return nil;
end ;
return redis.call('pttl', KEYS[1]);