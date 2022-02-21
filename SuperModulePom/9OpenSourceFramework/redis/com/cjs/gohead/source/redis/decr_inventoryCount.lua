-- 扣减库存的LUA脚本, inventoryCount表示商品的库存数, auctionCount表示已经竞拍出去的库存.
local n = tonumber(ARGV[1])
if not n  or n == 0 then
    return 0
end
local vals = redis.call("HMGET", KEYS[1], "inventoryCount", "auctionCount");
local total = tonumber(vals[1])
local blocked = tonumber(vals[2])
if not total or not blocked then
    return 0
end
if blocked + n <= total then -- 小悲观锁, 如果已售卖+n 大于 库存数, 则返回0, 表示库存不足.
    redis.call("HINCRBY", KEYS[1], "auctionCount", n)
    return n;
end
return 0