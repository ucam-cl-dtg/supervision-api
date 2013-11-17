package uk.ac.cam.cl.dtg.teaching.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.DefaultComponentSafeNamingStrategy;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.spi.Stoppable;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

	private static Logger log = LoggerFactory.getLogger(HibernateUtil.class);
	private static HibernateUtil singleton = null;
	
	public static HibernateUtil getInstance() {
		while(singleton == null) {
			synchronized(HibernateUtil.class) {
				if(singleton == null) {
					singleton = init("/hibernate.cfg.xml");					
				}
			}
		}
		return singleton;
	}
	
	public static void init() {
		init("/hibernate.cfg.xml");
	}

	public static HibernateUtil init(String configurationFile) {
		HibernateUtil o = new HibernateUtil();
		try {
			Configuration configuration = new Configuration();
			configuration.configure(configurationFile);
			configuration
					.setNamingStrategy(new DefaultComponentSafeNamingStrategy());

			if ("update".equals(configuration
					.getProperty("hibernate.hbm2ddl.auto"))) {
				SchemaUpdate update = new SchemaUpdate(configuration);
				update.execute(true, true);
				configuration.setProperty("hibernate.hbm2ddl.auto", "validate");
			}

			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = configuration
					.buildSessionFactory(serviceRegistry);

			o.sf = sessionFactory;
		} catch (HibernateException e) {
			log.error(
					"Exception occurred when building SessionFactory {}.  Deferring until use.",
					e.getMessage());
			o.heldException = e;
		}
		return o;
	}

	private SessionFactory sf = null;
	private HibernateException heldException = null;
	
	public void close() {
		if (sf instanceof SessionFactoryImpl) {
			SessionFactoryImpl i = (SessionFactoryImpl)sf;
			ConnectionProvider p = i.getConnectionProvider();
			if (p instanceof Stoppable) {
				log.info("Stopping ConnectionProvider {}",p.toString());
				((Stoppable)p).stop();
			}
		}
		sf.close();
	}
	
	public boolean isReady() {
		return sf != null;
	}

	public SessionFactory getSF() throws HibernateException {
		if (sf == null) {
			throw new HibernateException("SessionFactory was not initialised",
					heldException);
		} else {
			return sf;
		}
	}

	public Session getSession() throws HibernateException {
		Session session = getSF().getCurrentSession();

		if (!session.isOpen()) {
			session = sf.openSession();
		}

		if (!session.getTransaction().isActive()) {
			session.beginTransaction();
		}

		return session;
	}
}