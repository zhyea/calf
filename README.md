# Calf

## Summary

一个基于Java SpringBoot实现的CMS服务。有许多尚未完善之处，只是初生的小牛。

## Install

启动文件是`calf.service`。

启动文件需要被置于如下路径：

```shell script
/usr/lib/systemd/system/
```

相关指令：

```shell script
# 修改 service 文件之后需要刷新 Systemd
sudo systemctl daemon-reload

# 使 Calf 开机自启
sudo systemctl enable calf

# 启动 Calf
sudo service calf start

# 重启 Calf
sudo service calf restart

# 停止 Calf
sudo service calf stop

# 查看 Calf 的运行状态
sudo service calf status
```
