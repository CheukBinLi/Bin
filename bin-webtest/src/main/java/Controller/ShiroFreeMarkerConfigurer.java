package Controller;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class ShiroFreeMarkerConfigurer extends FreeMarkerConfigurer {

	@Override
	public Configuration createConfiguration() throws IOException, TemplateException {
		Configuration configuration = super.createConfiguration();
		configuration.setSharedVariable("shiro", new ShiroTags());
		return configuration;
	}
}
