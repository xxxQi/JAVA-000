## STRLEN key
>可用版本：>= 2.2.0 <br/>
>时间复杂度： O(1)

返回键 key 储存的字符串值的长度

## 返回值

STRLEN 命令返回字符串值的长度。

当键 key 不存在时， 命令返回 0 。

当 key 储存的不是字符串值时， 返回一个错误

## 代码示例

获取字符串值的长度：
```shell script
127.0.0.1:6379> strlen db
(integer) 5
127.0.0.1:6379> get db
"redis"
"redis"
```
获取不存在字符串值的长度：
```shell script
127.0.0.1:6379> get test
(nil)
```