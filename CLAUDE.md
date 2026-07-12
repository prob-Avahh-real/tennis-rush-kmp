# TennisRush (网球赛票) — KMP 项目

## 项目概述
网球赛事门票应用，Kotlin Multiplatform + Compose Multiplatform。

## 构建命令
- 全量构建: `./gradlew build`
- 共享模块: `./gradlew :shared:build`
- UI 模块: `./gradlew :composeApp:build`
- 全平台测试: `./gradlew allTests`
- 清理: `./gradlew clean`

## 文件组织
```
composeApp/src/commonMain/  → Compose UI (screens 含购票、选座)
shared/src/commonMain/      → 共享业务逻辑 (model, data)
iosApp/                     → iOS 原生入口
```

## 关键配置
- 项目名: `TennisRush` (settings.gradle.kts)
- 模块: `:composeApp`, `:shared`
- 序列化: kotlinx.serialization
- HTTP: Ktor Client
- UI: Compose Multiplatform + Material3
- 模型: Tournament, Ticket, Order, User (含 category/surface/status 枚举)
- 模拟数据: MockData.kt
