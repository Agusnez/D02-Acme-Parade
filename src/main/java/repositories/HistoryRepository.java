
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {

	@Query("select avg(h.legalRecords.size + h.periodRecords.size + h.linkRecords.size + h.miscellaneousRecords.size) + 1, min(h.legalRecords.size + h.periodRecords.size + h.linkRecords.size + h.miscellaneousRecords.size) + 1, max(h.legalRecords.size + h.periodRecords.size + h.linkRecords.size + h.miscellaneousRecords.size) + 1 from History h")
	Collection<Double> statsOfRecordPerHistory();

}
