package hu.bme.ph.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;

import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.GeoLocation;
import facebook4j.Place;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import hu.bme.ph.dao.PHDao;
import hu.bme.ph.model.PHEvent;
import hu.bme.ph.model.PHPlace;

@Local
@Singleton
public class FacebookManager {

	private static final Logger logger = Logger.getLogger(FacebookManager.class.getName());

	@Inject
	PHDao dao;

	private Facebook facebook;

	@PostConstruct
	public void init() {
		logger.info("init");
		String appId = dao.getPreference("app_id");
		String appSecret = dao.getPreference("app_secret");
		String permissions = dao.getPreference("permissions");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthAppId(appId).setOAuthAppSecret(appSecret).setOAuthAccessToken(appId + "|"
				+ appSecret).setOAuthPermissions(permissions);
		// .setOAuthPermissions("email,publish_stream");
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
		logger.info("Facebook initialized with permissions: " + permissions);
	}

	public ResponseList<Place> requestPlaceFromFacebook(String query, Double latitude, Double longitude, int distance,
			String fields) {
		try {
			// default: akvarium klub official
			if (latitude == null) {
				latitude = 47.49836031485;
			}
			if (longitude == null) {
				longitude = 19.054286954242;
			}
			// saveEventsToDb();
			ResponseList<Place> response = facebook.searchPlaces(query, new GeoLocation(latitude, longitude), distance, new Reading().fields(fields).limit(100));
			return response;
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PHPlace parseFbPlace(Place place) {
		PHPlace phplace = new PHPlace();
		phplace.setFacebookId(place.getId());
		phplace.setName(place.getName());
		phplace.setCity(place.getLocation().getCity());
		phplace.setCountry(place.getLocation().getCountry());
		phplace.setLatitude(place.getLocation().getLatitude());
		phplace.setLongitude(place.getLocation().getLongitude());
		phplace.setStreet(place.getLocation().getStreet());
		return phplace;
	}

	public void mergePlacesToDb(List<PHPlace> placeList) {
		Map<String, PHPlace> placeMap = dao.getAllPlacesMap();
		placeList.stream().forEach(p -> {
			if (placeMap.containsKey(p.getFacebookId())) {
				PHPlace exisitingPlace = placeMap.get(p.getFacebookId());
				exisitingPlace.setName(p.getName());
				exisitingPlace.setCity(p.getCity());
				exisitingPlace.setCountry(p.getCountry());
				exisitingPlace.setLatitude(p.getLatitude());
				exisitingPlace.setLongitude(p.getLongitude());
				exisitingPlace.setStreet(p.getStreet());
				dao.save(exisitingPlace);
			} else {
				dao.save(p);
			}
		});
	}

	private void saveEventsToDb() {
		Map<String, PHPlace> placeMap = dao.getAllPlacesMap();
		List<PHEvent> events = new ArrayList<PHEvent>();
		for (String placeId : placeMap.keySet()) {
			for (Event event : requestPlaceEventsFromFacebook(placeMap.get(placeId))) {
				PHEvent phe = parseFbEvent(event, placeMap.get(placeId));
				events.add(phe);
			}
		}
		mergeEventsToDb(events);
	}

	// TODO
	public ResponseList<Event> requestPlaceEventsFromFacebook(PHPlace place) {
		try {
			ResponseList<Event> response = facebook.getEvents(place.getFacebookId(), new Reading().fields("name, id, place, description, end_time, updated_time, start_time, attending_count").limit(100));
			return response;
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// TODO
	public PHEvent parseFbEvent(Event event, PHPlace place) {
		PHEvent phevent = new PHEvent();
		phevent.setPlace(place);
		phevent.setDescription(event.getDescription());
		if (event.getEndTime() != null) {
			phevent.setEndTime(event.getEndTime());
		}
		phevent.setName(event.getName());
		phevent.setUpdated(event.getUpdatedTime());
		phevent.setStartTime(event.getStartTime());
		phevent.setFacebookId(event.getId());

		phevent.setAttendingCount(0);
		phevent.setIsHidden(false);
		return phevent;
	}

	public void mergeEventsToDb(List<PHEvent> eventList) {
		Map<String, PHEvent> eventMap = dao.getAllEventsMap();
		eventList.stream().forEach(e -> {
			if (eventMap.containsKey(e.getFacebookId())) {
				PHEvent exisitingEvent = eventMap.get(e.getFacebookId());
				exisitingEvent.setPlace(e.getPlace());
				exisitingEvent.setDescription(e.getDescription());
				if (e.getEndTime() != null) {
					exisitingEvent.setEndTime(e.getEndTime());
				}
				exisitingEvent.setName(e.getName());
				exisitingEvent.setUpdated(e.getUpdated());
				exisitingEvent.setStartTime(e.getStartTime());
				exisitingEvent.setFacebookId(e.getFacebookId());

				exisitingEvent.setAttendingCount(e.getAttendingCount());
				exisitingEvent.setIsHidden(e.getIsHidden());
				dao.save(exisitingEvent);
			} else {
				dao.save(e);
			}
		});
	}
}
