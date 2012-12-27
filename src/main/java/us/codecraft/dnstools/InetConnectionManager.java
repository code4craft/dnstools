package us.codecraft.dnstools;

import java.util.List;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public interface InetConnectionManager {

	public InetConnectinoProperties getConnectionProperties(String name);

	public InetConnectinoProperties getDefaultConnectionProperties();

	public void setConnectionDns(InetConnectinoProperties connectino);

	public void setConnectionDns(String name, List<String> dnsServers);

	public void setConnectionDHCPEnabled(InetConnectinoProperties connectino);

	public void setConnectionDHCPEnabled(String name, boolean enabled);

}
