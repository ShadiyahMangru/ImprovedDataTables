# ImprovedDataTables -- Efficient Data Sorting and Filtering

## JAVA SE 8 COMPARATOR SUMMARY

#### Comparator class (in java.util package) used to: 
 - specify a different sort order than the object itself provides
 - to sort an object that did not implement Comparable
 - to sort objects in different ways at different times

#### A Comparator must implement compare() method
 - **Comparator class contains the abstract method compare(T o1, T o2) that compares its two arguments for order and returns an int.**

Each of this application's Comparators uses the @Override annotation to signal to the compiler that the ensuing method is an intended override of the Comparator Interface.  

In *sortByName*, the compareTo() method compares the String that is each HockeyPlayer object parameter’s lastName, and returns a positive, negative or zero number to determine alphabetic ordering.
```
public Comparator<HockeyPlayer> sortByName = new Comparator<HockeyPlayer>() {
	@Override
	public int compare(HockeyPlayer h1, HockeyPlayer h2) {
		return h1.getLastName().compareTo(h2.getLastName());
	}
};
```
In *sortByJersey*, subtraction compares the int that is each HockeyPlayer object parameter’s jersey number, and returns a positive, negative or zero number to determine numeric ordering.
```
public Comparator<HockeyPlayer> sortByJersey = new Comparator<HockeyPlayer>() {
	@Override
	public int compare(HockeyPlayer h1, HockeyPlayer h2) {
		return h2.getJersey() - h1.getJersey();	
	}
};
```
In *sortByPThenN*, if the first compareTo() method returns a zero to signify that both HockeyPlayer object parameters have the same position, another compareTo() method is called to compare each HockeyPlayer object parameter's last name.  This Comparator sorts the current stream elements first by position, and then subsorts players with the same position from A-Z by last name.  
```
public Comparator<HockeyPlayer> sortByPThenN = new Comparator<HockeyPlayer>() {
	@Override
	public int compare(HockeyPlayer h1, HockeyPlayer h2) {
		if(h2.getPosition().compareTo(h1.getPosition()) != 0){
			return h2.getPosition().compareTo(h1.getPosition());	
		}
		return h1.getLastName().compareTo(h2.getLastName());		
	}
};
```

In *sortByShootingPercentThenName*, each HockeyPlayer object parameter is cast to a Skater object to perform shooting percentages comparisons.  When the difference is positive/negative, a 1/-1 is returned to satisfy int return value requirements of the compare(T o1, T o2) method override.  The else statement implicitly handles the equality case of the two HockeyPlayer-cast-to-Skater object parameters' shooting percentages; in this case, the same logic as the *sortByName* comparator determines ordering alphabetically.  No narrowing cast is required for this comparison by last name, because *lastName* is a HockeyPlayer object instance variable.
```
public static Comparator<HockeyPlayer> sortByShootingPercentThenName = new Comparator<HockeyPlayer>() {
	@Override
	public int compare(HockeyPlayer h1, HockeyPlayer h2) {
		if(((Skater)h2).getShootingPercent() - ((Skater)h1).getShootingPercent() > 0) {
			return 1;	
		}
		else if(((Skater)h2).getShootingPercent() - ((Skater)h1).getShootingPercent() < 0) {
			return -1;	
		}
		else{
			return h1.getLastName().compareTo(h2.getLastName());	
		}
	}
};
```

<br>

## JAVA’S BUILT-IN FUNCTIONAL INTERFACES SUMMARY

A **functional interface** has exactly one abstract method.
*Bi* prefix denotes two parameters (BiConsumer, BiPredicate, BiFunction – BinaryOperator extends BiFunction).

#### The following common functional interfaces are in the java.util.function package:
- **Supplier**: generates or supplies values without taking any input
  - No parameters
  - Return type T 
  - get()   
  
In the *DataTable* class of this application, the *dataTableHeaderG* / *dataTableHeaderS* **Supplier** accepts no input and returns a (formatted) String with the Data Table headers customized for a Goalie / Skater (read: Center/Right-Wing/Left-Wing/Defense) object.

```
Supplier<String> dataTableHeaderG  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s | %-7s | %-9s | %-9s | %-9s | %-15s |", "TEAM", "PLAYER", "#", "POSITION", "GP", "WINS", "SHOTS AG", "SAVES", "SAVE %") +
	"\n----------------------------------------------------------------------------------------------------------------";
		
Supplier<String> dataTableHeaderS  = ()-> String.format("| %-4s | %-15s | %-4s | %-11s | %-7s | %-9s | %-9s | %-9s | %-15s |", "TEAM", "PLAYER", "#", "POSITION", "GP", "GOALS", "ASSISTS", "POINTS", "SHOOTING %") +
	"\n----------------------------------------------------------------------------------------------------------------";				
```

