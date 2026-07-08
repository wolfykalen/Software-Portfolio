
#----------#
# Week 5   #
# Homework #
#----------#

# Question 1: Read in the bikepghmembers.csv data set from the week 5 hw data folder and assess
# the structure.  Check the column names to see if they need to be reformatted, if so, carry 
# out reformatting process.

# Run the code below once you have set your working directory so that the start date is configured properly
df <- read.csv("bikepghmembers.csv")

df$Start.Date <- as.POSIXct(df$Start.Date,
                            "%m/%d/%Y %H:%M:%S %p",
                            origin = '1970-01-01',
                            tz = "America/Los_Angeles")

str(df$Start.Date)

# Make names clean and consistent
colnames(df) <- make.names(colnames(df))

colnames(df)


# Question 2: Use the grepl function to search the column names for all column names that have the word 'Safety' in it.
# Print those column names out to the console.
safety_cols <- colnames(df)[grepl("Safety", colnames(df))]
safety_cols


# Question 3: Check the unique values of each column from Q2, you may notice something obviously off about 
# one of the fields.  Which field looks to be off?
for(col in safety_cols){
  cat("\nColumn:", col, "\n")
  print(unique(df[[col]]))
}


# Question 4: Use the gsub function to remove all of the unwanted characters from the column.  Convert the cleaned
# column to numeric.
# Example (replace Safety.Column.Name with actual column)
df$Safety.Column.Name <- gsub("[^0-9.-]", "", df$Safety.Column.Name)

# Convert to numeric
df$Safety.Column.Name <- as.numeric(df$Safety.Column.Name)

# Check
str(df$Safety.Column.Name)


# Question 5: Check unique values of Response ID field, do some of the values show up more than once?
unique_ids <- unique(df$Response.ID)
length(unique_ids)
length(df$Response.ID)

df$Response.ID[duplicated(df$Response.ID)]
# Question 6: Convert the values within the AdvocacyIssues columns to a numeric range.  Have the range
# run from -2 to 2: -2: Strongly Disagree, -1: Disagree:, 0: Neutral, etc. 
# Do the same with the FeelingsProvingGround column.
likert_map <- c(
  "Strongly Disagree" = -2,
  "Disagree" = -1,
  "Neutral" = 0,
  "Agree" = 1,
  "Strongly Agree" = 2
)
df$AdvocacyIssues <- likert_map[df$AdvocacyIssues]
df$FeelingsProvingGround <- likert_map[df$FeelingsProvingGround]

# Convert to numeric (important)
df$AdvocacyIssues <- as.numeric(df$AdvocacyIssues)
df$FeelingsProvingGround <- as.numeric(df$FeelingsProvingGround)

unique(df$AdvocacyIssues)
unique(df$FeelingsProvingGround)
# Question 7: Now take the average of the AdvocacyIssues and FeelingsProvingGround column to get 
# the average response in a quantitative format.  You may need to use na.rm = TRUE as an argument.

df$AverageResponse <- rowMeans(
  df[, c("AdvocacyIssues", "FeelingsProvingGround")],
  na.rm = TRUE
)

summary(df$AverageResponse)