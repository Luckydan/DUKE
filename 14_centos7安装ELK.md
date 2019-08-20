---

---

# centos7安装elasticsearch+logstash+kibana

## 1.elasticsearch集群

### 1.1.下载

官方下载地址：http://www.elastic.co/products/elasticsearch

![01](./image/elasticsearch下载截图/01.png)

选择系统对应的安装包：centos可以使用rpm安装包或者tar包

![02](./image/elasticsearch下载截图/02.png)

### 1.2.安装

分别在三台服务器上进行elasticsearch的安装

将下载的安装包上传到服务器：

```
scp ./elasticsearch-6.2.3.tar.gz root@192.168.1.98:/tmp
```

登录服务器：

```
➜  Downloads ssh root@192.168.1.98
root@192.168.1.98's password: 
Last login: Sun Apr  8 18:32:13 2018 from 192.168.1.239
[root@tsslavenode7 ~]# 
```

解压安装包：

```
[root@tsslavenode7 ~]# cd /tmp/
[root@tsslavenode7 tmp]# tar -zxvf elasticsearch-6.2.3.tar.gz 
```

创建elasticsearch所需文件夹并将解压出的安装文件移动到指定位置：

```
[root@tsslavenode7 tmp]# mkdir /usr/local/elasticsearch
[root@tsslavenode7 tmp]# mkdir /usr/local/elasticsearch/data
[root@tsslavenode7 tmp]# mkdir /usr/local/elasticsearch/log
[root@tsslavenode7 tmp]# mv ./elasticsearch-6.2.3 /usr/local/elasticsearch/
```

### 1.3.配置

分别在三台服务器上进行elasticsearch的配置

elasticsearch解压后的文件结构：

```
[root@tsslavenode7 tmp]# cd /usr/local/elasticsearch/elasticsearch-6.2.3/
[root@tsslavenode7 elasticsearch-6.2.3]# ll
总用量 224
drwxr-xr-x.  2 root root   4096 4月   9 09:51 bin
drwxr-xr-x.  2 root root     75 4月   9 09:58 config
drwxr-xr-x.  2 root root   4096 3月  13 18:08 lib
-rw-r--r--.  1 root root  11358 3月  13 18:02 LICENSE.txt
drwxr-xr-x.  2 root root      6 3月  13 18:08 logs
drwxr-xr-x. 16 root root   4096 3月  13 18:08 modules
-rw-r--r--.  1 root root 191887 3月  13 18:07 NOTICE.txt
drwxr-xr-x.  2 root root      6 3月  13 18:08 plugins
-rw-r--r--.  1 root root   9268 3月  13 18:02 README.textile
[root@tsslavenode7 elasticsearch-6.2.3]# 
```

修改config/elasticsearch.yml配置文件：

```
[root@tsslavenode7 elasticsearch-6.2.3]# vim config/elasticsearch.yml
# 集群名称，不同节点通过集群名来进行组合
cluster.name: elasticsearch-cluster
# 每一个elasticsearch的节点名，同一个集群下的节点名不能相同
node.name: elasticsearch-node-1
# elasticsearch的数据存放路径
path.data: /usr/local/elasticsearch/data
# elasticsearch的日志存放路径
path.logs: /usr/local/elasticsearch/log
# 本节点的ip
network.host: 192.168.1.98
# 本节点的端口
http.port: 9200
# 不同节点的ip
discovery.zen.ping.unicast.hosts: ["192.168.1.98", "192.168.1.99", "192.168.1.100"]
# 最小主节点数，一般为总节点数的一半再加一
discovery.zen.minimum_master_nodes: 2
```

开放elasticsearch的端口，9200与9300：

```
[root@tsslavenode7 elasticsearch-6.2.3]# firewall-cmd --zone=public --add-port=9200/tcp --permanent
success
[root@tsslavenode7 elasticsearch-6.2.3]# firewall-cmd --zone=public --add-port=9300/tcp --permanent
success
[root@tsslavenode7 elasticsearch-6.2.3]# firewall-cmd --reload
success
[root@tsslavenode7 elasticsearch-6.2.3]# 
```

