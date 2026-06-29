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

#ifndef COMIC_H
#define COMIC_H

#define INITIAL_COMIC_LIST_SIZE 10 // Initial size of the comic list
#define MAX_TITLE_LENGTH 100       // Define max lengths
#define MAX_AUTHOR_LENGTH 100      // Define max lengths

struct Comic
{
    char *date;
    char *code;
    char *publisher;
    char *title;
    char *cost;
    int bought; // New field to indicate if the comic has been bought (1 for yes, 0 for no)
};

struct Comic_List
{
    struct Comic *list; // Pointer to dynamically allocated array of comics
    int count;          // Current number of comics in the list
    int size;           // Total allocated size of the list
};

// Function declarations
void init_list(struct Comic_List *list);
int load_comics(struct Comic_List *list, const char *filename);
void display_list(struct Comic_List list, FILE *out);
void parse_comic_fields(char *buffer, struct Comic *comic);
void free_comic(struct Comic *comic);
void free_list(struct Comic_List *list);
void add_comic(struct Comic_List *list, struct Comic comic);
void clear_comic_list(struct Comic_List *list);
void save_list(struct Comic_List list, FILE *out);
void find_comic_by_index(struct Comic_List *list, int index, FILE *out);
void remove_comic_by_index(struct Comic_List *list, int index, FILE *out);
void buy_comic_by_index(struct Comic_List *list, int index, FILE *out); // Added for buy

#endif
