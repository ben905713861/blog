nohup java -jar blog-admin/target/blog-admin-0.0.1-SNAPSHOT.jar >blog-admin/log/$(date +%Y-%m-%d).log 2>&1 &
nohup java -jar blog-user/target/blog-user-0.0.1-SNAPSHOT.jar >blog-user/log/$(date +%Y-%m-%d).log 2>&1 &
nohup java -jar blog-file/target/blog-file-0.0.1-SNAPSHOT.jar >blog-file/log/$(date +%Y-%m-%d).log 2>&1 &