创建elasticsearch用户组与用户，elasticsearch不允许以root运行：

若以如运行启动，会报can not run elasticsearch as root的一个错误。

```
[root@tsslavenode7 ~]# groupadd elsearch
[root@tsslavenode7 ~]# useradd elsearch -g elsearch -p elasticsearch
```

将elasticsearch文件夹及内部文件的所属用户及组为elsearch:elsearch：

```
[root@tsslavenode7 ~]# cd /usr/local/
[root@tsslavenode7 local]# ll
总用量 0
drwxr-xr-x. 2 root root  6 11月  5 2016 bin
drwxr-xr-x. 5 root root 56 4月   9 10:01 elasticsearch
drwxr-xr-x. 2 root root  6 11月  5 2016 etc
drwxr-xr-x. 2 root root  6 11月  5 2016 games
drwxr-xr-x. 2 root root  6 11月  5 2016 include
drwxr-xr-x. 3 root root 26 4月   8 18:38 java
drwxr-xr-x. 2 root root  6 11月  5 2016 lib
drwxr-xr-x. 2 root root  6 11月  5 2016 lib64
drwxr-xr-x. 2 root root  6 11月  5 2016 libexec
drwxr-xr-x. 2 root root  6 11月  5 2016 sbin
drwxr-xr-x. 5 root root 49 4月   8 18:16 share
drwxr-xr-x. 2 root root  6 11月  5 2016 src
[root@tsslavenode7 local]# chown -R elsearch:elsearch  elasticsearch
[root@tsslavenode7 local]# ll
总用量 0
drwxr-xr-x. 2 root     root      6 11月  5 2016 bin
drwxr-xr-x. 5 elsearch elsearch 56 4月   9 10:01 elasticsearch
drwxr-xr-x. 2 root     root      6 11月  5 2016 etc
drwxr-xr-x. 2 root     root      6 11月  5 2016 games
drwxr-xr-x. 2 root     root      6 11月  5 2016 include
drwxr-xr-x. 3 root     root     26 4月   8 18:38 java
drwxr-xr-x. 2 root     root      6 11月  5 2016 lib
drwxr-xr-x. 2 root     root      6 11月  5 2016 lib64
drwxr-xr-x. 2 root     root      6 11月  5 2016 libexec
drwxr-xr-x. 2 root     root      6 11月  5 2016 sbin
drwxr-xr-x. 5 root     root     49 4月   8 18:16 share
drwxr-xr-x. 2 root     root      6 11月  5 2016 src
[root@tsslavenode7 local]# 
```

切换到elsearch用户：

```
[root@tsslavenode9 local]# su elsearch
[elsearch@tsslavenode9 local]$ 
```

启动elasticsearch:

