/**
 * 
 * @author William Kealey
 * @version 2.0.b
 *
 */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;

public class PayCalc2 {
	private static Scanner getter = new Scanner(System.in);
	private static boolean running = true;
	private static Double baseRate;
	private static Double fedTax;
	private static Double stateTax;
	private static Double overTime;
	private static Double socialSec;
	private static Double medicare;
	private static String user = ""; 
    private static Double debt;
    private static Double setAside;
	private static DecimalFormat d = new DecimalFormat("#,###.00");
        

	
	
	//Sets the user of the program.
	private static void setUser(String u) {
		user = u;
	}
	
	/**
	 * Gets the saved values from a file.
	 * @param u is a username to look for
	 */
	private static void loadValues(String u) {
		File f = new File(u+".txt");
		try {
			Scanner reader = new Scanner(f);
			Double[] vals = new Double[7];
			int i = 0;
			while (reader.hasNextDouble()) {
				vals[i] = reader.nextDouble();
				i++;
			}
			reader.close();
			baseRate = vals[0];
			overTime = baseRate*1.5;
			debt = vals[1];
			setAside = vals[2];
			fedTax = vals[3];
			stateTax = vals[4];
			socialSec = vals[5];
			medicare = vals[6];
				
		} catch (FileNotFoundException fNFE) {
			System.out.println("File Not Found Exception occured in \"loadValues\". Getting user again.");
			getUser();
		} 
		
	}
	
	private static double getTotDeductions() {
		return (fedTax+stateTax+socialSec+medicare)/100;
	}
	
	//Prompts user to provide name then sets the user value to that name.
 	private static void getUser() {
		System.out.print("Who is using the program? (First Name): ");
		String a = getter.next();
		System.out.println();
		File f = new File(a+".txt");
		if(f.canRead()) {
			setUser(a);
			try {
				loadValues(a);
			} catch (NullPointerException n) {
				System.out.println("Values cannot be read.");
				f.delete();
				newUser(a);
				setUser(a);
				loadValues(a);
			}
		} else {
			System.out.println("Profile not found, creating new profile.\n");
			newUser(a);
			setUser(a);
			loadValues(a);
		}
		return;
	}
	
