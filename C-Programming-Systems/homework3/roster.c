#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "roster.h"

// Adds a student to the roster
int add_student(FILE* in, FILE* out, char* students[], int current_students, int const max_students) {
    if (current_students >= max_students) {
        char name[101];
        fscanf(in, "%[^\n]\n", name);
        fprintf(out, "Command: add %s\n", name);
        fprintf(out, "Student %s not added. The class is already at capacity.\n", name);
        return current_students;
    }

    char* name = (char*)malloc(101 * sizeof(char));
    fscanf(in, "%[^\n]\n", name);
    students[current_students] = name;
    fprintf(out, "Command: add %s\n", name);
    current_students++;
    fprintf(out, "%s was added. %d spot(s) remain.\n", name, max_students - current_students);

    return current_students;
}

// Removes a student from the roster
int remove_student(FILE* in, FILE* out, char* students[], int current_students, int const max_students) {
    char name[101];
    fscanf(in, "%[^\n]\n", name);
    fprintf(out, "Command: remove %s\n", name);

    for (int i = 0; i < current_students; i++) {
        if (strcmp(students[i], name) == 0) {
            free(students[i]);
            for (int j = i; j < current_students - 1; j++) {
                students[j] = students[j + 1];
            }
            students[current_students - 1] = NULL;
            current_students--;
            fprintf(out, "Student %s removed. The course now has %d seats remaining.\n", name, max_students - current_students);
            return current_students;
        }
    }
    fprintf(out, "No student named %s was found to remove.\n", name);
    return current_students;
}

// Modifies a students name
void modify_name(FILE* in, FILE* out, char* students[], int current_students) {
    char old_name[101], new_name[101];
    fscanf(in, "%[^:]:%[^\n]\n", old_name, new_name);
    fprintf(out, "Command: modify %s:%s\n", old_name, new_name);

    for (int i = 0; i < current_students; i++) {
        if (strcmp(students[i], old_name) == 0) {
            students[i] = realloc(students[i], (strlen(new_name) + 1) * sizeof(char));
            strcpy(students[i], new_name);
            fprintf(out, "Student %s name modified to %s.\n", old_name, new_name);
            return;
        }
    }
    fprintf(out, "No student with name %s found.\n", old_name);
}

// Displays roster
void display_class(FILE* out, char* students[], int current_students) {
    fprintf(out, "Command: display\n");
    fprintf(out, "Currently Enrolled:\n");
    for (int i = 0; i < current_students; i++) {
        fprintf(out, "    Student %d: %s\n", i + 1, students[i]);
    }
}

// Deletes all students from class
void delete_class(FILE* out, char* students[], int* current_students) {
    fprintf(out, "Command: delete\n");
    for (int i = 0; i < *current_students; i++) {
        fprintf(out, "%s removed in class delete.\n", students[i]);
        free(students[i]);
    }
    *current_students = 0;
    fprintf(out, "Class was restarted and reopened for enrollment.\n");
}
