package GameView;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JLabel;
import javax.swing.Timer;

import GameView.Shape.ShapeBody;



public class TetrisView extends Canvas implements ActionListener{
	
	private static final Color[] COLORS = {
		// RGB from »­Í¼
		new Color(0,0,0) ,
		new Color(254,255,39), new Color(112,0,159), 
		new Color(24,164,239),
		new Color(254,204,28), new Color(15, 184, 83),
		new Color(253,21,6), new Color(14,93,191)};
	
/*	ZMirrorShape(new int[][] {{-1, 0}, {0, 0}, {0, -1}, {1, -1}}),
	ZShape(new int[][] {{-1, -1}, {0, -1}, {0, 0}, {1, 0}}),
	Stick(new int[][] {{-1, 0}, {0, 0}, {1, 0}, {2, 0}}),
	TShape(new int[][] {{-1, 0}, {0, 0}, {1, 0}, {0, -1}}),
	SquareShape(new int[][] {{0, 0}, {1, 0}, {0, 1}, {1, 1}}),
	LShape(new int[][] {{-2, 0}, {-1, 0}, {0, 0}, {0, -1}}),
	LMirror(new int[][] {{0, -1}, {0, 0}, {1, 0}, {2, 0}});*/
	
	
	private Timer timer;
	private boolean isFallingFinished = false;
	private boolean isStarted = false;
	private boolean isPaused = false;
	private int numLinesRemoved = 0;
    //Score, Level, Lines
    private int Score;
    private int Level;
    private int Lines;
    
	
	private int curX = 0;
	private int curY = 0;
	private JLabel statusBar;
	private Shape curShape;
	private Shape nextShape;
	private Shape tempShape;
	private ShapeBody[] board;
	
	private int XCoordinateRect;
	private int YCoordinateRect;
	private int RectWidth;
	private int RectHeight;
	
	//Parse Rect
	private int XCoordinatePause;
	private int YCoordinatePause;
	private int PauseWidth;
	private int PauseHeight;
	
	//Parse Char
	private int XCoordinatePChar;
	private int YCoordinatePChar;
	
	//NextShapeView 
	private int XCoordinateNextShapeView;
    private int YCoordinateNextShapeView;
    private int NextShapeViewWidth;
    private int NextShapeViewHeight;
	
    //Level
    private int XLevel;
    private int YLevel;
    
    //Lines
    private int XLines;
    private int YLines;
    
    //Score
    private int XScore;
    private int YScore;
    //Quit Rect
    private int XQuitRect;
    private int YQuitRect;
    private int QuitRectWidth;
    private int QuitRectHeight;
    //Quit
    private int XQuit;
    private int YQuit;
    
    //XSizeNextShape, YSizeNextShape 
    private int XSizeNextShape;
    private int YSizeNextShape;
    

    private ConfigWindow config;
    
    
    
    //---------------------Adjusted Constants----------------------------
   
    private int scoreFactor;
    private int levelDifficulty;
    private static int speedFactor=5;
    public void setScoreFactor(int s){
    	scoreFactor = s;
    	}
    
    public int getScoreFactor(){
    	return scoreFactor;
    	}
    
    public void setLevelDifficulty(int s){
    	levelDifficulty = s;
    	}
    
    public int getLevelDifficulty(){
    	return levelDifficulty;
    }
    
    public void setSpeedFactor(int s){
    	speedFactor = s;
    }
    public int getSpeedFactor(){
    	return speedFactor;
    }
    
    //Level, score
    
  
    
    
  //--------BOARD RELATED---------------------------------------------------------------------------------- 
    //WidthOfPiece
    private int WidthOfPiece;
    private int HeightOfPiece;
    
	private boolean isInRect;
	private boolean isInQuit;
	
	
 	//Numbers of Pieces(each belongs to different ShapeBody) in Board, all: BORADWITHD * BOARDHEIGHT
	private static int BOARDWIDTH = 10;
	private static int BOARDHEIGHT = 20;
	
	public final int DELAY = 400;	// milliseconds per tick
	
	

	int n = 0;
	
