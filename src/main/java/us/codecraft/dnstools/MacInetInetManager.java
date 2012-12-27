package us.codecraft.dnstools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public class MacInetInetManager implements InetConnectionManager {

	private String shellPath = MacInetInetManager.class.getResource("/shell")
			.getPath();

	private Logger logger = Logger.getLogger(getClass());

	private String shellSetDns;

	private String shellGetPsid;

	private String shellGetDns;

	/**
	 * 
	 */
	public MacInetInetManager() {
		shellSetDns = shellPath + "/setdns.sh";
		shellGetPsid = shellPath + "/getpsid.sh ";
		shellGetDns = shellPath + "/getdns.sh ";
	}

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
		throw new UnsupportedOperationException();
	}

	@Override
	public void setConnectionDns(String name, List<String> dns) {
		if (name == null || dns == null || dns.size() <= 0) {
			return;
		}
		try {
			Runtime.getRuntime().exec(
					"sh " + shellSetDns + " " + StringUtils.join(dns, " "));
		} catch (IOException e) {
			logger.warn("set dns error" + e);
		}

	}

	public static void main(String[] args) {
		MacInetInetManager macInetInetManager = new MacInetInetManager();
		InetConnectinoProperties defaultConnectionProperties = macInetInetManager
				.getDefaultConnectionProperties();
		System.out.println(defaultConnectionProperties);
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
		String name = null;
		try {
			Process exec = Runtime.getRuntime().exec("sh " + shellGetPsid);
			String line = MiscUtils.toString(exec.getInputStream());
			name = line.trim();
		} catch (IOException e) {
			logger.warn("get psid failed " + e);
			return null;
		}
		InetConnectinoProperties inetConnectinoProperties = new InetConnectinoProperties();
		inetConnectinoProperties.setName(name);
		List<String> dnsServers = getDnsServers(name);
		inetConnectinoProperties.setDnsServer(dnsServers);
		return inetConnectinoProperties;
	}

	private Pattern dnsServersAddress = Pattern.compile(
			"ServerAddresses\\s*:\\s*<array>\\s*\\{(.*?)\\}", Pattern.DOTALL);

	private List<String> getDnsServers(String name) {
		List<String> dnsServers = new ArrayList<String>();
		try {
			Process exec = Runtime.getRuntime().exec(
					"sh " + shellGetDns + " " + name);
			String block = MiscUtils.toString(exec.getInputStream());
			block = MiscUtils.getGroupOneIfMatch(dnsServersAddress, block);
			if (block == null) {
				return null;
			}
			Matcher matcher = MiscUtils.ipv4Pattern.matcher(block);
			while (matcher.find()) {
				dnsServers.add(matcher.group(1));
			}
		} catch (IOException e) {
			logger.warn("get dns failed " + e);
			return null;
		}
		return dnsServers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.dnstools.InetConnectionManager#setConnectionDns(us.codecraft
	 * .dnstools.InetConnectinoProperties)
	 */
	@Override
	public void setConnectionDns(InetConnectinoProperties connectino) {
		setConnectionDns(connectino.getName(), connectino.getDnsServer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.dnstools.InetConnectionManager#setConnectionDHCPEnabled(
	 * us.codecraft.dnstools.InetConnectinoProperties)
	 */
	@Override
	public void setConnectionDHCPEnabled(InetConnectinoProperties connectino) {
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * us.codecraft.dnstools.InetConnectionManager#setConnectionDHCPEnabled(
	 * java.lang.String, boolean)
	 */
	@Override
	public void setConnectionDHCPEnabled(String name, boolean enabled) {
		throw new UnsupportedOperationException();
	}

}
