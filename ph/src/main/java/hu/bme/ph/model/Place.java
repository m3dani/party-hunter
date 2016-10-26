package hu.bme.ph.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="place")
public class Place implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8349053515992398803L;
	
	@Id
	@SequenceGenerator(name="PLACE_PKID_GENERATOR", sequenceName="SEQ_PLACE_PKID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLACE_PKID_GENERATOR")
	@Column(unique=true, nullable=false, name="pkid")
	private Long pkid;
	
	@Column(name="facebook_id")
	private String facebookId;
	
	@Column(name="name")
	private String name;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facebookId == null) ? 0 : facebookId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pkid == null) ? 0 : pkid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (facebookId == null) {
			if (other.facebookId != null)
				return false;
		} else if (!facebookId.equals(other.facebookId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pkid == null) {
			if (other.pkid != null)
				return false;
		} else if (!pkid.equals(other.pkid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Place [pkid=" + pkid + ", facebookId=" + facebookId + ", name=" + name + "]";
	}
	
	

}
