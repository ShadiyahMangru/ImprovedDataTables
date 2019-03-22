import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class DataTable{
	//fields
	private List<HockeyPlayer> roster;
	private String matchCategory;
	private String[] matchThis;
	private Comparator<HockeyPlayer> sortBy;
	private final Path file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\HockeyDataTable\\2019WSHStatsREV.txt").toPath();	//data input file location
	
	//constructor
	public DataTable(Comparator<HockeyPlayer> sortBy, String matchCategory, String... matchThis){
		this.sortBy = sortBy;
		this.matchCategory = matchCategory;
		this.matchThis = matchThis;
		setRoster();
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
		String jersey = sepVals[3].trim();
		int pipeIndexJ = jersey.indexOf("|");
		String gp = sepVals[4].trim();
		int pipeIndexGP = gp.indexOf("|");
		String savesGoals = sepVals[5].trim();
		int pipeIndexSG = savesGoals.indexOf("|");
		String shotsAgAssists = sepVals[6].trim();
		int pipeIndexSAA = shotsAgAssists.indexOf("|");
		HockeyPlayer hp = new HockeyPlayer(ln.substring(0, pipeIndexLN), position.substring(0, pipeIndexP), Integer.parseInt(jersey.substring(0, pipeIndexJ).trim()), "WSH");
		if(hp.getPosition().contains("Goalie")){
			return new Goalie(hp, Integer.parseInt(gp.substring(0, pipeIndexGP).trim()), Integer.parseInt(savesGoals.substring(0, pipeIndexSG).trim()), Integer.parseInt(shotsAgAssists.substring(0, pipeIndexSAA).trim()), Integer.parseInt(sepVals[7].trim()));
		}
		else{
			return new Skater(hp, Integer.parseInt(gp.substring(0, pipeIndexGP).trim()), Integer.parseInt(savesGoals.substring(0, pipeIndexSG).trim()), Integer.parseInt(shotsAgAssists.substring(0, pipeIndexSAA).trim()), Integer.parseInt(sepVals[7].trim()));
		}
	};

	public Function<String, String> getName = inputString -> {
		int colonIndex = inputString.indexOf(":");
		int pipeIndex = inputString.indexOf("|");
		return inputString.substring(colonIndex+1, pipeIndex).trim();
		
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
	
	Supplier<String> dataTableHeaderG  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s | %-7s | %-9s | %-9s | %-9s | %-15s |", "TEAM", "PLAYER", "#", "POSITION", "GP", "WINS", "SHOTS AG", "SAVES", "SAVE %") +
		"\n----------------------------------------------------------------------------------------------------------------";
		
	Supplier<String> dataTableHeaderS  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s | %-7s | %-9s | %-9s | %-9s | %-15s |", "TEAM", "PLAYER", "#", "POSITION", "GP", "GOALS", "ASSISTS", "POINTS", "SHOOTING %") +
		"\n----------------------------------------------------------------------------------------------------------------";			
	
	public void getDataTable(){
		String sortByType = setSortType.apply(sortBy);
		System.out.println("*** Filter Selection: " + matchCategory + " ***");	
		System.out.println("*** Results Sorted By: " + sortByType + " ***\n");
		if(matchCategory.contains("Goalie")){
			System.out.println(dataTableHeaderG.get());
		}
		else{
			System.out.println(dataTableHeaderS.get());
		}
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
	
	public void getStatsLeads(Comparator<HockeyPlayer> sortBy, String... positionType){
		try{
			Optional<HockeyPlayer> statsLead = 
			roster.stream()
			.filter(input ->keepMatches.test(((HockeyPlayer)input).getPosition(), positionType))
			.min(sortBy);
			System.out.println(statsLead.get());
			
		}
		catch(Exception e){
			System.out.println("Exception: " + e);
		}
		
	}
	
	public void getTopStats(Comparator<HockeyPlayer> sortBy){
		System.out.println(dataTableHeaderS.get());
		getStatsLeads(sortBy, "Center");
		getStatsLeads(sortBy, "Right Wing");
		getStatsLeads(sortBy, "Left Wing");
		getStatsLeads(sortBy, "Defense");
	}
	
	public void setRoster(){
		try{
			roster = new ArrayList<HockeyPlayer>();
			roster = 
			Files.lines(file)
			.filter(s -> s.startsWith("Pl"))
			.map(s -> initHP.apply(s))
			.sorted(SortOptions.sortByName)
			.collect(Collectors.toList());
		}
		catch (IOException ioe){
			System.out.println("Exception: " + ioe);	
		}
	}
	
	public void getRoster(){
		for(int i=0; i<roster.size(); i++){
			System.out.println("\t" + (i+1) + ".) " + ((HockeyPlayer)roster.get(i)).getLastName());	
		}
	}
	
}
