#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "roster.h"

int main(int argc, char* argv[]) {
    // Checks if the number of arguments is correct
    if (argc != 3) {
        fprintf(stderr, "Incorrect number of arguments.\n");
        fprintf(stderr, "USAGE: ./%s <input_file> <output_file>\n", argv[0]);
        return 1;
    }

    // Opens input and output 
    FILE* in = fopen(argv[1], "r");
    FILE* out = fopen(argv[2], "w");

    if (in == NULL || out == NULL) {
        fprintf(stderr, "Error opening files.\n");
        return 1;
    }

    // Reads the max number of students
    int max_students = 0;
    fscanf(in, "%d\n", &max_students);

    // Allocates memory for student list
    char** students = (char**)malloc(max_students * sizeof(char*));
    if (students == NULL) {
        fprintf(stderr, "Memory allocation failed.\n");
        return 1;
    }

    int current_students = 0;  

    char cmd[10]; 

    // Loops through the input file
    while (fscanf(in, "%s", cmd) != EOF) {
        if (strcmp(cmd, "add") == 0) {
            current_students = add_student(in, out, students, current_students, max_students);
        } 
        else if (strcmp(cmd, "remove") == 0) {
            current_students = remove_student(in, out, students, current_students, max_students);
        } 
        else if (strcmp(cmd, "modify") == 0) {
            modify_name(in, out, students, current_students);
        } 
        else if (strcmp(cmd, "display") == 0) {
            display_class(out, students, current_students);
        } 
        else if (strcmp(cmd, "delete") == 0) {
            delete_class(out, students, &current_students);
            fscanf(in, "%d\n", &max_students);  
            free(students);  
            students = (char**)malloc(max_students * sizeof(char*));  
        }
    }

    // Clean up
    fclose(in);
    fclose(out);
    free(students);

    return 0;
}
