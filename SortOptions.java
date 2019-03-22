import java.util.*;

interface SortOptions{
	public static Comparator<HockeyPlayer> sortByName = new Comparator<HockeyPlayer>() {
		@Override
		public int compare(HockeyPlayer h1, HockeyPlayer h2) {
			return h1.getLastName().compareTo(h2.getLastName());
		}
	};
	
	public static Comparator<HockeyPlayer> sortByJersey = new Comparator<HockeyPlayer>() {
		@Override
		public int compare(HockeyPlayer h1, HockeyPlayer h2) {
			return h2.getJersey() - h1.getJersey();	
		}
	};
	
	public static Comparator<HockeyPlayer> sortByGoals = new Comparator<HockeyPlayer>() {
		@Override
		public int compare(HockeyPlayer h1, HockeyPlayer h2) {
			return ((Skater)h2).getGoals() - ((Skater)h1).getGoals();	
		}
	};
	
	public static Comparator<HockeyPlayer> sortByPoints = new Comparator<HockeyPlayer>() {
		@Override
		public int compare(HockeyPlayer h1, HockeyPlayer h2) {
			return ((Skater)h2).getPoints() - ((Skater)h1).getPoints();	
		}
	};
	
	public static Comparator<HockeyPlayer> sortByPThenN = new Comparator<HockeyPlayer>() {
		@Override
		public int compare(HockeyPlayer h1, HockeyPlayer h2) {
			if(h2.getPosition().compareTo(h1.getPosition()) != 0){
				return h2.getPosition().compareTo(h1.getPosition());	
			}
			return h1.getLastName().compareTo(h2.getLastName());		
		}
	};
}
