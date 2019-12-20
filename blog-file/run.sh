echo "====== file模块 ======"

cd $(cd $(dirname $0); pwd)

ps -ef|grep "java -jar" |grep "blog-file" |grep -v "grep" |awk '{print "kill "$2}' |sh
echo "已停掉进程"

git pull origin master
echo "拉取代码完成"

mvn clean install
echo "编译完成"

nohup java -jar target/blog-file-0.0.1-SNAPSHOT.jar >> log/$(date +%Y-%m-%d).log 2>&1 &
echo "进程启动成功"
