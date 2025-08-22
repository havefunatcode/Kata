import java.time.Instant
import java.time.format.DateTimeFormatter

class AccountService(
    private val account: Account
) {

    private var transactionId: Long = 1L

    fun getBalance(): Int {
        return account.balance
    }

    fun deposit(amount: Int) {
        depositValidation(amount)
        val transaction = Transaction(transactionId++, Instant.now(), amount)
        account.transactions.add(transaction)
        account.balance += account.transactions.sumOf { it.amount }
    }

    fun depositValidation(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Amount must be positive")
        }
    }

    fun withdraw(amount: Int) {
        withdrawValidation(amount)

        val transaction = Transaction(transactionId++, Instant.now(), -amount)
        account.transactions.add(transaction)
        account.balance -= account.transactions.sumOf { it.amount }
    }

    fun withdrawValidation(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Amount must be positive")
        }

        if (amount > account.balance) {
            throw IllegalArgumentException("Insufficient funds")
        }
    }

    fun printStatement(): String {
        val lineFormat = "| %-10s | %2s | %2s |"

        val header = String.format(lineFormat, "Date", "Amount", "Balance")
        val separator = "|------------|--------|---------|"

        val statementLines = mutableListOf(header, separator)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            .withZone(java.time.ZoneId.systemDefault())
        var runningBalance = 0

        account.transactions.sortedBy { it.time }
            .map { transaction ->
                val dateStr = formatter.format(transaction.time)
                val amountStr =
                    if (transaction.amount > 0) "+${transaction.amount}" else transaction.amount.toString()
                runningBalance += transaction.amount
                val balanceStr = runningBalance.toString()

                statementLines.add(String.format(lineFormat, dateStr, amountStr, balanceStr))
            }

        return statementLines.joinToString("\n")
    }
}
