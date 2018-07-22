# Repository
spring boot +mybatis+druid 线程动态数据源切换

需求描述：
web应用部署在一台服务器上，供多个分店使用，但是各个分店都有自己的数据库服务器（数据库表结构都相同，数据各自独立）。
现在想要不同分店操作相同web应用的同一个mapper方法，但是各自访问自己的数据库。
所以mybatis的SqlSessionFactoryBean就要有动态数据源，并且根据请求线程的分店（分店标识作为dbkey），来切换数据源
这是一个利用spring AbstractRoutingDataSource类，来实现线程动态数据源的demo
