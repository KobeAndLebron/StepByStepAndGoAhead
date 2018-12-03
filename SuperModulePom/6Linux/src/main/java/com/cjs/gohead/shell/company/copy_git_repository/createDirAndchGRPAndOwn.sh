#!/bin/bash

# 在目录不存在的情况下(没有对目录所属group和user做判断), 创建目录, 并且修改目录所属的user和group.
if [ -d "${1}" ]; then
    echo "Directory named ${1} has existed."
else
    mkdir -p ${1} && chgrp ${2} ${1} && chown ${3} ${1} && \
    echo "The create and group/user change of ${1} directory success." && exit 0 || \
    echo "Fetal: The create or change group/user of ${1} directory fails." && exit 1
fi