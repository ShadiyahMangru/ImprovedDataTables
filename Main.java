import java.nio.file.*;
import java.io.*;

public class Main{
	
	public void mainMenu(){
		System.out.println("\n\t**********************************************************");
		System.out.println(String.format("%1s %-53s %2s", "\t*", "********** Welcome to Hockey Data Wizard! **********", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     ENTER THE NUMBER OF YOUR SELECTION", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     View Select Position Rankings:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      1.) Centers (by Goals)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      2.) Left-Wingers (by Points)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      3.) Right-Wingers (by Assists)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      4.) Forwards (by Shooting % Then Name)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      5.) Defense (by Shooting % Then Name)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      6.) Goalies (by Jersey Number)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     View the Current Roster:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      7.) All Players", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      8.) Wingers (by Position then Last Name)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "     View Stats Leaders by Position:", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      9.) Goals Leaders (C/RW/LW/D)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      10.) Points Leaders (C/RW/LW/D)", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", " ", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "      11.) Exit", "*"));
		System.out.println(String.format("%1s %-53s %2s", "\t*", "", "*"));
		System.out.println("\t**********************************************************");
		Console console = System.console();
		String userInput = "";
		DataTable dt = new DataTable();
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
			dt.getTopStats(SortOptions.sortByPoints);
		}
		else if(userInput.equals("9")){
			System.out.println("You selected: DISPLAY GOALS LEADERS BY POSITION\n");
			dt.getTopStats(SortOptions.sortByGoals);
		}
		else if(userInput.equals("8")){
			dt.getStatsByPosition(SortOptions.sortByPThenN, "Wingers", "Wing");
		}
		else if(userInput.equals("7")){
			System.out.println("You selected: DISPLAY ROSTER");
			dt.getRoster();
		}
		else if(userInput.equals("6")){
			dt.getStatsByPosition(SortOptions.sortByJersey, "Goalies", "Goalie");
		}
		else if(userInput.equals("5")){
			dt.getStatsByPosition(SortOptions.sortByShootingPercentThenName, "Defensemen", "Defense");
		}
		else if(userInput.equals("4")){
			dt.getStatsByPosition(SortOptions.sortByShootingPercentThenName, "Forwards", "Wing", "Center");
		}
		else if(userInput.equals("3")){
			dt.getStatsByPosition(SortOptions.sortByAssists, "Right-Wingers", "Right");
		}
		else if(userInput.equals("2")){
			dt.getStatsByPosition(SortOptions.sortByPoints, "Left-Wingers", "Left");
		}
		else{
			dt.getStatsByPosition(SortOptions.sortByGoals, "Centers", "Center");
		}
		mainMenu();
	}
	
	public static void main(String... args){
		Main m = new Main();
		m.mainMenu();
	}
}
