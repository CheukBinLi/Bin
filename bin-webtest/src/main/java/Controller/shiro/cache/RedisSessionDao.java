package Controller.shiro.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisSessionDao extends AbstractSessionDAO {

	private final transient Logger log = LoggerFactory.getLogger(ShiroRedisClusterManager.class);

	private long overTimeSceond = 10000;

	private CacheManager<String, Object> cacheManager;

	private static final SoftConcurrentHashMap<String, SessionVO> SESSION_CACHE = new SoftConcurrentHashMap<String, SessionVO>();

	class SessionVO {
		private volatile long lastRequest = System.currentTimeMillis() + overTimeSceond;
		private Session session;

		public void updateRequest() {
			lastRequest = System.currentTimeMillis() + overTimeSceond;
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
		Session session = null == vo ? null : vo.session;
		if (null == session) {
			session = cacheManager.get(key);
			System.out.println("Redis获取 isNull:" + (null == session));
			if (null != session)
				SESSION_CACHE.put(key, new SessionVO(session));
			return session;
		} else if ((System.currentTimeMillis() >= vo.lastRequest)) {
			session = cacheManager.get(key);
			if(null!=session)
				vo.updateRequest();
		}
		return session;
	}

	protected void updateSessionCache(Session session) throws Throwable {
		String key = session.getId().toString();
		SessionVO vo = SESSION_CACHE.get(key);
		boolean update = false;
		if (null != vo) {
			if ((System.currentTimeMillis() >= vo.lastRequest)) {
				System.out.println(overTimeSceond + ":更新:now" + (System.currentTimeMillis() + " last:" + vo.lastRequest));
				update = true;
			}
		}
		if (update) {
			cacheManager.update(new DefaultCache(session.getId().toString(), session));
			vo.updateRequest();
		}
	}

	public void update(Session session) throws UnknownSessionException {
		try {
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

	public long getOverTimeSceond() {
		return overTimeSceond;
	}

	public RedisSessionDao setOverTimeSceond(long overTimeSceond) {
		this.overTimeSceond = overTimeSceond;
		return this;
	}

}
