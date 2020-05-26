#!/bin/bash

####### 根据具体环境修改 ############
## 上传图标所在目录.
uploadFileDir="/Users/chenjingshuai/Downloads/应用构建2期icon/"
## 上传图标的服务器地址.
uploadServer="http://10.39.30.19"
####### 根据具体环境修改 ############

## 上传结果汇总.
resultSum=""
## 上传成功后的URLPath汇总.
urlPathSum=""

for f in $(ls ${uploadFileDir}); do
  file=${uploadFileDir}"/"${f}
  echo "上传文件: "${file}

  result=$(curl -XPOST \
  -F "file=@$file" http://fn-cloud-visual-configuration.fnwrancher-dev.enncloud.cn/file/upload)

  uploadName=$(echo ${result} | python3 -c "import sys, json; print(json.load(sys.stdin)['data']['name'])")
  uploadUrl=$(echo ${result} | python3 -c "import sys, json; print(json.load(sys.stdin)['data']['url'])")

  resultSum="${resultSum}""上传成功: "${file}", 名字: "${uploadName}", 路径: "${uploadUrl}"\n"
  urlPathSum="$urlPathSum""$uploadServer$uploadUrl\n"

done

echo -e "上传结果: \n$resultSum"

echo -e "上传文件所在的服务器路径: \n$urlPathSum"


