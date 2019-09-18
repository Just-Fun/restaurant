# Logic tests

Implementation in class - ```com.serzh.logic.AmpisLogic```

Unit tests in class - ```com.serzh.logic.AmpisLogicTest```

# Project for SDE Backend: Restaurant


## 1. How to start
```
$ git clone https://github.com/Just-Fun/restaurant.git
$ cd restaurant
$ mvn clean package
$ mvn spring-boot:run
```

## 2. Testing application

### You can use Swagger UI
```
http://localhost:8081/swagger-ui.html
```

### Or command line

### Items menu

Get all items

```
curl -X GET "http://localhost:8081/api/v1/items"
```

Get all items with pagination
```
curl -X GET "http://localhost:8081/api/v1/items?page=1&size=3"
```

Get Item by id
```
curl -X GET "http://localhost:8081/api/v1/items/2"
```

Searching menu by keyword that matches title, description, additional details
```
curl -X GET "http://localhost:8081/api/v1/items?keyword=Italian"
```

Creates a New Item
```
curl -X POST "http://localhost:8081/api/v1/items" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Item name\", \"description\": \"Item description\", \"image\": \"image address\", \"price\": 123.34, \"typeIds\": [ 1, 3 ]}"
```

Update item
```
curl -X PUT "http://localhost:8081/api/v1/items/8" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Item name\", \"description\": \"Item description\", \"image\": \"image address\", \"price\": 123.34, \"typeIds\": [ 1, 3 ]}"
```

Delete Item
```
curl -X DELETE "http://localhost:8081/api/v1/items/2"
```


### Orders menu

Creates a New Order
```
curl -X POST "http://localhost:8081/api/v1/orders" -H "accept: */*" -H "Content-Type: application/json" -d "[ { \"itemId\": 1, \"quantity\": 2 },{ \"itemId\": 2, \"quantity\": 1 }]"
```

Update Order
```
curl -X PUT "http://localhost:8081/api/v1/orders/1" -H "accept: */*" -H "Content-Type: application/json" -d "[ { \"itemId\": 1, \"quantity\": 1 },{ \"itemId\": 2, \"quantity\": 2 },{ \"itemId\": 4, \"quantity\": 4 }]"
```
Check Bill by Order id
```
curl -X GET "http://localhost:8081/api/v1/orders/1" -H "accept: */*"
```


--------------------

DOCKER_BUILDKIT=1 docker build -t serzh/restraurant .

docker run -p 8081:8081 serzh/restraurant