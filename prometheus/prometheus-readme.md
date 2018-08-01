https://blog.csdn.net/ericprotectearth/article/details/78581630



http://dockone.io/article/3298


prometheus下载
https://prometheus.io/download/

`prometheus.yml` 配置文件

```
# my global config
global:
  scrape_interval:     15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
  evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
  # scrape_timeout is set to the global default (10s).

# Alertmanager configuration
alerting:
  alertmanagers:
  - static_configs:
    - targets:
      # - alertmanager:9093

# Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
rule_files:
  # - "first_rules.yml"
  # - "second_rules.yml"

# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
    - targets: ['localhost:9090']

  # 添加一个springboot的监控
  - job_name: 'springboot'
    metrics_path: '/prometheus'
    static_configs:
    - targets: ['localhost:20001']
```
http://localhost:9090/graph

http://localhost:9090/targets

启动 prometheus