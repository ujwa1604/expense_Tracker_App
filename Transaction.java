import java.time.LocalDate;

public class Transaction {
    private TransactionType type;
    private double amount;
    private String category;
    private LocalDate date;

    public Transaction(TransactionType type, double amount, String category, LocalDate date) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public TransactionType getType(){
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return type.toString().toLowerCase() + "," + amount + "," + category + "," + date;
    }

    public static Transaction fromtoString(String line){
        String[] parts = line.split(",");
        return new Transaction(
                TransactionType.valueOf(parts[0].toUpperCase()),
                Double.parseDouble(parts[1]),
                parts[2],
                LocalDate.parse(parts[3])
        );
    }
}


