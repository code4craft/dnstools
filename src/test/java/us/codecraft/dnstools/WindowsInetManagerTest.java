package us.codecraft.dnstools;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * only for windows
 * 
 * @author yihua.huang
 * 
 */
public class WindowsInetManagerTest {

	private WindowsInetManager dnsManager;

	@Before
	public void setUp() {
		dnsManager = new WindowsInetManager();
	}

	@Ignore
	@Test
	public void testGetDefaultConnectionProperties() {
		InetConnectinoProperties defaultConnectionProperties = dnsManager
				.getDefaultConnectionProperties();
		System.out.println(defaultConnectionProperties);
	}

	@Ignore
	@Test
	public void testSetDns() {
		InetConnectinoProperties defaultConnectionProperties = dnsManager
				.getDefaultConnectionProperties();
		System.out.println(defaultConnectionProperties);
		List<String> s = new ArrayList<String>();
		s.add("192.168.63.47");
		s.add("192.168.50.11");
		defaultConnectionProperties.setDnsServer(s);
		dnsManager.setConnectionDns(defaultConnectionProperties);
	}

}
