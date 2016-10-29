package hu.bme.ph.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.conf.ConfigurationBuilder;
import hu.bme.ph.dao.PHDao;
import hu.bme.ph.logic.FacebookManager;
import hu.bme.ph.model.PHPlace;

@Named(value = "testbean")
@SessionScoped
public class TestBean implements Serializable {

	private static final long serialVersionUID = 2424609426348504426L;

	private static final Logger logger = Logger.getLogger(TestBean.class.getName());
	
	@Inject
	PHDao dao;
	
	@Inject
	FacebookManager fbm;
	
	private List<PHPlace> placeList;
	private List<Event> fbEventList;
	private List<String> eventNames;
	
	@PostConstruct
	public void init() {
		logger.fine("init");
		placeList = dao.getAll(PHPlace.class);
		
	}

	public List<PHPlace> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<PHPlace> placeList) {
		this.placeList = placeList;
	}
	
//	public void getPlaceEvents(){
//		setFbEventList(fbm.searchPlaceEvents(placeList.get(0).getFacebookId()));
//		fbEventList.forEach(e -> {
//			eventNames.add(e.getName());
//		});
//	}

	public List<Event> getFbEventList() {
		return fbEventList;
	}

	public void setFbEventList(List<Event> fbEventList) {
		this.fbEventList = fbEventList;
	}

	public List<String> getEventNames() {
		return eventNames;
	}

	public void setEventNames(List<String> eventNames) {
		this.eventNames = eventNames;
	}
	
	

}
