

#------------#
# Homework 3 #
# Week 3     #
#------------#
# Pre-processing . . .
# Run the code below to scrape BLS unemployment data
# Please email me if this returns an error - the link may have changed since I created this homework.

# Install rvest package and load
install.packages('rvest')
library(rvest)

# Set BLS url
url <- 'https://data.bls.gov/timeseries/LNS14000000'
webpage <- read_html(url)
tbls <- html_nodes(webpage, 'table')

# Scrape webpage for table
tbls_ls <- webpage %>% 
  html_nodes('table') %>%
  .[1:2] %>% 
  html_table(fill = TRUE)

# Set tabel as a dataframe
unRateDf <- tbls_ls[[2]]


# Question 1: Check the structure of unRateDf and confirm that you have a data frame.
str(unRateDf)
class(unRateDf)
is.data.frame(unRateDf)
head(unRateDf)
names(unRateDf)


unRateClean <- unRateDf

# Convert Year to numeric
unRateClean$Year <- as.numeric(unRateClean$Year)

# Convert month columns to numeric
month_cols <- c("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
unRateClean[month_cols] <- lapply(unRateClean[month_cols], function(x) as.numeric(as.character(x)))

str(unRateClean)


# Question 2: Subset unRateDf such that you only have rows between the years of 2015-2018.
unRate_2015_2018 <- subset(unRateClean, Year >= 2015 & Year <= 2018)
unRate_2015_2018


# Question 3: Subset unRateDf such that you only have rows with unemployment rates greater than 5.0 in the month of October.
unRate_oct_gt5 <- subset(unRateClean, Oct > 5.0)
unRate_oct_gt5


# Question 4: Using unRateDf, for each monthly column (Jan, Feb, Mar, ...) find the average of each column and store them in variables that follow
# this naming convention: <month abbreviation>_avg.  Example: jan_avg, feb_avg, mar_avg ... You should have 12 variables after this.
# Hint: You may need to use the mean() function, if you use this, make sure to add na.rm = TRUE as an argument to handle the columns with NAs.
# Example:  mean(df$column, na.rm = TRUE)
jan_avg <- mean(unRateClean$Jan, na.rm = TRUE)
feb_avg <- mean(unRateClean$Feb, na.rm = TRUE)
mar_avg <- mean(unRateClean$Mar, na.rm = TRUE)
apr_avg <- mean(unRateClean$Apr, na.rm = TRUE)
may_avg <- mean(unRateClean$May, na.rm = TRUE)
jun_avg <- mean(unRateClean$Jun, na.rm = TRUE)
jul_avg <- mean(unRateClean$Jul, na.rm = TRUE)
aug_avg <- mean(unRateClean$Aug, na.rm = TRUE)
sep_avg <- mean(unRateClean$Sep, na.rm = TRUE)
oct_avg <- mean(unRateClean$Oct, na.rm = TRUE)
nov_avg <- mean(unRateClean$Nov, na.rm = TRUE)
dec_avg <- mean(unRateClean$Dec, na.rm = TRUE)


# Question 5: Using unRateDf, take each column and store it as a separate vector in your environment.  You should end
# with 13 vectors.
year_vec <- unRateClean$Year
jan_vec  <- unRateClean$Jan
feb_vec  <- unRateClean$Feb
mar_vec  <- unRateClean$Mar
apr_vec  <- unRateClean$Apr
may_vec  <- unRateClean$May
jun_vec  <- unRateClean$Jun
jul_vec  <- unRateClean$Jul
aug_vec  <- unRateClean$Aug
sep_vec  <- unRateClean$Sep
oct_vec  <- unRateClean$Oct
nov_vec  <- unRateClean$Nov
dec_vec  <- unRateClean$Dec


# Question 6: Now, using all 13 vectors created from Q5, recreate the unRateDf from scratch as 'scratchUnRateDf' with
# custom column names that align with the original dataframe, unRateDf.
scratchUnRateDf <- data.frame(
  Year = year_vec,
  Jan  = jan_vec,
  Feb  = feb_vec,
  Mar  = mar_vec,
  Apr  = apr_vec,
  May  = may_vec,
  Jun  = jun_vec,
  Jul  = jul_vec,
  Aug  = aug_vec,
  Sep  = sep_vec,
  Oct  = oct_vec,
  Nov  = nov_vec,
  Dec  = dec_vec
)

str(scratchUnRateDf)
head(scratchUnRateDf)


