package hu.bme.ph.ui;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import hu.bme.ph.dao.PHDao;
import hu.bme.ph.logic.FacebookManager;
import hu.bme.ph.model.PHEvent;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@SessionScoped
@Named(value="searchBean")
public class SearchBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2661380857284353769L;

	private static final Logger logger = Logger.getLogger(SearchBean.class.getName());
	
	private static final Double BMElat = 47.4734564;
	private static final Double BMElong = 19.0576992;
	
	private String distance;

	@Inject
	PHDao dao;
	
	@Inject
	FacebookManager facebookManager;
	
	private List<PHEvent> searchResultList;
	
	private Date startTime = Calendar.getInstance().getTime();
	
	public void doSearch() {
		searchResultList = dao.getAll(PHEvent.class).stream()
			.filter(e -> e.getStartTime().after(startTime))
			.filter(e -> distance(BMElat, BMElong, e.getPlace().getLatitude(), e.getPlace().getLongitude(), "m") < Double.parseDouble(distance))
			.collect(Collectors.toList());
	}
	
	private Double distance(Double lat1, Double lon1, Double lat2, Double lon2, String unit) {
	      Double theta = lon1 - lon2;
	      Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	      dist = Math.acos(dist);
	      dist = rad2deg(dist);
	      dist = dist * 60 * 1.1515;
	      if (unit == "m") {
	        dist = dist * 1.609344 * 1000;
	      } else if (unit == "N") {
	        dist = dist * 0.8684;
	        }
	      return dist;
	    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts decimal degrees to radians             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    private double deg2rad(double deg) {
	      return (deg * Math.PI / 180.0);
	    }

	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    /*::  This function converts radians to decimal degrees             :*/
	    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	    private double rad2deg(double rad) {
	      return (rad * 180.0 / Math.PI);
	    }
	
	    
	    public String getDistance() {
			return distance;
		}

		public void setDistance(String distance) {
			this.distance = distance;
		}

		public List<PHEvent> getSearchResultList() {
			return searchResultList;
		}

		public void setSearchResultList(List<PHEvent> searchResultList) {
			this.searchResultList = searchResultList;
		}
		
		public Date getStartTime() {
			return startTime;
		}
		
		public void setStartTime(Date ts) {
			startTime = ts;
		}
}
