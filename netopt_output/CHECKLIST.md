# 📋 网络优化部署清单

## 服务端 (运维执行)

### Nginx 配置
- [ ] `nginx-gzip.conf` → 加入 http 块，启用 Gzip/Brotli 压缩
- [ ] `nginx-cache.conf` → 加入 server 块，设置 Cache-Control + ETag
- [ ] `nginx-http2.conf` → 替换 server 块，启用 HTTP/2 + TLS 1.3 + KeepAlive
- [ ] 验证: `nginx -t && nginx -s reload`

### CDN 配置 (如用 Cloudflare)
- [ ] `cloudflare.json` → 参考配置项在 Dashboard 中设置
- [ ] 启用 Argo Smart Routing (付费，可减少 30%+ 延迟)
- [ ] 启用 Tiered Cache (减少回源)

### Linux 系统调优
- [ ] TCP Fast Open: `sudo sysctl -w net.ipv4.tcp_fastopen=3`
- [ ] BBR 拥塞控制: `sudo sysctl -w net.ipv4.tcp_congestion_control=bbr`
- [ ] TCP 连接复用: `sudo sysctl -w net.ipv4.tcp_tw_reuse=1`

## 前端 (开发执行)

### HTML Head 注入
- [ ] `html-hints.html` → 复制 <link> 标签到 HTML <head>
- [ ] 将关键第三方域名加入 dns-prefetch
- [ ] 将首屏 LCP 资源加入 preload

### 图片优化
- [ ] 使用 WebP/AVIF 格式
- [ ] 非首屏图片启用 loading="lazy"
- [ ] 响应式图片使用 srcset

### JS/CSS
- [ ] 非关键脚本使用 deferred 或 async
- [ ] CSS 拆分 critical/non-critical
- [ ] Bundle 分析: 移除未使用的依赖

## 验证

运行以下命令验证优化效果:

```bash
# 优化前基准
netopt https://your-api.com --multi 5 --md before.md

# 应用配置后验证
netopt https://your-api.com --multi 5 --md after.md

# 对比报告
diff before.md after.md
```
