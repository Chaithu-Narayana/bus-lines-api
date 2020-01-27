# bus-lines-api
An API hosting a couple of endpoints using data from Trafiklab’s open API (https://www.trafiklab.se).

The API offers the following features:
- Return the details of top 10 bus lines in Stockholm area with most bus stops on their route. 
- Return a list of every bus stop of the bus line provided.

## Key Technologies Used
 - Java 11
 - Spring Boot 
	- embedded tomcat
	- cache
	- scheduler
	- rest template
	- webflux (for integration tests)
 - Junit 5
 - Mockito
 - slf4j
 - Maven
 - commons-lang3
## Instructions to run the application

 - Download the source code from [[https://github.com/Chaithu-Narayana/bus-lines-api](https://github.com/Chaithu-Narayana/bus-lines-api)].
 - Open the project using a Java IDE of your choice (IntelliJ or Eclipse).
 - Locate a file called '***BusLinesApplication***', right-click and run as a java application.
 - Since it is a spring boot application, no additional build steps are needed. The application listens on port '***8090***' once it is started successfully.
## API Responses
The API responds with two formats based on the status returned.
 - Success response with a 200 http response code to the client - format as shown below
>     { 
>        "status":"Success", // A string representing the status of the operation
>        "timestamp":"2020-01-18T18:45:00", //A date-time without a time-zone in the ISO-8601 calendar system
>        "data":[ 
>           { 
>             /* resultant data from endpoints */
>           }
>        ]
>     }
     
 - Failure response with corresponding http status code to the client - format as shown below
>     { 
>        "status":"Failure", // A string representing the status of the operation
>        "timestamp":"2020-01-18T18:45:00", //A date-time without a time-zone in the ISO-8601 calendar system
>        "errorMessage": "No bus line exists for number: 456" // A short description of the error encountered
>     }
 
## API Endpoints
The API exposes two endpoints for use. They shall be invoked as follows.
 - ***Top 10 bus lines*** 
 
 Use the below URL on a web browser or rest client -
   [http://localhost:8090/bus-lines/top-10-lines](http://localhost:8090/bus-lines/top-10-lines)

If successful, the endpoint responds back with a sample response as below.

    { 
       "status":"Success",
       "timestamp":"2020-01-18T18:45:00",
       "data":[ 
          { 
             "LineNumber":636,
             "LineDesignation":"636",
             "DefaultTransportMode":"",
             "DefaultTransportModeCode":"BUS",
             "LastModifiedUtcDateTime":"2007-08-24 00:00:00.000",
             "ExistsFromDate":"2007-08-24 00:00:00.000"
          },
          { 
             "LineNumber":626,
             "LineDesignation":"626",
             "DefaultTransportMode":"",
             "DefaultTransportModeCode":"BUS",
             "LastModifiedUtcDateTime":"2007-08-24 00:00:00.000",
             "ExistsFromDate":"2007-08-24 00:00:00.000"
          },
          /** more bus lines data **/
       ]
    }
    
    
     

- ***Stops on a given bus line***
  
Use the below URL on a web browser or rest client -
[http://localhost:8090/bus-lines/stops-on-line/{line-number}](http://localhost:8090/bus-lines/stops-on-line/{line-number}); where *line-number* is a ***positive integer*** representing the bus line (eg. 173)

If successful, the endpoint responds back with a sample response as below.

    { 
       "status":"Success",
       "timestamp":"2020-01-18T20:13:07",
       "data":[ 
          { 
             "StopPointNumber":13037,
             "StopPointName":"Bandhagen",
             "StopAreaNumber":13037,
             "LocationNorthingCoordinate":"59.2708250331152",
             "LocationEastingCoordinate":"18.0476716961957",
             "ZoneShortName":"A",
             "StopAreaTypeCode":"BUSTERM",
             "LastModifiedUtcDateTime":"2014-06-03 00:00:00.000",
             "ExistsFromDate":"2014-06-03 00:00:00.000"
          },
          { 
             "StopPointNumber":13055,
             "StopPointName":"Kämpetorpsskolan",
             "StopAreaNumber":13055,
             "LocationNorthingCoordinate":"59.2842499709449",
             "LocationEastingCoordinate":"17.9937633253391",
             "ZoneShortName":"A",
             "StopAreaTypeCode":"BUSTERM",
             "LastModifiedUtcDateTime":"2014-06-03 00:00:00.000",
             "ExistsFromDate":"2014-06-03 00:00:00.000"
          },
          /** more bus stop points data **/
       ]
    }

## Assumptions/Considerations

 - Trafiklab refreshes it's data between 00:00 and 2:00 hours, otherwise the data remains static for the rest of the day. So, this API caches that data and refreshes it everyday at 3 am.
 - UTF-8 encoding has been used and is recommended for use of this API
 - 'Direction Code' has not been considered in any of these endpoints as they anyways don't effect the outcome in both these cases.
 - API management aspects (securing access - authentication/authorization/api-key-protection) are not included in this assignment.
