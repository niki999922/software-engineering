# MacOs

## Start DB

```shell
brew services start mongodb-community@4.4
```

## Stop DB

```shell
brew services stop mongodb-community@4.4
```

## Curls

### Insert users

```shell
curl -X GET http://localhost:8080/register?id\=1\&currency\=RUB                          
User 1 inserted: code SUCCESS%

curl -X GET http://localhost:8080/register?id\=2\&currency\=USD                
User 2 inserted: code SUCCESS%

curl -X GET http://localhost:8080/register?id\=3\&currency\=EUR                          
User 3 inserted: code SUCCESS%

```

### Insert Products

```shell
curl -X GET http://localhost:8080/add-product?name\=korm\&value\=10\&currency\=RUB
Product korm inserted: code SUCCESS%

curl -X GET http://localhost:8080/add-product?name\=lapsha\&value\=5\&currency\=USD      
Product lapsha inserted: code SUCCESS%

curl -X GET http://localhost:8080/add-product?name\=pelmeni\&value\=50\&currency\=EUR
Product pelmeni inserted: code SUCCESS%
```

### Get products for user

```shell
curl -X GET http://localhost:8080/product?id\=1\&currency\=RUB                          
Product(name='korm', value=10.0, currency=RUB)
Product(name='lapsha', value=357.1428571428571, currency=RUB)
Product(name='pelmeni', value=4545.454545454546, currency=RUB)

curl -X GET http://localhost:8080/product?id\=2                                         
Product(name='korm', value=0.14, currency=USD)
Product(name='lapsha', value=5.0, currency=USD)
Product(name='pelmeni', value=63.63636363636365, currency=USD)
```