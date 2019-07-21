package kr.ac.hansung.cse;
import java.net.*;
import java.io.*;
import java.util.*;


public class OmokServer implements Runnable{

	private ServerSocket server; 
	private Massenger Man=new Massenger(); 
	private Random rnd= new Random(); 
	
	// 서버 실행
	void startServer(){
		try{
			server=new ServerSocket(9735);
			Main.textArea.append("서버를 동작시킵니다.\n");
			while(true){
				// Ŭ���̾�Ʈ�� ����� ������ ȹ��
				Socket socket=server.accept();
				// �����带 ����� ����
				controller con=new controller(socket);
				con.start();
				// bMan�� �����带 �߰��Ѵ�.
				Man.add(con);
				Main.textArea.append("현재 " + Man.size() + "명이 접속해 있습니다.\n");
      }
    }catch(Exception e){
      System.out.println(e);
    }
  }
  
	// 클라이언튼와 통신
	class controller extends Thread{
		private int roomNumber = -1;       
		private String userName = null;     
		private Socket socket; 
		
		private boolean ready=false;
		private BufferedReader reader;     
		private PrintWriter writer;  
		controller(Socket socket){ 
			this.socket=socket;
		}
		Socket getSocket(){            
			return socket;
		}
		int getRoomNumber(){            
			return roomNumber;
		}
		String getUserName(){            
			return userName;
		}
		boolean isReady(){                 
			return ready;
		}
		public void run(){
			try{
				reader=new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
				writer=new PrintWriter(socket.getOutputStream(), true);
				String msg;                     
				while((msg=reader.readLine())!=null){
					//사용자 이름
					if(msg.startsWith("[NAME]")){
						userName=msg.substring(6);  
					}
					// 방번호
					else if(msg.startsWith("[ROOM]")){
						int roomNum=Integer.parseInt(msg.substring(6));
						if( !Man.isFull(roomNum)){  
						
							if(roomNumber!=-1)
								Man.sendToOthers(this, "[EXIT]"+userName);
							
							roomNumber=roomNum;
							
							writer.println(msg);
							
							writer.println(Man.getNamesInRoom(roomNumber));
							
							Man.sendToOthers(this, "[ENTER]"+userName);
						}
						else writer.println("[FULL]");
					}
					
					else if(roomNumber>=1 && msg.startsWith("[STONE]"))
						Man.sendToOthers(this, msg);
					
					else if(msg.startsWith("[MSG]"))
						Man.sendToRoom(roomNumber, "["+userName+"]: "+msg.substring(5));
					
					else if(msg.startsWith("[START]")){
						ready=true; 
						
						if(Man.isReady(roomNumber)){
							
							int a=rnd.nextInt(2);
							if(a==0){
								writer.println("[COLOR]BLACK");
								Man.sendToOthers(this,"[COLOR]WHITE");
							}
							else{
								writer.println("[COLOR]WHITE");
								Man.sendToOthers(this,"[COLOR]BLACK");
							}
						}
					}
					
					else if(msg.startsWith("[STOPGAME]"))
						ready=false;
					
					else if(msg.startsWith("[DROPGAME]")){
						ready=false;
						
						Man.sendToOthers(this, "[DROPGAME]");
					}
					
					else if(msg.startsWith("[WIN]")){
						ready=false;
						
						writer.println("[WIN]");
						
						Man.sendToOthers(this, "[LOSE]");
					}  
				}
			}catch(Exception e){
			}finally{
				try{
					Man.remove(this);
					if(reader!=null) reader.close();
					if(writer!=null) writer.close();
					if(socket!=null) socket.close();
					reader=null; writer=null; socket=null;
					if(userName == null)
						userName = "�ſ� �Ҹ��� �����";
					Main.textArea.append(userName+"님이 접속을 끊었습니다.\n");
					Main.textArea.append("현재 " + Man.size() + "명이 접속해 있습니다.\n");
					
					Man.sendToRoom(roomNumber,"[DISCONNECT]"+userName);
				}catch(Exception e){}
			}
		}
	}

	class Massenger extends Vector{      
		void add(controller con){    
			super.add(con);
		}
		void remove(controller con){ 
			super.remove(con);
		}
		controller getOT(int i){ 
			return (controller)elementAt(i);
		}
		Socket getSocket(int i){ 
			return getOT(i).getSocket();
		}
		
		void sendTo(int i, String msg){
			try{
				PrintWriter pw= new PrintWriter(getSocket(i).getOutputStream(), true);
				pw.println(msg);
			}catch(Exception e){}  
		}
		int getRoomNumber(int i){
			return getOT(i).getRoomNumber();
		}
		synchronized boolean isFull(int roomNum){ 
			if(roomNum==0)
				return false;  
			
			int count=0;
			for(int i=0;i<size();i++)
				if(roomNum==getRoomNumber(i))count++;
			if(count>=2)
				return true;
			return false;
		}

		// roomNum
		void sendToRoom(int roomNum, String msg){
			for(int i=0;i<size();i++)
				if(roomNum==getRoomNumber(i))
					sendTo(i, msg);
		}
    
		// to User
		void sendToOthers(controller ot, String msg){
			for(int i=0;i<size();i++)
				if(getRoomNumber(i)==ot.getRoomNumber() && getOT(i)!=ot)
					sendTo(i, msg);
		}
    
		
		synchronized boolean isReady(int roomNum){
			int count=0;
			for(int i=0;i<size();i++)
				if(roomNum==getRoomNumber(i) && getOT(i).isReady())
					count++;
			if(count==2)
				return true;
			return false;
		}

		
		String getNamesInRoom(int roomNum){
			StringBuffer sb=new StringBuffer("[PLAYERS]");
			for(int i=0;i<size();i++)
				if(roomNum==getRoomNumber(i))
					sb.append(getOT(i).getUserName()+"\t");
			return sb.toString();
		}
	}
	
	@Override
	public void run() {
	    OmokServer server=new OmokServer();
	    server.startServer();
	}
	
	public void out() throws IOException {
		this.server.close();
	}
}