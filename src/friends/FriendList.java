package friends;

public class FriendList {
	public int index;
	public FriendList next;
	
	public FriendList(int index, FriendList next)
	{
		this.index = index;
		this.next = next;
	}
}
