
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Parade;

@Repository
public interface ParadeRepository extends JpaRepository<Parade, Integer> {

	@Query("select p from Parade p join p.floats f where f.id = ?1")
	Collection<Parade> findParadesByFloatId(int floatId);

	@Query("select count(p) from Parade p where p.ticker = ?1")
	int countParadeWithTicker(String ticker);

	@Query("select p from Parade p where p.brotherhood.id = ?1")
	Collection<Parade> findParadeByBrotherhoodId(int brotherhoodId);

	@Query("select p from Parade p where p.finalMode = true")
	Collection<Parade> findParadeCanBeSeen();

	@Query("select p from Parade p where p.finalMode = false and p.brotherhood.id = ?1")
	Collection<Parade> findParadeCannotBeSeenOfBrotherhoodId(int brotherhoodId);

	@Query("select p from Parade p where p.finalMode = true and p.brotherhood.id = ?1 order by p.status desc")
	Collection<Parade> findParadeCanBeSeenOfBrotherhoodId(int brotherhoodId);

	@Query("select p from Parade p join p.brotherhood.members m where m.id = ?1")
	Collection<Parade> findMemberParades(int memberId);
}