	TetrisView(){
		setFocusable(true);
		
		//config = new ConfigWindow();
		
		
		//setConfig(config);
	//	speedFactor = config.getSpeed();

		
		
		
	}
	
	
	//Config From ConfigWindow----------------------------------------------------------------------------------------------
	public void initialize(int speed, int width, int height, int scorefactor, int leveldifficulty){
		
		timer = new Timer((int) speed*100, this);
		BOARDWIDTH = width;
		BOARDWIDTH = height;
		
		board = new ShapeBody[ BOARDWIDTH * BOARDHEIGHT];
		clearBoard();
		
		curShape = new Shape();
		nextShape = new Shape();
		//System.out.println(scorefactor);
		//System.out.println("In ini" + n);
		
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent evt)
			{//----------------------------Click Quit------------------------------------------
				float xA = evt.getX(), yA = evt.getY();
				if(isInRectQuit(xA, yA))
					isInQuit = true;		
				else
					isInQuit = false;
				if(!isInRectPause(xA, yA)){	
					
					if(evt.getButton() == MouseEvent.BUTTON1){
						tryMove(curShape, curX-1, curY);
				}	
					if(evt.getButton() == MouseEvent.BUTTON3){				
						tryMove(curShape, curX+1, curY);
				}
				}
			}
			
			
			
			
		});	
		//-----------------------------In or out Board --> Pause--------------------- 
		addMouseMotionListener(new MouseAdapter(){
			public void mouseMoved(MouseEvent evt)
			{
				float xA = evt.getX(), yA = evt.getY();
				if(isInRectPause(xA, yA))	
					isInRect = true;				
				else 
					isInRect = false;
				repaint();	
			}

		});	

		
		addMouseWheelListener(new MouseAdapter(){
			public void mouseWheelMoved(MouseWheelEvent e){
				if(e.getWheelRotation() == 1){
					tryMove(curShape.rotateRight(), curX, curY);
				}
				if(e.getWheelRotation() == -1){
					tryMove(curShape.rotateLeft(), curX, curY);
				}
			}
			
		}
		);
		
	}


	//------------------------------------In main area Pause-------------------------------------------------
	
	
    private boolean isInRectPause(float x, float y)
    {

		isInRect = false;
		if(x<RectWidth+XCoordinateRect && y<RectHeight+YCoordinateRect
				&& x>XCoordinateRect && y>YCoordinateRect)
		{
			isInRect = true;
			timer.stop();
			//System.out.println("Yes, In Rect");	
		}
		else
		{
			//System.out.println("No, Not In Rect");	
			timer.start();
		}
    	return isInRect;
	
	}
    
    //--------------------------------In Quit Rect ---------------------------------------------------
    private boolean isInRectQuit(float x, float y)
    {
    	isInQuit = false;
		if(x<XQuitRect + QuitRectWidth && y<YQuitRect + QuitRectHeight
				&& x>XQuitRect && y>YQuitRect)
		{
			isInRect = true;
			System.out.println("Quit!!!!!!!!!!!!!!");	
			System.exit(0);
		}
		
    	return isInQuit;
    	
    }
	
    
    //-----------------------------attribute of each Piece--------------------------------------- 
    
	public int squareWidth(){
	
		RectWidth = this.getWidth()/20*10;
    	return (int)  RectWidth/BOARDWIDTH;
	}
	
	public int squareHeight(){
		RectHeight = this.getHeight()/20 * 18;
		return (int) RectHeight/BOARDHEIGHT;
	}
	
	public ShapeBody shapeAt(int x, int y){
		return board[y*BOARDWIDTH + x];
	}
	
	
	//----------------------------------Board operation----------------------------------
	private void clearBoard(){
		for(int i = 0; i <BOARDHEIGHT*BOARDWIDTH; i++){
			board[i] = ShapeBody.EmptyShape;
		}
	}
	
	private void pieceDropped(){
		for(int i=0; i<4; i++){
			int x = curX + curShape.x(i);
			int y = curY - curShape.y(i);
			board[y*BOARDWIDTH + x] = curShape.getShape();
		}
		removeFullLines();
		
		if(!isFallingFinished){
			newPiece();
		}
	}
	
	
	public void newTwoPiece(){
	
		curShape.setRandomShape();
	    nextShape.setRandomShape();
	 //   tempShape = nextShape;
	    
	    curX = BOARDWIDTH/2 + 1;
		curY = BOARDHEIGHT - 1 + curShape.minY(); //The logical coordinates 
		
		if(!tryMove(curShape, curX, curY-1)){
			curShape.setShape(ShapeBody.EmptyShape);
			nextShape.setShape(ShapeBody.EmptyShape);
			timer.stop();
			isStarted = false;
			System.out.println("Game over");
		}
		
	}
	
	private boolean tryMove(Shape newShape, int newX, int newY){
		for(int i = 0; i<4; ++i){
			int x = newX + newShape.x(i);
			int y = newY - newShape.y(i);
			
			if(x<0||x>=BOARDWIDTH || y<0 || y>= BOARDHEIGHT)
			{
				System.out.println("??cannot move anymore");
				return false;
			}
				
			
			if(shapeAt(x,y) != ShapeBody.EmptyShape)
			{
				return false;
			}
				
		}
		
		curShape = newShape;
		curX = newX;
		curY = newY;
		repaint();
		
		return true;
		
	}
	
	public void newPiece(){

	
	//	curShape.setRandomShape();
		curShape = nextShape;	
		Shape newShape = new Shape();
		nextShape = newShape;
		nextShape.setRandomShape();
		
		
		curX = BOARDWIDTH/2 + 1;
		curY = BOARDHEIGHT - 1 + curShape.minY(); //The logical coordinates 
		
		if(!tryMove(curShape, curX, curY-1)){
			curShape.setShape(ShapeBody.EmptyShape);
			nextShape.setShape(ShapeBody.EmptyShape);
			timer.stop();
			isStarted = false;
			//statusBar.setText("Game Over");
		}
		
	}
	
	private void oneLineDown(){
		if(!tryMove(curShape, curX, curY - 1))
			pieceDropped();
	}

	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (isFallingFinished){
			isFallingFinished = false;
			newPiece();
		}
		else{
			oneLineDown();
		}
	
		
	}
	
	
    
    public void initgr()
    {
    	//Rectangle
    			XCoordinateRect = this.getWidth()/20;
    			YCoordinateRect = this.getHeight()/20;
    			RectWidth = this.getWidth()/20*10;
    			RectHeight = this.getHeight()/20 * 18;
    			
    			//Parse Rect
    			XCoordinatePause = this.getWidth()/20*3;
    			YCoordinatePause = this.getHeight()/20*9;
    			PauseWidth = this.getWidth()/20*5;
    			PauseHeight = this.getHeight()/20*2;
    			
    			//Parse Character
    			XCoordinatePChar = this.getWidth()/20*4;
    			YCoordinatePChar = (int)(this.getHeight()/20*10.5);
    			
    			//nextShapeView Rect
    			XCoordinateNextShapeView = this.getWidth()/20*12;
    		    YCoordinateNextShapeView = this.getHeight()/20;
    		    NextShapeViewWidth = this.getWidth()/20*7;
    		    NextShapeViewHeight = this.getHeight()/20*6;
    			
    			//Level
    		    XLevel = this.getWidth()/20*12;
    		    YLevel = this.getHeight()/20*10;
    		    
    		    //Lines
    		    XLines = this.getWidth()/20*12;
    		    YLines = this.getHeight()/20*12;
    		    
    		    //Score
    		    XScore = this.getWidth()/20*12;
    		    YScore = this.getHeight()/20*14;
    		    
    		    //Quit Rect
    			XQuitRect = this.getWidth()/20*12;
    		    YQuitRect = this.getHeight()/20*17;
    		    QuitRectWidth = this.getWidth()/20*3;
    		    QuitRectHeight = this.getHeight()/20*2;
    		    
    		    //Quit
    		    XQuit = this.getWidth()/20*13;
    		    YQuit = this.getHeight()/20*18;
    		    
    		    //XSizeNextShape, YSizeNextShape 
    		    XSizeNextShape = 0 ;
    		    YSizeNextShape = 0 ;
    		    
    		    //WidthOfPiece
    		    WidthOfPiece = RectWidth/BOARDWIDTH;
    		    HeightOfPiece = RectHeight/BOARDHEIGHT;
    		    
    		
    	
    }
    

	

