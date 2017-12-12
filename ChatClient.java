package chat_v7;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import chat_v7.ChatServer;

/*
 * ChatClient version 4  sends to the server.. and receives from the server
 * Private Messaging //pm:12345-blahblhablahblahblahblahblah
 */
public class ChatClient implements Runnable{
	private Socket socket = null;
	BufferedReader console = null;
	private DataOutputStream strOut = null;
	private Thread thread = null;
	private ChatClientThread client = null;
	private String line = "";
	private boolean done = true;
	
	public ChatClient(String serverName, int serverPort){
		try {
			socket = new Socket(serverName, serverPort);//step 1	
			start();//step 2
			//stop();//step 4
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handle(String msg){
		System.out.println("--------" + msg);
		int indexOfMsgStart = msg.lastIndexOf(": ")+2;//find where the real Msg starts... because of what the server sticks onto the end
		String realMsg = msg.substring(indexOfMsgStart);//get the real msg from the "User said blah blah: ...."
		int midIndex = realMsg.length()/2 ;//0 to midIndex non-inclusive encr ... from midIndex to end is key
		String encMsg = realMsg.substring(0, midIndex);//0 to midIndex non-inclusive encr
		String keyMsg = realMsg.substring(midIndex);//from midIndex to end 
		OneTimePad otp = new OneTimePad();
		String decMsg = otp.decrypt(encMsg, keyMsg);//I know the enc and the key... can decrypt
		
		
		if(decMsg.equalsIgnoreCase("bye")){
			line="bye";
			stop();
		}
		else{
			System.out.println(decMsg);
			
		}
	}
	@Override
	public void run() {
		while( (thread!=null)  && (!line.equalsIgnoreCase("bye"))){//step 3
			try {
				line = console.readLine();
				OneTimePad otp = new OneTimePad(line);//make all messages encrypted
				String longerENC_KEY_MSG = otp.getEncrMsg() +otp.getKeyForMsg();//build a string double the lenght of the msg... include the encr + key 
				strOut.writeUTF(longerENC_KEY_MSG);
				
				strOut.flush();
			} catch (IOException e) {
				System.err.println("Problem reading line from client: "+e.getMessage());
			}
			
		}// TODO Auto-generated method stub
	}
	public void start() throws IOException{
		console = new BufferedReader(new InputStreamReader(System.in));//step 2a
		strOut = new DataOutputStream(socket.getOutputStream());//step 2b
		if(thread==null){
			client = new ChatClientThread(this, socket);
			thread = new Thread(this);
			thread.start();
		}
	
	
	}
	public void stop(){
		done=true;
		if(thread!=null){
			thread=null;
		}
		try{
			if(console != null){
				console.close();
			}if(strOut != null){
				strOut.close();
			}if(socket != null){
				socket.close();
			}
		}
		catch(IOException e){
			System.out.println("STOPPING ERROR"+e.getMessage());
		}
	}
	public static void main(String[] args) {
		//ChatClient myClient = new ChatClient("127.0.0.1", 8080);
		ChatClient myClient = null;
		if(args.length != 2){
			System.out.println("You need host name and a port address to run the client");
		}
		else{
			String serverName = args[0];
			int port = Integer.parseInt(args[1]);
			myClient = new ChatClient(serverName, port );
		}
	}

}