In the *getStatsByPosition()* method of the *DataTable* class, the *dataTableHeaderG* / *dataTableHeaderS* Supplier provides the Data Table headers depending on whether the control-flow if-else statement branches for a Goalie object, or for a Skater object.
```
if(matchCategory.contains("Goalie")){
	System.out.println(dataTableHeaderG.get());
}
else{
	System.out.println(dataTableHeaderS.get());
}
```
  
- **Consumer**: acts upon a parameter but does not return anything 
  - 1 parameter T
  - Return type void
  - accept()
- **Predicate**: used to test a condition (often used when filtering or matching)
  - 1 parameter T
  - Return type boolean
  - test()
- **Function**: turns one parameter into a value of a potentially different type and returns it
  - 1 parameter T
  - Return type R
  - apply()
  
In the *DataTable* class of this application, the *setSortType* **Function** accepts a Comparator of HockeyPlayer objects, and returns a String describing the current basis for HockeyPlayer object ordering.

```
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
```

In the *getStatsByPosition()* method of the *DataTable* class, the *setSortType* Function accepts the *sortBy* Comparator parameter of the *getStatsByPosition()* method, then dynamically provides the value for the *sortByType* String local variable of this method (see line 2 of the code below).  In the fourth line from the top, a print statement outputs this description to the screen.  
```
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
	
	... 

}
```
Each *getStatsByPosition()* method call provides a new opportunity to change the criteria by which the Data Table’s HockeyPlayer objects are sorted.

  
- **UnaryOperator**: transforms its value into one of the same type
  - 1 parameter T
  - Return type T
  - apply()

In the *DataTable* class of this application, the *getInfo* **UnaryOperator** accepts an input String of hockey player data, and returns a String with excess formatting/delimiters/spaces removed.
```
public UnaryOperator<String> getInfo = inputString -> {
	int pipeIndex = inputString.indexOf("|");
	return inputString.substring(0, pipeIndex).trim();
};
```
In the *initHP* Function of the *DataTable* class, the *getInfo* UnaryOperator appears in each new HockeyPlayer/Goalie/Skater object initialization (see the third, the sixth, and the eighth line from the bottom).  In each instance, the *getInfo* UnaryOperator accepts a String of hockey player data from the roster input file, and isolates the information necessary to initialize HockeyPlayer/Goalie/Skater objects that correspond to the input file data. 
```
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
```

<br>

## JAVA STREAM API SUMMARY
### A stream in Java is a sequence of data.  A stream pipeline has three parts: (i) Source, (ii) Intermediate Operations, (iii) Terminal Operations.

In this example, the second line from the top is the **source**, the third, fourth, and fifth lines from the top comprise the **intermediate operations**, and the last line is the **terminal operation**.
```
long matches = 
Files.lines(file) //returns the lines from the input file as a Stream (reads in data line-by-line)
.filter(inputString -> keepMatches.test(inputString, matchThis))
.map(match -> initHP.apply(match))
.peek(hp -> System.out.println(hp))
.count();
```
### (i) Source: creates the data in the stream (required).

### (ii) Intermediate Stream Operations: transform the stream into another one.  
There can be as few or as many intermediate operations as desired.  Since streams use lazy evaluation, the intermediate operations do not run until the terminal operation runs.  THERE CAN BE ZERO OR MORE INTERMEDIATE OPERATIONS; INTERMEDIATE OPERATIONS CAN EXIST MULTIPLE TIMES IN A PIPELINE.

**Intermediate Stream Operations Examples:**
- The **filter()** method returns a Stream with elements that match a given expression. 

The stream below utilizes the filter() method in the fourth line from the top.
```
long matches = 
Files.lines(file)
.flatMap(line -> Stream.of(line.split("\n"))) //reads in data line-by-line
.filter(inputString -> keepMatches.test(inputString, matchThis))
.peek(match -> {String[] matchArray = match.split(":");
	 String ln = matchArray[1].trim();
	 int pipeIndexLN = ln.indexOf("|");
	 String position = matchArray[2].trim();
	 int pipeIndexP = position.indexOf("|");
	 HockeyPlayer hp = new HockeyPlayer(ln.substring(0, pipeIndexLN), position.substring(0, pipeIndexP), Integer.parseInt(matchArray[3].trim()), "WSH");
	 System.out.println(hp);
})
.count();
```

In this stream, the filter() method examines each element of the stream (which is a line of the input file), and removes elements for which the *keepMatches* BiPredicate returns false.
```
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
  ```
