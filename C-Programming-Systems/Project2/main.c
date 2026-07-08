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
#include <stdlib.h>
#include <string.h>
#include "comic.h"

// Function prototypes
void find_comic_by_index(struct Comic_List *list, int index, FILE *out);
void remove_comic_by_index(struct Comic_List *list, int index, FILE *out);
void buy_comic_by_index(struct Comic_List *list, int index, FILE *out); // Added prototype for buy

int main(int argc, char *argv[])
{
    struct Comic_List comic_list; // Declare a Comic_List
    init_list(&comic_list);       // Initialize the comic list

    char command[100];
    char filename[100];
    char save_filename[100];
    int code;
    FILE *command_file = NULL;

    // If a command file is provided as an argument
    if (argc > 1)
    {
        command_file = fopen(argv[1], "r");
        if (command_file == NULL)
        {
            perror("Error opening command file"); // Print error if file cannot be opened
            return EXIT_FAILURE;
        }
    }

    FILE *output_file = fopen("output.txt", "w"); // Open the output file for writing
    if (output_file == NULL)
    {
        perror("Error opening output file"); // Print error if file cannot be opened
        if (command_file)
            fclose(command_file);
        return EXIT_FAILURE;
    }

    // Main command loop
    while (1)
    {
        if (command_file)
        {
            if (fgets(command, sizeof(command), command_file) == NULL)
            {
                break;
            }
        }
        else
        {
            printf("Enter command: ");
            fgets(command, sizeof(command), stdin); // Read command from standard input
        }

        // Debug: Show what command is being processed
        printf("Processing command: %s", command);

        // Load command
        if (sscanf(command, "load %s", filename) == 1)
        {
            fprintf(output_file, "Command: load %s\n", filename);
            printf("Loading comics from: %s\n", filename); // Debugging

            clear_comic_list(&comic_list); // Clear the list before loading new comics
            init_list(&comic_list);        // Reinitialize after clearing

            int num_comics = load_comics(&comic_list, filename);
            fprintf(output_file, "\tNumber of comics: %d\n", num_comics);
            printf("Loaded %d comics from %s\n", num_comics, filename); // Debugging
        }
        else if (strcmp(command, "display\n") == 0)
        {
            fprintf(output_file, "Command: display\n");
            printf("Displaying comics...\n"); // Debugging
            display_list(comic_list, output_file);
        }
        else if (strcmp(command, "clear\n") == 0)
        {
            fprintf(output_file, "Command: clear\n");
            clear_comic_list(&comic_list);
            printf("List cleared. Displaying to confirm it's empty...\n"); // Debugging
            display_list(comic_list, output_file);                         // Force display to confirm list is empty
        }

        // Find command
        else if (sscanf(command, "find %d", &code) == 1)
        {
            find_comic_by_index(&comic_list, code, output_file);
        }
        // Remove command
        else if (sscanf(command, "remove %d", &code) == 1)
        {
            fprintf(output_file, "Command: remove %d\n", code);
            remove_comic_by_index(&comic_list, code, output_file);
        }
        // Buy command
        else if (sscanf(command, "buy %d", &code) == 1)
        {
            fprintf(output_file, "Command: buy %d\n", code);
            buy_comic_by_index(&comic_list, code, output_file); // Call the new buy function
        }
        // Save command
        else if (sscanf(command, "save %s", save_filename) == 1)
        {
            fprintf(output_file, "Command: save %s\n", save_filename);
            FILE *save_file = fopen(save_filename, "w");
            if (save_file == NULL)
            {
                perror("Error opening save file");
            }
            else
            {
                save_list(comic_list, save_file);
                fclose(save_file);
            }
        }
        // Invalid command
        else
        {
            fprintf(output_file, "Invalid command: %s", command);
        }
    }

    // Free memory and close files
    free_list(&comic_list);
    if (command_file)
        fclose(command_file);
    fclose(output_file);
    return EXIT_SUCCESS;
}
