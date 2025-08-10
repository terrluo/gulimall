# gulimall
谷粒商城

# 后端项目注意点
## CentOS 7 上出现 "Failed to set locale, defaulting to C" 的警告
修改 /etc/environment 文件
```bash
cd /etc
sudo vi environment
# 设置下面内容
# export LANG=en_US.UTF-8"
```
重启 centos7

## centos7 换源
手动下载 http://mirrors.aliyun.com/repo/Centos-7.repo 文件，复制文件内容后通过 vi 创建 CentOS-Base.repo
```bash
cd /etc/yum.repos.d/
sudo mv CentOS-Base.repo CentOS-Base.repo.backup
sudo vi CentOS-Base.repo
sudo yum clean all
sudo yum makecache
```

## centos7 安装 docker
```bash
sudo yum remove docker \
                docker-client \
                docker-client-latest \
                docker-common \
                docker-latest \
                docker-latest-logrotate \
                docker-logrotate \
                docker-engine
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
sudo yum-config-manager --add-repo https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo
sudo yum makecache fast
```
不清楚为什么清华大学的源下载内容是官方源的内容，手动改一份清华源，替换 /etc/yum.repo.d/docker-ce.repo 的内容
```ini
[docker-ce-stable]
name=Docker CE Stable - $basearch
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/$basearch/stable
enabled=1
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-stable-debuginfo]
name=Docker CE Stable - Debuginfo $basearch
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/debug-$basearch/stable
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-stable-source]
name=Docker CE Stable - Sources
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/source/stable
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-test]
name=Docker CE Test - $basearch
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/$basearch/test
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-test-debuginfo]
name=Docker CE Test - Debuginfo $basearch
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/debug-$basearch/test
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-test-source]
name=Docker CE Test - Sources
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/source/test
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-nightly]
name=Docker CE Nightly - $basearch
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/$basearch/nightly
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-nightly-debuginfo]
name=Docker CE Nightly - Debuginfo $basearch
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/debug-$basearch/nightly
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg

[docker-ce-nightly-source]
name=Docker CE Nightly - Sources
baseurl=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/$releasever/source/nightly
enabled=0
gpgcheck=1
gpgkey=https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/gpg
```
```bash
sudo yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl start docker
sudo systemctl enable docker
```

## 配置 Docker 镜像加速
```bash
cd /etc/docker
sudo vim daemon.json
```
daemon.json 内容
```json
{
  "registry-mirrors": ["https://docker.m.daocloud.io"]
}
```
```bash
sudo systemctl daemon-reload && sudo systemctl restart docker
# 查看加速是否配置成功
sudo docker info
```

## docker 运行 mysql
```bash
sudo docker pull mysql:5.7
sudo docker run -p 3306:3306 --name mysql \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql/conf.d/ \
-e MYSQL_ROOT_PASSWORD=root \
-d mysql:5.7
```
在 /mydata/mysql/conf 添加配置文件 my.cnf
```bash
sudo vim /mydata/mysql/conf/my.cnf
```
my.cnf 配置内容
```ini
[client]
default-character-set=utf8

[mysql]
default-character-set=utf8

[mysqld]
init_connect='SET collation_connection = utf8_unicode_ci'
init_connect='SET NAMES utf8'
character-set-server=utf8
collation-server=utf8_unicode_ci
# 处理连接数据库慢问题
skip-character-set-client-handshake
skip-name-resolve # 跳过域名解析
```
重启 mysql
```bash
sudo docker restart mysql
```

## docker 运行 redis
```bash
sudo docker pull redis:7.4
sudo mkdir /mydata/redis
sudo mkdir /mydata/redis/data
sudo mkdir /mydata/redis/conf
sudo vim /mydata/redis/conf/redis.conf
# 添加内容
# appendonly yes

sudo docker run -p 6379:6379 --name redis \
-v /mydata/redis/data:/data \
-v /mydata/redis/conf/redis.conf:/etc/redis/redis.conf \
-d redis:7.4 redis-server /etc/redis/redis.conf
```

## mac 连接虚拟机数据库
* mac 连接虚拟机的ip需要在设置的本地网络启用设备间通讯
* jdbc url 添加 useSSL=false 参数
* 重启 vscode

## 前段请求 renren-fast 跨域
取消 CorsConfig.java 里的 addCorsMappings 注释

# 前端项目注意点
## npm install 报错
* 需要安装 python2.7
* chromedriver 版本改为 85.0.1
* 手动安装 node-sass，npm install node-sass@4 --save-dev