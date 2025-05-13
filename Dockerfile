# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Указываем рабочую директорию
WORKDIR /app

# Копируем скомпилированный JAR файл в контейнер
COPY target/user-subscription-service-0.0.1-SNAPSHOT.jar app.jar

# Открываем порт 8080 для приложения
EXPOSE 8080

# Запуск приложения
ENTRYPOINT ["java", "-jar", "app.jar"]
