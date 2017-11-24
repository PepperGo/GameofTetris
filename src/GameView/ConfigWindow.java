package GameView;
import GameView.TetrisGame;


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ConfigWindow extends JFrame {
	
	   private static JPanel sliderPanel;
	   private static JTextField textField;
	   private static ChangeListener Mlistener;
	   private static ChangeListener Nlistener;
	   private static ChangeListener Slistener;
	   private static ChangeListener Widthlistener;
	   private static ChangeListener Heightlistener;
	   private static ChangeListener Sizelistener;
	 
	   private static int scoringFactor = 5;  //1---10
	   private static int levelDifficulty = 35;//20---50
	   private static int speedFactor = 8;//0.1 --- 1.0
	   private static int boardwidth = 10;
	   private static int boardheight = 20;
	   
	   
	
	   
	   

	//------------------get Value------------------------------

	
	   private static void setScore(int s){
		   scoringFactor = s;

	   }
	   
	   public int getScore(){
		   return scoringFactor;
	   }
	   
	   private static void setLevel(int l){
		   levelDifficulty = l;
	   }
	   
	   public int getLevel(){
		   return levelDifficulty;
	   }
	   
	   private static void setSpeed(int s){
		   speedFactor = s;
	   }
	   
	   public int getSpeed(){
		   return speedFactor;
	   }
	   
	   

	   private static void setWidth(int s){
		   boardwidth = s;

	   }
	   
	   public int getWidth(){
		   return boardwidth;
	   }
	   

	   private static void setHeight(int s){
		   boardheight = s;

	   }
	   
	   public int getHeight(){
		   return boardheight;
	   }
	   
	   
	   
/*	   void changeStateOfTetris(TetrisView t, int score, int level, 
			   int speed)
	   {
			t.setScoreFactor(score);
			t.setLevelDifficulty(level);
			t.setSpeedFactor(speed/10);  
	   }*/

	 //  -------------------Slider----------------------------------------
	public static void openConfigWindow(TetrisView t){

		JFrame config = new JFrame("Configuration");
		config.setSize(350,500);
		config.setLocation(400, 300);
		config.setResizable(false);
	//	config.setLocationRelativeTo(frame);
		config.setLayout(null);
		changeConstant("Constant", config, 350, 400, t);
		config.setVisible(true);

		
		System.out.println("scoringFactor:___________"+scoringFactor);
		System.out.println("speedFactor:_____________"+speedFactor);
		
	}
	
	public static void changeConstant(String name, final JFrame config, int x, int y, final TetrisView t){
	
		

		sliderPanel = new JPanel();
		sliderPanel.setSize(x,y);
	    sliderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

	   // SCORE FACTOR---------------------------------------------
	      Mlistener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	               
	               JSlider source = (JSlider) event.getSource();
	                setScore(source.getValue());
	                //System.out.println(scoringFactor);
	               // System.out.println(getScore());
	           
	            }
	         };

	      JSlider mSlider = new JSlider();

	      mSlider = new JSlider(1,10,scoringFactor);
	      mSlider.setPaintTicks(true);
	      mSlider.setPaintLabels(true);
	      mSlider.setMajorTickSpacing(1);
	      mSlider.setMinorTickSpacing(1);
	      mSlider.setSnapToTicks(true);
	      addSlider(mSlider, "M:Scoring Factor", Mlistener);

	      // LEVEL OF DIFFICULTY----------------------------------
	      JSlider nSlider = new JSlider();
	      Nlistener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	              
	               JSlider source = (JSlider) event.getSource();
	               setLevel(source.getValue());
	          //     t.setScoreFactor(soure.getValue());
	         
	            }
	         };
	      
	      nSlider = new JSlider(20,50,levelDifficulty);
	      nSlider.setPaintTicks(true);
	      nSlider.setPaintLabels(true);
	      nSlider.setSnapToTicks(true);
	      nSlider.setMajorTickSpacing(5);
	      nSlider.setMinorTickSpacing(1);
	      addSlider(nSlider, "N:Level of Difficulty", Nlistener);

	      //SPEED FACTOR----------------------------------------------

	      JSlider sSlider = new JSlider();
	      Slistener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	              
	               JSlider source = (JSlider) event.getSource();
	               setSpeed(source.getValue());
	              // setSpeed(100);
	          //     t.setScoreFactor(soure.getValue());
	       
	            }
	         };
	      sSlider = new JSlider(1,10,speedFactor);
	      sSlider.setPaintTicks(true);
	      sSlider.setPaintLabels(true);
	      sSlider.setSnapToTicks(true);
	      sSlider.setMajorTickSpacing(1);
	      sSlider.setMinorTickSpacing(1);

	      
	      Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
	      labelTable.put(1, new JLabel("0.1"));
	      labelTable.put(5, new JLabel("0.5"));
	      labelTable.put(10, new JLabel("1.0"));

	      sSlider.setLabelTable(labelTable);
	      addSlider(sSlider, "S:Speed Factor", Slistener);

	 
	      //-------------------------------------width of main area-----------------------------------------------------------------------
	      JSlider wSlider = new JSlider();
	      Widthlistener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	              
	               JSlider source = (JSlider) event.getSource();
	               setWidth(source.getValue());
	          //     t.setScoreFactor(soure.getValue());
	         
	            }
	         };
	      
	      wSlider = new JSlider(10,20,boardwidth);
	      wSlider.setPaintTicks(true);
	      wSlider.setPaintLabels(true);
	      wSlider.setSnapToTicks(true);
	      wSlider.setMajorTickSpacing(5);
	      wSlider.setMinorTickSpacing(1);
	      addSlider(wSlider, "Width of Main Area", Widthlistener);
	      
	      //---------------------------------------height of main area------------------------------------------
	      JSlider hSlider = new JSlider();
	      Heightlistener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	              
	               JSlider source = (JSlider) event.getSource();
	               setHeight(source.getValue());
	          //     t.setScoreFactor(soure.getValue());
	         
	            }
	         };
	      
	      hSlider = new JSlider(15,20,boardheight);
	      hSlider.setPaintTicks(true);
	      hSlider.setPaintLabels(true);
	      hSlider.setSnapToTicks(true);
	      hSlider.setMajorTickSpacing(1);
	      hSlider.setMinorTickSpacing(1);
	      addSlider(hSlider, "Height of Main Area", Heightlistener);
	      
	      
	      //size of the square-----------------------------------------
	      JSlider squareSlider = new JSlider();
	      Sizelistener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	              
	               JSlider source = (JSlider) event.getSource();
	           //    setLevel(source.getValue());
	          //     t.setScoreFactor(soure.getValue());
	         
	            }
	         };
	      
	      squareSlider = new JSlider(10,20,10);
	      squareSlider.setPaintTicks(true);
	      squareSlider.setPaintLabels(true);
	      squareSlider.setSnapToTicks(true);
	      squareSlider.setMajorTickSpacing(5);
	      squareSlider.setMinorTickSpacing(1);
	      addSlider(squareSlider, "Size of square", Sizelistener);
	      
	      
	      //OK BUTTON--------------------------------------------------
	      JButton okButton = new JButton("OK");
	      okButton.addMouseListener(new MouseAdapter(){
	    	 public void mouseClicked(MouseEvent e){
	    		super.mouseClicked(e);

	    		
	    		System.out.println("OK!");
	    

	    	//	public void setConfig(int speed, int width, int height, int scorefactor, int leveldifficulty)
	    	//	t.setConfig(speedFactor, 1, 1, 1, 1);
	    		t.start(speedFactor, boardwidth, boardheight, scoringFactor, levelDifficulty);
	    		//t.updateTimer(speedFactor);
	    		

	    		
	    		config.dispose();
	    		
	    		
	    		
	    		
	 
	    	 } 
	      });
	     
	      sliderPanel.add(okButton);
	      
	      
	      
	      
	      config.add(sliderPanel, BorderLayout.CENTER);
	    //  config.add(sliderPanel);
	      
	     // button.setBounds(10,10,200,300);
		
		
	}
	
	   public static void addSlider(JSlider s, String description, ChangeListener c )
	   {
	      s.addChangeListener(c);
	      JPanel panel = new JPanel();
	      panel.add(s);
	      panel.add(new JLabel(description));
	      sliderPanel.add(panel);
	   }


	   
	   
	
	 /*  ConfigWindow(TetrisView tetris)
	   {
	      setTitle("Configuration");
	      setSize(350, 500);
	      setLocation(400, 300);
	      setResizable(false);
	     // setLayout(null);
	      
	       
	      
	      sliderPanel = new JPanel();
	      sliderPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

	      // common listener for all sliders
	      listener = new ChangeListener()
	         {
	            public void stateChanged(ChangeEvent event)
	            {
	               // update text field when the slider value changes
	               JSlider source = (JSlider) event.getSource();
	               textField.setText("" + source.getValue());
	            }
	         };

	      // add a plain slider

	      JSlider slider = new JSlider();
	      addSlider(slider, "Plain");

	      // add a slider with major and minor ticks

	      slider = new JSlider();
	      slider.setPaintTicks(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(5);
	      addSlider(slider, "Ticks");

	      // add a slider that snaps to ticks

	      slider = new JSlider();
	      slider.setPaintTicks(true);
	      slider.setSnapToTicks(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(5);
	      addSlider(slider, "Snap to ticks");

	      // add a slider with no track

	      slider = new JSlider();
	      slider.setPaintTicks(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(5);
	      slider.setPaintTrack(false);
	      addSlider(slider, "No track");

	      // add an inverted slider

	      slider = new JSlider();
	      slider.setPaintTicks(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(5);
	      slider.setInverted(true);
	      addSlider(slider, "Inverted");

	      // add a slider with numeric labels

	      slider = new JSlider();
	      slider.setPaintTicks(true);
	      slider.setPaintLabels(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(5);
	      addSlider(slider, "Labels");

	      // add a slider with alphabetic labels

	      slider = new JSlider();
	      slider.setPaintLabels(true);
	      slider.setPaintTicks(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(5);

	      Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
	      labelTable.put(0, new JLabel("A"));
	      labelTable.put(20, new JLabel("B"));
	      labelTable.put(40, new JLabel("C"));
	      labelTable.put(60, new JLabel("D"));
	      labelTable.put(80, new JLabel("E"));
	      labelTable.put(100, new JLabel("F"));

	      slider.setLabelTable(labelTable);
	      addSlider(slider, "Custom labels");

	      // add a slider with icon labels

	      slider = new JSlider();
	      slider.setPaintTicks(true);
	      slider.setPaintLabels(true);
	      slider.setSnapToTicks(true);
	      slider.setMajorTickSpacing(20);
	      slider.setMinorTickSpacing(20);

	      labelTable = new Hashtable<Integer, Component>();

	      // add card images

	      labelTable.put(0, new JLabel(new ImageIcon("nine.gif")));
	      labelTable.put(20, new JLabel(new ImageIcon("ten.gif")));
	      labelTable.put(40, new JLabel(new ImageIcon("jack.gif")));
	      labelTable.put(60, new JLabel(new ImageIcon("queen.gif")));
	      labelTable.put(80, new JLabel(new ImageIcon("king.gif")));
	      labelTable.put(100, new JLabel(new ImageIcon("ace.gif")));

	      slider.setLabelTable(labelTable);
	      addSlider(slider, "Icon labels");

	      // add the text field that displays the slider value

	      textField = new JTextField();
	      add(sliderPanel, BorderLayout.CENTER);
	      add(textField, BorderLayout.SOUTH);
	   }

	   *//**
	    * Adds a slider to the slider panel and hooks up the listener
	    * @param s the slider
	    * @param description the slider description
	    *//*
	   public void addSlider(JSlider s, String description)
	   {
	      s.addChangeListener(listener);
	      JPanel panel = new JPanel();
	      panel.add(s);
	      panel.add(new JLabel(description));
	      sliderPanel.add(panel);
	   }

	   public static final int DEFAULT_WIDTH = 350;
	   public static final int DEFAULT_HEIGHT = 450;

	   private JPanel sliderPanel;
	   private JTextField textField;
	   private ChangeListener listener;*/

}
