## HKEYS key field increment
>可用版本>= 2.0.0<br/>
>O(N)， N 为哈希表的大小。

返回哈希表 key 中的所有域。



## 返回值

O(N)， N 为哈希表的大小。

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