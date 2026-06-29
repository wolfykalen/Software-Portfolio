

#-----------#
# Exam 1    #
# ECON 4614 #
#-----------#

#-----------#
# Overview: #
#-----------#
# This exam encompasses the first half of the class thus far, up through material from
# week 5.  You will be tested on your general understanding
# of R at a basic level.  Please answer all questions on this R script.  You may leave
# explanations throughout your code to explain your methodology and logic behind your 
# answers.

#-----------#
# Logistics #
#-----------#
# The exam is open book, open material, open source - you are not allowed to work with other students.
# If you reference an online post for help, please cite the direct link to the StackOverflow post or 
# other blog/website.  *** Please also explain what the code does that you got from the internet ***  
# If this is not done, you will receive no credit for the question.

#--------------------------------#
# Grading: 100 out of 100 points #
#--------------------------------#
# For questions that require you to write code, you will be graded on the ability for your 
# code to run.  If your code does not output the correct answer when I run it, you will
# lose points from that respective question.  Partial credit will be given for code that 
# is well documented with logic that is correct, but not applied correctly.  No credit will
# be given when both of the above scenarios are not met.  For any questions that require R,
# you must submit your code for credit - you are not allowed to only post the answer.

# For questions in the first section (theoretical), you will be asked to explain how R 
# works in certain situations - this is to be done as comments.  Correct answers with 
# explanations will be given full credit, incorrect answers with the correct logic will 
# receive partial credit, and no credit will be assigned when neither of the above are met.

#-----------------------------------------------------#
# The exam will be curved, to what extent is unknown. # 
#-----------------------------------------------------#

#-----------#
# Sections: # 
#-----------#
#-------------------------------------------------------------------------------------------------#
# 1) Theoretical / Basic Application:  Total Points ( 15 )                                        #
#     - This section will test your knowledge of the RStudio interface and general                #
#       experience using R.  You will be asked to answer hypothetical questions.                  #
#     - It is not required to write code for these answers, though you are always                 #
#       welcome to leave your code on the R script that you used to test these questions.         #
#     - All answers should be commented for their respective quesitons and explained in           #
#       detail to demonstrate your knowledge of how R works in certain scenarios.                 #
#-------------------------------------------------------------------------------------------------#
# 2) Application of Functions:  Total Points ( 40 )                                               #
#     - This section aims to test your ability to apply basic functions in R to                   #
#       different data formats (e.g. variables, vectors, data frames, etc.).                      #
#     - All answers should be coded in R with the final output of the answer.                     #
#-------------------------------------------------------------------------------------------------#
# 3) Mini Activity + Learning new functions:  Total Points ( 45 )                                 #
#     - This section will encompass the course so far as a whole . . .                            #
#     - Beginning with a goal, your task is to begin with data from a dataset and                 #
#       go through the process on your own to:                                                    #
#         (a) Understand then clean the data.                                                      #
#         (b) Begin to restructure your data frames, possibly creating new data sets              #
#             if needed.                                                                          #
#         (c) Shape your data into analysis ready data.                                           #
#         (d) Perform very basic analysis on the the data (e.g., counting, percent changes,       #
#             averages and more).                                                                 #
#         (e) Output basic visuals                                                                #
#-------------------------------------------------------------------------------------------------#

# Honor code signature:
name <- "Kalen Dacosta"
curDateTime <- "3/5/26"
signature <- paste0("I, ", Kalen Dacosta, " understand that all information described above is final and that I will not copy other's work or share answers. Time: ", curDateTime)

#----------------#
# Begin the Exam #
#----------------#

# Set your working directory to a folder that holds all of the data required
# for this exam:
setwd("C:/Users/kalen/Downloads")

#---------------------------------------------------------#
# Section 1: Theoretical Coding in R | Basic Application  #
#---------------------------------------------------------#

#--------------------#
# Question 1 | 3pts: #
#--------------------#
# What are two reasons for why the following code throws an error:
15 minus "six"

