FROM eclipse-temurin:21-jre

WORKDIR /app

COPY *.class .
COPY *.txt .

CMD ["java", "-cp", ".", "Challenge"]