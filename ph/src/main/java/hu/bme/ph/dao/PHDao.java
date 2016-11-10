package hu.bme.ph.dao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NamedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
	
	public List<PHEvent> getActualEventList() {
		logger.info("getActualEventList called");
		Date current_day = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(current_day);
		cal.add(Calendar.DATE, 1);
		Date next_day = cal.getTime();
		Query query = em.createQuery("SELECT event FROM PHEvent event WHERE 1=1 AND HOUR(event.startTime) >= 19 AND HOUR(event.endTime) <= 7 AND event.startTime BETWEEN ?1 AND ?2 ORDER BY event.startTime")
				.setParameter(1, current_day, TemporalType.DATE)
				.setParameter(2, next_day, TemporalType.DATE);
//		TypedQuery<PHEvent> q = em.createNamedQuery("PHEvent.findActualEvents", PHEvent.class);
		return query.getResultList();
	}
}
