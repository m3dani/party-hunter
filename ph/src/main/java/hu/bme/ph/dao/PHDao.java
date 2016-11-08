package hu.bme.ph.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import hu.bme.ph.model.PHEvent;
import hu.bme.ph.model.PHPlace;
import hu.bme.ph.model.PHPreference;

@Stateless
@LocalBean
@Transactional
public class PHDao extends AbstractDao implements Serializable {

	private static final long serialVersionUID = -1829662256756327075L;

	private static final Logger logger = Logger.getLogger(PHDao.class.getName());

	public String getPreference(String key) {
		logger.info("getPreference called");
		TypedQuery<PHPreference> q = em.createNamedQuery("PHPreference.findByKey", PHPreference.class);
		try {
			String result = q.setParameter("key", key).getSingleResult().getValue();
			logger.info("preference found: " + key + "=" + result);
			return result;
		} catch (NoResultException e) {
			logger.info("preference with key " + key + " not found");
			return null;
		}
	}

	public Map<String, PHPlace> getAllPlacesMap() {
		logger.info("getAllPlacesMap called");
		Map<String, PHPlace> placeMap = new HashMap<>();
		TypedQuery<PHPlace> q = em.createNamedQuery("PHPlace.findAll", PHPlace.class);
		q.getResultList().stream().forEach(p -> {
			placeMap.put(p.getFacebookId(), p);
		});

		return placeMap;
	}

	public Map<String, PHEvent> getAllEventsMap() {
		logger.info("getAllEventsMap called");
		Map<String, PHEvent> eventMap = new HashMap<>();
		TypedQuery<PHEvent> q = em.createNamedQuery("PHEvent.findAll", PHEvent.class);
		q.getResultList().stream().forEach(e -> {
			eventMap.put(e.getFacebookId(), e);
		});

		return eventMap;
	}

}
