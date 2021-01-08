HSETNX hash field value
>可用版本： >= 2.0.0<br/>
>时间复杂度： O(1)

当且仅当域 field 尚未存在于哈希表的情况下， 将它的值设置为 value 。

如果给定域已经存在于哈希表当中， 那么命令将放弃执行设置操作。

如果哈希表 hash 不存在， 那么一个新的哈希表将被创建并执行 HSETNX 命令。

## 返回值

```shell script
HSETNX 命令在设置成功时返回 1 ， 在给定域已经存在而放弃执行设置操作时返回 0 。
```
## 代码示例
域尚未存在， 设置成功：
```shell script
redis> HSETNX database key-value-store Redis
(integer) 1

redis> HGET database key-value-store
"Redis"
```

域已经存在， 设置未成功， 域原有的值未被改变：
```shell script
redis> HSETNX database key-value-store Riak
(integer) 0

redis> HGET database key-value-store
"Redis"
```