answerQ1 <- " Reason 1: minus is not valid R syntax for subtraction. R uses the operator "-". 
six is a character string and cannot be subtracted from a numeric value."
print(answerQ1)

#--------------------#
# Question 2 | 3pts: #
#--------------------#
# There are three parts to this quesiton that will be based off of the code below.
testVectorQ2A <- runif(1000, 0, 1)
testVectorQ2A[2]

#   (a | 1pt) Why does the second line of the code throw an error?
answerQ2A <- "It errors because testVectorQ2A is a vector, which only has one dimension, but the code indexes it like a dataframe or matrix by using a comma."
print(answerQ2A)

#   (b | 1pt) Please correct the second line of code such that the 2nd value in the vector is executed.

#   (c | 1pt) What is the difference between the dimensions of a vector and a dataframe/array?
answerQ2C <- "A vector is one dimensional and uses one index position, while a dataframe or array has two or more dimensions and is accessed by rows and columns."
print(answerQ2C)

#--------------------#
# Question 3 | 3pts: #
#--------------------#
# There are three parts to this question that will be based off of the three subsets below.
# Subset A
df <- mtcars
subdf <- df[df$mpg > 22,]
subdf <- subdf[subdf$mpg <= 26,]
subdf <- subdf[subdf$wt > 3,]

# Subset B
df <- mtcars
subdf <- df[df$mpg > 22 & df$mpg <= 26 & df$wt > 3,]

# Subset C
df <- mtcars
subdf <- df[df$mpg > 22,]
subdf <- df[df$mpg <= 26,]
subdf <- subdf[subdf$wt > 3,]

#   (a | 1pt) Which of the subsets achieve the same result AND why?
answerQ3A <- "Subset A and Subset B produce the same result because both apply all three conditions to the same rows. Subset A does it step by step and Subset B does it in one line."
print(answerQ3A)

#   (b | 1pt) Please explain what each subset is doing.
answerQ3B <- "Subset A repeatedly filters the already filtered dataframe. Subset B filters the original dataframe one time using all three conditions together. Subset C starts filtering, but then resets to the original dataframe on the second line, so it does not preserve the first filter."
print(answerQ3B)

#   (c | 1pt) Explain what would happen and why if you changed all '&' in 'Subset B' to '|'
answerQ3C <- "If '&' is changed to '|', the subset would return rows meeting any one of the conditions instead of all three, so the result would contain many more rows."
print(answerQ3C)

#--------------------#
# Question 4 | 3pts: #
#--------------------#
# There are two parts to this question about data frames.

#   (a | 1.5pts) If there are two vectors, one with 15 values, and the second with 20 values, 
#                why won't R allow you to cbind them together to create a data frame while using
#                as.data.frame()?
answerQ4A <- "R data frames require columns to have the same number of rows. A vector with 15 values and a vector with 20 values do not align row by row, so they cannot be combined cleanly into a dataframe."
print(answerQ4A)

#   (b | 1.5pts) What are two ways to access a column from a data frame?
answerQ4B <- "Two ways are using the $ operator such as df$columnName and using brackets such as df[, 'columnName'] or df[['columnName']]."
print(answerQ4B)

#--------------------#
# Question 5 | 3pts: #
#--------------------#
# There are two parts to this question.

#   (a | 1.5pts) Say you have a vector of 10 values called vector5Q. If calculating the length of 
#                of vector5Q outputs a different number than the length of the unique values of
#                vector5Q, what does this mean?
answerQ5A <- "It means there are duplicate values in the vector, because unique() removes repeated values while length() counts everything."
print(answerQ5A)

#   (b | 1.5pts) Please explain in detail what a UniqueID/Key/etc. does when working with multiple
#                datasets within a data base.  Why is it important that you preserve the status of 
#                this field and keep it unaltered?
answerQ5B <- "A UniqueID or key is a field that uniquely identifies each record and allows multiple datasets to be matched correctly. It is important to keep it unchanged because altering it can cause incorrect joins, duplicates, or lost observations."
print(answerQ5B)


