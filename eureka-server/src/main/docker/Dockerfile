FROM registry.cn-hangzhou.aliyuncs.com/myimage/jdk8
VOLUME /tmp
ADD  eureka-server-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]