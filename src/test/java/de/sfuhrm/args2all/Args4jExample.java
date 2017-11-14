package de.sfuhrm.args2all;

import lombok.Getter;
import lombok.Setter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.List;

public class Args4jExample {
    @Getter @Setter
    @Option(name = "-mars")
    private String myField;

    @Getter @Setter
    @Option(name = "--saturn", aliases = {"--uranus", "--jupiter"})
    private Integer myField2;

    // unnamed
    @Getter @Setter
    @Argument(usage = "The list of planets to visit")
    private List<String> arguments;
}
