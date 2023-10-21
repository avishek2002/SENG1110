/* 
* Author: Avishek Sapkota Sharma
* Student No: 3393440
* Date: 08-04-2022
*/

public class Client{
    private String name;                     
    private Account savingAccount = new Account();
    private double grossSalary;
    private double netSalary;
    private boolean resident;
    private double tax;
    private double medicare;
    private double weeklyExpenses;



    public String getName(){
        return (name);
        //returns the name of the client to the method that called it
    }
    public double getGrossSalary(){
        return grossSalary;
        //returns grossSalary of the client to the method that called it
    }
    public double getNetSalary(){
        return netSalary;
        //returns the netSalary of the client to the method that called it
    }
    public double getTax(){
        return tax;
        //returns the tax amount of the client to the method that called it
    }
    public double getMedicare(){
        return medicare;
        //returns the medicare amount of the client to the called method
    }
    public boolean getResidency(){
        return resident;
        //returns residency status of the client
    }
    public double getWeeklyExpenses(){
        return weeklyExpenses;
        //returns the client's weekly expenses
    }
    public Account getSavingAccount(){
        return savingAccount;
    }
    
    public void setData(String inputName, double annualSalary, boolean residencyStatus){
        name = inputName;
        grossSalary = annualSalary;
        resident = residencyStatus;
        
        calcTax();
        //calling calcTax method
        calcMedicare();
        //calling calcMedicare method
        netSalary = grossSalary - tax - medicare;
        //calculating netSalary, after tax and medicare
        //tax and medicare have been assigned a value from the two methods we called earlier
    }
    public void setName(String inputName){
        name = inputName;
        //setting name
    }
    public void setSalary(double annualSalary){
        grossSalary = annualSalary;
        //setting total salary
    }
    public void setResidency(boolean residencyStatus){
        resident = residencyStatus;
        //setting residency status
    }
    public void setSavingAccount(Account account){
        savingAccount = account;
        //creating a function setSavingAccount which consists Account class
    }
    
    public void changeAnnualIncome(double annualSalary){
        grossSalary = annualSalary;
        //changing the value of grossSalary
        calcTax();
        calcMedicare();
        netSalary = grossSalary - tax - medicare;
        //calculating netSalary after grossSalary was changed
    }
    
  
    private void calcTax(){
        if (resident==true){
            //performs the following operations if the client is a resident
            if (grossSalary>0 && grossSalary<=18200){
                tax = 0;
            }
            else if (grossSalary>18200 && grossSalary<=45000){
                tax = (grossSalary-18200)*0.19;
            }
            else if (grossSalary>45000 && grossSalary<=120000){
                tax = 5092 + (grossSalary-45000)*0.325;
            }
            else if (grossSalary>120000 && grossSalary<=180000){
                tax = 29467 + (grossSalary-120000)*0.37;
            }
            else if (grossSalary>180000){
                tax = 52667 + (grossSalary-180000)*0.45;
            }
        }
        else if (resident==false){
            //performs the following operations if the client is not a resident
            if (grossSalary>0 && grossSalary<=120000){
                tax = grossSalary*0.325;
            }
            else if (grossSalary>120000 && grossSalary<=180000){
                tax = 39000 + (grossSalary-120000)*0.37;
            }
            else if (grossSalary>180000){
                tax = 61200 + (grossSalary-180000)*0.45;
            }
        }
    }
    private void calcMedicare(){
        if (resident==true && grossSalary>29032){
            medicare = grossSalary*0.02;
            //calculating medicare for residents
        }
        else {
            //medicare is not applicable to non-residents
            medicare = 0;
        }
    }
    
}
