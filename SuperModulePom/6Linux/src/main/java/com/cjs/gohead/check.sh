# script of linux
# purpose:Check mysql state and use heartbeat to failover when mysql is down,it refers to Mysql ha and heartbeat
#!/bin/bash
# set -ex

# export MYSQL_HOME=/mysql
# export PATH=$MYSQL_HOME/bin:$PATH
export PATH=/usr/bin:$PATH

# mysql="$MYSQL_HOME/bin/mysql"
mysql="mysql"
# delay_file="$MYSQL_HOME/slave_delay_second.log"
master_ip=192.168.130.59
master_port=3306
master_pwd=111111
master_uname=root
master_nodeName1=$(hostname)

while((1 > 0));do
  echo "check start..."
  # used to check heart status
        heart_ps=ps -A | grep -o heartbeat
  $mysql -u$master_uname -p$master_pwd -h$master_ip -P$master_port -connect_timeout=3 --execute="select version();" 1>/dev/null 2>/dev/null
        
  if [ $? -ne 0 ]; then # mysql service down
    echo "mysql service down"

    sleep 1
      if [ "" != "$heart_ps" ];then 
        echo "start failover..."
              service heartbeat stop 1>/dev/null#failover
        echo "failover finish..."
      else echo "failover has done..."
      fi
    sleep 1
  elif [ "" == "$heart_ps" ];then
    echo "recovery start..."
    service heartbeat start 1>/dev/null # heartbeat is closed
    echo "recovery stop..."
  fi
  echo "check stop..."
  sleep 10
done;