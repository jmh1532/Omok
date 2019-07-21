package kr.ac.hansung.cse;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class OmokBoard2 extends Canvas{
  	
	
	public static String myblack = OmokSingle.myblack;
	public static String mywhite = OmokSingle.mywhite;
	public static String soundControl = "on";
	
	private boolean enable=false;
	private boolean running=false; 
	private PrintWriter writer; 
	private Graphics gboard, gbuff; 
	private Image buff; 
	
	
	public static final int BLACK = 1,WHITE = -1; 
	private int[][]map; 
	private int size; 
	private int cell; 
	private int color=BLACK; 
	int a, b;
	Random rs = new Random();
	
	OmokBoard2(int s, int c) { 
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
					OmokClient2.msgView.append("쌍삼 자리입니다.");
					return;
					}
				
				map[x][y]=color;
				OmokSingle.msgView.append("플레이어가 (" + x + ", " + y + ")에 두었습니다.\n");
				enable=false;
				int resetPoint = 0;
				repaint2();
				
				if(check(new Point(x, y), color)){
					OmokSingle.msgView.append("승리하였습니다.\n");
					OmokSingle.msgView.append("1초 뒤에 재시작합니다.\n");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					endGame();
					resetPoint = 1;
				}
				if(resetPoint != 1)
				{
				AI(x, y);
				OmokSingle.msgView.append("컴퓨터가 (" + a + ", " + b + ")에 두었습니다.\n");
				if(check(new Point(a, b), -color)){
					OmokSingle.msgView.append("패배하였습니다.\n");
					OmokSingle.msgView.append("1초 뒤에 재시작합니다.\n");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					endGame();
					resetPoint = 1;
				}
				else
					putOpponent(a, b);
				}
				enable=true;
				
			}
		});
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

	public void AI(int x, int y) {
		
		
		int newx;
		int newy;
		for(newx = x - 4; newx < x + 4; newx++)
		{
			for(newy = y - 4; newy < y + 4; newy++)
			{
				if(!(newx <= 0 || newy <= 0 || newx >= size+1 || newy >=size+1))
				{
					if(check(new Point(newx, newy), color) && map[newx][newy] == 0)
					{
						a = newx;
						b = newy;
						if(!(map[a][b]==BLACK || map[a][b]==WHITE))
						{
							map[a][b]=-color;
							return;
						}
					}
				}
			}
		}
		
		
		if(map[x+1][y+1] == color && map[x+2][y+2] == color && x <= size - 3 && y <= size - 3 && x >= 2 && y >= 2 && map[x + 3][y + 3] == 0 && map[x - 1][y - 1] == 0)
		{
			a = x - 1;
			b = y - 1;
			map[a][b]=-color;
			return;
		}
		
		if(map[x][y + 1] == color && map[x][y + 2] == color && y <= size - 3 && y >= 2 && map[x][y + 3] == 0 && map[x][y - 1]  == 0)
		{
			a = x;
			b = y + 3;
			map[a][b]=-color;
			return;			
		}
		
		if(map[x - 1][y + 1] == color && map[x - 2][y + 2] == color && x >= 4 &&  x <= size - 1 && y <= size - 4 && y >= 2 && map[x + 1][y - 1] != -color && map[x - 3][y + 3] != -color)
		{
			a = x + 1;
			b = y - 1;
			map[a][b]=-color;
			return;			
		}
		
		if(map[x - 1][y] == color && map[x - 2][y] == color && x >= 4 && x <= size -1 && map[x - 3][y] != -color && map[x + 1][y] != -color)
		{
			a = x - 3;
			b = y;
			map[a][b]=-color;
			return;			
		}
		
		if(map[x - 1][y - 1] == color && map[x - 2][y - 2] == color && x <= size + 3 && y <= size + 3 && x >= -2 && y >= -2 && map[x - 3][y - 3] != -color && map[x + 1][y + 1] != -color)
		{
			a = x + 1;
			b = y + 1;
			map[a][b]=-color;
			return;
		}
		
		if(map[x][y - 1] == color && map[x][y - 2] == color && y <= size + 3 && y >= -2 && map[x][y - 3] != -color && map[x][y + 1] != -color)
		{
			a = x;
			b = y - 3;
			map[a][b]=-color;
			return;			
		}
		
		if(map[x + 1][y - 1] == color && map[x + 2][y - 2] == color && x >= -4 &&  x <= size + 1 && y <= size + 4 && y >= -2 && map[x - 1][y + 1] != -color && map[x + 3][y - 3] != -color)
		{
			a = x - 1;
			b = y + 1;
			map[a][b]=-color;
			return;			
		}
		
		if(map[x + 1][y] == color && map[x + 2][y] == color && x >= -4 && x <= size +1 && map[x + 3][y] != -color && map[x - 1][y] != -color)
		{
			a = x + 3;
			b = y;
			map[a][b]=-color;
			return;			
		}
		
		for(int i = 0; i < 5; i ++)
		{
			a = x + rs.nextInt(3) - 1;
			b = y + rs.nextInt(3) - 1;
			if(!(map[a][b]==BLACK || map[a][b]==WHITE))
			{
				if(!(a==0 || b==0 || a==size+1 || b==size+1))
				{
					map[a][b]=-color;
					return;
				}
			}
		}
		while(true)
		{
			a = x + rs.nextInt(5) - 2;
			b = y + rs.nextInt(5) - 2;
			if(!(map[a][b]==BLACK || map[a][b]==WHITE))
			{
				if(!(a==0 || b==0 || a==size+1 || b==size+1))
				{
					map[a][b]=-color;
					return;
				}
			}
		}
	}
	
	private void endGame(){   
		stopGame();
	}
	
	public boolean isRunning(){           
		return running;
	}
	
	public void startGame(String col){     
		running=true;
		if(col.equals("BLACK")){              
			enable=true; color=BLACK;
			OmokSingle.msgView.append("선공입니다.\n");
		}   
		else{                                
			enable=false; color=WHITE;
			OmokSingle.msgView.append("후공입니다.\n");
		}
	}

	public void stopGame(){             
		reset();                              
		enable=false;
		running=false;
	}

	public void repaint2()
	{
		repaint();
	}
	
	public void putOpponent(int x, int y){    
		map[x][y]=-color;
		if(soundControl.equals("on"))
			playSound();
		repaint();
	}

	public void setEnable(boolean enable){
		this.enable=enable;
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
		OmokSingle.msgView.append("게임이 새로 시작되었습니다.\n");
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
		String myblack = OmokSingle.myblack;
		Graphics2D gbuff=(Graphics2D)this.gbuff;
		BufferedImage image;
		image = ImageIO.read(getClass().getResourceAsStream(myblack));
		gbuff.drawImage(image, x*cell-cell/2,  y*cell-cell/2, null);
	}

	private void drawWhite(int x, int y) throws IOException{     
		String mywhite = OmokSingle.mywhite;
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
}

