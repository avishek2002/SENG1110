/* 
* Author: Avishek Sapkota Sharma
* Student No: 3393440
* Date: 26-05-2022
*/

public class Account{
    private double rate;
    private int numberOfWeeks;
    private double amount;
    private double totalAmount;
    private String investmentString;
    
    //setters
    public void setData(double investmentAmount, double interestRate, int investmentLength){
        rate = interestRate;
        numberOfWeeks = investmentLength;
        amount = investmentAmount;
        //assigning values to the null variables
    }
    public void setRate(double interestRate){
        rate = interestRate;
        //assigning rate value
    }
    public void setAmount(double investmentAmount){
        amount = investmentAmount;
        //assigning amount value
    }
    public void setInvestmenLength(int investmentLength){
        numberOfWeeks = investmentLength;
        //assigning number of weeks for investment
    }
    public void setTotalAmount(double total){
        totalAmount = total;
        //assigning totalAmount for investment
    }
    public void setInvestmentString(String string){
        investmentString = string;
        //assigning value to investmentString that stores monthly data for the investment
    }
    
    //getters
    public double getRate(){
        return rate;
        //returns rate to the method that called it
    }
    public int getNumberOfWeeks(){
        return numberOfWeeks;
        //returns number of weeks to the method that called it
    }
    public double getAmount(){
        return amount;
        //returns principle/weekly amount to the method that called it
    }
    public double getTotalAmount(){
        return totalAmount;
        //returns the total interest amount to the method that called it
    }
    public String getInvestmentString(){
        return investmentString;
        //returns the investmentString which contians monthly data for the investment
    }
    public double getInterestAmountForWeek(int weekNumber){
        totalAmount += amount*4;
        totalAmount *=  1+((rate/100)/13);
        return (totalAmount);
        //calculating the interest amount for specific week and returning the double value to the method that called it
    }
    
    //calculators
    public void calcInvestment(){
        investmentString = "Weeks\tBalance\n" + "-".repeat(15) + "\n";
        int weekNumber;
        double weekAmount = 0;
        for (weekNumber = 4; weekNumber<=numberOfWeeks; weekNumber+=4){
            //continues the loop, incrementiing the value of weekNumber by 4, while it is less/equal to time period
            investmentString += weekNumber + "\t";
            weekAmount = getInterestAmountForWeek(weekNumber);
            //retrieving weekly amount 
            investmentString += "$" + Math.round(weekAmount*100.0)/100.0 + "\n";
            //rounds up the weekAmount to second decimal
        }
        weekNumber -= 4; 
        //for the last iteration, weekNumber increases by 4 and weekNumber>investment, won't execute
        if (weekNumber<numberOfWeeks){
            //amount for last week if number of weeks is not divisible by 4
            //interest is not applied for these weeks
            investmentString += numberOfWeeks + "\t";
            weekAmount += amount*(numberOfWeeks-weekNumber);
            totalAmount = weekAmount;
            investmentString += "$" + Math.round(weekAmount*100.0)/100.0 + "\n";
        }
        investmentString += "-".repeat(15);
    }
}