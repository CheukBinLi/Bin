请跳转到2.01
https://github.com/fdisk123/Bin/tree/2.01

@Maven打包

							<module>bin-annotation</module>
							<module>bin-cache</module>
							<module>bin-util</module>
							<module>bin-bean</module>
							<module>bin-net</module>
							<module>bin-anythingtest</module>

@例子:

	IOC/AOP:
	可以用xml写配置。 mc-bean 包里包含MC.dtd(约束)、bean.xml两个文件(例子，可以配合com.cheuks.bin.anythingtest.bean.mc.mc_test.java例子运行)
	可以不用xml直接传入描述路径运行即可。
@例子:

		public static void main(String[] args) throws Throwable {
		
            	 //初始化（全局只要运行一次即可）(完成模式，既使用xml，又使用注解)
            	 //new DefaultApplicationContext("bean.xml");
            	 
            	 //或者(完成模式，既使用xml，又使用注解)
      			 //ApplicationContext ac = new DefaultApplicationContext("bean.xml");
      			 
      			 //或者(注解模式，不使用xml)
      			 //new DefaultApplicationContext("test", false, true);
      			 
      			 //或者(注解模式，不使用xml)
			     ApplicationContext ac = new DefaultApplicationContext("test",false, true);
			     
			     //获取实例
			     //IocTest1 i =ac.getBeans("IocTest1");
			     //或者(获取实例)
			     //IocTest1 i = BeanFactory.getBean("IocTest1");
			     //或者(获取实例)
			     IocTest1 i = DefaultApplicationContext.getBean("IocTest1");
			     //运行
		       	 i.aaxx("xxxx");
		       }
		       
注解说明:
@Register  要注入、要注入实现的类使用
      
      @Register
      class a{}
      
@AutoLoad 
      注入后自动装载实现类
      
      @Register
      class a{
        @AutoLoad
        private X x;
      }
      
@Intercept 拦截器(拦截整合类下所有方法/拦截某方法)
拦截器一定要实现com.ben.mc.bean.classprocessing.handler.Interception 接口

      //拦截所有的方法
      @Register
      @Intercept("com.ben.mc.AnthingTest.mc.xml.DefaultInterceptionXml")
      class a{
        @AutoLoad
        private void x(){}
         private void x2(){}
      }
      
      //或者
      //只拦截x() 方法
      @Register
      class a{
        @AutoLoad
        @Intercept("com.ben.mc.AnthingTest.mc.xml.DefaultInterceptionXml")
        private void x(){}
         private void x2(){}
      }

xml配置说明(xml配置顺序必需严格按照 InitSystemClassLoader,Bean,Intercept,ScanToPack 否则报错抛出)
<?xml version="1.0" encoding="UTF-8"?>
<!--指定 约束DTD路径 在mc-bean里有-->
<!-- <!DOCTYPE Context SYSTEM "file:///E:/javaProject/Eclipse/MC/mc-util/src/main/java/Mc.dtd"> -->
<!-- 或者放在同一目录-->
<!DOCTYPE Context SYSTEM "Mc.dtd">
<Context>
	<CloneModel value="true" /><!-- 开启对象克隆模式-->
	<InitSystemClassLoader value="true" />
	<!-- 注入例子-->
	<Bean name="bi" class="test.B" type="field" ref="xxxxxxxxxxx110" /><!-- test.B里面 bi的字段 要注入xxxxxxxxxxx110名字下的实例(test.BII)
	<Bean name="xxxxxxxxxxx110" class="test.BII" type="class" /><!--自动扫描 test 关键字下的所有包-->注册一个对象 type="class"-->

	<!-- 拦截器 methods="all" 默认 --><!-- 拦截 aaxx3 和 aaxx2 只方法 拦截实现 com.ben.mc.AnthingTest.mc.xml.DefaultInterceptionXml -->
	<Intercept methods="aaxx3,aaxx2" class="com.ben.mc.AnthingTest.mc.xml.XmlIocTest1" name="intercept0083" ref="com.ben.mc.AnthingTest.mc.xml.DefaultInterceptionXml" />

	<ScanToPack value="test,test.**.a" /><!--自动扫描1: test 关键字下的所有包,2 扫描test.**.a 关键字路径下的所有包 -->
</Context>


@例子:  远程调用

      服务端 :com.cheuks.bin.net.server.test.ServerX.java
      客户端 :com.cheuks.bin.net.server.test.clientX.java

@服务端

		   public class ServerX {
			public static void main(String[] args) throws Throwable {
				Server server = NioServer.newInstance();//实现例
				server.start(2000, 10000);//运行并设置并发数，和心跳超时
				server.addService(10088, Server.SERVICE_TYPE_RMI);//添加端口服务类型（多播服务）
				server.addHandler(new ServiceHandlerTest());//添加服务处理对象
				server.addEventHandle(new EventInfo(new RmiReadEvent(), new RmiWriteEvent(), new RmiHandleEvent()), Server.SERVICE_TYPE_RMI);//添加服务类型对应的实现
			}
		}
   
      ServiceHandlerTest服务处理对象  一定要实现  com.cheuks.bin.net.server.handler.ServiceHandler  这个接口
          实现public String classID();  对象唯一标志。
          
@客户端

			public class clientX {
			public static void main(String[] args) throws Throwable {
				ApplicationContext ac = new DefaultApplicationContext("com.cheuks.bin", false, false, true);//扫描注入
				Date now = new Date();
				for (int i = 3; i-- > 0;) {
					// ServiceHandlerTestI ser = ac.getBeans("ServiceHandlerTestI"); (可以是接口，可以是class)
					ServiceHandlerTest2 ser = ac.getBeans("ServiceHandlerTest2");//获取实例
					//调用远程方法
					System.err.println("运行时间:" + (new Date().getTime() - now.getTime()) + "ms " + ser.a());
					System.out.println(ser.mmx().getMethod());
					System.err.println(ser.a("哈哈哈哈哈:" + i + ":"));
					System.err.println(ser.a("哈佬:" + i + ":", i));
					now = new Date();
					}
				}
			}

          ServiceHandlerTestI/ServiceHandlerTest2  
          
                              无论是接口还是类 只要加上下面两个注解即可以   

          @Register
          @RmiClient(path = "127.0.0.1:10088", classID = "x/1.0")
          public class ServiceHandlerTest2 implements ServiceHandlerTestI {}
          
          @RmiClient
                    path:远程服务器的IP：端口
                    classID:对应远程服务器ServiceHandlerTest的 classID
      
      
