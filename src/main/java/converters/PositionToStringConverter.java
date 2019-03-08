
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Position;

@Component
@Transactional
public class PositionToStringConverter implements Converter<Position, String> {

	@Override
	public String convert(final Position position) {
		String result;

		if (position == null)
			result = null;
		else
			result = String.valueOf(position.getId());

		return result;
	}

}
