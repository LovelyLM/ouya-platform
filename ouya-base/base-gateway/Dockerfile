# 该镜像需要依赖的基础镜像
FROM openjdk:8-jdk-alpine
# 将targer目录下的jar包复制到docker容器/home/springboot目录下面目录下面
ADD ./target/base-gateway.jar /home/springboot/base-gateway.jar
# 声明服务运行在8080端口
EXPOSE 80
# 执行命令
CMD ["java","-jar","/home/springboot/base-gateway.jar"]
# 指定维护者名称
MAINTAINER Mirror 1079626899@qq.com
ENV TIME_ZONE Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TIME_ZONE /etc/localtime