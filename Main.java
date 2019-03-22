import java.nio.file.*;
import java.io.*;

public class Main{
	
	public void mainMenu(){
		System.out.println("\n\t**********************************************************");
		System.out.println(String.format("%1s %-53s %2s", "\t*", "********** Welcome to Hockey Data Wizard! **********", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     ENTER THE NUMBER OF YOUR SELECTION", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     Stats Filtered by Position:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      1.) Centers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      2.) Left-Wingers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      3.) Right-Wingers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      4.) Wingers", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      5.) Forwards", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      6.) Defense", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      7.) Goalies", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     View the Current Roster:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      8.) All Players", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     Stats Leaders by Position:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      9.) Goals Leaders (C/RW/LW/D)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      10.) Points Leaders (C/RW/LW/D)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      11.) Exit", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "", "*"));
		System.out.println("\t**********************************************************");
		Console console = System.console();
		String userInput = "";
		if(console != null){
			userInput = console.readLine();
			//console.writer().println("Your selection: " + userInput);
		}
		if(userInput.equals("11")){
			System.out.println("You selected: EXIT");
			System.exit(0);
		}
		else if(userInput.equals("10")){
			System.out.println("You selected: DISPLAY POINTS LEADERS BY POSITION\n");
			DataTable dt = new DataTable(SortOptions.sortByName, "Goalies", "Goalie");
			dt.getTopStats(SortOptions.sortByPoints);
		}
		else if(userInput.equals("9")){
			System.out.println("You selected: DISPLAY GOALS LEADERS BY POSITION\n");
			DataTable dt = new DataTable(SortOptions.sortByName, "Goalies", "Goalie");
			dt.getTopStats(SortOptions.sortByGoals);
		}
		else if(userInput.equals("8")){
			System.out.println("You selected: DISPLAY ROSTER");
			DataTable dt = new DataTable(SortOptions.sortByName, "Goalies", "Goalie");
			dt.getRoster();
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
