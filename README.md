# Args2All

Converts machine readable command line parser library object descriptions (args4j, JCommander)
into a variety of human readable text documents. 

The main purpose is to reduce overhead when documenting software.

Two examples can be seen [below](#output-formats-supported).

## Command line options

Usage is as follows:

    java -jar target/args2all-0.0.1-jar-with-dependencies.jar <OPTIONS>
    
Where the possible options are listed here (args2all-generated):    

* **-help, -h**

  Show the command line help.

  
* **-class, -c** *=* *CLASS*

  Name of the annotated class to read. The annotations are from a library like args4j or JCommander. Note that the class must be loadable from the programs context, meaning reachable from the classpath.

  *Required*
* **-template, -t**

  The template to use for output. This is the output flavor you are desiring to generate.

  
* **-charset, -C** *=* *CHARSET*

  The charset encoding to use for the output file.

  
* **-out, -o** *=* *FILE*

  The output where to write the result to.

  *Required*

### Output formats supported

The *all* in Args2all is quite limited at the moment. Please
read the current options in the next sections.

#### Markdown

Outputs a markdown text that is inspired by the layout of UN*X manual pages.

Here's an example of the program itself:

![Markdown example](https://raw.githubusercontent.com/sfuhrm/args2all/master/examples/markdown.png "Markdown example")

Markdown code can be seen [here](https://raw.githubusercontent.com/sfuhrm/args2all/master/examples/args2all.md).

#### Manpage

Outputs a partial manual page / troff page that can be used as a
template. The parts of the file that need to be filled are
marked as pseudo-variables, for example `$DESCRIPTION`.

Here's an example of the program itself:

![Manpage example](https://raw.githubusercontent.com/sfuhrm/args2all/master/examples/manpage.png "Manpage example")

Manpage code can be seen [here](https://raw.githubusercontent.com/sfuhrm/args2all/master/examples/args2all.1).

## License

Copyright 2017 Stephan Fuhrmann

Licensed under the GNU GENERAL PUBLIC LICENSE 2.0.
Please see the licensing conditions under [LICENSE](./LICENSE)
or at [https://www.gnu.org/licenses/gpl-2.0.html](https://www.gnu.org/licenses/gpl-2.0.html).
