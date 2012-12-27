package us.codecraft.dnstools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public class WindowsInetManager implements InetConnectionManager {

	private Logger logger = Logger.getLogger(getClass());

	private final Map<String, List<String>> keys = new HashMap<String, List<String>>();

	private final static List<String> TYPE_PREFIX = Arrays.asList("以太网适配器", "隧道适配器");

	public WindowsInetManager() {
		initMap();
	}

	private void initMap() {
		keys.put(InetConnectinoProperties.KEY_DNS_SERVER,
				Collections.singletonList("DNS 服务器"));
		keys.put(InetConnectinoProperties.KEY_DESCRIPTION,
				Collections.singletonList("描述"));
		keys.put(InetConnectinoProperties.KEY_DHCP_ENABLE,
				Collections.singletonList("DHCP 已启用"));
		keys.put(InetConnectinoProperties.KEY_DHCP_SERVER,
				Collections.singletonList("DHCP 服务器"));
		keys.put(InetConnectinoProperties.KEY_IPV4,
				Collections.singletonList("IPv4 地址"));
		keys.put(InetConnectinoProperties.KEY_GATEWAY,
				Collections.singletonList("默认网关"));
	}

	private Pattern titleLinePattern = Pattern.compile("^[^\\s]+");

	private Map<String, InetConnectinoProperties> getIpconfig() {
		Map<String, InetConnectinoProperties> inetConnectinoPropertiesMap = new HashMap<String, InetConnectinoProperties>();
		try {
			StringBuilder stringBuilder = new StringBuilder();
			Process exec = Runtime.getRuntime().exec("ipconfig /all");
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(exec.getInputStream(), "GBK"));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				Matcher matcher = titleLinePattern.matcher(line);
				if (matcher.find()) {
					InetConnectinoProperties inetConnectinoProperties = getInetConnectinoProperties(stringBuilder
							.toString());
					if (inetConnectinoProperties != null) {
						System.out.println(inetConnectinoProperties);
						inetConnectinoPropertiesMap.put(
								inetConnectinoProperties.getName(),
								inetConnectinoProperties);
					}
					stringBuilder = new StringBuilder();
				}
				stringBuilder.append(line + "\n");

			}
			return inetConnectinoPropertiesMap;
		} catch (Exception e) {
			throw new RuntimeException(
					"call command \"ipconfig /all\" error, maybe it's disabled.");
		}
	}

	/**
	 * use a connection status to generate an InetConnectinoProperties
	 * 
	 * @return
	 */
	private InetConnectinoProperties getInetConnectinoProperties(String block) {
		if (block == null) {
			return null;
		}
		if (block.trim().length() == 0) {
			return null;
		}

		String[] lines = block.split("\\n+");
		if (lines.length < 1) {
			return null;
		}
		String name = lines[0];
		name = processName(name);
		Map<String, List<String>> winProperties = processWinProperties(lines, 1);
		Map<String, List<String>> propertiMaps = getInetPropertiesKey(winProperties);
		propertiMaps.put(InetConnectinoProperties.KEY_NAME, Collections.singletonList(name));
		return new InetConnectinoProperties(propertiMaps);
	}

	private Pattern nameTypePattern = Pattern.compile("^([^\\s]+)");

	private String processName(String name) {
		if (name.endsWith(":")) {
			name = name.substring(0, name.length() - 1);
		}
<<<<<<< HEAD
		name = name.trim();
		String nameType = getGroupOneIfMatch(nameTypePattern, name);
		if (nameType != null) {
			nameType = nameType.trim();
			if (TYPE_PREFIX.contains(nameType)) {
				int indexOf = name.indexOf(nameType);
				name = name.substring(indexOf + nameType.length()).trim();
			}
		}
		return name;

=======
		Map<String, List<String>> winProperties = processProperties(lines, 1);
		Map<String, List<String>> propertiMaps = getInetPropertiesKey(winProperties);
		propertiMaps.put(InetConnectinoProperties.KEY_NAME,
				Collections.singletonList(name));
		return new InetConnectinoProperties(propertiMaps);
>>>>>>> 4e4cc5ae7a89333156638abfa5dbaeecf5c757ab
	}

	private String convertWinPropertiesToInetProperties(String winkey) {
		for (Entry<String, List<String>> entry : keys.entrySet()) {
			if (entry.getValue().contains(winkey)) {
				return entry.getKey();
			}
		}
		return null;
	}

	private final static List<String> booleanTrueDict = Arrays.asList("是",
			"yes", "true", "YES", "TRUE", "1");

	private String convertToBoolean(String value) {
		return String.valueOf(booleanTrueDict.contains(value));
	}

	private List<String> convertToBoolean(List<String> values) {
		List<String> list = new ArrayList<String>();
		for (String value : values) {
			list.add(convertToBoolean(value));
		}
		return list;
	}

