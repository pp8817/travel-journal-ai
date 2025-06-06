FROM eclipse-temurin:17-jdk-alpine

ENV SPRING_PROFILES_ACTIVE=dev

# JAR 파일을 app.jar로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 포트 오픈 (Render에서 기본 포트 8080)
EXPOSE 8080

# 앱 실행 명령
ENTRYPOINT ["java","-jar","/app.jar"]