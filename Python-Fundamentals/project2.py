'''
Project 2 - Home Sales - Fall 2024
Author: Kalen Dacosta, [kalendaco]

This program analyzes real estate sales data from a file, calculating the total number of sales,
total amount, average sale, largest sale, and smallest sale.

I have neither given nor received unauthorized assistance on this assignment.
Signed: Kalen Dacosta
'''

def read_data(file_name):
    ''' Reads the file sales data and returns a list of sales [first_name, last_name, sale_price].
        Parameters:
            file_name (str): the name of the processing file.
        Returns:
            list: list of lists containing first name, last name, and sale price.
    '''
    sales = []
    with open(file_name, 'r') as file:
        for line in file:
            last, first, price = line.split()
            sales.append([first, last, int(price)])
    return sales

def compute_sum(sales_list):
    ''' Computes the sum of all sales.
        Parameters:
            sales_list (list): list of sales [first_name, last_name, sale_price].
        Returns:
            int: the sum of all sales.
    '''
    total = 0
    for sale in sales_list:
        total += sale[2]
    return total

def compute_avg(sales_list):
    ''' Computes the average sale price.
        Parameters:
            sales_list (list): list of sales.
        Returns:
            int: the integer average of sales.
    '''
    total = compute_sum(sales_list)
    return total // len(sales_list) if sales_list else 0

def get_largest_sale(sales_list):
    ''' Finds the largest sale in the sales list.
        Parameters:
            sales_list (list): list of sales.
        Returns:
            list: the largest sale [first_name, last_name, sale_price].
    '''
    largest = sales_list[0]
    for sale in sales_list[1:]:
        if sale[2] > largest[2]:
            largest = sale
    return largest

def get_smallest_sale(sales_list):
    ''' Finds the smallest sale in the sales list.
        Parameters:
            sales_list (list): list of sales.
        Returns:
            list: the smallest sale [first_name, last_name, sale_price].
    '''
    smallest = sales_list[0]
    for sale in sales_list[1:]:
        if sale[2] < smallest[2]:
            smallest = sale
    return smallest

def main():
    ''' Main program to read file process sales data and print results. '''
    print("Let's analyze those sales!\n")
    file_name = input("Enter the name of the file to process? ")
    sales_list = read_data(file_name)

    print(f"\nNumber of sales: {len(sales_list)}")
    print(f"Total: {compute_sum(sales_list)}")
    print(f"Average: {compute_avg(sales_list)}")

    largest = get_largest_sale(sales_list)
    smallest = get_smallest_sale(sales_list)
    print(f"Largest sale: {largest[1]} {largest[0]} {largest[2]}")
    print(f"Smallest sale: {smallest[1]} {smallest[0]} {smallest[2]}\n")

    print("Now go sell some more houses!")

if __name__ == '__main__':
    main()
