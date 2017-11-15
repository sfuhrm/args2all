package de.sfuhrm.args2all.mapping;

import de.sfuhrm.args2all.model.ModelParameter;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Objects;

/** Maps one single annotation class to a {@linkplain ModelParameter}.
 * @param <U> the annotation that is being mapped by this class.
 * @author Stephan Fuhrmann
 * */
public abstract class Mapping<U extends Annotation> {

    /** The annotation class to match. */
    @Getter
    private final Class<U> annotationClass;

    /** Creates a new instance.
     * @param clazz the annotation class to match.
     * */
    Mapping(final Class<U> clazz) {
        annotationClass = Objects.requireNonNull(clazz);
    }

    /** Creates a model parameter from an field annotation.
     * @param f the field to create an model for.
     * @param annotation the fields annotation to fill the
     *                   model with.
     * @return the created model parameter object.
     * */
    public abstract ModelParameter createFrom(Field f, U annotation);

    /** Maps the empty String to {@code null}.
     * @param in the String to map.
     * @return {@code in} or {@code null} if {@code in} was the empty string.
     * */
    static String emptyIsNull(final String in) {
        if (in == null) {
            return null;
        }
        if (in.equals("")) {
            return null;
        }
        return in;
    }
}
