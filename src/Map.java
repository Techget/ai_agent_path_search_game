import java.util.*;

//use hash map store map
public class Map {
	private HashMap< MapCoordinate, Character> storedMap;
	private MapCoordinate goldLocation;
	private MapCoordinate axeLocation;
	private MapCoordinate keyLocation;	
	private ArrayList<MapCoordinate> nStonesLocation; 
	
	private boolean agentHasKey;
	private boolean agentHasAxe;
	private int agentHasNStones;
	
	public HashMap<MapCoordinate, Character> getStoredMap() {
		return storedMap;
	}

	public void setStoredMap(HashMap<MapCoordinate, Character> storedMap) {
		this.storedMap = storedMap;
	}

	public MapCoordinate getGoldLocation() {
		return goldLocation;
	}

	public void setGoldLocation(MapCoordinate goldLocation) {
		this.goldLocation = goldLocation;
	}

	public MapCoordinate getAxeLocation() {
		return axeLocation;
	}

	public void setAxeLocation(MapCoordinate axeLocation) {
		this.axeLocation = axeLocation;
	}

	public MapCoordinate getKeyLocation() {
		return keyLocation;
	}

	public void setKeyLocation(MapCoordinate keyLocation) {
		this.keyLocation = keyLocation;
	}

	public ArrayList<MapCoordinate> getnStonesLocation() {
		return nStonesLocation;
	}

	public void setnStonesLocation(ArrayList<MapCoordinate> nStonesLocation) {
		this.nStonesLocation = nStonesLocation;
	}

	public boolean isAgentHasKey() {
		return agentHasKey;
	}

	public void setAgentHasKey(boolean agentHasKey) {
		this.agentHasKey = agentHasKey;
	}

	public boolean isAgentHasAxe() {
		return agentHasAxe;
	}

	public void setAgentHasAxe(boolean agentHasAxe) {
		this.agentHasAxe = agentHasAxe;
	}

	public int getAgentHasNStones() {
		return agentHasNStones;
	}

	public void setAgentHasNStones(int agentHasNStones) {
		this.agentHasNStones = agentHasNStones;
	}

	public Map(){
		storedMap=new HashMap<MapCoordinate,Character>();
		storedMap.put(new MapCoordinate(0,0),' ');  //add the start point to map
		goldLocation=null;
		axeLocation=null;
		keyLocation=null;
		nStonesLocation=new ArrayList<MapCoordinate>();
		
		agentHasKey=false;
		agentHasAxe=false;
		agentHasNStones=0;
	}
	
	/*
	 * @precondition, input map coordinate has been recorded
	 * */
	public char getSymbol(MapCoordinate mc){
		return storedMap.get(mc);
	}
	public char getSymbol(int x,int y){
		MapCoordinate temp=new MapCoordinate(x,y);
		//System.out.println("contains"+storedMap.containsKey(temp));
		System.out.println("getSymbol() location:"+temp.getX()+" "+temp.getY());
		return storedMap.get(temp);
	}
	
	public void setMapCell(MapCoordinate mc,char symbol){
		storedMap.put(mc, symbol);
	}
	public void setMapCell(int x,int y,char symbol){
		setMapCell(new MapCoordinate(x,y),symbol);
	}
	
	public boolean hasCoordinate(MapCoordinate mc){
		if(storedMap.containsKey(mc)){
			return true;
		}else{ 
			return false;
		}
	}
	public boolean hasCoordinate(int x,int y){
		MapCoordinate mc=new MapCoordinate(x,y);
		if(hasCoordinate(mc)){
			return true;
		}else{
			return false;
		}
	}
	private void addToMap(int x,int y,char symbol){
		if(symbol=='g'){
			MapCoordinate mc=new MapCoordinate(x,y);
			storedMap.put(mc, symbol);
			this.goldLocation=mc;
		}else if(symbol=='k'){
			MapCoordinate mc=new MapCoordinate(x,y);
			storedMap.put(mc, symbol);
			this.keyLocation=mc;
		}else if(symbol=='a'){
			MapCoordinate mc=new MapCoordinate(x,y);
			storedMap.put(mc, symbol);
			this.axeLocation=mc;
		}else if(symbol=='o'){
			MapCoordinate mc=new MapCoordinate(x,y);
			storedMap.put(mc, symbol);
			this.nStonesLocation.add(mc);
		}else{
			MapCoordinate mc=new MapCoordinate(x,y);
			storedMap.put(mc, symbol);
		}
	}
	
	public void UpdateMap(MapCoordinate agentLocation,char[][] view){
		for(int i=2;i>=-2;i--){
			for(int j=2;j>=-2;j--){
				if(!hasCoordinate(agentLocation.getX()-j,agentLocation.getY()+i)){ //haven't add this coordinate into map
					//if(i==0&&j==0){
					//	continue;
					//}else{
						addToMap(agentLocation.getX()-j,agentLocation.getY()+i,view[2-i][2-j]);// a little index trick
					//}
				}
			}
		}
		for(MapCoordinate mc:storedMap.keySet()){
			System.out.println(mc.getX()+" "+mc.getY()+": "+storedMap.get(mc)+"."+(int)storedMap.get(mc));
		}
	}
	
	public ArrayList<MapCoordinate> goldReachable(MapCoordinate agentLocation){ //return route
		System.out.println("invoke goldReachable method in Map");
		if(goldLocation==null){
			System.out.println("return null in gold reachable");
			return null;
		}else{
			return reachable(agentLocation,this.goldLocation);
		}
	}
	public ArrayList<MapCoordinate> axeReachable(MapCoordinate agentLocation){ //return route
		System.out.println("invoke axeReachable method in Map");
		if(this.axeLocation==null){
			return null;
		}else{
			return reachable(agentLocation,this.axeLocation);
		}
	}
	public ArrayList<MapCoordinate> keyReachable(MapCoordinate agentLocation){ //return route
		System.out.println("invoke keyReachable method in Map");
		if(this.keyLocation==null){
			return null;
		}else{
			return reachable(agentLocation,this.keyLocation);
		}
	}
	/*public ArrayList<MapCoordinate> stonesReachable(MapCoordinate agentLocation){// return all reachable stones
		
	}*/
	public ArrayList<MapCoordinate> stonesReachable(MapCoordinate agentLocation){
		System.out.println("invoke stoneReachable method in Map");
		if(this.nStonesLocation.isEmpty()){
			return null;
		}else{
			for(MapCoordinate mc:nStonesLocation){
				ArrayList<MapCoordinate> tempA=reachable(agentLocation,mc);
				if(tempA!=null && !tempA.isEmpty()){
					return tempA;
				}
			}
			return null;
		}
	}
	private ArrayList<MapCoordinate> reachable(MapCoordinate loc1,MapCoordinate loc2){
		return new BFSSearchMap().bfsSearch(loc1, loc2, this);
	}
	
}
