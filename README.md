# SimpleOrder

## Order
GET   http://localhost:8080/orders

GET   http://localhost:8080/orders/9

POST  http://localhost:8080/orders
    ```
    {
      "email":"vu@mail",
      "inventory":[3,4],
      "quantity": [5,5]
    }
    ```

PUT   http://localhost:8080/orders/9
    ```
    {
      "email":"vu@mail",
      "inventory":[4],
      "quantity": [1]
    }
    ```

DELETE  http://localhost:8080/orders/11

## Inventory

GET   http://localhost:8080/inventory

GET   http://localhost:8080/inventory/1

POST  http://localhost:8080/inventory
    ```
    {
      "name":"item 4",
      "description":"description 4",
      "price": 4,
        "quantity": 10
    }
    ```