```
[elsearch@tsslavenode7 bin]$ ./elasticsearch
[2018-04-09T11:24:50,858][INFO ][o.e.n.Node               ] [elasticsearch-node-1] initializing ...
[2018-04-09T11:24:50,955][INFO ][o.e.e.NodeEnvironment    ] [elasticsearch-node-1] using [1] data paths, mounts [[/ (rootfs)]], net usable_space [38.2gb], net total_space [49.9gb], types [rootfs]
[2018-04-09T11:24:50,955][INFO ][o.e.e.NodeEnvironment    ] [elasticsearch-node-1] heap size [1015.6mb], compressed ordinary object pointers [true]
[2018-04-09T11:24:50,956][INFO ][o.e.n.Node               ] [elasticsearch-node-1] node name [elasticsearch-node-1], node ID [LTEfFq7aSFmLoK-ixS_wDg]
[2018-04-09T11:24:50,956][INFO ][o.e.n.Node               ] [elasticsearch-node-1] version[6.2.3], pid[35987], build[c59ff00/2018-03-13T10:06:29.741383Z], OS[Linux/3.10.0-693.el7.x86_64/amd64], JVM[Oracle Corporation/Java HotSpot(TM) 64-Bit Server VM/1.8.0_162/25.162-b12]
[2018-04-09T11:24:50,956][INFO ][o.e.n.Node               ] [elasticsearch-node-1] JVM arguments [-Xms1g, -Xmx1g, -XX:+UseConcMarkSweepGC, -XX:CMSInitiatingOccupancyFraction=75, -XX:+UseCMSInitiatingOccupancyOnly, -XX:+AlwaysPreTouch, -Xss1m, -Djava.awt.headless=true, -Dfile.encoding=UTF-8, -Djna.nosys=true, -XX:-OmitStackTraceInFastThrow, -Dio.netty.noUnsafe=true, -Dio.netty.noKeySetOptimization=true, -Dio.netty.recycler.maxCapacityPerThread=0, -Dlog4j.shutdownHookEnabled=false, -Dlog4j2.disable.jmx=true, -Djava.io.tmpdir=/tmp/elasticsearch.YOct4USS, -XX:+HeapDumpOnOutOfMemoryError, -XX:+PrintGCDetails, -XX:+PrintGCDateStamps, -XX:+PrintTenuringDistribution, -XX:+PrintGCApplicationStoppedTime, -Xloggc:logs/gc.log, -XX:+UseGCLogFileRotation, -XX:NumberOfGCLogFiles=32, -XX:GCLogFileSize=64m, -Des.path.home=/usr/local/elasticsearch/elasticsearch-6.2.3, -Des.path.conf=/usr/local/elasticsearch/elasticsearch-6.2.3/config]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [aggs-matrix-stats]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [analysis-common]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [ingest-common]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [lang-expression]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [lang-mustache]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [lang-painless]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [mapper-extras]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [parent-join]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [percolator]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [rank-eval]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [reindex]
[2018-04-09T11:24:52,231][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [repository-url]
[2018-04-09T11:24:52,232][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [transport-netty4]
[2018-04-09T11:24:52,232][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] loaded module [tribe]
[2018-04-09T11:24:52,232][INFO ][o.e.p.PluginsService     ] [elasticsearch-node-1] no plugins loaded
[2018-04-09T11:24:58,369][INFO ][o.e.d.DiscoveryModule    ] [elasticsearch-node-1] using discovery type [zen]
[2018-04-09T11:24:58,998][INFO ][o.e.n.Node               ] [elasticsearch-node-1] initialized
[2018-04-09T11:24:58,998][INFO ][o.e.n.Node               ] [elasticsearch-node-1] starting ...
[2018-04-09T11:24:59,164][INFO ][o.e.t.TransportService   ] [elasticsearch-node-1] publish_address {192.168.1.98:9300}, bound_addresses {192.168.1.98:9300}
[2018-04-09T11:24:59,170][INFO ][o.e.b.BootstrapChecks    ] [elasticsearch-node-1] bound or publishing to a non-loopback address, enforcing bootstrap checks
ERROR: [2] bootstrap checks failed
[1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]
[2]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[2018-04-09T11:24:59,181][INFO ][o.e.n.Node               ] [elasticsearch-node-1] stopping ...
[2018-04-09T11:24:59,192][INFO ][o.e.n.Node               ] [elasticsearch-node-1] stopped
[2018-04-09T11:24:59,193][INFO ][o.e.n.Node               ] [elasticsearch-node-1] closing ...
[2018-04-09T11:24:59,247][INFO ][o.e.n.Node               ] [elasticsearch-node-1] closed
[elsearch@tsslavenode7 bin]$ 
```

发现报了两个错误：

[1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65536]

[2]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]

第一个错误是elsearch用户拥有的可创建文件描述的权限太低，至少需要65536；

第二个错误是elsearch用户拥有的内存权限太小，至少需要262144；

切换到root用户，修改第一个问题：

```
[root@tsslavenode7 bin]# vim /etc/security/limits.conf
文件末尾添加：
* hard nofile 65536
* soft nofile 65536
```

修改第二个问题：

