package GameView;

import java.util.Random;


public class Shape {
	
	public enum ShapeBody{
		//The origin in the center
		//from 论文俄罗斯方块旋转算法的坐标实现  
		EmptyShape(new int[][] {{0, 0}, {0, 0}, {0, 0}, {0, 0}}),
		ZMirrorShape(new int[][] {{-1, 0}, {0, 0}, {0, -1}, {1, -1}}),
		ZShape(new int[][] {{-1, -1}, {0, -1}, {0, 0}, {1, 0}}),
		Stick(new int[][] {{-1, 0}, {0, 0}, {1, 0}, {2, 0}}),
		TShape(new int[][] {{-1, 0}, {0, 0}, {1, 0}, {0, -1}}),
		SquareShape(new int[][] {{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
		LShape(new int[][] {{-2, 0}, {-1, 0}, {0, 0}, {0, -1}}),
		LMirror(new int[][] {{0, -1}, {0, 0}, {1, 0}, {2, 0}});
		
		public int[][] coords;
		
		private ShapeBody(int[][] coords){
			this.coords = coords;
		}
	}
	
	private ShapeBody pieceShape;
	private int[][] coords;
	
	public Shape(){
		coords = new int[4][2];
		setShape(ShapeBody.EmptyShape);
	}
	
	public void setShape(ShapeBody shape){
		for (int i = 0 ; i < 4; i++)
			for (int j = 0; j <2; ++j){
				coords[i][j] = shape.coords[i][j];
						
			}
		pieceShape = shape;

	}
	
	private void setX(int index, int x){
		coords[index][0] = x;
	}
	
	private void setY(int index, int y){
		coords[index][1] = y;
	}
	
	public int x(int index){
		return coords[index][0];
	}
	
	public int y(int index){
		return coords[index][1];
		
	}
	
	public ShapeBody getShape(){
		return pieceShape;
	}

	public void setRandomShape(){
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 +1;
		ShapeBody[] values = ShapeBody.values();
		setShape(values[x]);
	}
	

	
	public int minX(){
		int m = coords[0][0];
		
		for (int i = 0; i < 4; i++){
			m = Math.min(m, coords[i][0]);
		}
		
		return m;
	}
	
	public int minY(){
		int m = coords[0][0];
		
		for (int i = 0; i < 4; i++){
			m = Math.min(m, coords[i][1]);
		}
		
		return m;
	}
	
	//--------------------------------------rotate()--------------------------------------------
	
	public Shape rotateLeft(){
		if(pieceShape == ShapeBody.SquareShape)
			return this;
		
		Shape result = new Shape();
		result.pieceShape = pieceShape;
		for (int i = 0; i < 4; i++ ){
			result.setX(i, y(i));
			result.setY(i, -x(i));
		}		
		return result;
	}
	
	public Shape rotateRight(){
		if(pieceShape == ShapeBody.SquareShape)
			return this;
		
		Shape result = new Shape();
		result.pieceShape = pieceShape;
		
		for(int i = 0; i < 4; i++){
			result.setX(i, -y(i));
			result.setY(i, x(i));
		}
		return result;
	}
}
