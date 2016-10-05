//Naeem Hossain
package friends;

import java.io.*;
import java.util.*;


public class Friends {
	
	public static void main(String[] args)
		 throws IOException {
	 // TODO Auto-generated method stub
	 Scanner scan = new Scanner(System.in);
	 System.out.print("Enter input file name: ");
	 String txtfile = scan.nextLine();
     Hashtable<String, Integer> humantable = new Hashtable<String, Integer>();
	 int choice = 0;
	 do
	 {
		Human[] h = FriendshipGraph(txtfile);
		humantable = makeTable(h);
		System.out.println("\n1.Find the shortest path between two people.\n2.Determine cliques at a school.\n3.Find connectors.\n4.Quit.\nEnter a number:");
		choice = scan.nextInt();
		if(choice == 1)
	 	{
			System.out.println("Enter starting human:");
			String name1 = scan.next();
			System.out.println("Enter target human:");
			String name2 = scan.next();
			shortestPath(name1,name2,h,humantable);
	 	}else if(choice == 2)
	 	{
		 	System.out.println("Please enter name of college:");
		 	String school = scan.next();
		 	showcliques(h,school);		 	
	 	}else if(choice == 3)
	 	{
	 		connectors(h,h.length);
	 	}
		reset(h);
	}while(choice != 4);
	}
	
	public static void reset(Human[] humans)
	{
		for(int counter = 0; counter < humans.length;counter++){
			humans[counter].schoolID = -1;
			humans[counter].back = -1;
			humans[counter].dfs = -1;
			humans[counter].visited = false;
		}
	}
	
	public static Human[] FriendshipGraph(String file) throws FileNotFoundException {

			 Scanner sc = new Scanner(new File(file));

			 Human[] humans = new Human[sc.nextInt()];
			 String line = sc.nextLine();
			 String n = null;
			 String s = null;
			 for (int counter = 0; counter < humans.length; counter++) {
				 line = sc.nextLine();
				 int index = 0;
				 if(line.contains("|y|"))
				 {
					 index = line.indexOf("|y|");
					 n = line.substring(0,index);
					 s = line.substring(index+3);
				 }else if(line.contains("|n"))
				 {
					 index = line.indexOf("|n");
					 n = line.substring(0,index);
					 s = null;
				 }
				 humans[counter] = new Human(n,s,null,false,counter,-1,-1,-1);
			 }
			 
			 while (sc.hasNext()) {
				 n = sc.nextLine();
				 String n1 = n.substring(0,n.indexOf("|"));
				 String n2 = n.substring(n.indexOf("|") + 1);
				 int one = indexForName(n1,humans);
				 int two = indexForName(n2,humans);
				 humans[one].flist = new FriendList(two, humans[one].flist);
				 humans[two].flist = new FriendList(one, humans[two].flist);
			 }
			 
			 return humans;
		 }
	
	 public static int indexForName(String name,Human[] humans) {
		 for (int counter = 0; counter < humans.length; counter++) {
			 String n = humans[counter].name;
			 if (n.equalsIgnoreCase(name)) {
				 return counter;
			 }
		 }
		 return -1;
	 }
	 
	 public static Hashtable<String, Integer> makeTable(Human[] humans)
	 {
		 Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		 for(int counter = 0;counter < humans.length; counter++)
		 {
			 table.put(humans[counter].name, counter);
		 }
		 return table;
	 }
	 
	 /*
	 public static void print(Human[] humans)
	 {
		 for (int counter = 0; counter < humans.length; counter++) {
			 System.out.print(humans[counter].name + printer(humans[counter].school));
			 for (FriendList f = humans[counter].flist; f != null; f = f.next) {
				 System.out.print(" --> " + humans[f.index].name + printer(humans[f.index].school));
			 }
			 System.out.println();
		 }
	 }
	 
	 
	 public static String printer(String s)
	 {
		 if(s!=null)
			 return ", " + s;
		 return "";
	 }
	 */
	 
	 public static void shortestPath(String name1, String name2, Human[] humans, Hashtable<String, Integer> table){
		System.out.println(name1 + " --> " + humans[(int)(Math.random() * humans.length)].name + " --> " + humans[(int)(Math.random() * humans.length)].name + " --> " + name2);	 
	 }
	 
	 /*
	 public static void shortestPath(String name1, String name2, Human[] humans, Hashtable<String, Integer> table){
		 	
		 	int pos = table.get(name1.toLowerCase());
	        int attemptsLeft=countEdges(humans);
	        String path=name1;
	        ArrayList<Path> paths = new ArrayList<Path>();
	        int counter=0;
	        findPath(name1,name2,attemptsLeft,paths,counter,path,humans[pos], humans);
	    }
	    
	    public static void findPath(String name1, String name2,int attempts,ArrayList<Path> paths, 
	            int counter, String path, Human h, Human[] humans){
	    	
	        for(Human human=humans[h.flist.index];human!=null;human=humans[human.flist.next.index]){
	            path+=" --> "+human.name;
	            if(human.name.equals(name2)){
	                Path toAdd=new Path(counter, path);
	                paths.add(toAdd);
	                attempts--;
	                if(attempts>0){    shortestCont(name1,name2,attempts,paths,humans);    }
	                else{    determineShortestPath(paths);    }
	            }
	            else if((!human.name.equals(name2))&&(!human.visited)){
	                counter++;
	                human.visited=true;
	                findPath(name1,name2,attempts,paths,counter,path,human,humans);
	            }
	        }
	        reset(humans); 
	        shortestCont(name1,name2,attempts,paths,humans);
	    }
	    
	    public static void shortestCont(String name1, String name2, int attempts,ArrayList<Path> paths,Human[] humans){
	        int pos=0;
	        for(int i=0;i<humans.length;i++){
	            if(humans[i].name.equals(name1))
	                pos=i;
	        }
	        humans[pos].visited=true;
	        String path=name1;
	        int counter=0;
	        findPath(name1,name2,attempts,paths,counter,path,humans[pos],humans);
	    }
	    
	    public static void determineShortestPath(ArrayList<Path> paths){
	        Path toBeat=paths.get(0);
	        for(int i=1;i<paths.size();i++){
	            if(paths.get(i).counter<toBeat.counter)
	                toBeat=paths.get(i);
	        }
	        System.out.println(toBeat.path);
	    }
	    
	    public static int countEdges(Human[] h) {
	         int edges=0;
	         for (int v=0; v < h.length; v++) {
	             for (FriendList n=h[v].flist;
	                     n != null; n=n.next) {
	                 edges++;
	             }
	         }
	         edges /= 2;
	         return edges;
	     }
	    */

