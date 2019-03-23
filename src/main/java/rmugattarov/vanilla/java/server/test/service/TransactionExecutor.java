package rmugattarov.vanilla.java.server.test.service;

import org.apache.commons.lang3.StringUtils;
import rmugattarov.vanilla.java.server.test.model.WorkItem;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class TransactionExecutor {
    private final Map<String, BigInteger> accounts = new HashMap<>();

    {
        accounts.put("donald", BigInteger.valueOf(1000));
        accounts.put("vladimir", BigInteger.valueOf(9999));
    }

    public Optional<BigInteger> getBalance(String account) {
        return Optional.ofNullable(accounts.get(account));
    }

    public String addWorkItem(WorkItem workItem) {
        return Optional.ofNullable(workItem)
                .map(i -> {
                    createAccountsIfNecessary(workItem);

                    switch (workItem.getWorkItemType()) {
                        case DEPOSIT:
                            deposit(workItem);
                            break;
                        case WITHDRAW:
                            withdraw(workItem);
                            break;
                        case TRANSFER:
                            transfer(workItem);
                            break;
                    }

                    return toString();
                }).orElse("Something went wrong");
    }

    @Override
    public String toString() {
        return StringUtils.join(accounts.entrySet(), "\n\n============\n\n");
    }

    private void deposit(WorkItem workItem) {
        BigInteger accountBalance = accounts.get(workItem.getTo());
        accounts.put(workItem.getTo(), accountBalance.add(BigInteger.valueOf(workItem.getSum())));
    }

    private void withdraw(WorkItem workItem) {
        BigInteger accountBalance = accounts.get(workItem.getFrom());
        accounts.put(workItem.getFrom(), accountBalance.subtract(BigInteger.valueOf(workItem.getSum())));
    }

    private void transfer(WorkItem workItem) {
        String from = workItem.getFrom();
        String to = workItem.getTo();
        if (Objects.equals(from, to)) return;
        BigInteger accountBalanceFrom = accounts.get(from);
        BigInteger accountBalanceTo = accounts.get(to);
        accounts.put(from, accountBalanceFrom.subtract(BigInteger.valueOf(workItem.getSum())));
        accounts.put(to, accountBalanceTo.add(BigInteger.valueOf(workItem.getSum())));
    }

    private void createAccountsIfNecessary(WorkItem workItem) {
        String from = workItem.getFrom();
        String to = workItem.getTo();
        if (!isBlank(from) && !accounts.containsKey(from)) {
            accounts.put(from, BigInteger.ZERO);
        }
        if (!isBlank(to) && !accounts.containsKey(to)) {
            accounts.put(to, BigInteger.ZERO);
        }
    }
}
