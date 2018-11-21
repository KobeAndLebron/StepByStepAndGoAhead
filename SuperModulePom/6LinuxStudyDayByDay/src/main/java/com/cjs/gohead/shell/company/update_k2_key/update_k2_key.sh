#!/bin/bash

#### 数据升级脚本.
#### 由于KMX从1.9升到2.0后, KMX租户的用户名不支持'@'和'.', 所以需要重新创建租户, 然后还要更新iam中user表中的daas_key和daas_name;

# contains(string, substring)
#
# 如果指定字符串包含另一个字符串则返回0,否则返回1.
contains() {
    string="$1"
    substring="$2"
    if test "${string#*$substring}" != "$string"
    then
        return 0    # $substring is in $string
    else
        return 1    # $substring is not in $string
    fi
}

TENANT_URI="/tenants/init" # 创建租户的URI.
TENANT_TOKENS_URI="/tokens" # 获取租户认证信息的URI.
TENANT_USERNAME_PREFIX="tadmin_" # KMX租户管理员用户名前缀.

SCRIPT_DIR=$(dirname $(readlink -e $0))

# set ENV_FILE
ENV_FILE=$1
if [[ -z ${ENV_FILE} ]]; then
    ENV_FILE="env.qa"
fi
ENV_FILE="${SCRIPT_DIR}/${ENV_FILE}"
# export env.
$(awk '{print "export " $0}' ${ENV_FILE})

# 获取email列表.
EMAIL_OUTPUT=$(mysql -h${MYSQL_HOST} -P${MYSQL_PORT} -u${MYSQL_USER} -p${MYSQL_PASSWORD} \
    -e "SELECT email FROM iam.user"  -s  -N 2>/dev/null)

echo "All emails:${EMAIL_OUTPUT}"
# echo 加上和不加""的区别?
IFS=' ' read -r -a EMAIL_ARRAY <<< ${EMAIL_OUTPUT}


for (( i=0; i<${#EMAIL_ARRAY[@]}; i++))
do
    echo "总数:${#EMAIL_ARRAY[@]}, 当前处理到第${i}个(从0开始)."
    email=${EMAIL_ARRAY[$i]}

    # 由于kmx2.0的用户名不支持'@'和'.', 所以将email中的'@'和'.'替换为'_'.
    replaceEmail=$(echo ${email} | sed -e "s/@/_/g" | sed -e "s/\./_/g")
    echo "original email:${email}, replaced email:${replaceEmail}"

    # 创建租户.
    output=$(curl -X POST \
        "${KMX_AUTH_URL}${TENANT_URI}" \
        -H 'Accept: application/json' \
        -H 'Content-Type: application/json' \
        -H "K2_KEY: ${OPERATOR_K2_KEY}" \
        -d "{
            \"id\" : \"${replaceEmail}\",
            \"name\" : \"${replaceEmail}\"
            }"
    2&>1) # 将标准错误输出流重定向到标准输出流以捕捉错误信息.
    # 返回结果实例-成功: {"code":0,"message":"init success!","result":{
    # "tenantId":"test","tenantAdminName":"tadmin_test","tenantAdminPassword":"123456"}}
    # 返回结果实例-失败: {"code":1,"message":"init failed, message : Tenant id or name exists."}

    # 创建成功或者已经存在都更新token信息.
    if $(contains "$output" "$replaceEmail") || $(contains "$output" "Tenant id or name exists"); then
        echo "租户(${replaceEmail})创建成功/已经存在: ${output}"

        # 获取租户的认证信息.
        output=$(curl -X GET \
                "${KMX_AUTH_URL}${TENANT_TOKENS_URI}?username=${TENANT_USERNAME_PREFIX}${replaceEmail}"\
                -H 'Content-Type: application/json' \
                -H "K2_KEY: ${OPERATOR_K2_KEY}" \
                -H "TENANTID: ${replaceEmail}"
            2&>1)
            # 返回结果实例:
            # {"code":0,"message":"","result":[
            # {"tenantId":"005x_yeah_net","id":62,"userId":63,"token":"xxx",
            #   "createTime":"2018-11-21T10:06:55.000+0800","expireTime":"2030-01-01T00:00:00.000+0800",
            #   "username":"tadmin_005x_yeah_net"}],"pages":{"total":1,"pages":1,"pageSize":20,"pageNum":1,"size":1}}
        if $(contains "$output" "$replaceEmail");then
            echo "租户认证信息(${replaceEmail})获取成功: ${output}"
            dassKey=$(echo ${output} | python2.7 -c "import sys,json; jdata = sys.stdin.read(); \
                            print json.loads(jdata)['result'][0]['token']")
            dassuserName=$(echo ${output} | python2.7 -c "import sys,json; jdata = sys.stdin.read(); \
                            print json.loads(jdata)['result'][0]['username']")

            echo "daas_key:${dassKey}, daas_username:${dassuserName}"

            # 将iam的user表中的daas_key更新为上面的token, daas_username更新为username.
            mysql -h${MYSQL_HOST} -P${MYSQL_PORT} -u${MYSQL_USER} -p${MYSQL_PASSWORD} -e \
                "UPDATE iam.user SET daas_key=\"${dassKey}\", daas_username=\"${dassuserName}\" WHERE email=\"${email}\""

            if [ $? -eq 0 ];then
                echo "租户(${replaceEmail})认证信息更新成功."
            else
                echo "租户(${replaceEmail})认证信息更新失败."
            fi
        else
            echo "租户认证信息(${replaceEmail})获取失败, 错误信息: ${output}"
        fi
    else
        echo "租户(${replaceEmail})创建失败, 错误信息:${output}"
    fi

done
