package Controller.custom;

import java.text.Annotation;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

@Component
public class BeanScannerConfigurer implements BeanFactoryPostProcessor, ApplicationContextAware {

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println(beanFactory);
		Scanner scan = new Scanner((BeanDefinitionRegistry) beanFactory);
		scan.setResourceLoader(this.applicationContext);
	}

	static class Scanner extends ClassPathBeanDefinitionScanner {

		public Scanner(BeanDefinitionRegistry registry) {
			super(registry);
		}

		@Override
		protected void registerDefaultFilters() {
			super.registerDefaultFilters();
			this.addIncludeFilter(new AnnotationTypeFilter(MyAnnotation.class));
		}

		@Override
		protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
			return super.doScan(basePackages);
		}

		@Override
		protected boolean isCompatible(BeanDefinition newDefinition, BeanDefinition existingDefinition) {
			return super.isCompatible(newDefinition, existingDefinition);
		}

	}

}
