class AccountRepository(
    private val accounts: MutableMap<Long, Account> = mutableMapOf()
) {

    fun save(account: Account) {
        accounts[account.id] = account
    }

    fun findById(id: Long): Account? {
        return accounts[id]
    }

    fun findAll(): List<Account> {
        return accounts.values.toList()
    }

    fun deleteById(id: Long) {
        accounts.remove(id)
    }

    fun count(): Int {
        return accounts.size
    }

    fun clear() {
        accounts.clear()
    }
}