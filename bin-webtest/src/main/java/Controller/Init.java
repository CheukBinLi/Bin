package Controller;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import Controller.entity.service.DictService;

public class Init {

	private static final Logger LOG = LoggerFactory.getLogger(Init.class);

	@Autowired
	private DictService dictService;

	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	void init() {
		try {
			int count = dictService.getCount(null);
			if (count < 1) {
				Map<RequestMappingInfo, HandlerMethod> mappings = requestMappingHandlerMapping.getHandlerMethods();
				for (Entry<RequestMappingInfo, HandlerMethod> en : mappings.entrySet()) {

				}
			}
		} catch (Throwable e) {
			LOG.error(null, e);
		}

	}

}
