# CHANGELOG

## [1.0.0] — 2025-06-07

### Added
- Initial KMP project for tennis tournament ticket booking
- Data models: Tournament (8 real tournaments), Ticket (4 types), Order, AppUser
- TennisRepository with mock data (Grand Slams + Masters + ATP Finals)
- OrderRepository for order management
- TennisRushHttpClient with Ktor (timeout, retry, content negotiation)
- HomeScreen with category filter tabs (所有/大满贯/大师赛/年终)
- TournamentCard with status badges, info chips, category colors
- TournamentDetailScreen with ticket type selection, pricing display
- OrderScreen with order history list
- ProfileScreen with settings
- CI/CD pipeline for Android and iOS

### Fixed
- APP.md corrected from generic "LaGe" template

### Security
- INTERNET + ACCESS_NETWORK_STATE declared
- HTTPS API endpoint configured
- No auth token mechanism in HttpClient (adds no auth header)
