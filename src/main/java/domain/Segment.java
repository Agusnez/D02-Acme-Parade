
package domain;

import java.sql.Time;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	private String	origin;
	private String	destination;
	private Time	timeOrigin;
	private Time	timeDestination;
	private Boolean	finalMode;
	private String	status;


	@NotBlank
	@SafeHtml
	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(final String origin) {
		this.origin = origin;
	}

	@NotBlank
	@SafeHtml
	public String getDestination() {
		return this.destination;
	}

	public void setDestination(final String destination) {
		this.destination = destination;
	}

	public Time getTimeOrigin() {
		return this.timeOrigin;
	}

	public void setTimeOrigin(final Time timeOrigin) {
		this.timeOrigin = timeOrigin;
	}

	public Time getTimeDestination() {
		return this.timeDestination;
	}

	public void setTimeDestination(final Time timeDestination) {
		this.timeDestination = timeDestination;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	@NotBlank
	@Pattern(regexp = "\\APENDING\\z|\\AREJECTED\\z|\\AAPPROVED\\z")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

}
