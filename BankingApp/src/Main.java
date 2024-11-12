
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        while (true) {
            int ID_Input;
            int ID_Count = 0; // Set to 0 to take input continuously
            Scanner InputScanner;
            Database db = new Database();
            User user = new User();
            int OperationInput;
            boolean isOperationInputValid=false;
            boolean isDepositInputValid=false;
            int DepositInput=0;

            db.ConnectDb();

            //* Getting the ID count to login */
            while (ID_Count == 0) {

                try {
                    //* Getting Input and getting the Id count */
                    System.out.print("Please enter an ID to login: ");
                    InputScanner = new Scanner(System.in);
                    ID_Input = InputScanner.nextInt();

                    ID_Count = db.GetIDCount(ID_Input);

                    //* ID Found */
                    if (ID_Count == 1) {
                        // Fetch everything from database
                        System.out.println("User found!");

                        user.setID(ID_Input);
                        System.out.println("ID set to " + user.getID());

                        db.GetUserMoney(user);
                        System.out.println("Money set to " + user.getMoney());
                    } else {
                        System.out.println("Invalid ID, please try again.");
                    }

                } catch (Exception e) {
                    System.out.println("An error occurred on login.");
                }
            }




            //*Getting the operation input */
            while (!isOperationInputValid) {
                
                try{
                    System.out.println("Balance:"+user.getMoney());
                    System.out.println("Welcome to the System.Please select an operation to make:");
        
                    System.out.println("");
        
                    System.out.println("[1] Witdrawing money");
                    System.out.println("[2] Depositing Money");

                    InputScanner=new Scanner(System.in);
                    OperationInput=InputScanner.nextInt();




                    //*Withdrawing money */
                    if(OperationInput==1){

                        int WithDrawInput;
                        boolean isWithdrawAmountValid=false;

                        while (!isWithdrawAmountValid) {
                            try{
                                System.out.println("Please enter the amount that you want to withdraw(Press 0 to quit):");
                                InputScanner=new Scanner(System.in);
                                WithDrawInput=InputScanner.nextInt();

                                //*Quitting */
                                if(WithDrawInput==0){
                                    isWithdrawAmountValid=true;
                                }
    
                                //*Updating Money */
    
                                //*Input is greater than user's balance */
                                else if(WithDrawInput>user.getMoney()){
                                    System.out.println("The amount of money that you want to withdraw can not be 0 or greater than your balance");
                                    System.out.println(user.getMoney());
                                }
                                else{

                                    //*Updating money */
                                    db.WithDrawMoney(user, WithDrawInput);
                                }
                            }
                            catch(Exception e){
                                System.out.println("Invalid Input!");
                            }
                        }
                    }

                    //*Depositing Money */
                    else if(OperationInput==2){

                        isDepositInputValid=false;//This variable is set to false to tak input after quitting

                        while (!isDepositInputValid) {
                            
                            try{
                                System.out.print("Please enter the amount of money that you want to deposit(Press 0 to quit):");
                                InputScanner=new Scanner(System.in);
                                DepositInput=InputScanner.nextInt();

                                //* Quitting*/
                                if(DepositInput==0){
                                    isDepositInputValid=true;
                                }

                                //*Depositing money */
                                else{
                                    db.DepositMoney(user, DepositInput);
                                }
                            }
                            catch(Exception e){
                                System.out.println("An error occured on money deposit");
                            }
                        }
                    }

                    //*Invalid operation input */
                    else{
                        System.out.println("Invalid operation Input!");
                    }


                }
                catch(Exception e){
                    System.out.println("An error occured!");
                }
            }
        }
    }
}
