package kr.ac.hansung.cse;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Toolkit;

public class Main extends JFrame {

	String myblack = "/images/black1.png";
	String mywhite = "/images/white1.png";
	String soundControl = "on";
	private JPanel contentPane;
	private JTextField nametext;
    private OmokClient2 client;
    private OmokSingle Csingle=new OmokSingle();
    
	public static void main(String[] args) {
		
		Main frame = new Main();
		frame.setVisible(true);
		
	}

	public Main() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		setUndecorated(true);
		setLocationRelativeTo(null);
		
		
		JLabel close = new JLabel("");
		close.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png")));
			}
			public void mouseClicked(MouseEvent arg0){
				System.exit(0);
			}
		});
		close.setIcon(new ImageIcon(Main.class.getResource("/images/close.png")));
		close.setBounds(365, 7, 28, 30);
		contentPane.add(close);
		
		///////////////
		
		nametext = new JTextField();
		nametext.setFont(new Font("궁서", Font.BOLD, 15));
		nametext.setBounds(173, 101, 184, 35);
		contentPane.add(nametext);
		nametext.setColumns(10);
		JLabel setup = new JLabel("");
		JLabel double_ = new JLabel("");
		// 참여 login
		JLabel join = new JLabel("");
		join.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				join.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/join-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				join.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/join.png")));
			}
			public void mouseClicked(MouseEvent arg0){
				client.setSize(1280, 1000);
			    client.setVisible(false);
			    client.connect();
				client.nameBox.setText(nametext.getText());
				client.goToWaitRoom();
				client.startButton.setEnabled(false);
				client.stopButton.setEnabled(false);
				client.setVisible(true);
				setVisible(false);
			}
		});
		join.setIcon(new ImageIcon(Main.class.getResource("/images/join.png")));
		join.setBounds(107, 194, 179, 63);
		contentPane.add(join);
		
		JLabel background_d = new JLabel("");
		background_d.setIcon(new ImageIcon(Main.class.getResource("/images/background2.png")));
		background_d.setBounds(0, 0, 400, 300);
		contentPane.add(background_d);
		
	    join.setVisible(false);
	    nametext.setVisible(false);
	    background_d.setVisible(false);
		//////////////////////
	    Csingle.setVisible(false);
		
		JLabel single = new JLabel("");
		single.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				single.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/single-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				single.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/single.png")));
			}
			public void mouseClicked(MouseEvent arg0){
				// 혼자하기 클릭시
				Csingle.setSize(1280, 1000);
				Csingle.setVisible(true);
				setVisible(false);
			    OmokSingle.myblack = myblack;
			    OmokSingle.mywhite = mywhite;
			    OmokSingle.board.myblack = myblack;
			    OmokSingle.board.mywhite = mywhite;
			    OmokBoard2.myblack = myblack;
			    OmokBoard2.mywhite = mywhite;
			    OmokSingle.soundControl = soundControl;
			    OmokSingle.board.soundControl = soundControl;
			    OmokBoard2.soundControl = soundControl;
			    
			}
		});
		single.setIcon(new ImageIcon(Main.class.getResource("/images/single.png")));
		single.setBounds(106, 67, 179, 65);
		contentPane.add(single);
		
		
		
		JLabel deco = new JLabel("");
		
		JLabel basic = new JLabel("");
		JLabel soundOn = new JLabel("");
		JLabel soundOff = new JLabel("");
		
		soundOn.setVisible(false);
		soundOff.setVisible(false);
		deco.setVisible(false);
		basic.setVisible(false);
		
		soundOn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				soundOn.setIcon(new ImageIcon(getClass().getResource("/images/on-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				soundOn.setIcon(new ImageIcon(getClass().getResource("/images/on.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				single.setVisible(true);
			    double_.setVisible(true);
			    setup.setVisible(true);
			    basic.setVisible(false);
			    deco.setVisible(false);
				soundOff.setVisible(false);
				soundOn.setVisible(false);
				soundControl = "on";
			}
			
		});
		soundOn.setIcon(new ImageIcon(Main.class.getResource("/images/on.png")));
		soundOn.setBounds(130, 210, 60, 60);
		contentPane.add(soundOn);
		soundOff.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				soundOff.setIcon(new ImageIcon(getClass().getResource("/images/off-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				soundOff.setIcon(new ImageIcon(getClass().getResource("/images/off.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				single.setVisible(true);
			    double_.setVisible(true);
			    setup.setVisible(true);
			    basic.setVisible(false);
			    deco.setVisible(false);
				soundOff.setVisible(false);
				soundOn.setVisible(false);
				soundControl = "off";
				
			}

			
		});
		soundOff.setIcon(new ImageIcon(Main.class.getResource("/images/off.png")));
		soundOff.setBounds(210, 210, 179, 60);
		contentPane.add(soundOff);
		
		basic.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				basic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basic-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				basic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/basic.png")));
			}
			public void mouseClicked(MouseEvent arg0){
			    single.setVisible(true);
			    double_.setVisible(true);
			    setup.setVisible(true);
			    basic.setVisible(false);
			    deco.setVisible(false);
			    soundOn.setVisible(false);
			    soundOff.setVisible(false);
				myblack = "/images/black1.png";
				mywhite = "/images/white1.png";
			}
		});
		basic.setIcon(new ImageIcon(Main.class.getResource("/images/basic.png")));
		basic.setBounds(106, 65, 179, 65);
		contentPane.add(basic);

		deco.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				deco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deco-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				deco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/deco.png")));
			}
			public void mouseClicked(MouseEvent arg0){
			    single.setVisible(true);
			    double_.setVisible(true);
			    setup.setVisible(true);
			    basic.setVisible(false);
			    deco.setVisible(false);
			    soundOn.setVisible(false);
			    soundOff.setVisible(false);
				myblack = "/images/black2.png";
				mywhite = "/images/white2.png";
			}
		});
		deco.setIcon(new ImageIcon(Main.class.getResource("/images/deco.png")));
		deco.setBounds(107, 138, 179, 63);
		contentPane.add(deco);

		double_.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				double_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/double-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				double_.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/double.png")));
			}
			public void mouseClicked(MouseEvent arg0){
				
			    single.setVisible(false);
			    double_.setVisible(false);
			    setup.setVisible(false);
			    join.setVisible(true);
			    nametext.setVisible(true);
			    background_d.setVisible(true);
			    OmokClient2.myblack = myblack;
			    OmokClient2.mywhite = mywhite;
			    OmokClient2.soundControl = soundControl;
			    client = new OmokClient2("같이 하기");
			    
			    
			}
		});
		double_.setIcon(new ImageIcon(Main.class.getResource("/images/double.png")));
		double_.setBounds(106, 144, 179, 65);
		contentPane.add(double_);
		

		setup.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/setup-hover.png")));
			}
			public void mouseExited(MouseEvent arg0) {
				setup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/setup.png")));
			}
			public void mouseClicked(MouseEvent arg0){
			    single.setVisible(false);
			    double_.setVisible(false);
			    setup.setVisible(false);
			    basic.setVisible(true);
			    deco.setVisible(true);
			    soundOn.setVisible(true);
			    soundOff.setVisible(true);
			}
		});
		setup.setIcon(new ImageIcon(Main.class.getResource("/images/setup.png")));
		setup.setBounds(106, 221, 179, 65);
		contentPane.add(setup);
		
		JLabel background = new JLabel("");
		background.setIcon(new ImageIcon(Main.class.getResource("/images/background.png")));
		background.setBounds(0, 0, 400, 300);
		contentPane.add(background);
	}
}
