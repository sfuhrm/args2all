package de.sfuhrm.args2all;

import com.beust.jcommander.Parameter;
import lombok.Getter;
import lombok.Setter;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

import java.util.List;

public class JCommanderExample {
    @Getter @Setter
    @Parameter(names = "-mars")
    private String myField;

    @Getter @Setter
    @Parameter(names = {"--saturn", "--uranus", "--jupiter"})
    private Integer myField2;

    // unnamed
    @Getter @Setter
    @Parameter(description = "The list of planets to visit")
    private List<String> arguments;
}
