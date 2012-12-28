package us.codecraft.dnstools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yihua.huang@dianping.com
 * @date Dec 27, 2012
 */
public class MiscUtils {

	private MiscUtils() {
	}

	public static Pattern ipv4Pattern = Pattern
			.compile("((?:\\d{1,3}\\.){3}\\d{1,3})");

	public static String toString(InputStream inputStream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuilder.append(line + "\n");
		}
		return stringBuilder.toString();
	}

	public static String getGroupOneIfMatch(Pattern pattern, String line) {
		if (StringUtils.isBlank(line)) {
			return null;
		}
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

}
