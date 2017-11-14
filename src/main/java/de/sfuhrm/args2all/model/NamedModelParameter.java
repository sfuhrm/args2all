package de.sfuhrm.args2all.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/** A parameter associated to a field with names / aliases. */
@EqualsAndHashCode()
@ToString
public class NamedModelParameter extends ModelParameter {

    /** The names in the command line for this field. */
    @Getter @Setter
    private List<String> names;

    /** Creates a new instance of the parameter.
     * @param myReference the field this parameter belongs to.
     * */
    public NamedModelParameter(final Field myReference) {
        super(myReference);
        names = new ArrayList<>();
    }
}
