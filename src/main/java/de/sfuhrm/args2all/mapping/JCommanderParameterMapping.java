package de.sfuhrm.args2all.mapping;

import com.beust.jcommander.Parameter;
import de.sfuhrm.args2all.model.ModelParameter;

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
    public ModelParameter createFrom(final ModelParameter modelParameter,
                                     final Parameter parameter) {
        modelParameter
                    .setNames(Arrays.asList(parameter.names())
                            .stream()
                            .collect(Collectors.toList()));
        modelParameter.setDescription(emptyIsNull(parameter.description()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setOrder(parameter.order());
        return modelParameter;
    }
}
