/* 
* Author: Avishek Sapkota Sharma
* Student No: 3393440
* Date: 08-04-2022
*/

import java.util.*;

public class CalculatorInterface{
    
    public void run(Scanner console){
        Client client = new Client();
        Account account = new Account();
        client.setSavingAccount(account);
        
        String inputName, inputFirstName, inputLastName;
        double annualIncome;
        boolean residencyStatus;
        String auxResidencyStatus;
        double expenditure;
        double investmentValue;
        double interestRate;
        int investmentLength;
        
        int iteration = 0;
        do{
            if (iteration!=0){
                System.out.println("Please enter a valid first name! Must contain only alphabets!");
                //if iteration is not equal to zero(meaning user input was not accepted), warning message is shown
            }
            iteration++;
            System.out.print("First name : ");
            inputFirstName = console.nextLine().replaceAll("\\s+","");
            //removing all whitespaces and non-visible characters (e.g., tab, \n)
        }
        while(inputFirstName==null || !(inputFirstName.matches("[a-zA-Z]+")));
        //runs the do..while loop until inputFirstName is not null and contains only alphabets
        iteration = 0;
        do{
            if (iteration!=0){
                System.out.println("Please enter a valid last name! Must contain only alphabets!");
                //if iteration is not equal to zero(meaning user input was not accepted), warning message is shown
            }
            iteration++;
            System.out.print("Last name : ");
            inputLastName = console.nextLine().replaceAll("\\s+","");
            //removing all whitespaces and non-visible characters (e.g., tab, \n)
        }
        while(inputLastName==null || !(inputLastName.matches("[a-zA-Z]+")));
        //runs the do..while loop until inputLastName is not null and contains only alphabets
        inputName = inputFirstName + " " + inputLastName;
        iteration = 0;
        do{
            if (iteration!=0){
                System.out.println("Please enter valid income! Must only contain integers and be greater than 0!");
            }
            iteration++;
            System.out.print("Annual income : ");
            annualIncome = console.nextDouble();
        }
        while(annualIncome<=0);
        //runs the do..while loop until annualIncome is greater than 0
        iteration = 0;
        do{
            if (iteration!=0){
                System.out.println("Please enter valid option! Must be either true or false!");
            }
            iteration++;
            System.out.print("Residency(true/false) : ");
            auxResidencyStatus = console.next();
            auxResidencyStatus = auxResidencyStatus.toLowerCase();
        }
        while(!(auxResidencyStatus.equals("true")) && !(auxResidencyStatus.equals("false")));
        //runs the do..while loop until auxResidencyStatus is not equal to "true" or "false"
        //auxResidencyStatus is used so that the user input can be checked and looped if it is not boolean
        residencyStatus = Boolean.parseBoolean(auxResidencyStatus);
        //Boolean.parseBoolean(str) is used to change the type of str to boolean
        
        client.setData(inputName,annualIncome,residencyStatus);
        printer(client);
        //calls the printer method inside this class, with parameter client
        
        System.out.print("\nWeekly expenditure : ");
        expenditure = console.nextDouble();
        while (expenditure<1 || expenditure>(client.getNetSalary()/52)){
            //runs the while loop until the expenditure is a positive integer less than weekly salary
            System.out.println("Your expenditure cannot be negative, equal to zero or less than your weekly net salary!");
            System.out.print("Do you want to re-enter your expenditure(1) or your annual income(2) or exit(3) : ");
            int option = console.nextInt();
            if (option==1){
                System.out.print("Re-enter your expenditure : ");
                expenditure = console.nextDouble();
            }
            else if (option==2){
                System.out.print("Re-enter your annual income : ");
                annualIncome = console.nextDouble();
                client.changeAnnualIncome(annualIncome);
                //changes the annual income in the Client class
                printer(client);
                //using the printer method again, since client's annual income has changed
            }
            else if (option==3){
                System.exit(0);
                //terminates the program
            }
            else {
                System.out.println("Option " + option + " is invalid!");
            }
        }
        
        if ((client.getNetSalary()/52)-expenditure>0){
            System.out.print("\nWould you like to invest some money(y/n)? : ");
            char option = console.next().charAt(0);
            //assigns the variable with charater at 0th index
            option = Character.toLowerCase(option);
            //changing option to lowercase alphabets
            if (option=='y'){
                //enters the if loop if option is equal to 'y'
                System.out.print("Investment amount : ");
                investmentValue = console.nextDouble();
                while (investmentValue<0 || (client.getNetSalary()/52)-expenditure-investmentValue<0){
                    //runs the while loop until the input is a positive integer less/equal to weekly salary minus expenditure
                    System.out.println("Investment amount not feasible!");
                    System.out.print("Re-enter investment amount : ");
                    investmentValue = console.nextDouble();
                }
                System.out.print("Interest rate : ");
                interestRate = console.nextDouble();
                while (interestRate>20 || interestRate<1){
                    //runs the while loop until the rate is within range 1 and 20
                    System.out.println("Interest rate must be within range 1% and 20%!");
                    System.out.print("Re-enter interest rate : ");
                    interestRate = console.nextDouble();
                }
                System.out.print("Investment length(# of weeks) : ");
                investmentLength = console.nextInt();
                while (investmentLength<=0){
                    //runs the loop while time period is less/equal to 0
                    System.out.println("Investment length cannot be less than or equal to zero!");
                    System.out.print("Re-enter investment length(weeks) : ");
                    investmentLength = console.nextInt();
                }
                
                client.getSavingAccount().setData(investmentValue, interestRate, investmentLength);
                //sends the parameters to the method in Client class, which further sends them to Account class
                
                String investmentString = client.getSavingAccount().calcInvestment();
                //retrieves value from method calcInvestment in Account class and assigns it to a string
                System.out.println(investmentString);
                //printing string from above code, ie monthly investment table 
            }
        }
    }
    
    public void printer(Client client){
        System.out.println("\nName: " + client.getName());
        //calling the method getName() from Client class
        System.out.println("\nNet Salary\n" + "Per Week: $" + Math.round((client.getNetSalary()/52)*100.0)/100.0);
        //rounding up the net salary, public variable netSalary(in Client class), to the second decimal place
        System.out.println("Per Year: $" + Math.round(client.getNetSalary()*100.0)/100.0);
        //rounding up the net salary, public variable netSalary(in Client class), to the second decimal place
        System.out.println("\nTax Paid\n" + "Per Week: $" + Math.round((client.getTax()/52)*100.0)/100.0);
        //rounding up the tax, public variable tax(in Client class), to the second decimal point
        System.out.println("Per Year: $" + Math.round(client.getTax()*100.0)/100.0);
        //rounding up the tax, public variable tax(in Client class), to the second decimal point
        System.out.println("\nResidency Status : " + client.getResidency());
        System.out.println("\nMedicare Levy Per Year: $" + client.getMedicare());
        //calling the public variable medicare from Client class
    }
    
    public static void main(String[] args){
        Scanner console = new Scanner (System.in);

        CalculatorInterface calc = new CalculatorInterface();
        char option = 'y';
        do{
            calc.run(console);
            System.out.println("*".repeat(50));
            System.out.print("Would you like to continue(y/n)? ");
            option = console.next().charAt(0);
            //assigns value at 0th index to option
            option = Character.toLowerCase(option);
            //changes option to lowercase, if it was uppercase
            console.nextLine();
            System.out.println("*".repeat(50));
        }
        while(option=='y');
        //loops through method run() while option is equal to 'y'
    }
    
}
        
    

    
    
    
    
    
    
    


        

    