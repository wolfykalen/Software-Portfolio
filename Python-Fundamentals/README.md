# Python Class Best - Python Programming Fundamentals

**Course**: Python Programming  
**Focus**: Python fundamentals, functions, data processing, algorithms  
**Author**: Kalen Dacosta (kalendaco)

## 📋 Overview

These 3 projects demonstrate proficiency in Python programming fundamentals, including functions, file I/O, data processing, and algorithm implementation. Projects show clean code structure and practical problem-solving.

## 🎯 Projects

### 1. project1.py - Shape Calculator
**Description**: Interactive calculator for 7 different 2D and 3D shapes with area, perimeter, volume, and surface area calculations.

**Key Features**:
- **7 shapes**: Circle, Square, Rectangle, Triangle, Sphere, Cube, Cylinder
- **Interactive menu system**: User-friendly interface
- **Mathematical calculations**: Using math module (pi, powers)
- **Input validation**: Numeric input handling
- **Modular design**: Separate functions for each shape

**Skills Demonstrated**:
- Function definition and usage
- Mathematical operations
- User input/output
- Modular programming
- Error handling

**Code Example**:
```python
def process_circle():
    radius = float(input("Enter the radius of the circle: "))
    area = math.pi * radius ** 2
    circumference = 2 * math.pi * radius
    print(f"Area: {area:.2f}, Circumference: {circumference:.2f}")
```

---

### 2. project2.py - Home Sales Data Analysis
**Description**: Real estate sales data analyzer that reads from files and calculates statistics (total, average, min, max).

**Key Features**:
- **File I/O**: Reading sales data from files
- **Data parsing**: Parsing name and price from text files
- **Statistical calculations**: Total, average, min, max
- **Data structures**: Lists for data storage
- **Formatted output**: Clean result presentation

**Skills Demonstrated**:
- File reading and processing
- Data parsing and validation
- Statistical calculations
- List manipulation
- String formatting

**Code Example**:
```python
def read_data(file_name):
    sales = []
    with open(file_name, 'r') as file:
        for line in file:
            last, first, price = line.split()
            sales.append([first, last, int(price)])
    return sales
```

---

### 3. project3.py - Reading Ease Analyzer
**Description**: Text analysis tool that calculates readability metrics including Flesch Reading Ease score and Flesch-Kincaid Grade Level.

**Key Features**:
- **File I/O**: Reading text files
- **Text cleaning**: Removing punctuation, converting to lowercase
- **Syllable counting**: Algorithm to estimate syllables in words
- **Word frequency analysis**: Dictionary for word counts
- **Readability metrics**: Flesch Reading Ease and Grade Level calculations
- **Interactive word lookup**: Check frequency of specific words

**Skills Demonstrated**:
- Text processing and manipulation
- Algorithm design (syllable counting)
- Dictionary usage for frequency analysis
- Statistical calculations
- File processing
- String manipulation

**Code Example**:
```python
def count_syllables(word):
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
    return max(syllables, 1)
```

## 🛠️ Technical Skills

### Python Concepts
- **Functions**: Definition, parameters, return values
- **File I/O**: Reading and writing files
- **Data Structures**: Lists, dictionaries
- **String Manipulation**: Cleaning, parsing, formatting
- **Mathematical Operations**: Using math module
- **Algorithms**: Syllable counting, statistical calculations
- **User Interaction**: Input/output, menu systems

### Libraries Used
- **math**: Mathematical operations (pi, powers)
- **Standard library**: File I/O, string operations

## 🚀 How to Run

Each Python script can be run from command line:
```bash
python project1.py
python project2.py
python project3.py
```

Or in Python IDE (PyCharm, VS Code, IDLE):
1. Open the .py file
2. Run the script
3. Follow on-screen prompts

## 📊 Project Statistics

- **Total Projects**: 3
- **Lines of Code**: 400+
- **Key Concepts**: Functions, File I/O, Data Structures, Algorithms
- **Difficulty Level**: Beginner to Intermediate

## 💡 Why This Portfolio 

1. **Clean Code**: Well-structured, readable code with functions
2. **Practical Applications**: Real-world problems (calculators, data analysis)
3. **File Processing**: Demonstrates ability to work with external data
4. **Algorithm Design**: Custom algorithms (syllable counting)
5. **User Interaction**: Interactive programs with good UX
6. **Mathematical Skills**: Statistical calculations and geometry
