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
// was obtained from an allowed source, such as a textbook or course
// notes, that has been clearly noted with a proper citation in
// the comments of my program.
//
// Kalen Dacosta

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "comic.h"

// Initialize the comic list
void init_list(struct Comic_List *list)
{
    printf("Initializing new comic list...\n"); // Debug: Confirm reinitialization

    list->count = 0;
    list->size = INITIAL_COMIC_LIST_SIZE;
    list->list = malloc(list->size * sizeof(struct Comic));
    if (list->list == NULL)
    {
        perror("Failed to allocate memory for comic list");
        exit(EXIT_FAILURE);
    }

    printf("Comic list initialized. Initial size: %d\n", list->size); // Debug: Show initialized size
}

// Load comics from a CSV file
int load_comics(struct Comic_List *list, const char *filename)
{
    FILE *file = fopen(filename, "r");
    if (file == NULL)
    {
        perror("Error opening comic file");
        return 0;
    }

    char buffer[1024];
    fgets(buffer, sizeof(buffer), file); // Skip the header line

    printf("Loading comics from file: %s\n", filename); // Debugging

    while (fgets(buffer, sizeof(buffer), file))
    {
        // Ignore empty lines
        if (strlen(buffer) <= 1)
            continue;

        if (list->count >= list->size)
        {
            // Resize the list if necessary
            list->size *= 2;
            struct Comic *new_list = realloc(list->list, list->size * sizeof(struct Comic));
            if (new_list == NULL)
            {
                perror("Failed to reallocate memory for comic list");
                fclose(file);
                return 0;
            }
            list->list = new_list;
        }

        // Parse and add the comic to the list
        struct Comic comic;
        parse_comic_fields(buffer, &comic);
        if (comic.date && comic.code && comic.publisher && comic.title && comic.cost)
        {
            comic.bought = 0; // Initialize as not bought
            add_comic(list, comic);
        }
        else
        {
            free_comic(&comic);
            fprintf(stderr, "Error: Malformed line: %s", buffer);
        }
    }
    fclose(file);
    printf("Finished loading comics from %s. Total comics: %d\n", filename, list->count); // Debugging
    return list->count;
}

// Display the comics in the list
void display_list(struct Comic_List list, FILE *out)
{
    if (list.count == 0)
    {
        fprintf(out, "List is currently empty.\n");
        return;
    }

    // Debug: Print comics to terminal for verification
    printf("Displaying %d comics from the list:\n", list.count);

    for (int i = 0; i < list.count; i++)
    {
        struct Comic comic = list.list[i];
        fprintf(out, "Comic Number: %d\n", i + 1);
        fprintf(out, "\tDate: %s\n", comic.date);
        fprintf(out, "\tCode: %s\n", comic.code);
        fprintf(out, "\tPublisher: %s\n", comic.publisher);
        fprintf(out, "\tTitle: %s\n", comic.title);
        fprintf(out, "\tCost: %s\n", comic.cost);
        fprintf(out, "\tBought: %s\n", comic.bought ? "Yes" : "No"); // Display buy status

        // Debug: Print to terminal as well for tracking
        printf("Comic %d: Date: %s, Code: %s, Publisher: %s, Title: %s, Cost: %s, Bought: %s\n",
               i + 1, comic.date, comic.code, comic.publisher, comic.title, comic.cost,
               comic.bought ? "Yes" : "No");
    }
}

// Parse a line of the CSV file into a Comic structure
void parse_comic_fields(char *line, struct Comic *comic)
{
    char *token = strtok(line, ",");
    if (token)
        comic->date = strdup(token);
    else
        comic->date = NULL;

    token = strtok(NULL, ",");
    if (token)
        comic->code = strdup(token);
    else
        comic->code = NULL;

    token = strtok(NULL, ",");
    if (token)
        comic->publisher = strdup(token);
    else
        comic->publisher = NULL;

    token = strtok(NULL, ",");
    if (token)
        comic->title = strdup(token);
    else
        comic->title = NULL;

    token = strtok(NULL, ",");
    if (token)
        comic->cost = strdup(token);
    else
        comic->cost = NULL;

    // Check if any field is NULL
    if (!comic->date || !comic->code || !comic->publisher || !comic->title || !comic->cost)
    {
        // Free already allocated memory if any field is missing
        free_comic(comic);
        fprintf(stderr, "Error: Missing fields in line: %s\n", line);
    }
}

