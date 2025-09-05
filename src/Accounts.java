public class Accounts {
    private int acc_no;
    private String name;
    private int pin;
    private double balance;

    public Accounts(int acc_no, String name, int pin, double balance) {
        this.acc_no = acc_no;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(int acc_no) {
        this.acc_no = acc_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
