package hu.bme.ph.model;

import java.io.Serializable;

public interface PHEntity extends Serializable {
	Long getPkid();

	void setPkid(Long pkid);
}