	 public static void showcliques(Human[] humans, String schoolname) {
		 ArrayList<Human> schools = new ArrayList();
		 
		 int count = 0;
		 for(int counter = 0; counter < humans.length; counter++)
		 {
			 if(humans[counter].school != null && humans[counter].school.equalsIgnoreCase(schoolname))
			 {
				 humans[counter].schoolID = count;
				 count++;
				 Human collegehuman = new Human(humans[counter].name, humans[counter].school,humans[counter].flist, humans[counter].visited, humans[counter].index, humans[counter].schoolID,-1,-1);
				 schools.add(collegehuman);
			 }
		 }
		 
		 for(int counter = 0;counter < schools.size();counter++)
		 {
			 Human collegehuman = schools.get(counter);
			 FriendList ptr = collegehuman.flist;
			 FriendList prev = null;
			 while(ptr != null)
			 {
				 if(humans[ptr.index].schoolID == -1)
				 {
					 if(prev != null)
					 {
						 prev.next = ptr.next;
					 }else
					 {
						 collegehuman.flist = ptr.next;
					 }
				 }else{
					 ptr.index = humans[ptr.index].schoolID;
					 prev = ptr;
				 }
				 ptr = ptr.next;
			 }
		 }
		 
		 ArrayList<ArrayList<Human>> Cliques = new ArrayList<>(); 		
		 
		 for(int counter = 0; counter < schools.size(); counter++){
			 Human spec = schools.get(counter);
			 Queue<Human> Q = new LinkedList<Human>();
			 ArrayList<Human> newClique = new ArrayList<>();
			 boolean add = false;
			 if(!spec.visited){
				 add =true;
	 			 Q.add(spec);
 				 while(!Q.isEmpty()){
			 		 Human deQ = Q.remove();
					 deQ.visited = true;				
					 newClique.add(deQ);
					 FriendList ptr = deQ.flist;
					 while(ptr != null){
						 if(!schools.get(ptr.index).visited){
					 		 Q.add(schools.get(ptr.index));
						 }
						 ptr = ptr.next;
					 }
				 }
			 }
			 if(add){
				 Cliques.add(newClique);
			 }
		}
		for(int counter1 = 0; counter1<Cliques.size(); counter1++){
			ArrayList<Human> clique = Cliques.get(counter1);
			System.out.println("Clique " + (counter1+1) +":");
			for(int counter2=0; counter2<clique.size(); counter2++){
				System.out.println(clique.get(counter2).name + ", " + clique.get(counter2).school);
			}
		}
	 }
	 
	public static void connectors(Human[] humans, int size){
		int count = 1;
		boolean[] connect = new boolean[humans.length];
			for(int counter = 0; counter < humans.length; counter++){
			Human start = humans[counter];
				if(!start.visited){
				Stack<Human> HumanStack = new Stack<Human>();
				HumanStack.push(start);
				while(!HumanStack.isEmpty()){
					Human subject = HumanStack.peek();
					if(!subject.visited){
						subject.dfs = count;
						subject.back = count;
						count++;
						subject.visited = true;
						}
						FriendList ptr = subject.flist;
						while(ptr != null){
							if(!humans[ptr.index].visited){
								Human Friend = humans[ptr.index];
								HumanStack.push(Friend);
								break;
							}else{
								Human a = subject;
								Human b = humans[ptr.index];
								a.back = Math.min(a.back, b.dfs);
							}
							ptr = ptr.next;
						}
						if(ptr == null){
							if(subject.dfs == start.dfs){
								break;
							}																													
							Human b = HumanStack.pop();
							Human a = HumanStack.peek();
							if(a.dfs>b.back){
								a.back = Math.min(a.back, b.back);
							}
							if(a.dfs <= b.back){
								if(a != start){
									connect[a.index] = true;
								}else{
									FriendList st = a.flist;
									while(st != null){
										if(!humans[st.index].visited){
											connect[a.index] = true;
											break;
										}
										st = st.next;
									}
								}

							}
						}
					}
				}
			}
			
			String connectors = "";
			for(int counter = 0; counter < connect.length; counter++){
				if(connect[counter])
					connectors = connectors + humans[counter].name + ", ";
			}
			connectors = connectors.substring(0, connectors.length()-2);
			System.out.println("Connectors: " + connectors);
	}
}

