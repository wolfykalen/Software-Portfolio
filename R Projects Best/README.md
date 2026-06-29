# R Projects Best - Data Analysis & Visualization

**Course**: ECON 4614 - Economic Data Analysis  
**Focus**: Data cleaning, manipulation, visualization, statistical analysis with R  
**Author**: Kalen Dacosta (kalendaco)

## 📋 Overview

These 4 comprehensive R projects demonstrate proficiency in data analysis, cleaning, manipulation, and visualization using modern R packages (dplyr, ggplot2, rvest). Projects include real-world data analysis scenarios.

## 🎯 Projects

### 1. Final Exam _ Spring 2026.R - Comprehensive Data Analysis
**Description**: Complete data analysis pipeline for real estate transaction data including cleaning, manipulation, aggregation, and visualization.

**Key Features**:
- **Data cleaning**: Handling missing values, type conversion
- **Data manipulation**: Creating filtered datasets based on transaction types
- **Aggregation**: Calculating metrics by zipcode, month-year, and entity
- **Advanced metrics**: Skewness calculations for transaction volumes
- **Visualization**: ggplot2 plots for feature trends over time
- **Function creation**: Reusable functions for data analysis

**Skills Demonstrated**:
- Complete data analysis pipeline
- dplyr for data manipulation
- ggplot2 for visualization
- Statistical calculations (skewness, aggregates)
- Function writing in R

**Code Example**:
```r
# Corporate-to-corporate transactions
corpEntityFlag <- c("LLC", "INC", "CORP", "BANK", ...)
corp_to_corp <- df[grepl(paste(corpEntityFlag, collapse="|"), 
                          df$buyer) & 
                  grepl(paste(corpEntityFlag, collapse="|"), 
                          df$seller), ]
```

---

### 2. ECON 4614 Exam 1 _ 03.01.2026.R - Theoretical & Applied R
**Description**: Comprehensive exam covering theoretical R concepts, function applications, and mini data analysis activity.

**Key Features**:
- **Theoretical concepts**: Data structures, subsetting, type conversion
- **Applied functions**: Statistical modeling, plotting
- **Mini-activity**: Complete data cleaning and analysis workflow
- **Linear regression**: Statistical modeling
- **Extra credit**: Spatial visualization

**Skills Demonstrated**:
- R language fundamentals
- Statistical modeling
- Data visualization
- Theoretical understanding of R

---

### 3. ECON4614_week_5_homework.R - Data Cleaning & Transformation
**Description**: Data cleaning and transformation project using bike membership data with string manipulation and type conversion.

**Key Features**:
- **Column name reformatting**: Standardizing naming conventions
- **String searching**: Using `grepl` for pattern matching
- **String replacement**: Using `gsub` for data cleaning
- **Type conversion**: Converting character to numeric
- **Duplicate handling**: Managing duplicate IDs
- **Likert scale mapping**: Converting qualitative responses to numeric (-2 to 2)

**Skills Demonstrated**:
- String manipulation in R
- Data type conversion
- Data cleaning techniques
- Likert scale analysis

**Code Example**:
```r
likert_map <- c(
  "Strongly Disagree" = -2,
  "Disagree" = -1,
  "Neutral" = 0,
  "Agree" = 1,
  "Strongly Agree" = 2
)
df$AdvocacyIssues <- likert_map[df$AdvocacyIssues]
```

---

### 4. ECON4614_week_3_homework.r - Web Scraping & Data Analysis
**Description**: Web scraping project using rvest to retrieve BLS unemployment data, followed by comprehensive data analysis.

**Key Features**:
- **Web scraping**: Using `rvest` package to scrape BLS data
- **Data cleaning**: Type conversion, data structure checks
- **Data subsetting**: Filtering by year and unemployment rate
- **Statistical calculations**: Monthly averages
- **Dataframe reconstruction**: From individual vectors

**Skills Demonstrated**:
- Web scraping with rvest
- Data cleaning and transformation
- Statistical analysis
- Dataframe manipulation

**Code Example**:
```r
# Subset unemployment data 2015-2018
unRate_2015_2018 <- subset(unRateClean, Year >= 2015 & Year <= 2018)

# Calculate monthly averages
monthly_avg <- colMeans(unRateClean[, 3:12], na.rm = TRUE)
```

## 🛠️ Technical Skills

### R Packages Used
- **dplyr**: Data manipulation and transformation
- **ggplot2**: Data visualization
- **rvest**: Web scraping
- **Base R**: Statistical functions, data structures

### Data Analysis Techniques
- Data cleaning and preprocessing
- Statistical analysis (mean, median, skewness)
- Data visualization
- Web scraping
- String manipulation
- Type conversion
- Data aggregation

## 🚀 How to Run

Each R script can be run in RStudio or R console:
1. Open RStudio
2. Open the .R file
3. Run line-by-line or entire script
4. Ensure required packages are installed: `install.packages(c("dplyr", "ggplot2", "rvest"))`

## 📊 Project Statistics

- **Total Projects**: 4
- **Lines of Code**: 500+
- **Packages**: dplyr, ggplot2, rvest
- **Data Sources**: BLS (web scraping), real estate transactions, survey data

## 💡 Why This Portfolio Impresses Recruiters

1. **Complete Data Pipeline**: From raw data to insights
2. **Real-World Data**: Economic data, real estate, survey responses
3. **Modern R Practices**: Using dplyr and ggplot2 (tidyverse)
4. **Web Scraping**: Demonstrates ability to collect data from web sources
5. **Statistical Analysis**: Shows understanding of statistical concepts
