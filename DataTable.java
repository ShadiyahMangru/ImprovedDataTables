import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.function.*;

public class DataTable{
	//fields
	private String matchCategory;
	private String[] matchThis;
	private Comparator<HockeyPlayer> sortBy;
	private final Path file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\HockeyDataTable\\2019WSHStatsREV.txt").toPath();	//data input file location
	
	//constructor
	public DataTable(Comparator<HockeyPlayer> sortBy, String matchCategory, String... matchThis){
		this.sortBy = sortBy;
		this.matchCategory = matchCategory;
		this.matchThis = matchThis;
	}
	
	public BiPredicate<String, String[]> keepMatches = (input, matchThisArray) -> {
		if(matchThisArray.length==0){ //handles an ArrayIndexOutOfBoundsException
			return false;	
		}
		else if(matchThisArray.length==2){
			if(input.contains(matchThisArray[0]) | input.contains(matchThisArray[1])){
				return true;	
			}
			return false;	
		}
		else if(input.contains(matchThisArray[0])){
				return true;	
				
		}
		return false;
	};
	
	public Function<String, HockeyPlayer> initHP = inputString -> {
		String[] sepVals = inputString.split(":");
		String ln = sepVals[1].trim();
		int pipeIndexLN = ln.indexOf("|");
		String position = sepVals[2].trim();
		int pipeIndexP = position.indexOf("|");
		HockeyPlayer hp = new HockeyPlayer(ln.substring(0, pipeIndexLN), position.substring(0, pipeIndexP), Integer.parseInt(sepVals[3].trim()), "WSH");
		return hp;
	};
	
	public Function<Comparator<HockeyPlayer>, String> setSortType = inputComparator -> {
		if(inputComparator == SortOptions.sortByName){
			return "Last Name";		
		}
		else if(inputComparator == SortOptions.sortByJersey){
			return "Jersey Number";
		}
		else if(inputComparator == SortOptions.sortByPThenN){
			return "Position Then Last Name";	
		}
		return "Arbitrary";
	};
	
	public void getDataTable(){
		String sortByType = setSortType.apply(sortBy);
		System.out.println("*** Filter Selection: " + matchCategory + " ***");	
		System.out.println("*** Results Sorted By: " + sortByType + " ***\n");
		System.out.println(dataTableHeader.get());
		try{
			long matches = 
			Files.lines(file) //returns the lines from the input file as a Stream (reads in data line-by-line)
			.filter(inputString -> keepMatches.test(inputString, matchThis))
			.map(match -> initHP.apply(match))
			.sorted(sortBy)
			.peek(hp -> System.out.println(hp))
			.count();
		
			System.out.println("\n# of " + matchCategory + ": " + matches);
		}
		catch(Exception e){
			System.out.println("Exception: " + e);
		}
	}

	Supplier<String> dataTableHeader  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s |", "TEAM", "PLAYER", "#", "POSITION") +
		"\n-----------------------------------------------";
}			