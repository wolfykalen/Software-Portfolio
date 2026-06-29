#ifndef ROSTER_H
#define ROSTER_H
#include <stdio.h>

//Headere file for functions that are used in roster.c
int add_student(FILE* in, FILE* out, char* students[], int current_students, int max_students);
int remove_student(FILE* in, FILE* out, char* students[], int current_students, int max_students);
void modify_name(FILE* in, FILE* out, char* students[], int current_students);
void display_class(FILE* out, char* students[], int current_students);
void delete_class(FILE* out, char* students[], int* current_students);

#endif 
