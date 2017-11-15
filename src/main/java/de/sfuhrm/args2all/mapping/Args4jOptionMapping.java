package de.sfuhrm.args2all.mapping;

import de.sfuhrm.args2all.model.ModelParameter;
import org.kohsuke.args4j.Option;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Mapping for {@linkplain Option}.
 * @author Stephan Fuhrmann
 */
final class Args4jOptionMapping extends Mapping<Option> {

    /** Creates a new instance. */
    Args4jOptionMapping() {
        super(Option.class);
    }

    @Override
    public ModelParameter createFrom(final ModelParameter modelParameter,
                                     final Option parameter) {
        modelParameter.getNames().add(parameter.name());
        if (parameter.aliases() != null) {
            modelParameter.getNames().addAll(
                    Arrays.asList(parameter.aliases()));
        }
        modelParameter.setDescription(emptyIsNull(parameter.usage()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setValueName(emptyIsNull(parameter.metaVar()));
        return modelParameter;
    }
}
