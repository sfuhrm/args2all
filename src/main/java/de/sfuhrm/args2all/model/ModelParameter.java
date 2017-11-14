package de.sfuhrm.args2all.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Objects;

/** A parameter associated to a field. */
@EqualsAndHashCode()
@ToString
public class ModelParameter {
    /** The field this parameter belongs to.
     * TBD should be the field name instead?
     * */
    @Getter
    @Setter
    private Field reference;

    /** The human readable description in the help text for this parameter. */
    @Getter @Setter
    private String description;

    /** Is this parameter required? */
    @Getter @Setter
    private boolean required;

    /** What is the name of the value type? */
    @Getter @Setter
    private String valueName;

    /** The value class for this parameter. */
    @Getter @Setter
    private Class<?> valueClass;

    /** The key for sorting the parameter. */
    @Getter @Setter
    private int order = -1;

    /** Creates a new instance of the parameter.
     * @param myReference the field this parameter belongs to.
     * */
    public ModelParameter(final Field myReference) {
        this.reference = Objects.requireNonNull(myReference);
    }
}
