package us.codecraft.dnstools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public class MacInetInetManager implements InetConnectionManager {

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

	public static void main(String[] args) {
		MacInetInetManager macInetInetManager = new MacInetInetManager();
		try {
			macInetInetManager.getDns();
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
		// TODO Auto-generated method stub

	}

}
