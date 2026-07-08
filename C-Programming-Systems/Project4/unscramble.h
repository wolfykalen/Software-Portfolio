#ifndef UNSCRAMBLE_H
#define UNSCRAMBLE_H

#include <stdint.h>
#include "word.h"

// Unscrambles a clear text quote from binary data
void unscramble_clear(uint8_t* data, uint32_t data_size, struct Word_List* word_list);

// Unscrambles a fuzzy quote from binary data
void unscramble_fuzzy(uint8_t* data, uint32_t data_size, struct Word_List* word_list);

#endif
