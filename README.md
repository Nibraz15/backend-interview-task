# PowerPlant Backend application

This is a SpringBoot REST application using postgresSql 
as DataBase and deployed to docker. Before making queries user need to authenticate using `login` 
api where client will recive a token for `Authorization` header. 

##Run the application
* Navigate to `/src/main/docker`
* Run `docker-compose up  `

##Reset the Docker container
* if this a new build `cp target/powerplant-0.0.1-SNAPSHOT.jar src/main/docker
  `
* Navigate to `/src/main/docker`
* Run `docker-compose down`
* Run `docker rmi docker-spring-boot-postgres:latest`
* Run `docker-compose up  `


## Run the app

docker hosted on `localhost:8080`

# REST API


## User Signup

### Request
* Type : `Post`
* Path : `/user/Signup`
* RequestBody : 
```json
{"user" : "name", "password": "password"}
  ```

### Response

```json
{
    "user": "name",
    "password": null,
    "token": null
}
```

## User Login to get Access token

### Request
* Type : `Get`
* Path : `/user/Signup`
* RequestBody :
```json
{"user" : "name", "password": "password"}
  ```
### Response

```json
{
    "user": "name",
    "password": null,
    "token": "Bearer Toke"
}
```

## Get by PowerPlant name Thing

* Type : `Get`
* Path : `/powerPlant/get`
* RequestBody :
```json
{"powerPlantName":"7-Mile Ridge Wind Project"}  
  ```
### Response

```json
{
  "powerPlantName": "7-Mile Ridge Wind Project",
  "location": "AK",
  "year": 2019,
  "generators": {
    "generators": [
      {
        "generatorId": "WT1",
        "generatorStatus": "CN",
        "generatorPowerGeneration": 0
      }
    ]
  },
  "powerOutPut": 0,
  "powerOutputPercentage": 0.0
}
```

## Get ALL PowerPlants

### Request

* Type : `Get`
* Path : `/powerPlant/getAll`

### Response
```json
[
  {
    "powerPlantName": "7-Mile Ridge Wind Project",
    "location": "AK",
    "year": 2019,
    "generators": {
      "generators": [
        {
          "generatorId": "WT1",
          "generatorStatus": "CN",
          "generatorPowerGeneration": 0
        }
      ]
    },
    "powerOutPut": 0,
    "powerOutputPercentage": 0.0
  }, {..},...
  
]
```

## Filter power plants by location and paginate filtered power plants by location

### Request
* Type : `Get`
* Path : `/powerPlant/getByLocation`
* RequestBody :
```json
{"location" : "AK","page" : 3 ,"size": 1}    
  ```
### Response
```json
{
    "location": "AK",
    "plantsInLocation": 154,
    "plantResponseList": {
        "content": [
            {
                "powerPlantName": "Alakanuk",
                "location": "AK",
                "year": 2019,
                "generators": {
                    "generators": [
                        {
                            "generatorId": "UNIT4",
                            "generatorStatus": "SB",
                            "generatorPowerGeneration": 14
                        },
                        {
                            "generatorId": "G309",
                            "generatorStatus": "CN",
                            "generatorPowerGeneration": 0
                        },
                        {
                            "generatorId": "UNIT1",
                            "generatorStatus": "RE",
                            "generatorPowerGeneration": 0
                        },
                        {
                            "generatorId": "UNIT2",
                            "generatorStatus": "RE",
                            "generatorPowerGeneration": 0
                        },
                        {
                            "generatorId": "UNIT3",
                            "generatorStatus": "RE",
                            "generatorPowerGeneration": 0
                        }
                    ]
                },
                "powerOutPut": 14,
                "powerOutputPercentage": 0.0
            }
        ],
        "pageable": {
            "sort": {
                "sorted": false,
                "empty": true,
                "unsorted": true
            },
            "pageNumber": 2,
            "pageSize": 1,
            "offset": 2,
            "paged": true,
            "unpaged": false
        },
        "last": false,
        "totalElements": 154,
        "totalPages": 154,
        "first": false,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "size": 1,
        "number": 2,
        "numberOfElements": 1,
        "empty": false
    }
}
```

## Display top N and bottom N plants in either descending or ascending order by total generation output

