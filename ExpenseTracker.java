import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class ExpenseTracker {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Transaction> transactions = new ArrayList<>();

    public static void main(String[] args) {
       boolean running = true;

       while(running){
           System.out.println("\n===== Expense Tracker Menu =====");
           System.out.println("1. Add Transaction (Income/Expense)");
           System.out.println("2. Load Transactions from File");
           System.out.println("3. Save Transactions to File");
           System.out.println("4. View Monthly Summary");
           System.out.println("5. Exit");
           System.out.print("Choose an option: ");
           int options = sc.nextInt();
           sc.nextLine();

           switch (options){
               case 1 -> addTransaction();
               case 2 -> loadFromFile();
               case 3 -> saveToFile();
               case 4 -> viewMonthlySummary();
               case 5 ->{
                   running = false;
               System.out.println("Thanks for using Expense Tracker");
               }
               default -> System.out.println("Invalid option! Please Try again");
           }
       }
    }

    public static void addTransaction(){
        System.out.println("Enter Type (income/expense) !");
        String typeInput = sc.nextLine().trim().toLowerCase();
        TransactionType type;

        if(typeInput.equals("income")){
            type = TransactionType.INCOME;
        }else if(typeInput.equals("expense")){
            type = TransactionType.EXPENSE;
        }else{
            System.out.println("Invalid type.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter category (e.g., salary/business/food/rent/travel): ");
        String category = sc.nextLine();

        System.out.print("Enter date (yyyy-mm-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine());

        transactions.add(new Transaction(type, amount, category, date));
        System.out.println("Transaction added.");
    }

    private static void viewMonthlySummary() {
        Map<Month, Double> incomeMap = new HashMap<>();
        Map<Month, Double> expenseMap = new HashMap<>();

        for (Transaction t : transactions) {
            Month month = t.getDate().getMonth();
            if (t.getType() == TransactionType.INCOME) {
                incomeMap.put(month, incomeMap.getOrDefault(month, 0.0) + t.getAmount());
            } else {
                expenseMap.put(month, expenseMap.getOrDefault(month, 0.0) + t.getAmount());
            }
        }

        Set<Month> allMonths = new HashSet<>();
        allMonths.addAll(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        System.out.println("\n----- Monthly Summary -----");
        for (Month month : allMonths.stream().sorted().toList()) {
            double income = incomeMap.getOrDefault(month, 0.0);
            double expense = expenseMap.getOrDefault(month, 0.0);
            double net = income - expense;
            System.out.printf("%s => Income: ₹%.2f | Expense: ₹%.2f | Net: ₹%.2f%n",
                    month, income, expense, net);
        }
    }

    private static void saveToFile() {
        System.out.print("Enter filename to save");
        String filename = "C:/Users/ujwal/Documents/data.txt";

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                writer.println(t);
            }
            System.out.println("Transactions saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    private static void loadFromFile() {
        System.out.print("Enter filename to load from ");
        String filename = "C:/Users/ujwal/Documents/data.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                try {
                    transactions.add(Transaction.fromtoString(line));
                    count++;
                } catch (Exception e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
            System.out.println(count + " transactions loaded from " + filename);
        } catch (IOException e) {
            System.out.println("File not found or error reading file.");
        }
    }
}
