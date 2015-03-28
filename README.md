# UtahNameGenerator
Generates a unique Utah name, using 2000-2013 Social Security Name data for Utah and a four-gram language model

Utah has a justified reputation for naming their babies unique and innovative names. 
This program takes in all of the names given to five or more Utah babies per year for
2000 - 2013, according to the Social Security data at http://www.ssa.gov/oact/babynames/limits.html,
converts the data into a four-gram language model (see http://en.wikipedia.org/wiki/N-gram), and 
uses that data to generate a new Utah name. If the name generated is the same as an existing name 
in the database, the name is rejected and another name is generated, to guarantee a unique name.

The four-gram language model is in the file called Utah2000-2013Spinner.txt. This is a comma separated file
of four-grams and the logarithm of their associated probabilities. The three start characters (i.e.  characters
start - 3, start - 2, and start - 1) are represented by ^_` [carat, underscore, accent], and the three 
end characters are {|} [open curly bracket, pipe, close curly bracket].

To run the runnable jar file UtahNameGenerator.jar from the command line, navigate to the folder that 
contains the runnable jar and Utah2000-2013Spinner.txt, and type

    java -jar UtahNameGenerator.jar Utah2000-2013Spinner.txt

The program will output a unique male and female Utah name.
