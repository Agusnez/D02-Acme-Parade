
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SegmentRepository;
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

	// Simple CRUD methods

	public Segment create(final int brotherhoodId) {

		final Segment result = new Segment();

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

}
