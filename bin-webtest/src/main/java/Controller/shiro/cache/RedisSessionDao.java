package Controller.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDao extends AbstractSessionDAO {

	private final transient Logger log = LoggerFactory.getLogger(ShiroRedisClusterManager.class);

	CacheManager<String, Object> cacheManager;

	public void update(Session session) throws UnknownSessionException {
		try {
			System.out.println("id" + session.getId().toString());
			cacheManager.update(new DefaultCache(session.getId().toString(), session));
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
	}

	public void delete(Session session) {
		try {
			System.out.println("id" + session.getId().toString());
			cacheManager.delete(session.getId().toString());
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
	}

	@SuppressWarnings("unchecked")
	public Collection<Session> getActiveSessions() {
		try {
			return (Collection<Session>) cacheManager.getcollection();
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
		return new ArrayList<Session>();
	}

	@Override
	protected Serializable doCreate(Session session) {
		try {
			System.out.println("id" + session.getId().toString());
			cacheManager.create(new DefaultCache(session.getId().toString(), session));
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		try {
			System.out.println("id" + sessionId.toString());
			cacheManager.get(sessionId.toString());
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
		return null;
	}

}
