package Controller;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import Controller.entity.service.DictService;

@Component
public class Init {

	private static final Logger LOG = LoggerFactory.getLogger(Init.class);

	@Autowired
	private DictService dictService;

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	@Autowired
	RequestMappingInfoHandlerMapping rmim;

	@PostConstruct
	void init() {

		BeanNameUrlHandlerMapping b = new BeanNameUrlHandlerMapping();
		Map<String, Object> mapping = b.getHandlerMap();
		for (Entry<String, Object> en : mapping.entrySet()) {
			System.err.println(en.getKey());
			System.err.println(en.getValue());
		}

		System.out.println(rmim == null);
		try {
			int count = dictService.getCount(null);
			if (count < 1) {
				Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
				for (Entry<RequestMappingInfo, HandlerMethod> en : mappings.entrySet()) {
					System.out.println(en.getKey());
					Iterator<String> it = en.getKey().getPatternsCondition().getPatterns().iterator();
					while (it.hasNext())
						System.out.print(it.next().replaceAll("([{].*[}])", "**")+"			");
					System.out.println(en.getValue());
				}
			}
		} catch (Throwable e) {
			LOG.error(null, e);
		}
	}

	public static void main(String[] args) {
		String a = "a{aaaa}a";
		System.err.println(a.replaceAll("([{].*[}])", "123"));
	}

}
