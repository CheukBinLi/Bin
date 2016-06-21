package Controller.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDao extends AbstractSessionDAO {

	private final transient Logger log = LoggerFactory.getLogger(ShiroRedisClusterManager.class);

	private CacheManager<String, Object> cacheManager;

	public void update(Session session) throws UnknownSessionException {
		try {
			System.out.println("update-id:" + (null == session));
			if (null == session)
				return;
			cacheManager.update(new DefaultCache(session.getId().toString(), session));
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
	}

	public void delete(Session session) {
		try {
			System.out.println("delete-id:" + (null == session));
			if (null == session)
				return;
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
			// if (isNull(session))
			// ((SimpleSession) session).setId(UUID.randomUUID().toString());
			// else
			// session.getId();
			// // System.out.println("id:" + o);
			Serializable sessionId = generateSessionId(session);
			assignSessionId(session, sessionId);
			cacheManager.create(new DefaultCache(session.getId().toString(), session));
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		try {
			System.out.println("doReadSession-id:" + sessionId);
			if (null == sessionId)
				return null;
			return (Session) cacheManager.get(sessionId.toString());
		} catch (Throwable e) {
			log.error("redis is error:", e);
		}
		return null;
	}

	public CacheManager<String, Object> getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(CacheManager<String, Object> cacheManager) {
		this.cacheManager = cacheManager;
	}

	private boolean isNull(Session session) {
		if (null == session)
			session = new SimpleSession();
		// return true;
		return null == session.getId();
	}

}
