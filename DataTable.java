import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class DataTable{
	//fields
	private List<HockeyPlayer> roster;
	private final Path file = new File("C:\\Users\\593476\\Desktop\\Java Programs\\HockeyDataTable\\2019WSHStatsREV.txt").toPath();	//data input file location
	
	//constructor
	public DataTable(){
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
	
	public UnaryOperator<String> getInfo = inputString -> {
		int pipeIndex = inputString.indexOf("|");
		return inputString.substring(0, pipeIndex).trim();
	};
	
	public Function<String, HockeyPlayer> initHP = inputString -> {
		String[] sepVals = inputString.split(":");
		String ln = sepVals[1].trim();
		String position = sepVals[2].trim();
		String jersey = sepVals[3].trim();
		String gp = sepVals[4].trim();
		String savesGoals = sepVals[5].trim();
		String shotsAgAssists = sepVals[6].trim();
		HockeyPlayer hp = new HockeyPlayer(getInfo.apply(ln), getInfo.apply(position), Integer.parseInt(getInfo.apply(jersey)), "WSH");
		if(hp.getPosition().contains("Goalie")){
			return new Goalie(hp, Integer.parseInt(getInfo.apply(gp)), Integer.parseInt(getInfo.apply(savesGoals)), Integer.parseInt(getInfo.apply(shotsAgAssists)), Integer.parseInt(sepVals[7].trim()));
		}
		else{
			return new Skater(hp, Integer.parseInt(getInfo.apply(gp)), Integer.parseInt(getInfo.apply(savesGoals)), Integer.parseInt(getInfo.apply(shotsAgAssists)), Integer.parseInt(sepVals[7].trim()));
		}
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
		else if(inputComparator == SortOptions.sortByGoals){
			return "Goals";	
		}
		else if(inputComparator == SortOptions.sortByPoints){
			return "Points";	
		}
		else if(inputComparator == SortOptions.sortByAssists){
			return "Assists";	
		}
		else if(inputComparator == SortOptions.sortByShootingPercentThenName){
			return "Shooting Percent Then Name";	
		}
		return "Arbitrary";
	};
	
	Supplier<String> dataTableHeaderG  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s | %-7s | %-9s | %-9s | %-9s | %-15s |", "TEAM", "PLAYER", "#", "POSITION", "GP", "WINS", "SHOTS AG", "SAVES", "SAVE %") +
		"\n----------------------------------------------------------------------------------------------------------------";
		
	Supplier<String> dataTableHeaderS  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s | %-7s | %-9s | %-9s | %-9s | %-15s |", "TEAM", "PLAYER", "#", "POSITION", "GP", "GOALS", "ASSISTS", "POINTS", "SHOOTING %") +
		"\n----------------------------------------------------------------------------------------------------------------";			
	
	public void getStatsByPosition(Comparator<HockeyPlayer> sortBy, String matchCategory, String... matchThis){
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
			roster.stream() //returns the lines from the input file as a Stream (reads in data line-by-line)
			.filter(inputString -> keepMatches.test(((HockeyPlayer)inputString).getPosition(), matchThis))
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