// ---------------------------draw the Rectangle, board, charactors and so on--------------------

	private void drawComponent(Graphics g) {
		// TODO Auto-generated method stub
				
		//System.out.println("Left: getWidth()=" + this.getWidth());
		//System.out.println("Left: getHeight()=" + this.getHeight());
		//System.out.println("Left: getWidth()=" + RectWidth);
		//System.out.println("Left: getHeight()=" + RectHeight);
		
		g.drawRect( XCoordinateRect, YCoordinateRect, RectWidth, RectHeight);
		g.drawRect( XCoordinateNextShapeView, YCoordinateNextShapeView, NextShapeViewWidth,
	    NextShapeViewHeight);
		
		g.setFont(new Font("Tahoma", Font.BOLD,18));
		g.drawString("Level:    "+ 1, XLevel, YLevel);
		g.drawString("Lines:    "+ numLinesRemoved, XLines, YLines);
		g.drawString("Score:    "+ numLinesRemoved*scoreFactor, XScore, YScore);
		g.drawRect( XQuitRect, YQuitRect, QuitRectWidth, QuitRectHeight);
		g.drawString("QUIT", XQuit, YQuit);
		
		if (isInRect)
		{
			
			g.setColor(Color.BLUE);
			g.drawRect(XCoordinatePause, YCoordinatePause, PauseWidth, PauseHeight);
			g.setFont(new Font("Tahoma", Font.BOLD,22));
			g.drawString("PAUSE", XCoordinatePChar, YCoordinatePChar);
		}
		
		/*		//nextShape
		g.setColor(Color.BLACK);
		g.drawRect( XCoordinateNextShapeView/20*24, YCoordinateNextShapeView*4, NextShapeViewWidth/7,
				 NextShapeViewWidth/7);
		g.drawRect( XCoordinateNextShapeView/20*24 + NextShapeViewWidth/7, YCoordinateNextShapeView*4, NextShapeViewWidth/7,
				 NextShapeViewWidth/7);
		g.drawRect( XCoordinateNextShapeView/20*24 + 2*NextShapeViewWidth/7, YCoordinateNextShapeView*4, NextShapeViewWidth/7,
				 NextShapeViewWidth/7);
		g.drawRect( XCoordinateNextShapeView/20*24 + 2*NextShapeViewWidth/7, YCoordinateNextShapeView*4 -  NextShapeViewWidth/7, NextShapeViewWidth/7,
				 NextShapeViewWidth/7);
		
		g.setColor(Color.RED);
		g.fillRect(XCoordinateNextShapeView/20*24+1, YCoordinateNextShapeView*4+1, NextShapeViewWidth/7-1,
				 NextShapeViewWidth/7-1);
		g.fillRect( XCoordinateNextShapeView/20*24 + NextShapeViewWidth/7+1, YCoordinateNextShapeView*4+1, NextShapeViewWidth/7-1,
				 NextShapeViewWidth/7-1);
		g.fillRect( XCoordinateNextShapeView/20*24 + 2*NextShapeViewWidth/7+1, YCoordinateNextShapeView*4+1, NextShapeViewWidth/7-1,
				 NextShapeViewWidth/7-1);
		g.fillRect( XCoordinateNextShapeView/20*24 + 2*NextShapeViewWidth/7+1, YCoordinateNextShapeView*4 -  NextShapeViewWidth/7+1, NextShapeViewWidth/7-1,
				 NextShapeViewWidth/7-1);*/
	
		
	/*	//Animation Square
		g.setColor(Color.BLACK);
		g.drawRect(XCoordinateRect+6*WidthOfPiece, YCoordinateRect + RectHeight - WidthOfPiece,
				WidthOfPiece-1, WidthOfPiece-1);
		g.drawRect(XCoordinateRect+7*WidthOfPiece, YCoordinateRect + RectHeight - WidthOfPiece,
				WidthOfPiece-1, WidthOfPiece-1);
		g.drawRect(XCoordinateRect+7*WidthOfPiece, YCoordinateRect + RectHeight - 2*WidthOfPiece,
				WidthOfPiece-1, WidthOfPiece-1);
		g.drawRect(XCoordinateRect+8*WidthOfPiece, YCoordinateRect + RectHeight - 2*WidthOfPiece,
				WidthOfPiece-1, WidthOfPiece-1);
		
		g.setColor(Color.YELLOW);
		g.fillRect(XCoordinateRect+6*WidthOfPiece+1, YCoordinateRect + RectHeight - WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+7*WidthOfPiece+1, YCoordinateRect + RectHeight - WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+7*WidthOfPiece+1, YCoordinateRect + RectHeight - 2*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+8*WidthOfPiece+1, YCoordinateRect + RectHeight - 2*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		
		//second
		g.setColor(Color.BLACK);
		g.drawRect(XCoordinateRect+8*WidthOfPiece, YCoordinateRect + RectHeight - WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.drawRect(XCoordinateRect+9*WidthOfPiece, YCoordinateRect + RectHeight - WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.drawRect(XCoordinateRect+9*WidthOfPiece, YCoordinateRect + RectHeight - 2*WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.drawRect(XCoordinateRect+9*WidthOfPiece, YCoordinateRect + RectHeight - 3*WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.setColor(Color.BLUE);
		g.fillRect(XCoordinateRect+8*WidthOfPiece+1, YCoordinateRect + RectHeight - WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+9*WidthOfPiece+1, YCoordinateRect + RectHeight - WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+9*WidthOfPiece+1, YCoordinateRect + RectHeight - 2*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+9*WidthOfPiece+1, YCoordinateRect + RectHeight - 3*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		
	
		//third - square
		g.setColor(Color.BLACK);
		g.drawRect(XCoordinateRect+4*WidthOfPiece, YCoordinateRect + RectHeight - 11*WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.drawRect(XCoordinateRect+4*WidthOfPiece, YCoordinateRect + RectHeight - 10*WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.drawRect(XCoordinateRect+5*WidthOfPiece, YCoordinateRect + RectHeight - 11*WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.drawRect(XCoordinateRect+5*WidthOfPiece, YCoordinateRect + RectHeight - 10*WidthOfPiece,
				WidthOfPiece, WidthOfPiece);
		g.setColor(Color.ORANGE);
		g.fillRect(XCoordinateRect+4*WidthOfPiece+1, YCoordinateRect + RectHeight - 11*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+4*WidthOfPiece+1, YCoordinateRect + RectHeight - 10*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+5*WidthOfPiece+1, YCoordinateRect + RectHeight - 11*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
		g.fillRect(XCoordinateRect+5*WidthOfPiece+1, YCoordinateRect + RectHeight - 10*WidthOfPiece+1,
				WidthOfPiece-1, WidthOfPiece-1);
*/
		//---------------------------------------------------------------
      //  Dimension size = getSize();
		
		
	}
	
	
	//------------------------------Draw next Shape view------------------------------------------
    
	private void drawNextShape(Graphics g, int x, int y, ShapeBody nextShape){
		
		drawShape(g, x, y, nextShape);
		
	}
	

	//------------------------------Draw all the Shape------------------------------------------
	private void drawShape(Graphics g, int x, int y, ShapeBody shape){
		//YCoordinateRect = this.getHeight()/20;
	//	RectHeight = this.getHeight()/20 * 18;
    //	y = y - (this.getHeight() - YCoordinateRect - RectHeight);
		
		Color color = COLORS[shape.ordinal()];
		g.setColor(color);
		g.fillRect(x+1, y+1, squareWidth()-2, squareHeight() -2 );
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight()-1, x , y);
		g.drawLine(x, y, x+squareWidth()-1, y);
		g.setColor(color.BLACK);
		g.drawLine(x+1, y+squareHeight()-1, x+squareWidth()-1, y+squareHeight()-1);
		g.drawLine(x+squareWidth()-1, y+squareHeight()-1, x+squareWidth()-1, y+1);
		
	}
	
	
	
	public void paint(Graphics g){
		
		
		
	//	super.paint(g);
	//	Dimension size = getSize();
	//	g.drawString("MingSun Assignment1, thank TA a lot", 10, 10);
	//	g.drawString("Utd ID: 2021326379", 10, 20);

		//XCoordinateRect = this.getWidth()/20;
		//YCoordinateRect = this.getHeight()/20;
		//RectWidth = this.getWidth()/20*10;
		//RectHeight = this.getHeight()/20 * 18;
		
		//System.out.println("Start Paint");
	
		initgr();
		drawComponent(g);
		
/*		XCoordinateNextShapeView = this.getWidth()/20*12;
	    YCoordinateNextShapeView = this.getHeight()/20;
	    NextShapeViewWidth = this.getWidth()/20*7;
	    NextShapeViewHeight = this.getHeight()/20*6;*/
		
		//---------------------------------nextShape View---------------------------------------
		for (int i = 0; i<4; ++i){
			int x = nextShape.x(i);
			int y = nextShape.y(i);
			drawNextShape(g, XCoordinateNextShapeView+NextShapeViewWidth/2 + x *squareWidth(),
			YCoordinateNextShapeView + NextShapeViewHeight/2 + y*squareHeight(), 
			nextShape.getShape());
		}
		
		
	  //-----------------------------------Draw Shape in board----------------------------------------
	    int BoardTop = this.getHeight() - YCoordinateRect - RectHeight; // the actual bottom
		//int BoardTop = YCoordinateRect;
		int BoardLeft = XCoordinateRect;

		for(int i = 0; i< BOARDHEIGHT; i++){
			for (int j = 0; j < BOARDWIDTH; ++j){
				ShapeBody shape = shapeAt(j, BOARDHEIGHT - i -1);
				
				if (shape !=ShapeBody.EmptyShape){
					drawShape(g, BoardLeft + j*squareWidth(), BoardTop + i * squareHeight(), shape);
				}
			}
		}
		
		if (curShape.getShape()!= ShapeBody.EmptyShape){
			for (int i = 0; i<4; ++i){
				int x = curX + curShape.x(i);
				int y = curY - curShape.y(i);
				drawShape(g, BoardLeft + x *squareWidth(), BoardTop + (BOARDHEIGHT - y - 1) * squareHeight(),
						curShape.getShape());
			}
		}
		

		
	}
	
	
	// ------------------------------Start---------------------------------------------------
	
	public void start(int speed, int boardwidth, int boardheight, int scorefactor, int difficulty){
		if(isPaused)
			return;
		
		
		
		//setSpeedFactor(100);
		//int speed, int width, int height, int scorefactor, int leveldifficulty
		initialize(speed, boardwidth, boardheight, scorefactor, difficulty);
		
		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		
		
		
		System.out.println(123);
		clearBoard();
		newTwoPiece();
		timer.start(); 
	}
	
	
	
	//--------------------------Remove a full line------------------------
	private void removeFullLines(){
		int numFullLines = 0;
		
		for(int i = BOARDHEIGHT - 1; i >= 0; --i){
			boolean lineIsFull = true;
			
			for (int j = 0 ; j < BOARDWIDTH; ++j){
				if(shapeAt(j,i) == ShapeBody.EmptyShape){
					lineIsFull = false;
					break;
				}
			}
			
			if (lineIsFull){
				++numFullLines;
				
				for(int k = i; k < BOARDHEIGHT -1; ++k){
					for (int j = 0; j <BOARDWIDTH; ++j){
						board[k*BOARDWIDTH + j] = shapeAt(j, k + 1);
					}
				}
			}
			
			if(numFullLines > 0){
				numLinesRemoved += numFullLines;
			//	statusBar.setText(String.valueOf(numLinesRemoved));
				isFallingFinished = true;
				curShape.setShape(ShapeBody.EmptyShape);
				repaint();
			}
			
		}	
	}
	
	//Drop 
/*	private void dropDown(){
		int newY = curY;
		
		while(newY > 0){
			if(!tryMove(curShape,curX, newY-1))
				break;
			
			--newY;

		}
		
		pieceDropped();
	}*/
	
}