```
[root@tsslavenode7 bin]# sysctl -w vm.max_map_count=262144
vm.max_map_count = 262144
[root@tsslavenode7 bin]# sysctl -a|grep vm.max_map_count
sysctl: reading key "net.ipv6.conf.all.stable_secret"
sysctl: reading key "net.ipv6.conf.default.stable_secret"
sysctl: reading key "net.ipv6.conf.ens33.stable_secret"
sysctl: reading key "net.ipv6.conf.lo.stable_secret"
sysctl: reading key "net.ipv6.conf.virbr0.stable_secret"
sysctl: reading key "net.ipv6.conf.virbr0-nic.stable_secret"
vm.max_map_count = 262144
[root@tsslavenode7 bin]# vim /etc/sysctl.conf
文件末尾添加：
vm.max_map_count=262144
[root@tsslavenode8 local]# sysctl -p
vm.max_map_count = 262144
```

切换到elsearch用户，启动elasticsearch:

```
[elsearch@tsslavenode7 bin]$ ./elasticsearch -d
[elsearch@tsslavenode7 bin]$ ps -ef | grep elasticsearch
elsearch  37188      1 88 11:45 pts/0    00:00:07 /usr/local/java/jdk1.8.0_162/bin/java -Xms1g -Xmx1g -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+AlwaysPreTouch -Xss1m -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Djna.nosys=true -XX:-OmitStackTraceInFastThrow -Dio.netty.noUnsafe=true -Dio.netty.noKeySetOptimization=true -Dio.netty.recycler.maxCapacityPerThread=0 -Dlog4j.shutdownHookEnabled=false -Dlog4j2.disable.jmx=true -Djava.io.tmpdir=/tmp/elasticsearch.GcHFkuib -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+PrintGCApplicationStoppedTime -Xloggc:logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=32 -XX:GCLogFileSize=64m -Des.path.home=/usr/local/elasticsearch/elasticsearch-6.2.3 -Des.path.conf=/usr/local/elasticsearch/elasticsearch-6.2.3/config -cp /usr/local/elasticsearch/elasticsearch-6.2.3/lib/* org.elasticsearch.bootstrap.Elasticsearch -d
elsearch  37214  36730  0 11:45 pts/0    00:00:00 grep --color=auto elasticsearch
[elsearch@tsslavenode7 bin]$ 
```

查看集群状态：

其中*代表为主节点的意思。

```
[elsearch@tsslavenode7 bin]$ curl -XGET 'http://192.168.1.98:9200/_cat/nodes?pretty'
192.168.1.98  7 95 0 0.01 0.05 0.06 mdi - elasticsearch-node-1
192.168.1.99  9 96 1 0.01 0.10 0.12 mdi * elasticsearch-node-2
192.168.1.100 7 96 7 0.33 0.20 0.13 mdi - elasticsearch-node-3
```

## 2.logstash单点

### 2.1.下载

官方下载地址：https://www.elastic.co/products/logstash

![01](./image/logstash下载截图/01.png)

选择系统对应的安装包：centos可以使用rpm安装包或者tar包

![02](./image/logstash下载截图/02.png)

### 2.2.安装

上传logstash安装包到服务器：

```
scp ./logstash-6.2.3.tar.gz root@192.168.1.139:/tmp
```

登录服务器：

```
➜  Downloads ssh root@192.168.1.139                             
root@192.168.1.139's password: 
Last login: Mon Apr  9 13:29:38 2018 from 192.168.1.244
[root@tsmainnode4 ~]# 
```

解压安装文件：

```
[root@tsmainnode4 tmp]# tar -zxvf logstash-6.2.3.tar.gz 
```

创建logstash所需文件夹并将解压出的安装文件移动到指定位置：

```
[root@tsmainnode4 tmp]# mkdir /usr/local/logstash/
[root@tsmainnode4 tmp]# mv ./logstash-6.2.3/ /usr/local/logstash/
```

### 2.3.配置

logstash解压后的文件结构：

