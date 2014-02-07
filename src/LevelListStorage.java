import java.util.ArrayList;


public class LevelListStorage {
	
	public ArrayList<ListStorage> list;
	
	public LevelListStorage()
	{
		list = new ArrayList<ListStorage>();
		// we start adding stuff at index 1 to coordinate with level 1
		list.add(0, null);
	}
	
	public void addLevelList(ListStorage levelList)
	{
		list.add(levelList);
	}
	
	public ListStorage getList(int index)
	{
		return list.get(index);
	}
	
	public int size()
	{
		return list.size();
	}

}
