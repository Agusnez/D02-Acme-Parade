
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.InceptionRecordRepository;
import security.Authority;
import domain.Brotherhood;
import domain.InceptionRecord;

@Service
@Transactional
public class InceptionRecordService {

	//Managed Repository

	@Autowired
	private InceptionRecordRepository	inceptionRecordRepository;

	//Supporting services

	@Autowired
	private BrotherhoodService			brotherhoodService;


	//Simple CRUD methods

	public InceptionRecord create() {

		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.BROTHERHOOD);
		Assert.isTrue(brotherhood.getUserAccount().getAuthorities().contains(authority));

		InceptionRecord result;

		result = new InceptionRecord();

		return result;
	}

	public Collection<InceptionRecord> findAll() {

		final Collection<InceptionRecord> inceptionRecords = this.inceptionRecordRepository.findAll();

		Assert.notNull(inceptionRecords);

		return inceptionRecords;
	}

	public InceptionRecord findOne(final int inceptionRecordId) {

		final InceptionRecord inceptionRecords = this.inceptionRecordRepository.findOne(inceptionRecordId);

		Assert.notNull(inceptionRecords);

		return inceptionRecords;
	}

	public InceptionRecord save(final InceptionRecord inceptionRecord) {
		final Brotherhood brotherhood = this.brotherhoodService.findByPrincipal();
		Assert.notNull(brotherhood);
		Assert.notNull(inceptionRecord);

		InceptionRecord result;

		result = this.inceptionRecordRepository.save(inceptionRecord);

		return result;
	}

}
