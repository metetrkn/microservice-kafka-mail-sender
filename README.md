# JavaShop - E-commerce Platform

JavaShop is a modern e-commerce platform built with Spring Boot, offering a robust and secure shopping experience. The platform features a clean, responsive design and implements industry-standard security practices.

## 🚀 Features

- **User Authentication & Authorization**
  - Secure login system
  - OAuth2 integration with GitHub
  - Role-based access control

- **Security**
  - Spring Security integration
  - Encrypted data storage
  - Secure payment processing

## 🛠️ Technology Stack

- **Backend**
  - Java 21
  - Spring Boot 2.7.18
  - Spring Security
  - Spring Data JPA
  - Hibernate 5.6.15
  - PostgreSQL 17

- **Frontend**
  - Thymeleaf
  - Bootstrap
  - JavaScript
  - CSS3

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL
- Git

## 🚀 Getting Started

1. **Clone the repository**
   ```bash
   git clone [repository-url]
   cd javashop-pro
   ```

2. **Configure the database**
   - Create a PostgreSQL database
   - Update `application-dev.properties` with your database credentials

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - Open your browser and navigate to `http://localhost:5000`

## 🔧 Configuration

The application uses different profiles for development and production environments:

- **Development**: `mvn spring-boot:run -Pdev`
- **Production**: `mvn spring-boot:run -Pproduction`

### Production Environment Variables

The following environment variables need to be set for production deployment:

```bash
RDS_HOSTNAME=your-db-host
RDS_PORT=your-db-port
RDS_DB_NAME=your-db-name
RDS_USERNAME=your-db-username
RDS_PASSWORD=your-db-password
GITHUB_CLIENT_ID=your-github-client-id
GITHUB_CLIENT_SECRETS=your-github-client-secret
```

## 🚀 Deployment

This project is deployed using AWS Elastic Beanstalk and AWS CodePipeline with GitHub webhook integration:

### AWS Elastic Beanstalk
- The application is deployed on AWS Elastic Beanstalk for scalable and managed hosting
- Environment is configured for Java 21 and Spring Boot applications
- Uses PostgreSQL RDS for database management
- Configured for production-grade performance and security

### CI/CD Pipeline
- AWS CodePipeline is used for continuous integration and deployment
- GitHub webhook integration for automatic deployments on push
- Automated build and test process using AWS CodeBuild
- Zero-downtime deployments with Elastic Beanstalk

### Deployment Process
1. Code is pushed to the main branch
2. GitHub webhook triggers AWS CodePipeline
3. CodeBuild compiles and tests the application
4. Elastic Beanstalk deploys the new version
5. Health checks ensure successful deployment

## 📁 Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── se/systementor/supershoppen/
│   │       ├── controllers/
│   │       ├── models/
│   │       ├── repositories/
│   │       ├── services/
│   │       └── security/
│   └── resources/
│       ├── static/
│       ├── templates/
│       └── application-*.properties
└── test/
```

## 🔒 Security

- OAuth2 authentication with GitHub
- Spring Security for authorization
- Encrypted password storage
- CSRF protection
- Secure session management

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the [MIT License](LICENSE) - click to view the full license text.
