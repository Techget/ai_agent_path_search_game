import java.util.*;

//go across river when cannot reach destination,but still cannot choose which path to go
public class BFSSearchMap implements BFSsearch {
	
	@Override
	//loc1 is the start location,loc2 is the destination
	public ArrayList<MapCoordinate> bfsSearch(MapCoordinate loc1, MapCoordinate loc2, Map m) {
		// TODO Auto-generated method stub
		System.out.println("come into bfsSearch");
		if(m.isAgentHasAxe() && m.isAgentHasKey()){
			LinkedList<State> queue=new LinkedList<State>();
			ArrayList<MapCoordinate> Visited=new ArrayList<MapCoordinate>();
			
			//Visited.add(loc1);
			State s1=new State(loc1,m.getAgentHasNStones(),null);
			queue.add(s1);
			
			while(!queue.isEmpty()){
				State s=queue.remove();
				MapCoordinate currentLocation=s.getLocation();
				Visited.add(s.getLocation()); //add to visited every loop
				if(loc2.equals(currentLocation)){
					//means could reach loc2 from loc1
					ArrayList<MapCoordinate> route=new ArrayList<MapCoordinate>();
					while(s.getPrevState()!=null){
						route.add(0,s.getLocation()); //add to head
						s=s.getPrevState();
					}
					return route;
				}
				MapCoordinate temp=new MapCoordinate(currentLocation.getX()+1,currentLocation.getY());
				if(m.hasCoordinate(temp)){//state move east
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' ){
							if(cTemp=='~' && s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){ //this is important else if(cTemp!='~'), not barely else,
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*'   ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' && s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX()-1,currentLocation.getY());
				if(m.hasCoordinate(temp)){//state move south
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' ){
							if(cTemp=='~' && s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*'   ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' && s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()+1);
				if(m.hasCoordinate(temp)){//state move north
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' ){
							if(cTemp=='~' && s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*'   ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' && s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()-1);
				if(m.hasCoordinate(temp)){//state move south
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' ){
							if(cTemp=='~' &&s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*'   ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' && s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}	
			}
			return null;
		}else if(m.isAgentHasAxe()){ //only have axe
			LinkedList<State> queue=new LinkedList<State>();
			ArrayList<MapCoordinate> Visited=new ArrayList<MapCoordinate>();
			
			//Visited.add(loc1);
			State s1=new State(loc1,m.getAgentHasNStones(),null);
			queue.add(s1);
			
			while(!queue.isEmpty()){
				State s=queue.remove();
				MapCoordinate currentLocation=s.getLocation();
				Visited.add(s.getLocation());  //add visited every loop
				if(loc2.equals(currentLocation)){
					//means could reach loc2 from loc1
					ArrayList<MapCoordinate> route=new ArrayList<MapCoordinate>();
					while(s.getPrevState()!=null){
						route.add(0,s.getLocation()); //add to head
						s=s.getPrevState();
					}
					return route;
				}
				MapCoordinate temp=new MapCoordinate(currentLocation.getX()+1,currentLocation.getY());
				if(m.hasCoordinate(temp)){//state move south
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='-' ){
							if(cTemp=='~' && s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' && s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX()-1,currentLocation.getY());
				if(m.hasCoordinate(temp)){//state move south
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='-' ){
							if(cTemp=='~' && s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()+1);
				if(m.hasCoordinate(temp)){//state move north
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='-' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()-1);
				if(m.hasCoordinate(temp)){//state move south
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='-' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
			}
			return null;
		}else if(m.isAgentHasKey()){ //only have key
			LinkedList<State> queue=new LinkedList<State>();
			ArrayList<MapCoordinate> Visited=new ArrayList<MapCoordinate>();
			
			//Visited.add(loc1);
			State s1=new State(loc1,m.getAgentHasNStones(),null);
			queue.add(s1);
			
			while(!queue.isEmpty()){
				State s=queue.remove();
				MapCoordinate currentLocation=s.getLocation();
				Visited.add(s.getLocation());
				if(loc2.equals(currentLocation)){
					//means could reach loc2 from loc1
					ArrayList<MapCoordinate> route=new ArrayList<MapCoordinate>();
					while(s.getPrevState()!=null){
						route.add(0, s.getLocation()); //add to head,in this fashion, return the right order of route
						s=s.getPrevState();
					}
					return route;
				}
				MapCoordinate temp=new MapCoordinate(currentLocation.getX()+1,currentLocation.getY()); 
				if(m.hasCoordinate(temp)){//state move east
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX()-1,currentLocation.getY()); //state that move west
				if(m.hasCoordinate(temp)){
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()+1); //state move north
				if(m.hasCoordinate(temp)){
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()-1); //state move south
				if(m.hasCoordinate(temp)){
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){//at least has 1 stone
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for initial state
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
			}
			return null;
		}
		
		
		/*
		 * 
		 **/		
		else{ //have no key and axe
			System.out.println("come into the last elas clause");
			LinkedList<State> queue=new LinkedList<State>();
			ArrayList<MapCoordinate> Visited=new ArrayList<MapCoordinate>();
			
			Visited.add(loc1);
			State s1=new State(loc1,m.getAgentHasNStones(),null);
			queue.add(s1);
			
			//int i=0;
			while(!queue.isEmpty()){
				//i++;
				//System.out.println("come into while: "+i);
				State s=queue.remove();
				MapCoordinate currentLocation=s.getLocation();
				Visited.add(s.getLocation()); //add visited, let program won't stuck in 
				if(loc2.equals(currentLocation)){
					//means could reach loc2 from loc1
					System.out.println("return computed route");
					ArrayList<MapCoordinate> route=new ArrayList<MapCoordinate>();
					while(s.getPrevState()!=null){
						route.add(0, s.getLocation()); //add to head
						s=s.getPrevState();
					}
					for(MapCoordinate mc:route){
						//System.out.println("print returned route in bfssearch");
						System.out.print("mc:"+mc.getX()+" "+mc.getY()+"->");
						
					}
					return route;
				}
				MapCoordinate temp=new MapCoordinate(currentLocation.getX()+1,currentLocation.getY());
				if(m.hasCoordinate(temp)){
					//System.out.println("1 if");
					if(s.getPrevState()!=null &&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if");
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T'&&cTemp!='-'  ){
							//System.out.println("3 if");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 if");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 else");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for the initial state 
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'&&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX()-1,currentLocation.getY());
				if(m.hasCoordinate(temp)){
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T'&&cTemp!='-'  ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.add(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for the initial state 
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'&&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()+1);
				if(m.hasCoordinate(temp)){
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*'&&cTemp!='T'&&cTemp!='-'   ){
							if(cTemp=='~' && s.getStoneNumber()>0){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}else if(s.getPrevState()==null){ //for the initial state 
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'&&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}
						}
					}
				}
				temp=new MapCoordinate(currentLocation.getX(),currentLocation.getY()-1);
				if(m.hasCoordinate(temp)){
					if(s.getPrevState()!=null&&!temp.equals(s.getPrevState().getLocation())){
						char cTemp=m.getSymbol(temp);
						if(!Visited.contains(temp)&&cTemp!='*' &&cTemp!='T' &&cTemp!='-' ){
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}//do not do any action for not enough stones situation
						}
					}else if(s.getPrevState()==null){ //for the initial state 
						char cTemp=m.getSymbol(temp);
						//System.out.println("2 if lalala");
						if(cTemp!='*' &&cTemp!='T'&&cTemp!='-'  ){
							//System.out.println("3 iflalala");
							if(cTemp=='~' &&  s.getStoneNumber()>0){
								//System.out.println("4 iflalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber()-1,s);
								queue.addLast(newState);
							}else if(cTemp!='~'){
								//System.out.println("4 elselalala");
								State newState=new State(new MapCoordinate(temp),s.getStoneNumber(),s);
								queue.addFirst(newState);
							}//do not do any action for not enough stones situation
						}
					}
				}
			}
			return null;
		}
	}

}
