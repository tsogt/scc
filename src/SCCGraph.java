import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SCCGraph {
	FileReader fr;
	BufferedReader br;
	static int t=0;
	static int s=0;
//	int ii,sizeG;
	static int maxVal;
	HashMap<Integer,Boolean> explored = new HashMap<Integer, Boolean>();
	HashMap<Integer,Integer> leader = new HashMap<Integer, Integer>();
	static HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
	static HashMap<Integer,Integer> finishTime = new HashMap<Integer, Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SCCGraph obj = new SCCGraph();
		ArrayList<String> arr = obj.readFile2("sccTest");
		
		System.out.println("Read File");
//		maxVal = obj.findMax(arr);
//		System.out.println(maxVal);
		int i,j;
		for(i=1;i<=maxVal;i++) {
			obj.explored.put(i,false);
		}
		ArrayList<Integer>[] graphOrder = new ArrayList[maxVal+1];
		for(i=0;i<=maxVal;i++) {
			graphOrder[i] = new ArrayList<Integer>();
		}

		ArrayList<Integer>[] graphOrig = obj.adjacency(arr);
		System.out.println("adj");
		ArrayList<Integer>[] graphRev = obj.adjacencyRev(arr);
		System.out.println("adjRev");
		obj.DFSLoop(graphRev);
		System.out.println("DFS Loop on Rev");
		for(j=1;j<=maxVal;j++) {
//			graphOrder[finishTime.get(j)] = graphOrig[j];
			for(int k:graphOrig[j]) {
				graphOrder[finishTime.get(j)].add(finishTime.get(k));
			}			
			
			
		}
		
		
		for(i=1;i<=maxVal;i++) {
			obj.explored.put(i,false);
		}		
		obj.DFSLoop(graphOrder);
		System.out.println("DFS Loop on Ordered");
//display leader		
/*		for(Map.Entry m:SCCGraph.leader.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
*/		
		
//Count SCC size

		int tmp=0, leadNode;
//		HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
		for(i=1;i<=maxVal;i++) {
			leadNode = obj.leader.get(i);
			if(leaders.containsKey(leadNode)) {
				tmp = leaders.get(leadNode);
				tmp++;
				leaders.put(leadNode, tmp);
			}
			else {
				leaders.put(leadNode, 1);
			}			
		}

//		Print SCCs
		int maxSCC = 0,maxKey = 0;
		for(Map.Entry m:leaders.entrySet()) {
//			System.out.println(m.getKey() + ": " + m.getValue());
			if(maxSCC < (int)m.getValue()) {
				maxSCC = (int)m.getValue();
				maxKey = (int)m.getKey();
			}
		}
		
		System.out.println(maxKey + ": " + maxSCC);
		leaders.remove(maxKey);
		maxSCC = 0;maxKey = 0;
		for(Map.Entry n:leaders.entrySet()) {
//			System.out.println(m.getKey() + ": " + m.getValue());
			if(maxSCC < (int)n.getValue()) {
				maxSCC = (int)n.getValue();
				maxKey = (int)n.getKey();
			}
		}
		
		System.out.println(maxKey + ": " + maxSCC);
		leaders.remove(maxKey);

		maxSCC = 0;maxKey = 0;
		for(Map.Entry n:leaders.entrySet()) {
//			System.out.println(m.getKey() + ": " + m.getValue());
			if(maxSCC < (int)n.getValue()) {
				maxSCC = (int)n.getValue();
				maxKey = (int)n.getKey();
			}
		}
		
		System.out.println(maxKey + ": " + maxSCC);
		leaders.remove(maxKey);

		maxSCC = 0;maxKey = 0;
		for(Map.Entry n:leaders.entrySet()) {
//			System.out.println(m.getKey() + ": " + m.getValue());
			if(maxSCC < (int)n.getValue()) {
				maxSCC = (int)n.getValue();
				maxKey = (int)n.getKey();
			}
		}
		
		System.out.println(maxKey + ": " + maxSCC);
		leaders.remove(maxKey);

		maxSCC = 0;maxKey = 0;
		for(Map.Entry n:leaders.entrySet()) {
//			System.out.println(m.getKey() + ": " + m.getValue());
			if(maxSCC < (int)n.getValue()) {
				maxSCC = (int)n.getValue();
				maxKey = (int)n.getKey();
			}
		}
		
		System.out.println(maxKey + ": " + maxSCC);
		leaders.remove(maxKey);

//checking values by displaying		
/*		for(j=1;j<=maxVal;j++) {
			for(int k:graphOrder[j]) {
				System.out.println(j + ": " + k);
			}			
		}
		System.out.println("map:");
		for(Map.Entry m:SCCGraph.finishTime.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
*/
	}
	public void DFSLoop(ArrayList<Integer>[] Graph) {
		int i;
		t=0;
		for(i=maxVal;i>0;i--) {
			if(explored.get(i)==false) {
				s=i;
				DFS(Graph, i);
			}
		}
	}
	public void DFS(ArrayList<Integer>[] Graph, int node) {
		explored.put(node,true);
		leader.put(node, s);
		int sizeG = Graph[node].size();
		int ii;
		for(ii=0;ii<sizeG;ii++) {
			if(explored.get(Graph[node].get(ii))==false) {
				DFS(Graph,Graph[node].get(ii));
			}
		}
		t++;
		finishTime.put(node, t);
		
	}
	public ArrayList<Integer>[] adjacencyRev(ArrayList<String> fileList) {
		
		int len = fileList.size();
		int i,j;
//		for(i=0;i<len;i++) {
//			for(j=0;j<2;j++) {
//				if(maxVal < Integer.parseInt(matStr[i][j])) {
//					maxVal = Integer.parseInt(matStr[i][j]);
//				}
//			}
//		}
		
		ArrayList[] adjMat= new ArrayList[maxVal+1];
			
		for(j=0;j<=maxVal;j++) {
			adjMat[j] = new ArrayList<Integer>();
		}
		String[] col = new String[2];
		for(j=0;j<len;j++) {
			col = fileList.get(j).split(" ");
			adjMat[Integer.parseInt(col[1])].add(Integer.parseInt(col[0]));
		}
//		System.out.println(adjMat[10].size());
		
//		for(int k:adjMat[10]) {
//			System.out.println(k);
//		}
		return adjMat;
	}

	public ArrayList<Integer>[] adjacency(ArrayList<String> fileList) {
			
		int len = fileList.size();
		int i,j;
//		for(i=0;i<len;i++) {
//			for(j=0;j<2;j++) {
//				if(maxVal < Integer.parseInt(matStr[i][j])) {
//					maxVal = Integer.parseInt(matStr[i][j]);
//				}
//			}
//		}
		
		ArrayList<Integer>[] adjMat= new ArrayList[maxVal+1];
			
		for(j=0;j<=maxVal;j++) {
			adjMat[j] = new ArrayList<Integer>();
		}
		System.out.println("declared adjMat");
//		String[] strArr = new String[len];
//		String[][] ind = new String[len][2];
//		int col[] = new int[2];
//		for(j=0;j<len;j++) {
//			strArr[j]= fileList.get(j);
//			ind[j] = fileList.get(j).split(" ");
//			ind[j][0] = 
//			ind[j][1] = Integer.parseInt(col[1]);
			
//		}
//		System.out.println("copied to array");
		
		for(j=0;j<len;j++) {
//			col = fileList.get(j).split(" ");
//			col = strArr[j].split(" ");
			adjMat[Integer.parseInt(fileList.get(j).split(" ")[0])].add(Integer.parseInt(fileList.get(j).split(" ")[1]));
//			adjMat[ind[j][0]].add(ind[j][1]);
		}
//		System.out.println(adjMat[10].size());
		
//		for(int k:adjMat[10]) {
//			System.out.println(k);
//		}
		return adjMat;
	}
	
	public int findMax(int[][] matStr) {
		int len = Array.getLength(matStr);
		int i,j,maxVal = 0;
		for(i=0;i<len;i++) {
			for(j=0;j<2;j++) {
				if(maxVal < matStr[i][j]) {
					maxVal = matStr[i][j];
				}
			}
		}
		return maxVal;
	}
	public int[][] readFile(String filename) {
		try {
			fr = new FileReader("/home/stark/Documents/Workspace/java/" + filename + ".txt");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {		
			String i;
			
			List<String> fileList = new ArrayList<String>();
			
			while(true) {				
				i = br.readLine();
				if (i == null)
					break;
			
				fileList.add(i);
				

			}
			br.close();
			fr.close();
			System.out.println("finished reading");
			int[][] simpleArray = new int[ fileList.size() ][2];
			int graphLen = fileList.size();
			int j;
			String col[] = new String[2];
//			String[] col = new String[2];
			for(j=0;j<graphLen;j++) {
				
				col = fileList.get(j).split(" ");
				simpleArray[j][0] = Integer.parseInt(col[0]);				
				simpleArray[j][1] = Integer.parseInt(col[1]);		
				
			}
			
			
			return simpleArray;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public ArrayList<String> readFile2(String filename) {
		try {
			fr = new FileReader("/home/stark/Documents/Workspace/java/" + filename + ".txt");
			br = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {		
			String i;
			
			ArrayList<String> fileList = new ArrayList<String>();
			
			while(true) {				
				i = br.readLine();
				if (i == null)
					break;
			
				fileList.add(i);
				

			}
			br.close();
			fr.close();
			
			
			int graphLen = fileList.size();
			int j;
			String col[] = new String[2];
//			String[] col = new String[2];
//			int maxVal = 0;
			for(j=0;j<graphLen;j++) {
				
				col = fileList.get(j).split(" ");
				if(maxVal < Integer.parseInt(col[0])) {
					maxVal = Integer.parseInt(col[0]);
				}
				if(maxVal < Integer.parseInt(col[1])) {
					maxVal = Integer.parseInt(col[1]);
				}
				
			}
					
			return fileList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
