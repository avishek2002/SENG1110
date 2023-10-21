/* 
* Author: Avishek Sapkota Sharma
* Student No: 3393440
* Date: 26-05-2022
*/

import java.util.*;
import java.io.*;

public class CalculatorInterface{
    
    //adding methods
    public static void addClient(Scanner console, Client[] client, Account[] account){
        if (client[0] == null){
            client[0] = new Client();
        }
        //creating new client object so that if the client array is empty, the system doesn't crash
        int workingClientNumber = client[0].getNoClient();
        //using the getNoClient method from client to retrieve the client number we will be working with
        client[workingClientNumber] = new Client();
        //creating a new client object for the client number
                
        String inputName;
        double annualIncome=0;
        boolean residencyStatus;
        String auxResidencyStatus;
        double expenditure;
        
        int iteration = 0;
        do {
            if (iteration!=0){
                System.out.println("Client with name already exists! Please try again.");
            }
            inputName = createName(console);
            iteration++;
        }
        while(checkNameInArray(client, inputName));
        //check if client with name already exist using the checkNameInArray method
        //variable iteration is used so that we don't print the statement inside the if statement for the first iteration
        
        annualIncome = createIncome(console, annualIncome);
        //calling the method createIncome, and assining the retrieved value to annualIncome
        iteration = 0;
        do{
            if (iteration!=0){
                System.out.println("Please enter valid option! Must be either true or false!");
            }
            iteration++;
            System.out.print("Residency(true/false) : ");
            auxResidencyStatus = console.nextLine();
            auxResidencyStatus = auxResidencyStatus.toLowerCase();
        }
        while(!(auxResidencyStatus.equals("true")) && !(auxResidencyStatus.equals("false")));
        //runs the do..while loop until auxResidencyStatus is not equal to "true" or "false"
        //auxResidencyStatus is used so that the user input can be checked and looped if it is not boolean
        residencyStatus = Boolean.parseBoolean(auxResidencyStatus);
        //Boolean.parseBoolean(str) is used to change the type of str to boolean
        
        client[workingClientNumber].setData(inputName,annualIncome,residencyStatus);
        //passing the inputs to setData method which stores them for the specific client
        printerClient(client[workingClientNumber]);
        //calls the printer method inside this class, with parameter working client of the client array
        
        int iterationOutside = 0;
        String expenditureString = "";
        expenditure = 0;
        do {
            if (iterationOutside == 0){
                //runs this code only if statement if the weekly expenditure is being input for the first time
                System.out.print("\nWeekly expenditure : ");
                expenditureString = console.nextLine();
                if (isStringDouble(expenditureString)){
                    expenditure = Double.parseDouble(expenditureString);
                    //isStringDouble method checks if the string has double value, if true parses it to double and stores in expenditure
                }
                else {
                    expenditure = 0;
                    //if expenditure is not a double, it initializes expenditure as 0, hence continues the do..while when the while statment checks the value
                }
            }
            else if (iterationOutside>0){
                //runs this code if input expenditure doesn't work in the first iteration
                System.out.println("Your expenditure cannot be negative, equal to zero or less than your weekly net salary!");
                iteration = 0;
                String optionString;
                do {
                    if (iteration!=0){
                        System.out.println("Option is invalid!");
                    }
                    System.out.print("Do you want to re-enter your expenditure(1) or your annual income(2) or exit(3) : ");
                    optionString = console.nextLine();
                    if (!(isStringInt(optionString))){
                        optionString ="0";
                    }
                    iteration++;
                }
                while(!(Integer.parseInt(optionString)>0 && Integer.parseInt(optionString)<=3));
                //do..while continues until valid input has been chosen from the given options
                //isStringInt method checks if the input is an integer, if not sets optionString to "0" and hence do..while continues
                int option = Integer.parseInt(optionString);
                if (option==1){
                    System.out.print("Re-enter your expenditure : ");
                    expenditureString = console.nextLine();
                    if (isStringDouble(expenditureString)){
                        expenditure = Double.parseDouble(expenditureString);
                    }
                    else {
                        expenditure = 0;
                    }  
                }
                //when the user chooses to re-enter expenditure, this checks if the value is valid, if not initializes expenditure as 0
                else if (option==2){
                    annualIncome = createIncome(console, annualIncome);
                    client[workingClientNumber].changeAnnualIncome(annualIncome);
                    //changes the annual income for client object, and has methods to change other values so that they are updated as well
                    printerClient(client[workingClientNumber]);
                    //using the printer method again, since client's annual income was changed
                    System.out.print("\nPlease enter Weekly expenditure one more time: ");
                    expenditureString = console.nextLine();
                    if (isStringDouble(expenditureString)){
                        expenditure = Double.parseDouble(expenditureString);
                    }
                    else {
                        expenditure = 0;
                    }
                }
                //when the user chooses to re-enter annualIncome, other data is updated too, and asks user to re-enter expenditure
                else if (option==3){
                    System.exit(0);
                    //terminates the program
                }            
            }
            iterationOutside++;
        }
        while(expenditure<1 || expenditure>(client[workingClientNumber].getNetSalary()/52));
        //iterates over the do..while until the expenditure is less than the income and more than 0

        client[workingClientNumber].setWeeklyExpenses(expenditure);
        //using the setWeeklyExpenses method to initialize the value of expenditure for the specific client in the array
        
        if ((client[workingClientNumber].getNetSalary()/52)-expenditure>0){
            System.out.print("\nWould you like to invest some money(y/n)? : ");
            char option = console.nextLine().charAt(0);
            //assigns the variable with charater at 0th index
            option = Character.toLowerCase(option);
            //changing option to lowercase alphabets
            if (option=='y'){
                //enters the if loop if option is equal to 'y'
                account[0] = new Account();
                //creating an empty account object in the first position of the account array, because the client was just created
                client[workingClientNumber].setSavingAccount(account, 0);
                //using the setSavingAccount function to set data to the accout object at first index of the account array
                addAccount(console, client[workingClientNumber], account);
                //addAccount method now initializes values for the account at index=0
                printerAccount(client[workingClientNumber],account);
                //printerAccount method retrieves the data from the account object and displays it
            }
        }
    }
    public static void addAccount(Scanner console, Client client, Account[] account){
        //in this method client refers to the specific client and not the array, as the passed parameter was sent accordingly
        double investmentValue = 0;
        String investmentValueString;
        double expenditure = client.getWeeklyExpenses();
        //retrieving the weekly expenses of specific client
        int iteration = 0;
        do{
            if (iteration==0){
                System.out.print("Investment amount : ");
                investmentValueString = console.nextLine();
            }
            else {
                System.out.println("Investment amount not feasible!");
                System.out.println("Remaining Investable Amount: $" + checkRemainingAmountInvestable(client));
                System.out.print("Re-enter investment amount : ");
                investmentValueString = console.nextLine();
            }
            if (!isStringDouble(investmentValueString)){
                investmentValue = 0;
            }
            else {
                investmentValue = Double.parseDouble(investmentValueString);
            }
            iteration++;
        }
        while (investmentValue<=0 || (client.getNetSalary()/52)-expenditure-investmentValue<client.getSavingAccount(0).getAmount());
        //runs the while loop until the input is a positive integer less/equal to weekly salary minus expenditure minus weekly investment amount in account 1 if it exists
        //input is retrieved as String, and checked if it has type double, else value is set such that the do..while continues
        double interestRate = 0;
        String interestRateString;
        iteration = 0;
        do{
            if (iteration==0){
                System.out.print("Interest rate : ");
                interestRateString = console.nextLine();
            }
            else {
                System.out.println("Interest rate must be within range 1% and 20%!");
                System.out.print("Re-enter interest rate : ");
                interestRateString = console.nextLine();
            }
            if (!isStringDouble(interestRateString)){
                interestRate = 0;
            }
            else {
                interestRate = Double.parseDouble(interestRateString);
            }
            iteration++;
        }
        while (interestRate>20 || interestRate<1);
        //runs the while loop until the rate is within range 1 and 20
        //input is retrieved as String and checked if it is type double, else value is set such that the do..while continues
        int investmentLength = 0;
        String investmentLengthString;
        iteration = 0;
        do {
            if (iteration==0){
                System.out.print("Investment length(# of weeks) : ");
                investmentLengthString = console.nextLine();
            }
            else {
                System.out.println("Investment length cannot be less than or equal to zero!");
                System.out.print("Re-enter investment length(weeks) : ");
                investmentLengthString = console.nextLine();
            }
            if (!isStringInt(investmentLengthString)){
                investmentLength = 0;
            }
            else {
                investmentLength = Integer.parseInt(investmentLengthString);
            }
            iteration++;
        }
        while (investmentLength<=0);
        //runs the loop while time period is less/equal to 0
        //input is retrieved as String and checked if it is type double, else value is set such that the do..while continues
        if (client.getSavingAccount(0) == null || client.getSavingAccount(0).getAmount() == 0){
            account[0] = new Account();
            client.setSavingAccount(account, 0);
            client.getSavingAccount(0).setData(investmentValue, interestRate, investmentLength);
            client.getSavingAccount(0).calcInvestment();
        }
        //checking if the first account for the client is null or not, if null input is stored in the first and investment value is calculated and stored in first account object
        else if (client.getSavingAccount(0) != null){
            account[1] = new Account();
            client.setSavingAccount(account, 1);
            client.getSavingAccount(1).setData(investmentValue, interestRate, investmentLength);
            client.getSavingAccount(1).calcInvestment();
        }
        //if first account object was already created, this creates the second/final account object
    }
    
