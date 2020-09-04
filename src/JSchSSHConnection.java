import java.io.InputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class JSchSSHConnection {
	private String hostname;
	private String account;
	private String pwd;
	private short port = 22;
	
	/**
	 * JSch Example Tutorial
	 * Java SSH Connection Program
	 */
	public String execCommand(String command) throws JSchException, Exception {
	    String host = this.hostname;
	    String user= this.account;
	    String password= this.pwd ;
	    String result = new String();
	    
	    java.util.Properties config = new java.util.Properties(); 
    	config.put("StrictHostKeyChecking", "no");
    	JSch jsch = new JSch();
    	Session session=jsch.getSession(user, host, 22);
    	session.setPassword(password);
    	session.setConfig(config);
	    session.connect();
    	System.out.println("Connected");
    	
    	Channel channel=session.openChannel("exec");
        ((ChannelExec)channel).setCommand(command);
        channel.setInputStream(null);
        ((ChannelExec)channel).setErrStream(System.err);
        
	    try{
	        InputStream in=channel.getInputStream();
	        channel.connect();
	        byte[] tmp=new byte[1024];
	        while(true){
	          while(in.available()>0){
	            int i=in.read(tmp, 0, 1024);
	            if(i<0)break;
	            //System.out.print(new String(tmp, 0, i));
	            result += new String(tmp, 0, i);
	          }
	          if(channel.isClosed()){
	            //System.out.println("exit-status: "+channel.getExitStatus());
	            break;
	          }
	          try{Thread.sleep(1000);}catch(Exception ee){}
	        }
	        //System.out.println("DONE");
	        return result;
	    } catch(JSchException accountPwdFaild) {
	    	accountPwdFaild.printStackTrace();
	    	accountPwdFaild.getMessage();
	    	throw accountPwdFaild;
	    } catch(Exception e){
	    	e.printStackTrace();
	    	e.getMessage();
	    	throw e;
	    } finally {
	    	channel.disconnect();
	        session.disconnect();
	    }
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public short getPort() {
		return port;
	}

	public void setPort(short port) {
		this.port = port;
	}

}
