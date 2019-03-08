
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select p from Parade p join p.floats f where f.id = ?1")
	Collection<Parade> findProcessionsByFloatId(int floatId);

	@Query("select count(p) from Parade p where p.ticker = ?1")
	int countProcessionsWithTicker(String ticker);

	@Query("select p from Parade p where p.brotherhood.id = ?1")
	Collection<Parade> findProcessionByBrotherhoodId(int brotherhoodId);

	@Query("select p from Parade p where p.finalMode = true")
	Collection<Parade> findProcessionCanBeSeen();

	@Query("select p from Parade p where p.finalMode = false and p.brotherhood.id = ?1")
	Collection<Parade> findProcessionCannotBeSeenOfBrotherhoodId(int brotherhoodId);

	@Query("select p from Parade p where p.finalMode = true and p.brotherhood.id = ?1")
	Collection<Parade> findProcessionCanBeSeenOfBrotherhoodId(int brotherhoodId);

	@Query("select p from Parade p join p.brotherhood.members m where m.id = ?1")
	Collection<Parade> findMemberProcessions(int memberId);
}
