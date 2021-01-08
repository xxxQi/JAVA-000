## PSETEX key milliseconds value
>可用版本： >= 2.6.0<br/>
>时间复杂度： O(1)

这个命令和 SETEX 命令相似， 但它以毫秒为单位设置 key 的生存时间， 而不是像 SETEX 命令那样以秒为单位进行设置。

## 返回值
命令在设置成功时返回 OK 。
## 代码示例
```shell script
redis> PSETEX mykey 1000 "Hello"
OK

redis> PTTL mykey
(integer) 999

redis> GET mykey
"Hello"
```