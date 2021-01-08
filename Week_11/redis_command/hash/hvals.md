## HVALS key field increment
>可用版本>= 2.0.0<br/>
>O(N)， N 为哈希表的大小。

返回哈希表 key 中所有域的值。



## 返回值

一个包含哈希表中所有值的表。
当 key 不存在时，返回一个空表。

## 代码示例

```shell script
# 哈希表非空

redis> HMSET website google www.google.com yahoo www.yahoo.com
OK

redis> HKEYS website
1) "google"
2) "yahoo"


# 空哈希表/key不存在

redis> EXISTS fake_key
(integer) 0

redis> HKEYS fake_key
(empty list or set)                                     # 不存在的域返回nil值
```