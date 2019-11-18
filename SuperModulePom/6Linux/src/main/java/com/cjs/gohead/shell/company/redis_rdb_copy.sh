#!/bin/bash

## rdb备份脚本
## 配合crontab命令使用. crontab使用方法:
## crontab -e
### 分钟 小时 天 月 周
### * * * * * cmd 每分钟执行一次
### 0 * * * * cmd 每小时执行一次
### 3,15 * * * * cmd 每小时的3和15分钟
### 0,30 18-23 * * * 每天的18点至23点每隔30分钟
### 45 4 1,10,22 * * 每月的1 10 22日的4:45
### 0 23 * * 6 每周6的11:00 PM执行.

set -ex
start_date=$(date "+%Y_%m_%d_%H%M%S")
BACKUP_DIR="/tmp"

# 获取bgsave状态, 返回0则表示bgsave执行完毕.
# TODO: 判断`info persistence`的rdb_last_bgsave_status值是否为OK.
function get_bgsave_status() {
    return $(redis-cli -i info persistence | grep -i "rdb_bgsave_in_progress" | awk -F":" {print $2})
}

msg=$(redis-cli -i 1 bgsave)
echo "BGSAVE start: $start_date, BGSAVE message: $msg"

while (( $(get_bgsave_status) == 0));do
    sleep 2
done

end_date=$(date "+%Y_%m_%d_%H%M%S")
echo "BGSAVE end:$end_date"

redis_data_dir=$(redis-cli -i 1 config get dir | awk -F" " '{if (FNR==2) print $1;}')
redis_rdb_name=$(redis-cli -i 1 config get dbfilename | awk -F" " '{if (FNR==2) print $1;}')

cp ${redis_data_dir}/${redis_rdb_name} ${BACKUP_DIR}/${start_date}.rdb

## 之后将文件上传到S3服务器, 如阿里云服务器. 做异地灾备.
echo "finish backup rdb file."