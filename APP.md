# 拉歌 (LaGe) — KMP 版

> 像拉片一样拉歌 — 深度音乐分析平台  
> Kotlin Multiplatform + Compose Multiplatform 架构

## 架构概览

```
lage-kmp/
├── shared/                          # KMP 共享模块 (业务逻辑)
│   └── src/
│       ├── commonMain/kotlin/com/lage/
│       │   ├── data/
│       │   │   ├── model/           # Song, Annotation, User 数据模型
│       │   │   ├── repository/      # 仓库层 (Supabase API 封装)
│       │   │   └── network/         # 网络优化层 (HTTP/2, 压缩, 缓存, 预连接)
│       │   ├── domain/              # 领域层 (use cases)
│       │   └── platform/            # expect 声明 (平台抽象)
│       ├── androidMain/             # Android actual 实现
│       └── iosMain/                 # iOS actual 实现
├── composeApp/                      # Compose Multiplatform UI
│   └── src/
│       ├── commonMain/              # 跨平台 UI (Compose)
│       │   └── kotlin/com/lage/
│       │       ├── ui/              # 屏幕 (home, player, auth, profile, analysis)
│       │       └── theme/           # Material3 主题
│       ├── androidMain/             # Android 特定 UI
│       └── iosMain/                 # iOS 特定 UI
├── iosApp/                          # iOS 原生入口 (Swift)
├── gradle/                          # Gradle 构建脚本
├── build.gradle.kts
└── settings.gradle.kts
```

## 性能优化架构

### 客户端层
```
┌─────────────────────────────────────────┐
│  UI Layer (Compose Multiplatform)       │
│  ├── LazyColumn / LazyVerticalGrid       │  ← 内置懒加载
│  ├── SubcomposeLayout                   │  ← 延迟组合
│  └── Image with Coil (async + disk cache)│  ← 图片优化
├─────────────────────────────────────────┤
│  Network Layer (Ktor Client)            │
│  ├── HTTP/2 多路复用                      │  ← 协议升级
│  ├── Brotli/Gzip Content-Encoding       │  ← 压缩
│  ├── Connection Pool (Keep-Alive)        │  ← 连接复用
│  ├── DNS 预解析 + 预连接                  │  ← 预热
│  └── Response Cache (ETag/304)          │  ← 协商缓存
├─────────────────────────────────────────┤
│  Data Layer (Repository)                │
│  ├── TTL 内存缓存 (歌曲30s, 详情5min)    │
│  ├── Request Batching (合并查询)         │
│  └── Retry + 指数退避                    │
└─────────────────────────────────────────┘
```

### 服务端层 (Supabase + CDN)
```
┌─────────────────────────────────────────┐
│  Supabase (BaaS)                        │
│  ├── PostgREST API (自动 RESTful)       │
│  ├── Row Level Security                │
│  └── Realtime Subscriptions            │
├─────────────────────────────────────────┤
│  CDN / Edge (自动 via Supabase)         │
│  ├── 静态资源缓存 (Storage)             │
│  ├── API 响应缓存 (via CDN)            │
│  └── HTTP/2 + TLS 1.3                  │
└─────────────────────────────────────────┘
```
