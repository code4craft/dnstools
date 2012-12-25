package us.codecraft.dnstools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public class WindowsInetManager implements InetConnectionManager {

	private static final List<String> KEY_DNS_SERVER = Collections.singletonList("DNS 服务器");
	private static final List<String> KEY_DHCP_ENABLE = Collections.singletonList("DHCP 已启用");
	private static final List<String> KEY_DHCP_SERVER = Collections.singletonList("DHCP 服务器");
	private static final List<String> KEY_IPV4 = Collections.singletonList("IPv4 地址");
	private static final List<String> KEY_GATEWAY = Collections.singletonList("默认网关");
	private static final String SEPERATOR = ". .";

	private Pattern patternForTitleLine = Pattern.compile("^[^\\s]+");

	private Map<String, InetConnectinoProperties> getIpconfig() {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			Process exec = Runtime.getRuntime().exec("ipconfig /all");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "GBK"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				Matcher matcher = patternForTitleLine.matcher(line);
				if (matcher.find()) {
					getInetConnectinoProperties(stringBuilder.toString());
					stringBuilder = new StringBuilder();
				}
				stringBuilder.append(line + "\n");

			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("call command \"ipconfig /all\" error, maybe it's disabled.");
		}
	}

	/**
	 * use a connection status to generate an InetConnectinoProperties
	 * 
	 * @return
	 */
	private InetConnectinoProperties getInetConnectinoProperties(String block) {
		System.out.println(block);
		return null;
	}

	public static void main(String[] args) {
		WindowsInetManager inetManager = new WindowsInetManager();
		System.out.println(inetManager.getIpconfig());
	}

	/**
	 * 
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#getDefaultConnectionProperties()
	 */
	@Override
	public InetConnectinoProperties getDefaultConnectionProperties() {
		String ipconfig = getIpconfig();
		return null;
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#getConnectionProperties(java.lang.String)
	 */
	@Override
	public InetConnectinoProperties getConnectionProperties(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#setConnectionProperties(us.codecraft.dnstools.InetConnectinoProperties)
	 */
	@Override
	public void setConnectionProperties(InetConnectinoProperties connectino) {
		// TODO Auto-generated method stub

	}

}
