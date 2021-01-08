## HLEN key field 
>时间复杂度： O(1)

返回哈希表 key 中域的数量。

## 返回值

哈希表中域的数量。
当 key 不存在时，返回 0 。

## 代码示例

```shell script
redis> HSET db redis redis.com
(integer) 1

redis> HSET db mysql mysql.com
(integer) 1

redis> HLEN db
(integer) 2

redis> HSET db mongodb mongodb.org
(integer) 1

redis> HLEN db
(integer) 3
```