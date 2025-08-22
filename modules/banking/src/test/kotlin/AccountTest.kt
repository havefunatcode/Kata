import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.format.DateTimeFormatter
import kotlin.test.BeforeTest

class AccountTest {

    lateinit var accountService: AccountService

    @BeforeTest
    fun setup() {
        accountService = AccountService(
            Account(1L, 0, mutableListOf())
        )
    }

    @Test
    fun `save_money`() {
        accountService.deposit(100)
        Assertions.assertThat(accountService.getBalance()).isEqualTo(100)
    }

    @Test
    fun `withdraw_money`() {
        accountService.deposit(100)
        accountService.withdraw(50)
        Assertions.assertThat(accountService.getBalance()).isEqualTo(50)
    }

    @Test
    fun `print_account_no_statement`() {
        Assertions.assertThat(accountService.printStatement()).isEqualTo(
            "| Date       | Amount | Balance |\n" +
                    "|------------|--------|---------|"
        )
    }

    @Test
    fun `print_account_statement`() {
        accountService.deposit(100)
        accountService.withdraw(50)

        val currentDate = Instant.now().atZone(java.time.ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        Assertions.assertThat(accountService.printStatement()).isEqualTo(
            "| Date       | Amount | Balance |\n" +
                    "|------------|--------|---------|\n" +
                    "| ${currentDate} | +100 | 100 |\n" +
                    "| ${currentDate} | -50 | 50 |"
        )
    }

    @Test
    fun `deposit_validation`() {
        Assertions.assertThatThrownBy { accountService.deposit(-1000) }
            .message().isEqualTo("Amount must be positive")
    }

    @Test
    fun `withdraw_validation_negative_amount`() {
        Assertions.assertThatThrownBy { accountService.withdraw(-1000) }
            .message().isEqualTo("Amount must be positive")
    }

    @Test
    fun `withdraw_validation_over_balance`() {
        accountService.deposit(1000)
        Assertions.assertThatThrownBy { accountService.withdraw(1001) }
            .message().isEqualTo("Insufficient funds")
    }
}