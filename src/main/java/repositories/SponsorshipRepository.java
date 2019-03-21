
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends JpaRepository<Sponsorship, Integer> {

	@Query("select s from Sponsorship s where s.sponsor.id = ?1")
	Collection<Sponsorship> findAllBySponsorId(int id);

	@Query("select s from Sponsorship s where s.activated = true and s.parade.id = ?1")
	Collection<Sponsorship> findAllByParadeId(int paradeId);
	
	@Query("select s from Sponsorship s where (s.creditCard.expYear < ?2) or (s.creditCard.expYear = ?2 or s.creditCard.expMonth < ?1)")
	Collection<Sponsorship> findCreditCardExpired(int actualMonth, int actualYear);
}
