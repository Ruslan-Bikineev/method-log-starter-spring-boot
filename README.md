# Spring Boot Starter для логирования HTTP-запросов и ответов

## Содержание

- [Описание](#описание)
- [Возможности](#возможности)
- [Структура проекта](#структура-проекта)
- [Конфигурация](#конфигурация)
- [Сборка и запуск](#сборка-и-запуск)

## Описание

Разработка собственного Spring Boot стартера, который добавляет функциональность логирования HTTP-запросов и ответов в
приложении. Логирование выполняется с использованием AOP (Aspect-Oriented Programming), а методы для логирования явно
определяются разработчиком.

## Возможности

- Логирование HTTP-запросов и ответов для методов PUT и POST
- Реализация логирования через Aspect
- Гибкая настройка логирования через application.properties или application.yml
- Возможность включения/отключения логирования
- Настройка уровня логов (INFO, DEBUG, WARN, ERROR)

## Структура проекта

Проект состоит из трех частей:

- method-logger — библиотека для логирования
- method-log-starter — Spring Boot стартер
- task-service — сервис, использующий стартер

## Конфигурация

Для настройки логирования в application.yml необходимо указать:

  ```yaml
  method-logger:
    enable: ${METHOD_LOGGER_ENABLE}
    level: ${METHOD_LOGGER_LOG_LEVEL}
  ```

- Где:
    - METHOD_LOGGER_ENABLE — флаг включения/отключения логирования (true/false)
    - METHOD_LOGGER_LOG_LEVEL — уровень логирования (info, debug, warn, error) регистр не имеет значения

## Сборка и запуск

Локальная сборка проектов

- Для локальной сборки и установки зависимостей выполните команды:

  ```bash
  mvn clean install -f method-logger/pom.xml
  mvn clean install -f method-log-starter/pom.xml
  mvn clean install -f task-service/pom.xml
  ```

Запуск с Docker:

- Проект содержит Dockerfile и docker-compose.yml. Для запуска используйте:

  ```bash
  docker-compose up --build
  ```
    - Это создаст контейнеры и запустит сервис с настроенным логированием.