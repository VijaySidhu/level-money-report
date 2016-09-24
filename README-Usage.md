Level Money Report API
========
# How much money am I spending?

Level-Money-Report is a REST API that does the following:

- Loads a user's transactions from the GetAllTransactions endpoint
- Determines how much money the user spends and makes in each of the months for which we have data, and in the "average" month.


Technology Stack 
---------
Below open source technologies are used to build this API.

	1. Java 8
	2. Spring Frame Work
	3. Spring Boot
	4. Maven 3
	5. Embedded Tomcat Server in Spring Boot
	6. Jackson for JSON data 


How to run API ?  Prerequisite
-----------

	1. Java 8
	2. Maven 3.x
	3. Postman Rest client to test api



Level Money Get All Transactions need user id, api token & token for authentication. Below properties are configured in application.properties file, please change values if you need to run with different values

uid=1110590645
apitoken=AppTokenForInterview
token=D3BB69420805C10AB852B2625D481105
Go to project directory and execute command : mvn spring-boot:run

 
Resources Accessible
---------------


- **Get /transactions**   

                   1. http://localhost:8080/transactions/monthlysummary?ignoreDonuts=true
                   2. Hit on Get button to retrieve monthly summary
				   
   Output 
   				{
	   
				    "2016-9": {
			  		 "spent": "$31,964,800.00",
			    	 "income": "$22,296,300.00"
			  		 },
			  
			  		"average": {
			    	"spent": null,
			    	"income": null
			  }
  				   

