public class State{ //used in BFS in reachable test
	private MapCoordinate location;
	private int stoneNumber;
	private State prevState;
	
	public State(MapCoordinate mc,int nstone,State prevState){
		this.location=mc;
		this.stoneNumber=nstone;
		this.prevState=prevState;
	}
	
	public MapCoordinate getLocation() {
		return location;
	}

	public void setLocation(MapCoordinate location) {
		this.location = location;
	}

	public int getStoneNumber() {
		return stoneNumber;
	}

	public void setStoneNumber(int stoneNumber) {
		this.stoneNumber = stoneNumber;
	}

	public State getPrevState() {
		return prevState;
	}

	public void setPrevState(State prevState) {
		this.prevState = prevState;
	}
}
