cd $(cd $(dirname $0); pwd)
sh blog-user/clear.sh
sh blog-admin/clear.sh
sh blog-file/clear.sh
sh blog-publisher/clear.sh

echo "清理全部日志完成"
