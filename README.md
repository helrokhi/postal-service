# postal-service
Тестовое задание Java Developer
Pеализован REST API, который позволяет отслеживать почтовые отправления.
Система регистрирует почтовые отправления и их передвижение
между почтовыми отделениями, а также реализована возможность получения информации и
всей истории передвижения конкретного почтового отправления.
Реализованы операции
● регистрация почтового отправления
● его прибытие в промежуточное почтовое отделения
● его убытие из почтового отделения
● его получение адресатом
● просмотр статуса и полной истории движения почтового отправления
Почтовое отправление определены следующими свойствами (идентификатор, имя получателя, тип (письмо, посылка, бандероль, открытка)б
индекс получателя, адрес получателя, статус)
Почтовое отделение характеризуется следующими свойствами (индекс, название, адрес)

Для реализации системы использованы: Java 17+, Spring, Spring Boot.
БД - PostgreSQL, Jooq ORM, Liquibase
Сборка Maven. RUN: clean install -P dev
Документация по web-API Swagger
Тестирование Mockito (на уровне контроллеров)

## Создание контейнера с базой данных PostgreSQL в Docker
После установки приложения Docker в командной строке ввести команду для запуска контейнера с PostgreSQL (образ при необходимости загрузится автоматически) :
    
    docker run -d --name postaldb -p 5432:5432 -e POSTGRES_USER=admin -e POSTGRES_DB=test3 -e POSTGRES_PASSWORD=11111111 -e PGDATA=/var/lib/postgresql/data/pgdata postgres:16.0-alpine3.18

## database connection parameters `application-docker.yaml`:
    spring:
        datasource:
        url:
        username:
        password:
        driver-class-name:

## Локальный запуск
    docker login -u helrokhitest4 -p helrokhitest4

    docker network create -d bridge test3-postal-net

    docker run -d --name postaldb --network test3-postal-net -p 5432:5432 -v data:/var/lib/postgresql/data -e POSTGRES_USER=admin -e POSTGRES_DB=test3 -e POSTGRES_PASSWORD=11111111 postgres:16.0-alpine3.18

    docker-compose -f .deploy/docker-compose-local.yaml pull

    docker-compose -p="test3-postal-net" -f .deploy/docker-compose-local.yaml up -d

## Build images:
    docker build -f .deploy/dockerfile-mailing -t helrokhitest4/postal:mailing .
    docker build -f .deploy/dockerfile-database -t helrokhitest4/postal:database .

## Push images into Docker:
    docker push helrokhitest4/postal:mailing
    docker push helrokhitest4/postal:database
