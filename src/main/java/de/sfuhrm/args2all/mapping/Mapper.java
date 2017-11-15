package de.sfuhrm.args2all.mapping;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Manages the annotation mappings that are available.
 * The mappings can be checked with the {@link #getClassMappings()} call.
 * @see Mapping
 * @author Stephan Fuhrmann
 * */
public class Mapper {

    /** The list of mappers. */
    private final List<Mapping> mappers;

    /** Associative map with the key being the
     * annotation class and the value being the mapping
     * itself.
     * */
    @Getter
    private final Map<Class<?>, Mapping> classMappings;

    /** Creates a new mapper instance with the default mappings
     * installed.
     * */
    public Mapper() {
        mappers = Arrays.asList(
                new Args4jArgumentMapping(),
                new Args4jOptionMapping(),
                new JCommanderParameterMapping()
        );

        classMappings = mappers
                .stream()
                .collect(Collectors.toMap(
                        p -> p.getAnnotationClass(), p -> p));
    }
}
