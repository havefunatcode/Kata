data class Account(
    val id: Long,
    var balance: Int,
    val transactions: MutableList<Transaction>
)