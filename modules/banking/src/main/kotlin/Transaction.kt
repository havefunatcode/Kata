import java.time.Instant

data class Transaction(
    val id: Long,
    val time: Instant,
    val amount: Int
)
