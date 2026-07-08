#include <stdio.h>
#include <stdlib.h>
#include "word.h"
#include "unscramble.h"

/**
 * Main function that processes command line arguments and unscrambles quotes
 * Takes two command line arguments:
 * Input file containing data size, binary filename, and mode (clear/fuzzy)
 * Output file where the unscrambled quote will be written
 */
int main(int argc, char* argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s input_file output_file\n", argv[0]);
        return EXIT_FAILURE;
    }

    // Open input file
    FILE* input = fopen(argv[1], "r");
    if (!input) {
        fprintf(stderr, "Error opening input file: %s\n", argv[1]);
        return EXIT_FAILURE;
    }

    // Read input parameters
    uint32_t data_size;
    char data_filename[256];
    char mode[10];

    fscanf(input, "%u\n", &data_size);
    fscanf(input, "%255[^\n]\n", data_filename);
    fscanf(input, "%9[^\n]", mode);
    fclose(input);

    // Open data file
    FILE* data_file = fopen(data_filename, "rb");
    if (!data_file) {
        fprintf(stderr, "Error opening data file: %s\n", data_filename);
        return EXIT_FAILURE;
    }

    // Read binary data
    uint8_t* data = malloc(data_size);
    if (!data) {
        fprintf(stderr, "Memory allocation failed\n");
        fclose(data_file);
        return EXIT_FAILURE;
    }

    size_t bytes_read = fread(data, sizeof(uint8_t), data_size, data_file);
    fclose(data_file);

    if (bytes_read != data_size) {
        fprintf(stderr, "Error reading data file\n");
        free(data);
        return EXIT_FAILURE;
    }

    // Open output file
    FILE* output = fopen(argv[2], "w");
    if (!output) {
        fprintf(stderr, "Error opening output file: %s\n", argv[2]);
        free(data);
        return EXIT_FAILURE;
    }

    // Print header information
    fprintf(output, "Bytes in file:  %u\n", data_size);
    fprintf(output, "Name of file:   %s\n", data_filename);
    fprintf(output, "Clear|Fuzzy:    %s\n", mode);

    // Initialize word list and unscramble
    struct Word_List word_list;
    init_list(&word_list, 32);

    if (mode[0] == 'c') {
        unscramble_clear(data, data_size, &word_list);
    } else {
        unscramble_fuzzy(data, data_size, &word_list);
    }

    // Display results
    display_words(output, word_list);

    // Cleanup
    free_list(word_list);
    free(data);
    fclose(output);

    return EXIT_SUCCESS;
}
