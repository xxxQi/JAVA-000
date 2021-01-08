## INCR key
> 可用版本： >= 1.0.0<br/>
  时间复杂度： O(1)

为键 key 储存的数字值加上一。

如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令。

如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误。

本操作的值限制在 64 位(bit)有符号数字表示之内。

> Note<br/>
>INCR 命令是一个针对字符串的操作。 因为 Redis 并没有专用的整数类型， 所以键 key 储存的值在执行 INCR 命令时会被解释为十进制 64 位有符号整数。

## 返回值
INCR 命令会返回键 key 在执行加一操作之后的值。
## 代码示例
```shell script
127.0.0.1:6379> set page_view 20
OK
127.0.0.1:6379> incr page_view
(integer) 21
127.0.0.1:6379> get page_view
"21"
```
INCR 命令操作不能解释为数字的值
```shell script
127.0.0.1:6379> set test2 "test"
OK
127.0.0.1:6379> incr test2
(error) ERR value is not an integer or out of range
```