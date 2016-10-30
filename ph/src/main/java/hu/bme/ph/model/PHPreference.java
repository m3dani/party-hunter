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

import hu.bme.ph.dao.PHDao;

@Entity
@Table(name="preferences")
@NamedQueries({
	@NamedQuery(name="PHPreference.findAll", query="SELECT p FROM PHPreference p"),
	@NamedQuery(name="PHPreference.findByKey", query="SELECT p FROM PHPreference p WHERE p.key = :key")
})
public class PHPreference implements PHEntity {
	
	private static final long serialVersionUID = 6392740837704126991L;

	@Id
	@SequenceGenerator(name="PREFERENCE_PKID_GENERATOR", sequenceName="PREFERENCE_PKID_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PREFERENCE_PKID_GENERATOR")
	@Column(unique=true, nullable=false, name="pkid")
	private Long pkid;
	
	@Column(name="key")
	private String key;
	
	@Column(name="value")
	private String value;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
}
