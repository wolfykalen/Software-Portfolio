
#=========================#
# Spring 2026: Final Exam #
#=========================#

library(dplyr)
library(ggplot2)

df <- read.csv("C:/Users/kalen/Downloads/merged-exam-rps-data.csv", stringsAsFactors = FALSE)
#-----------#
# Overview: #
#-----------#
# This final exam encompasses the course in its entirety.  You will be tested on your 
# general understanding of R and how to tackle real world problems.  Please answer all 
# questions on this R script.  You may leave explanations throughout your code to explain 
# your methodology and logic behind your answers. 

#-----------#
# Logistics #
#-----------#
# The exam is open book, open material, open source - you are not allowed to work with other students.
# If you reference an online post for help, please cite the direct link to the StackOverflow post or 
# other blog/website.  *** Please also explain what the code does that you got from the internet ***  
# If this is not done, you will receive no credit for the question.

# Honor code signature:
name <- "Kalen Dacosta"
curDateTime <- "5/6/2026"
signature <- paste0("I, ", name , " understand that all information described above is final and that I will not copy other's work or share answers. Time: ", curDateTime)



# [15 points]
# Question 1: Data Cleaning
# Please clean, standardize, convert, and create any fields that you need to 
# prep your dataset for the questions downstream.

# Buyer/Seller names
df$BuyerName <- toupper(trimws(df$BuyerName))
df$SellerName <- toupper(trimws(df$SellerName))

df$BuyerName <- gsub("L L C|L\\.L\\.C", "LLC", df$BuyerName)
df$SellerName <- gsub("L L C|L\\.L\\.C", "LLC", df$SellerName)

df$BuyerName <- gsub('"+', '', df$BuyerName)
df$SellerName <- gsub('"+', '', df$SellerName)

df$BuyerName <- gsub("\\s+", " ", trimws(df$BuyerName))
df$SellerName <- gsub("\\s+", " ", trimws(df$SellerName))

# Address
df$Address <- toupper(trimws(df$Address))
df$Address <- gsub("\\s+", " ", df$Address)

# ZipCode
df$ZipCode <- trimws(as.character(df$ZipCode))
df$ZipCode <- gsub("\\.0$", "", df$ZipCode)
df$ZipCode <- substr(df$ZipCode, 1, 5)
df$ZipCode[df$ZipCode %in% c("0", "", "WA", "98", "NA")] <- NA

# Date fields
df$DocumentDate <- as.Date(df$DocumentDate, "%Y-%m-%d")
df$DocumentDateMonth <- format(df$DocumentDate, "%m")
df$DocumentDateYear <- format(df$DocumentDate, "%Y")
df$MonthYear <- format(df$DocumentDate, "%Y-%m")

# Count field
df$dummy <- 1

# SalePrice cleaning
# I am keeping original SalePrice, but creating a cleaned version for averages/medians.
# Zero-dollar sales may represent transfers, gifts, or non-market transactions.
df$SalePriceClean <- df$SalePrice
df$SalePriceClean[df$SalePriceClean <= 0] <- NA

# Create porch, garage, and basement fields for Question 5
df$TotalPorchSqFt <- df$SqFtOpenPorch + df$SqFtEnclosedPorch
df$TotalGarageSqFt <- df$SqFtGarageBasement + df$SqFtGarageAttached

df$HasGarage <- FALSE
df$HasGarage[df$TotalGarageSqFt > 0] <- TRUE

df$HasBasement <- FALSE
df$HasBasement[df$SqFtTotBasement > 0 | df$SqFtFinBasement > 0] <- TRUE

# Remove impossible or unhelpful year built values for year-based analysis
df$YrBuiltClean <- df$YrBuilt
df$YrBuiltClean[df$YrBuiltClean <= 0] <- NA



# [15 points]
# Question 2: Data Manipulation / Creation
#   - Create 3 new datasets from the original dataset and save for downstream questions based on these conditions:
#       (1): Corporate-to-corporate transactions
#       (2): Human-to-human transactions (non-corporate)
#       (3): Any transaction where a bank sold to a non-bank entity

corpEntityFlag <- c(
  "LLC", "L L C", "L.L.C", "INC", "INCORPORATED",
  "CORP", "CORPORATION", "CO ", "COMPANY",
  "HOLDINGS", "TRUST", "TRUSTEE", "LTD", "LIMITED",
  "BANK", "ASSOCIATION", "CITY", "COUNTY", "STATE"
)

is_corporate_name <- function(name_vector) {
  grepl(paste(corpEntityFlag, collapse = "|"), name_vector)
}

