package hu.bme.ph.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import facebook4j.User;
import hu.bme.ph.dao.PHDao;
import hu.bme.ph.model.AdminUser;

@SessionScoped
@Named("loginBean")
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 5274138312286715971L;

	private static final Logger logger = Logger.getLogger(LoginBean.class.getName());


	private String name;
	private String profilePicture;
	private List<String> friendIdList;
	private List<String> attendingEventList;


	@Inject
	private PHDao dao;

	@PostConstruct
	public void init() {
		logger.fine("init");
	}

	public String loginWithFacebook() {
		logger.info("successRemote");
		FacesContext context = FacesContext.getCurrentInstance();
	    Map<String,String> params = context.getExternalContext().getRequestParameterMap();
	    this.name = params.get("name");
	    
	    try {
			context.getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
