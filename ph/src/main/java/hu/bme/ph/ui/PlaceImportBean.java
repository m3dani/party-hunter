package hu.bme.ph.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.bme.ph.dao.PHDao;
import hu.bme.ph.logic.FacebookManager;
import hu.bme.ph.model.PHPlace;

@Named(value = "placeImportBean")
@SessionScoped
public class PlaceImportBean implements Serializable{

	private static final long serialVersionUID = 8294693911880181790L;
	
	private static final Logger logger = Logger.getLogger(PlaceImportBean.class.getName());
	
	private static final String FIELDS = "name, location";
	
	@Inject
	PHDao dao;
	
	@Inject
	FacebookManager facebookManager;
	
	private Double longitude;
	private Double latitude;
	private int distance;
	private String query;
	

	private String fields;
	
	private List<PHPlace> searchResultList;
	private List<PHPlace> selectedPlaceList;
	
	@PostConstruct
	public void init() {
		searchResultList = new ArrayList<>();
		selectedPlaceList = new ArrayList<>();
	}
	
	public void searchPlaces() {
		searchResultList.clear();
		facebookManager.requestPlaceFromFacebook(query, latitude, longitude, distance, FIELDS)
			.forEach(p -> {
				searchResultList.add(facebookManager.parseFbPlace(p));
			});
	}
	
	public void saveSelectedPlaces() {
		facebookManager.mergePlacesToDb(selectedPlaceList);
	}
	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public List<PHPlace> getSearchResultList() {
		return searchResultList;
	}

	public void setSearchResultList(List<PHPlace> searchResultList) {
		this.searchResultList = searchResultList;
	}

	public List<PHPlace> getSelectedPlaceList() {
		return selectedPlaceList;
	}

	public void setSelectedPlaceList(List<PHPlace> selectedPlaceList) {
		this.selectedPlaceList = selectedPlaceList;
	}


}
