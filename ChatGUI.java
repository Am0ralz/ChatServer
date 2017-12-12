package chat_v7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import chat_v7.ChatClientThread;

public class ChatGUI extends JFrame{
	private static JTextArea displayArea = new JTextArea(10,25);
	public static String dis= null;
	public String serverName= "127.0.0.1";
	public	int serverPort= 8080;
	public ChatGUI(){
		
		
		ChatPanel chatPanel = new ChatPanel(serverName, serverPort);
		this.setTitle("Let' Chat!");//title
		JPanel Container = new JPanel();
		TA ta = new TA();
		
        Container.setLayout(new BorderLayout());
        Container.add(BorderLayout.NORTH, ta);
        Container.add(BorderLayout.SOUTH, chatPanel);
       
        add(Container);
		//add(chatPanel);
	}
	
private class TA extends JPanel{
		
		public TA(){
			JScrollPane scroll = new JScrollPane (displayArea, 
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

					add(scroll);
					setVisible (true);	
		}
	}
	
	private class ChatPanel extends JPanel implements Runnable, ActionListener{
		private Socket socket = null;
		private DataInputStream strIn= null;
		private DataOutputStream strOut= null;
		private ChatClientThread client = null;
		private Thread thread = null;
		private String line = "";
		boolean done = true;
		private String _serverName = null;
		private int _serverPort;
		
///////////////////////////display//////////////////////////////////////////
		
		private JTextField tfInput= new JTextField("WRITE HERE...");
		private JButton enterM= new JButton("Group Message");
		private JButton subPM= new JButton("Private Message");
		private JButton enc= new JButton("Encrypt");
		//private JButton nonenc= new JButton("Non Encrypt");
		private JButton conn= new JButton("Connect");
		private JButton discon= new JButton("Disconnect");
		//private JTextField lhTxt= new JTextField("127.0.0.1");
		//private JTextField pTxt= new JTextField("8080");
		private JTextField PID= new JTextField("Private ID");
		//a bunch of JButtons :-) .. and anything else you need...
		
////////////////////////// adding display///////////////////////////////////		
		public ChatPanel(String serverName, int serverPort){//jpanel constructor
			
			//setLayout as you prefer
			setLayout(new GridLayout(4,3));//(rows,columns)//add more buttons later		
			
			//create your components
			//connect= new JButton("Connect");
			add(enterM);
			add(subPM);
			add(enc);
            add(conn);
            add(discon);
			add(tfInput);
            add(PID);
       
            
			
			// actionListeners
			
			enterM.addActionListener(this);
			subPM.addActionListener(this);
			discon.addActionListener(this);
			conn.addActionListener(this);
			subPM.addActionListener(this);
			//create your components
			//add your components
			
		}
		
		
		
/////////////////////////////////////////////////////////////////////////////		
		@Override
		public void run() {
			while(!done){
				try{
					String line = strIn.readUTF();
					//put your logic here for figuring out the decryption...
					//maybe call the decrypt method???
					updateDisplay(line);
				}catch(IOException e){
					updateDisplay(e.getMessage());
				}
			}
		}
		
		public void connect(){
			String connStatus = "WOOHOO";
			try{
				socket = new Socket(serverName, serverPort);
				//System.out.println(serverName+" "+ serverPort);
				start(); //step 2
			}catch(UnknownHostException uhe){
				connStatus = uhe.getMessage();
			}catch(IOException ioe){
				connStatus = ioe.getMessage();
			}
			finally{
				updateDisplay(connStatus);
			}
		}
		public void disconnect(){
			//disableButtons(); // enable connect
			//send("bye");
			done=true;
		}
		
		public void handle(String msg){
			if(msg.equalsIgnoreCase("bye")){
				line="bye";
				//stop();
			}
			else{
				System.out.print("devfedvgfdbg dfbg v");
				updateDisplay(msg);
			}
		}
		public void run(String msg){// step 3
			try{
				strOut.writeUTF(msg);
				updateDisplay(msg);
				System.out.println("IM HERE");
				strOut.flush();
			}catch(IOException ioe){
				updateDisplay(ioe.getMessage());
			}
		}
		public void sendPrivate(String ID, String msg){
			//use the id and the msg to send to the server as normal
			//use the same string format you did in your reg console chat
		}
		public void sendPrivateEncr(String ID, String msg){
			//use the id and the msg to send to the server as normal
			//make sure you encrypt the msg
			//prefix the encrypted by the unencrypted pm: ...
			//use the same string format you did in your reg console chat
		}
		