```
[root@tsmainnode4 tmp]# cd /usr/local/logstash/logstash-6.2.3/
[root@tsmainnode4 logstash-6.2.3]# ll
总用量 68
drwxr-xr-x. 2 root root  4096 4月   9 14:53 bin
drwxr-xr-x. 2 root root   114 4月   9 14:57 config
-rw-r--r--. 1 root root  2276 3月  13 19:49 CONTRIBUTORS
drwxr-xr-x. 2 root root     6 3月  13 19:49 data
-rw-r--r--. 1 root root  3869 3月  13 19:53 Gemfile
-rw-r--r--. 1 root root 21170 3月  13 19:51 Gemfile.lock
drwxr-xr-x. 6 root root    84 4月   9 14:53 lib
-rw-r--r--. 1 root root   589 3月  13 19:49 LICENSE
drwxr-xr-x. 4 root root    90 4月   9 14:53 logstash-core
drwxr-xr-x. 3 root root    57 4月   9 14:53 logstash-core-plugin-api
drwxr-xr-x. 4 root root    55 4月   9 14:53 modules
-rw-rw-r--. 1 root root 28122 3月  13 19:53 NOTICE.TXT
drwxr-xr-x. 3 root root    30 4月   9 14:53 tools
drwxr-xr-x. 4 root root    33 4月   9 14:53 vendor
[root@tsmainnode4 logstash-6.2.3]# 
```

创建kafka的logstash配置文件：

```
[root@tsmainnode4 logstash-6.2.3]# vim /usr/local/logstash/logstash-kafka.conf
input {
  stdin { }
  kafka {
    bootstrap_servers => "192.168.1.147:9092,192.168.1.122:9092,192.168.1.183:9092"
    group_id => "logstash"
    topics => ["logstash_kafka_test"]
  }
}

output {
    stdout { codec=> rubydebug }
    elasticsearch {
        hosts => ["192.168.1.98:9200","192.168.1.99:9200","192.168.1.100:9200"]
        index => "logstash-%{+YYYY.MM.dd.hh}"
    }
}
```

启动logstash：

```
[root@tsmainnode4 logstash-6.2.3]# cd bin
[root@tsmainnode4 bin]# ./logstash -f /usr/local/logstash/logstash-kafka.conf
```

后台启动logstash：

```
[root@tsmainnode4 bin]# nohup ./logstash -f /usr/local/logstash/logstash-kafka.conf > /dev/null &
```



## 3.kibana单点

### 3.1.下载

官方下载地址：https://www.elastic.co/products/kibana

![01](./image/kibana下载截图/01.png)

选择系统对应的安装包：centos可以使用rpm安装包或者tar包

![02](./image/kibana下载截图/02.png)

### 3.2.安装

上传kibana安装包到服务器：

```
scp ./kibana-6.2.3-linux-x86_64.tar.gz root@192.168.1.139:/tmp
```

登录服务器：

```
➜  Downloads ssh root@192.168.1.139
root@192.168.1.139's password: 
Last login: Sun Apr  8 18:57:58 2018 from 192.168.1.139
[root@tsmainnode4 ~]# 
```

解压安装文件：

```
[root@tsmainnode4 tmp]# tar -zxvf kibana-6.2.3-linux-x86_64.tar.gz
```

创建kibana所需文件夹并将解压出的安装文件移动到指定位置：

```
[root@tsmainnode4 tmp]# mv ./kibana-6.2.3-linux-x86_64 /usr/local/kibana/
```

### 3.3.配置

kibana文件解压结构：

