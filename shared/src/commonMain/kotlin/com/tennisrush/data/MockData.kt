package com.tennisrush.data

import com.tennisrush.model.*
import kotlinx.datetime.Clock

object TennisRepository {
    val tournaments = listOf(
        Tournament("ao-2027", "Australian Open", "澳大利亚网球公开赛", TournamentCategory.GRAND_SLAM, Surface.HARD, "Melbourne", "Australia", "AU", "2027-01-18T00:00:00", "2027-01-31T00:00:00", "https://images.unsplash.com/photo-1591195830540-c7015e35e86e?w=800", TicketSaleStatus.BALLOT, "2026-10-01T00:00:00", "https://ausopen.com/tickets", "AUD 86,500,000", "Jannik Sinner", "年度第一项大满贯赛事，在墨尔本公园举行。硬地球场，盛夏激战。"),
        Tournament("rg-2027", "Roland Garros", "法国网球公开赛", TournamentCategory.GRAND_SLAM, Surface.CLAY, "Paris", "France", "FR", "2027-05-23T00:00:00", "2027-06-05T00:00:00", "https://images.unsplash.com/photo-1544473244-f6895e69ad8b?w=800", TicketSaleStatus.UPCOMING, "2027-03-08T00:00:00", "https://tickets.rolandgarros.com", "EUR 53,000,000", "Carlos Alcaraz", "唯一在红土上进行的大满贯赛事，巴黎罗兰加洛斯球场见证传奇。"),
        Tournament("wim-2027", "Wimbledon", "温布尔登网球锦标赛", TournamentCategory.GRAND_SLAM, Surface.GRASS, "London", "United Kingdom", "GB", "2027-06-28T00:00:00", "2027-07-11T00:00:00", "https://images.unsplash.com/photo-1531315396756-905549cb120a?w=800", TicketSaleStatus.UPCOMING, "2027-03-01T00:00:00", "https://www.wimbledon.com", "GBP 50,000,000", "Novak Djokovic", "网球运动最古老、最具声望的赛事，全英俱乐部草坪上的白色盛宴。"),
        Tournament("us-2026", "US Open", "美国网球公开赛", TournamentCategory.GRAND_SLAM, Surface.HARD, "New York", "United States", "US", "2026-08-30T00:00:00", "2026-09-12T00:00:00", "https://images.unsplash.com/photo-1531315396756-905549cb120a?w=800", TicketSaleStatus.ON_SALE, "2026-06-01T00:00:00", "https://www.usopen.org", "USD 75,000,000", "Jannik Sinner", "四大满贯收官之战，纽约法拉盛公园的硬地狂欢。"),
        Tournament("iw-2027", "BNP Paribas Open", "印第安维尔斯大师赛", TournamentCategory.ATP_1000, Surface.HARD, "Indian Wells, CA", "United States", "US", "2027-03-08T00:00:00", "2027-03-21T00:00:00", "https://images.unsplash.com/photo-1622279457486-62dcc4a431d6?w=800", TicketSaleStatus.UPCOMING, "2026-11-01T00:00:00", "https://bnpparibasopen.com/tickets", "USD 12,000,000", "Carlos Alcaraz", "「第五大满贯」，加州沙漠中的网球天堂。"),
        Tournament("miami-2027", "Miami Open", "迈阿密公开赛", TournamentCategory.ATP_1000, Surface.HARD, "Miami, FL", "United States", "US", "2027-03-22T00:00:00", "2027-04-04T00:00:00", "https://images.unsplash.com/photo-1536348600380-ddc493ffebd1?w=800", TicketSaleStatus.UPCOMING, "2026-12-01T00:00:00", "https://www.miamiopen.com/tickets", "USD 10,000,000", "Jannik Sinner", "阳光双赛第二站，迈阿密硬石球场的碧海蓝天。"),
        Tournament("shanghai-2026", "Rolex Shanghai Masters", "上海大师赛", TournamentCategory.ATP_1000, Surface.HARD, "Shanghai", "China", "CN", "2026-10-04T00:00:00", "2026-10-18T00:00:00", "https://images.unsplash.com/photo-1537531383496-f4749b88b2b1?w=800", TicketSaleStatus.ON_SALE, "2026-07-01T00:00:00", "https://en.rolexshanghaimasters.com/tickets", "USD 12,000,000", "Jannik Sinner", "亚洲唯一的ATP1000赛事，旗忠网球中心的巅峰对决。"),
        Tournament("finals-2026", "Nitto ATP Finals", "ATP年终总决赛", TournamentCategory.FINALS, Surface.HARD, "Turin", "Italy", "IT", "2026-11-14T00:00:00", "2026-11-21T00:00:00", "https://images.unsplash.com/photo-1599586120429-48281b6f0ece?w=800", TicketSaleStatus.ON_SALE, "2026-05-01T00:00:00", "https://www.nittoatpfinals.com", "USD 15,000,000", "Jannik Sinner", "年终八强巅峰对决，都灵室内硬地的王者加冕。")
    )

