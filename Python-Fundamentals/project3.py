'''
Project 3 - Reading Ease - Fall 2024  
Author: Kalen Dacosta (kalendaco)

This program analyzes a text file for various readability statistics such as the number of
sentences, words, unique words, syllables, It also allows users to check the frequency of specific words.

I have neither given nor received unauthorized assistance on this assignment.
Signed: Kalen Dacosta
'''

def read_text(file_name):
    ''' Reads the content of a file returns it as a single string
        Parameters:
            file_name - name of file to read
        Return: string containing file content
    '''
    with open(file_name, 'r') as file:
        return file.read()

def clean_text(raw_text):
    ''' Cleans text by converting to lowercase and removing punctuation
        Parameters:
            raw_text - string containing text to clean
        Return: cleaned text 
    '''
    to_remove = ',;:.!?[]()*-\'"'
    for char in to_remove:
        raw_text = raw_text.replace(char, '')
    return raw_text.lower()

def get_word_frequencies(words):
    ''' Creates a dictionary of word frequencies
        Parameters:
            words - list of words from the cleaned text
        Return: dictionary with word frequencies
    '''
    frequencies = {}
    for word in words:
        frequencies[word] = frequencies.get(word, 0) + 1
    return frequencies

def count_syllables(word):
    ''' Estimates and returns the number of syllables in a word
        Parameters:
            word - the word to analyze
        Return: number of syllables in the word
    '''
    syllables = 0
    vowels = 'aeiouy'
    word = word.lower().strip(".:;?!")
    if word[0] in vowels:
        syllables += 1
    for index in range(1, len(word)):
        if word[index] in vowels and word[index - 1] not in vowels:
            syllables += 1
    if word.endswith('e'):
        syllables -= 1
    if word.endswith('le'):
        syllables += 1
    if syllables == 0: 
        syllables = 1
    return syllables

def count_all_syllables(words):
    ''' Counts the total number of syllables in a list of words
        Parameters:
            words - list of words from cleaned text
        Return: total syllable count
    '''
    return sum(count_syllables(word) for word in words)

def main():
    ''' Main program to analyze a text file 
    '''
    print("Welcome to the Reading Ease Analyzer!\n")
    
    file_name = input("Name of file to analyze? ")
    try:
        raw_text = read_text(file_name)
    except FileNotFoundError:
        print(f"Error: File '{file_name}' not found.")
        return

    cleaned_text = clean_text(raw_text)
    words = cleaned_text.split()
    sentences = raw_text.count('.') + raw_text.count('!') + raw_text.count('?')
    word_frequencies = get_word_frequencies(words)
    total_words = len(words)
    unique_words = len(word_frequencies)
    syllable_count = count_all_syllables(words)
    
    avg_words_per_sentence = total_words / sentences if sentences > 0 else 0
    avg_syllables_per_word = syllable_count / total_words if total_words > 0 else 0

    flesch_reading_ease = 206.835 - (1.015 * avg_words_per_sentence) - (84.6 * avg_syllables_per_word)
    flesch_grade_level = (0.39 * avg_words_per_sentence) + (11.8 * avg_syllables_per_word) - 15.59

    # Display statistics
    print(f"\nNumber of sentences: {sentences}")
    print(f"Number of words: {total_words}")
    print(f"Number of unique words: {unique_words}")
    print(f"Average words per sentence: {avg_words_per_sentence:.1f}")
    print(f"Average syllables per word: {avg_syllables_per_word:.1f}")
    print(f"Reading-ease score: {flesch_reading_ease:.1f}")
    print(f"U.S. grade level: {flesch_grade_level:.1f}\n")

    # Word frequency checker
    while True:
        word_to_check = input('Enter word to check ("q" to quit): ').lower()
        if word_to_check == 'q':
            break
        count = word_frequencies.get(word_to_check, 0)
        time_word = "time" if count == 1 else "times"
        print(f'The word "{word_to_check}" appears {count} {time_word}.')

    print("\nThanks for using the Reading Ease Analyzer!")

if __name__ == '__main__':
    main()
