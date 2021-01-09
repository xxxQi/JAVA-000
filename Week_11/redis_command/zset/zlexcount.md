## ZLEXCOUNT key min max
>可用版本： >= 2.8.9 <br/>
>时间复杂度： O(log(N)+M)， 其中 N 为有序集合的元素数量， 而 M 则是命令返回的元素数量。 如果 M 是一个常数（比如说，用户总是使用 LIMIT 参数来返回最先的 10 个元素）， 那么命令的复杂度也可以看作是 O(log(N)) 。

对于一个所有成员的分值都相同的有序集合键 key 来说， 这个命令会返回该集合中， 成员介于 min 和 max 范围内的元素数量。

这个命令的 min 参数和 max 参数的意义和 ZRANGEBYLEX key min max [LIMIT offset count] 命令的 min 参数和 max 参数的意义一样。

## 返回值

整数回复：指定范围内的元素数量。

## 代码示例

```shell script
redis> ZADD myzset 0 a 0 b 0 c 0 d 0 e
(integer) 5

redis> ZADD myzset 0 f 0 g
(integer) 2

redis> ZLEXCOUNT myzset - +
(integer) 7

redis> ZLEXCOUNT myzset [b [f
(integer) 5
```
   