FROM eclipse-temurin:24-jre

WORKDIR /app

RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup

COPY target/*.jar app.jar

RUN chown appuser:appgroup /app/app.jar

USER appuser

EXPOSE 8099

ENTRYPOINT ["java", "-jar", "app.jar"]