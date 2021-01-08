## SETNX key value
> 可用版本： >= 1.0.0 <br/>
  时间复杂度： O(1)
 
只在键 key 不存在的情况下， 将键 key 的值设置为 value 。

若键 key 已经存在， 则 SETNX 命令不做任何动作。

SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写。

## 返回值

命令在设置成功时返回 1 ， 设置失败时返回 0 。

## 代码示例
````shell script
127.0.0.1:6379> exists exists-key
(integer) 1
127.0.0.1:6379> exists job
(integer) 0
127.0.0.1:6379> setnx job "programmer"
(integer) 1
127.0.0.1:6379> setnx job "code-farmer"
(integer) 0
127.0.0.1:6379> get job
"programmer"
````