    //printer methods
    public static void printerClient(Client client){
        System.out.println("\nName: " + client.getName());
        System.out.println("\nResidency Status: " + client.getResidency());
        System.out.println("\nGrossSalary\n" + "Per Week: $" + Math.round((client.getGrossSalary()/52)*100.0)/100.0);
        System.out.println("Per Year: $" + Math.round(client.getGrossSalary()*100.0)/100.0);
        System.out.println("\nNet Salary\n" + "Per Week: $" + Math.round((client.getNetSalary()/52)*100.0)/100.0);
        System.out.println("Per Year: $" + Math.round(client.getNetSalary()*100.0)/100.0);
        System.out.println("\nTax Paid\n" + "Per Week: $" + Math.round((client.getTax()/52)*100.0)/100.0);
        System.out.println("Per Year: $" + Math.round(client.getTax()*100.0)/100.0);
        System.out.println("\nResidency Status : " + client.getResidency());
        if (client.getResidency()){
            System.out.println("\nMedicare Levy\n" + "Per Week: $" + Math.round((client.getMedicare()/52)*100.0)/100.0);
            System.out.println("Per Year: $" + Math.round(client.getMedicare()*100.0)/100.0);
        }
        else {
            System.out.println("\nMedicare Levy : Not Applicable");
        }
        //using the methods in client class to retrieve specific data for the client
    }
    public static void printerAccount(Client client,Account[] account){
        for (int i=0; i<=1; i++){
            //iterating over the two possible account objects
            if (client.getSavingAccount(i) != null){
                //checks if the saving account is not null
                String investmentString = client.getSavingAccount(i).getInvestmentString();
                //retrieves value from method getInvestmentString in Account class and assigns it to a string
                System.out.println("\nAccount: " + (i+1));
                System.out.println("Amount invested per week: $" + client.getSavingAccount(i).getAmount());
                System.out.println("Interest rate: " + client.getSavingAccount(i).getRate() + "%");
                System.out.println("Number of weeks: " + client.getSavingAccount(i).getNumberOfWeeks());
                System.out.println(investmentString);
                System.out.println("Total amount in the end of the period: $" + Math.round(client.getSavingAccount(i).getTotalAmount()*100.0)/100.0);
                //printing string from above code, ie monthly investment table 
            }
        }
    }
    
