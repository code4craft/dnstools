package us.codecraft.dnstools;

import java.util.List;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 25, 2012
 */
public interface DnsManager {

	public List<String> getDNS();

	public void setDNS(List<String> dns);

	public String getIp();

	public void setIp();

	public String getGateway();

	public void setGateway();

}
