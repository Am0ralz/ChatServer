package chat_v7;
/*
 * I am writing this class as a separate public class
 * Please take it... rewrite... as a private nested class of your ChatClient
 * 
 */
public class OneTimePad {

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
			System.out.println("DECRYPTED MSG: "+ decryptedMsg);
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
