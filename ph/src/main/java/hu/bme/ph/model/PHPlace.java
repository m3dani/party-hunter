package hu.bme.ph.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "place")
@NamedQueries({ @NamedQuery(name = "PHPlace.findAll", query = "SELECT p FROM PHPlace p ORDER BY p.name"), })
public class PHPlace implements PHEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8349053515992398803L;

	@Id
	@SequenceGenerator(name = "PLACE_PKID_GENERATOR", sequenceName = "SEQ_PKID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLACE_PKID_GENERATOR")
	@Column(unique = true, nullable = false, name = "pkid")
	private Long pkid;

	@Column(name = "facebook_id")
	private String facebookId;

	@Column(name = "name")
	private String name;

	@Column(name = "city")
	private String city;

	@Column(name = "street")
	private String street;

	@Column(name = "country")
	private String country;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

}
