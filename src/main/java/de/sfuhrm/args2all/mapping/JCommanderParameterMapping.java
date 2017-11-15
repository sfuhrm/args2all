package de.sfuhrm.args2all.mapping;

import com.beust.jcommander.Parameter;
import de.sfuhrm.args2all.model.ModelParameter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Mapping for {@linkplain Parameter}.
 * @author Stephan Fuhrmann
 */
final class JCommanderParameterMapping extends Mapping<Parameter> {

    /** Creates a new instance. */
    JCommanderParameterMapping() {
        super(Parameter.class);
    }

    @Override
    public ModelParameter createFrom(final Field field,
                                     final Parameter parameter) {
        ModelParameter modelParameter;
        if (parameter.names().length > 0) {
            modelParameter = new ModelParameter(field);
            (modelParameter)
                    .setNames(Arrays.asList(parameter.names())
                            .stream()
                            .collect(Collectors.toList()));
        } else {
            modelParameter = new ModelParameter(field);
        }
        modelParameter.setDescription(emptyIsNull(parameter.description()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setOrder(parameter.order());
        return modelParameter;
    }
}
