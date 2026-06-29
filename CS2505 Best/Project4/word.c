#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <assert.h>
#include "word.h"

//Calculates the length of a null-terminated string
uint32_t strlength(const char* const word) {
    uint32_t length = 0;
    while (*(word + length) != '\0') {
        length++;
    }
    return length;
}

//Copies a string from source to destination
void strcopy(char* const destination, const char* const source) {
    uint32_t i = 0;
    while (*(source + i) != '\0') {
        *(destination + i) = *(source + i);
        i++;
    }
    *(destination + i) = '\0';
}

/**
 * Initializes a Word structure
 * Sets word pointer to NULL and offset to 0
 */
void init_word(struct Word* word) {
    word->word = NULL;
    word->offset = 0;
}

/**
 * Updates a Word structure with new string and offset
 * Frees existing word if present, allocates memory for new word
 */
void update_word(struct Word* word, const char* const wrd, uint16_t offset) {
    if (word->word != NULL) {
        free(word->word);
    }

    uint32_t length = strlength(wrd) + 1;
    word->word = malloc(length * sizeof(char));
    if (word->word == NULL) {
        fprintf(stderr, "Memory allocation failed in update_word\n");
        exit(EXIT_FAILURE);
    }

    strcopy(word->word, wrd);
    word->offset = offset;
}

/**
 * Displays a Word structure to a file
 * Formats output with fixed width for word and hex offset
 */
void display_word(FILE* out, struct Word word) {
    fprintf(out, "%-20s @ Offset: %02x\n", word.word, word.offset);
}

//Frees memory associated with a Word structure
void free_word(struct Word word) {
    free(word.word);
    word.word = NULL;
}

/**
 * Initializes a Word_List structure
 * Allocates memory for initial word array
 */
void init_list(struct Word_List* word_list, uint16_t size) {
    if (size == 0) size = 1;
    word_list->words = malloc(size * sizeof(struct Word));
    assert(word_list->words != NULL);
    word_list->size = size;
    word_list->count = 0;
}

/**
 * Adds a Word to a Word_List
 * Resizes array if necessary
 */
void add_word(struct Word_List* word_list, struct Word word) {
    if (word_list->count == word_list->size) {
        word_list->size = (word_list->size > 0) ? word_list->size * 2 : 1;
        word_list->words = realloc(word_list->words, word_list->size * sizeof(struct Word));
        assert(word_list->words != NULL);
    }

    *(word_list->words + word_list->count) = word;
    word_list->count++;
}

/**
 * Retrieves a Word from a Word_List at specified index
 * Returns "Word Not Found" if index is invalid
 */
struct Word get_word_at_index(struct Word_List word_list, uint16_t index) {
    struct Word result;
    init_word(&result);

    if (index < word_list.count) {
        struct Word source = *(word_list.words + index);
        update_word(&result, source.word, source.offset);
        return result;
    }

    update_word(&result, "Word Not Found", 0);
    return result;
}

/**
 * Frees all memory associated with a Word_List
 * Frees each Word and the word array
 */
void free_list(struct Word_List word_list) {
    for (uint16_t i = 0; i < word_list.count; i++) {
        free_word(*(word_list.words + i));
    }
    free(word_list.words);
    word_list.words = NULL;
    word_list.count = 0;
    word_list.size = 0;
}

//Displays all Words in a Word_List to a file
void display_words(FILE* out, struct Word_List word_list) {
    for (uint16_t i = 0; i < word_list.count; i++) {
        display_word(out, *(word_list.words + i));
    }
}
