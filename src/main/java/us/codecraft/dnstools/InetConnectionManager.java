package us.codecraft.dnstools;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public interface InetConnectionManager {

	public InetConnectinoProperties getConnectionProperties(String name);

	public InetConnectinoProperties getDefaultConnectionProperties();

	public void setConnectionProperties(InetConnectinoProperties connectino);

}