The keepMatches BiPredicate accepts an input String and a String array of desired matches.  If the input String contains the array element at index 0 or index 1 (for 2 element arrays), or contains the array element at index 0 (for arrays less than or greater than 2 elements), a true is returned – this BiPredicate was designed for arrays with 2 or 1 element(s) but contains implicit ArrayIndexOutOfBounds exception handling should the varargs parameter accept an array with more than 2 elements or no array.  When the Stream filter() method utilizes this BiPredicate, each input that yields a true remains an element in the stream.



- The **flatMap()** method takes each element in a stream and makes any elements it contains top-level elements in a single stream.  The flatMap() method flattens nested lists into a single level and removes empty lists.
- The **sorted()** method returns a stream with the elements sorted.  Just like sorting arrays, Java uses natural ordering unless one specifies a comparator.  

The stream below utilizes the sorted() method in the fifth line from the top.
```
long matches = 
Files.lines(file) //returns the lines from the input file as a Stream (reads in data line-by-line)
.filter(inputString -> keepMatches.test(inputString, matchThis))
.map(match -> initHP.apply(match))
.sorted(sortBy)
.peek(hp -> System.out.println(hp))
.count();
```
In this stream, the sorted() method (re)orders the current elements in the stream according to the definition of *sortBy* (a parameter, of type Comparator<HockeyPlayer>, of the larger method that contains this stream).  In this application, *sortBy* currently assumes one of three forms: *sortByName*, *sortByJersey*, *sortByPThenN*.  The current elements in the stream are reordered depending on which Comparator<HockeyPlayer> the Stream sorted() method relies on (*sortByName*, *sortByJersey*, or *sortByPThenN* in this example).

- The **peek()** method is useful for debugging because it allows one to perform a stream operation without actually changing the stream.  The most common use for peek() is to output the contents of the stream as it goes by.  
- The **distinct()** method returns a stream with duplicate values removed. 
- The **limit()** and **skip()** methods make a Stream smaller. 
- The **map()** method creates a one-to-one mapping from the elements in the stream to the elements of the next step of the stream.  The map() method returns a Stream transforming each element to another through a Function. 

The stream below utilizes the map() method in the fifth line from the top.
```
long matches = 
Files.lines(file)
.flatMap(inputData -> Stream.of(inputData.split("\n"))) //reads in data line-by-line
.filter(inputString -> keepMatches.test(inputString, matchThis))
.map(match -> initHP.apply(match))
.peek(hp -> System.out.println(hp))
.count();
```
In this stream, the map() method examines each element of the stream (which in this example is a line in the format Player: xxxxxxxx | Position: xxxxxxxx | Jersey: xx), and, after extracting the information necessary to initialize a new HockeyPlayer object, transforms each stream element into a HockeyPlayer object.  The *initHP* Function accomplishes this stream element transformation.
```
public Function<String, HockeyPlayer> initHP = inputString -> {
	String[] sepVals = inputString.split(":");
	String ln = sepVals[1].trim();
	int pipeIndexLN = ln.indexOf("|");
	String position = sepVals[2].trim();
	int pipeIndexP = position.indexOf("|");
	HockeyPlayer hp = new HockeyPlayer(ln.substring(0, pipeIndexLN), position.substring(0, pipeIndexP), Integer.parseInt(sepVals[3].trim()), "WSH");
	return hp;
};
```
The initHP Function receives a String of hockey player information and methodically extracts a hockey player’s name (as a String), position (as a String), and jersey number (as an Integer) from this String (via split(), trim(), indexOf(), and substring() String API methods, and parseInt() Integer API method).  These values (the latter of which Java automatically unboxes), initializes a new HockeyPlayer object which this Function returns.  When the Stream map() method utilizes the *initHP* Function, each String input transforms into a HockeyPlayer object output, of which the stream is now comprised.


### (iii) Terminal Stream Operations: actually produce a result.  
Since streams can be used only once, the stream is no longer valid after a terminal operation completes.  TERMINAL OPERATIONS CANNOT EXIST MULTIPLE TIMES IN A PIPELINE.  IF NO TERMINAL OPERATION IS IN THE PIPELINE, A STREAM IS RETURNED BUT NOT EXECUTED.

**Terminal Stream Operations Examples:**
- The **count()** method determines the number of elements in a finite stream. 
- The **min()** and **max()** methods allow one to pass a custom comparator and find the smallest or largest value in a finite stream according to that sort order.
- The **findAny()** and **findFirst()** methods return an element of the stream unless the stream is empty.**
- The **allMatch()**, **anyMatch()** and **noneMatch()** methods search a stream and return information about how the stream pertains to the predicate.**
- The **forEach()** method is a looping construct.
- The **reduce()** method combines a stream into a single object.
- The **collect()** method is a special type of reduction called a mutable reduction.  A mutable reduction collects into the same object as it goes.

** Denotes search methods of the Stream API.

NOTE: You can perform a terminal operation without any intermediate operations but not the other way around.