		private String decrypt(String text){
			String decryptedMsg = text;//not yet decrypted..
			//... split the incoming message accordingly}
			return decryptedMsg;
		}
		public void start(){
			try{
				strIn = new DataInputStream(socket.getInputStream());
				strOut = new DataOutputStream(socket.getOutputStream());

				new Thread(this).start();
			}catch(IOException e){
					e.printStackTrace();
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			String msg = tfInput.getText();
			
			if(source.equals(enterM)){
				try {

	                System.out.println("I passed run method");
					strOut.writeUTF(msg);
					strOut.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                updateDisplay(msg);
                System.out.println("i displayed too");
               // run();
			}
			else if(source.equals(conn)){
                connect();
			}
			else if(source.equals(subPM)){
               
			// TODO Auto-generated method stub
			//if connect was clicked
			//if send was clicked
			//if send private
			//encrypted
			//disconnect
			//... whatever else
			}
		}
		public void updateDisplay(String line){
			System.out.println("entered update");
			displayArea.append(line + "\n");
			if(dis != null) {
				displayArea.append(dis + "\n");
			}
		}
		
		
	}
	public static ChatGUI chat;
	
	public static void main(String[] args) {
		//ChatClient myClient = new ChatClient("127.0.0.1", 8080);
//		public static ChatGUI chat;
		ChatGUI myClient = null;
		if(args.length != 2){
			System.out.println("You need host name and a port address to run the client");
		}
		else{
			String serverName = args[0];
			int port = Integer.parseInt(args[1]);
			//myClient = new ChatGUI(serverName, port);
		}
		
		javax.swing.SwingUtilities.invokeLater(new Runnable(){//to create the window, so tht it appears to the user
			public void run(){
				chat = new ChatGUI();//bringing in the JFrame[the one i created and called FileJFrame] and calling it gui
				chat.setDefaultCloseOperation(chat.EXIT_ON_CLOSE);//stop running.. when user X's out of the GUI Window
				chat.setSize(500,400);// (width, length)
				chat.setVisible(true);//to make gui visible
			}
		} );//from new Runnable
		
	}
	
	
	/////////////////////////// Encryption and Decryption////////////////////////
	private class OneTimePad {

		private String plnMsg;
		private String encMsg; 
		private String msgKey; 
		
		public OneTimePad(){
			plnMsg  = "";
			encMsg = "";
			msgKey  = "";
		}
		
		public OneTimePad(String msg){
			plnMsg = msg;
			msgKey = getKey();
			encMsg = encrypt();
		}
		
		protected String getKey(){
			String key = "";
			for(int i=0; i<plnMsg.length(); i++){
				char randChar = Character.toChars( (int)(Math.random() * 60 ))[0];
				key += randChar;
				//System.out.println("KEY: "+key);
			}
			return key;
		}
		
		protected String encrypt(){
			String encryptedMsg = "";
			for(int i=0; i<plnMsg.length(); i++){
				encryptedMsg = encryptedMsg + Character.toChars(   ( (msgKey.charAt(i)) + plnMsg.charAt(i)  ) )[0];
				//System.out.println("ENCRYPTED MSG: "+ encryptedMsg );
			}
			return encryptedMsg;
		}

		protected String decrypt(){
			String decryptedMsg = "";
			for(int i=0; i<encMsg.length(); i++){
				decryptedMsg = decryptedMsg +  Character.toChars(  Math.abs( ( encMsg.charAt(i) - msgKey.charAt(i)) )  )[0];
				//System.out.println("DECRYPTED MSG: "+ decryptedMsg);
			}
			return decryptedMsg;
		}
		protected String decrypt(String e, String k){
			String decryptedMsg = "";
			for(int i=0; i<e.length(); i++){
				decryptedMsg = decryptedMsg +  Character.toChars( Math.abs(  ( e.charAt(i) - k.charAt(i)) )  )[0];
				//System.out.println("DECRYPTED MSG: "+ decryptedMsg);
			}
			return decryptedMsg;
		}
		protected String getEncrMsg(){
			return encMsg;
		}
		protected String getKeyForMsg(){
			return msgKey;
		}
		/*public static void main(String [] args){
			String plain = "hello goodbye abcdefghijklmnopqrstuvwxyz";
			OneTimePad otp = new OneTimePad(plain);
			System.out.println("The plain message was "+ plain);
			otp.decrypt(otp.encMsg, otp.msgKey);
		}*/
		
		
		
	}


	public void handle(String s) 
	{
		// TODO Auto-generated method stub
		displayArea.append(s);
		System.out.println("-------------");
	}

}
