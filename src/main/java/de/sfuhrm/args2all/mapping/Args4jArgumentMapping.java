package de.sfuhrm.args2all.mapping;

import de.sfuhrm.args2all.model.ModelParameter;
import org.kohsuke.args4j.Argument;

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
    public ModelParameter createFrom(final ModelParameter modelParameter,
                                     final Argument parameter) {
        modelParameter.setDescription(emptyIsNull(parameter.usage()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setValueName(emptyIsNull(parameter.metaVar()));
        return modelParameter;
    }
}
