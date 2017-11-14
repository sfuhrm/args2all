package de.sfuhrm.args2all;

import de.sfuhrm.args2all.model.ModelBase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/** Main entry point for the program.
 * */
@Slf4j
public final class Main {

    /** The command line parameters for easier reference. */
    private final Params params;

    /** Internal invocation only.
     * @param myParams the command line params.
     * */
    private Main(final Params myParams) {
        this.params = Objects.requireNonNull(myParams);
    }

    /** Parse the referenced command line parser class and
     * print the results.
     * @throws ClassNotFoundException if the referenced class was not found.
     * @throws IOException if I/O went wrong.
     * @throws TemplateException if there was a problem in template processing.
     * */
    private void parseAndPrint() throws ClassNotFoundException,
            IOException, TemplateException {
        ModelBase modelBase = parseModelBase();
        print(modelBase);
    }

    /** Parse the referenced command line parser class.
     * @return the parsed command line parser directives, projected
     * into a generic parameter model.
     * @throws ClassNotFoundException if the referenced class was not found.
     * */
    private ModelBase parseModelBase() throws
            ClassNotFoundException {
        Class<?> clazz = Main.class.getClassLoader()
                .loadClass(params.getClassName());
        ClassInspector classInspector = new ClassInspector();
        return classInspector.inspect(clazz);
    }

    /** Prints the template instantiation to the output.
     * @param modelBase the model to print.
     * @throws IOException if some I/O goes wrong.
     * @throws TemplateException if there is a problem with the template.
     * */
    private void print(final ModelBase modelBase) throws
            IOException,
            TemplateException {
        Map<String, Object> environment = new HashMap<>();
        environment.put("model", modelBase);
        Charset charset = Charset.forName(params.getCharset());
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setClassForTemplateLoading(getClass(), "/templates");
        cfg.setDefaultEncoding(charset.name());
        cfg.setTemplateExceptionHandler(
                TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setLocale(Locale.US);
        Template temp = cfg.getTemplate(params.getTemplate().getTemplateName());
        try (Writer out = Files.newBufferedWriter(params.getOut(), charset)) {
            temp.process(environment, out);
        }
    }

    /** Main entry point of the method.
     * @param args the command line arguments.
     * @throws ClassNotFoundException if the given class was not found.
     * @throws IOException if some I/O goes wrong.
     * @throws TemplateException if there is a problem with the template.
     * */
    public static void main(final String[] args) throws ClassNotFoundException,
            IOException,
            TemplateException {
        Params params = Params.parse(args);
        if (params != null) {
            Main main = new Main(params);
            main.parseAndPrint();
        }
    }
}
