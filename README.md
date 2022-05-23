# kafkaExample
消息中间件 - kafka的学习

消息中间件 - kafka的学习

一、基础学习

消息：字节数组

键（key）

批次：可以提高效率，需要权衡（时间延迟和吞吐量）

主题：（数据库中的表）

分区：分表

二、kafka特点 1：多生产者和多消费者 2：基于磁盘的数据存储 3：高伸缩性 4：高性能

三、常见场景 1：活动跟踪 2：传递消息 3：收集指标和日志 4：提交日志 5：处理流（聚合、统计等）

四、kafka的安装和配置

Kafka的安装、管理和配置

安装

预备环境

Kafka是Java生态圈下的一员，用Scala编写，运行在Java虚拟机上，所以安装运行和普通的Java程序并没有什么区别。

安装Kafka官方说法，Java环境推荐Java8。

Kafka需要Zookeeper保存集群的元数据信息和消费者信息。Kafka一般会自带Zookeeper，但是从稳定性考虑，应该使用单独的Zookeeper，而且构建Zookeeper集群。

下载和安装Kafka

在http://kafka.apache.org/downloads上寻找合适的版本下载，我们这里选用的是kafka_2.11-0.10.1.1，下载完成后解压到本地目录。

运行

启动Zookeeper

进入Kafka目录下的bin\windows

执行kafka-server-start.bat ../../config/server.properties，出现以下画面表示成功

Linux下与此类似，进入bin后，执行对应的sh文件即可

基本的操作和管理

##列出所有主题

kafka-topics.bat --zookeeper localhost:2181/kafka --list

##列出所有主题的详细信息

kafka-topics.bat --zookeeper localhost:2181/kafka --describe

##创建主题 主题名 my-topic，1副本，8分区

kafka-topics.bat --zookeeper localhost:2181/kafka --create --topic my-topic --replication-factor 1 --partitions 8

##增加分区，注意：分区无法被删除

kafka-topics.bat --zookeeper localhost:2181/kafka --alter --topic my-topic --partitions 16

##删除主题

kafka-topics.bat --zookeeper localhost:2181/kafka --delete --topic my-topic

##列出消费者群组（仅Linux）

kafka-topics.sh --new-consumer --bootstrap-server localhost:9092/kafka --list

##列出消费者群组详细信息（仅Linux）

kafka-topics.sh --new-consumer --bootstrap-server localhost:9092/kafka --describe --group 群组名

##启动一个生产者

kafka-console-producer.bat --broker-list localhost:9092 --topic kafka-spring-topic

##启动一个消费者

#kafka-console-consumer.bat --zookeeper localhost:2181/kafka-one --from-beginning --topic kafka-spring-topic

kafka-console-consumer.bat --bootstrap-server localhost:9092 --from-beginning --topic justin

##查看某主题的某消费者群组消费偏移量

kafka-consumer-offset-checker.bat --zookeeper localhost:2181/kafka-one --topic kafka-spring-topic-b --group spring-kafka-group-ack

Broker配置

配置文件放在Kafka目录下的config目录中，主要是server.properties文件

常规配置

broker.id

在单机时无需修改，但在集群下部署时往往需要修改。它是个每一个broker在集群中的唯一表示，要求是正数。当该服务器的IP地址发生改变时，broker.id没有变化，则不会影响consumers的消息情况

listeners

监听列表(以逗号分隔 不同的协议(如plaintext,trace,ssl、不同的IP和端口)),hostname如果设置为0.0.0.0则绑定所有的网卡地址；如果hostname为空则绑定默认的网卡。如果

没有配置则默认为java.net.InetAddress.getCanonicalHostName()。

如：PLAINTEXT://myhost:9092,TRACE://:9091或 PLAINTEXT://0.0.0.0:9092,

zookeeper.connect

zookeeper集群的地址，可以是多个，多个之间用逗号分割

log.dirs

Kafka把所有的消息都保存在磁盘上，存放这些数据的目录通过log.dirs指定。

************************************************************************************
五、使用生产者

kafka发送的三种方式
1、发送并忘记
2、同步发送
3、异步发送

多线程下使用生产者

其它的发送配置

#################

kafka + springboot

版本问题：

springboot2.1一下使用 2.1.7

2.1以上，使用2.2.0