	/**
	 * Creates a new user
	 * @param n is the name of the new user.
	 * @return true if successful, false otherwise.
	 */
	private static void newUser(String n){
		try {
			File f = new File(n+".txt");
			f.createNewFile();
			FileWriter out = new FileWriter(f);
			System.out.print("User "+n+" created!\n\nPlease input a base hourly rate for "+n+" (Decimal Number): ");
			Double i = getter.nextDouble();
			baseRate = i;
			out.write(""+baseRate);
			System.out.print(n+" has a value of $"+d.format(baseRate)+" for basic hourly payrate.\n");
			out.write(System.lineSeparator());
			System.out.print("How much debt does "+n+" have? (Decimal Number): ");
			debt = getter.nextDouble();
			System.out.println(n+" is about $"+d.format(debt)+" in debt.");
			out.write(""+debt);
			out.write(System.lineSeparator());
			System.out.print("How much by default does "+n+" set aside to live each week? (Decimal Number): ");
			setAside = getter.nextDouble();
			out.write(""+setAside);
			out.write(System.lineSeparator());
			System.out.println(n+" sets aside around $"+d.format(setAside)+" to live on.");
			System.out.print("You may either enter any input to use the default rate enter \"1\" to set your \nown federal withholding: ");
		    String in1 = getter.next();
		    if (in1.equalsIgnoreCase("1")) {
		    	System.out.print("You have opted to use a custom federal withholding, please input it here (Decimal Number): ");
		    	Double i2 = getter.nextDouble();
		    	fedTax = i2;
		    	out.write(""+fedTax);
		    	System.out.println(n+" has a value of "+d.format(fedTax)+" for Federal Tax withholding.");
		    	out.write(System.lineSeparator());
		    } else {
		    	fedTax = 9.9;
		    	out.write(""+fedTax);
		    	System.out.println(n+" has a value of "+d.format(fedTax)+" for Federal Tax withholding.");
		    	out.write(System.lineSeparator());
		    }
		    System.out.print("You may either any input to use the default rate or enter \"1\" to set \nyour own state withholding: ");
		    String in2 = getter.next();
		    if (in2.equalsIgnoreCase("1")) {
		    	System.out.print("You have opted to use a custom state withholding, please input it here (Decimal Number): ");
		    	Double i2 = getter.nextDouble();
		    	stateTax = i2;
		    	out.write(""+stateTax);
		    	System.out.println(n+" has a value of "+d.format(stateTax)+" for State Tax withholding.");
		    	out.write(System.lineSeparator());
		    } else {
		    	stateTax = 7.2;
		    	out.write(""+stateTax);
		    	System.out.println(" has a value of "+d.format(stateTax)+" for State Tax withholding.");
		    	out.write(System.lineSeparator());
		    }
		    System.out.print("You may either press any key to use the default rate or press \"1\" to set \nyour own social security value: ");
		    String in3 = getter.next();
		    if (in3.equalsIgnoreCase("1")) {
		    	System.out.print("You have opted to use a custom social security withholding, please input it here (Decimal Number): ");
		    	Double i3 = getter.nextDouble();
		    	socialSec = i3;
		    	out.write(""+socialSec);
		    	System.out.println(n+" has a value of "+d.format(socialSec)+" for Social Security withholding.");
		    	out.write(System.lineSeparator());
		    } else {
		    	socialSec = 6.2;
		    	out.write(""+socialSec);
		    	System.out.println(n+" has a value of "+d.format(socialSec)+" for Social Security withholding.");
		    	out.write(System.lineSeparator());
		    }
		    System.out.print("You can use either press any key to use the default rate or press \"1\" to set \nyour own medicare witholding value: ");
		    String in4 = getter.next();
		    if (in4.equalsIgnoreCase("1")) {
		    	System.out.println("You have opted to use a custom medicare withholding, please input it here (Decimal Number): ");
		    	Double i4 = getter.nextDouble();
		    	medicare = i4;
		    	out.write(""+medicare);
		    	System.out.println(n+" has a value of "+d.format(medicare)+" for Medicare withholding.");
		    	out.write(System.lineSeparator());
		    } else {
		    	medicare = 1.45;
		    	out.write(""+medicare);
		    	System.out.println(n+" has a value of "+d.format(medicare)+" for Medicare withholding.");
		    	out.write(System.lineSeparator());
		    }
		    System.out.println();
			out.close();
			System.out.println("\nA file to store data for "+n+" has been created at "+f.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("\n\nSorry, but the system could not create new user "+n+".");
		} catch (InputMismatchException m) {
			System.out.println("\n\nUnexpected value type, please try again.\n\n");
			newUser(n);
		}
	
	
	}
    
	/**
	 * Checks if a user exists and removes them.
	 * @param n is a name for a user to delete information for.
	 * @return true if the user is deleted false if no such user was found.
	 */
	private static boolean removeUser(String n) {
		File f = new File(n+".txt");
		if (f.exists()){
			return(f.delete());
		} else {
			return false;
		}
	}
	
	/**
	 * Does Calculations for a single week.
	 */
	private static void singleWeekCalc() {
		System.out.print("How many hours will you have worked over the target week? (Decimal number): ");
		Double totHours = getter.nextDouble();
		Double grossPayVal = 0.0;
		Double bonus = 0.0;
		Double deducts = getTotDeductions();
		Double grossTotPay;
		String b = "";
		if(totHours>40) {
			Double oTHours = totHours - 40;
			Double oTVal = oTHours*overTime;
			Double regVal = baseRate*40;
			grossPayVal += regVal+oTVal;
		} else {
			grossPayVal += baseRate*totHours;
		}
		System.out.print("Did you get any bonuses or tips? (Y/N): ");
		String i = getter.next();
		System.out.println();
		if (i.equalsIgnoreCase("y")) {
			System.out.print("How much did you/will you get in bonus money? (Decimal number): ");
			bonus += getter.nextDouble();
			b += "$"+d.format(bonus);
			System.out.println();
		}
		if (i.equalsIgnoreCase("n")) {
			b = "no money";
		}
		grossTotPay = grossPayVal+bonus;
		System.out.println(user+" makes $"+d.format(grossTotPay)+" with the given values.");
		Double des = grossTotPay*deducts;
		System.out.println("This means that $"+d.format(des)+" is lost to deductions.");
		Double takeHome = grossTotPay - des;
		System.out.println("The end result is that working "+totHours+ " hours with "+b+" in bonuses nets a take home of $"+d.format(takeHome));
		System.out.println();
		calcTimeLeft1(takeHome);
		System.out.println();
	}
	
	/**
	 * Performs calculations for a two week period.
	 */
	private static void biWeeklyCalc() {
		Double week1Hours;
		Double week2Hours;
		Double ot1 = 0.0;
		Double ot2 = 0.0;
		Double oTHours;
		Double regHours;
		Double bonus = 0.0;
		Double lost;
		String b = "";
		
		System.out.print("How many hours will you work in week 1? (Decimal Number): " );
		week1Hours = getter.nextDouble();
		if (week1Hours>40.0) {
			ot1 = week1Hours-40.0;
			week1Hours = 40.0;
		}
		System.out.println();
		System.out.print("How many hours will you work in week 2? (Decimal Number): ");
		week2Hours = getter.nextDouble();
		if (week2Hours>40.0) {
			ot2 = week2Hours-40.0;
			week2Hours = 40.0;
		}
		oTHours = ot1+ot2;
		regHours = week1Hours+week2Hours;
		Double tHours = oTHours+regHours;
		Double regValue = regHours*baseRate;
		Double otValue = oTHours*overTime;
		System.out.println();
		System.out.print("Will you get any bonuses or tips? (Y/N): ");
		String i = getter.next();
		if (i.equalsIgnoreCase("Y")) {
			System.out.println();
			System.out.print("How much will you get in bonuses? (Decimal Number): ");
			bonus = getter.nextDouble();
			b += "$"+d.format(bonus);
		} else {
			b += "no money";
		}
		Double grossVal = regValue+otValue+bonus;
		System.out.println();
		System.out.println(user+" makes $"+d.format(grossVal)+" with the given values.");
		lost = grossVal*getTotDeductions();
		System.out.println("This means that $"+d.format(lost)+" is lost to deductions.");
		Double takeHome = grossVal-lost;
		System.out.println("The end result is that working "+tHours+" hours with "+b+" in bonuses nets a take home of $"+d.format(takeHome));
		System.out.println();
		
		calcTimeLeft2(takeHome);
		System.out.println();
	}
	
	//Headlines the Main Menu to make it more visible.
	private static String pMM() {
		String o = "";
		for (int i = 0; i < 14; i++) {
			o += "/";
		}
		o += " MAIN MENU ";
		for (int i = 0; i < 14; i++) {
			o += "\\";
		}
		return o;
	}
	

	private static void displayMenu() {
		System.out.println(pMM());
		System.out.println("Enter \"1\" to calculate for a single week. ");
		System.out.println("Enter \"2\" to calculate for two weeks. ");
		System.out.println("Enter \"3\" to add a new user. ");
		System.out.println("Enter \"4\" to change user. ");
		System.out.println("Enter \"5\" to update saved values for current user. ");
		System.out.println("Enter \"6\" to delete a user." );
		System.out.println("Enter \"7\" to exit." );
		System.out.println();
	}
	
	private static void run() {
		System.out.print("Select: ");
		int choice = getter.nextInt();
		System.out.println();
		switch(choice) {
			case 1:
				singleWeekCalc();
				break;
			case 2:
				biWeeklyCalc();
				break;
			case 3:
				
				System.out.print("Enter new user name: ");
				String n = getter.next();
				newUser(n);
				break;
			case 4:
				
				System.out.print("Select next user: ");
				String n2 = getter.next();
				setUser(n2);
				loadValues(n2);
				break;
			case 5: 
				//String temp = user;
				removeUser(user);
				newUser(user);
				setUser(user);
				loadValues(user);
				break;
			case 6: 
				
				System.out.print("User to delete: ");
				String n5 = getter.next();
				removeUser(n5);
				break;
			case 7: 
				
				System.out.println("System is shutting down...");
				System.out.println("Good bye, "+user+", and good luck!");
				running = false;
				System.exit(0);
				break;
			default:
				
				System.out.println("Input not recognized in this menu. Please check input.");
				break;
		}
	}
	
	/**
	 * Method for determining how many weeks it would take to pay off all debts (USING SINGLE WEEK CALC)
	 * @param takehome is the take home amount from the weekly calculation.
	 */
	private static void calcTimeLeft1(Double takehome) {
			Double payment = takehome-setAside;
			if(payment > 0) {
		    System.out.println("Setting aside $"+d.format(setAside)+" each week, leaves $"+d.format(payment)+" to pay towards $"+d.format(debt)+" in debt.");
			double weeks = 0;
			Double debttemp = debt;
			while (debttemp > 0.0) {
				debttemp = debttemp-payment;
				weeks++;
			}
		    if (weeks > 52) {
		    	double years = weeks/52.1429;
		    	double weeksR = weeks%52.1429;
                        int yearsf = (int)Math.round(years);
                        int weeksf = (int)Math.round(weeksR);
 		    	System.out.println("Notice this Calculation is in Beta!");
		    	System.out.println("If every week were like this one: \nIt would take "+user+": "+yearsf+" years and "+weeksf+" weeks \nto pay off all debts.");
		    } else {
		    	System.out.println("If every week were like this one:\nIt would take "+user+": "+weeks+" weeks to pay off all debts.");
		    }
		}
	}
	

	
	/**
	 * Method for determining how many weeks it would take to pay off all debts (USING BIWEEKLY CALC)
	 * Note: this is the slightly more accurate method that takes longer.
	 * @param takehome is the take home amount from the biweekly calculation.
	 */
	private static void calcTimeLeft2(Double takehome) {
		Double setAside2 = setAside*2;
			Double payment = takehome-setAside2;
			if(payment>0);
			System.out.println("Setting aside $"+d.format(setAside2)+" bi-weekly, leaves $"+d.format(payment)+" to pay towards $"+d.format(debt)+" in debt.");
			int weeks = 0;
			Double debttemp = debt;
			while (debttemp > 0.0) {
				debttemp = debttemp-payment;
				weeks+=2;
			}
		    if (weeks >= 52) {
		    	double years = weeks/52.1429;
		    	double weeksR = weeks%52.1429;
                        long yearsf = Math.round(years);
                        long weeksf = Math.round(weeksR);
		    	System.out.println("Notice this Calculation is in Beta!");
		    	System.out.println("If every week were like this one: \nIt would take "+user+": "+yearsf+" years and "+weeksf+" weeks to \npay off all debts.");
		    } else {
		    	System.out.println("If every week were like this one:\nIt would take "+user+": "+weeks+" weeks to pay off all debts.");
		    }
				
			
		
	}
	
	
	//Main method to run everything
	public static void main(String[] args) {
		
		try {
			String name = args[0];
			setUser(name);
			loadValues(name);
			while (running) {
				displayMenu();
				run();
			}
			getter.close();
		} catch (Exception e) {
			System.out.println("Shortcut name entry was not detected, please manually set user.");
			System.out.print("User: ");
		    String name = getter.next();
		    setUser(name);
		    loadValues(name);
			while (running) {
				displayMenu();
				run();
			}
		}
		
	}
	
	
}
