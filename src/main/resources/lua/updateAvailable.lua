-- 使用lua脚本redis操作的保证原子性
local result ={}
--重复秒杀
if redis.call('hexists', KEYS[2], ARGV[1]) ~= 0 then  
  result["success"] = false
  result["resultCode"] = "-1"
  return cjson.encode(result)
end  
local info = redis.call('get',KEYS[3])
local infoQ = cjson.decode(info)
--秒杀时间段内
if tonumber(ARGV[2])<tonumber(infoQ["startTime"]) or tonumber(ARGV[2])>tonumber(infoQ["endTime"]) then
    result["success"] = true
    result["resultCode"] = "0"
    local encodestr = cjson.encode(result)
    return  encodestr
end
redis.call('DECR', KEYS[1])
local current = redis.call('GET', KEYS[1])
if tonumber(current)>=0 then
    redis.call('hset', KEYS[2], ARGV[1], ARGV[2]);
    result["success"] = true
    result["resultCode"] = "1"
    local encodestr = cjson.encode(result)
    return  encodestr
end
--余量不足
result["success"] = false
result["resultCode"] = "-4"
local encodestr = cjson.encode(result)
--print(encodestr)
return  encodestr