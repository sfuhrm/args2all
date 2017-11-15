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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.sfuhrm.args2all.model.ModelBase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/** Main entry point for the program.
 * @author Stephan Fuhrmann
 * */
@Slf4j
public final class Main {

    /** The command line parameters for easier field. */
    private final Params params;

    /** Internal invocation only.
     * @param myParams the command line params.
     * */
    private Main(final Params myParams) {
        this.params = Objects.requireNonNull(myParams);
    }

    /** Parse the referenced command line parser class and
     * print the results.
     * @throws ArgsException if I/O went wrong.
     * */
    private void parseAndPrint() throws ArgsException {
        ModelBase modelBase = parseModelBase();
        print(modelBase);
    }

    /** Parse the referenced command line parser class.
     * @return the parsed command line parser directives, projected
     * into a generic parameter model.
     * @throws ArgsException if the referenced class was not found.
     * */
    private ModelBase parseModelBase() throws
            ArgsException {
        Class<?> clazz = null;
        try {
            clazz = Main.class.getClassLoader()
                    .loadClass(params.getClassName());
        } catch (ClassNotFoundException e) {
            throw new ArgsException(e);
        }
        ClassInspector classInspector = new ClassInspector();
        return classInspector.inspect(clazz);
    }

    /** Prints the template instantiation to the output.
     * @param modelBase the model to print.
     * @throws ArgsException if some I/O goes wrong.
     * */
    private void print(final ModelBase modelBase) throws
            ArgsException {
        Map<String, Object> environment = new HashMap<>();
        environment.put("model", modelBase);
        environment.put("main", this);
        try {
            Charset charset = Charset.forName(
                    params.getCharset());
            Configuration cfg = new Configuration(
                    Configuration.VERSION_2_3_23);
            cfg.setClassForTemplateLoading(getClass(),
                    "/templates");
            cfg.setDefaultEncoding(charset.name());
            cfg.setTemplateExceptionHandler(
                    TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setLocale(Locale.US);
            Template temp = cfg.getTemplate(
                    params.getTemplate().getTemplateName());
            try (Writer out = Files.newBufferedWriter(
                    params.getOut(), charset)) {
                temp.process(environment, out);
            }
        } catch (IOException
                | TemplateException e) {
            throw new ArgsException(e);
        }
    }

    /**
     * Writes the model to XML.
     * @param modelBase the model to write.
     * @return the serialized XML content.
     * @throws ArgsException if writing happens to be a problem.
     * */
    public String toXml(final ModelBase modelBase)
            throws ArgsException {
        XmlMapper xmlMapper = new XmlMapper();
        StringWriter stringWriter = new StringWriter();
        try {
            xmlMapper.writeValue(stringWriter, modelBase);
        } catch (IOException e) {
            throw new ArgsException(e);
        }
        return stringWriter.toString();
    }

    /** Main entry point of the method.
     * @param args the command line arguments.
     * @throws ArgsException if some I/O goes wrong.
     * */
    public static void main(final String[] args) throws ArgsException {
        Params params = Params.parse(args);
        if (params != null) {
            Main main = new Main(params);
            main.parseAndPrint();
        }
    }
}