### Request
* Type : `Get`
* Path : `/powerPlant/getNSorted`
* `order` has two values `asc` and `desc`
* RequestBody :
```json
{
  "limit" : 1,
  "provideHighResults" : true ,
  "provideLowResults": true,
  "order" : "asc",
}    
  ```
### Response
```json
{
    "highOutputPlants": [
        {
            "powerPlantName": "Palo Verde",
            "location": "AZ",
            "year": 2019,
            "generators": {
                "generators": [
                    {
                        "generatorId": "1",
                        "generatorStatus": "OP",
                        "generatorPowerGeneration": 10640123
                    },
                    {
                        "generatorId": "2",
                        "generatorStatus": "OP",
                        "generatorPowerGeneration": 10640123
                    },
                    {
                        "generatorId": "3",
                        "generatorStatus": "OP",
                        "generatorPowerGeneration": 10640123
                    }
                ]
            },
            "powerOutPut": 31920369,
            "powerOutputPercentage": 0.0
        }
    ],
    "lowOutputPlants": [
        {
            "powerPlantName": "7-Mile Ridge Wind Project",
            "location": "AK",
            "year": 2019,
            "generators": {
                "generators": [
                    {
                        "generatorId": "WT1",
                        "generatorStatus": "CN",
                        "generatorPowerGeneration": 0
                    }
                ]
            },
            "powerOutPut": 0,
            "powerOutputPercentage": 0.0
        }
    ]
}
```
## Show both actual and percentage values of the plants generation output by location

### Request

* Type : `Get`
* Path : `/powerPlant/getPowerOutPutByLocation`
* RequestBody :
```json
{
  "getForAllRegions" :false,
  "regions" : ["PR","CA"]
}    
  ```
### Response
```json
[
  {
    "region": "PR",
    "regionOutput": 18171884,
    "powerPlants": [
      {
        "powerPlantName": "AES ILUMINA",
        "location": "PR",
        "year": 2019,
        "powerOutPut": 37967,
        "powerOutputPercentage": 0.20893265
      },
      {
        "powerPlantName": "AES Puerto Rico",
        "location": "PR",
        "year": 2019,
        "powerOutPut": 3532312,
        "powerOutputPercentage": 19.438337
      },
      .
      .

```

## Create or Update Power plant

### Request

* Type : `POST`
* Path : `/powerPlant/save`
* RequestBody :
```json
{
  "powerPlantName": "7-Mile Ridge Wind Project",
  "location": "AK",
  "year": "2019",
  "generators": {
    "generators": [
      {
        "generatorId": "WT1",
        "generatorStatus": "CN",
        "generatorPowerGeneration": 0
      }
    ]
  }
}
   
  ```

### Response
* When the Object is new
```json
{
  "powerPlantName": "7-Mile Ridge Wind Project",
  "location": "AK",
  "year": "2019",
  "generators": {
    "generators": [
      {
        "generatorId": "WT1",
        "generatorStatus": "CN",
        "generatorPowerGeneration": 0
      }
    ]
  }
}
```

* If object already exist year could be updated and new Generators will be added if the new Object have Generators
```json
{
  "powerPlantName": "7-Mile Ridge Wind Project",
  "location": "AK",
  "year": "2019",
  "generators": {
    "generators": [
      "Previous GeneratorList +",
      {
        "generatorId": "WT1",
        "generatorStatus": "CN",
        "generatorPowerGeneration": 0
      }
    ]
  }
}
```

## Batch Create Useful for populating large datasets


### Request

* Type : `POST`
* Path : `/powerPlant/batchSave`
* RequestBody :
```json
{
  "powerPlants": [
    {
      "powerPlantName": "7-Mile Ridge Wind Project",
      "location": "AK",
      "year": "2019",
      "generators": {
        "generators": [
          {
            "generatorId": "WT1",
            "generatorStatus": "CN",
            "generatorPowerGeneration": 0
          }
        ]
      }
    },
    "list of power plants"
  ]
}
  ```

### Response
```json
[
  {
    "powerPlantName": "7-Mile Ridge Wind Project",
    "location": "AK",
    "year": "2019",
    "generators": {
      "generators": [
        {
          "generatorId": "WT1",
          "generatorStatus": "CN",
          "generatorPowerGeneration": 0
        }
      ]
    }
  },"list of created power plants"
]
```

