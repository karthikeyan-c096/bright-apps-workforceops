## BrightApps WorkforceOps
This application is a simple search application used for searching employees in elasticsearch sever.

## What you need
You’ll need a computer that you can install (or have already installed) the following:

- Java 17
- IDE of your choice (Intellj or Eclipse are recommended)
- Build tool of your choice (Maven or Gradle are recommended)
- curl
- (Optional) Postman (or a similar REST client) can be useful for interfacing with ElasticSearch and your application

## Setting up ElasticSearch
The following steps will get ElasticSearch running on your computer:

- Download ElasticSearch from here: https://www.elastic.co/downloads/elasticsearch
- Install ElasticSearch by untarring/unzipping in into the directory of your choice.
- Edit config/elasticsearch.yml and add xpack.security.enabled: false to the file
- Start ElasticSearch with bin/elasticsearch (or bin\elasticsearch.bat on Windows)
- Unzip the sample data into a directory of your choice.
- Load the ES mapping using curl: curl -X PUT http://localhost:9200/employees -H
'Content-Type: application/json' -d @employees-mapping.json
- Load the test data with curl: curl -o /dev/null -X POST http://localhost:9200/_bulk -H
'Content-Type: application/json' --data-binary @employees.jsons. (Windows version:
curl -o nul -X POST http://localhost:9200/_bulk -H 'Content-Type: application/json'
--data-binary @employees.jsons)

## Hints
- ElasticSearch has a java client library which can simplify your development:
https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/introdu
ction.html
- For the query to ElasticSearch, since you need to search two different fields as the
same time, you’ll need to use a Boolean query:
https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-qu
ery.html

## Run Locally
Build the application with Maven

```bash
  mvn clean package
```

Start the server

```bash
  java -jar target/<application>.jar
```
Access the application with URL: http://localhost:9090/employees/search


## API Reference

#### Search Employees

```http
  GET /employees/search

  ( Paging )
  GET /employees/search?page=2
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `interest` | `string` | **Required**. Employee's one of the interest |
| `salary` | `string` | **Required**. Employee salary criteria |

Sample Request
```json
{
  "interest": "Aircraft Spotting",
  "minSalary": 70000
}
```
Sample Response
```json
{
  "count": 81,
  "results": [
    {
      "firstName": "LIA",
      "lastName": "BELICH",
      "designation": "Manager",
      "salary": 84000,
      "dateOfJoining": "1997-02-13",
      "address": "43 Broad St. Grand Blanc, MI 48439",
      "gender": "Female",
      "age": 40,
      "maritalStatus": "Unmarried",
      "interests": [
        "Aircraft Spotting"
      ]
    }
  ]
}
```