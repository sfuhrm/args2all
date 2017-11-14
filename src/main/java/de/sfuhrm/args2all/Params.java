/*
 * Copyright (C) 2017 Stephan Fuhrmann
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package de.sfuhrm.args2all;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * The command line parameters as a POJO. Must be created using
 * {@link #parse(java.lang.String[])}.
 *
 * @see #parse(java.lang.String[])
 * @author Stephan Fuhrmann
 */
@Slf4j
final class Params {

    /** Whether just to show the command line help.  */
    @Getter
    @Option(name = "-help", aliases = {"-h"},
            usage = "Show the command line help.", help = true)
    private boolean help;

    /** Name of the annotated class to read.  */
    @Getter
    @Option(name = "-class", aliases = {"-c"},
            usage = "Name of the annotated class to read. The annotations are "
            + "from a library like args4j or JCommander. Note that the class "
            + "must be loadable from the programs context, meaning reachable "
            + "from the classpath.",
            metaVar = "CLASS",
            required = true)
    private String className;

    /** The template to print. */
    enum Template {
        /** Output in the Markdown format. */
        MARKDOWN("markdown.ftl"),

        /** Output in the Man page / troff format. */
        MANPAGE("manpage.ftl");

        /** The name of the corresponding template file. */
        @Getter
        private final String templateName;

        /** Creates a new instance.
         * @param myTemplateName the template file name.
         * */
        Template(final String myTemplateName) {
            this.templateName = Objects.requireNonNull(myTemplateName);
        }
    };

    /** The template to use for output.  */
    @Getter
    @Option(name = "-template", aliases = {"-t"},
            usage = "The template to use for output. This is the output "
                    + "flavor you are desiring to generate."
            )
    private Template template = Template.MARKDOWN;

    /** The charset to use for output.  */
    @Getter
    @Option(name = "-charset", aliases = {"-C"},
            usage = "The charset encoding to use for the output file.",
            metaVar = "CHARSET"
    )
    private String charset = "UTF-8";

    /** The charset to use for output.  */
    @Getter
    @Option(name = "-out", aliases = {"-o"},
            usage = "The output where to write the result to.",
            metaVar = "FILE",
            required = true
    )
    private Path out;

    /**
     * Parse the command line options.
     *
     * @param args the command line args as passed to the main method of the
     * program.
     * @return the parsed command line options or {@code null} if the program
     * needs to exit. {@code null} will be returned if the command lines are
     * wrong or the command line help was displayed.
     */
    public static Params parse(final String[] args) {
        Params result = new Params();
        CmdLineParser cmdLineParser = new CmdLineParser(result);
        try {
            if (log.isDebugEnabled()) {
                log.debug("Args: {}", Arrays.toString(args));
            }
            cmdLineParser.parseArgument(args);
            if (result.help) {
                cmdLineParser.printUsage(System.err);
                return null;
            }
            return result;
        } catch (CmdLineException ex) {
            log.warn("Error in parsing", ex);
            System.err.println(ex.getMessage());
            cmdLineParser.printUsage(System.err);
        }
        return null;
    }
}
