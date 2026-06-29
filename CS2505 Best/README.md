# CS2505 Best Projects - Computer Organization & C Programming

**Course**: CS2505 - Computer Organization & C Programming  
**Focus**: Low-level programming, memory management, bit manipulation, assembly concepts  
**Author**: Kalen Dacosta (kalendaco)

## 📋 Overview

These 5 projects demonstrate proficiency in C programming, memory management, file I/O, and low-level bit manipulation. The highlight is the prestigious Carnegie Mellon Data Lab, a well-known challenging assignment.

## 🎯 Projects

### 1. Project1 - File Search with Arrays
**Description**: Efficient file searching algorithm using arrays for pattern matching and data retrieval.

**Key Features**:
- Array-based data storage
- File I/O operations
- Search algorithm implementation
- Memory-efficient design

**Skills Demonstrated**:
- C file operations
- Array manipulation
- Algorithm design
- Memory management

---

### 2. Project2 - Comic Collection Management
**Description**: Dynamic memory management system for organizing and managing a comic book collection.

**Key Features**:
- Dynamic memory allocation (malloc/free)
- Linked data structures
- File persistence
- CRUD operations

**Skills Demonstrated**:
- Dynamic memory management
- Pointer manipulation
- File I/O
- Data structure design

---

### 3. Project4 - Word Unscrambler
**Description**: Binary data processing tool for unscrambling words using bitwise operations and file handling.

**Key Features**:
- Binary file processing
- Bit manipulation
- String manipulation
- Algorithm design

**Skills Demonstrated**:
- Binary file I/O
- Bit-level operations
- String algorithms
- Low-level data processing

---

### 4. **datalab-handout - Carnegie Mellon Data Lab ⭐**
**Description**: **Prestigious Carnegie Mellon Data Lab** - Advanced bit manipulation puzzles with strict operator constraints.

**Key Features**:
- **Famous CMU assignment** - highly recognized in industry
- Bit-level operations without standard operators
- Strict operator limits (only ! ~ & ^ | + << >>)
- Complex puzzles: fitsShort, anyOddBit, isAsciiDigit, bang
- Demonstrates deep understanding of computer systems

**Skills Demonstrated**:
- **Advanced bit manipulation**
- **Two's complement arithmetic**
- **Computer systems knowledge**
- **Creative problem-solving with constraints**

**Why This Impresses Recruiters**:
The CMU Data Lab is a well-known, challenging assignment that demonstrates:
- Deep understanding of low-level computer operations
- Ability to solve complex problems with constraints
- Computer systems knowledge beyond typical coursework
- Problem-solving creativity

**Example Puzzle Solution**:
```c
int bang(int x) {
    return ((x | (~x + 1)) >> 31) + 1; // Compute !x without using !
}
```

---

### 5. homework3 - Student Roster Management
**Description**: Dynamic memory management system for student roster with linked list implementation.

**Key Features**:
- Dynamic memory allocation
- Linked list data structure
- Student data management
- File persistence

**Skills Demonstrated**:
- Pointer manipulation
- Linked list implementation
- Memory management
- File I/O

## 🌟 Key Highlight: Carnegie Mellon Data Lab

The `datalab-handout` folder contains solutions to the famous Carnegie Mellon Data Lab, which is:
- **Recognized industry-wide** as a challenging assignment
- Demonstrates **advanced bit manipulation skills**
- Shows **computer systems proficiency**
- **Highly impressive to recruiters** in systems programming roles

## 🚀 How to Run

### C Projects (Project1, Project2, Project4, homework3):
```bash
gcc main.c -o project_name
./project_name
```

### Data Lab (datalab-handout):
```bash
make
./btest
```

## 📊 Project Statistics

- **Total Projects**: 5
- **Languages**: C
- **Key Skills**: Memory Management, Bit Manipulation, File I/O, Pointers
- **Highlight**: Carnegie Mellon Data Lab (industry-recognized assignment)

## 💡 Why This Portfolio Impresses Recruiters

1. **CMU Data Lab**: Shows advanced computer systems knowledge
2. **Memory Management**: Demonstrates understanding of low-level programming
3. **Bit Manipulation**: Essential for systems programming and embedded systems
4. **File I/O**: Practical data processing skills