is_bank_name <- function(name_vector) {
  grepl("BANK|WELLS FARGO|CHASE|CITIBANK|U S BANK|US BANK|BANC|CREDIT UNION|MORTGAGE", name_vector)
}

df$BuyerIsCorporate <- is_corporate_name(df$BuyerName)
df$SellerIsCorporate <- is_corporate_name(df$SellerName)

df$BuyerIsBank <- is_bank_name(df$BuyerName)
df$SellerIsBank <- is_bank_name(df$SellerName)

# Dataset 1: Corporate-to-corporate transactions
corp_to_corp <- df[df$BuyerIsCorporate == TRUE & df$SellerIsCorporate == TRUE, ]

# Dataset 2: Human-to-human transactions
human_to_human <- df[df$BuyerIsCorporate == FALSE & df$SellerIsCorporate == FALSE, ]

# Dataset 3: Bank sold to non-bank entity
bank_to_nonbank <- df[df$SellerIsBank == TRUE & df$BuyerIsBank == FALSE, ]


# [20 points]
# Question 3: Aggregations
# Please use the corporate-to-corporate dataset for this question.
#   - Create 3 separate aggregated datasets for each the following unique values:
#       + zipcodes
#       + month-year 
#       + entity (buyer/seller)
#   - With the following calculations (10 total):
#       + transaction volume
#       + average price, median price, price distribution skew direction (right/left/symmetric)
#       + average square footage, median square footage, square footage skew direction (right/left/symmetric)
#       + average year built, median year built, year built skew direction (right/left/symmetric)


# Helper function for skew direction
skew_direction <- function(avg_value, median_value) {
  case_when(
    is.na(avg_value) | is.na(median_value) ~ NA_character_,
    avg_value > median_value ~ "Right Skewed",
    avg_value < median_value ~ "Left Skewed",
    avg_value == median_value ~ "Symmetric"
  )
}

# Dataset 1: Zipcodes
corp_zipcode_agg <- corp_to_corp %>%
  filter(!is.na(ZipCode)) %>%
  group_by(ZipCode) %>%
  summarize(
    TransactionVolume = sum(dummy, na.rm = TRUE),
    
    AvgPrice = mean(SalePriceClean, na.rm = TRUE),
    MedianPrice = median(SalePriceClean, na.rm = TRUE),
    PriceSkew = skew_direction(AvgPrice, MedianPrice),
    
    AvgSqFt = mean(SqFtTotLiving, na.rm = TRUE),
    MedianSqFt = median(SqFtTotLiving, na.rm = TRUE),
    SqFtSkew = skew_direction(AvgSqFt, MedianSqFt),
    
    AvgYearBuilt = mean(YrBuiltClean, na.rm = TRUE),
    MedianYearBuilt = median(YrBuiltClean, na.rm = TRUE),
    YearBuiltSkew = skew_direction(AvgYearBuilt, MedianYearBuilt)
  ) %>%
  mutate(
    AvgPrice = ifelse(is.nan(AvgPrice), NA, AvgPrice),
    AvgSqFt = ifelse(is.nan(AvgSqFt), NA, AvgSqFt),
    AvgYearBuilt = ifelse(is.nan(AvgYearBuilt), NA, AvgYearBuilt)
  )

# Dataset 2: Month-Year
corp_monthyear_agg <- corp_to_corp %>%
  group_by(MonthYear) %>%
  summarize(
    TransactionVolume = sum(dummy, na.rm = TRUE),
    
    AvgPrice = mean(SalePriceClean, na.rm = TRUE),
    MedianPrice = median(SalePriceClean, na.rm = TRUE),
    PriceSkew = skew_direction(AvgPrice, MedianPrice),
    
    AvgSqFt = mean(SqFtTotLiving, na.rm = TRUE),
    MedianSqFt = median(SqFtTotLiving, na.rm = TRUE),
    SqFtSkew = skew_direction(AvgSqFt, MedianSqFt),
    
    AvgYearBuilt = mean(YrBuiltClean, na.rm = TRUE),
    MedianYearBuilt = median(YrBuiltClean, na.rm = TRUE),
    YearBuiltSkew = skew_direction(AvgYearBuilt, MedianYearBuilt)
  )

# Dataset 3: By Entity
# Some entities have NA for AvgPrice, MedianPrice, and PriceSkew because they had
# no valid sale price values after cleaning. I kept these rows because the
# transactions still exist and can still be used for transaction volume,
# square footage, and year-built calculations. ValidPriceCount shows how many
# usable sale price values were available for each entity.

