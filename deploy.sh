#!/bin/bash

## 1. 拉取最新的代码
#echo "正在拉取最新的代码..."
#git pull

# 2. mvn 强制clean
echo "正在执行 mvn clean -U..."
mvn clean package -DskipTests -U

# 3. 检查并杀死当前进程
echo "正在检查当前进程..."
PID=$(pgrep -f "template-admin/target/template.jar --spring.config.additional-location=file:../config/template.yml")
if [ -n "$PID" ]; then
    echo "发现当前进程，正在杀死进程 $PID..."
    kill -SIGTERM "$PID"
    sleep 5
fi

# 清除数据文件夹
echo "正在清除日志文件夹..."
rm -rf data

echo "正在启动项目..."
nohup java -jar template-admin/target/template.jar --spring.config.additional-location=file:../config/template.yml >/dev/null 2>&1 &
echo "项目已启动！"

