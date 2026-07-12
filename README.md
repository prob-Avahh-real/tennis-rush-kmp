# 网球赛票 (TennisRush)

网球赛事门票应用 — 浏览赛事、选座购票、订单管理。

## 技术架构

```
tennis-rush-kmp/
├── composeApp/              # Compose Multiplatform UI
│   └── src/commonMain/
│       └── kotlin/com/tennisrush/ui/screens/
│           ├── HomeScreen              # 赛事列表
│           ├── TournamentDetailScreen  # 赛事详情
│           ├── OrderScreen             # 购票下单
│           ├── ProfileScreen           # 个人中心
│           └── TennisRushApp
├── shared/                  # KMP 共享模块
│   └── src/commonMain/
│       └── kotlin/com/tennisrush/
│           ├── model/       # Tournament, Ticket, User
│           └── data/        # HttpClient, MockData
├── iosApp/                  # iOS 原生入口
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## 构建命令

```bash
./gradlew build                # 构建全部
./gradlew :shared:build        # 共享模块
./gradlew :composeApp:build    # UI 模块
```

## 测试命令

```bash
./gradlew :shared:check        # 共享模块测试
./gradlew :composeApp:check    # UI 模块测试
./gradlew allTests             # 全平台测试
```