// Free memory allocated for a Comic structure
void free_comic(struct Comic *comic)
{
    if (comic->date)
    {
        free(comic->date);
        comic->date = NULL;
    }
    if (comic->code)
    {
        free(comic->code);
        comic->code = NULL;
    }
    if (comic->publisher)
    {
        free(comic->publisher);
        comic->publisher = NULL;
    }
    if (comic->title)
    {
        free(comic->title);
        comic->title = NULL;
    }
    if (comic->cost)
    {
        free(comic->cost);
        comic->cost = NULL;
    }
}

// Free memory allocated for the comic list
void free_list(struct Comic_List *list)
{
    for (int i = 0; i < list->count; i++)
    {
        free_comic(&list->list[i]);
    }
    free(list->list);  // Free the list itself
    list->list = NULL; // Prevent dangling pointer
    list->count = 0;   // Reset count
    list->size = 0;    // Reset size
}

// Add a comic to the comic list
void add_comic(struct Comic_List *list, struct Comic comic)
{
    if (list->count >= list->size)
    {
        list->size *= 2;
        struct Comic *new_list = realloc(list->list, list->size * sizeof(struct Comic));
        if (new_list == NULL)
        {
            perror("Failed to reallocate memory for comic list");
            exit(EXIT_FAILURE);
        }
        list->list = new_list;
    }
    list->list[list->count++] = comic;
}

// Clear the comic list
void clear_comic_list(struct Comic_List *list)
{
    printf("Clearing comic list with %d comics...\n", list->count); // Debugging

    // Free all individual comics
    for (int i = 0; i < list->count; i++)
    {
        free_comic(&list->list[i]);
    }

    // Free the list array itself
    free(list->list);
    list->list = NULL; // Important to avoid dangling pointers

    // Reset the count and size to ensure the list is empty
    list->count = 0;
    list->size = 0;

    printf("Comic list cleared. Current count: %d\n", list->count); // Debugging
}

// Save the comic list to a file
void save_list(struct Comic_List list, FILE *out)
{
    fprintf(out, "DATE,CODE,PUBLISHER,TITLE,PRICE\n");
    for (int i = 0; i < list.count; i++)
    {
        fprintf(out, "%s,%s,%s,%s,%s\n",
                list.list[i].date,
                list.list[i].code,
                list.list[i].publisher,
                list.list[i].title,
                list.list[i].cost);
    }
}

// Function to find a comic by its index in the list
void find_comic_by_index(struct Comic_List *list, int index, FILE *out)
{
    fprintf(out, "Command: find %d\n", index);

    if (index >= 0 && index < list->count)
    {
        fprintf(out, "\tDate: %s\n", list->list[index].date);
        fprintf(out, "\tCode: %s\n", list->list[index].code);
        fprintf(out, "\tPublisher: %s\n", list->list[index].publisher);
        fprintf(out, "\tTitle: %s\n", list->list[index].title);
        fprintf(out, "\tCost: %s\n", list->list[index].cost);
        fprintf(out, "\tBought: %s\n", list->list[index].bought ? "Yes" : "No"); // Display buy status
    }
    else
    {
        fprintf(out, "There is no comic at index #%d in the list.\n", index);
    }
}

// Function to remove a comic by its index
void remove_comic_by_index(struct Comic_List *list, int index, FILE *out)
{
    if (index >= 0 && index < list->count)
    {
        free_comic(&list->list[index]);

        // Shift the remaining comics to fill the gap
        for (int i = index; i < list->count - 1; i++)
        {
            list->list[i] = list->list[i + 1];
        }

        list->count--; // Decrease the count
        fprintf(out, "Comic at index %d successfully removed\n", index);

        // Debugging: Show the current state of the list after removal
        printf("Removed comic at index %d. Current list count: %d\n", index, list->count);
    }
    else
    {
        fprintf(out, "Comic at index %d was not removed\n", index);
        printf("Invalid index %d for removal. Current list count: %d\n", index, list->count); // Debugging
    }
}

// Function to buy a comic by its index
void buy_comic_by_index(struct Comic_List *list, int index, FILE *out)
{
    if (index >= 0 && index < list->count)
    {
        if (list->list[index].bought == 1)
        {
            fprintf(out, "Comic at index %d has already been bought.\n", index);
            printf("Comic at index %d has already been bought.\n", index); // Debugging
        }
        else
        {
            list->list[index].bought = 1; // Mark the comic as bought
            fprintf(out, "Comic at index %d has been bought\n", index);
            printf("Comic at index %d marked as bought.\n", index); // Debugging
        }
    }
    else
    {
        fprintf(out, "Comic at index %d could not be found for purchase\n", index);
        printf("Invalid index %d for buying. Current list count: %d\n", index, list->count); // Debugging
    }
}
