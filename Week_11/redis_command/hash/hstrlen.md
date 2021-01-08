## HSTRLEN  key field
>可用版本>= 3.2.0<br/>
>时间复杂度： O(1)

返回哈希表 key 中， 与给定域 field 相关联的值的字符串长度（string length）。

如果给定的键或者域不存在， 那么命令返回 0 。

## 返回值

一个整数。

## 代码示例

```shell script
redis> HMSET myhash f1 "HelloWorld" f2 "99" f3 "-256"
OK

redis> HSTRLEN myhash f1
(integer) 10

redis> HSTRLEN myhash f2
(integer) 2

redis> HSTRLEN myhash f3
(integer) 4
```