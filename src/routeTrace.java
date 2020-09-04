import java.io.Console;
import java.util.Scanner;

import com.jcraft.jsch.JSchException;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;



public class routeTrace {
	
	private static IPAddress getSwichIP(IPAddress host) {
		String[] ipAddr = host.toIPv4().toString().split("\\.");
		ipAddr[2] = Integer.toString((Integer.parseInt(ipAddr[2]) - (Integer.parseInt(ipAddr[2]) % 4)));
		String retIP = ipAddr[0] + "." + ipAddr[1] + "." + ipAddr[2] + "." + "1";
		return new IPAddressString(retIP).getAddress() ;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		JSchSSHConnection sshRoute = new JSchSSHConnection();
		
		if ("1".equals("2")) {
			// production
			Scanner scan = new Scanner(System.in);
			System.out.println("Please input your account: (ex: xylhsieho)");
			sshRoute.setAccount(scan.next());
			
			Console console = System.console();
			String pwd = new String(console.readPassword("Please input password:"));
			sshRoute.setPwd(pwd);
			
			System.out.println("Please input Client IP:");
			sshRoute.setHostname(scan.next());
			
		} else {
			// cat
			sshRoute.setAccount("ylhsieh");
			sshRoute.setPwd("zx1412521");
			sshRoute.setHostname("192.168.1.113");
		}

		IPAddress ipv4 = new IPAddressString(sshRoute.getHostname() + "/22").getAddress() ;
		System.out.println(ConsoleColor.YELLOW + "Switch IP" + ConsoleColor.RESET  + " -> "+ getSwichIP(ipv4).toIPv4().toString());

		System.out.println(ipv4.toIPv4().toString());	

		String result = new String();
		System.out.println("Connecting to " + ConsoleColor.GREEN + sshRoute.getHostname() + ConsoleColor.RESET);
		try {
			result = sshRoute.execCommand("ls -al");
		} catch(JSchException loginFail) {
			System.err.println(loginFail.getMessage() + ", plz check.");
			System.err.println(" -- need to cutting network IP -> " + sshRoute.getHostname());
			System.err.println(" -- account/pwd -> " + sshRoute.getAccount() + "/" + sshRoute.getPwd());
		} catch(Exception e){
			System.err.println(e.getMessage() + ", plz check.");
			System.err.println(" -- need to cutting network IP -> " + sshRoute.getHostname());
			System.err.println(" -- account/pwd -> " + sshRoute.getAccount() + "/" + sshRoute.getPwd());
	    }
		
		System.out.println(result);
		
		System.out.println("line count = " + result.split("\n").length);
	}

	
}
