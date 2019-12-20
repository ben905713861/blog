cd $(cd $(dirname $0); pwd)

sh blog-user/run.sh
sh blog-admin/run.sh
sh blog-file/run.sh
sh blog-publisher/run.sh

echo "进程全部启动成功"
