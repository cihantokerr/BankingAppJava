import java.sql.*;

public class Database {
    String url = "jdbc:mysql://localhost:3306/banking_database";
    String username = "root";
    String password = "";
    Connection conn;
    PreparedStatement statement;
    ResultSet result_set;
    boolean ConnectionStatus = false;
    int idcount;
    int money;

    // Database Connection
    public void ConnectDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established!");
            ConnectionStatus = true;
        } catch (Exception e) {
            System.out.println("Database connection error: " + e.getMessage());
            ConnectionStatus = false;
        }
    }

    // Getting user money
    public void GetUserMoney(User user) {
        if (ConnectionStatus) {
            try {
                statement = conn.prepareStatement("SELECT Money FROM users WHERE ID=?");
                statement.setInt(1, user.getID());

                result_set = statement.executeQuery();

                if (result_set.next()) {
                    user.setMoney(result_set.getInt(1));  
                }

            } catch (Exception e) {
                System.out.println("An error occurred on getting money");
            }
        }
    }

    // Get ID count
    public int GetIDCount(int ID) {
        if (ConnectionStatus) {
            try {
                statement = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE ID=?");
                statement.setInt(1, ID);

                result_set = statement.executeQuery();

                if (result_set.next()) {
                    idcount = result_set.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("An error occurred on fetching ID: " + e.getMessage());
            }
        } else {
            System.out.println("A problem with the connection occurred!");
        }
        return idcount;
    }

    // Withdraw money
    public void WithDrawMoney(User user,int MoneytoWithDraw) throws SQLException {
        if (ConnectionStatus) {
            try {
                int newBalance = user.getMoney() - MoneytoWithDraw;

                statement = conn.prepareStatement("UPDATE users SET Money=? WHERE ID=?");
                statement.setInt(1, newBalance);
                statement.setInt(2, user.getID());

                int rowsUpdated = statement.executeUpdate(); 

                if (rowsUpdated > 0) {
                    user.setMoney(newBalance); 
                    System.out.println("Withdrawal is complete!");
                    System.out.println("New balance: " + user.getMoney());
                }
            } catch (Exception e) {
                System.out.println("An error occurred during withdrawal: " + e.getMessage());
            }
        }
    }


    public void DepositMoney(User user,int MoneytoDeposit){
        if(ConnectionStatus){


            try {

                int NewBalance=user.getMoney()+MoneytoDeposit;
                statement=conn.prepareStatement("UPDATE users SET Money=? WHERE ID=?");

                statement.setInt(1, NewBalance);
                statement.setInt(2, user.getID());

                int rowsUpdated = statement.executeUpdate(); 

                if (rowsUpdated > 0) {
                    user.setMoney(NewBalance); 
                    System.out.println("Withdrawal is complete!");
                    System.out.println("New balance: " + user.getMoney());
                }

            } catch (Exception e) {
                System.out.println("An error on updating money");
            }
        }
    }
}
