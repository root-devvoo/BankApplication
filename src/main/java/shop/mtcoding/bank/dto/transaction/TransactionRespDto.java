package shop.mtcoding.bank.dto.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.bank.domain.account.Account;
import shop.mtcoding.bank.domain.transaction.Transaction;
import shop.mtcoding.bank.util.CustomDateUtil;

public class TransactionRespDto {
    @Setter
    @Getter
    public static class TransactionListRespDto {
        private List<TransactionDto> transactions = new ArrayList<>();

        public TransactionListRespDto(List<Transaction> transactions, Account account) {
            this.transactions = transactions.stream()
                    .map((transaction) -> new TransactionDto(transaction, account.getNumber()))
                    .collect(Collectors.toList());
        }

        @Setter
        @Getter
        public class TransactionDto {
            private Long id;
            private String gubun;
            private Long amount;
            private String sender;
            private String receiver;
            private String tel;
            private String createdAt;
            private Long balance;

            public TransactionDto(Transaction transaction, Long accountNumber) {
                this.id = transaction.getId();
                this.gubun = transaction.getGubun().getValue();
                this.amount = transaction.getAmount();
                this.sender = transaction.getSender();
                this.receiver = transaction.getReceiver();
                this.createdAt = CustomDateUtil.toStringFormat((transaction.getCreatedAt()));
                // tel은 입금일때만 나오므로 null이면 없음이라고 하고, 그게 아닐 때만 getTel() 호출
                this.tel = transaction.getTel() == null ? "없음" : transaction.getTel();

                // 1111 계좌의 입출금 내역 (출금계좌 = null, 입금계좌 = 값) (출금계좌 = 값, 입금계좌 = null)
                if (transaction.getDepositAccount() == null) {
                    this.balance = transaction.getWithdrawAccountBalance();
                } else if (transaction.getWithdrawAccount() == null) {
                    this.balance = transaction.getDepositAccountBalance();
                } else {
                    // 1111 계좌의 입출금 내역 (출금계좌 = 값, 입금계좌 = 값)
                    if (accountNumber.longValue() == transaction.getDepositAccount().getNumber()) {
                        this.balance = transaction.getDepositAccountBalance();
                    } else {
                        this.balance = transaction.getWithdrawAccountBalance();
                    }
                }
            }
        }
    }
}
