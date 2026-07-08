'''
Project 1 - Shape Calculator - Fall 2024  
Author: Kalen Dacosta (9064-78888)

Describe the program here.
    This program displays a prompt asking the user to 1 pick of 7 shapes
    Circle, Square, Rectangle, Triangle, Sphere, Cube, and Cylinder
    Once the user has picked a shape it will ask them for the shapes dimensions
    These dimension can be whatever numeric value the user would like
    Once given the dimensions calculation will be made to display the
    Correct outputs depending on the shape
    Radius, Area, Circumference, Length, Perimeter, Width, Volume, Surface Area, and Height
    These are all outputs that could be returned depending on what shape is chosen
    A quit button is also implemented to exit the page followed by a farewell message 
    

I have neither given or received unauthorized assistance on this assignment.
Signed:  Kalen Dacosta
'''


import math

def main():
    ''' Main function controls shape calculator
        prints the welcome message and loops the shapes 
        until the user quits
        Parameters: Nothing
        Returns: Nothing '''

    print("Welcome to the Shape Calculator!")
    while True:
        choice = get_shape_choice()
        if choice.lower() == 'q':
            break
        process_shape_choice(choice)
    print("\nThanks for using the Shape Calculator!")

def get_shape_choice():    
    ''' Prompts the user to choose a shape 
        Parameters: Nothing
        Returns: Nothing
        choice (str) - the users shape choice as a string '''

    print("\nChoose a shape from the following:")
    print("1. Circle\n2. Square\n3. Rectangle\n4. Triangle")
    print("5. Sphere\n6. Cube\n7. Cylinder")
    return input("Enter the number corresponding to your choice (or Q to quit): ")

def process_shape_choice(choice):
    ''' calls the correct processing function 
        based on the users selection
        Parameters: Nothing
            choice (str) - value that represents the shape chosen 
        Returns: Nothing '''

    if choice == '1':
        process_circle()
    elif choice == '2':
        process_square()
    elif choice == '3':
        process_rectangle()
    elif choice == '4':
        process_triangle()
    elif choice == '5':
        process_sphere()
    elif choice == '6':
        process_cube()
    elif choice == '7':
        process_cylinder()
    else:
        print("Invalid choice. Please choose a valid option.")

# Functions to calculate each shape

def process_circle():
    ''' asks for the radius computes and prints 
        the area and circumference
        Parameters: Nothing
        Returns: Nothing '''
 
    radius = float(input("Enter the radius of the circle: "))
    area = math.pi * radius ** 2
    circumference = 2 * math.pi * radius
    print(f"\nShape: Circle\nRadius: {radius:.2f}\nArea: {area:.2f}\nCircumference: {circumference:.2f}")

def process_square():
    ''' asks for the side length computes and prints 
        area and perimeter
        Parameters: Nothing
        Returns: Nothing '''
    side = float(input("Enter the side length of the square: "))
    area = side ** 2
    perimeter = 4 * side
    print(f"\nShape: Square\nSide Length: {side:.2f}\nArea: {area:.2f}\nPerimeter: {perimeter:.2f}")

def process_rectangle():
    ''' asks for the length and width
        computes and prints the area and perimeter
        Parameters: Nothing
        Returns: Nothing '''

    length = float(input("Enter the length of the rectangle: "))
    width = float(input("Enter the width of the rectangle: "))
    area = length * width
    perimeter = 2 * (length + width)
    print(f"\nShape: Rectangle\nLength: {length:.2f}\nWidth: {width:.2f}\nArea: {area:.2f}\nPerimeter: {perimeter:.2f}")

def process_triangle():
    ''' asks for the base length and height 
        computes and prints the area and perimeter
        (assumes an equilateral triangle)
        Parameters: Nothing
        Returns: Nothing '''

    base = float(input("Enter the base length of the triangle: "))
    height = float(input("Enter the height of the triangle: "))
    area = 0.5 * base * height
    perimeter = 3 * base  # Assuming equilateral for simplicity
    print(f"\nShape: Triangle\nBase Length: {base:.2f}\nHeight: {height:.2f}\nArea: {area:.2f}\nPerimeter (Equilateral): {perimeter:.2f}")

def process_sphere():
    ''' asks for the radius 
        computes and prints the volume and surface area
        Parameters: Nothing
        Returns: Nothing'''

    radius = float(input("Enter the radius of the sphere: "))
    volume = (4/3) * math.pi * radius ** 3
    surface_area = 4 * math.pi * radius ** 2
    print(f"\nShape: Sphere\nRadius: {radius:.2f}\nVolume: {volume:.2f}\nSurface Area: {surface_area:.2f}")

def process_cube():
    ''' asks for the side length
        computes and prints the volume and surface area
        Parameters: Nothing
        Returns: Nothing '''

    side = float(input("Enter the side length of the cube: "))
    volume = side ** 3
    surface_area = 6 * side ** 2
    print(f"\nShape: Cube\nSide Length: {side:.2f}\nVolume: {volume:.2f}\nSurface Area: {surface_area:.2f}")

def process_cylinder():
    ''' asks for the radius and height 
        computes and prints the volume and surface area
        Parameters: Nothing
        Returns: Nothing '''

    radius = float(input("Enter the radius of the cylinder: "))
    height = float(input("Enter the height of the cylinder: "))
    volume = math.pi * radius ** 2 * height
    surface_area = 2 * math.pi * radius * (radius + height)
    print(f"\nShape: Cylinder\nRadius: {radius:.2f}\nHeight: {height:.2f}\nVolume: {volume:.2f}\nSurface Area: {surface_area:.2f}")

# Call main like this to keep Web-CAT happy
if __name__ == '__main__':
    main()
