#!/bin/sh
docker stats $(docker ps | grep k2db-rest | awk '{print $13}')

# Result:
# CONTAINER              CPU %               MEM USAGE / LIMIT     MEM %               NET I/O               BLOCK I/O           PIDS
# great100_k2db-rest_1   0.07%               3.403 GB / 33.61 GB   10.13%              16.98 MB / 11.78 MB   524.3 kB / 0 B      0

# $将最后awk所输出的列作为一个变量
# docker ps | grep k2db-rest | awk '{print $13}' 获取docker镜像的名字.