
public class MapCoordinate {
	private int x;
	private int y;
	
	public MapCoordinate(int x,int y){
		this.x=x;
		this.y=y;
	}
	public MapCoordinate(MapCoordinate mc){
		this.x=mc.getX();
		this.y=mc.getY();
	}
	
	public void setCoordinate(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	
	public void setX(int v){
		this.x+=v;
	}
	public void setY(int v){
		this.y+=v;
	}
	
	/*public boolean equals(MapCoordinate mc){
		if(mc.getX()==this.x && mc.getY()==this.y){
			return true;
		}else{
			return false;
		}
	}*/
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MapCoordinate other = (MapCoordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
