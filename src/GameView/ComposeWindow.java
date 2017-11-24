package GameView;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.JFrame;

public class ComposeWindow extends JFrame {

	
	
	ComposeWindow() {
		super("Compose Window");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setLocation(400, 300);
		setSize(400, 300);
		add("Center", new CvDefPoly());
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		show();
	}

	public static void openComposeWindow(TetrisView t) {

		/*
		 * JFrame config = new JFrame("Compose Window"); config.setSize (400,
		 * 300);
		 * 
		 * config.setLocation(400, 300); config.setResizable(false); //
		 * config.setLocationRelativeTo(frame); config.setLayout(null); //
		 * changeConstant("Constant", config, 350, 400, t);
		 * config.setVisible(true);
		 * 
		 * config.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		 * 
		 * config.add("Center", new CvIsotrop());
		 * System.out.println("COMPOSE Factor:___________");
		 * System.out.println("COMPOSE speedFactor:_____________");
		 * config.show();
		 */
		// new ComposeWindow();

	}
}

class CvDefPoly extends Canvas {
	
	private static int[][] squareOrder = {{0,0,0},{0,0,0},{0,0,0}};
	public static int Xpoint, Ypoint = 0;
	
	
	//Vector v = new Vector();
	float x0, y0, rWidth = 10.0F, rHeight = 10.0F, pixelSize;
	boolean ready = true;
	int centerX, centerY;
	int left = iX(-rWidth / 2);
    int right = iX(rWidth / 2); 
	int bottom = iY(-rHeight / 2);
	int top = iY(rHeight / 2);
	int size = (right - left) /3;

	CvDefPoly() {
	

		
	}

	
/*	void squareInvolved(int[][] square, int i, int j){
		int slength = square.length;
		square[i][j] = 0;
		for(int a = 0; a <3; a++)
			for(int b = 0; b < 3; b++){
				CvDefPoly.squareOrder[a][b] = square[a][b];
				
			}
		
	}*/

	/*int getSquare(int[][] square, int i , int j){
		return square[i][j];
	}*/
	
	void initgr() {
		Dimension d = getSize();
		int maxX = d.width - 1, maxY = d.height - 1;
		pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
		centerX = maxX / 2;
		centerY = maxY / 2;
		float x0, y0, rWidth = 10.0F, rHeight = 10.0F, pixelSize;
		boolean ready = true;
		int centerX, centerY;
		final int left = iX(-rWidth / 2);
	    int right = iX(rWidth / 2); 
		int bottom = iY(-rHeight / 2);
		final int top = iY(rHeight / 2);
		final int size = (right - left) /3;
		

		addMouseListener(new MouseAdapter() {
			//  int[][] square = new int[3][3];	
			  public void mousePressed(MouseEvent evt) {
				int xA = evt.getX(), yA = evt.getY();
			//	int MouseX = iX(xA), MouseY = iY(yA);
			  
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++)
					{
						//System.out.println("left" + left);
						//System.out.println("left + i:" + (left+i*size));
						//System.out.println("left + i+1:" + (left+(i+1)*size));
						//System.out.println("xA" + xA);
						if( left+i*size <= xA && xA <= left+(i+1)*size
								&& top + j*size < yA && yA <top + (j+1)*size)
						{
							System.out.println("yes");
							squareOrder[i][j] = 1;
							System.out.print(squareOrder[i][j]);
						//	squareInvolved(CvDefPoly.squareOrder, i, j);
						//	System.out.println(getSquare(CvDefPoly.squareOrder, i , j));
						//	break;
						}
					}
				System.out.println();
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++){
					//	CvDefPoly.squareOrder[i][j] = square[i][j];
						System.out.print(CvDefPoly.squareOrder[i][j]);
					}
				System.out.println();
				for(int i = 0; i < 3; i++)
					for(int j = 0; j < 3; j++){
					
						System.out.print(CvDefPoly.squareOrder[i][j]);
					}
				
				repaint();
			
			/*	
				if (ready) {
				//	v.removeAllElements();
					x0 = xA;
					y0 = yA;
					ready = false;
				}
				float dx = xA - x0, dy = yA - y0;
			//	if (v.size() > 0
						&& dx * dx + dy * dy < 4 * pixelSize * pixelSize)
					ready = true;
				else
				//	v.addElement(new Point2D(xA, yA));
				repaint();*/
			}
		});
	}

	int iX(float x) {
		return Math.round(centerX + x / pixelSize);
	}

	int iY(float y) {
		return Math.round(centerY - y / pixelSize);
	}

	float fx(int x) {
		return (x - centerX) * pixelSize;
	}

	float fy(int y) {
		return (centerY - y) * pixelSize;
	}

	

	
	void drawSquareOrder(Graphics g, int[][] a){
		int left = iX(-rWidth / 2), right = iX(rWidth / 2), bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
		int size = (right - left) /3;
		for(int i = 0; i < 3; i++)
			for(int j =0 ; j < 3; j++){
			//	System.out.print(CvDefPoly.squareOrder[i][j]);
				
				if(a[i][j] == 1){
					//left+i*size<MouseX && MouseX < left+(i+1)*size
					//&& top + j*size < MouseY && MouseY <top + (j+1)*size
					CvDefPoly.Xpoint = i;
					CvDefPoly.Ypoint = j;
					System.out.println("fillRect");
					g.fillRect(left + i*size, top + j*size, size-1, size-1);
					
					//break;
				//	System.out.println("=1");
				}
					
			}
		
	
		//System.out.println(size);
		g.setColor(Color.BLACK);
		//g.drawRect(left, top, right - left, bottom - top);
		
		//g.drawRect(left, top, right - left, bottom - top);
	}
	
	public void paint(Graphics g) {
		initgr();
		int left = iX(-rWidth / 2), right = iX(rWidth / 2), bottom = iY(-rHeight / 2), top = iY(rHeight / 2);
		g.drawRect(left, top, right - left, bottom - top);
		/*int n = v.size();
		if (n == 0)
			return;
		Point2D a = (Point2D) (v.elementAt(0));
		// Show tiny rectangle around first vertex:
		g.drawRect(iX(a.x) - 2, iY(a.y) - 2, 4, 4);
		for (int i = 1; i <= n; i++) {
			if (i == n && !ready)
				break;
			Point2D b = (Point2D) (v.elementAt(i % n));
			g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
			a = b;
		}*/
		
		int size = (right - left) /3;
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
		{
			g.drawRect(left + i*size ,top + j*size, size-1, size-1);	
		//	System.out.println(left);
		}
		
/*		for(int i = 0; i < 3; i++)
			for(int j =0 ; j < 3; j++){
			//	System.out.print(CvDefPoly.squareOrder[i][j]);
				if(CvDefPoly.squareOrder[i][j] == 1){
					//left+i*size<MouseX && MouseX < left+(i+1)*size
					//&& top + j*size < MouseY && MouseY <top + (j+1)*size
					System.out.println("fillRect");
					g.fillRect(left + i*size, top + j*size, size-1, size-1);
					//break;
				//	System.out.println("=1");
				}
					
			}*/
		
		drawSquareOrder(g, CvDefPoly.squareOrder);

		System.out.println();
	}
}

// The class Point2D, used in the above file, will also be useful in other
// programs, so that we define this in another separate file, Point2D.java:

// Point2D.java: Class for points in logical coordinates.
class Point2D {
	float x, y;

	Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