```
[root@tsmainnode4 tmp]# cd /usr/local/kibana/kibana-6.2.3-linux-x86_64/
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# ll
总用量 1168
drwxr-xr-x.   2 thundersoft thundersoft      64 3月  13 18:25 bin
drwxrwxr-x.   2 thundersoft thundersoft      24 4月   9 13:42 config
drwxrwxr-x.   2 thundersoft thundersoft       6 3月  13 18:25 data
-rw-rw-r--.   1 thundersoft thundersoft     562 3月  13 18:25 LICENSE.txt
drwxrwxr-x.   6 thundersoft thundersoft     108 3月  13 18:25 node
drwxrwxr-x. 906 thundersoft thundersoft   28672 3月  13 18:25 node_modules
-rw-rw-r--.   1 thundersoft thundersoft 1129761 3月  13 18:25 NOTICE.txt
drwxrwxr-x.   3 thundersoft thundersoft      45 3月  13 18:25 optimize
-rw-rw-r--.   1 thundersoft thundersoft     721 3月  13 18:25 package.json
drwxrwxr-x.   2 thundersoft thundersoft       6 3月  13 18:25 plugins
-rw-rw-r--.   1 thundersoft thundersoft    4772 3月  13 18:25 README.txt
drwxr-xr-x.  15 thundersoft thundersoft     225 3月  13 18:25 src
drwxrwxr-x.   5 thundersoft thundersoft      47 3月  13 18:25 ui_framework
drwxr-xr-x.   2 thundersoft thundersoft    4096 3月  13 18:25 webpackShims
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# 
```

修改config/kibana.yml

```
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# vim config/kibana.yml
# kibana服务端口
server.port: 5601
# kibana主机地址，ip和hostname均可，但hostname必须能够解析
server.host: "192.168.1.139"
# kibana服务名称
server.name: "tsmainnode4"
# kibana用于查询所有elasticsearch实例的url，只能配置一个。
elasticsearch.url: "http://192.168.1.98:9200"
# Kibana在Elasticsearch中使用索引，如果索引不存在，Kibana会创建一个新的索引来存储保存的搜索，可视化和仪表板。
kibana.index: ".kibana"
```

开放kibana服务端口：

```
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# firewall-cmd --zone=public --add-port=5601/tcp --permanent
success
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# firewall-cmd --reload
success
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# 
```

启动kibana：

```
[root@tsmainnode4 kibana-6.2.3-linux-x86_64]# cd bin
[root@tsmainnode4 bin]# nohup ./kibana > /dev/null &
[2] 21587
[root@tsmainnode4 bin]# nohup: 忽略输入并把输出追加到"nohup.out"
```

## 4.验证

### 4.1.kafka写入消息

像kafka的logstash_kafka_test（logstash配置文件制定的topic）topic任意发一条消息：

登录kafka所在服务器：

```
➜  ~ ssh root@192.168.1.147
root@192.168.1.147's password: 
Last login: Mon Apr  9 17:48:01 2018 from 192.168.1.244
[root@tsslavenode4 ~]# 
```

发送消息：

```
[root@tsslavenode4 ~]# kafka-console-producer.sh --broker-list 192.168.1.147:9092 --topic logstash_kafka_test
>this is a test
>
```

查看消息：

```
[root@tsslavenode4 ~]# kafka-console-consumer.sh --bootstrap-server 192.168.1.122:9092 --from-beginning --topic logstash_kafka_test
this is a test
```

### 4.2.logstash查看消息

在logstash处查看消息（前台启动logstash，控制台输出的日志）：

```
[2018-04-09T17:02:11,690][INFO ][org.apache.kafka.clients.consumer.internals.ConsumerCoordinator] [Consumer clientId=logstash-0, groupId=logstash] Setting newly assigned partitions [logstash_kafka_test-0, logstash_kafka_test-2, logstash_kafka_test-1]
{
      "@version" => "1",
    "@timestamp" => 2018-04-09T09:49:46.990Z,
       "message" => "this is a test"
}
```

### 4.3.在kibana查看消息：

登录kibana：http://192.168.1.139:5601

![03](./image/kibana下载截图/03.png)

首次登录会提示Waring。

在Management处，新建index pattern，输入logstash-*，点击Next step

![04](./image/kibana下载截图/04.png)

选择根据时间戳过滤，点击Create index pattern

index pattern创建完如下图所示：

![05](./image/kibana下载截图/05.png)

点击Discover菜单：

发现根据时间过滤的刚新添加的消息

![06](./image/kibana下载截图/06.png)

