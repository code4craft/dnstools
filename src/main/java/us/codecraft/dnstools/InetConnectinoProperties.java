/**
 * Project: dnstools
 * 
 * File Created at 2012-12-25
 * $Id$
 * 
 * Copyright 2010 dianping.com.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Dianping Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with dianping.com.
 */
package us.codecraft.dnstools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * InetConnectinoProperties
 * 
 * @author yihua.huang
 * 
 */
public class InetConnectinoProperties {

	public static final String KEY_NAME = "name";
	public static final String KEY_DNS_SERVER = "dns_server";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_DHCP_ENABLE = "dhcp_enable";
	public static final String KEY_DHCP_SERVER = "dhcp_server";
	public static final String KEY_IPV4 = "ipv4_address";
	public static final String KEY_IPV6 = "ipv6_address";
	public static final String KEY_GATEWAY = "gateway";

	private final Map<String, List<String>> properties;

	/**
	 * 
	 */
	public InetConnectinoProperties() {
		properties = new HashMap<String, List<String>>();
	}

	public InetConnectinoProperties(Map<String, List<String>> map) {
		properties = map;
	}

	private String getSingle(String key) {
		List<String> list = properties.get(key);
		if (list == null || list.size() < 1) {
			return null;
		}
		return list.get(0);
	}

	private void setSingle(String key, String value) {
		properties.put(key, Collections.singletonList(value));
	}

	private List<String> getList(String key) {
		return properties.get(key);
	}

	private void setList(String key, List<String> list) {
		properties.put(key, list);
	}

	@SuppressWarnings("unused")
	private void addToList(String key, String value) {
		List<String> list = properties.get(key);
		if (list == null) {
			list = new ArrayList<String>();
			properties.put(key, list);
		}
		list.add(value);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return getSingle(KEY_NAME);
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		setSingle(KEY_NAME, name);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return getSingle(KEY_DESCRIPTION);
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		setSingle(KEY_DESCRIPTION, description);
	}

	/**
	 * @return the ipv4Address
	 */
	public String getIpv4Address() {
		return getSingle(KEY_IPV4);
	}

	/**
	 * @param ipv4Address
	 *            the ipv4Address to set
	 */
	public void setIpv4Address(String ipv4Address) {
		setSingle(KEY_IPV4, ipv4Address);
	}

	/**
	 * @return the ipv6Address
	 */
	public String getIpv6Address() {
		return getSingle(KEY_IPV6);
	}

	/**
	 * @param ipv6Address
	 *            the ipv6Address to set
	 */
	public void setIpv6Address(String ipv6Address) {
		setSingle(KEY_IPV6, ipv6Address);
	}

	/**
	 * @return the dnsServer
	 */
	public List<String> getDnsServer() {
		return getList(KEY_DNS_SERVER);
	}

	/**
	 * @param dnsServer
	 *            the dnsServer to set
	 */
	public void setDnsServer(List<String> dnsServer) {
		setList(KEY_DNS_SERVER, dnsServer);
	}

	/**
	 * 
	 * @return the gateway
	 */
	public String getGateway() {
		return getSingle(KEY_GATEWAY);
	}

	/**
	 * @param gateway
	 *            the gateway to set
	 */
	public void setGateway(String gateway) {
		setSingle(KEY_GATEWAY, gateway);
	}

	private final static List<String> booleanTrueDict = Arrays.asList("是", "yes", "true", "YES", "TRUE", "1");

	private boolean convertToBoolean(String value) {
		return booleanTrueDict.contains(value);
	}

	/**
	 * @return the isDHCPenabled
	 */
	public boolean isDHCPenabled() {
		return convertToBoolean(getSingle(KEY_DHCP_ENABLE));
	}

	/**
	 * @param isDHCPenabled
	 *            the isDHCPenabled to set
	 */
	public void setDHCPenabled(boolean isDHCPenabled) {
		setSingle(KEY_DHCP_ENABLE, String.valueOf(isDHCPenabled));
	}

	/**
	 * @return the dhcpAddress
	 */
	public String getDhcpAddress() {
		return getSingle(KEY_DHCP_SERVER);
	}

	/**
	 * @param dhcpAddress
	 *            the dhcpAddress to set
	 */
	public void setDhcpAddress(String dhcpAddress) {
		setSingle(KEY_DHCP_SERVER, dhcpAddress);
	}

	/**
	 * check whether a key need a ipv4 value
	 * 
	 * @param key
	 * @return
	 */
	public static boolean needIpv4Value(String key) {
		return KEY_DHCP_SERVER.equalsIgnoreCase(key) || KEY_IPV4.equalsIgnoreCase(key)
				|| KEY_DNS_SERVER.equalsIgnoreCase(key);
	}

	private static Pattern ipv4Pattern = Pattern.compile("((?:\\d{1,3}\\.){3}\\d{1,3})");

	public static List<String> convertIpv4Address(List<String> list) {
		return getGroupOneIfMatch(ipv4Pattern, list);
	}

	/**
	 * check whether a key need a boolean value
	 * 
	 * @param key
	 * @return
	 */
	public static boolean needBooleanValue(String key) {
		return KEY_DHCP_ENABLE.equalsIgnoreCase(key);
	}

	private static List<String> getGroupOneIfMatch(Pattern pattern, List<String> lines) {
		List<String> result = new ArrayList<String>();
		for (String line : lines) {
			String groupOneIfMatch = getGroupOneIfMatch(pattern, line);
			if (groupOneIfMatch != null) {
				result.add(groupOneIfMatch);
			}
		}
		return result;
	}

	private static String getGroupOneIfMatch(Pattern pattern, String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * (non-Jsdoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InetConnectinoProperties [getName()=" + getName() + ", getDescription()=" + getDescription()
				+ ", getIpv4Address()=" + getIpv4Address() + ", isDHCPenabled()=" + isDHCPenabled()
				+ ", getDhcpAddress()=" + getDhcpAddress() + ", getDnsServer()=" + getDnsServer() + ", getGateway()="
				+ getGateway() + ", getIpv6Address()=" + getIpv6Address() + "]";
	}

}
