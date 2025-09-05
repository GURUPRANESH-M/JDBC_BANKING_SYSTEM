import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try(Connection con = db.getConnection()){
            mainOperation m = new mainOperation(con);

            while (true){
                System.out.println("=========== WELCOME TO ATM MACHINE ===========");
                System.out.println("1. CREATE ACCOUNT");
                System.out.println("2. DELETE ACCOUNT");
                System.out.println("3. WITHDRAW");
                System.out.println("4. DEPOSIT");
                System.out.println("5. CHECK BALANCE");
                System.out.println("6. VIEW ACCOUNTS");
                System.out.println("7. VIEW STATEMENT");
                System.out.println("8. EXIT");
                System.out.println("ENTER CHOICE:");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice){
                    case 1:
                        m.createAccount(sc);
                        break;
                    case 2:
                        m.deleteAccount(sc);
                        break;
                    case 3:
                        m.withdraw(sc);
                        break;
                    case 4:
                        m.deposit(sc);
                        break;
                    case 5:
                        m.checkBalance(sc);
                        break;
                    case 6:
                        m.viewAccounts();
                        break;
                    case 7:
                        m.viewStatement(sc);
                        break;
                    case 8:
                        System.out.println("THANK YOU FOR USING OUR SERVICE.........");
                        System.out.println("EXIT SUCCESSFUL.");
                        return;
                    default:
                        System.out.println("INVALID CHOICE.");
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}