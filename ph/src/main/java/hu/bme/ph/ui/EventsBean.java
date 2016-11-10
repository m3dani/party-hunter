package hu.bme.ph.ui;

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

import java.io.Serializable;

@Named(value = "eventsBean")
@SessionScoped
public class EventsBean implements Serializable {

	
	private static final long serialVersionUID = 2243556870290746790L;
	
	private static final Logger logger = Logger.getLogger(EventsBean.class.getName());
	
	@Inject
	PHDao dao;
	
	@Inject
	FacebookManager facebookManager;
	
	private List<PHEvent> eventList;
	private PHEvent selectedEvent;
	
	@PostConstruct
	public void init(){
		refreshEvents();
	}
	
	public void refreshEvents() {
		selectedEvent = new PHEvent();
		eventList = dao.getAll(PHEvent.class);
	}
	
	public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((PHEvent) event.getObject()).getFacebookId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void onRowEdit(RowEditEvent event) {
		dao.save((PHEvent) event.getObject());
        FacesMessage msg = new FacesMessage("Event Edited", ((PHEvent) event.getObject()).getFacebookId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	public List<PHEvent> getEventList() {
		return eventList;
	}

	public void setEventList(List<PHEvent> eventList) {
		this.eventList = eventList;
	}

	public PHEvent getSelectedEvent() {
		return selectedEvent;
	}

	public void setSelectedEvent(PHEvent selectedEvent) {
		this.selectedEvent = selectedEvent;
	}
}
