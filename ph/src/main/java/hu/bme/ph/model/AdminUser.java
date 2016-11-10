package hu.bme.ph.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="admin_user")
public class AdminUser implements PHEntity {
	
	
	private static final long serialVersionUID = -5888318171944609786L;

	@Id
	@SequenceGenerator(name = "USER_PKID_GENERATOR", sequenceName = "SEQ_PKID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_PKID_GENERATOR")
	@Column(unique = true, nullable = false, name = "pkid")
	private Long pkid;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
