
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SegmentRepository;
import domain.Parade;
import domain.Segment;

@Service
@Transactional
public class SegmentService {

	// Managed repository

	@Autowired
	private SegmentRepository	segmentRepository;

	// Suporting services
	
	public Collection<Segment> findByParade(final int paradeId) {
		return this.segmentRepository.segmentsPerParade(paradeId);
	}

	@Autowired
	private ParadeService		paradeService;


	// Simple CRUD methods

	public Segment create(final int paradeId) {

		final Collection<Segment> segments = this.segmentsPerParade(paradeId);

		final Parade parade = this.paradeService.findOne(paradeId);

		final Segment result = new Segment();

		if (!segments.isEmpty()) {

			final Segment contiguous = this.lastSegment(paradeId);

			result.setOrigin(contiguous.getDestination());

			result.setTimeOrigin(contiguous.getTimeDestination());

			result.setContiguous(contiguous);
		}

		result.setParade(parade);

		return result;

	}
	public Collection<Segment> findAll() {

		final Collection<Segment> segments = this.segmentRepository.findAll();

		Assert.notNull(segments);

		return segments;
	}

	public Segment findOne(final int segmentId) {

		final Segment segment = this.segmentRepository.findOne(segmentId);

		return segment;

	}

	public Segment save(final Segment segment) {
		Segment result;

		result = this.segmentRepository.save(segment);

		return result;
	}

	public void delete(final Segment segment) {

		Assert.isTrue(segment.getId() != 0);

		this.segmentRepository.delete(segment);

	}

	// Other business methods 

	public Segment lastSegment(final int paradeId) {

		final Segment result = this.segmentRepository.lastSegment(paradeId);

		return result;
	}

	public Collection<Segment> segmentsPerParade(final int paradeId) {

		final Collection<Segment> result = this.segmentsPerParade(paradeId);

		return result;
	}

}