    //creating methods
    public static String createName(Scanner console){
        String inputName, inputFirstName, inputLastName;
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
        return inputName;
        //this method creates the full name such that it is a valid name(only containing alphabets), and returns the full name
    }
    public static double createIncome(Scanner console, double annualIncome){
        int iteration = 0;
        String annualIncomeString = "0";
        do{
            if (iteration!=0){
                System.out.println("Please enter valid income! Must only contain integers and be greater than 0!");
            }
            iteration++;
            System.out.print("Annual income : ");
            annualIncomeString = console.nextLine();
            if (!(isStringDouble(annualIncomeString))){
                    annualIncomeString ="0";
            }
        }
        while(Double.parseDouble(annualIncomeString)<=0);
        annualIncome = Double.parseDouble(annualIncomeString);
        return annualIncome;
        //this methods checks if the input income is valid(greater than 0 and only double values) and returns the annualIncome
    }
    
    //deleting methods
    public static void deleteClient(Scanner console, Client[] client){        
        String inputName = createName(console);
        //getting the name of the client object to be deleted
        for (int i=0; i<client.length; i++){
            //iterating throught the client objects in client array
            if (client[i] != null && client[i].getName().equals(inputName)){
                client[i].decreaseNoClient();
                //decreaseNoClient method decreases the value of noClient in client class by 1, since 1 client object was removed
                System.arraycopy(client, i + 1, client, i, client.length - i - 1);
                //thic method, takes the object from i+1 index position of client array and copies it to the i index position of the same client array
                //the last arguments sets the number of objects to be copied
                client[client.length - 1] = null;
                System.out.println("Client successfully deleted!");
                break;
            }
            //if client object is not null and name matches with the input, client object is deleted, and exits out of the for loop
            if (i == client.length - 1){
                System.out.println("Client with name " + inputName + " does not exist!");
            }
            //if iteration number reaches the final object and name still doesn't match, it enters into this if statement
        }
    }
    public static void deleteAccount(Client client,Account[] account, int deleteAccountNumber){
        if (deleteAccountNumber==0 && client.getSavingAccount(1)==null){
            account[deleteAccountNumber] = null;
            client.setSavingAccount(account,deleteAccountNumber);
        }
        else if (deleteAccountNumber==0 && client.getSavingAccount(1)!=null){
            account[deleteAccountNumber] = client.getSavingAccount(1);
            account[1] = null;
            client.setSavingAccount(account,deleteAccountNumber);
            client.setSavingAccount(account, 1);  
        }
        else if(deleteAccountNumber==1){
            account[deleteAccountNumber] = null;
            client.setSavingAccount(account,deleteAccountNumber);
        }
        //deletes the account at the input index and stores a null value there
        //if the first account was deleted, moves the second account object(if it is not null) to the first place
        //setSavingAccount method sets the changes made to the object in specific position
    }
    
