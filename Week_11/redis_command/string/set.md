# SET key value [EX seconds] [PX milliseconds] [NX|XX]
> 可用版本： >= 1.0.0
  时间复杂度： O(1)

将字符串值 value 关联到 key 。

如果 key 已经持有其他值， SET 就覆写旧值， 无视类型。

当 SET 命令对一个带有生存时间（TTL）的键进行设置之后， 该键原有的 TTL 将被清除。