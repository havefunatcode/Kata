import java.time.Instant
import java.time.format.DateTimeFormatter

class AccountService(
    private val accountRepository: AccountRepository
) {

    private var transactionId: Long = 1L

    fun getAccount(id: Long): Account =
        accountRepository.findById(id)
            ?: throw IllegalArgumentException("Account not found")

    fun getBalance(id: Long): Int = getAccount(id).balance

    fun deposit(amount: Int, accountId: Long) {
        depositValidation(amount)
        val transaction = Transaction(transactionId++, Instant.now(), amount)
        val account = getAccount(accountId)
        account.transactions.add(transaction)
        account.balance += account.transactions.sumOf { it.amount }
    }

    fun depositValidation(amount: Int) {
        if (amount < 0) {
            throw IllegalArgumentException("Amount must be positive")
        }
    }

    fun withdraw(amount: Int, accountId: Long) {
        val account = getAccount(accountId)
        withdrawValidation(amount, account)

        val transaction = Transaction(transactionId++, Instant.now(), -amount)

        account.transactions.add(transaction)
        account.balance -= account.transactions.sumOf { it.amount }
    }

    fun withdrawValidation(amount: Int, account: Account) {
        if (amount < 0) {
            throw IllegalArgumentException("Amount must be positive")
        }

        if (amount > account.balance) {
            throw IllegalArgumentException("Insufficient funds")
        }
    }

    fun printStatement(id: Long): String {
        val lineFormat = "| %-10s | %2s | %2s |"

        val header = String.format(lineFormat, "Date", "Amount", "Balance")
        val separator = "|------------|--------|---------|"

        val statementLines = mutableListOf(header, separator)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            .withZone(java.time.ZoneId.systemDefault())
        var runningBalance = 0

        val account = getAccount(id)
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
