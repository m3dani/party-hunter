package hu.bme.ph.logic;

import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.GeoLocation;
import facebook4j.Location;
import facebook4j.Place;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONObject;

public class Test {

	Facebook facebook;
	
	public void SearchPlaceEvents(String placeId){
		
		GeoLocation center = new GeoLocation(47.497173, 19.054189);
		int distance = 1000;
		try {
			ResponseList<Place> results = facebook.searchPlaces("Akvarium", center, distance, new Reading().fields("location, name, events"));
//			ResponseList<JSONObject> results = facebook.search("coffee");
			for (Place place : results) {
//				System.out.println(place.getId() + " - " + place.getName());
//				System.out.println(place.getId() + " - " + place.getName() + " - " + place);
				System.out.println(place);
				for (Event event : facebook.getEvents(place.getId(), new Reading().fields("name, place, description"))) {
					System.out.println(event);
				}
			}
			System.out.println(results.size());
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthAppId("1863799350573056")
		  .setOAuthAppSecret("fb67675b661485b1929fe12f360ec673")
		  //.setOAuthAccessToken("EAAafHZAo5OAABAPZBAjN3JKvvXmg5uJ6OMjpexWTcEZCb3v6Dm9sSdkF6zruZA8B5pVleCU3W2KcjFDJfKBfZCN5eE9DoDumcYtujklj11HZB4wVF9wzvCIbRXdlyBgFUGNedG6Y47ies0Yfhldi5eX0I9gMz8Rs2qr3TRZBNdKJAZDZD")
		  .setOAuthAccessToken("1863799350573056|fb67675b661485b1929fe12f360ec673")
		  .setOAuthPermissions("email,publish_stream");
		FacebookFactory ff = new FacebookFactory(cb.build());
		t.facebook = ff.getInstance();
		
		t.SearchPlaceEvents("333460040008128");
		
		
	}

}
