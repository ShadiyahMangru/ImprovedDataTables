import java.nio.file.*;
import java.io.*;

public class Main{
	
	public void mainMenu(){
		System.out.println("\n\t**********************************************************");
		System.out.println(String.format("%1s %-53s %2s", "\t*", "***** Welcome to Hockey Data Filtering Wizard! *****", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     Query Current Roster to Determine Number of:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      1.) Centers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      2.) Left-Wingers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      3.) Right-Wingers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      4.) Wingers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      5.) Forwards", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      6.) Defense", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      7.) Goalies", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      8.) Exit", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "", "*"));
		System.out.println("\t**********************************************************");
		Console console = System.console();
		String userInput = "";
		if(console != null){
			userInput = console.readLine();
			//console.writer().println("Your selection: " + userInput);
		}
		if(userInput.equals("8")){
			System.out.println("You selected: EXIT");
			System.exit(0);
		}
		else if(userInput.equals("7")){
			DataTable dt = new DataTable(SortOptions.sortByName, "Goalies", "Goalie");
			dt.getDataTable();
		}
		else if(userInput.equals("6")){
			DataTable dt = new DataTable(SortOptions.sortByJersey, "Defensemen", "Defense");
			dt.getDataTable();
		}
		else if(userInput.equals("5")){
			DataTable dt = new DataTable(SortOptions.sortByPThenN, "Forwards", "Wing", "Center");
			dt.getDataTable();
		}
		else if(userInput.equals("4")){
			DataTable dt = new DataTable(SortOptions.sortByName, "Wingers", "Wing");
			dt.getDataTable();
		}
		else if(userInput.equals("3")){
			DataTable dt = new DataTable(SortOptions.sortByName, "Right-Wingers", "Right");
			dt.getDataTable();
		}
		else if(userInput.equals("2")){
			DataTable dt = new DataTable(SortOptions.sortByName, "Left-Wingers", "Left");
			dt.getDataTable();
		}
		else{
			DataTable dt = new DataTable(SortOptions.sortByName, "Centers", "Center");
			dt.getDataTable();
		}
		mainMenu();
	}
	
	public static void main(String... args){
		Main m = new Main();
		m.mainMenu();
	}

}