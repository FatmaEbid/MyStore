
# Use Maven with JDK 17
FROM maven:3.8.5-openjdk-17

# Set working directory
WORKDIR /app

# Copy project files
COPY . /app

# Install Allure CLI
RUN apt-get update &&     apt-get install -y unzip &&     curl -o allure.zip -L https://github.com/allure-framework/allure2/releases/latest/download/allure-2.24.0.zip &&     unzip allure.zip -d /opt/ &&     ln -s /opt/allure-2.24.0/bin/allure /usr/bin/allure &&     rm allure.zip

# Install Lighthouse (requires Node.js)
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - &&     apt-get install -y nodejs &&     npm install -g lighthouse

# Run tests and generate reports
CMD mvn clean test &&     allure generate --clean &&     lighthouse https://example.com --output html --output-path ./lighthouse-report.html
