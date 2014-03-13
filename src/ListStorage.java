import java.util.ArrayList;

// This class stores the list from the parser

public class ListStorage {
	
	public ArrayList<String> leftList;
	public ArrayList<String> rightList;
	public int levelIndex;
	public ArrayList<String> goalState;
	
	public ListStorage()
	{
		leftList = new ArrayList<String>();
		rightList = new ArrayList<String>();
		goalState = new ArrayList<String>();
//		goalState.add("r");
//		goalState.add("b");
//		goalState.add("y");
//		goalState.add("r");
	}
	
	public void addToLeftList(String lstringToAdd)
	{
		leftList.add(0, lstringToAdd);
		//leftList.add(lstringToAdd);
	}
	
	public void addToRightList(String rstringToAdd)
	{
		rightList.add(rstringToAdd);
	}
	
	public void addLevelInfo(int index)
	{
		levelIndex = index;
	}
	
	public void setGoalState(String[] goalList)
	{
		for(int i = 0; i < goalList.length; i++)
		{
			goalState.add(goalList[i]);
		}
	}
	
	public ArrayList<String> getGoalState()
	{
		return goalState;
	}
	
	public int getLevelInfo()
	{
		return levelIndex;
	}
	
	public int leftListSize()
	{
		return leftList.size();
	}
	
	public int rightListSize()
	{
		return rightList.size();
	}
	
	public ArrayList<String> getCurrentLeftList()
	{
		return leftList;
	}
	
	public ArrayList<String> getCurrentRightList()
	{
		return rightList;
	}

}
