import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.test.BeforeTest

class AccountTest {

    lateinit var accountService: AccountService
    lateinit var accountRepository: AccountRepository

    @BeforeTest
    fun setup() {
        accountRepository = AccountRepository(
            mutableMapOf(
                1L to Account(1L, 0, mutableListOf()),
                2L to Account(
                    2L,
                    2000,
                    mutableListOf(Transaction(2L, convertStringToInstant("2022-03-08 13:00:12"), 2000))
                ),
                3L to Account(
                    3L,
                    10000,
                    mutableListOf(Transaction(3L, convertStringToInstant("2025-01-05 08:09:30"), 10000))
                ),
                4L to Account(4L, 0, mutableListOf()),
                5L to Account(
                    5L,
                    50000,
                    mutableListOf(Transaction(4L, convertStringToInstant("2020-11-01 11:23:15"), 50000))
                ),
                6L to Account(6L, 0, mutableListOf())
            )
        )

        accountService = AccountService(accountRepository)
    }

    private fun convertStringToInstant(dateString: String): Instant {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(dateString, formatter)
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
    }

    @Test
    fun `find_account`() {
        val account = accountService.getAccount(1L)
        Assertions.assertThat(account.id).isEqualTo(1L)
    }

    @Test
    fun `save_money`() {
        accountService.deposit(100, 2L)
        Assertions.assertThat(accountService.getBalance(2L)).isEqualTo(4100)
    }

    @Test
    fun `withdraw_money`() {
        accountService.deposit(100, 2L)
        accountService.withdraw(50, 2L)
        Assertions.assertThat(accountService.getBalance(2L)).isEqualTo(2050)
    }

    @Test
    fun `print_account_no_statement`() {
        Assertions.assertThat(accountService.printStatement(6L)).isEqualTo(
            "| Date       | Amount | Balance |\n" +
                    "|------------|--------|---------|"
        )
    }

    @Test
    fun `print_account_statement`() {
        accountService.deposit(100, 3L)
        accountService.withdraw(50, 3L)

        val currentDate = Instant.now().atZone(java.time.ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        Assertions.assertThat(accountService.printStatement(3L)).isEqualTo(
            "| Date       | Amount | Balance |\n" +
                    "|------------|--------|---------|\n" +
                    "| ${"05.01.2025"} | +10000 | 10000 |\n" +
                    "| ${currentDate} | +100 | 10100 |\n" +
                    "| ${currentDate} | -50 | 10050 |"
        )
    }

    @Test
    fun `deposit_validation`() {
        Assertions.assertThatThrownBy { accountService.deposit(-1000, 4L) }
            .message().isEqualTo("Amount must be positive")
    }

    @Test
    fun `withdraw_validation_negative_amount`() {
        Assertions.assertThatThrownBy { accountService.withdraw(-1000, 5L) }
            .message().isEqualTo("Amount must be positive")
    }

    @Test
    fun `withdraw_validation_over_balance`() {
        accountService.deposit(1000, 4L)
        Assertions.assertThatThrownBy { accountService.withdraw(1001, 4L) }
            .message().isEqualTo("Insufficient funds")
    }
}