package de.sfuhrm.args2all;

import de.sfuhrm.args2all.model.ModelBase;
import de.sfuhrm.args2all.model.ModelParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

/** Converts command line parser specific POJO classes to the
 * generic model format.
 * @see ModelBase
 * */
public final class ClassInspector {
    /** Inspect the given class and return the model for it.
     * @param clazz the class to inspect for annotations.
     * @return the model derived from the class inspection.
     * */
    public ModelBase inspect(final Class<?> clazz) {
        ModelBase result = new ModelBase();
        result.setReference(clazz);
        Arrays.asList(clazz.getDeclaredFields())
                .stream()
                .forEach(
                    f -> {
                        for (Annotation annotation
                                : f.getDeclaredAnnotations()) {
                            ModelParameter parameter = null;
                            // TBD if we really have both args4j
                            // and JCommander annotations here,
                            // it will get funny
                            if (annotation
                                    instanceof
                                    com.beust.jcommander.Parameter) {
                                parameter = handleField(
                                        f,
                                        (com.beust.jcommander.Parameter)
                                                annotation);
                            }

                            if (annotation
                                    instanceof
                                    org.kohsuke.args4j.Option) {
                                parameter = handleField(
                                        f,
                                        (org.kohsuke.args4j.Option)
                                                annotation);
                            }

                            if (annotation
                                    instanceof
                                    org.kohsuke.args4j.Argument) {
                                parameter = handleField(
                                        f,
                                        (org.kohsuke.args4j.Argument)
                                                annotation);
                            }

                            if (parameter != null) {
                                parameter.setValueClass(f.getType());
                                result.getParameters().add(parameter);
                            }
                        }
                    }
                );

        Collections.sort(result.getParameters(), ModelBase.COMPARATOR);
        return result;
    }

    /** Converts a field to model data.
     * @param field the field to process.
     * @param parameter the field annotation to process.
     * @return the created model parameter derived from the input.
     * */
    private ModelParameter handleField(
                   final Field field,
                   final org.kohsuke.args4j.Option parameter) {
        ModelParameter modelParameter = new ModelParameter(field);
        modelParameter.getNames().add(parameter.name());
        if (parameter.aliases() != null) {
            modelParameter.getNames().addAll(Arrays.asList(parameter.aliases())
                    .stream()
                    .collect(Collectors.toList()));
        }
        modelParameter.setDescription(emptyIsNull(parameter.usage()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setValueName(emptyIsNull(parameter.metaVar()));
        return modelParameter;
    }

    /** Converts a field to model data.
     * @param field the field to process.
     * @param parameter the field annotation to process.
     * @return the created model parameter derived from the input.
     * */
    private ModelParameter handleField(
            final Field field,
            final org.kohsuke.args4j.Argument parameter) {
        ModelParameter modelParameter = new ModelParameter(field);
        modelParameter.setDescription(emptyIsNull(parameter.usage()));
        modelParameter.setRequired(parameter.required());
        modelParameter.setValueName(emptyIsNull(parameter.metaVar()));
        return modelParameter;
    }

    /** Converts a field to model data.
     * @param field the field to process.
     * @param parameter the field annotation to process.
     * @return the created model parameter derived from the input.
     * */
    private ModelParameter handleField(
                   final Field field,
                   final com.beust.jcommander.Parameter parameter) {
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

    /** Maps the empty String to {@code null}.
     * @param in the String to map.
     * @return {@code in} or {@code null} if {@code in} was the empty string.
     * */
    private static String emptyIsNull(final String in) {
        if (in == null) {
            return null;
        }
        if (in.equals("")) {
            return null;
        }
        return in;
    }
}
