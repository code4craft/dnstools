package us.codecraft.dnstools;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

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

/**
 * TODO Comment of WindowsDnsManagerTest
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

	@Test
	public void testGetDefaultConnectionProperties() {
		InetConnectinoProperties defaultConnectionProperties = dnsManager.getDefaultConnectionProperties();
		System.out.println(defaultConnectionProperties);
	}

	@Test
	public void testSetDns() {
		InetConnectinoProperties defaultConnectionProperties = dnsManager.getDefaultConnectionProperties();
		System.out.println(defaultConnectionProperties);
		List<String> s = new ArrayList<String>();
		s.add("192.168.63.47");
		s.add("192.168.50.11");
		defaultConnectionProperties.setDnsServer(s);
		dnsManager.setConnectionDns(defaultConnectionProperties);
	}

}
