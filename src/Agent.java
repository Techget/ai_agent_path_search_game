/*********************************************
 *  Agent.java 
 *  Sample Agent for Text-Based Adventure Game
 *  COMP3411 Artificial Intelligence
 *  UNSW Session 1, 2016
*/
// able to figure out s0~s6
import java.util.*;
import java.io.*;
import java.net.*;

public class Agent {
	private boolean hasGold;
	private boolean hasAxe;
	private boolean hasKey;
	private int nStone;
	private MapCoordinate currentLocation;
	private char currentDirection; 
	private Map map;
	
	private Stack<MapCoordinate> stack; //used when roaming,but recorded every 'f'
	private ArrayList<MapCoordinate> hasBeenTo; //used when roaming ,but recorded every 'f'
	
	public Agent(){
		hasGold=false;
		hasAxe=false;
		hasKey=false;
		nStone=0;
		currentLocation=new MapCoordinate(0,0);
		currentDirection='^';
		map=new Map();
		//stackStates=new LinkedList<State>();
		hasBeenTo=new ArrayList<MapCoordinate>();
		//hasBeenTo.add(this.currentLocation);
		stack=new Stack<MapCoordinate>();
		//stack.push(currentLocation); //add currentLocation(0,0) to hasBeenTo and stack,it will be pushed twice if 
		//here push
	}
	
