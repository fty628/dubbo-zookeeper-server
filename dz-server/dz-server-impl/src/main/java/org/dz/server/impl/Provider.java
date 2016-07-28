package org.dz.server.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;


public class Provider extends AbstractIdleService{

	protected static final Logger logger = LoggerFactory.getLogger(Provider.class);

	private static final String[] CONFIG_LOCATIONS = new String[] { "classpath*:spring-config.xml"};

	private GenericXmlApplicationContext context;
	private String activeProfile = "dev";
	
	public Provider(String activeProfile) {
		super();
		this.activeProfile = activeProfile;
	}

	public static void main(String[] args) throws Exception {
		String activeProfile = "dev";
		if (args != null && args.length > 0) {
			activeProfile = args[0];
		}
		Provider provider = new Provider(activeProfile);
		provider.startAsync();
        try {
            Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    lock.wait();
                }
            }
        } catch (InterruptedException ex) {
            System.err.println("ignoreinterruption");
        }
	}

	@Override
	protected void startUp() throws Exception {
		long beginTime = System.currentTimeMillis();
		try {
			System.out.println("INFO: Dubbo + Zookeeper Provider starting ...");
			context = new GenericXmlApplicationContext();
			context.getEnvironment().setActiveProfiles(activeProfile);
			context.load(CONFIG_LOCATIONS);
			System.out.println("INFO: Dubbo + Zookeeper Provider Environment -- " + activeProfile);
			context.refresh();
			System.out.println(context);
			long endTime = System.currentTimeMillis();
			System.out.println("INFO: Dubbo + Zookeeper Provider startup in " + (endTime - beginTime) + " ms");
			context.registerShutdownHook();
		} catch (Exception e) {
			System.err.println("INFO: Dubbo + Zookeeper Provider startup failure.");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	protected void shutDown() throws Exception {
		if(context != null){
			context.stop();
		}
	    System.out.println("-------------service stoppedsuccessfully-------------");
	}
}
