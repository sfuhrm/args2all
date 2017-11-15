package de.sfuhrm.args2all.mapping;

import de.sfuhrm.args2all.model.ModelParameter;
import org.kohsuke.args4j.Argument;

import java.lang.reflect.Field;

/**
 * Mapping for {@linkplain Argument}.
 * @author Stephan Fuhrmann
 */
final class Args4jArgumentMapping extends Mapping<Argument> {

    /** Creates a new instance. */
    Args4jArgumentMapping() {
        super(Argument.class);
    }

    @Override
    public ModelParameter createFrom(final Field field,
                                     final Argument parameter) {
        ModelParameter modelParameter = new ModelParameter(field);
        modelParameter.setDescription(emptyIsNull(parameter.usage()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setValueName(emptyIsNull(parameter.metaVar()));
        return modelParameter;
    }
}
