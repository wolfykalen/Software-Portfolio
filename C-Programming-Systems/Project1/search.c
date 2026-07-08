#include <stdio.h>
#include "search.h"

// Initializes the count of sought values
void read_sought_values(FILE *file, int sought[], int *num_sought) {
    int value;
    *num_sought = 0;  

    // Reads the values until an EOF or a negative number appears
    while (fscanf(file, "%d", &value) != EOF && value >= 0) {
        if (*num_sought < MAX_S) {
            sought[(*num_sought)++] = value;  
        }
    }
}

// Searches for values in the file and outputs the result
void search_values(FILE *file, int sought[], int num_sought, FILE *output_file) {
    int value, search_position = 0, total_search_space = 0;
    // Array that tracks which sought values were found
    int found[num_sought];  
    int first_match_index[num_sought];  

    // Initialize found and first_match_index
    for (int i = 0; i < num_sought; ++i) {
        found[i] = 0;  
        first_match_index[i] = -1;  
    }

    // Output titles
    fprintf(output_file, "Searched at Searching  Found at\n");
    fprintf(output_file, "-------------------------------\n");

    // Scans through the search space and keeps count of how many are in the space
    while (fscanf(file, "%d", &value) != EOF) {
        total_search_space++;  
        
        // Checks if the value matches a sought values
        for (int i = 0; i < num_sought; ++i) {
            if (value == sought[i]) {
                // If the first match of the value
                if (found[i] == 0) {
                    // Marks value as found
                    found[i] = 1;
                    // Stores the first match index 
                    first_match_index[i] = i;
                }

                // Always prints the first match index 
                fprintf(output_file, "%6d%10d%10d\n", first_match_index[i], value, search_position);
                break;
            }
        }
        search_position++;  
    }
    
    // Outputs the total number of items searched through
    fprintf(output_file, "Number of items searched through in list: %d", total_search_space);
}
