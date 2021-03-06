package hu.bme.ph.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "event")
@NamedQueries({ 
	@NamedQuery(name = "PHEvent.findAll",
			    query = "SELECT e FROM PHEvent e ORDER BY e.name")
	}
)

public class PHEvent implements PHEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3527945853379638174L;

	@Id
	@SequenceGenerator(name = "EVENT_PKID_GENERATOR", sequenceName = "SEQ_PKID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_PKID_GENERATOR")
	@Column(unique = true, nullable = false, name = "pkid")
	private Long pkid;

	@Column(name = "facebook_id")
	private String facebookId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "image_normal")
	private String imageNormal;

	@Column(name = "image_small")
	private String imageSmall;

	@Column(name = "image_cover_url")
	private String imageCoverUrl;

	@Column(name = "updated")
	private Date updated;

	@Column(name = "created")
	private String created;

	@Column(name = "is_hidden")
	private Boolean isHidden;

	@Column(name = "attending_count")
	private int attendingCount;

	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "start_time")
	private Date startTime;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "event_category_map", joinColumns = @JoinColumn(name = "event_pkid"), inverseJoinColumns = @JoinColumn(name = "category_pkid"))
	private Set<PHCategory> category;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "place_pkid")
	private PHPlace place;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageNormal() {
		return imageNormal;
	}

	public void setImageNormal(String imageNormal) {
		this.imageNormal = imageNormal;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageCoverUrl() {
		return imageCoverUrl;
	}

	public void setImageCoverUrl(String imageCoverUrl) {
		this.imageCoverUrl = imageCoverUrl;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public int getAttendingCount() {
		return attendingCount;
	}

	public void setAttendingCount(int attendingCount) {
		this.attendingCount = attendingCount;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public PHPlace getPlace() {
		return place;
	}

	public void setPlace(PHPlace place) {
		this.place = place;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}
