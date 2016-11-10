package hu.bme.ph.ui;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import hu.bme.ph.dao.PHDao;
import hu.bme.ph.model.AdminUser;

@SessionScoped
@Named("loginBean")
public class LoginBean implements Serializable {

//	private static final long serialVersionUID = 5274138312286715971L;
//
//	private static final Logger logger = Logger.getLogger(LoginBean.class.getName());
//
//	private static final String CLIENTID_OLDPW = "mainForm:oldPw";
//
//	private static final int BCRYPT_HASH_ROUNDS = 15;
//
//	private String userName;
//	private String password;
//	private String newPassword;
//
//	private boolean loggedIn;
//	private AdminUser loggedInUser;
//	private String redirectTo;
//
//	@Inject
//	private PHDao dao;
//
//	@PostConstruct
//	public void init() {
//		logger.fine("init");
//	}
//
//	@PreDestroy
//	public void destroy() {
//		logger.fine("destroy: " + userName);
//	}
//
//	public String login() {
//		logger.info("logging in " + userName + "...");
//		loggedIn = false;
//		loggedInUser = null;
//
//		AdminUser user = dao.getUserByUserName(userName);
//		if (user == null) {
//			logger.info("Unsuccessful login attempt with non-existing user: " + userName);
//			password = null;
//			JsfHelper.addGlobalWarnMessage("Incorrect username and password", "Please enter correct username and password");
//			return "login";
//		}
//		if (user.getPassword() == null) {
//			logger.warning("Unsuccessful login attempt, the user's password is empty: " + userName);
//			password = null;
//			JsfHelper.addGlobalWarnMessage("Incorrect username and password", "Please enter correct username and password");
//			return "login";
//		}
//		if (BCrypt.checkpw(password, user.getPassword())) {
//			logger.info("login successful: " + userName);
//			loggedInUser = user;
//			loggedIn = true;
//			password = null;
//			HttpSession session = (HttpSession) (FacesContext.getCurrentInstance()).getExternalContext().getSession(false);
//			session.setAttribute("username", userName);
//			user.setLastSuccessfulLogin(new Date());
//			dao.save(user);
//			String homeUrl = globals.getHomeUrl();
//			try {
//				ExternalContext externalContext = (FacesContext.getCurrentInstance()).getExternalContext();
//				if (redirectTo != null && redirectTo.trim().length() > 0) {
//					externalContext.redirect(redirectTo);
//					return redirectTo.split("\\.")[0];
//				} else if (globals.getHomeUrl() != null) {
//					externalContext.redirect(homeUrl);
//					return globals.getHomeUrl();
//				}
//			} catch (IOException e) {
//				logger.log(Level.WARNING, "ERROR when redirecting after successful login, redirectTo="
//						+ redirectTo + " homeUrl=" + homeUrl, e);
//			}
//			return "index";
//		} else {
//			logger.info("Unsuccessful login attempt, invalid password: " + userName);
//			password = null;
//			JsfHelper.addGlobalWarnMessage("Incorrect username and password", "Please enter correct username and password");
//			user.setLastUnsuccessfulLogin(new Date());
//			dao.save(user);
//			return "login";
//		}
//	}
//
//	public String changePassword() {
//		logger.info("changePassword for " + loggedInUser.getUserName());
//
//		if (BCrypt.checkpw(password, loggedInUser.getPassword())) {
//			// old password is OK
//			String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt(BCRYPT_HASH_ROUNDS));
//			loggedInUser.setPassword(newHash);
//			loggedInUser = dao.save(loggedInUser);
//			JsfHelper.addGlobalInfoMessage("Password changed successfully", "You can continue using NETVision");
//			logger.fine("Password successfully changed for " + loggedInUser.getUserName());
//			return "index?faces-redirect=true";
//		} else {
//			// old password error
//			JsfHelper.addGlobalWarnMessage("The old password is incorrect", "Please correct the old password and try again");
//			password = null;
//			JsfHelper.updateComponent(CLIENTID_OLDPW);
//			return "changePassword";
//		}
//	}
//
//	public String logout() {
//		try {
//			releaseAllScriptLocks();
//		} catch (Exception e) {
//			logger.log(Level.WARNING, "ERROR when releasing locks for player " + userName, e);
//		}
//		String tempUsr = userName;
//		userName = null;
//		password = null;
//		loggedInUser = null;
//		loggedIn = false;
//
//		HttpSession session = (HttpSession) (FacesContext.getCurrentInstance()).getExternalContext().getSession(false);
//		session.invalidate();
//		logger.info("Successfully logged out " + tempUsr);
//		return "login";
//	}
//
//	public BaseStatus lockScriptForEdit(Script script) {
//		logger.info("lockScriptForEdit user = " + loggedInUser.getUserName());
//		BaseStatus status = sm.lockScriptForEdit(script, loggedInUser);
//		if (status.isSuccess()) {
//			lockedScripts.put(script.getPkid(), script);
//		}
//		return status;
//	}
//
//	public BaseStatus releaseLockScriptForEdit(Script script) {
//		lockedScripts.remove(script.getPkid());
//		return sm.releaseLockScriptForEdit(script);
//	}
//
//	private void releaseAllScriptLocks() {
//		logger.fine("releasing all script locks for user " + loggedInUser);
//		List<Map.Entry<Long, Script>> sidList = new ArrayList<>(lockedScripts.entrySet());
//		sidList.forEach(entry -> {
//			sm.releaseLockScriptForEdit(entry.getValue());
//			lockedScripts.remove(entry.getKey());
//		});
//	}
//
//	// {{ getters - setters
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public boolean isLoggedIn() {
//		return loggedIn;
//	}
//
//	public void setLoggedIn(boolean loggedIn) {
//		this.loggedIn = loggedIn;
//	}
//
//	public AdminUser getLoggedInUser() {
//		return loggedInUser;
//	}
//
//	public void setLoggedInUser(AdminUser loggedInUser) {
//		this.loggedInUser = loggedInUser;
//	}
//
//	public String getNewPassword() {
//		return newPassword;
//	}
//
//	public void setNewPassword(String newPassword) {
//		this.newPassword = newPassword;
//	}
//
//	public String getRedirectTo() {
//		return redirectTo;
//	}
//
//	public void setRedirectTo(String redirectTo) {
//		this.redirectTo = redirectTo;
//	}
//
//	public String getProjectCompanyName() {
//		return ProjectProperty_.PROJECT_COMPANYNAME;
//		
//	}
//	
//	public String getProjectName() {
//		return ProjectProperty_.PROJECT_NAME;
//	}
	// }}
}
