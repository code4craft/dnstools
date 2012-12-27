package us.codecraft.dnstools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public class MacInetInetManager implements InetConnectionManager {

	private String shell = "/Users/cairne/Documents/dp_workspace/dnstools/setdns.sh";

	// nslookup a

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.dnstools.InetConnectionManager#getConnectionProperties(java
	 * .lang.String)
	 */
	@Override
	public InetConnectinoProperties getConnectionProperties(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	private void setDns(List<String> dns) throws IOException {
		Process exec = Runtime.getRuntime().exec(
				shell + " " + StringUtils.join(dns, " "));
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(exec.getInputStream()));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}
	}

	public static void main(String[] args) {
		MacInetInetManager macInetInetManager = new MacInetInetManager();
		try {
			macInetInetManager
					.setDns(Arrays.asList("127.0.0.1", "192.168.0.1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getDns() throws IOException {
		Process exec = Runtime.getRuntime().exec("nslookup www.baidu.com");
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(exec.getInputStream()));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			System.out.println(line);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.dnstools.InetConnectionManager#getDefaultConnectionProperties
	 * ()
	 */
	@Override
	public InetConnectinoProperties getDefaultConnectionProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.dnstools.InetConnectionManager#setConnectionProperties(us
	 * .codecraft.dnstools.InetConnectinoProperties)
	 */
	@Override
	public void setConnectionProperties(InetConnectinoProperties connectino) {

	}

}