<<<<<<< HEAD
	private Map<String, List<String>> getInetPropertiesKey(Map<String, List<String>> winProperties) {
		Map<String, List<String>> propertiMaps = new LinkedHashMap<String, List<String>>();
=======
	private Map<String, List<String>> getInetPropertiesKey(
			Map<String, List<String>> winProperties) {
		Map<String, List<String>> propertiMaps = new HashMap<String, List<String>>();
>>>>>>> 4e4cc5ae7a89333156638abfa5dbaeecf5c757ab
		for (Entry<String, List<String>> entry : winProperties.entrySet()) {
			String key = convertWinPropertiesToInetProperties(entry.getKey());
			if (key == null) {
				continue;
			}
			List<String> value = entry.getValue();
			if (InetConnectinoProperties.needIpv4Value(key)) {
				value = InetConnectinoProperties.convertIpv4Address(value);
			}
			if (InetConnectinoProperties.needBooleanValue(key)) {
				value = convertToBoolean(value);
			}
			propertiMaps.put(key, value);
		}
		return propertiMaps;
	}

	private Pattern keyPattern = Pattern.compile("^\\s*([^\\.]+)");
	private Pattern valuePattern = Pattern.compile(":\\s*(.*)");

	private String getGroupOneIfMatch(Pattern pattern, String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

<<<<<<< HEAD
	private Map<String, List<String>> processWinProperties(String[] lines, int start) {
		Map<String, List<String>> winPropertiMaps = new LinkedHashMap<String, List<String>>();
=======
	private Map<String, List<String>> processProperties(String[] lines,
			int start) {
		Map<String, List<String>> winPropertiMaps = new HashMap<String, List<String>>();
>>>>>>> 4e4cc5ae7a89333156638abfa5dbaeecf5c757ab
		String lastKey = InetConnectinoProperties.KEY_NAME;
		for (int i = start; i < lines.length; i++) {
			String line = lines[i];
			if (!line.contains(":")) {
				String value = line.trim();
				if (winPropertiMaps.get(lastKey) != null) {
					winPropertiMaps.get(lastKey).add(value);
				}
				continue;
			}
			String key = getGroupOneIfMatch(keyPattern, line);
			if (key == null) {
				continue;
			} else {
				key = key.trim();
			}
			List<String> list = new ArrayList<String>();
			String value = getGroupOneIfMatch(valuePattern, line);
			if (value != null) {
				list.add(value);
				value = value.trim();
			}
			winPropertiMaps.put(key, list);
			lastKey = key;

		}
		return winPropertiMaps;
	}

	public static void main(String[] args) {
		WindowsInetManager inetManager = new WindowsInetManager();
		inetManager.getIpconfig();
	}

	private final static String DEFAULT_CONNECTION_NAME = "本地连接";

	/**
	 * 
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#getDefaultConnectionProperties()
	 */
	@Override
	public InetConnectinoProperties getDefaultConnectionProperties() {
		Map<String, InetConnectinoProperties> ipconfig = getIpconfig();
		if (ipconfig.get(DEFAULT_CONNECTION_NAME) != null) {
			return ipconfig.get(DEFAULT_CONNECTION_NAME);
		}
		return ipconfig.entrySet().iterator().next().getValue();
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#getConnectionProperties(java.lang.String)
	 */
	@Override
	public InetConnectinoProperties getConnectionProperties(String name) {
		return null;
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#setConnectionProperties(us.codecraft.dnstools.InetConnectinoProperties)
	 */
	@Override
	public void setConnectionDns(InetConnectinoProperties connectino) {
		List<String> dnsServer = connectino.getDnsServer();
		if (dnsServer == null) {
			logger.info("No dns server, abort setting.");
			return;
		}
		setConnectionDns(connectino.getName(), dnsServer);
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#setConnectionDns(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void setConnectionDns(String name, List<String> dnsServers) {
		try {
			if (dnsServers.size() >= 1) {
				Process exec = Runtime.getRuntime().exec(
						"netsh interface ip set dns \"" + name + "\" static " + dnsServers.get(0));
				if (logger.isDebugEnabled()) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(),
							"GBK"));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						logger.debug(line);
					}
				}
			}
			if (dnsServers.size() >= 2) {
				Process exec = Runtime.getRuntime().exec(
						"netsh interface ip add dns name=\"" + name + "\" addr=" + dnsServers.get(1) + " index=2 ");
				if (logger.isDebugEnabled()) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(),
							"GBK"));
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						logger.debug(line);
					}
				}

			}
		} catch (IOException e) {
			logger.warn("set dns error ", e);
		}
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#setConnectionDHCPEnabled(us.codecraft.dnstools.InetConnectinoProperties)
	 */
	@Override
	public void setConnectionDHCPEnabled(InetConnectinoProperties connectino) {
		// TODO Auto-generated method stub

	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see us.codecraft.dnstools.InetConnectionManager#setConnectionDHCPEnabled(java.lang.String,
	 *      boolean)
	 */
	@Override
	public void setConnectionDHCPEnabled(String name, boolean enabled) {
		// TODO Auto-generated method stub

	}

}
