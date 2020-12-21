# gradle 好大
FROM gradle:jdk14
COPY SocketClient.java /app/src
# 编译程序
WORKDIR /app/src
RUN find -name "*.java" > sources.txt
RUN javac @sources.txt
