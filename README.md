# Company REST [![Build Status](https://travis-ci.org/amarcinkowski/spark-rest-service.svg?branch=master)](https://travis-ci.org/amarcinkowski/spark-rest-service) [![codecov.io](https://codecov.io/github/amarcinkowski/spark-rest-service/coverage.svg?branch=master)](https://codecov.io/github/amarcinkowski/spark-rest-service?branch=master)

This is "Company" REST Web Service with Spark Framework and Angularjs front-end. Below you will find specs

## REST / JSON Queries

### HTTP methods
| HTTP Method |	CRUD |	Entire Collection (e.g. /companies) |	Specific Item (e.g. /companies/{id}) |
|---|---|---|---|---|
|POST |Create |201 (Created), 'Location' header with link to /customers/{id} containing new ID. 400 (Bad Request) if constraints are violated (missing required fields).|404 (Not Found), 409 (Conflict) if resource already exists..|
|GET |Read	|200 (OK), list of customers. |200 (OK), single customer. 404 (Not Found), if ID not found or invalid.|
|PUT	|Update/Replace	|N/A 404 (Not Found) |200 (OK) or 204 (No Content). 404 (Not Found), if ID not found or invalid.|
|DELETE	|Delete	|N/A 404 (Not Found)	|200 (OK). 404 (Not Found), if ID not found or invalid.|

### Examples
#### 1. Correct requests
##### POST new Company - 201 (CREATED)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X POST localhost:4567/companies -d '{"name" : "IT Services",  "address" : "Armii Krajowej 41",  "city": "Kalisz",  "country" : "Poland",  "phoneNumber" : "+48 745634543",  "beneficialOwner" : ["De vilde Svaner", "Emil i Lönneberga", "Mary Poppins", "Den lille Havfrue"]}'
```
###### JSON
```json
{
  "name": "IT Services",
  "address" : "Parczewskiego 8",
  "city": "Kalisz",
  "country" : "Poland",
  "phoneNumber" : "+48 745634543",
  "beneficialOwner" : ["De vilde Svaner", "Emil i Lönneberga", "Mary Poppins", "Den lille Havfrue"]
}
```
###### Response
<pre>
< HTTP/1.1 <b>201 Created</b>
< <b>Location: localhost:4567/companies/-7643613933603680963</b>
</pre>

##### PUT new Beneficial Owners for Company - 200 (OK)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X PUT localhost:4567/companies/owners/-6283486299957005396 -d '["Den grimme Ælling", "Den lille Pige med Svovlstikkerne"]'
```
###### JSON
```json
["Den grimme Ælling", "Den lille Pige med Svovlstikkerne"]
```
###### Response
<pre>
< HTTP/1.1 <b>200 OK</b>
< Content-Type: application/json
</pre>

##### GET all Companies - 200 (OK)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X GET localhost:4567/companies
```
###### Response
<pre>
< HTTP/1.1 200 OK
< Content-Type: application/json
[{"companyID":"-7300474150165584659","name":"ACME","address":"5th","city":"NYC","country":"USA","beneficialOwner":["BO"]}]
</pre>

##### GET Company by ID - 200 (OK)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X GET localhost:4567/companies/-7300474150165584659
```
###### Response
<pre>
< HTTP/1.1 200 OK
< Content-Type: application/json
{"companyID":"-7300474150165584659","name":"ACME","address":"5th","city":"NYC","country":"USA","beneficialOwner":["BO"]}
</pre>

##### PUT update Company - 200 (OK)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X PUT localhost:4567/companies -d '{"companyID": -7300474150165584659, "name" : "United Comics",  "address" : "Main Ave",  "city": "London",  "country" : "UK",  "phoneNumber" : "+44 745634543", "mail" : "hello@heaven.co.uk", "beneficialOwner" : ["Mary Poppins", "Den lille Havfrue"]}'
```
###### Response
<pre>
< HTTP/1.1 200 OK
< Content-Type: application/json
{"companyID":"-7300474150165584659","name":"United Comics","address":"Main Ave","city":"London","country":"UK","mail":"hello@heaven.co.uk","phoneNumber":"+44 745634543","beneficialOwner":["Mary Poppins","Den lille Havfrue"]}
</pre>
#### 2. Incorrect requests
##### POST new Company missing required field - 400 (BAD REQUEST)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X POST localhost:4567/companies -d '{"city": "Kalisz",  "country" : "Poland",  "phone" : "+48 745634543",  "beneficialOwner" : ["De vilde Svaner", "Emil i Lönneberga", "Mary Poppins", "Den lille Havfrue"]}'
```
###### JSON
```json
{
  "city": "Kalisz",
  "country" : "Poland",
  "phone" : "+48 745634543",
  "beneficialOwner" : ["De vilde Svaner", "Emil i Lönneberga", "Mary Poppins", "Den lille Havfrue"]
}
```
###### Response
<pre>
< HTTP/1.1 <b>400 Bad Request</b>
< Date: Mon, 22 Feb 2016 22:32:48 GMT
< Content-Type: text/html;charset=utf-8
< Transfer-Encoding: chunked
< Server: Jetty(9.3.2.v20150730)
< 
</pre>
##### POST new Company duplicate ID - 409 (CONFLICT)
##### GET Company with nonexistent ID - 404 (NOT FOUND)
##### PUT update Company with nonexistent ID - 404 (NOT FOUND)
#### 3. Ommited requests
DELETE was not specified in requirements.

## Development
### Tools
* Apache Maven 3.3.3
* Eclipse Mars.1
* OpenJDK 64-Bit Server VM (build 1.8.0_66-internal-b17)

### Libraries
* hibernate-validator

## License

The code is available under the [MIT license](LICENSE.txt).
