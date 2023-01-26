package com.test.drone.domain.drone;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ModelConverter implements AttributeConverter<Model, String> {

    @Override
    public String convertToDatabaseColumn(Model model) {
        if (model == null) {
            return null;
        }
        return model.getCode();
    }

    @Override
    public Model convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Model.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
