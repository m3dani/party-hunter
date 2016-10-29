package hu.bme.ph.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJBTransactionRolledbackException;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import hu.bme.ph.model.PHEntity;

public abstract class AbstractDao implements Serializable {

	private static final long serialVersionUID = 8103109840132086894L;

	private static final Logger logger = Logger.getLogger(AbstractDao.class.getName());

	protected static final String JPA_QUERYHINT_FETCHGRAPH = "javax.persistence.fetchgraph";

	@PersistenceContext(unitName = "PHEntities")
	protected EntityManager em;

	/**
	 * Clears the full L2 cache
	 */
	public void evictFullCache() {
		Cache cache = em.getEntityManagerFactory().getCache();
		logger.fine("Evicting full cache now...");
		cache.evictAll();
	}

	/**
	 * Removes all of the instances of klass from the L2 cache
	 * @param klass
	 * @param <T>
	 */
	public <T extends PHEntity> void evictCache(Class<T> klass) {
		Cache cache = em.getEntityManagerFactory().getCache();
		logger.fine("Evicting all '" + klass.getName() + "' fromm cache now...");
		cache.evict(klass);
	}

	public <E extends PHEntity> E save(E obj) {
		// TODO exception handling
		return em.merge(obj);
	}

	public <E extends PHEntity> void delete(E e) {
		// TODO exception handling
		try {
			em.remove(em.contains(e) ? e : em.merge(e));
		} catch (EJBTransactionRolledbackException e2) {
			e2.printStackTrace();
		}
	}

	public <E extends PHEntity> E findById(Long id, Class<E> klass) {
		return em.find(klass, id);
	}

	public <E extends PHEntity> List<E> findAllById(List<Long> idList, Class<E> klass) {
		TypedQuery<E> query = em.createQuery("SELECT e FROM " + klass.getName() + " e WHERE e.pkid in :idList", klass);
		query.setParameter("idList", idList);
		return query.getResultList();
	}

	public <E extends PHEntity> List<E> getAll(Class<E> klass) {
		TypedQuery<E> query = em.createQuery("SELECT e FROM " + klass.getName() + " e", klass);
		return query.getResultList();
	}
}
