package hu.bme.ph.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="category")
public class PHCategory implements PHEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2982079601623545154L;

	@Id
	@SequenceGenerator(name="CATEGORY_PKID_GENERATOR", sequenceName="SEQ_CATEGORY_PKID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CATEGORY_PKID_GENERATOR")
	@Column(unique=true, nullable=false, name="pkid")
	private Long pkid;
	
	@Column(name="name")
	private String name;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
