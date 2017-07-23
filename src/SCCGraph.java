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
	static int maxVal;
	static HashMap<Integer,Boolean> explored = new HashMap<Integer, Boolean>();
	static HashMap<Integer,Integer> leader = new HashMap<Integer, Integer>();
	static HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
	static HashMap<Integer,Integer> finishTime = new HashMap<Integer, Integer>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SCCGraph obj = new SCCGraph();
		int[][] arr = obj.readFile("sccmini");
//		System.out.println(Array.getLength(arr));
		maxVal = obj.findMax(arr);
		System.out.println(maxVal);
		int i,j;
		for(i=1;i<=maxVal;i++) {
			explored.put(i,false);
		}
		ArrayList<Integer>[] graphOrder = new ArrayList[maxVal+1];
		for(i=0;i<=maxVal;i++) {
			graphOrder[i] = new ArrayList<Integer>();
		}

		ArrayList<Integer>[] graphOrig = obj.adjacency(arr);
		ArrayList<Integer>[] graphRev = obj.adjacencyRev(arr);
		obj.DFSLoop(graphRev);
		for(j=1;j<=maxVal;j++) {
//			graphOrder[finishTime.get(j)] = graphOrig[j];
			for(int k:graphOrig[j]) {
				graphOrder[finishTime.get(j)].add(finishTime.get(k));
			}			
			
			
		}
		
		
		for(i=1;i<=maxVal;i++) {
			explored.put(i,false);
		}		
		obj.DFSLoop(graphOrder);
//display leader		
/*		for(Map.Entry m:SCCGraph.leader.entrySet()) {
			System.out.println(m.getKey() + ": " + m.getValue());
		}
*/		
		
//Count SCC size

		int tmp=0, leadNode;
//		HashMap<Integer,Integer> leaders = new HashMap<Integer, Integer>();
		for(i=1;i<=maxVal;i++) {
			leadNode = leader.get(i);
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
		for(int i:Graph[node]) {
			if(explored.get(i)==false) {
				DFS(Graph,i);
			}
		}
		t++;
		finishTime.put(node, t);
		
	}
	public ArrayList<Integer>[] adjacencyRev(int[][] matStr) {
		
		int len = Array.getLength(matStr);
		int i,j;
//		for(i=0;i<len;i++) {
//			for(j=0;j<2;j++) {
//				if(maxVal < Integer.parseInt(matStr[i][j])) {
//					maxVal = Integer.parseInt(matStr[i][j]);
//				}
//			}
//		}
//		System.out.println(maxVal);
		ArrayList<Integer>[] adjMat= new ArrayList[maxVal+1];
		for(i=0;i<=maxVal;i++) {
			adjMat[i] = new ArrayList<Integer>();
		}

		for(i=0;i<len;i++) {
			adjMat[matStr[i][1]].add(matStr[i][0]);
		}
//		System.out.println(adjMat[10].size());
		
//		for(int k:adjMat[10]) {
//			System.out.println(k);
//		}
		return adjMat;
	}

	public ArrayList<Integer>[] adjacency(int[][] matStr) {
			
		int len = Array.getLength(matStr);
		int i,j;
//		for(i=0;i<len;i++) {
//			for(j=0;j<2;j++) {
//				if(maxVal < Integer.parseInt(matStr[i][j])) {
//					maxVal = Integer.parseInt(matStr[i][j]);
//				}
//			}
//		}
		
		ArrayList<Integer>[] adjMat= new ArrayList[maxVal+1];
		for(i=1;i<=maxVal;i++) {
			adjMat[i] = new ArrayList<Integer>();
		}

		for(i=0;i<len;i++) {
			adjMat[matStr[i][0]].add(matStr[i][1]);
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
	
	public int[][] readFile2(String filename) {
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
			int maxVal;
			for(j=0;j<graphLen;j++) {
				
				col = fileList.get(j).split(" ");
				
			}
			
			
			return simpleArray;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
