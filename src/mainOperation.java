import java.sql.*;
import java.util.*;
public class mainOperation {
    private Connection conn;

    public mainOperation(Connection conn) {
        this.conn = conn;
    }

    public void createAccount(Scanner sc) {
        System.out.println("ENTER ACCOUNT HOLDER NAME:");
        String name = sc.nextLine();
        System.out.println("ENTER PIN:");
        int pin = sc.nextInt();
        sc.nextLine();

        String query = "INSERT INTO ACCOUNTS (NAME,PIN) VALUES (? , ?);";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,name);
            stmt.setInt(2,pin);
            stmt.executeUpdate();
            System.out.println("ACCOUNT CREATED SUCCESSFULLY.");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAccount(Scanner sc) throws SQLException{
        conn.setAutoCommit(false);
        System.out.println("ENTER ACCOUNT NO NAME:");
        int id = sc.nextInt();
        System.out.println("ENTER PIN:");
        int pin = sc.nextInt();
        sc.nextLine();

        String query = "DELETE FROM ACCOUNTS WHERE ACC_NO = ? AND PIN = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,id);
            stmt.setInt(2,pin);
            stmt.executeUpdate();
            System.out.println("ACCOUNT DELETED SUCCESSFULLY.");
            conn.commit();

        }catch (Exception e){
            System.out.println("DETAILS MISMATCH.");
            conn.rollback();
            e.printStackTrace();
        }
    }

    public void withdraw(Scanner sc) throws SQLException{
        conn.setAutoCommit(false);
        System.out.println("ENTER ACCOUNT NO:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER PIN:");
        int pin = sc.nextInt();
        sc.nextLine();

        accounts a = findById(id);


        if(id == a.getAcc_no() && pin == a.getPin()){
            System.out.println("ENTER AMOUNT TO WITHDRAW:");
            double amt = sc.nextDouble();
            sc.nextLine();

            if(amt<=a.getBalance()){
                String query = "UPDATE ACCOUNTS SET BALANCE = ? WHERE ACC_NO = ?;";
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    double newBal = a.getBalance() - amt;
                    stmt.setDouble(1,newBal);
                    stmt.setInt(2,id);
                    stmt.executeUpdate();

                    conn.commit();
                }catch (Exception e){
                    conn.rollback();
                    e.printStackTrace();
                }


                query = "INSERT INTO TRANSACTIONS (ACC_NO,TYPE,AMOUNT) VALUES (?,?,?);";
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    double newBal = amt;
                    stmt.setDouble(1,id);
                    stmt.setString(2,"WITHDRAW");
                    stmt.setDouble(3,newBal);
                    stmt.executeUpdate();
                    System.out.println("ACCOUNT WITHDRAWN SUCCESSFULLY.");

                    conn.commit();
                }catch (Exception e){
                    conn.rollback();
                    e.printStackTrace();
                }
            }

        }else{
            System.out.println("DETAILS MISMATCH.");
        }
    }

    public void deposit(Scanner sc) throws SQLException{
        conn.setAutoCommit(false);
        System.out.println("ENTER ACCOUNT NO:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER PIN:");
        int pin = sc.nextInt();
        sc.nextLine();

        accounts a = findById(id);


        if(id == a.getAcc_no() && pin == a.getPin()){
            System.out.println("ENTER AMOUNT TO DEPOSIT:");
            double amt = sc.nextDouble();
            sc.nextLine();

            if(amt>0){
                String query = "UPDATE ACCOUNTS SET BALANCE = ? WHERE ACC_NO = ?;";
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    double newBal = a.getBalance() + amt;
                    stmt.setDouble(1,newBal);
                    stmt.setInt(2,id);
                    stmt.executeUpdate();
                    conn.commit();
                }catch (Exception e){
                    conn.rollback();
                    e.printStackTrace();
                }


                query = "INSERT INTO TRANSACTIONS (ACC_NO,TYPE,AMOUNT) VALUES (?,?,?);";
                try(PreparedStatement stmt = conn.prepareStatement(query)){
                    double newBal = amt;
                    stmt.setDouble(1,id);
                    stmt.setString(2,"DEPOSIT");
                    stmt.setDouble(3,newBal);
                    stmt.executeUpdate();
                    System.out.println("ACCOUNT DEPOSITED SUCCESSFULLY.");

                    conn.commit();
                }catch (Exception e){
                    conn.rollback();
                    e.printStackTrace();
                }
            }

        }else{
            System.out.println("DETAILS MISMATCH.");
        }
    }

    public void checkBalance(Scanner sc) {
        System.out.println("ENTER ACCOUNT NO:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER PIN:");
        int pin = sc.nextInt();
        sc.nextLine();

        accounts a = findById(id);

        if(id == a.getAcc_no() && pin == a.getPin()){
            System.out.println("CURRENT BALANCE: "+a.getBalance());
        }
    }

    public accounts findById(int id){
        String query = "SELECT * FROM ACCOUNTS WHERE ACC_NO = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                 return new accounts(rs.getInt(1),
                        rs.getString(2),rs.getInt(3),rs.getDouble(4));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void viewAccounts(){
        String query = "SELECT ACC_NO , NAME FROM ACCOUNTS;";
        try(PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                System.out.println(rs.getInt(1)+" | "+rs.getString(2));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewStatement(Scanner sc) {
        System.out.println("ENTER ACCOUNT NO:");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("ENTER PIN:");
        int pin = sc.nextInt();
        sc.nextLine();

        accounts a = findById(id);

        if(id == a.getAcc_no() && pin == a.getPin()){
            String query = "SELECT * FROM TRANSACTIONS WHERE ACC_NO = ?;";
            try(PreparedStatement stmt = conn.prepareStatement(query)){
                stmt.setInt(1,id);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    System.out.println(rs.getInt(1)+" | "+
                            rs.getInt(2)+" | "+
                            rs.getString(3)+" | "+
                            rs.getDouble(4)+" | "+
                            rs.getObject(5));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
