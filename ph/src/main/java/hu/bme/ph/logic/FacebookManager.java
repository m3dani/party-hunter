package hu.bme.ph.logic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.w3c.dom.events.EventTarget;

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
	public void init(){
		logger.info("init");
		String appId = dao.getPreference("app_id");
		String appSecret = dao.getPreference("app_secret");
		String permissions = dao.getPreference("permissions");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthAppId(dao.getPreference(appId))
		  .setOAuthAppSecret(appSecret)
		  .setOAuthAccessToken(appId + "|" + appSecret)
		  .setOAuthPermissions(permissions);
		 // .setOAuthPermissions("email,publish_stream");
		FacebookFactory ff = new FacebookFactory(cb.build());
		facebook = ff.getInstance();
		logger.info("Facebook initialized with permissions: " + permissions);
	}
	
	
	public ResponseList<Place> requestPlaceFromFacebook(String query, Long latitude, Long longitude, int distance, String fields){
		try {
			ResponseList<Place> response = facebook.searchPlaces(query, new GeoLocation(latitude, longitude), distance, new Reading().fields(fields));
			return response;
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO
	private ResponseList<Event> requestPlaceEventsFromFacebook(PHPlace place){
		try {
			ResponseList<Event> response = facebook.getEvents(place.getFacebookId(), new Reading().fields("name, place, description"));
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
		phplace.setLatitude(place.getLocation().getLatitude().longValue());
		phplace.setLongitude(place.getLocation().getLongitude().longValue());
		phplace.setStreet(place.getLocation().getStreet());
		return phplace;
	}
	
	//TODO
	private PHEvent parseFbEvent(Event event, PHPlace place) {
		PHEvent phevent = new PHEvent();
		phevent.setPlace(place);
		phevent.setDescription(event.getDescription());
		phevent.setEndTime(event.getEndTime().toString());
		phevent.setName(event.getName());
		phevent.setUpdated(event.getUpdatedTime().toString());
		phevent.setStartTime(event.getStartTime().toString());
		return phevent;
	}
	
	public void mergePlacesToDb(List<PHPlace> placeList){
		Map<String, PHPlace> placeMap = dao.getAllPlacesMap();
		placeList.stream()
			.forEach(p -> {
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
	
}
