// On my honor:
//
// - I have not discussed the C language code in my program with
// anyone other than my instructor or the teaching assistants
// assigned to this course.
//
// - I have not used C language code obtained from another student,
// or any other unauthorized source, either modified or unmodified.
//
// - If any C language code or documentation used in my program
// was obtained from an allowed source, such as a text book or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// Kalen Dacosta

#include <stdio.h>
#include "search.h"

    // Checks if the correct number of arguments are passed

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s input_file output_file\n", argv[0]);
        return 1;
    }

    // Opens the input file 
    FILE *input_file = fopen(argv[1], "r");
    if (!input_file) {
        return 1;
    }

    // Opens the output file 
    FILE *output_file = fopen(argv[2], "w");
    if (!output_file) {
        fclose(input_file);
        return 1;
    }

    // Array to store sought values and a variable for sought values
    int sought[MAX_S];
    int num_sought;

    // Reads sought values from input file
    read_sought_values(input_file, sought, &num_sought);

    // Outputs the number of sought values
    fprintf(output_file, "Searching file for matches for %d values.\n\n", num_sought);

    // Search for the sought values in the search space
    search_values(input_file, sought, num_sought, output_file);

    // Closes the input and output files
    fclose(input_file);
    fclose(output_file);

    return 0;
}
