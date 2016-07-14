package Controller.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Controller.shiro.cache.SoftConcurrentHashMap;
import Controller.shiro.redis.RedisManager;

public class RedisSessionDao extends AbstractSessionDAO {

	private static transient final Logger LOG = LoggerFactory.getLogger(RedisSessionDao.class);

	RedisManager<String, Session> redisManager;

	private int tempCacheTimeOutSecond = 10;// 10秒

	private static final SoftConcurrentHashMap<String, SessionVO> TEMP_SESSION_CACHE = new SoftConcurrentHashMap<String, SessionVO>();

	public void update(Session session) throws UnknownSessionException {
		try {
			if (null == session)
				return;
			updateSession(session);
		} catch (Throwable e) {
			LOG.error("redis is error:", e);
		}
	}

	public void delete(Session session) {
		try {
			if (null == session)
				return;
			redisManager.delete(session.getId().toString());
		} catch (Throwable e) {
			LOG.error("redis is error:", e);
		}
	}

	public Collection<Session> getActiveSessions() {
		try {
			return redisManager.getcollection();
		} catch (Throwable e) {
			LOG.error("redis is error:", e);
		}
		return new ArrayList<Session>();
	}

	@Override
	protected Serializable doCreate(Session session) {
		try {
			Serializable sessionId = generateSessionId(session);
			assignSessionId(session, sessionId);
			if (redisManager.create(session.getId().toString(), session))
				TEMP_SESSION_CACHE.put(session.getId().toString(), new SessionVO(session));
		} catch (Throwable e) {
			LOG.error("redis is error:", e);
		}
		return session.getId();
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		try {
			if (null == sessionId)
				return null;
			return getSession(sessionId.toString());
		} catch (Throwable e) {
			LOG.error("redis is error:", e);
		}
		return null;
	}

	class SessionVO {
		private volatile long lastAccessTime = System.currentTimeMillis();
		private Session session;

		public long getLastAccessTime() {
			return lastAccessTime;
		}

		public void updateSession(Session session) {
			this.session = session;
			updateAccessTime();
		}

		public long updateAccessTime() {
			return lastAccessTime = (System.currentTimeMillis() + (tempCacheTimeOutSecond * 1000));
		}

		public SessionVO(Session session) {
			super();
			this.session = session;
			updateAccessTime();
		}
	}

	protected Session getSession(String key) throws Throwable {
		SessionVO vo = TEMP_SESSION_CACHE.get(key);
		Session session = null == vo ? null : vo.session;
		if (null == session) {
			session = redisManager.get(key);
			if (null != session)
				TEMP_SESSION_CACHE.put(key, new SessionVO(session));
			return session;
		} else
			if ((System.currentTimeMillis() >= vo.lastAccessTime)) {
				session = redisManager.get(key);
				if (LOG.isDebugEnabled())
					LOG.debug("获取Session-id：" + (null == session ? null : session.getId()));
				if (null != session)
					vo.updateSession(session);
			}
		return session;
	}

	protected void updateSession(Session session) throws Throwable {
		String key = session.getId().toString();
		SessionVO vo = TEMP_SESSION_CACHE.get(key);
		if (redisManager.create(session.getId().toString(), session))
			vo.updateSession(session);
		if (LOG.isDebugEnabled())
			LOG.debug("Session更新：" + session.getId());
	}

	public RedisSessionDao setRedisManager(RedisManager<String, Session> redisManager) {
		this.redisManager = redisManager;
		return this;
	}

	public RedisSessionDao setTempCacheTimeOutSecond(int tempCacheTimeOutSecond) {
		this.tempCacheTimeOutSecond = tempCacheTimeOutSecond;
		return this;
	}

}
