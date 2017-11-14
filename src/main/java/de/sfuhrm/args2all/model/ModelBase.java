package de.sfuhrm.args2all.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Base for a class that has annotations from a command line parser.
 * */
@EqualsAndHashCode()
@ToString
public final class ModelBase {

    /** Which class is this model for? */
    @Getter @Setter
    private Class<?> reference;

    /** The names in the command line for this field. */
    @Getter @Setter
    private List<String> names;

    /** The parameters for the referenced entity. */
    @Getter @Setter
    private List<ModelParameter> parameters;

    /** Construct a new instance. */
    public ModelBase() {
        parameters = new ArrayList<>();
    }

    /** Does this model have a nameless parameter?
     * Example for a nameless parameter: {@code ls -al foo bar} has
     * two nameless parameters, {@code foo} and {@code bar}.
     * @return true if the model has a nameless parameter.
     * */
    public boolean hasNameless() {
        return parameters.stream()
                .filter(p -> p.getClass().equals(ModelParameter.class))
                .findFirst()
                .isPresent();
    }

    /** Get the nameless parameter.
     * Example for a nameless parameter: {@code ls -al foo bar} has
     * two nameless parameters, {@code foo} and {@code bar}.
     * @return the optional nameless parameter.
     * */
    public Optional<ModelParameter> getNameless() {
        return parameters.stream()
                .filter(p -> p.getNames().isEmpty())
                .map(p -> (ModelParameter) p)
                .findFirst();
    }

    /** Get the namefull parameters.
     * Example for a nameless parameter: {@code ls -al foo bar} has
     * two nameless parameters, {@code foo} and {@code bar}.
     * @return the namefull parameters.
     * */
    public List<ModelParameter> getNamefull() {
        List<ModelParameter> result = parameters.stream()
                .filter(p -> !p.getNames().isEmpty())
                .collect(Collectors.toList());
        return result;
    }

    /** The comparator used to order the named model parameters.
     * Uses first the {@linkplain ModelParameter#getOrder() order}
     * and then the {@linkplain ModelParameter#getNames() first name}
     * as sort keys.
     * */
    public static final Comparator<ModelParameter> COMPARATOR =
            Comparator.comparingInt(ModelParameter::getOrder)
                    .thenComparing(q ->
                            { if (q.getNames().isEmpty()) {
                                return "";
                                } else {
                                    return q.getNames().get(0);
                                }
                            },
                            String.CASE_INSENSITIVE_ORDER);
}
