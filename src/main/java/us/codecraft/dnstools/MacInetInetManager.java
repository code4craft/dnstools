package us.codecraft.dnstools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public class MacInetInetManager implements InetConnectionManager {

	private String shellPath = "/shell/";

	private Logger logger = Logger.getLogger(getClass());

	private String shellSetDns = shellPath + "setdns.sh";

	private String shellGetPsid = shellPath + "getpsid.sh";

	private String shellGetDns = shellPath + "getdns.sh";

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
			Process exec = exec(shellGetPsid, "");
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

	private Process exec(String filename, String param) throws IOException {
		InputStream resourceAsStream = MacInetInetManager.class
				.getResourceAsStream(filename);
		String path = MacInetInetManager.class.getClassLoader().getResource("")
				.getPath();
		File filePath = new File(path + "/temp/shell");
		if (!(filePath.exists()) && !(filePath.isDirectory())) {
			filePath.mkdirs();
		}
		String tempFileName = path + "/temp/" + filename;
		System.out.println(tempFileName);
		File tempFile = new File(tempFileName);
		OutputStream outputStream = new FileOutputStream(tempFile);
		IOUtils.copy(resourceAsStream, outputStream);
		resourceAsStream.close();
		outputStream.close();
		Process exec = Runtime.getRuntime().exec("sh " + tempFileName);
		return exec;
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
