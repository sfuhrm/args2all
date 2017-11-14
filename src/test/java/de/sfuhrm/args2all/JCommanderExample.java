package de.sfuhrm.args2all;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.List;

public class JCommanderExample {
    @Getter @Setter
    @Parameter(names = "-mars", order = 1)
    private String myField;

    @Getter @Setter
    @Parameter(names = {"--saturn", "--uranus", "--jupiter"}, order = 2)
    private Integer myField2;

    // unnamed
    @Getter @Setter
    @Parameter(description = "The list of planets to visit", order = 3)
    private List<String> arguments;
}
