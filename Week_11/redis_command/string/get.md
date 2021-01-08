## GET key
>可用版本： >= 1.0.0 <br/>
>时间复杂度： O(1)

返回与键 key 相关联的字符串值。

## 返回值

如果键 key 不存在， 那么返回特殊值 nil ； 否则， 返回键 key 的值。

如果键 key 的值并非字符串类型， 那么返回一个错误， 因为 GET 命令只能用于字符串值。

## 代码示例

对不存在的键 key 或是字符串类型的键 key 执行 GET 命令：
```shell script
127.0.0.1:6379> get db
(nil)
127.0.0.1:6379> set db redis
OK
127.0.0.1:6379> get db
"redis"
```
对不是字符串类型的键 key 执行 GET 命令：
```shell script
127.0.0.1:6379> del db
(integer) 1
127.0.0.1:6379> lpush db redis mongodb mysql
(integer) 3
127.0.0.1:6379> get db
(error) WRONGTYPE Operation against a key holding the wrong kind of value
```