#---------------------------------------#
# Section 2: Applications of Functions  #
#---------------------------------------#

#--------------------#
# Question 1 | 4pts: #
#--------------------#

df <- mtcars
df$cars <- rownames(df)
df[df$cars == c("Merc 230", "Fiat 128", "Camaro Z28"),]

# Please correct the above code such that the dataframe is subsetted to the three cars within the c() function.
# Leave the subset above unaltered, enter your code and answer below:
df[df$cars %in% c("Merc 230", "Fiat 128", "Camaro Z28"), ]


#--------------------#
# Question 2 | 4pts: #
#--------------------#

# Please read in the uber_jun2014.txt data for this question. (This includes July 1st which is okay)
# Create a new column called WeekdayString that is the text version of the corresponding 
# weekday value.  Key: 1 = Sunday | 2 = Monday | 3 = Tuesday | 4 = Wednesday | 5 = Thursday
# 6 = Friday | 7 = Saturday.

uber$WeekdayString <- NA
# Enter code below:
uber <- read.csv("uber_jun2014.txt")

uber$Weekday <- as.numeric(format(as.POSIXct(uber$`Date/Time`, format="%m/%d/%Y %H:%M:%S"), "%w")) + 1

uber$WeekdayString[uber$Weekday == 1] <- "Sunday"
uber$WeekdayString[uber$Weekday == 2] <- "Monday"
uber$WeekdayString[uber$Weekday == 3] <- "Tuesday"
uber$WeekdayString[uber$Weekday == 4] <- "Wednesday"
uber$WeekdayString[uber$Weekday == 5] <- "Thursday"
uber$WeekdayString[uber$Weekday == 6] <- "Friday"
uber$WeekdayString[uber$Weekday == 7] <- "Saturday"


#--------------------#
# Question 3 | 4pts: #
#--------------------#

# Using the uber data from Q2, execute the following four steps:
#   (a | 1pt) - Change the name of the column 'Date/Time' to something more reflective of the contents:
#   (b | 1pt) - Format your new column from part (a) to be of the Date format.
#   (c | 1pt) - Plot the price column against the new column from part (a).
#   (d | 1pt) - Explain in text what the plot suggests about price fluctuations across the standard week in June.

# Part A - Enter code below:
names(uber)[names(uber) == "Date/Time"] <- "TripDateTime"


# Part B - Enter code below:
uber$TripDate <- as.Date(uber$TripDateTime)


# Part C - Enter code below:
plot(uber$TripDate, uber$Price,
     main = "Uber Price by Date",
     xlab = "Date",
     ylab = "Price",
     pch = 16)

# Part D - Enter text below:
answerS2Q3D <- "The plot suggests that Uber prices fluctuate across days in June, with some days having higher prices than others, likely due to changing demand patterns over the week."
print(answerS2Q3D)


#--------------------#
# Question 4 | 4pts: #
#--------------------#