	public boolean getHasGold(){
		return this.hasGold;
	}
	public boolean getHasAxe(){
		return this.hasAxe;
	}
	public boolean getHasKey(){
		return this.hasKey;
	}
	public int getPossessStone(){
		return this.nStone;
	}
	public MapCoordinate getCurrentLocation(){
		return this.currentLocation;
	}
	public char getCurrentDirection(){
		return this.currentDirection;
	}
/*	public LinkedList<State> getStack(){
		return this.stackStates;
	}*/
	
	
	public static ArrayList<MapCoordinate> designatedRoute=new ArrayList<MapCoordinate>();
	//keyword:this means agent
	public char get_action( char view[][] ) {
		//following 4 if clause,check whether it reach some tool point,if get some tool,set agent and map state
		//and update map on these specified location.
		/*if(map.hasCoordinate(-15,-3)){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("-7,-9 zai zhe ne:"+map.getSymbol(-15, -3));
		}*/
		if(map.getStoredMap().size()>0){
			if(map.getAxeLocation()!=null && this.currentLocation.equals(map.getAxeLocation())){
				this.hasAxe=true; 
				map.getStoredMap().put(this.currentLocation,' ');
				//map.setAxeLocation(null);
				map.setAgentHasAxe(true);
				map.setAxeLocation(null);
			}
			if(map.getKeyLocation()!=null && this.currentLocation.equals(map.getKeyLocation())){
				this.hasKey=true;
				map.getStoredMap().put(this.currentLocation,' ');
				map.setAgentHasKey(true);
				map.setKeyLocation(null);
			}
			if(map.getGoldLocation()!=null && this.currentLocation.equals(map.getGoldLocation())){
				//if get gold, return to (0,0) directly
				this.hasGold=true;
				map.getStoredMap().put(this.currentLocation,' ');
				designatedRoute.clear(); //clear the designated route the empty
				System.out.println("find Gold");
				map.setGoldLocation(null);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				designatedRoute.addAll(new BFSSearchMap().bfsSearch(this.currentLocation, new MapCoordinate(0,0),this.map));
				//designatedRoute.remove(0);
				return moveAccordingToDesignatedRoute();
			}
			if(!map.getnStonesLocation().isEmpty() &&map.getnStonesLocation().contains(this.currentLocation)){
				this.nStone++;
				map.setAgentHasNStones(this.nStone);
				map.getnStonesLocation().remove(this.currentLocation);
				map.getStoredMap().put(this.currentLocation, ' ');
			}
		}
		//System.out.println("here11111");
		//update the map if something new come up
		//have to update map for every view
		/*if(!map.hasCoordinate(this.currentLocation.getX()+2,this.currentLocation.getY()+2)||
				!map.hasCoordinate(this.currentLocation.getX()+2,this.currentLocation.getY()-2)||
				!map.hasCoordinate(this.currentLocation.getX()-2,this.currentLocation.getY()+2)||
				!map.hasCoordinate(this.currentLocation.getX()-2,this.currentLocation.getY()-2)){*/
			if(this.currentDirection=='^'){
				map.UpdateMap(this.currentLocation, view);  //update the map
				//System.out.println("update map");
			}else if(this.currentDirection=='<'){
				view=rotateRView(view);
				map.UpdateMap(this.currentLocation, view);
			}else if(this.currentDirection=='v'){
				view=rotateRView(rotateRView(view));
				map.UpdateMap(this.currentLocation, view);
			}else if(this.currentDirection=='>'){
				view=rotateRView(rotateRView(rotateRView(view)));
				map.UpdateMap(this.currentLocation, view);
			}
		//}
		//System.out.println("here22222");
		//if there is a designated route, walk through that path first
		if(!designatedRoute.isEmpty()){
			/*MapCoordinate mcTemp=designatedRoute.get(0); //next step
			if(!this.hasBeenTo.contains(mcTemp)){
				hasBeenTo.add(new MapCoordinate(mcTemp));
			}*/
			/*for(MapCoordinate mc:designatedRoute){
				
				System.out.print("mc:"+mc.getX()+" "+mc.getY()+"->");
				
			}*/
			return moveAccordingToDesignatedRoute();
		}
		
		//if able to find useful path ,go along that path,else roaming
		//System.out.println("question gold reachability");
		if(!this.hasGold){//can find path to gold
			ArrayList<MapCoordinate> tempA=map.goldReachable(this.currentLocation);
			if(tempA!=null&&!tempA.isEmpty()){
				System.out.println("find gold location and reachable");
				designatedRoute.clear();
				designatedRoute.addAll(tempA);
				//designatedRoute.remove(0); //the first node is itself//do not need to remove any node
				return moveAccordingToDesignatedRoute();
			}
		} //the reason cannot use else if is if go for gold comes out with nothing, still need to go for other tool
		if(!this.hasKey ){//use lazy check to improve efficiency
			ArrayList<MapCoordinate> tempA=map.keyReachable(this.currentLocation);
			if(tempA!=null&&!tempA.isEmpty()){
				designatedRoute.clear();
				designatedRoute.addAll(tempA);
				//designatedRoute.remove(0);
				return moveAccordingToDesignatedRoute();
			}
		}
		if(!this.hasAxe){
			ArrayList<MapCoordinate> tempA=map.axeReachable(this.currentLocation);
			if(tempA!=null&&!tempA.isEmpty()){
				designatedRoute.clear();
				designatedRoute.addAll(tempA);
				//designatedRoute.remove(0);
				return moveAccordingToDesignatedRoute();
			}
		}
		if(!map.getnStonesLocation().isEmpty() ){ 
			//designatedRoute=null;
			ArrayList<MapCoordinate> tempA=map.stonesReachable(this.currentLocation);//return the route
			
			if(tempA!=null && !tempA.isEmpty()){
				designatedRoute.clear();
				designatedRoute.addAll(tempA);
				//designatedRoute.remove(0);
				return moveAccordingToDesignatedRoute();
			}
			
		}  
		//roaming, but do not go across the river		
		return roamAgent();
		
	      // REPLACE THIS CODE WITH AI TO CHOOSE ACTION		   
	
	     /* int ch=0;
	
	      System.out.print("Enter Action(s): ");
	
	      try {
	         while ( ch != -1 ) {
	            // read character from keyboard
	            ch  = System.in.read();
	
	            switch( ch ) { // if character is a valid action, return it
	            case 'F': case 'L': case 'R': case 'C': case 'U':
	            case 'f': case 'l': case 'r': case 'c': case 'u':
	               return((char) ch );
	            }
	         }
	      }
	      catch (IOException e) {
	         System.out.println ("IO error:" + e );
	      }
	
	      return 0;*/
	   }
	/* move according to designated route*/
	private char moveAccordingToDesignatedRoute(){
		System.out.println("come into move Accordingto designated route");
		System.out.println("currentLocation: "+this.currentLocation.getX()+" "+this.currentLocation.getY());
		MapCoordinate mcTemp=designatedRoute.get(0); //next step NEXT STEP
		System.out.println("next step: "+mcTemp.getX()+" "+mcTemp.getY());
		for(MapCoordinate mc:designatedRoute){
			//System.out.println("print returned route in bfssearch");
			System.out.print("mc:"+mc.getX()+" "+mc.getY()+"->");
			
		}
		if(!this.hasBeenTo.contains(mcTemp)){
			hasBeenTo.add(new MapCoordinate(mcTemp));
		}
		if(mcTemp.getX()-this.currentLocation.getX()==1){//means move toward east
			if(this.currentDirection!='>'){
				char tempC=changeDirection(this.currentDirection,'>');
				execRotateUpdateAgent(tempC);
				return tempC;
			}else{
				if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='-'){
					execUUpdateAgent();
					return 'u';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='T'){
					execCUpdateAgent();
					return 'c';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='~'){
					this.nStone--;
					this.map.setAgentHasNStones(this.nStone);
					this.map.setMapCell(mcTemp, 'O'); //really walk through the river
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}else{ //means ' ' or 'O'
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}
			}
		}else if(mcTemp.getX()-this.currentLocation.getX()==-1){
			//move toward left
			if(this.currentDirection!='<'){
				char tempC=changeDirection(this.currentDirection,'<');
				execRotateUpdateAgent(tempC);
				return tempC;
			}else{
				if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='-'){
					execUUpdateAgent();
					return 'u';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='T'){
					execCUpdateAgent();
					return 'c';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='~'){
					this.nStone--;
					this.map.setAgentHasNStones(this.nStone);
					this.map.setMapCell(mcTemp, 'O'); //really walk through the river
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}else{ //means ' ' or 'O'
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}
			}
		}else if(mcTemp.getY()-this.currentLocation.getY()==1){
			//move toward up
			if(this.currentDirection!='^'){
				char tempC=changeDirection(this.currentDirection,'^');
				execRotateUpdateAgent(tempC);
				return tempC;
			}else{
				if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='-'){
					execUUpdateAgent();
					return 'u';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='T'){
					execCUpdateAgent();
					return 'c';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='~'){
					this.nStone--;
					this.map.setAgentHasNStones(this.nStone);
					this.map.setMapCell(mcTemp, 'O'); //really walk through the river
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}else{
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}
			}
		}else if(mcTemp.getY()-this.currentLocation.getY()==-1){
			//move toward down
			if(this.currentDirection!='v'){
				char tempC=changeDirection(this.currentDirection,'v');
				execRotateUpdateAgent(tempC);
				return tempC;
			}else{
				if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='-'){
					execUUpdateAgent();
					return 'u';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='T'){
					execCUpdateAgent();
					return 'c';
				}else if(designatedRoute.size()>=1&&map.getSymbol(mcTemp)=='~'){
					this.nStone--;
					this.map.setAgentHasNStones(this.nStone);
					this.map.setMapCell(mcTemp, 'O'); //really walk through the river
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}else{
					execFUpdateAgent();
					designatedRoute.remove(0);
					//this.hasBeenTo.add(this.currentLocation);
					return 'f';
				}
			}
		}else{
			System.out.println("error happen at moveAccordingToDesignatedRoute");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 
				e.printStackTrace();
			}
			return 0;
		}
	}
	/*update the agent field*/
	//everytime when you actually move to next cell, record you've been to this cell
	private void execFUpdateAgent(){ 
		if(this.currentDirection=='^'){
			this.currentLocation.setY(1);
			if(!this.hasBeenTo.contains(new MapCoordinate(this.currentLocation))){
				this.hasBeenTo.add(new MapCoordinate(this.currentLocation));
			}
			this.stack.push(new MapCoordinate(this.currentLocation));
		}else if(this.currentDirection=='v'){
			this.currentLocation.setY(-1);
			if(!this.hasBeenTo.contains(new MapCoordinate(this.currentLocation))){
				this.hasBeenTo.add(new MapCoordinate(this.currentLocation));
			}
			this.stack.push(new MapCoordinate(this.currentLocation));
		}else if(this.currentDirection=='>'){
			this.currentLocation.setX(1);
			if(!this.hasBeenTo.contains(new MapCoordinate(this.currentLocation))){
				this.hasBeenTo.add(new MapCoordinate(this.currentLocation));
			}
			this.stack.push(new MapCoordinate(this.currentLocation));
		}else if(this.currentDirection=='<'){
			this.currentLocation.setX(-1);
			if(!this.hasBeenTo.contains(new MapCoordinate(this.currentLocation))){
				this.hasBeenTo.add(new MapCoordinate(this.currentLocation));
			}
			this.stack.push(new MapCoordinate(this.currentLocation));
		}
	}
	/*update when rotate*/
	private void execRotateUpdateAgent(char c){ 
		if(c=='l'||c=='L'){
			if(this.currentDirection=='>'){
				this.currentDirection='^';
			}else if(this.currentDirection=='^'){
				this.currentDirection='<';
			}else if(this.currentDirection=='<'){
				this.currentDirection='v';
			}else{
				this.currentDirection='>';
			}
		}else{
			if(this.currentDirection=='>'){
				this.currentDirection='v';
			}else if(this.currentDirection=='^'){
				this.currentDirection='>';
			}else if(this.currentDirection=='<'){
				this.currentDirection='^';
			}else{
				this.currentDirection='<';
			}
		}
	}
	private void execCUpdateAgent(){ //update map
		if(this.currentDirection=='^'){
			map.setMapCell(this.currentLocation.getX(),this.currentLocation.getY()+1, ' ');
		}else if(this.currentDirection=='>'){
			map.setMapCell(this.currentLocation.getX()+1,this.currentLocation.getY(), ' ');
		}else if(this.currentDirection=='<'){
			map.setMapCell(this.currentLocation.getX()-1,this.currentLocation.getY(), ' ');
		}else if(this.currentDirection=='v'){
			map.setMapCell(this.currentLocation.getX(),this.currentLocation.getY()-1, ' ');
		}else{
			System.out.println("error happens in execCUpadateAgent");
		}			
	}
	private void execUUpdateAgent(){ //update map
		if(this.currentDirection=='^'){
			map.setMapCell(this.currentLocation.getX(),this.currentLocation.getY()+1, ' ');
		}else if(this.currentDirection=='>'){
			map.setMapCell(this.currentLocation.getX()+1,this.currentLocation.getY(), ' ');
		}else if(this.currentDirection=='<'){
			map.setMapCell(this.currentLocation.getX()-1,this.currentLocation.getY(), ' ');
		}else if(this.currentDirection=='v'){
			map.setMapCell(this.currentLocation.getX(),this.currentLocation.getY()-1, ' ');
		}else{
			System.out.println("error happens in execUUpadateAgent");
		}	
	}
	
	private char changeDirection(char dirnStart,char dirnEnd ){
		if(dirnStart=='^'&&dirnEnd=='>'){
			return 'r';
		}else if(dirnStart=='^'&&dirnEnd=='<'){
			return 'l';
		}else if(dirnStart=='^'&&dirnEnd=='v'){
			return 'r';
		}else if(dirnStart=='>'&&dirnEnd=='^'){
			return 'l';
		}else if(dirnStart=='>'&&dirnEnd=='<'){
			return 'l';
		}else if(dirnStart=='>'&&dirnEnd=='v'){
			return 'r';
		}else if(dirnStart=='v'&&dirnEnd=='^'){
			return 'l';
		}else if(dirnStart=='v'&&dirnEnd=='>'){
			return 'l';
		}else if(dirnStart=='v'&&dirnEnd=='<'){
			return 'r';
		}else if(dirnStart=='<'&&dirnEnd=='^'){
			return 'r';
		}else if(dirnStart=='<'&&dirnEnd=='>'){
			return 'r';
		}else if(dirnStart=='<'&&dirnEnd=='v'){
			return 'l';
		}else{
			System.out.println("error happens at changeDirection");
			return 'e';
		}
	}
	
	/*change index not content*/
	private char[][] rotateRView(char arr[][]){ 
		//first change the dimensions vertical length for horizontal length
        //and vice versa
        char[][] newArray = new char[arr[0].length][arr.length];

        //invert values 90 degrees clockwise by starting from button of
        //array to top and from left to right
        
        for(int i=0; i<arr[0].length; i++){
            for(int j=0; j<arr.length;j++){
                newArray[i][j] =arr[j][arr.length-1-i];
            }
        }
        
        return newArray;
	}
	
	//return moveAccordingToDesignatedRoute
	private char roamAgent(){
		System.out.println("come into roamAgent()");
		
		if(stack.size()==1||stack.size()==0){ //means go back to original point, the use of prevLocation has NullPointerException
			System.out.println("come into roamAgent() if");
			
			map.getStoredMap().put(currentLocation, ' '); //notice
			
			char symbnorth=this.map.getSymbol(this.currentLocation.getX(),this.currentLocation.getY()+1);
			char symbsouth=this.map.getSymbol(this.currentLocation.getX(),this.currentLocation.getY()-1);
			char symbeast=this.map.getSymbol(this.currentLocation.getX()+1,this.currentLocation.getY());			
			char symbwest=this.map.getSymbol(this.currentLocation.getX()-1,this.currentLocation.getY());
		
			MapCoordinate maybeNextLocation=null;
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX(),this.currentLocation.getY()+1); //maybe north
			if(!this.hasBeenTo.contains(maybeNextLocation)&&symbnorth!='*'&&symbnorth!='~'){
				if(symbnorth=='T'){ 
					if(this.hasAxe){ //means could be there,has axe have some effect
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbnorth=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbnorth==' '||symbnorth=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX(),this.currentLocation.getY()-1); //maybe south
			if(!this.hasBeenTo.contains(maybeNextLocation)&&symbsouth!='*'&&symbsouth!='~'){
				if(symbsouth=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbsouth=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbsouth==' '||symbsouth=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX()+1,this.currentLocation.getY()); //maybe east
			if(!this.hasBeenTo.contains(maybeNextLocation)&&symbeast!='*'&&symbeast!='~'){
				if(symbeast=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbeast=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbeast==' '||symbeast=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX()-1,this.currentLocation.getY()); //maybe west
			if(!this.hasBeenTo.contains(maybeNextLocation)&&symbwest!='*'&&symbwest!='~'){
				if(symbwest=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbwest=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbwest==' '||symbwest=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			//locate at original point but has nowhere to roam, means some thing wrong
			System.out.println("something wrong with roamAgent(),maybe need to roam across river");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 
				e.printStackTrace();
			}
			return 0;
		}else{ //could use prevLocation
			System.out.println("come into roamAgent() else");
			
			map.getStoredMap().put(currentLocation, ' '); //notice
			
			char symbnorth=this.map.getSymbol(this.currentLocation.getX(),this.currentLocation.getY()+1);
			char symbsouth=this.map.getSymbol(this.currentLocation.getX(),this.currentLocation.getY()-1);
			char symbeast=this.map.getSymbol(this.currentLocation.getX()+1,this.currentLocation.getY());			
			char symbwest=this.map.getSymbol(this.currentLocation.getX()-1,this.currentLocation.getY());
			
			System.out.println("currentDirection: "+this.currentDirection+"symbwest: "+symbwest+"symbnorth: "+symbnorth+
					"symbeast: "+symbeast+"symbsouth: "+symbsouth);
			MapCoordinate prevLocation=stack.elementAt(stack.size()-2); //the previous location is the second last element
			/*System.out.println("print stack");
			for(MapCoordinate mc:stack){
				System.out.println("mc:"+mc.getX()+" "+mc.getY());
			}
			System.out.println("finish printing stack");
			System.out.println("print hasBeenTo");
			for(MapCoordinate mc:hasBeenTo){
				System.out.println("mc:"+mc.getX()+" "+mc.getY());
			}
			System.out.println("finish printing HasBeenTo");*/
			MapCoordinate maybeNextLocation=null;
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX(),this.currentLocation.getY()+1); //maybe north
			if(!this.hasBeenTo.contains(maybeNextLocation)&&!prevLocation.equals(maybeNextLocation)&&
				symbnorth!='*'&&symbnorth!='~'){
				if(symbnorth=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbnorth=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbnorth==' '||symbnorth=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX(),this.currentLocation.getY()-1); //maybe south
			if(!this.hasBeenTo.contains(maybeNextLocation)&&!prevLocation.equals(maybeNextLocation)&&
				symbsouth!='*'&&symbsouth!='~'){
				if(symbsouth=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						System.out.println("hello here");
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbsouth=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbsouth==' '||symbsouth=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX()+1,this.currentLocation.getY()); //maybe east
			if(!this.hasBeenTo.contains(maybeNextLocation)&&!prevLocation.equals(maybeNextLocation)&&
				symbeast!='*'&&symbeast!='~'){
				if(symbeast=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbeast=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbeast==' '||symbeast=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			maybeNextLocation=new MapCoordinate(this.currentLocation.getX()-1,this.currentLocation.getY()); //maybe west
			if(!this.hasBeenTo.contains(maybeNextLocation)&&!prevLocation.equals(maybeNextLocation)&&
				symbwest!='*'&&symbwest!='~'){
				if(symbwest=='T'){ //has axe have some effect
					if(this.hasAxe){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbwest=='-'){
					if(this.hasKey){ //means could be there
						ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
						temp.add(maybeNextLocation);
						designatedRoute.clear();
						designatedRoute.addAll(temp);
						return moveAccordingToDesignatedRoute();
					}
				}else if(symbwest==' '||symbwest=='O'){//means could go straightforward
					ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
					temp.add(maybeNextLocation);
					designatedRoute.clear();
					designatedRoute.addAll(temp);
					return moveAccordingToDesignatedRoute();
				}		
			}
			
			//has nowhere to roam, then go back
			ArrayList<MapCoordinate> temp = new ArrayList<MapCoordinate>();
			//System.out.println("prevLocation:"+prevLocation.getX()+" "+prevLocation.getY());
			temp.add(prevLocation);
			this.stack.pop();
			this.stack.pop(); //pop twice, since the prevLocation will be added when execute 'f' to walk back
			designatedRoute.clear();
			designatedRoute.addAll(temp);
			return moveAccordingToDesignatedRoute();
		}	
	}

	
	   void print_view( char view[][] )
	   {
	      int i,j;
	
	      System.out.println("\n+-----+");
	      for( i=0; i < 5; i++ ) {
	         System.out.print("|");
	         for( j=0; j < 5; j++ ) {
	            if(( i == 2 )&&( j == 2 )) {
	               System.out.print('^');
	            }
	            else {
	               System.out.print( view[i][j] );
	            }
	         }
	         System.out.println("|");
	      }
	      System.out.println("+-----+");
	   }
	
	   public static void main( String[] args )
	   {
	      InputStream in  = null;
	      OutputStream out= null;
	      Socket socket   = null;
	      Agent  agent    = new Agent();
	      char   view[][] = new char[5][5];
	      char   action   = 'F';
	      int port;
	      int ch;
	      int i,j;
	
	      if( args.length < 2 ) {
	         System.out.println("Usage: java Agent -p <port>\n");
	         System.exit(-1);
	      }
	
	      port = Integer.parseInt( args[1] );
	
	      try { // open socket to Game Engine
	         socket = new Socket( "localhost", port );
	         in  = socket.getInputStream();
	         out = socket.getOutputStream();
	      }
	      catch( IOException e ) {
	         System.out.println("Could not bind to port: "+port);
	         System.exit(-1);
	      }
	
	      try { // scan 5-by-5 wintow around current location
	         while( true ) {
	            for( i=0; i < 5; i++ ) {
	               for( j=0; j < 5; j++ ) {
	                  if( !(( i == 2 )&&( j == 2 ))) {
	                     ch = in.read();
	                     if( ch == -1 ) {
	                        System.exit(-1);
	                     }
	                     view[i][j] = (char) ch;
	                  }
	               }
	            }
	            agent.print_view( view ); // COMMENT THIS OUT BEFORE SUBMISSION
	            action = agent.get_action( view );
	            out.write( action );
	         }
	      }
	      catch( IOException e ) {
	         System.out.println("Lost connection to port: "+ port );
	         System.exit(-1);
	      }
	      finally {
	         try {
	            socket.close();
	         }
	         catch( IOException e ) {}
	      }
	   }
}
