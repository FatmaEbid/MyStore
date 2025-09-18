# Use Maven with JDK 19 as base image
FROM eclipse-temurin:19-jdk

WORKDIR /app
COPY . /app

# Install system tools
RUN apt-get update && \
    apt-get install -y curl unzip gnupg nodejs npm && \
    npm install -g lighthouse

# Install Allure CLI
RUN curl -o allure.zip -L https://github.com/allure-framework/allure2/releases/download/2.24.0/allure-2.24.0.zip && \
    unzip allure.zip -d /opt/ && \
    ln -s /opt/allure-2.24.0/bin/allure /usr/bin/allure && \
    rm allure.zip

# Run tests and generate reports
CMD mvn clean test && \
    allure generate --clean --output /app/allure-report && \
    lighthouse https://example.com --output html --output-path /app/lighthouse-report.html
