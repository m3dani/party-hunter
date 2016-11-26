package hu.bme.ph.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"*.jsp"})
public class LoginFilter implements Filter {

	private static final Logger logger = Logger.getLogger(LoginFilter.class.getName());

	@Inject
	private GlobalBean globals;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	public String getLoginURL(GlobalBean globals, HttpServletRequest reqt) throws UnsupportedEncodingException {
		String page = "";
		String baseUrl = globals.getGlobalBaseUrl();

		if (baseUrl != null) {
			page = baseUrl + reqt.getContextPath();
		} else {
			page = reqt.getContextPath();
		}
		page += "/login.xhtml";
		for (String p : reqt.getRequestURI().split("/")) {
			if (p.contains(".")) {
				page += "?r=" + p;
				if (reqt.getParameterMap().keySet() != null && reqt.getParameterMap().keySet().isEmpty() == false) {
					page += URLEncoder.encode("?", java.nio.charset.StandardCharsets.UTF_8.toString());
					List<String> keys = new ArrayList<>();
					keys.addAll(reqt.getParameterMap().keySet());
					for (String key : keys) {
						if (key != keys.get(0)) {
							page += URLEncoder.encode("&", java.nio.charset.StandardCharsets.UTF_8.toString());
						}
						page += key;
						page += URLEncoder.encode("=", java.nio.charset.StandardCharsets.UTF_8.toString());
						page += reqt.getParameter(key);
					}
				}
				break;
			}
		}
		return page;
	}

	public GlobalBean getGlobals() {
		return globals;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);


//			String reqURI = reqt.getRequestURI();
//			// logger.fine(" request uri: " + reqURI);
//			if (reqURI.indexOf("/login.xhtml") > 0 && ses != null && ses.getAttribute("loginBean") != null
//					&& ((LoginBean) ses.getAttribute("loginBean")).isLoggedIn()) {
//				// if opening the login when already logged in, redirect to the
//				// index
//				String homeUrl = globals.getHomeUrl();
//				if (homeUrl != null) {
//					resp.sendRedirect(reqt.getContextPath() + "/" + homeUrl);
//				} else {
//					resp.sendRedirect(reqt.getContextPath() + "/index.xhtml");
//				}
//			} else if (reqURI.contains("/login.xhtml") || (ses != null && ses.getAttribute("username") != null)
//					|| reqURI.contains("/javax.faces.resource/")) {
//				chain.doFilter(request, response);
//			} else {
//				resp.sendRedirect(getLoginURL(globals, reqt));
//			}
		} catch (Throwable e) {
			logger.log(Level.WARNING, "ERROR in webfilter", e);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
