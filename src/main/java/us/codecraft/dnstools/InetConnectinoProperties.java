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

import java.util.List;

/**
 * TODO Comment of InetConfig
 * 
 * @author yihua.huang
 * 
 */
public class InetConnectinoProperties {

	/**
	 * 以太网适配器 本地连接:
	 * 
	 * 连接特定的 DNS 后缀 . . . . . . . : 描述. . . . . . . . . . . . . . . : Intel(R)
	 * 82578DM Gigabit Network Connection 物理地址. . . . . . . . . . . . . :
	 * 84-2B-2B-97-85-27 DHCP 已启用 . . . . . . . . . . . : 是 自动配置已启用. . . . . . .
	 * . . . : 是 本地链接 IPv6 地址. . . . . . . . : fe80::58ee:e5a:f233:de70%11(首选)
	 * IPv4 地址 . . . . . . . . . . . . : 192.168.32.58(首选) 子网掩码 . . . . . . . .
	 * . . . . : 255.255.255.0 获得租约的时间 . . . . . . . . . : 2012年12月25日 9:32:27
	 * 租约过期的时间 . . . . . . . . . : 2012年12月25日 15:02:27 默认网关. . . . . . . . . .
	 * . . . : 192.168.32.1 DHCP 服务器 . . . . . . . . . . . : 192.168.50.11
	 * DHCPv6 IAID . . . . . . . . . . . : 246983791 DHCPv6 客户端 DUID . . . . . .
	 * . : 00-01-00-01-13-C7-3D-CA-B8-AC-6F-AD-91-E2 DNS 服务器 . . . . . . . . . .
	 * . : 192.168.50.11 192.168.50.2 TCPIP 上的 NetBIOS . . . . . . . : 已启用
	 */
	private String name;

	private String description;

	private String ipv4Address;

	private String ipv6Address;

	private List<String> dnsServer;

	private String gateway;

	private boolean isDHCPenabled;

	private String dhcpAddress;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the ipv4Address
	 */
	public String getIpv4Address() {
		return ipv4Address;
	}

	/**
	 * @param ipv4Address
	 *            the ipv4Address to set
	 */
	public void setIpv4Address(String ipv4Address) {
		this.ipv4Address = ipv4Address;
	}

	/**
	 * @return the ipv6Address
	 */
	public String getIpv6Address() {
		return ipv6Address;
	}

	/**
	 * @param ipv6Address
	 *            the ipv6Address to set
	 */
	public void setIpv6Address(String ipv6Address) {
		this.ipv6Address = ipv6Address;
	}

	/**
	 * @return the dnsServer
	 */
	public List<String> getDnsServer() {
		return dnsServer;
	}

	/**
	 * @param dnsServer
	 *            the dnsServer to set
	 */
	public void setDnsServer(List<String> dnsServer) {
		this.dnsServer = dnsServer;
	}

	/**
	 * 
	 * @return the gateway
	 */
	public String getGateway() {
		return gateway;
	}

	/**
	 * @param gateway
	 *            the gateway to set
	 */
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	/**
	 * @return the isDHCPenabled
	 */
	public boolean isDHCPenabled() {
		return isDHCPenabled;
	}

	/**
	 * @param isDHCPenabled
	 *            the isDHCPenabled to set
	 */
	public void setDHCPenabled(boolean isDHCPenabled) {
		this.isDHCPenabled = isDHCPenabled;
	}

	/**
	 * @return the dhcpAddress
	 */
	public String getDhcpAddress() {
		return dhcpAddress;
	}

	/**
	 * @param dhcpAddress
	 *            the dhcpAddress to set
	 */
	public void setDhcpAddress(String dhcpAddress) {
		this.dhcpAddress = dhcpAddress;
	}

}
