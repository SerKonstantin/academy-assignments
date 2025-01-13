## **Kafka sandbox**

1. Компилим в jar
```shell
mvn clean package
```
2. Запускаем сервисы в раздельных контейнерах
```shell
docker compose up -d
```

3. Делаем тестовый заказ
```shell
curl -X POST http://localhost:8081 \
-H "Content-Type: application/json" \
-d '{
    "customer": "John Smith",
    "product": "TV",
    "price": "10"
}'
```

4. Фильтруем логи отправки/получения сообщений
```shell
docker logs <container-name> | grep PERSONAL
```

5. Не забываем про уборку
```shell
docker compose down --volumes --rmi all
```