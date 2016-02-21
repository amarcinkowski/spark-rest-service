# spark-rest-service [![Build Status](https://travis-ci.org/amarcinkowski/spark-rest-service.svg?branch=master)](https://travis-ci.org/amarcinkowski/spark-rest-service) [![codecov.io](https://codecov.io/github/amarcinkowski/spark-rest-service/coverage.svg?branch=master)](https://codecov.io/github/amarcinkowski/spark-rest-service?branch=master)

Rest Web Service with Spark Framework

## REST / JSON Queries

### HTTP methods
| HTTP Method |	CRUD |	Entire Collection (e.g. /companies) |	Specific Item (e.g. /companies/{id}) |
|---|---|---|---|---|
|POST |Create |201 (Created), 'Location' header with link to /customers/{id} containing new ID. 400 (Bad Request) if constraints are violated (missing required fields).|404 (Not Found), 409 (Conflict) if resource already exists..|
|GET |Read	|200 (OK), list of customers. |200 (OK), single customer. 404 (Not Found), if ID not found or invalid.|
|PUT	|Update/Replace	|N/A 404 (Not Found) |200 (OK) or 204 (No Content). 404 (Not Found), if ID not found or invalid.|
|DELETE	|Delete	|N/A 404 (Not Found)	|200 (OK). 404 (Not Found), if ID not found or invalid.|

### Examples
#### POST Request
```bash
curl -v -H "Content-Type: application/json" -X POST localhost:4567/companies -d '{"name" : "IT Services",  "address" : "Armii Krajowej 41",  "city": "Kalisz",  "country" : "Poland",  "phone" : "+48 745634543",  "beneficialOwner" : ["Andrzej Marcinkowski", "Emil i Lönneberga", "Mary Poppins"]}'
```

#### JSON
```json
{
  "name": "IT Services",
  "address" : "Armii Krajowej 41",
  "city": "Kalisz",
  "country" : "Poland",
  "phone" : "+48 745634543",
  "beneficialOwner" : ["De vilde Svaner", "Emil i Lönneberga", "Mary Poppins", "Den lille Havfrue"]
}
```
##### Response
```bash
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 4567 (#0)
> POST /companies HTTP/1.1
> Host: localhost:4567
> User-Agent: curl/7.43.0
> Accept: */*
> Content-Type: application/json
> Content-Length: 210
> 
* upload completely sent off: 210 out of 210 bytes
< HTTP/1.1 200 OK
< Date: Sun, 21 Feb 2016 01:19:22 GMT
< Content-Type: application/json
< Transfer-Encoding: chunked
< Server: Jetty(9.3.2.v20150730)
< 
* Connection #0 to host localhost left intact
{"companyID":"-5163499408764871927","name":"IT Services","address":"Armii Krajowej 41","city":"Kalisz","country":"Poland","beneficialOwner":["Andrzej Marcinkowski","Emil i Lönneberga","Mary Poppins"]}
```

## Development
### Tools
* Apache Maven 3.3.3
* Eclipse Mars.1
* OpenJDK 64-Bit Server VM (build 1.8.0_66-internal-b17)

### Libraries
* hibernate-validator

## License

The code is available under the [MIT license](LICENSE.txt).
