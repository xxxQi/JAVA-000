## LSET key index value
>可用版本： >= 1.0.0 <br/>
>时间复杂度：对头元素或尾元素进行 LSET 操作，复杂度为 O(1)。其他情况下，为 O(N)， N 为列表的长度。

将列表 key 下标为 index 的元素的值设置为 value 。

当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。

关于列表下标的更多信息，请参考 LINDEX key index 命令。


## 返回值

操作成功返回 ok ，否则返回错误信息。

## 代码示例

```shell script
# 对空列表(key 不存在)进行 LSET

redis> EXISTS list
(integer) 0

redis> LSET list 0 item
(error) ERR no such key


# 对非空列表进行 LSET

redis> LPUSH job "cook food"
(integer) 1

redis> LRANGE job 0 0
1) "cook food"

redis> LSET job 0 "play game"
OK

redis> LRANGE job  0 0
1) "play game"


# index 超出范围

redis> LLEN job                    # 列表长度为 1
(integer) 1

redis> LSET job 3 'out of range'
(error) ERR index out of range
```