ps -ef|grep "java -jar blog-" |grep -v "grep" |awk '{print "kill "$2}' |sh
echo "已停掉全部进程"

git pull origin master
echo "拉取代码完成"

sh mvn.sh
echo "编译完成"

nohup java -jar blog-publisher/target/blog-publisher-0.0.1-SNAPSHOT.jar >> blog-publisher/log/$(date +%Y-%m-%d).log 2>&1 &
nohup java -jar blog-admin/target/blog-admin-0.0.1-SNAPSHOT.jar >> blog-admin/log/$(date +%Y-%m-%d).log 2>&1 &
nohup java -jar blog-user/target/blog-user-0.0.1-SNAPSHOT.jar >> blog-user/log/$(date +%Y-%m-%d).log 2>&1 &
nohup java -jar blog-file/target/blog-file-0.0.1-SNAPSHOT.jar >> blog-file/log/$(date +%Y-%m-%d).log 2>&1 &
echo "进程启动成功"
