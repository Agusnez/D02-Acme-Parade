
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.InceptionRecord;

@Component
@Transactional
public class InceptionRecordToStringConverter implements Converter<InceptionRecord, String> {

	@Override
	public String convert(final InceptionRecord inceptionRecord) {
		String result;

		if (inceptionRecord == null)
			result = null;
		else
			result = String.valueOf(inceptionRecord.getId());

		return result;
	}
}
