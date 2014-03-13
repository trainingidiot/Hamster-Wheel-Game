import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class Parser {
	
	private LevelListStorage levelStorage;

	public void callParser() throws FileNotFoundException
	{
		  // parser
  		File file = new File("resources/Input.txt");
  		FileReader fr = new FileReader(file);
  		Scanner scanner = new Scanner(fr);
  		
  		levelStorage = new LevelListStorage();
  		while(scanner.hasNext())
  		{
  	  		ListStorage storage = new ListStorage();
  			storage.addLevelInfo(scanner.nextInt());
  	  		String currToken = scanner.next();
  	  		String goalList[] = new String[4]; 
  	  		for(int i = 0; i < goalList.length; i++)
  	  		{
  	  			goalList[i] = currToken;
  	  			currToken = scanner.next();
  	  		}
  	  		storage.setGoalState(goalList);
  	  		while(!currToken.equals("Right"))
	  		{
	  			storage.addToLeftList(currToken);
	  			currToken = scanner.next();
	  		}	
	  		currToken = scanner.next();
	  		while(!currToken.equals("#") && scanner.hasNext())
	  		{
	  			storage.addToRightList(currToken);
	  			currToken = scanner.next();
	  		}
	  		levelStorage.addLevelList(storage);
	  		
  		}
  		
  		scanner.close();
 /* 		
  		//parser test
  		System.out.println("Beginning of parser test");
  		System.out.println("levelListStrage size: "+ levelStorage.size());
  		
  		for(int i = 1; i < levelStorage.size(); i++)
  		{
  			System.out.println();
  			System.out.println("########  LEVEL "+levelStorage.getList(i).getLevelInfo()+" #########");
  			System.out.print("Left list: ");
  			for(int j = 0; j < levelStorage.getList(i).getCurrentLeftList().size(); j++)
  			{
  				System.out.print(levelStorage.getList(i).getCurrentLeftList().get(j) + " ");
  			}
  			System.out.println();
  			System.out.print("Right list: ");
  			for(int j = 0; j < levelStorage.getList(i).getCurrentRightList().size(); j++)
  			{
  				System.out.print(levelStorage.getList(i).getCurrentRightList().get(j) + " ");
  			}
  			System.out.println();
  		}
  		
 		System.out.println();
 		System.out.println("End of parser test");
 		System.out.println();
 		System.out.println();
*/
  		
	}
	
	public LevelListStorage getList(){
		return levelStorage;
	}

}
