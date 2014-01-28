import java.util.ArrayList;

// This class stores the list from the parser

public class ListStorage {
	
	ArrayList<String> leftList;
	ArrayList<String> rightList;
	
	public ListStorage()
	{
		leftList = new ArrayList<String>();
		rightList = new ArrayList<String>();
	}
	
	public void addToLeftList(String lstringToAdd)
	{
		leftList.add(lstringToAdd);
	}
	
	public void addToRightList(String rstringToAdd)
	{
		rightList.add(rstringToAdd);
	}
	
	public String popLeft()
	{
		if(leftList.isEmpty()==false)
		{
			return null;
		}
		return leftList.remove(0);
	}
	
	public String popRight()
	{
		if(rightList.isEmpty())
		{
			return null;
		}
		return rightList.remove(0);
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
