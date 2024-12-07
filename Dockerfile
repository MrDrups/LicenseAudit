# Используем базовый образ с Java 21
FROM eclipse-temurin:21-jdk-alpine
# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app
# Копируем JAR-файл приложения в контейнер
COPY build/libs/LicenseAudit-0.0.1-SNAPSHOT.jar app.jar
# Открываем порт 8081
EXPOSE 8081
# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
