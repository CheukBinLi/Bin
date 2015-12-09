package Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JspProxy {

	private static Pattern pattern = Pattern.compile(".*charset=(.*)");
	private Matcher matcher;
	//	"<result>(.*)</result>"

	public static void main(String[] args) {
		JspProxy j = new JspProxy();
		j.matcher = j.pattern.matcher("Content-Type : [text/html; charset=GBK");
		while (j.matcher.find()) {
			System.err.println(j.matcher.group(1));
		}
	}

	public static String getProxy(String address) throws IOException {
		if (!address.startsWith("http://"))
			address = "http://" + address;
		URL url = new URL(address);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		//		httpURLConnection.setRequestProperty("Accept-Charset:", "UTF-8");
		//		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		InputStream in = httpURLConnection.getInputStream();
		int code = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((code = in.read()) != -1) {
			out.write(code);
		}
		Map<String, List<String>> o = httpURLConnection.getHeaderFields();

		Iterator<Entry<String, List<String>>> it = o.entrySet().iterator();
		Entry<String, List<String>> en;
		while (it.hasNext()) {
			en = it.next();
			System.out.println(en.getKey() + " : " + en.getValue());
		}
		List<String> contentType = o.get("Content-Type");
		//		System.out.println(contentType);
		//		String[] values = contentType.get(0).split(";");
		//		values = values[1].split("=");
		//		System.err.println(values[1]);
		
		//meta
		
		String encode = "UTF-8";
		Matcher matcher;
		if (null != contentType && contentType.size() > 0) {
			matcher = pattern.matcher(contentType.get(0));
			while (matcher.find()) {
				encode = matcher.group(1);
			}
		}
		String result = new String(out.toByteArray(), encode);
		return result;
	}

}
