# book-app-interview-project-api

## Instructions

### Setup Guidelines
1. clone the repository using the code below

    >git clone https://gitlab.com/musala-coding-tasks-solutions/oluwasegun-ariyo.git
1. create a branch for test on your system and on the repository online
1. checkout to your newly created branch
1. build the project.
1. create your application-dev.properties file using the application-test.properties as a sample
1. edit the application-dev.properties file by replacing port,username,db and password to your own mysql connection and database details
1. create your sql database using the database name from 5. above e.g "drone_app_schema"
1. You can check implemented API endpoints with the swagger UI configured in the app with [http://localhost:8080/swagger-ui/](http://localhost:8080/swagger-ui/) after running the app

1. All API end points use the BaseResponse object as thier response payload, set the status code, description and data where necessary. errors gotten from exceptions would be put in the errors object during runtime. see example response from getting the user types below

    ```
    {
      "statusCode": 200,
      "description": "Drones found successfully.",
      "data": [
        {
          "id": 18,
          "serialNumber": "hkwencoe00823",
          "model": "O01920302",
          "weightLimit": 500,
          "batteryCapacity": 100,
          "state": "IDLE",
          "medications": [],
          "date_created": "2022-01-14T08:18:19.000+00:00",
          "last_updated": "2022-01-14T08:18:19.000+00:00"
        },
        {
          "id": 19,
          "serialNumber": "hkwen02300823",
          "model": "O01920301",
          "weightLimit": 500,
          "batteryCapacity": 90,
          "state": "IDLE",
          "medications": [],
          "date_created": "2022-01-14T08:22:06.000+00:00",
          "last_updated": "2022-01-14T08:22:06.000+00:00"
        }
      ],
      "errors": null
    }

1. setting `HttpServletResponse.SC_OK (200)` as the statusCode in the response payload for all successfull and `HttpServletResponse.SC_BAD_REQUEST (400)` for all failed operations. only uses `HttpServletResponse.SC_INTERNAL_SERVER_ERROR (500)` when you try to catch an internal server error during an operation.