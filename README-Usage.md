Level Money Report API
========
# How much money am I spending?

Level-Money-Report is a REST API that does the following:

	
	1. Determines how much money the user spends and makes in each of the months for which we have data, and in the "average" month.
	2. Generate predicted spending and income numbers for the rest of this month, in addition to previous month


Technology Stack 
---------
Below open source technologies are used to build this API.

	1. Java 8
	2. Spring Framework 4.3.2
	3. Spring Boot
	4. Maven 3
	5. Embedded Tomcat Server in Spring Boot
	6. Jackson for JSON data 


How to run API & Test Service ?  Prerequisite
-----------

	1. Java 8
	2. Maven 3.x
	3. Postman Rest client to test rest service.



Level Money Get All Transactions need user id, api token & token for authentication. Below properties are configured in application.properties file, please change values if you need to run with different values

	uid=1110590645
	apitoken=AppTokenForInterview
	token=D3BB69420805C10AB852B2625D481105

Go to project directory and execute below command to start api : - **mvn spring-boot:run**


Resources Accessible
---------------


- **Get /transactions**   

	- **Get /transactions/monthlysummary**  

		- **Monthly Summary : Determines total money spent and income in each of the months for which we have data, and in the "average" month.**

- Request Parameter

 		ParameterType :: boolean
 		ParameterName :: ignoreDonuts
 		Default       :: true
 		Description   :: Switch to ignore donut transactions by default API ignores donut transactions but switch is provided as parameter in case if user want to add donut transaction later set ignoreDonuts to false
 		Required      :: No


                   1. Open POSTMAN Rest Client
                   2. http://localhost:8080/transactions/monthlysummary
                   3. Hit on Get button to retrieve monthly summary
                   4. Click on body tab to see Output (In JSON Format) 


    - **Get /transactions/predictedReport**  
			  
		- **Generate predicted spending and income numbers for the rest of this month, in addition to previous month**

- Request Parameters

   ParameterType :: int
   ParameterName :: yyyy
   Description   :: Year in four digits
   Required      :: Yes
   
   ParameterType :: int
   ParameterName :: mm
   Description   :: Month in two digits
   Required      :: Yes
	 

			  
			  1. Open POSTMAN Rest Client
			  2. http://localhost:8080/transactions/predictedReport?yyyy=2016&mm=9 
			  3. Hit on Get button to retrieve monthly summary
			  4. Click on body tab to see Output (In JSON Format)
   
  				   
