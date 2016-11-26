package hu.bme.ph.ui;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
@Named("globals")
public class GlobalBean implements Serializable {

	private static final long serialVersionUID = -5341262979863775453L;

	private static final Logger logger = Logger.getLogger(GlobalBean.class.getName());

	private static boolean devMode = false;
	private String homeUrl;
	private String globalBaseUrl;

	@PostConstruct
	public void init() {
		refreshData();
	}

	private void refreshData() {
		logger.fine("Reading params:");
		devMode = true;
		logger.fine("devMode " + devMode);

		homeUrl = "index.xhtml";
		logger.fine("homeUrl: " + homeUrl);

		globalBaseUrl = null;
		
		logger.fine("globalBaseUrl: " + globalBaseUrl);
	}

	public void refresh() {
		logger.fine("refresh");
		refreshData();
	}

	public boolean isDevMode() {
		return devMode;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public String getGlobalBaseUrl() {
		return globalBaseUrl;
	}
}

