
package forms;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DomainEntity;

@Entity
@Access(AccessType.PROPERTY)
public class FirstSegmentForm extends DomainEntity {

	private String	origin;
	private String	destination;
	private Date	timeOrigin;
	private Date	timeDestination;

	private int		paradeId;


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

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getTimeOrigin() {
		return this.timeOrigin;
	}

	public void setTimeOrigin(final Date timeOrigin) {
		this.timeOrigin = timeOrigin;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@NotNull
	public Date getTimeDestination() {
		return this.timeDestination;
	}

	public void setTimeDestination(final Date timeDestination) {
		this.timeDestination = timeDestination;
	}

	public int getParadeId() {
		return this.paradeId;
	}

	public void setParadeId(final int paradeId) {
		this.paradeId = paradeId;
	}

}
