## HDEL key field [field …]
>可用版本： >= 2.0.0 <br/>
>时间复杂度： O(N)， N 为要删除的域的数量。

删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。


## 返回值

被成功移除的域的数量，不包括被忽略的域。

## 代码示例

被成功移除的域的数量，不包括被忽略的域。
```shell script
# 测试数据

redis> HGETALL abbr
1) "a"
2) "apple"
3) "b"
4) "banana"
5) "c"
6) "cat"
7) "d"
8) "dog"


# 删除单个域

redis> HDEL abbr a
(integer) 1


# 删除不存在的域

redis> HDEL abbr not-exists-field
(integer) 0


# 删除多个域

redis> HDEL abbr b c
(integer) 2

redis> HGETALL abbr
1) "d"
2) "dog"
```