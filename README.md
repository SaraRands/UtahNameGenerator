# Utah Name Generator
Generates a unique Utah name, using 2000-2014 Social Security Name data for Utah and a four-gram language model


Utah has a justified reputation for naming their babies unique and innovative names. This program takes in all of the names given to five or more Utah babies per year for 2000 - 2014, according to the [Social Security data](http://www.ssa.gov/oact/babynames/limits.html), converts the data into a [four-gram language model](http://en.wikipedia.org/wiki/N-gram), and uses that data to generate a new Utah name. If the name generated is the same as an existing name in the database, the name is rejected and another name is generated, to guarantee a unique name.

## How to Run the Utah Name Generator
Download ZIP, extract files, and navigate to the downloaded folder at the command line. Run the batch file typing

```
UtahNameGenerator.bat
```

The program will output a unique girl and boy Utah name, different from all other Utah names in the Social Security Database but with the same distinctive Utah style.


### Some Girls' Names generated by the Utah Name Generator
* Aurynn
* Mikelsey
* Macklyn
* McKennedy
* Madily
* Mirissa
* Kendrey
* Sashley
* Beccally
* Lillory
* Preslyn
* McKelly
* Samandra
* Scotlynn
* Maddyson
* Kenity
* Ximee
* Carleigh
* Malyson
* Jennika
* Jayleigh
* Janesis
* Katiara
* Myleigh
* Jennedy
* Aubrigh
* Genelope
* Addily
* Seredith
* Mollyssa
* Breese
* Kelsea
* Kimberklee
* Cheyennah
* Austina


### Some Boys' Names generated by the Utah Name Generator
* Dacob
* Tristian
* Gradley
* Wyatton
* Cameson
* Graymond
* Brando
* Brigdon
* Eddison
* Zanderson
* Park
* Righam
* Brix
* Brocky
* Jakota
* Zekiel
* Mackson
* Breckay
* Braxter
* Zackson
* Reesell
* Jarediah
* Trayden
* Williot
* Matticus
* Greyton
* Becker


### More about the Program
The four-gram language model is in the file called Utah2000-2014Spinner.txt. This is a comma separated file of four-grams and the logarithm of their associated probabilities. The three start characters (i.e.  characters start - 3, start - 2, and start - 1) are represented by ^_` [carat, underscore, accent], and the three end characters are {|} [open curly bracket, pipe, close curly bracket].


### Links about Utah Names
[The Utah Baby Namer](http://wesclark.com/ubn/)

[Utah Girls Names video](http://www.youtube.com/watch?v=BfIehCrO4Zs)

[Utah Boys Names video](http://www.youtube.com/watch?v=GXPrtJKPmB0)

[Mormon Baby Names by Hildie Westenhaver](http://nameberry.com/blog/mormon-baby-names-traditions-and-trends)

[The Nayme Gaimme by Eric D. Snider](http://www.ericdsnider.com/snide/the-nayme-gaimme/)