    //checking methods
    public static boolean checkClientArray(Client[] client){
        boolean empty = true;
        for (int i=0; i<client.length; i++){
            if (client[i] != null){
            empty = false;
            break;
            }
        }
        return empty;
        //checks if the client array is empty, returns the corresponding boolean value 
    }
    public static boolean checkNameInArray(Client[] client, String inputName){
        boolean nameTaken = false;
        for (int i=0; i<client[0].getNoClient(); i++){
            if (client[i]!= null && client[i].getName().equals(inputName)){
                nameTaken = true;
                break;        
            }
        }
        return nameTaken;
        //checks if the input name is in the client array, returns correspondingn boolean value
    }
    public static double checkRemainingAmountInvestable(Client client){
        double remainingAmountInvestable = client.getNetSalary()/52*100.0/100.0 - client.getWeeklyExpenses();
        for (int i=0; i<=1; i++){
            if (client.getSavingAccount(i) != null){
                remainingAmountInvestable -= client.getSavingAccount(i).getAmount();
            }
            
        }
        return (Math.round(remainingAmountInvestable*100.0)/100.0);
        //iterates over the existing non-null account objects, calculates the remaining investable amount and returns it as double
    }
    public static boolean isStringInt(String s){
        try{
            Integer.parseInt(s);
            return true;
        } 
        catch (NumberFormatException ex){
            return false;
        }
        //checks if String can be parsed into Integer
        //catch statement returns boolean value false if an error occurs in the try statement
    }
    public static boolean isStringDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }
        catch (NumberFormatException ex){
            return false;
        }
        //checks if String can be parsed into Double
        //catch statement returns boolean value false if an error occurs in the try statement
    }
    public static int[] numberOfAccounts(Client[] client, Account[] account, String inputName){
        int clientNum = 0;
        for (int i=0; i<client[0].getNoClient(); i++){
            if (client[i]!= null && client[i].getName().equals(inputName)){
                clientNum = i;
                break;        
            }
        }
        int numberOfAccounts = 0;
        for (int i=0; i<=1; i++){
            if (client[clientNum].getSavingAccount(i) != null){
                numberOfAccounts++;
            }
        }
        return new int[] {clientNum , numberOfAccounts};
        //the first for loop iterates over the client class to search for the inputName matching name of client and provides index value of the corresponding client object
        //the second iterates over the account class and returns the number of not-null account objects for the corresponding client
        //this methods returns an array of type int, storing index value of the client object and the number of accounts the client has
    }
    
    //file writer
    public static void fileWriter(Client[] client)throws IOException{
        String fileName = "clientsData.txt";
        PrintWriter outFile = new PrintWriter(fileName);
        if (client[0] == null){
            outFile.println("No Clients.");
        }
        else {
            for (int i=0; i<client.length; i++){
                if (client[i] != null){
                    outFile.println("Client: " + (i+1));
                    outFile.println("Name: " + client[i].getName());
                    outFile.println("Gross Salary");
                    outFile.println("\tPer Week: $" + Math.round((client[i].getGrossSalary()/52)*100.0)/100.0);
                    outFile.println("\tPer Year: $" + Math.round(client[i].getGrossSalary()*100.0)/100.0);
                    outFile.println("Residency Status: " + client[i].getResidency());
                    outFile.println("Tax");
                    outFile.println("\tPer Week: $" + Math.round((client[i].getTax()/52)*100.0)/100.0);
                    outFile.println("\tPer year: $" + Math.round(client[i].getTax()*100.0)/100.0);
                    outFile.println("Net Salary");
                    outFile.println("\tPer Week: $" + Math.round((client[i].getNetSalary()/52)*100.0)/100.0);
                    outFile.println("\tPer year: $" + Math.round(client[i].getNetSalary()*100.0)/100.0);
                    if (client[i].getResidency()){
                        outFile.println("Medicare Levy Per year: $" + Math.round(client[i].getMedicare()*100.0)/100.0);
                    }
                    else {
                        outFile.println("Medicare Levy: Not Applicable");
                    }
                    outFile.println("Expenses Per Week: $" + Math.round(client[i].getWeeklyExpenses()*100.0)/100.0);
                    outFile.println("Remaining Amount Investable: $" + Math.round(checkRemainingAmountInvestable(client[i])*100.0)/100.0);
                    if (client[i].getSavingAccount(0) == null){
                        outFile.println("Account: None");
                    }
                    else {
                        for (int j=0; j<=1; j++){
                            if (client[i].getSavingAccount(j) != null){
                                outFile.println("Account: " + (j+1));
                                outFile.println("Amount invested per week: $" + client[i].getSavingAccount(j).getAmount());
                                outFile.println("Interest rate: " + client[i].getSavingAccount(j).getRate() + "%");
                                outFile.println("Number of weeks: " + client[i].getSavingAccount(j).getNumberOfWeeks());
                                outFile.println(client[i].getSavingAccount(j).getInvestmentString());
                                outFile.println("Total amount in the end of the period: $" + Math.round(client[i].getSavingAccount(j).getTotalAmount()*100.0)/100.0);
                            }
                        }
                    }
                }
                outFile.println();
            }
        }
        System.out.println("Successfully stored at " + fileName + "!");
        outFile.close();
        //iterating over all the not-null client objects and writing their stored data into the file "clientData.txt"
        //writes "No Clients" to the txt file if all client objects are null type
        //writes "Account : None" if the specific client has no saving account(s)
    }
    
    
    public static void main(String[] args)throws IOException{
        CalculatorInterface calc = new CalculatorInterface();
        
        Scanner console = new Scanner (System.in);
        Client[] client = new Client[5];
        Account[] account = new Account[2];
     
        String optionString;
        int option;
        do{
            System.out.println("*".repeat(50));
            System.out.println("1. Add a client ");
            System.out.println("2. Delete a client");
            System.out.println("3. Display a client");
            System.out.println("4. Display all clients");
            System.out.println("5. Add an account");
            System.out.println("6. Display account");
            System.out.println("7. Delete an account");
            System.out.println("8. Save in a file");
            System.out.println("9. Exit");
            int iteration = 0;
            do {
                if (iteration==0){
                    System.out.print("Enter your selection : ");
                }
                else {
                    System.out.print("Invalid! Enter your option (1-9) : ");
                }
                optionString = console.nextLine();
                if (!(isStringInt(optionString))){
                    optionString ="0";
                }
                iteration++;
            }
            while(!(Integer.parseInt(optionString)>0 && Integer.parseInt(optionString)<=9));
            option = Integer.parseInt(optionString);
            //checking if the option selected is invalid, and continuing the do..while until input is valid
            
            System.out.println("*".repeat(50));
            
            if (option==1){
                System.out.println("You have selected to add client.");
                if (client[4]==null){
                    addClient(console,client,account);
                    //since we have client class sorted according to null values at the last index, checking if last object is not null
                    //if last object is null, means we can add more clients
                }
                else {
                    System.out.println("Not possible to add clients! Already 5 clients exist!");
                }
            }
            else if (option==2){
                System.out.println("You have selected to delete client.");
                if (checkClientArray(client)){
                    System.out.println("No Clients!");
                    //diplays this message if there are no clients in the client array
                }
                else {
                    deleteClient(console, client);
                    //deleteClient methods inputs name and if client with name exists, deletes the client object
                }
            }
            else if (option==3){
                System.out.println("You have selected to display a client.");
                if (checkClientArray(client)){
                    System.out.println("No Clients!");
                    //diplays this message if there are no clients in the client array
                }
                else {
                    String inputName = createName(console);
                    for (int i=0; i<client.length; i++){
                        if (client[i] != null && client[i].getName().equals(inputName)){
                            printerClient(client[i]);
                            System.out.println("\nExpenses Per Week: $" + client[i].getWeeklyExpenses());
                            System.out.println("\nRemaining Investable Amount: $" + checkRemainingAmountInvestable(client[i]));
                            if (client[i].getSavingAccount(0)==null){
                                System.out.println("\nAccount : None");
                            }
                            else {
                                printerAccount(client[i], account);
                            }
                            break;
                        }
                        else if (i == client.length - 1){
                            System.out.println("Client with name " + inputName + " does not exist!");
                        }
                    }
                    //loops through the for statement until client name matches input name, and then displays the clients data
                    //if none of the client names match the input name, message indicating such is displayed
                }
            }
            else if (option==4){
                System.out.println("You have selected to display all client(s).");
                if (checkClientArray(client)){
                    System.out.println("No Clients!");
                    //diplays this message if there are no clients in the client array
                }
                else {
                    for (int i=0; i<client.length; i++){
                        if (client[i] != null){
                            System.out.println("-".repeat(25) + "\nClient " + (i+1) + " :");
                            printerClient(client[i]);
                            System.out.println("\nExpenses Per Week: $" + client[i].getWeeklyExpenses());
                            int numberOfAccounts = numberOfAccounts(client, account,client[i].getName())[1];
                            if (numberOfAccounts == 0){
                                System.out.println("\nAccount : None");
                            }
                            else{
                                System.out.println("\nNumber of Account(s): " + numberOfAccounts);
                            }
                        }
                    }
                    //loops through the for statement until all client objects which are not null are displayed
                }
            }
            else if (option==5){
                System.out.println("You have selected to add account.");
                if (checkClientArray(client)){
                    System.out.println("No Clients!");
                    //diplays this message if there are no clients in the client array                    
                }
                else {
                    String inputName = createName(console);
                    if (checkNameInArray(client, inputName)){
                        int[] aux = numberOfAccounts(client, account, inputName);
                        int clientNum = aux[0];
                        int numberOfAccounts = aux[1];
                        if (numberOfAccounts<2){
                            addAccount(console, client[clientNum], account);
                            printerAccount(client[clientNum], account);
                        }
                        //adds a new account object for the client with corresponding name if client doesn't already have 2 account
                        else if (numberOfAccounts==2){
                            System.out.println("Client with name " + inputName + " already has two accounts!");
                        }
                        //displays this message if corresponding client already has 2 accounts
                    }
                    else {
                        System.out.println("Client with name " + inputName + " does not exist!");
                    }
                    //displays this message if input name doesn't match any of the client names
                }
            }
            else if (option==6){
                System.out.println("You have selected to display account.");
                if (checkClientArray(client)){
                    System.out.println("No Clients!");
                    //diplays this message if there are no clients in the client array
                }
                else {
                    String inputName = createName(console);
                    if (checkNameInArray(client, inputName)){
                        int clientNum = 0;
                        for (int i=0; i<client.length; i++){
                            if (client[i].getName().equals(inputName)){
                                clientNum = i;
                                break;
                            }
                        }
                        //if input name is present in client array, finds the positional value for the client object
                        int choiceAccountNumber;
                        String choiceAccountString = "";
                        iteration = 0;
                        do{
                            if (iteration==0){
                                System.out.print("Account number 1 or 2 for " + inputName + ": ");
                            }
                            else {
                                System.out.println("Invalid entry! A client can have either 1 or 2 accounts!");
                                System.out.print("Re-enter account number :");
                            }
                            choiceAccountString = console.nextLine();
                            if (!(isStringInt(choiceAccountString))){
                                choiceAccountString = "0";
                            }
                            iteration++;
                        }
                        while(!(Integer.parseInt(choiceAccountString)>0 && Integer.parseInt(choiceAccountString)<3));
                        //checks if the input String has Integer value of 1 or 2, if not loops until it does
                        choiceAccountNumber = Integer.parseInt(choiceAccountString) - 1;
                        //decreasing 1 from the choosen value, since indexing starts from 0 not from 1
                        if (client[clientNum].getSavingAccount(choiceAccountNumber) != null){
                            String investmentString = client[clientNum].getSavingAccount(choiceAccountNumber).getInvestmentString();
                            System.out.println("\nClient: " + (clientNum+1) + "\nAccount: " + (choiceAccountNumber+1));
                            System.out.println(investmentString);
                        }
                        //diplays the account information if the account object at index is not null
                        else {
                            System.out.println("\nClient: " + inputName + " does not have Account " + (choiceAccountNumber+1));
                        }
                        //displays message if there is not account at index
                    }
                    else {
                        System.out.println("Client with name " + inputName + " does not exist!");
                    }
                    //displays message if client with input name doesn't exist
                }
            }
            else if(option==7){
                System.out.println("You have selected to delete account.");
                if (checkClientArray(client)){
                    System.out.println("No Clients!");
                    //diplays this message if there are no clients in the client array
                }
                else {
                    String inputName = createName(console);
                    if (checkNameInArray(client, inputName)){
                        int clientNum = 0;
                        for (int i=0; i<client.length; i++){
                            if (client[i].getName().equals(inputName)){
                                clientNum = i;
                                break;
                            }
                        }
                        //retrives the index value of the client with corresponding name
                        int deleteAccountNumber;
                        String deleteAccountString = "";
                        iteration = 0;
                        do{
                            if (iteration==0){
                                System.out.print("Account number 1 or 2 to delete for " + inputName + ": ");
                            }
                            else {
                                System.out.println("Invalid entry! A client can have either 1 or 2 accounts!");
                                System.out.print("Re-enter account number :");
                            }
                            deleteAccountString = console.nextLine();
                            if (!(isStringInt(deleteAccountString))){
                                deleteAccountString = "0";
                            }
                            iteration++;
                        }
                        while(!(Integer.parseInt(deleteAccountString)>0 && Integer.parseInt(deleteAccountString)<3));
                        //checks if the input String has Integer value of 1 or 2, if not loops until it does
                        deleteAccountNumber = Integer.parseInt(deleteAccountString) - 1;
                        //decreasing 1 from the choosen value, since indexing starts from 0 not from 1
                        if (client[clientNum].getSavingAccount(deleteAccountNumber) != null){
                            deleteAccount(client[clientNum], account, deleteAccountNumber);
                            System.out.println("\nSuccessfully deleted Account " + (deleteAccountNumber+1) + " for " + inputName + "!");
                        }
                        //deletes the client at given index and moves the account at second index(if it exists) to the first place
                        else {
                            System.out.println("\nClient: " + inputName + " does not have Account " + (deleteAccountNumber+1));
                        }
                        //displays message if client doesn't have account at that index
                    }
                    else {
                        System.out.println("Client with name " + inputName + " does not exist!");
                    }
                    //displays message if client with matching name doesn't exist
                }
            }
            if (option==8){
                System.out.println("You have selected to save data into a file.");
                fileWriter(client);
                //
            }
        }
        while(option!=9);
        System.out.println("Succesfully ended program!");
        //loops through do..while until option is not equal to 9
    }
}