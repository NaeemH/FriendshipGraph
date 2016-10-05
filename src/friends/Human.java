package friends;

public class Human {//encapsulates all the behaviors of humans
	public String name;
	public String school;
	public FriendList flist;
	public boolean visited;
	public int index;
	public int schoolID;
	public int back;
	public int dfs;
	
	public Human(String name, String school, FriendList flist, boolean visited, int index, int schoolID, int back, int dfs)
	{
		this.name = name;
		this.school = school;
		this.flist = flist;
		this.visited = visited;
		this.index = index;
		this.schoolID = schoolID;
		this.back = back;
		this.dfs = dfs;
	}
}
