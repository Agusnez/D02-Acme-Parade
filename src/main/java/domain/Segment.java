
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Segment extends DomainEntity {

	private String	origin;
	private String	destination;
	private Date	timeOrigin;
	private Date	timeDestination;

	private Parade	parade;
	private Segment	contiguous;


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
	public Date getTimeOrigin() {
		return this.timeOrigin;
	}

	public void setTimeOrigin(final Date timeOrigin) {
		this.timeOrigin = timeOrigin;
	}

	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getTimeDestination() {
		return this.timeDestination;
	}

	public void setTimeDestination(final Date timeDestination) {
		this.timeDestination = timeDestination;
	}

	@Valid
	@ManyToOne(optional = false)
	public Parade getParade() {
		return this.parade;
	}

	public void setParade(final Parade parade) {
		this.parade = parade;
	}

	@Valid
	@OneToOne()
	public Segment getContiguous() {
		return this.contiguous;
	}

	public void setContiguous(final Segment contiguous) {
		this.contiguous = contiguous;
	}

}