# Using the uber data from Q2, write 7 subsets that subset each of the 7 unique values of the WeekdayString 
# column and prints out the range of the prices and durations for each weekday - Sunday, Monday, Tuesday, etc.
# Make sure to specify the price and duration print statements, ex: "Price - Sunday: range" & "Duration - Sunday: range"
subSunday <- uber[uber$WeekdayString == "Sunday", ]
print(paste("Price - Sunday:", paste(range(subSunday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Sunday:", paste(range(subSunday$Duration, na.rm = TRUE), collapse = " to ")))

subMonday <- uber[uber$WeekdayString == "Monday", ]
print(paste("Price - Monday:", paste(range(subMonday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Monday:", paste(range(subMonday$Duration, na.rm = TRUE), collapse = " to ")))

subTuesday <- uber[uber$WeekdayString == "Tuesday", ]
print(paste("Price - Tuesday:", paste(range(subTuesday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Tuesday:", paste(range(subTuesday$Duration, na.rm = TRUE), collapse = " to ")))

subWednesday <- uber[uber$WeekdayString == "Wednesday", ]
print(paste("Price - Wednesday:", paste(range(subWednesday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Wednesday:", paste(range(subWednesday$Duration, na.rm = TRUE), collapse = " to ")))

subThursday <- uber[uber$WeekdayString == "Thursday", ]
print(paste("Price - Thursday:", paste(range(subThursday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Thursday:", paste(range(subThursday$Duration, na.rm = TRUE), collapse = " to ")))

subFriday <- uber[uber$WeekdayString == "Friday", ]
print(paste("Price - Friday:", paste(range(subFriday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Friday:", paste(range(subFriday$Duration, na.rm = TRUE), collapse = " to ")))

subSaturday <- uber[uber$WeekdayString == "Saturday", ]
print(paste("Price - Saturday:", paste(range(subSaturday$Price, na.rm = TRUE), collapse = " to ")))
print(paste("Duration - Saturday:", paste(range(subSaturday$Duration, na.rm = TRUE), collapse = " to ")))


#--------------------#
# Question 5 | 4pts: #
#--------------------#

# Using the uber data set from Q2, subset the data into a two new data frames based on:
# Subset 1 - subUber6_9AM: The hours between 6AM - 9AM.
# Subset 2 - subUber6_9PM: The hours between 6PM - 9PM.
# Print the average price and duration of the two subsets and the difference between the two.
uber$Hour <- as.numeric(format(uber$TripDateTime, "%H"))

subUber6_9AM <- uber[uber$Hour >= 6 & uber$Hour < 9, ]
subUber6_9PM <- uber[uber$Hour >= 18 & uber$Hour < 21, ]

avgPriceAM <- mean(subUber6_9AM$Price, na.rm = TRUE)
avgDurationAM <- mean(subUber6_9AM$Duration, na.rm = TRUE)

avgPricePM <- mean(subUber6_9PM$Price, na.rm = TRUE)
avgDurationPM <- mean(subUber6_9PM$Duration, na.rm = TRUE)

print(paste("Average Price 6AM-9AM:", avgPriceAM))
print(paste("Average Duration 6AM-9AM:", avgDurationAM))
print(paste("Average Price 6PM-9PM:", avgPricePM))
print(paste("Average Duration 6PM-9PM:", avgDurationPM))
print(paste("Difference in Average Price (PM - AM):", avgPricePM - avgPriceAM))
print(paste("Difference in Average Duration (PM - AM):", avgDurationPM - avgDurationAM))

# Extra credit ( 2pts ):
extraQ5 <- "If the PM averages are higher than the AM averages, the data may suggest evening trips are more expensive or longer because of heavier traffic or stronger demand after work hours."
print(extraQ5)


#--------------------#
# Question 6 | 4pts: #
#--------------------#

uncleanedVector <- c("$2,200.86M", "$90.50T", "$1,532.87B", "$11,727.39M")
# Using the vector above, clean all of the unwanted values from the data, change the structure to numeric,
# then adjust the vector for 15% inflation.  Please be mindful of the magnitude of each value.
suffix <- gsub(".*([MTB])$", "\\1", uncleanedVector)
numericPart <- gsub("[$,MTB]", "", uncleanedVector)
numericPart <- as.numeric(numericPart)

cleanedNumeric <- numericPart
cleanedNumeric[suffix == "M"] <- cleanedNumeric[suffix == "M"] * 1e6
cleanedNumeric[suffix == "B"] <- cleanedNumeric[suffix == "B"] * 1e9
cleanedNumeric[suffix == "T"] <- cleanedNumeric[suffix == "T"] * 1e12

inflationAdjusted <- cleanedNumeric * 1.15

print(cleanedNumeric)
print(inflationAdjusted)

#--------------------#
# Question 7 | 4pts: #
#--------------------#

df <- mtcars
df$cars <- rownames(df)
df$cars[grepl("Merc", df$cars)]
df$cars[grepl("Toyota", df$cars)]
# Using the code above, combine the last two lines of code into one such that the output prints
# all values in the 'cars' column that have 'Merc' and 'Toyota' in it.
df$cars[grepl("Merc|Toyota", df$cars)]


#---------------------#
# Question 8 | 12pts: #
#---------------------#
mtcarsDf <- mtcars[,1:7]

#   (a | 8pts) Using the data above and partially done if statement below, please update the code below such that:
#               - the empty parentheses after the if (on the 5th line of code) is populated with a conditional statement that...
#                   + Tests the r squared value is greater than 0.70
#   (b | 4pts): Explain in text what is happening in this code.
#   (c | 4pts EXTRA CREDIT):  Please update the for loop to also store the column name within each iteration
#                             so that you keep track of what the [x] value is in each model.  Have this such
#                             that your print statement also prints out the corresponding column name.
for (i in 2:ncol(mtcarsDf)){
  curXName <- names(mtcarsDf)[i]
  curModel <- lm(mtcarsDf$mpg ~ mtcarsDf[,i], data = mtcarsDf)
  curSummary <- summary(curModel)
  
  if (curSummary$r.squared > 0.70){
    print(paste0("Column: ", curXName, " | R-Squared: ", round(curSummary$r.squared, digits = 2)))
  }
}

answerS2Q8B <- "This loop runs a separate linear regression using mpg as the dependent variable and each remaining column as the independent variable. It stores the model summary and prints the column name and r-squared only when the model's r-squared is greater than 0.70."
print(answerS2Q8B)

#---------------------------#
# Section 3: Mini-activity  #
#---------------------------#

#---------------------------------------------#
# Please read in the following two data sets: #
#   - ab_df.csv                               #
#---------------------------------------------#

# After reading in the data, complete/answer the following items in R:
# You must complete each action item in your R script for me to be able to review your work for credit.
# Hint: The dplyr package may help here.  Look at the dplyr group_by function if you wish to try it out.
#   - Create multiple new data frames based on yearly profit ***in 2025 dollars*** for each of the below:
#       - Total Profit of each individual product. (5)
#       - Total Profit of each location subdivision - please breakout by city rather than subdivision code. (5)
#       - Total Profit of each state. (5)
#       - Total Profit of each product type. (5)
#           - This new column needs to be derived in R.  Please do not do this in Excel or outside of R.
#           - Ex: Soup, Sandwich, etc.
#   - Which product produces the most profit in each state? (5)
#   - Create a profit-margins data frame of each product. (10) - Profit Margins should be price - total cost of each individual product.
#       - Based on this data frame, which product has the highest profit margins? (5)
#       - Based on this data frame, which product has the lowest profit margins? (5)
#   - Extra credit ( 10pts ): Create a spatial graph of each individual ABP location that has circle markers
#     with different sizes and shades of green to show their respective overall profit and total products sold.


library(dplyr)

ab_df <- read.csv("abp_df.csv", stringsAsFactors = FALSE)

#------------------#
# Clean the data   #
#------------------#

ab_df$Total.Cost <- as.numeric(gsub("[$, ]", "", ab_df$Total.Cost))
ab_df$Fixed.Cost <- as.numeric(gsub("[$, ]", "", ab_df$Fixed.Cost))
ab_df$Variable.Cost <- as.numeric(gsub("[$, ]", "", ab_df$Variable.Cost))
ab_df$Price <- as.numeric(gsub("[$, ]", "", ab_df$Price))
ab_df$Quantity.Sold <- as.numeric(gsub(",", "", ab_df$Quantity.Sold))
ab_df$Date <- as.numeric(ab_df$Date)

# Inflate 2015 profit into 2025 dollars
# 3% annual inflation assumption is used.
inflationFactor <- (1.03)^10

# Create core variables
ab_df$ProfitPerUnit <- ab_df$Price - ab_df$Total.Cost
ab_df$TotalProfit <- ab_df$ProfitPerUnit * ab_df$Quantity.Sold
ab_df$TotalProfit2025 <- ab_df$TotalProfit * inflationFactor

#-----------------------------------#
# Derive Product Type in R          #
#-----------------------------------#
ab_df$ProductType <- ifelse(grepl("Soup", ab_df$Product), "Soup",
  ifelse(grepl("Salad", ab_df$Product), "Salad",
    ifelse(grepl("Sandwich|Wrap|Bagel", ab_df$Product), "Sandwich",
      "Other")))

#-----------------------------------#
# Total Profit of each product      #
#-----------------------------------#
profit_by_product <- ab_df %>%
  group_by(Product) %>%
  summarize(TotalProfit2025 = sum(TotalProfit2025, na.rm = TRUE)) %>%
  arrange(desc(TotalProfit2025))

print(profit_by_product)

#-----------------------------------#
# Total Profit of each city         #
#-----------------------------------#
profit_by_city <- ab_df %>%
  group_by(Location) %>%
  summarize(TotalProfit2025 = sum(TotalProfit2025, na.rm = TRUE)) %>%
  arrange(desc(TotalProfit2025))

print(profit_by_city)

#-----------------------------------#
# Total Profit of each state        #
#-----------------------------------#
profit_by_state <- ab_df %>%
  group_by(State) %>%
  summarize(TotalProfit2025 = sum(TotalProfit2025, na.rm = TRUE)) %>%
  arrange(desc(TotalProfit2025))

print(profit_by_state)

#-----------------------------------#
# Total Profit of each product type #
#-----------------------------------#
profit_by_product_type <- ab_df %>%
  group_by(ProductType) %>%
  summarize(TotalProfit2025 = sum(TotalProfit2025, na.rm = TRUE)) %>%
  arrange(desc(TotalProfit2025))

print(profit_by_product_type)

#--------------------------------------------------#
# Which product produces the most profit in state? #
#--------------------------------------------------#
top_product_each_state <- ab_df %>%
  group_by(State, Product) %>%
  summarize(TotalProfit2025 = sum(TotalProfit2025, na.rm = TRUE), .groups = "drop") %>%
  group_by(State) %>%
  slice_max(order_by = TotalProfit2025, n = 1, with_ties = FALSE)

print(top_product_each_state)

#-----------------------------------#
# Profit margins data frame         #
# Profit margin = price - total cost#
#-----------------------------------#
profit_margins_df <- ab_df %>%
  group_by(Product) %>%
  summarize(
    AvgUnitProfitMargin = mean(ProfitPerUnit, na.rm = TRUE),
    TotalProfitMargin = sum(TotalProfit, na.rm = TRUE)
  ) %>%
  arrange(desc(AvgUnitProfitMargin))

print(profit_margins_df)

#-----------------------------------#
# Highest profit margin product     #
#-----------------------------------#
highest_profit_margin <- profit_margins_df %>%
  slice_max(order_by = AvgUnitProfitMargin, n = 1, with_ties = FALSE)

print(highest_profit_margin)

#-----------------------------------#
# Lowest profit margin product      #
#-----------------------------------#
lowest_profit_margin <- profit_margins_df %>%
  slice_min(order_by = AvgUnitProfitMargin, n = 1, with_ties = FALSE)

print(lowest_profit_margin)

#-----------------------------------#
# Extra credit: spatial graph       #
#-----------------------------------#
location_profit <- ab_df %>%
  group_by(Location, State, Lat, Long) %>%
  summarize(
    TotalProfit2025 = sum(TotalProfit2025, na.rm = TRUE),
    TotalProductsSold = sum(Quantity.Sold, na.rm = TRUE),
    .groups = "drop"
  )

plot(location_profit$Long, location_profit$Lat,
     cex = location_profit$TotalProductsSold / max(location_profit$TotalProductsSold) * 3,
     col = rgb(0, location_profit$TotalProfit2025 / max(location_profit$TotalProfit2025), 0, 0.6),
     pch = 16,
     xlab = "Longitude",
     ylab = "Latitude",
     main = "ABP Locations by Profit and Products Sold")

text(location_profit$Long, location_profit$Lat,
     labels = location_profit$Location,
     pos = 4,
     cex = 0.7)
