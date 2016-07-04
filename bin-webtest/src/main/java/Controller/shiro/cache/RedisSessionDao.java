package Controller.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDao extends AbstractSessionDAO {

	private final transient Logger log = LoggerFactory.getLogger(ShiroRedisClusterManager.class);

	private int overTimeSceond = 1000;

	private CacheManager<String, Object> cacheManager;

	private static final Map<String, SessionVO> SESSION_CACHE = new WeakHashMap<String, SessionVO>();

	class SessionVO {
		private long lastRequest = System.currentTimeMillis();
		private Session session;

		public void updateRequest() {
			lastRequest = System.currentTimeMillis();
		}

		public long getLastRequest() {
			return lastRequest;
		}

		public SessionVO setLastRequest(long lastRequest) {
			this.lastRequest = lastRequest;
			return this;
		}

		public Session getSession() {
			return session;
		}

		public SessionVO setSession(Session session) {
			this.session = session;
			return this;
		}

		public SessionVO(Session session) {
			super();
			this.session = session;
		}
	}

	protected Session getSessionCache(String key) throws Throwable {
		SessionVO vo = SESSION_CACHE.get(key);
		Session session = null;
		if (null == vo) {
			System.err.println("get");
			session = cacheManager.get(key);
			if (null != session)
				SESSION_CACHE.put(key, new SessionVO(session));
			return session;
		}
		return vo.session;
	}

	protected void updateSessionCache(Session session) throws Throwable {
		String key = session.getId().toString();
		SessionVO vo = SESSION_CACHE.get(key);
		if (null != vo) {
			if ((System.currentTimeMillis() - vo.lastRequest) > overTimeSceond) {
				// cacheManager.expire(key, overTimeSceond);
				// else
				System.out.println(overTimeSceond + ":a:" + (System.currentTimeMillis() - vo.lastRequest));
				cacheManager.update(new DefaultCache(session.getId().toString(), session));
				System.out.println("up");
				vo.updateRequest();
			}
		}
	}

	public void update(Session session) throws UnknownSessionException {
		try {
			// System.out.println("update-id:" + (null == session));
			if (null == session)
				return;
			// SimpleSession old = cacheManager.get(session.getId().toString());
			// cacheManager.update(new DefaultCache(session.getId().toString(), session));
			updateSessionCache(session);
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
			// System.out.println("doReadSession-id:" + sessionId);
			if (null == sessionId)
				return null;
			return getSessionCache(sessionId.toString());
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

	public int getOverTimeSceond() {
		return overTimeSceond;
	}

	public RedisSessionDao setOverTimeSceond(int overTimeSceond) {
		this.overTimeSceond = overTimeSceond;
		return this;
	}

}
