# kafkaExample
消息中间件 - kafka的学习

一、基础学习

消息：字节数组

键（key）

批次：可以提高效率，需要权衡（时间延迟和吞吐量）

主题：（数据库中的表）

分区：分表

二、kafka特点
1：多生产者和多消费者
2：基于磁盘的数据存储
3：高伸缩性
4：高性能

三、常见场景
1：活动跟踪
2：传递消息
3：收集指标和日志
4：提交日志
5：处理流（聚合、统计等）

四、kafka的安装和配置

五、kafka管理命令

##列出所有主题

kafka-topics.bat --zookeeper localhost:2181/kafka --list

##列出所有主题的详细信息

kafka-topics.bat --zookeeper localhost:2181/kafka --describe

##创建主题 主题名 my-topic，1副本，8分区

kafka-topics.bat --zookeeper localhost:2181/kafka --create --replication-factor 1 --partitions 8 --topic my-topic

##增加分区，注意：分区无法被删除

kafka-topics.bat --zookeeper localhost:2181/kafka --alter --topic my-topic --partitions 16

##删除主题

kafka-topics.bat --zookeeper localhost:2181/kafka --delete --topic my-topic

##列出消费者群组（仅Linux）

kafka-topics.sh --new-consumer --bootstrap-server localhost:9092/kafka --list

##列出消费者群组详细信息（仅Linux）

kafka-topics.sh --new-consumer --bootstrap-server localhost:9092/kafka --describe --group 群组名 


