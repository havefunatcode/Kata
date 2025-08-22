# Banking Kata

This kata is about replacing an old COBOL accounting software with a modern one, using TDD.

## Requirements

Write a class `Account` that offers the following methods:

* `void deposit(int)`
* `void withdraw(int)`
* `String printStatement()`

An example statement would be:

| Date       | Amount | Balance |
|------------|--------|---------|
| 24.12.2015 | +500   | 500     |
| 23.8.2016  | -100   | 400     |

### Test Case

1. deposit
    1. save_money
    2. validation : 0원이나 음수 금액 입금 불가
    3. balance
2. withdraw
    1. withdraw_money
    2. validation
        - 0원이나 음수 금액 출금 불가
        - 잔액보다 많은 금액 출금 불가
    3. balance
3. printStatement
    1. no_state
    2. deposit
    3. withdraw
    4. sort by date
    5. balance

### Improve

-[ ] deposit, withdraw 동시성
-[ ] Account를 관리하는 1급 객체 생성
-[ ] Account를 관리하는 1급 객체를 사용하여 ID를 사용하여 계좌 조회 후 deposit, withdraw 진행

Link: [Banking Kata](https://kata-log.rocks/banking-kata)
