# Mosac
---
## 项目说明
一款面向大学校园的社交型应用软件，具备用户系统、论坛系统以及组队系统。 </br>
包含面向用户的Android端以及面向管理员的Web端。 </br>
Demo演示视频：[https://github.com/hemn97/Mosac/blob/master/Android-Demo.mp4](https://github.com/hemn97/Mosac/blob/master/Android-Demo.mp4 "Android-Demo.mp4")  </br>

## 数据库
使用SQL Server数据库进行存储数据。 </br>
Android端与数据库间的通信采用WebService中间件，Web端与数据库间的通信直接使用JDBC进行连接。 </br>
##Android端
**开发工具** </br>
Android Studio </br>
**导入第三方包** </br>
ksoap2-android-assembly-3.4.0-jar-with-dependencies.jar </br>
**开发模式** </br>
使用MVP模式进行开发。 </br>
![](http://www.jcodecraeer.com/uploads/userup/13953/1G020140036-F40-0.png) </br>
Activity 和Fragment 视为View 层，负责处理 UI。 </br>
Presenter 为业务处理层，既能调用UI逻辑，又能请求数据。 </br>
Model 层中包含着具体的数据请求，数据源。 </br>
三层之间调用顺序为view->presenter->model，不可反向调用。 </br>
低层的不会直接给上一层做反馈，而是通过 View 、 Callback 为上级做出反馈，这样就解决了请求数据与更新界面的异步操作。

## Web端
**开发工具** </br>
Eclipse </br>
**导入第三方包** </br>
------------WebContent </br>
------------------lib </br>
**JDBC连接池的配置** </br>
------------src </br>
------------------c3p0-config.xml </br>
**后台开发** </br>
使用Servlet+JSP+JavaBean开发模式。 </br>
域模型层 </br>
------------src </br>
------------------domain </br>
数据访问层 </br>
------------src </br>
------------------dao </br>
业务逻辑层 </br>
------------src </br>
------------------service </br>
表现层 </br>
------------src </br>
------------------servlet </br>
