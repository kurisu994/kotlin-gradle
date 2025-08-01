# 使用OpenJDK 17作为基础镜像
FROM openjdk:17-jdk-slim

# 设置维护者信息
LABEL maintainer="Kurisu"

# 设置工作目录
WORKDIR /app

# 复制jar文件到容器中
COPY build/libs/kotlin-gradle-1.0.0.jar app.jar

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]