public class OmokSingle extends JFrame {

	private JLabel emotion = new JLabel("");
	private JPanel contentPane;
	public static TextArea msgView=new TextArea("", 1,1,1);
	public static String myblack = "/src/images/black1.png";
	public static String mywhite = "/src/images/white1.png";
	public static String soundControl = "on";
	public static OmokBoard2 board=new OmokBoard2(15,50);
	Panel p3=new Panel();
	public static JLabel battleground = new JLabel();
	
	
	
	
	public OmokSingle() {
		setTitle("싱글 대전");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(board);
		contentPane.add(p3);
		board.setBounds(20, 120, 795, 795);
		board.startGame("BLACK");
		p3.setLayout(new BorderLayout());
		p3.add(msgView, BorderLayout.CENTER);
		p3.setBounds(822, 120, 430, 400);
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width/2 - 640, size.height/2 - 500);
		System.out.println();
		emotion.setIcon(new ImageIcon(Main.class.getResource("/src/images/solo.jpg")));
		emotion.setBounds(826, 530, 426, 400);
		getContentPane().add(emotion);
		emotion.setVisible(true);
		
		JLabel close = new JLabel("");
		close.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/close-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/images/close.png")));
			}
			public void mouseClicked(MouseEvent arg0){
				Main frame = new Main();
				dispose();
				frame.setVisible(true);
				
			}
		});
		close.setIcon(new ImageIcon(Main.class.getResource("/src/images/close.png")));
		close.setBounds(1230, 7, 28, 30);
		contentPane.add(close);
		close.setVisible(true);
		
		battleground.setIcon(new ImageIcon(Main.class.getResource("/src/images/battleground.png")));
		battleground.setBounds(0, 0, 1280, 1000);
		getContentPane().add(battleground);
		OmokSingle.msgView.append("게임을 시작합니다.\n 난이도는 하 입니다.\n");
	}

}
