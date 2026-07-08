#ifndef SEARCH_H
#define SEARCH_H

#include <stdio.h>  

// Maximum number of sought values
#define MAX_S 10  

// Function declarations
void read_sought_values(FILE *file, int sought[], int *num_sought);
void search_values(FILE *file, int sought[], int num_sought, FILE *output_file);

#endif
