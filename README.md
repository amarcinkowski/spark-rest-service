# Company REST [![Build Status](https://travis-ci.org/amarcinkowski/spark-rest-service.svg?branch=master)](https://travis-ci.org/amarcinkowski/spark-rest-service) [![codecov.io](https://codecov.io/github/amarcinkowski/spark-rest-service/coverage.svg?branch=master)](https://codecov.io/github/amarcinkowski/spark-rest-service?branch=master)

Java8 app with RESTful WebService (Spark microframework) and AngularJS front-end. Server side validation is performed using hibernate-validator annotations.
![AngularJS front-end](http://amarcinkowski.github.io/imgs/company-app.png)

## Heroku deployment

With Heroku-Toolbelt installed deployment is as easy as:
```bash
heroku create
heroku buildpacks:set heroku/java
git push heroku master
```
now the app may be opened with:
```bash
heroku open
```
## Openshift deployment
With Openshift clien tools (rhc) deployment is as easy as:
```bash
rhc app create companies diy --from-code=https://github.com/amarcinkowski/spark-rest-service.git
```

To easily push your changes to Openshift server add a git remote to the app:
```bash
git remote add openshift -f <openshift-git-repo>
```
Now commited changes are pushed with:
```bash
git push openshift master
```

## REST / JSON Queries

### HTTP methods
| HTTP Method |	CRUD |	Entire Collection (e.g. /companies) |	Specific Item (e.g. /companies/{id}) |
|---|---|---|---|---|
|POST |Create |201 (Created), 'Location' header with link to /customers/{id} containing new ID. <br/> 400 (Bad Request) if constraints are violated (missing required fields).|404 (Not Found), <br/>409 (Conflict) if resource already exists..|
|GET |Read	|200 (OK), list of customers. |200 (OK), single customer. <br/>404 (Not Found), if ID not found or invalid.|
|PUT	|Update/Replace	|N/A 404 (Not Found) |200 (OK) or 204 (No Content). <br/>404 (Not Found), if ID not found or invalid.|
|DELETE	|Delete	|N/A 404 (Not Found)	| N/A 200 (OK). 404 (Not Found), if ID not found or invalid.|

### REST URLs
		POST http://<hostname>/companies - Create new company
		GET  http://<hostname>/companies - Get a list of all companies
		GET  http://<hostname>/companies/:id - Get details about a company
		PUT  http://<hostname>/companies - update a company
		PUT  http://<hostname>/companies/owners/:id - add beneficial owner(s) of the company
### Server side vs client side validation
#### Server side
![Server side validation](http://amarcinkowski.github.io/imgs/server-validation.png)
#### Client side
![Client side validation](http://amarcinkowski.github.io/imgs/client-validation.png)
### JSON Format
###### Company
JSON might include companyID, if it doesn't it's generated. **Bold** fields are required, *italics* fields are validated:
<pre>
{
  "companyID": "1",
  <b>"name": "Company name",</b>
  <b>"address" : "Address",</b>
  <b>"city": "City",</b>
  <b>"country" : "Country",</b>
  <i>"mail" : "em@il.add.res",</i>
  <i>"phoneNumber" : "number consisting of digits and chars: +()-",</i>
  <b>"beneficialOwner" : ["Array", "of", "Beneficial", "Owners"]</b>
}
</pre>

###### Owners
```json
["Array", "of", "Beneficial", "Owners"]
```
### Examples
#### 1. Correct requests
##### POST new Company - 201 (CREATED)
###### cURL Request
```bash
curl -v -H "Content-Type: application/json" -X POST localhost:4567/companies -d '{"name" : "IT Services",  "address" : "Armii Krajowej 41",  "city": "Kalisz",  "country" : "Poland",  "mail" : "em@il.add.res", "phoneNumber" : "+48 745634543",  "beneficialOwner" : ["De vilde Svaner", "Emil i Lönneberga", "Mary Poppins", "Den lille Havfrue"]}'
```
###### JSON
```json
{
  "name": "IT Services",
  "address" : "Parczewskiego 8",
  "city": "Kalisz",
  "country" : "Poland",
  "mail" : "em@il.add.res",
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
##### PUT add Owners of Company with nonexistent ID - 404 (NOT FOUND)
###### cURL Reguest
```bash
curl -v -H "Content-Typon" -X PUT localhost:4567/companies/owners/2 -d '["Den grimme Ælling", "Den lille Pige med Svovlstikkerne"]'
```
###### Response
```bash
< HTTP/1.1 404 Not Found
< Content-Type: text/html;charset=utf-8
```

## License

The code is available under the [MIT license](LICENSE.txt).