buyer_entities <- corp_to_corp %>%
  select(EntityName = BuyerName, SalePriceClean, SqFtTotLiving, YrBuiltClean, dummy)

seller_entities <- corp_to_corp %>%
  select(EntityName = SellerName, SalePriceClean, SqFtTotLiving, YrBuiltClean, dummy)

corp_entity_long <- bind_rows(buyer_entities, seller_entities)

# Clean entity names before grouping
corp_entity_long$EntityName <- gsub('"+', '', corp_entity_long$EntityName)
corp_entity_long$EntityName <- gsub("L L C|L\\.L\\.C", "LLC", corp_entity_long$EntityName)
corp_entity_long$EntityName <- gsub("\\s+", " ", trimws(corp_entity_long$EntityName))

corp_entity_agg <- corp_entity_long %>%
  group_by(EntityName) %>%
  summarize(
    TransactionVolume = sum(dummy, na.rm = TRUE),

    AvgPrice = mean(SalePriceClean, na.rm = TRUE),
    MedianPrice = median(SalePriceClean, na.rm = TRUE),
    PriceSkew = skew_direction(AvgPrice, MedianPrice),
    
    AvgSqFt = mean(SqFtTotLiving, na.rm = TRUE),
    MedianSqFt = median(SqFtTotLiving, na.rm = TRUE),
    SqFtSkew = skew_direction(AvgSqFt, MedianSqFt),
    
    AvgYearBuilt = mean(YrBuiltClean, na.rm = TRUE),
    MedianYearBuilt = median(YrBuiltClean, na.rm = TRUE),
    YearBuiltSkew = skew_direction(AvgYearBuilt, MedianYearBuilt)
  ) %>%
  mutate(
    AvgPrice = ifelse(is.nan(AvgPrice), NA, AvgPrice),
    AvgSqFt = ifelse(is.nan(AvgSqFt), NA, AvgSqFt),
    AvgYearBuilt = ifelse(is.nan(AvgYearBuilt), NA, AvgYearBuilt)
  )

# [20 points]
# Question 4: Functions
# Using the logic above from question 3, create a function that takes the following arguments:
#   - Argument 1: 'df' - this is any dataset of transactions
#   - Argument 2: 'metric_name' - this is the name of which calculation desired, alternatively, 
#                 add the option to provide 'all' as an input and return all 10 calculations
#   - Argument 3: 'aggregation_type' - this is one of the three aggregation values of zipcodes, 
#                 month-year, or entity 
#   - Output: an aggregated dataframe of the calculation(s) desired based on the aggregation type inputted
# Please test your function 3 times, one for each of the 3 datasets created in Question 2 by 
# using the 'all' input for 'metric_name'.


