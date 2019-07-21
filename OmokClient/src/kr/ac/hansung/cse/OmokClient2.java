package kr.ac.hansung.cse;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class OmokBoard extends Canvas{
  	
	
		String myblack = OmokClient2.myblack;
		String mywhite = OmokClient2.mywhite;
		String soundControl = OmokClient2.soundControl;
		
		private boolean enable=false;
		private boolean running=false; 
		private PrintWriter writer;
		private Graphics gboard, gbuff; 
		private Image buff; 
		
		
		public static final int BLACK = 1,WHITE = -1;
		private int[][]map; 
		private int size; 
		private int cell; 
		private String info="[ 1. ]";
		private int color=BLACK; 

		OmokBoard(int s, int c) { // 바둑판 크기(s=15, c=30)
			this.size = s; this.cell = c;
			map = new int[size+2][]; 
			for(int i=0;i < map.length;i++)
				map[i]=new int[size+2];
			setSize(size*(cell+2)+size, size*(cell+2)+size); 
			
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent me){
					if(!enable)return; 
					
					int x=(int)Math.round(me.getX()/(double)cell);
					int y=(int)Math.round(me.getY()/(double)cell);
					
					if(x==0 || y==0 || x==size+1 || y==size+1)return;
					
					if(map[x][y]==BLACK || map[x][y]==WHITE)return;
					//쌍삼방지
					if(map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 
							|| map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 && y >= 4
							|| map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x+1][y-1] == color && map[x+2][y-2] == color && map[x+3][y-3] == 0 && x <=size-4 && y >= 4
							|| map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x <= size-4
							|| map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x <= size-4 && y <=size-4
							|| map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0 && y <=size-4
							|| map[x-1][y] == color && map[x-2][y] == color && map[x-3][y] == 0 && x >= 4 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && x >= 4 && y <=size-4
							|| map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 && map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 
							|| map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 && map[x+1][y-1] == color && map[x+2][y-2] == color && map[x+3][y-3] == 0 && x<=size-4
							|| map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 && map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x<=size-4
							|| map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 && map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x<=size-4 && y <= size-4
							|| map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 && map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0 && y <= size-4
							|| map[x-1][y-1] == color && map[x-2][y-2] == color && map[x-3][y-3] == 0 && x >= 4 && y >= 4 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && y <= size-4
							|| map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 && y >= 4 && map[x+1][y-1] == color && map[x+2][y-2] == color && map[x+3][y-3] == 0 && x <= size-4
							|| map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 && y >= 4 && map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x <= size-4
							|| map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 && y >= 4 && map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x <= size-4 && y <= size-4
							|| map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 && y >= 4 && map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0 && y <= size-4
							|| map[x][y-1] == color && map[x][y-2] == color && map[x][y-3] == 0 && y >= 4 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && x >= 4 && y <= size-4
							|| map[x+1][y-1] == color && map[x+1][y-2] == color && map[x+1][y-3] == 0 && x <= size-4 && y >= 4 && map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x <= size-4
							|| map[x+1][y-1] == color && map[x+1][y-2] == color && map[x+1][y-3] == 0 && x <= size-4 && y >= 4 && map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x <= size-4 && y <= size-4
							|| map[x+1][y-1] == color && map[x+1][y-2] == color && map[x+1][y-3] == 0 && x <= size-4 && y >= 4 && map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0 && y <= size-4
							|| map[x+1][y-1] == color && map[x+1][y-2] == color && map[x+1][y-3] == 0 && x <= size-4 && y >= 4 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && x >= 4 && y <= size-4
							|| map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x<=size-4 && map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x <= size-4 && y <= size-4
							|| map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x<=size-4 && map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0 && y <= size-4
							|| map[x+1][y] == color && map[x+2][y] == color && map[x+3][y] == 0 && x<=size-4 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && x >= 4 && y <= size-4
							|| map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x <= size-4 && y <= size-4 && map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0
							|| map[x+1][y+1] == color && map[x+2][y+2] == color && map[x+3][y+3] == 0 && x <= size-4 && y <= size-4 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && x >= 4
							|| map[x][y+1] == color && map[x][y+2] == color && map[x][y+3] == 0 && map[x-1][y+1] == color && map[x-2][y+2] == color && map[x-3][y+3] == 0 && x >= 4 && y <= size-4
							|| map[x-1][y-1] == color && map[x+1][y+1] == color && map[x+1][y-1] == color && map[x-1][y+1] ==color && map[x-2][y-2] == 0 && map[x+2][y+2] == 0 && map[x+2][y-2] == 0 && map[x-2][y+2] == 0 && x>=3 && y>=3 && x <= size-3 && y <= size-3 
							|| map[x][y-1] == color && map[x][y+1] == color && map[x+1][y] == color && map[x-1][y] ==color && map[x-2][y] == 0 && map[x+2][y] == 0 && map[x][y-2] == 0 && map[x][y+2] == 0 && x>=3 && y>=3 && x <= size-3 && y <= size-3 
					) {
						OmokClient2.msgView.append("쌍삼자리입니다.\n");
						return;
						}
					
					writer.println("[STONE]" + x + " "+y);
					map[x][y]=color;
					
					if(soundControl.equals("on"))
						playSound();
					repaint();
					
					if(check(new Point(x, y), color)){
						OmokClient2.msgView.append("당신이 승리하였습니다.\n");
						writer.println("[WIN]");
					}
					else OmokClient2.msgView.append("당.\n");
					
					
					enable=false;
				}
			});
		}
		
		public boolean isRunning(){ 
			return running;
		}
		
		public void startGame(String col){ 
			running=true;
			if(col.equals("BLACK")){ 
				enable=true;
				color=BLACK;
				OmokClient2.msgView.append("흑돌을 잡으셨습니다.\n");
				
			}   
			else{  
				enable=false;
				color=WHITE;
				OmokClient2.msgView.append("백돌을 잡으셨습니다.\n");
			}
		}

		public void stopGame(){
			reset();
			writer.println("[STOPGAME]"); 
			enable=false;
			running=false;
		}

		public void putOpponent(int x, int y){ 
			map[x][y]=-color;
			if(soundControl.equals("on"))
				playSound();
			OmokClient2.msgView.append("기다리세요.\n");
			repaint();
		}
		public void RemoveOpponent(int x, int y){  
			map[x][y]= 0;
			repaint();
		}

		public void setEnable(boolean enable){
			this.enable=enable;
		}
		public boolean IsEnable(){
			return this.enable;
		}
		
		public void setWriter(PrintWriter writer){
			this.writer=writer;
		}

		
		public void update(Graphics g){ 
			paint(g);                           
		}
		
		
		public void paint(Graphics g){           
			if(gbuff==null){  
				buff=createImage(getWidth(),getHeight());
				gbuff=buff.getGraphics();
			}
			try {
				drawBoard(g);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		public void reset(){ 
			for(int i=0;i<map.length;i++)
				for(int j=0;j<map[i].length;j++)
					map[i][j]=0;
			OmokClient2.msgView.append("3.\n");
			repaint();
		}

		private void drawLine() throws IOException{ 
			gbuff.setColor(Color.black);
			BufferedImage image;
			image = ImageIO.read(getClass().getResourceAsStream("/src/images/board.png"));
			gbuff.drawImage(image, 0,  0, null);
			for(int i=1; i<=size + 1;i++){
				gbuff.drawLine(cell - cell, i*cell - cell, cell*size + cell, i*cell - cell);
				gbuff.drawLine(i*cell - cell , cell - cell, i*cell- cell , cell*size + cell);
			}
		}

		private void drawBlack(int x, int y) throws IOException{ 
			String myblack = OmokClient2.myblack;
			
			Graphics2D gbuff=(Graphics2D)this.gbuff;
			BufferedImage image;
			image = ImageIO.read(getClass().getResourceAsStream(myblack));
			gbuff.drawImage(image, x*cell-cell/2,  y*cell-cell/2, null);
		}

		private void drawWhite(int x, int y) throws IOException{ 
			String mywhite = OmokClient2.mywhite;
			
			Graphics2D gbuff=(Graphics2D)this.gbuff;
			BufferedImage image;
			image = ImageIO.read(getClass().getResourceAsStream(mywhite));
			gbuff.drawImage(image, x*cell-cell/2,  y*cell-cell/2, null);
		}

		private void drawStones() throws IOException{ 
			for(int x=1; x<=size;x++)
				for(int y=1; y<=size;y++){
					if(map[x][y]==BLACK)
						drawBlack(x, y);
					else if(map[x][y]==WHITE)
						drawWhite(x, y);
				}
		}

		synchronized private void drawBoard(Graphics g) throws IOException{ 
			
			gbuff.clearRect(0, 0, getWidth(), getHeight());
			drawLine();
			drawStones();
			gbuff.setColor(Color.red);
			g.drawImage(buff, 0, 0, this);
		}

		
		
		private boolean check(Point p, int col){
			if(count(p, 1, 0, col)+count(p, -1, 0, col)==4)
				return true;
			if(count(p, 0, 1, col)+count(p, 0, -1, col)==4)
				return true;
			if(count(p, -1, -1, col)+count(p, 1, 1, col)==4)
				return true;
			if(count(p, 1, -1, col)+count(p, -1, 1, col)==4)
				return true;
			return false;
		}

		private int count(Point p, int dx, int dy, int col){
			int i=0;
			for(; map[p.x+(i+1)*dx][p.y+(i+1)*dy]==col ;i++);
			return i;
		}
		
	
		public void playSound() {
			
			AudioInputStream stream;
			AudioFormat format;
			DataLine.Info info;
			File sound = new File("sound2.wav");
		
			Clip clip;
			try{
				stream = AudioSystem.getAudioInputStream(sound);
				format = stream.getFormat();
				info = new DataLine.Info(Clip.class, format);
				clip = (Clip)AudioSystem.getLine(info);
				clip.open(stream);
				clip.start();
			}
			catch(Exception e){
				System.out.println("sound error\n");
			}

		}
	}


	public class OmokClient2 extends JFrame implements Runnable, ActionListener {
		
		public static String myblack = "/src/images/black1.png";
		public static String mywhite = "/src/images/white1.png";
		public static String soundControl;
		
		
		public static TextArea msgView=new TextArea("", 1,1,1);

		private TextField sendBox=new TextField("");
		public static TextField nameBox=new TextField(); 
		
		private TextField roomBox=new TextField("0");

		
		private Label pInfo=new Label("4:  5");
		private Label rInfo=new Label("방 목록");
		private List pList=new List(); 
		private List rList=new List();	
		public Button startButton=new Button("7");   
		public Button stopButton=new Button("8");      
		private Button enterButton=new Button("9");   
		private Button exitButton=new Button("10");     
		JLabel enterButton2 = new JLabel("");
		JLabel exitButton2 = new JLabel("");
		JLabel close2 = new JLabel("");
		
		private Label infoView=new Label("11.", 1);
		private OmokBoard board=new OmokBoard(15,50); 
		private BufferedReader reader; 
		private PrintWriter writer; 
		private Socket socket; 
		private int roomNumber=-1; 
		private String userName=null; 

		Panel p2=new Panel();
		Panel p=new Panel();
		Panel p3=new Panel();
		Panel p4 = new Panel();
		Panel p5 = new Panel();
		Panel p6 = new Panel();

		
		JLabel go = new JLabel("");
		JLabel no = new JLabel("");
		JLabel readyback = new JLabel("");
		JLabel battleground = new JLabel("");
		JLabel emotion = new JLabel("");
		JLabel help = new JLabel("");
		JLabel accept = new JLabel("");
		JLabel refuse = new JLabel("");

		JLabel angry = new JLabel("");
		JLabel happy = new JLabel("");
		JLabel question = new JLabel("");
		JLabel cry = new JLabel("");
		
		JLabel angry2 = new JLabel("");
		JLabel happy2 = new JLabel("");
		JLabel question2 = new JLabel("");
		JLabel cry2 = new JLabel("");
		
		JLabel timer1 = new JLabel("");
		JLabel timer2 = new JLabel("");

		Timer tmr1;
		Timer tmr2;
		Timer etmr1;
		Timer etmr2;
		

		int timecount1 = 20;
		int timecount2 = 20;
		int emotioncount1 = 3;
		int emotioncount2 = 3;
		public void emotionTask1() {
		    TimerTask emotionTask1 = new TimerTask() {
		        @Override
		        public void run() {
		        	emotioncount1--;
		    		if(emotioncount1 == 0) {
		
		    			emotioncount1 = 3;
		    			cry2.setVisible(false);
		    			angry2.setVisible(false);
		    			question2.setVisible(false);
		    			happy2.setVisible(false);
		    			etmr1.cancel();

		    		}
		        }
		    }; 
		    etmr1 = new Timer();
		    etmr1.schedule(emotionTask1, 0, 1000);
		}
		public void emotionTask2() {
		    TimerTask emotionTask2 = new TimerTask() {
		        @Override
		        public void run() {
		        	emotioncount2--;
		    		if(emotioncount2 == 0) {
		    
		    			emotioncount2 = 3;

		    			cry2.setVisible(false);
		    			angry2.setVisible(false);
		    			question2.setVisible(false);
		    			happy2.setVisible(false);
		    			etmr2.cancel();

		    		}
		        }
		    }; 
		    etmr2 = new Timer();
		    etmr2.schedule(emotionTask2, 0, 1000);
		}

		public void tempTask1() {
		    TimerTask task1 = new TimerTask() {
		        @Override
		        public void run() {
		        	timer1.setText(Integer.toString(timecount1));
		    		timecount1--;
		    		if(timecount1 == 0) {
		    			writer.println("[TIMEOVER]");
		    			timecount1 = 20;

		    		}
		        }
		    }; 
		    tmr1 = new Timer();
		    tmr1.schedule(task1, 0, 1000);
		}
		
		public void tempTask2() {
		    TimerTask task2 = new TimerTask() {
		        @Override
		        public void run() {
		        	timer2.setText(Integer.toString(timecount2));
		    		timecount2--;

		    		if(timecount2 == 0) {
		    			timecount2 = 20;
		    	
		    		}
		        }
		    }; 
		    tmr2 = new Timer();
		    tmr2.schedule(task2, 0, 1000);
		}

		public OmokClient2(String title){ 
			super(title);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			getContentPane().setLayout(null);   
			infoView.setFont(new Font("", Font.BOLD, 30));
			infoView.setBounds(831,281,418,40);
			infoView.setBackground(new Color(204, 114, 61));
			nameBox.setBackground(Color.WHITE);
			nameBox.setForeground(Color.BLACK);
			nameBox.setFont(new Font("aharoni", Font.BOLD, 30));
			nameBox.setBounds(165, 161, 305, 58);
			getContentPane().add(nameBox);
			roomBox.setFont(new Font("aharoni", Font.BOLD, 30));
			roomBox.setBounds(650, 161, 310, 58);
			
			getContentPane().add(roomBox);
			getContentPane().add(infoView);
			Toolkit toolkit = getToolkit();
			Dimension size = toolkit.getScreenSize();
			setLocation(size.width/2 - 640, size.height/2 - 500);
			

			timer1.setBounds(893, 900, 40, 40);
			timer1.setFont(new Font("aharoni", Font.BOLD, 30));
			getContentPane().add(timer1);
			timer1.setVisible(false);
			timer1.setText("20");
			
			timer2.setBounds(1150, 900, 40, 40);
			timer2.setFont(new Font("aharoni", Font.BOLD, 30));
			getContentPane().add(timer2);
			timer2.setVisible(false);
			timer2.setText("20");
			
			
			
			
			p.setBackground(new Color(200,255,255));
			p.setLayout(new GridLayout(3,3));
			
			p.add(enterButton);
			
			p.add(exitButton);
			exitButton.setVisible(false);
			enterButton.setEnabled(false);
			p.setBounds(2000,366,250,70);
			infoView.setVisible(false);
			startButton.setVisible(false);
			stopButton.setVisible(false);
			
			
			
			
			p2.setBackground(new Color(50, 205, 50));
			p2.setLayout(new BorderLayout());
			p2.add(pList,BorderLayout.CENTER);
			pList.setFont(new Font("aharoni", Font.BOLD, 20));
			p2.setBounds(45,340,555,300);

			p3.setLayout(new BorderLayout());
			p3.add(msgView, BorderLayout.CENTER);
			
			p4.setBackground(new Color(50, 205, 50));
			p4.setLayout(new BorderLayout());

			p4.add(rList, BorderLayout.CENTER);
			p4.setBounds(670, 340, 555, 300);
			
			p5.setBackground(new Color(180,126,66));
			p5.setBounds(960, 670, 150, 150);
			p5.setLayout(new GridLayout(2, 2));
			getContentPane().add(p5);
			p5.setVisible(false);

			p5.add(cry);
			p5.add(angry);
			p5.add(question);
			p5.add(happy);
			
			question2.setIcon(new ImageIcon(Main.class.getResource("/src/images/question.png")));
			cry2.setIcon(new ImageIcon(Main.class.getResource("/src/images/cry.png")));
			angry2.setIcon(new ImageIcon(Main.class.getResource("/src/images/angry.png")));
			happy2.setIcon(new ImageIcon(Main.class.getResource("/src/images/happy.png")));
			getContentPane().add(cry2);
			getContentPane().add(question2);
			getContentPane().add(happy2);
			getContentPane().add(angry2);
			
			p6.setBounds(1230, 18, 30, 30);
			p6.setLayout(new BorderLayout());
			p6.setBackground(new Color(180,126,66));
			getContentPane().add(p6);
			
			cry2.setVisible(false);
			angry2.setVisible(false);
			happy2.setVisible(false);
			question2.setVisible(false);
			
			
			
			

			msgView.setEditable(false);
			sendBox.setFont(new Font("aharoni", Font.BOLD, 30));
			sendBox.setBounds(45, 860, 1180, 50);
			getContentPane().add(sendBox);
			
			p3.setBounds(45, 660, 1180,200);
			enterButton2.setBounds(1000, 147, 233, 83);
			getContentPane().add(enterButton2);
			rList.setFont(new Font("aharoni", Font.BOLD, 20));
			
			
			enterButton2.setIcon(new ImageIcon(Main.class.getResource("/src/images/enter.png")));
			exitButton2.setBounds(831, 209, 421, 68);
			getContentPane().add(exitButton2);
			
			exitButton2.setIcon(new ImageIcon(Main.class.getResource("/src/images/toroom.png")));
			exitButton2.setVisible(false);
			
			startButton.setBounds(50, 80, 78, 25);
			getContentPane().add(startButton);

			Button emotion2 = new Button("12");
			emotion2.setBounds(1145, 860, 80,50);
			getContentPane().add(emotion2);
			emotion2.setVisible(false);
			
			emotion.setIcon(new ImageIcon(Main.class.getResource("/src/images/duo.png")));
			emotion.setBounds(826, 706, 426, 210);
			getContentPane().add(emotion);
			emotion.setVisible(false);
			
			close2.setIcon(new ImageIcon(Main.class.getResource("/src/images/close.png")));
			close2.setBounds(1230, 7, 28, 30);
			
			p6.add(close2, "Center");
			p6.setVisible(true);
			close2.setVisible(true);
			
			
			cry.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {			
					p5.setVisible(false);
					happy2.setVisible(false);
					question2.setVisible(false);
					angry2.setVisible(false);
					
					cry2.setBounds(860, 630, 70, 70);
					cry2.setVisible(true);	
					writer.println("[cry]");
					emotionTask1();
				}
			});
			cry.setIcon(new ImageIcon(Main.class.getResource("/src/images/cry.png")));
			question.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					p5.setVisible(false);
					happy2.setVisible(false);
					angry2.setVisible(false);
					cry2.setVisible(false);
					
					question2.setBounds(860, 630, 70, 70);
					question2.setVisible(true);
					writer.println("[question]");
					emotionTask1();
			
				}
				
			});
			question.setIcon(new ImageIcon(Main.class.getResource("/src/images/question.png")));
			happy.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					p5.setVisible(false);
					angry2.setVisible(false);
					question2.setVisible(false);
					cry2.setVisible(false);
					
					happy2.setBounds(860, 630, 70, 70);
					happy2.setVisible(true);
					writer.println("[happy]");
					emotionTask1();
				}
				
			});
			happy.setIcon(new ImageIcon(Main.class.getResource("/src/images/happy.png")));
			angry.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					p5.setVisible(false);
					happy2.setVisible(false);
					question2.setVisible(false);
					cry2.setVisible(false);
					angry2.setBounds(860, 630, 70, 70);
					angry2.setVisible(true);
					writer.println("[angry]");
					
					emotionTask1();
				}
				
			});
			angry.setIcon(new ImageIcon(Main.class.getResource("/src/images/angry.png")));
			
			emotion2.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					board.setEnable(false);
					p5.setVisible(true);
					
				}
				
			});
			
			
			rList.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					try{
						msgView.setText("");
						
						// TODO Auto-generated method stub
						if(e.getItem() != null) {
							if(rList.getItemCount() != 0) {
						
								String temp = rList.getItem(Integer.parseInt(e.getItem().toString()));
								
								writer.println("[ROOM]"+temp.substring(0, temp.indexOf("번방")));
								writer.println("[ROOMLIST]");
								board.setVisible(true);
								exitButton.setVisible(true);
								startButton.setVisible(false);
								stopButton.setVisible(false);
								enterButton2.setVisible(false);
								roomBox.setVisible(false);
								go.setVisible(true);
								no.setVisible(true);
								nameBox.setVisible(false);
								readyback.setVisible(false);
								battleground.setVisible(true);
								pInfo.setVisible(false);
								rInfo.setVisible(false);
								p3.setBounds(826, 324, 426, 240);
								p2.setVisible(false);
								p4.setVisible(false);
								close2.setVisible(false);
								infoView.setVisible(true);
								exitButton2.setVisible(true);
								emotion.setVisible(true);
								help.setVisible(false);
								p6.setVisible(false);
								timer1.setVisible(true);
								timer2.setVisible(true);
								
								sendBox.setBounds(826, 564, 380, 50);
								emotion2.setBounds(1206, 564, 47, 50);
								emotion2.setVisible(true);
								sendBox.repaint();
								emotion2.repaint();
								
								
							}
						}
					}
					catch(Exception ie){
					}
				}
			});
			
			
			enterButton2.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					enterButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/enter-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					enterButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/enter.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					try{
						msgView.setText("");
						if(Integer.parseInt(roomBox.getText())<1){
							infoView.setText("13");
							return;
						}
						writer.println("[ROOM]"+Integer.parseInt(roomBox.getText()));
						writer.println("[ROOMLIST]");
						board.setVisible(true);
						exitButton.setVisible(true);
						startButton.setVisible(false);
						stopButton.setVisible(false);
						enterButton2.setVisible(false);
						roomBox.setVisible(false);
						go.setVisible(true);
						no.setVisible(true);
						nameBox.setVisible(false);
						readyback.setVisible(false);
						battleground.setVisible(true);
						pInfo.setVisible(false);
						rInfo.setVisible(false);
						p3.setBounds(826, 324, 426, 240);
						p2.setVisible(false);
						p4.setVisible(false);
						close2.setVisible(false);
						infoView.setVisible(true);
						exitButton2.setVisible(true);
						emotion.setVisible(true);
						help.setVisible(false);
						p6.setVisible(false);
						timer1.setVisible(true);
						timer2.setVisible(true);
						
						sendBox.setBounds(826, 564, 380, 50);
						emotion2.setBounds(1206, 564, 47, 50);
						emotion2.setVisible(true);
						sendBox.repaint();
						emotion2.repaint();
					
						
					}catch(Exception ie){
						infoView.setText("14.");
					}
				}
			});	
			
			//���Ƿ� ���ư���
			exitButton2.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					exitButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/toroom-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					exitButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/toroom.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					msgView.setText("");
					String roomBox2 = roomBox.getText();
					goToWaitRoom();
					rList.removeAll();
					writer.println("[EMPTY]"+Integer.parseInt(roomBox2));
					writer.println("[ROOMLIST]");
					board.setVisible(false);
					exitButton.setVisible(false);
					startButton.setVisible(false);
					stopButton.setVisible(false);
					enterButton2.setVisible(true);
					roomBox.setVisible(true);
					go.setVisible(false);
					no.setVisible(false);
					nameBox.setVisible(true);
					readyback.setVisible(true);
					battleground.setVisible(false);
					pInfo.setVisible(true);
					rInfo.setVisible(true);
					p3.setBounds(45, 660, 1180,250);
					p2.setVisible(true);
					p4.setVisible(true);
					infoView.setVisible(false);
					exitButton2.setVisible(false);
					close2.setVisible(true);
					emotion.setVisible(false);
					help.setVisible(false);
					refuse.setVisible(false);
					accept.setVisible(false);
					p6.setVisible(true);
					timer1.setVisible(false);
					timer2.setVisible(false);
					
					cry2.setVisible(false);
					happy2.setVisible(false);
					question2.setVisible(false);
					angry2.setVisible(false);
					
					emotion2.setVisible(false);
					sendBox.setBounds(45, 860, 1180, 50);
					
				}
			});
			
			
			
			startButton.setEnabled(false);
			startButton.addActionListener(this);
			stopButton.setBounds(0, 0, 44, 25);
			getContentPane().add(stopButton);
			stopButton.setEnabled(false);
			stopButton.addActionListener(this);
			
		
			help.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					
					help.setIcon(new ImageIcon(getClass().getResource("/src/images/help-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					help.setIcon(new ImageIcon(getClass().getResource("/src/images/help.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					try{
					
						OmokClient2.msgView.append("상대에게 무르기를 요청했습니다.\n");
						writer.println("[HELP]");
						tmr1.cancel();
						tmr2.cancel();
						timer1.setText("20");
						timer2.setText("20");
						board.setEnable(false);
						
						
						
					}catch(Exception e){}
				}
			});
			help.setIcon(new ImageIcon(Main.class.getResource("/src/images/help.png")));
			help.setBounds(831, 209, 421, 68);
			getContentPane().add(help);
			help.setVisible(false);
			
			accept.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					accept.setIcon(new ImageIcon(getClass().getResource("/src/images/accept-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					accept.setIcon(new ImageIcon(getClass().getResource("/src/images/accept.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					try{
						writer.println("[ACCEPT]");
						OmokClient2.msgView.append("16.\n");
						accept.setVisible(false);
						refuse.setVisible(false);
						help.setVisible(true);
						board.setEnable(false);
						//tempTask2();
					}catch(Exception e){}
				}
			});
			accept.setIcon(new ImageIcon(Main.class.getResource("/src/images/accept.png")));
			accept.setBounds(831, 209, 209, 68);
			getContentPane().add(accept);
			accept.setVisible(false);
			
			
			refuse.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					refuse.setIcon(new ImageIcon(getClass().getResource("/src/images/refuse-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					refuse.setIcon(new ImageIcon(getClass().getResource("/src/images/refuse.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					try{
						writer.println("[REFUSE]");
						accept.setVisible(false);
						refuse.setVisible(false);
						help.setVisible(true);
						board.setEnable(false);
						//tempTask2();
					}catch(Exception e){}
				}
			});
			refuse.setIcon(new ImageIcon(Main.class.getResource("/src/images/refuse.png")));
			refuse.setBounds(1050, 209, 209, 68);
			getContentPane().add(refuse);
			refuse.setVisible(false);
			
			go.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					go.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/go-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					go.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/go.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					try{
						writer.println("[START]");
						infoView.setText("게임을 시작합니다.");
					}catch(Exception e){}
				}
			});
			go.setIcon(new ImageIcon(Main.class.getResource("/src/images/go.png")));
			go.setBounds(831, 128, 209, 68);
			getContentPane().add(go);
			go.setVisible(false);
			
			
			no.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					no.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/no-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					no.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/no.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					try{
						if(board.IsEnable()) {
							tmr1.cancel();
							tmr2.cancel();
							timecount1 = 20;
							timecount2 = 20;
							
							writer.println("[DROPGAME]");
							exitButton2.setVisible(true);
							exitButton.setVisible(true);
							endGame("15.");
						}
						else
							OmokClient2.msgView.append("16.\n");
					}catch(Exception e){}
				}
			});
			no.setIcon(new ImageIcon(Main.class.getResource("/src/images/no.png")));
			no.setBounds(1050, 128, 209, 68);
			getContentPane().add(no);
			no.setVisible(false);
			
			
			
			
			pInfo.setBackground(new Color(204, 114, 61));
			pInfo.setFont(new Font("aharoni", Font.BOLD, 30));
			pInfo.setBounds(48, 230, 623, 50);
			
		
			rInfo.setBackground(new Color(204, 114, 61));
			rInfo.setFont(new Font("aharoni", Font.BOLD, 30));
			rInfo.setBounds(671, 230, 560, 50);
			
			getContentPane().add(pInfo);
			getContentPane().add(rInfo);
			board.setBounds(20, 120, 795, 795);
			getContentPane().add(board);
			board.setVisible(false);
			getContentPane().add(p);
			getContentPane().add(p2);
			getContentPane().add(p3);
			getContentPane().add(p4);
	 
			
			readyback.setIcon(new ImageIcon(Main.class.getResource("/src/images/ready.png")));
			readyback.setBounds(0, 0, 1280, 1000);
			getContentPane().add(readyback);
			
			
			battleground.setIcon(new ImageIcon(Main.class.getResource("/src/images/battleground.png")));
			battleground.setBounds(0, 0, 1280, 1000);
			getContentPane().add(battleground);
			battleground.setVisible(false);
			
			 
			
			sendBox.addActionListener(this);
			enterButton.addActionListener(this);
			enterButton.setVisible(false);
			exitButton.addActionListener(this);
			
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we){
					System.exit(0);
				}
			});
			
			
			close2.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseEntered(MouseEvent arg0) {
					close2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/close-hover.png")));
				}
				public void mouseExited(MouseEvent arg0) {
					close2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/close.png")));
				}
				public void mouseClicked(MouseEvent arg0){
					writer.println("[EXIT]");
					Main frame = new Main();
					dispose();
					frame.setVisible(true);
					
				}
			});
			
			
		}

		
		  

		
	
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource()==sendBox){            
				String msg=sendBox.getText();
				if(msg.length()==0)
					return;
				if(msg.length()>=30)msg=msg.substring(0,30);
				try{  
					writer.println("[MSG]"+msg);
					sendBox.setText("");
				}catch(Exception ie){}
			}
			
			else if(ae.getSource()==enterButton2){       

			}

			else if(ae.getSource()==exitButton){         
				try{
					goToWaitRoom();
					startButton.setEnabled(false);
					stopButton.setEnabled(false);
				}catch(Exception e){}
			}

			else if(ae.getSource()==startButton){        

			}

			else if(ae.getSource()==stopButton){    

			}
		}
		
		void goToWaitRoom(){                  
			if(userName==null){
				String name=nameBox.getText();
				if(name.length()<=1 || name.length()>10){
					infoView.setText("17.");
					nameBox.requestFocus();
					return;
				}
				
				userName=name;
				writer.println("[NAME]"+userName);    
				nameBox.setText(userName);
				nameBox.setEditable(false);
			}  
			msgView.setText("");
			
			writer.println("[ROOM]0");
			infoView.setText("18.");
			roomBox.setText("0");
			enterButton.setEnabled(true);
			exitButton.setEnabled(false);
		}
 
	public void run(){
		String msg;                             
		try{
			while((msg=reader.readLine())!=null){
				
				System.out.println(msg);
				if(msg.startsWith("[STONE]")){   
					
					String temp=msg.substring(7);
					int x=Integer.parseInt(temp.substring(0,temp.indexOf(" ")));
					int y=Integer.parseInt(temp.substring(temp.indexOf(" ")+1));
					board.putOpponent(x, y);    
					board.setEnable(true);       
					
					timecount2 = 20;
					timer2.setText("20");
					tmr2.cancel();
					tempTask1();
					
				}
				else if(msg.startsWith("[TIME]")) {
					timecount1 = 20;
					timer1.setText("20");
					tmr1.cancel();
					tempTask2();
				}
				else if(msg.startsWith("[TIMEOVERme]")) {
					timer1.setText("20");
					timecount1 = 20;
					tmr1.cancel();
					board.setEnable(false);
					tempTask2();
				}
				else if(msg.startsWith("[TIMEOVER]")) {
					timer2.setText("20");
					timecount2 = 20;
					tmr2.cancel();
					board.setEnable(true);
					tempTask1();
				}
				
				else if(msg.startsWith("[HELP]")) { 
					
					help.setVisible(false);
					accept.setVisible(true);
					refuse.setVisible(true);
					tmr2.cancel();
					tmr1.cancel();
					timecount1 = 20;
					timecount2 = 20;
					timer1.setText("20");
					timer2.setText("20");
					
					
				}
				else if(msg.startsWith("[REFUSE]")) { 
					OmokClient2.msgView.append("19.\n");
					board.setEnable(true);
					tempTask1();
				}
				else if(msg.startsWith("[ACCEPT]")) {	
					
					String temp = msg.substring(8); 
					int x=Integer.parseInt(temp.substring(0,temp.indexOf(" ")));
					int y=Integer.parseInt(temp.substring(temp.indexOf(" ")+1));
					board.RemoveOpponent(x, y);
				}
				else if(msg.startsWith("[ACCEPT2]")) {
					tempTask1();
					board.setEnable(true);
				}
				else if(msg.startsWith("[NEW]"))	
						rList.add(msg.substring(5) + "999");
					
				else if(msg.startsWith("[cry]")) { 
					
					cry2.setBounds(1150, 630, 70, 70);
					cry2.setVisible(true);	
					emotionTask2();
				}
				else if(msg.startsWith("[happy]")) {
						
					happy2.setBounds(1150, 630, 70, 70);
					happy2.setVisible(true);
					emotionTask2();
				}
				else if(msg.startsWith("[question]")) {
						
					question2.setBounds(1150, 630, 70, 70);
					question2.setVisible(true);	
					emotionTask2();
				}
				else if(msg.startsWith("[angry]")) {
		
					angry2.setBounds(1150, 630, 70, 70);
					angry2.setVisible(true);	
					emotionTask2();
				}
				
				else if(msg.startsWith("[ROOM]")){
					if(!msg.equals("[ROOM]0")){    
						enterButton.setEnabled(false);
						exitButton.setEnabled(true);
						infoView.setText(msg.substring(6)+"번 방에 입장하였습니다.");
					}
					else infoView.setText("21.");
					roomNumber=Integer.parseInt(msg.substring(6));   
					if(board.isRunning()){                   
						board.stopGame();                    
					}
				}
				else if(msg.startsWith("[ROOMSET]")) {
					rList.removeAll();
				}
				else if(msg.startsWith("[ROOMLIST]")) {	
					if(rList.getItemCount() != 0) {
						for(int i = 0; i < rList.getItemCount(); i++)
							if(!rList.getItem(i).equals(msg.substring(10) + "번 방"))
								rList.add(msg.substring(10) + "번 방");
					}
					else
						rList.add(msg.substring(10) + "번 방");
					
				}
				
				else if(msg.startsWith("[FULL]")){
					infoView.setText("방이 가득찼습니다.");
				}
				else if(msg.startsWith("[PLAYERS]")){  
					nameList(msg.substring(9));
				}
				else if(msg.startsWith("[ENTER]")){
					pList.add(msg.substring(7));
					playersInfo();
					msgView.append("["+ msg.substring(7)+"]님이 입장하였습니다.\n");
				}
				else if(msg.startsWith("[EXIT]")){      
					pList.remove(msg.substring(6));     
					playersInfo();                       
					msgView.append("["+msg.substring(6)+"]님이 다른 방으로 입장하였습니다.\n");
					endGame("24.");
				}
				else if(msg.startsWith("[DISCONNECT]")){
					pList.remove(msg.substring(12));
					playersInfo();
					msgView.append("["+msg.substring(12)+"]25.\n");
					if(roomNumber!=0)
						endGame("26.");
				}
				
				else if(msg.startsWith("[COLOR]")){         
					String color=msg.substring(7);
					board.startGame(color);                     
					exitButton.setVisible(false);
					exitButton2.setVisible(false);
					help.setVisible(true);
					if(color.equals("BLACK")) {
						infoView.setText("흑돌을 잡으셨습니다.");	
						tempTask1();
					}
					
					else {
						infoView.setText("백돌을 잡으셨습니다.");
						tempTask2();
					}
					stopButton.setEnabled(true);           
				}
				else if(msg.startsWith("[DROPGAME]")) {     
					tmr2.cancel();
					tmr1.cancel();
					timecount1 = 20;
					timecount2 = 20;

					
					exitButton2.setVisible(true);
					exitButton.setVisible(true);
					endGame("29.");
				}
				else if(msg.startsWith("[WIN]")) {        
					tmr2.cancel();
					tmr1.cancel();
					timecount1 = 20;
					timecount2 = 20;
					exitButton2.setVisible(true);
					exitButton.setVisible(true);
					endGame("30.");
				}
				else if(msg.startsWith("[LOSE]")) {      
					tmr1.cancel();
					tmr2.cancel();
					timecount1 = 20;
					timecount2 = 20;
					exitButton2.setVisible(true);
					exitButton.setVisible(true);
					endGame("31.");
				}
				
				else msgView.append(msg+"\n");
			}
		}catch(IOException ie){
			msgView.append(ie+"\n");
		}
		msgView.append("접속이 끊겼습니다.");
	}
	
	private void endGame(String msg){   
		infoView.setText(msg);
		timer1.setText("20");
		timer2.setText("20");
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		try{ Thread.sleep(2000); }catch(Exception e){}  
		if(board.isRunning())board.stopGame();
		if(pList.getItemCount()==2)startButton.setEnabled(true);
	}
	
	
	
	private void playersInfo(){         
		int count=pList.getItemCount();
		if(roomNumber==0)
			pInfo.setText("대기실: "+count+"명");
		else 
			pInfo.setText(roomNumber+" 34: "+count+"��");
		
		if(count==2 && roomNumber!=0)
			startButton.setEnabled(true);
		else startButton.setEnabled(false);
	}
	

	
	private void nameList(String msg){
		pList.removeAll();
		StringTokenizer st=new StringTokenizer(msg, "\t");
		while(st.hasMoreElements())
			pList.add(st.nextToken());
		playersInfo();
	}
	
	public void connect(){ 
		try{
			msgView.append("35.\n");
			socket=new Socket("localhost", 9735);
			msgView.append("36.\n");
			msgView.append("37.\n");
			reader=new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			writer=new PrintWriter(socket.getOutputStream(), true);
			new Thread(this).start();
			board.setWriter(writer);
		}catch(Exception e){
			msgView.append(e+"\n\n38.\n");  
		}
	}

}