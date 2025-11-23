import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BasicBankingSystem {
    private static final Scanner sc = new Scanner(System.in);
    private final Bank bank = new Bank();

    public static void main(String[] args) {
        BasicBankingSystem app = new BasicBankingSystem();
        app.seedDemoData();
        app.run();
    }

    private void seedDemoData(){
        bank.createAccount("1001","Alice", 1000.0);
        bank.createAccount("1002","Bob", 500.0);
    }
    private void run(){
        boolean running = true;
        System.out.println("----- Basic Banking System -----");
        while(running){
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice){
                case "1" -> createAccount();
                case "2" -> deposit();
                case "3" -> withdraw();
                case "4" -> transfer();
                case "5" -> viewBalance();
                case "6" -> transactionHistory();
                case "0" -> running = false;
                default -> System.out.println("Invalid choice");
            }
            System.out.println();
        }
        System.out.println("Goodbye!!!!!!!");
    }

    private void printMenu(){
        System.out.println("1) Create account");
        System.out.println("2) Deposit");
        System.out.println("3) Withdraw");
        System.out.println("4) Transfer");
        System.out.println("5) View balance");
        System.out.println("6) View transaction history");
        System.out.println("0) Exit");
        System.out.println("Choice: ");
    }

    private void createAccount(){
        System.out.println("Enter account number: ");
        String accNo = sc.nextLine().trim();
        System.out.println("Enter owner name: ");
        String owner = sc.nextLine().trim();
        System.out.println("Enter initial deposit: ");
        try{
            double amount = Double.parseDouble(sc.nextLine().trim());
            bank.createAccount(accNo, owner, amount);
            System.out.println("Account created.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deposit(){
        System.out.println("Account number: ");
        String acc = sc.nextLine().trim();
        System.out.print("Amount: ");
        try{
            double amt = Double.parseDouble(sc.nextLine().trim());
            bank.deposit(acc, amt);
            System.out.println("Deposit successful.");
        }catch (NumberFormatException e){
            System.out.println("Invalid amount.");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void withdraw(){
        System.out.print("Account number: ");
        String acc = sc.nextLine().trim();
        System.out.print("Amount: ");
        try{
            double amt = Double.parseDouble(sc.nextLine().trim());
            bank.withdraw(acc, amt);
            System.out.println("Withdrawal successful.");
        }catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void transfer(){
        System.out.print("From account: ");
        String from = sc.nextLine().trim();
        System.out.print("To account: ");
        String to = sc.nextLine().trim();
        System.out.print("Amount: ");
        try{
            double amt = Double.parseDouble(sc.nextLine().trim());
            bank.transfer(from, to, amt);
            System.out.println("Transfer successful.");
        }catch (NumberFormatException e){
            System.out.println("Invalid amount.");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    private void viewBalance(){
        System.out.println("Account number: ");
        String acc = sc.nextLine().trim();
        Optional<Account> a = bank.findAccount(acc);
        if(a.isPresent()){
            System.out.printf("Account %s (%s) - Balance: %.2f%n", a.get().getAccountNumber(), a.get().getOwner(), a.get().getBalance());
        } else System.out.println("Account not found.");
    }

    private void transactionHistory(){
        System.out.print("Account number: ");
        String acc = sc.nextLine().trim();
        Optional<Account> a = bank.findAccount(acc);
        if(a.isPresent()){
            System.out.println("Transactions for " + acc + ":");
            for(String tx : a.get().getTransactions()){
                System.out.println(" - " + tx);
            }
        }else System.out.println("Account not found.");
    }

    static class Bank{
        private final List<Account> accounts = new ArrayList<>();

        public void createAccount(String number, String owner, double initialDeposit){
            if(findAccount(number).isPresent()) throw new IllegalArgumentException("Account already exists.");
            if(initialDeposit<0) throw new IllegalArgumentException("Initial deposit cannot be negative.");
            Account acc = new Account(number, owner, initialDeposit);
            accounts.add(acc);
            acc.addTransaction("Account created with deposit: " + initialDeposit);
        }
        public Optional<Account> findAccount(String number){
            return accounts.stream().filter(a -> a.getAccountNumber().equals(number)).findFirst();
        }

        public void deposit(String number, double amount) {
            Account a = findAccount(number).orElseThrow(() -> new IllegalArgumentException("Account not found."));
            if (amount <= 0) throw new IllegalArgumentException("Deposit must be > 0.");
            a.credit(amount);
            a.addTransaction("Deposit: " + amount);
        }

        public void withdraw(String number, double amount) {
            Account a = findAccount(number).orElseThrow(() -> new IllegalArgumentException("Account not found."));
            if (amount <= 0) throw new IllegalArgumentException("Withdrawal must be > 0.");
            if (a.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds.");
            a.debit(amount);
            a.addTransaction("Withdrawal: " + amount);
        }

        public void transfer(String from, String to, double amount) {
            if (from.equals(to)) throw new IllegalArgumentException("Cannot transfer to same account.");
            Account aFrom = findAccount(from).orElseThrow(() -> new IllegalArgumentException("Source account not found."));
            Account aTo = findAccount(to).orElseThrow(() -> new IllegalArgumentException("Destination account not found."));
            if (amount <= 0) throw new IllegalArgumentException("Amount must be > 0.");
            if (aFrom.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds in source account.");
            aFrom.debit(amount);
            aTo.credit(amount);
            String tx = String.format("Transfer of %.2f from %s to %s", amount, from, to);
            aFrom.addTransaction("Sent: " + tx);
            aTo.addTransaction("Received: " + tx);
        }
    }

    static class Account {
        private final String accountNumber;
        private final String owner;
        private double balance;
        private final List<String> transactions = new ArrayList<>();

        Account(String accountNumber, String owner, double balance) {
            this.accountNumber = accountNumber;
            this.owner = owner;
            this.balance = balance;
        }

        public String getAccountNumber() { return accountNumber; }
        public String getOwner() { return owner; }
        public double getBalance() { return balance; }

        public void credit(double amt) { balance += amt; }
        public void debit(double amt) { balance -= amt; }

        public void addTransaction(String tx) { transactions.add(tx); }
        public List<String> getTransactions() { return new ArrayList<>(transactions); }
    }
}

