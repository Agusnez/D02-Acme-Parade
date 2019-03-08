
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class PeriodRecord extends DomainEntity {

	private String				title;
	private String				description;
	private Date				startYear;
	private Date				endYear;
	private Collection<String>	photos;


	@NotBlank
	@SafeHtml
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartYear() {
		return this.startYear;
	}

	public void setStartYear(final Date startYear) {
		this.startYear = startYear;
	}

	@NotNull
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getEndYear() {
		return this.endYear;
	}

	public void setEndYear(final Date endYear) {
		this.endYear = endYear;
	}

	@ElementCollection
	public Collection<String> getPhotos() {
		return this.photos;
	}

	public void setPhotos(final Collection<String> photos) {
		this.photos = photos;
	}

}
