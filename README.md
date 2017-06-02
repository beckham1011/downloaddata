# downloaddata
DownloadData  from Your DB. It's just a Test Project for exam my technology with db,jvm


技术架构：Spring Boot + Spring JPA + Maven 
数据库目前只支持PostgreSQL

支持 Predix Cloud




未实现以下功能:

1, PC内存1G只能导出50M左右的数据，目前不支持分页查询

2, 数据类型暂时不全，需要总结，出来

3, 其他类型的数据类型， 比如主键，外键，存储过程， 视图，触发器，等还不支持

4, 如何一键导出所有

5, 考虑如何支持MySQL, MongoDB等所有NoSQL，以及如何实现文件系统HDFS, GFS.

6, 如何智能的支持小表，大表分别导，

7, 是否支持多文件导出

8, 排查出性能瓶颈在哪里，JVM内存分布状态是什么样的，内存不等的情况下最大支持多少数据量等

9, 考虑如何支持AWS，阿里云

10,目前分隔符是 $ 如果数据库中存有此数据，如何分割