aggregate_transactions <- function(df, metric_name = "all", aggregation_type) {
  
  # This function takes a transaction dataset, a requested metric, and an aggregation type.
  # The metric_name argument allows the user to request one calculation, such as "AvgPrice",
  # or use "all" to return all 10 calculations required in the question.
  # The aggregation_type argument controls whether the dataset is grouped by zipcode,
  # month-year, or entity.
  
  if (aggregation_type == "zipcode") {
    
    # Missing zip codes are removed because NA is not a real zipcode.
    # Keeping NA would create one large group of records with no usable zipcode.
    grouped_df <- df %>%
      filter(!is.na(ZipCode)) %>%
      group_by(ZipCode)
    
  } else if (aggregation_type == "month-year") {
    
    # MonthYear was created during cleaning using year-month format so that
    # transactions from different years are not combined into the same month.
    grouped_df <- df %>%
      group_by(MonthYear)
    
  } else if (aggregation_type == "entity") {
    
    # For entity-level aggregation, buyers and sellers are combined into one
    # column so the same summary logic can be applied to all entities.
    buyer_entities <- df %>%
      select(EntityName = BuyerName, SalePriceClean, SqFtTotLiving, YrBuiltClean, dummy)
    
    seller_entities <- df %>%
      select(EntityName = SellerName, SalePriceClean, SqFtTotLiving, YrBuiltClean, dummy)
    
    entity_df <- bind_rows(buyer_entities, seller_entities)
    
    # Standardize entity names before grouping so variations like "L L C"
    # and "LLC" are grouped together.
    entity_df$EntityName <- gsub('"+', '', entity_df$EntityName)
    entity_df$EntityName <- gsub("L L C|L\\.L\\.C", "LLC", entity_df$EntityName)
    entity_df$EntityName <- gsub("\\s+", " ", trimws(entity_df$EntityName))
    
    grouped_df <- entity_df %>%
      group_by(EntityName)
    
  } else {
    
    stop("aggregation_type must be 'zipcode', 'month-year', or 'entity'")
    
  }
  
  # Create the 10 calculations required by the question:
  # transaction volume, average/median/skew for price,
  # average/median/skew for square footage, and average/median/skew for year built.
  output <- grouped_df %>%
    summarize(
      TransactionVolume = sum(dummy, na.rm = TRUE),
      
      AvgPrice = mean(SalePriceClean, na.rm = TRUE),
      MedianPrice = median(SalePriceClean, na.rm = TRUE),
      PriceSkew = skew_direction(AvgPrice, MedianPrice),
      
      AvgSqFt = mean(SqFtTotLiving, na.rm = TRUE),
      MedianSqFt = median(SqFtTotLiving, na.rm = TRUE),
      SqFtSkew = skew_direction(AvgSqFt, MedianSqFt),
      
      AvgYearBuilt = mean(YrBuiltClean, na.rm = TRUE),
      MedianYearBuilt = median(YrBuiltClean, na.rm = TRUE),
      YearBuiltSkew = skew_direction(AvgYearBuilt, MedianYearBuilt)
    ) %>%
    mutate(
      AvgPrice = ifelse(is.nan(AvgPrice), NA, AvgPrice),
      AvgSqFt = ifelse(is.nan(AvgSqFt), NA, AvgSqFt),
      AvgYearBuilt = ifelse(is.nan(AvgYearBuilt), NA, AvgYearBuilt)
    )
  
  # If the user requests "all", return every calculation.
  if (metric_name == "all") {
    return(output)
  }
  
  # If the user requests one specific metric, return the grouping column
  # plus only that selected metric.
  if (metric_name %in% names(output)) {
    return(output[, c(1, which(names(output) == metric_name))])
  } else {
    stop("metric_name not found. Use 'all' or one of the output column names.")
  }
}

# Test 1: corporate-to-corporate dataset using all metrics
test_corp_to_corp <- aggregate_transactions(
  df = corp_to_corp,
  metric_name = "all",
  aggregation_type = "zipcode"
)

View(test_corp_to_corp)


# Test 2: human-to-human dataset using all metrics
test_human_to_human <- aggregate_transactions(
  df = human_to_human,
  metric_name = "all",
  aggregation_type = "month-year"
)

View(test_human_to_human)


# Test 3: bank-to-nonbank dataset using all metrics
test_bank_to_nonbank <- aggregate_transactions(
  df = bank_to_nonbank,
  metric_name = "all",
  aggregation_type = "entity"
)

View(test_bank_to_nonbank)

# [30 points]
# Question 5: Please use the entire cleaned dataset for this question.  Please provide supporting interpretations
# of the analysis (e.g., text in comments, plots, ...).
#   - [10 points] How has the size of porches changed over years built?  
#   - [10 points] How has the prominence of garages changed over years built?
#   - [10 points] How has the prominence of basements changed over years built?


# Creates a year-built summary table using the entire cleaned dataset.
# Each row represents a year built and summarizes average porch size,
# the percentage of properties with garages, and the percentage with basements.
year_built_summary <- df %>%
  filter(!is.na(YrBuiltClean)) %>%
  group_by(YrBuiltClean) %>%
  summarize(
    TransactionVolume = n(),
    AvgPorchSqFt = mean(TotalPorchSqFt, na.rm = TRUE),
    GarageProminence = mean(HasGarage, na.rm = TRUE) * 100,
    BasementProminence = mean(HasBasement, na.rm = TRUE) * 100
  )

# Plot 1: Porch size over year built.
ggplot(year_built_summary, aes(x = YrBuiltClean, y = AvgPorchSqFt)) +
  geom_line() +
  labs(
    title = "Average Porch Size by Year Built",
    x = "Year Built",
    y = "Average Porch Square Footage"
  )

# Plot 2: Garage prominence over year built.
ggplot(year_built_summary, aes(x = YrBuiltClean, y = GarageProminence)) +
  geom_line() +
  labs(
    title = "Garage Prominence by Year Built",
    x = "Year Built",
    y = "Percent of Properties with a Garage"
  )

# Plot 3: Basement prominence over year built.
ggplot(year_built_summary, aes(x = YrBuiltClean, y = BasementProminence)) +
  geom_line() +
  labs(
    title = "Basement Prominence by Year Built",
    x = "Year Built",
    y = "Percent of Properties with a Basement"
  )