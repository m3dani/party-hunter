package hu.bme.ph.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.RowEditEvent;

import hu.bme.ph.dao.PHDao;
import hu.bme.ph.logic.FacebookManager;
import hu.bme.ph.model.PHEvent;
import hu.bme.ph.model.PHPlace;

@Named(value = "placesBean")
@SessionScoped
public class PlacesBean implements Serializable{

	private static final long serialVersionUID = 6957228920901587112L;
	
	private static final Logger logger = Logger.getLogger(PlacesBean.class.getName());
		
	@Inject
	PHDao dao;
	
	@Inject
	FacebookManager facebookManager;
	
	private List<PHPlace> placeList;
	
	@PostConstruct
	public void init(){
		refreshPlaces();
	}
	
	public void refreshPlaces() {
		placeList = dao.getAll(PHPlace.class);
	}
	
	public void syncPlaceEvents(PHPlace place) {
		List<PHEvent> eventList = new ArrayList<>();
		facebookManager.requestPlaceEventsFromFacebook(place).forEach(e -> {
			eventList.add(facebookManager.parseFbEvent(e, place));
		});
		facebookManager.mergeEventsToDb(eventList);
	}

	public List<PHPlace> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<PHPlace> placeList) {
		this.placeList = placeList;
	}
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((PHPlace) event.getObject()).getFacebookId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void onRowEdit(RowEditEvent event) {
		dao.save((PHPlace) event.getObject());
        FacesMessage msg = new FacesMessage("Place Edited", ((PHPlace) event.getObject()).getFacebookId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
