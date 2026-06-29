#include <stdio.h>
#include <stdlib.h>
#include "unscramble.h"

/**
 * Helper function to flip nybbles in a byte
 * Takes a byte and swaps its high and low nybbles
 */
static uint8_t flip_nybbles(uint8_t byte) {
    return ((byte & 0x0F) << 4) | ((byte & 0xF0) >> 4);
}

/**
 * Helper function to read a 16-bit value from memory
 */
static uint16_t read_uint16(uint8_t* ptr) {
    return *((uint16_t*)ptr);
}

/**
 * Unscrambles a clear text quote from binary data
 * Processes each word in the data file and adds it to the word list
 */
void unscramble_clear(uint8_t* data, uint32_t data_size, struct Word_List* word_list) {
    uint16_t num_words = read_uint16(data);
    uint16_t current_offset = read_uint16(data + 2);
    uint16_t words_processed = 0;

    while (current_offset < data_size && words_processed < num_words) {
        uint8_t record_length = data[current_offset];

        // Add validation check for record length
        if (record_length < 3 || current_offset + record_length > data_size) {
            break;
        }

        uint16_t next_offset = read_uint16(data + current_offset + 1);

        // Allocate and copy the word
        char* word = malloc(record_length - 2);
        for (uint8_t i = 0; i < record_length - 3; i++) {
            uint8_t unfuzzed = data[current_offset + 3 + i];
            if (unfuzzed < 32 || unfuzzed > 126) {
                unfuzzed = '?';
            }
            word[i] = (char)unfuzzed;
        }
        word[record_length - 3] = '\0';

        uint16_t display_offset = current_offset + 3;

        // Create and add word to list
        struct Word new_word;
        init_word(&new_word);
        update_word(&new_word, word, display_offset);
        add_word(word_list, new_word);

        free(word);

        current_offset = next_offset;
        words_processed++;
    }
}

/**
 * Unscrambles a fuzzy (encrypted) quote from binary data
 * Decrypts each word using the flipped record length and adds it to the word list
 */
void unscramble_fuzzy(uint8_t* data, uint32_t data_size, struct Word_List* word_list) {
    uint16_t num_words = read_uint16(data);
    uint16_t current_offset = read_uint16(data + 2);
    uint16_t words_processed = 0;

    while (current_offset < data_size && words_processed < num_words) {
        uint8_t record_length = data[current_offset];

        if (record_length < 3 || current_offset + record_length > data_size) {
            break;
        }

        uint16_t next_offset = read_uint16(data + current_offset + 1);
        uint8_t flipped_length = flip_nybbles(record_length);

        // Unfuzz the letters first
        char* word = malloc(record_length - 2);
        for (uint8_t i = 0; i < record_length - 3; i++) {
            uint8_t unfuzzed = data[current_offset + 3 + i] ^ flipped_length;
            if (unfuzzed < 32 || unfuzzed > 126) {
                unfuzzed = '?';
            }
            word[i] = (char)unfuzzed;
        }
        word[record_length - 3] = '\0';

        // Use the first unfuzzed letter for the offset mask
        uint16_t offset_mask = ((uint16_t)word[0] << 8) | word[0];
        next_offset ^= offset_mask;

        uint16_t display_offset = current_offset + 3;

        // Create and add word to list
        struct Word new_word;
        init_word(&new_word);
        update_word(&new_word, word, display_offset);
        add_word(word_list, new_word);

        free(word);

        // Move to next word
        current_offset = next_offset;
        words_processed++;
    }
}
