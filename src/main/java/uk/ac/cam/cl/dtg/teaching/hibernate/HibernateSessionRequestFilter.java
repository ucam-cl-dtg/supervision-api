package uk.ac.cam.cl.dtg.teaching.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

public class HibernateSessionRequestFilter implements Filter {

	private HibernateUtil hibernateUtil;

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		hibernateUtil = HibernateUtil.getInstance();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		if (hibernateUtil.isReady()) {
			// this should ensure that the transaction is clean i.e. it will
			// roll back if necessary
			hibernateUtil.getSF().getCurrentSession().getTransaction().begin();
		}

		chain.doFilter(request, response);

		if (hibernateUtil.isReady()) {
			Transaction t = hibernateUtil.getSF().getCurrentSession().getTransaction();
			if (t.isActive()) {
				try {
					t.commit();
				}
				catch (HibernateException e) {
					t.rollback();
				}
			}
		}
	}

	@Override
	public void destroy() {
	}
}