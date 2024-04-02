FROM openjdk:17
ENV PORT 9082

WORKDIR /usr/app/
COPY /build/libs/weather-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE $PORT
ENTRYPOINT ["java"]
CMD  ["-jar", "app.jar"]
