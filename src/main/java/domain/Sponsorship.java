
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	private String		banner;
	private String		targetUrl;
	private CreditCard	creditCard;
	private Boolean		activated;
	private Sponsor		sponsor;


	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@URL
	@NotBlank
	public String getTargetUrl() {
		return this.targetUrl;
	}

	public void setTargetUrl(final String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Boolean getActivated() {
		return this.activated;
	}

	public void setActivated(final Boolean activated) {
		this.activated = activated;
	}

	@OneToOne(optional = false)
	@Valid
	public Sponsor getSponsor() {
		return this.sponsor;
	}

	public void setSponsor(final Sponsor sponsor) {
		this.sponsor = sponsor;
	}

}
