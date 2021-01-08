## SET key value [EX seconds] [PX milliseconds] [NX|XX]
> 可用版本： >= 1.0.0 <br/>
  时间复杂度： O(1)

将字符串值 value 关联到 key 。

如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。

当 SET 命令对一个带有生存时间（TTL）的键进行设置之后， 该键原有的 TTL 将被清除。

## 可选参数
从 Redis 2.6.12 版本开始， SET 命令的行为可以通过一系列参数来修改：

- EX seconds：将键的过期时间设置为 seconds 秒。 执行 SET key value EX seconds 的效果等同于执行 SETEX key seconds value 。
- PX milliseconds：将键的过期时间设置为 milliseconds 毫秒。 执行 SET key value PX milliseconds 的效果等同于执行 PSETEX key milliseconds value.
- NX：只在键不存在时， 才对键进行设置操作。 执行 SET key value NX 的效果等同于执行 SETNX key value
- XX：只在键已经存在时，才对键进行设置操作。

> 因为 SET 命令可以通过参数来实现 SETNX 、 SETEX 以及 PSETEX 命令的效果， 所以 Redis 将来的版本可能会移除并废弃 SETNX 、 SETEX 和 PSETEX 这三个命令。

## 返回值

在 Redis 2.6.12 版本以前， SET 命令总是返回 OK 。

从 Redis 2.6.12 版本开始， SET 命令只在设置操作成功完成时才返回 OK ； 如果命令使用了 NX 或者 XX 选项， 但是因为条件没达到而造成设置操作未执行， 那么命令将返回空批量回复（NULL Bulk Reply）。
## 代码示例

对不存在的键进行设置：
```shell script
127.0.0.1:6379> set key "value"
OK
127.0.0.1:6379> get key
"value"
```

对已存在的键进行设置
```shell script
127.0.0.1:6379> set key "new-value"
OK
127.0.0.1:6379> get key
"new-value"
```

使用 EX 选项：
```shell script
127.0.0.1:6379> SET key-with-expire-time "hello" EX 1008
OK
127.0.0.1:6379> GET key-with-expire-time
"hello"
127.0.0.1:6379> TTL key-with-expire-time
(integer) 10059
```
使用 PX 选项：
```shell script
127.0.0.1:6379> set key 1 px 12331
OK
127.0.0.1:6379> ttl key
(integer) 11
```
使用 NX 选项：
```shell script
127.0.0.1:6379> set not-exists-key "new" nx
OK # 键不存在，设置成功
127.0.0.1:6379> get not-exists-key
"new"
127.0.0.1:6379> set not-exists-key "newvalue" nx
(nil) # 键已经存在，设置失败
127.0.0.1:6379> get not-exists-key
"new" # 维持原值不变
```

使用 XX 选项：
```shell script
127.0.0.1:6379> exists exists-key
(integer) 0
127.0.0.1:6379> set exists-key "value" xx
(nil)
127.0.0.1:6379> set exists-key "value"
OK
127.0.0.1:6379> set exists-key "new-value" xx
OK
127.0.0.1:6379> get exists-key
"new-value"
```