    fun getTickets(tournamentId: String): List<Ticket> {
        val t = tournaments.firstOrNull { it.id == tournamentId } ?: return emptyList()
        val basePrice = basePrice(t)
        val currency = currency(t)
        val count = availableCount(t)
        val startDate = t.startDate.take(10)
        val endDate = t.endDate.take(10)
        val mainCourt = mainCourt(t)
        return listOf(
            Ticket("${tournamentId}-gp-1", tournamentId, TicketType.GROUND_PASS, TicketSession.DAY, "Grounds", "R1-R4", startDate, basePrice.toDouble(), currency, count, saleStart = t.ticketSaleDate),
            Ticket("${tournamentId}-rs-1", tournamentId, TicketType.RESERVED_SEAT, TicketSession.DAY, mainCourt, "QF-F", startDate, basePrice * 3.0, currency, (count * 0.3).toInt(), saleStart = t.ticketSaleDate),
            Ticket("${tournamentId}-rs-2", tournamentId, TicketType.RESERVED_SEAT, TicketSession.NIGHT, mainCourt, "SF", startDate, basePrice * 4.0, currency, (count * 0.15).toInt(), maxPerUser = 2, saleStart = t.ticketSaleDate, isHot = true),
            Ticket("${tournamentId}-vip-1", tournamentId, TicketType.VIP, TicketSession.FULL_DAY, mainCourt, "Final", endDate, basePrice * 10.0, currency, (count * 0.05).toInt(), maxPerUser = 2, saleStart = t.ticketSaleDate, isHot = true)
        )
    }

    fun basePrice(t: Tournament): Int = when (t.category) {
        TournamentCategory.GRAND_SLAM -> 80
        TournamentCategory.ATP_1000, TournamentCategory.WTA_1000 -> 45
        TournamentCategory.FINALS -> 100
        else -> 30
    }

    fun currency(t: Tournament): String = when (t.countryCode) {
        "AU" -> "AUD"; "GB" -> "GBP"; "FR", "MC", "ES", "IT" -> "EUR"; "CN" -> "CNY"; else -> "USD"
    }

    fun mainCourt(t: Tournament): String = when (t.id) {
        "ao-2027" -> "Rod Laver Arena"; "rg-2027" -> "Court Philippe-Chatrier"; "wim-2027" -> "Centre Court"; "us-2026" -> "Arthur Ashe Stadium"; "shanghai-2026" -> "中央球场"; "finals-2026" -> "Pala Alpitour"; else -> "Stadium Court"
    }

    fun availableCount(t: Tournament): Int = when (t.ticketStatus) {
        TicketSaleStatus.SOLD_OUT -> 0
        TicketSaleStatus.SELLING_FAST -> 50 + (Clock.System.now().toEpochMilliseconds() % 200).toInt()
        TicketSaleStatus.ON_SALE -> 500 + (Clock.System.now().toEpochMilliseconds() % 1000).toInt()
        else -> -1
    }
}

object OrderRepository {
    private val _orders = mutableListOf<Order>()

    suspend fun getOrders(userId: String): List<Order> {
        _orders.sortByDescending { it.createdAt }
        return _orders.toList()
    }

    suspend fun createOrder(
        userId: String, ticketId: String, quantity: Int, totalPrice: Double, currency: String,
        tournamentName: String?, ticketType: String?, sessionDate: String?, court: String?, round: String?
    ): Order {
        val now = Clock.System.now().toString()
        val order = Order(
            id = "ORD${Clock.System.now().toEpochMilliseconds()}",
            userId = userId, ticketId = ticketId, quantity = quantity,
            totalPrice = totalPrice, currency = currency, status = OrderStatus.PENDING,
            createdAt = now, tournamentName = tournamentName, ticketType = ticketType,
            sessionDate = sessionDate, court = court, round = round
        )
        _orders.add(order)
        return order
    }

    suspend fun cancelOrder(orderId: String) {
        val idx = _orders.indexOfFirst { it.id == orderId }
        if (idx != -1) _orders[idx] = _orders[idx].copy(status = OrderStatus.CANCELLED)
    }
}
