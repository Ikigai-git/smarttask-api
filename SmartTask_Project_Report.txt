# AI-Enabled Smart Task Management System for Corporate Workflows PROJECT REPORT

## Title
AI-Enabled Smart Task Management System for Corporate Workflows

## Candidate Details
- Student Name: ____________________
- Register Number: ____________________
- Department: ____________________
- Institution: ____________________
- Academic Year: 2025-2026
- Guide Name: ____________________

## Certificate
This is to certify that the project report titled **"AI-Enabled Smart Task Management System for Corporate Workflows"** is a bonafide work carried out by the above candidate under my guidance and supervision for partial fulfillment of the degree requirements.

- Guide Signature: ____________________
- HoD Signature: ____________________
- External Examiner Signature: ____________________

## Declaration
I hereby declare that this project report is my original work and has not been submitted in part or full to any other institution for the award of any degree or diploma.

- Student Signature: ____________________
- Date: ____________________

## Acknowledgement
I express my sincere gratitude to my guide, department faculty, institution, and my peers for their continuous support and valuable feedback throughout the development of this project. I also thank my family for constant encouragement.

## Abstract
AI-Enabled Smart Task Management System for Corporate Workflows is a web-based enterprise workflow platform that supports task assignment, progress tracking, ticket handling, activity logging, and AI-assisted productivity enhancements. The system is designed for three user roles: Super Admin, Manager, and Employee. Super Admin manages users and monitors system-wide activity, Managers create and supervise tasks, and Employees execute assigned work and raise support tickets.

The project uses a Spring Boot backend with JWT-based security and a React frontend with role-based dashboards. AI-Enabled Smart Task Management System for Corporate Workflows integrates AI services for task description rewriting, summarization, and deadline suggestion through an external language model API. The platform also includes an OTP-based password reset workflow via SMTP email. Overall, the solution improves clarity, accountability, and communication in organizational task execution.

## Table of Contents
1. Introduction
2. Problem Statement
3. Objectives
4. Scope of the Project
5. Literature/Existing System and Proposed System
6. System Requirements
7. System Analysis and Design
8. Implementation
9. Testing and Validation
10. Results and Discussion
11. Limitations
12. Future Enhancements
13. Conclusion
14. References
15. Appendix

## 1. Introduction
Modern teams require transparent and accountable task execution with minimal communication gaps. Conventional methods such as spreadsheets, chat threads, and unstructured email lead to missed deadlines, poor visibility, and weak follow-up.

AI-Enabled Smart Task Management System for Corporate Workflows addresses these gaps by providing:
- centralized task lifecycle management,
- clear role-based responsibilities,
- ticket raising and resolution workflow,
- notification-based communication,
- AI assistance for better task clarity and planning,
- and secure authentication with OTP password reset.

The application is implemented as a full-stack system:
- Backend: Spring Boot REST API with MySQL
- Frontend: React with Tailwind CSS
- Security: JWT authentication and BCrypt password hashing
- AI Integration: Groq-compatible chat completion API

## 2. Problem Statement
Organizations with multiple hierarchical roles often face the following issues:
- no unified platform for assigning and tracking tasks,
- poor progress visibility between managers and employees,
- delayed ticket resolution,
- inconsistent task descriptions causing misunderstanding,
- and lack of actionable dashboards for decision-making.

The problem is to build a secure and scalable platform that streamlines assignment, monitoring, communication, and reporting while reducing ambiguity in daily operations.

## 3. Objectives
Primary objectives of AI-Enabled Smart Task Management System for Corporate Workflows are:
- design a role-based task management portal for Super Admin, Manager, and Employee,
- implement secure login with JWT and protected APIs,
- enable managers to create, assign, and monitor tasks,
- enable employees to post task updates and raise tickets,
- provide dashboard analytics for each role,
- integrate AI features for rewriting/summarizing tasks and suggesting deadlines,
- provide OTP-based password reset through registered email,
- and maintain auditable activity logs.

## 4. Scope of the Project
### In Scope
- user management and status control (active/deactivated),
- role-based dashboards and route protection,
- task creation, updates, status transitions, and deletion,
- ticket raising, manager reply, and status updates,
- notification tracking with unread count and mark-as-read,
- AI recommendation persistence,
- password reset via OTP email workflow,
- activity logging.

### Out of Scope
- mobile native applications,
- SSO/LDAP integration,
- multi-tenant organization separation,
- advanced analytics and forecasting,
- file storage service integration for attachments.

## 5. Literature/Existing System and Proposed System
### Existing System
Typical existing approaches rely on spreadsheets, emails, and chat tools. These are fragmented, hard to audit, and not role-aware. Task status and accountability are often unclear.

### Proposed System
AI-Enabled Smart Task Management System for Corporate Workflows provides a unified, role-based workflow platform with:
- centralized data and APIs,
- secure authentication,
- clear task/ticket ownership,
- proactive notifications,
- AI-assisted productivity,
- and administrative oversight through analytics and logs.

### Advantages
- Better visibility of progress and pending items,
- Faster communication through notifications,
- Reduced ambiguity using AI rewriting,
- Improved security through JWT and hashed passwords,
- Stronger accountability through logs and role controls.

## 6. System Requirements
### Hardware Requirements
- Processor: Intel i5 or above
- RAM: 8 GB minimum
- Storage: 10 GB free space

### Software Requirements
- Operating System: Windows 10/11 or equivalent
- JDK: Java 21
- Build Tool: Maven
- Backend Framework: Spring Boot 3.x
- Frontend: React 19 + Tailwind CSS
- Database: MySQL 8+
- API Testing: Postman
- Browser: Chrome/Edge/Firefox

### Backend Dependencies (Major)
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- spring-boot-starter-validation
- spring-boot-starter-mail
- mysql-connector-j
- jjwt (api/impl/jackson)

## 7. System Analysis and Design
### 7.1 Architecture
AI-Enabled Smart Task Management System for Corporate Workflows follows a layered architecture:
- Presentation Layer: React UI
- API Layer: Spring Boot Controllers
- Business Layer: Service classes
- Data Access Layer: JPA repositories
- Persistence Layer: MySQL database

Flow:
1. User interacts with React UI.
2. Frontend sends REST request with JWT token.
3. Spring Security validates JWT using filter.
4. Controller delegates to service.
5. Service performs business logic and repository operations.
6. Response returned as JSON to UI.

### 7.2 Role Design
- **SUPERADMIN**: user management, global dashboard, activity log monitoring
- **MANAGER**: task creation and supervision, ticket response, notifications
- **EMPLOYEE**: task execution updates, ticket raising, notification consumption

### 7.3 Core Modules
- Authentication and Authorization
- User Management
- Task Management
- Ticket Management
- Notification Management
- Dashboard Analytics
- AI Recommendations
- Activity Logging

### 7.4 Database Design (Entity Summary)
- `login`: username (PK), password, role, status
- `user_details`: userId (PK), username, name, email, gender, contact, department, designation, address, status
- `tasks`: taskId (PK), managerUsername, employeeUsername, title, description, priority, startDate, deadline, status, attachment
- `task_updates`: updateId (PK), taskId, employeeUsername, updateMessage, updateDate, filePath
- `tickets`: ticketId (PK), employeeUsername, managerUsername, subject, message, createdDate, reply, replyDate, status
- `notifications`: notificationId (PK), sentTo, sentBy, message, dateSent, type, status
- `activitylogs`: logId (PK), username, action, timestamp, details
- `ai_recommendations`: recId (PK), taskId, username, recommendation, createdDate

### 7.5 API Design (Representative Endpoints)
- Auth: `/api/auth/login`, `/api/auth/forgot-password/request-otp`, `/api/auth/forgot-password/verify-otp`
- Users: `/api/users/all`, `/api/users/add`, `/api/users/{username}`, `/api/users/{username}/status`
- Tasks: `/api/tasks/create`, `/api/tasks/all`, `/api/tasks/manager/{username}`, `/api/tasks/employee/{username}`, `/api/tasks/{taskId}/status`, `/api/tasks/update`
- Tickets: `/api/tickets/raise`, `/api/tickets/all`, `/api/tickets/{ticketId}/reply`, `/api/tickets/{ticketId}/status`
- Notifications: `/api/notifications/{username}`, `/api/notifications/{username}/count`, `/api/notifications/{username}/read-all`
- Dashboard: `/api/dashboard/admin`, `/api/dashboard/manager/{username}`, `/api/dashboard/employee/{username}`
- AI: `/api/ai/rewrite`, `/api/ai/summarize`, `/api/ai/suggest-deadline`
- Logs: `/api/logs/all`, `/api/logs/{username}`

### 7.6 Security Design
- Stateless JWT authentication
- All routes except `/api/auth/**` require authentication
- BCrypt password hashing
- Role information included in JWT claims
- CORS configured for frontend origin (`http://localhost:3000`)

## 8. Implementation
### 8.1 Backend Implementation
The backend is developed using Spring Boot and organized into:
- `controller` package for REST endpoints,
- `service` package for business logic,
- `repository` package using Spring Data JPA,
- `model` package for entity mapping,
- `security` package for JWT and security filter chain.

Key behaviors implemented:
- task assignment initializes with status `Pending`,
- employee updates trigger manager notifications,
- raised tickets notify managers,
- manager replies auto-mark ticket as `Resolved`,
- OTP reset stores temporary OTP in memory with 10-minute expiry,
- AI outputs are persisted in recommendations table.

### 8.2 Frontend Implementation
React frontend uses:
- role-based routing and protected routes,
- context-based authentication state,
- axios interceptor for bearer token injection,
- dedicated dashboard pages per role,
- modal-driven forms (task create, profile edit, forgot password),
- charts for admin analytics.

UI provides:
- responsive dashboard layout,
- role-specific navigation tabs,
- real-time feedback and validation messages,
- visual status indicators for tasks/tickets/users.

### 8.3 AI Feature Implementation
The AI module sends prompts to a chat completion API and supports:
- task description rewrite for clarity,
- task summarization for quick understanding,
- deadline suggestion based on title, description, and priority.

Generated results can be stored for future reference and auditing.

## 9. Testing and Validation
### 9.1 Testing Approach
- Unit-level behavior checks at service methods
- API endpoint validation using Postman
- UI workflow testing using browser-based manual testing
- Integration checks across login-task-ticket-notification pipeline

### 9.2 Functional Test Cases (Sample)
1. Login with valid credentials returns JWT and role.
2. Deactivated user cannot login.
3. Manager creates task and employee receives notification.
4. Employee posts update and manager receives notification.
5. Employee raises ticket and manager receives alert.
6. Manager replies to ticket and status changes to Resolved.
7. Admin toggles user active/deactive successfully.
8. Forgot password OTP request validates user, email, and SMTP settings.
9. OTP verification resets password with valid OTP.
10. Dashboard counters match database records.

### 9.3 Observed Results
- Core task lifecycle worked correctly.
- Ticket flow from raise to resolve worked correctly.
- Role-based access and route protection worked.
- AI responses were generated when API key was configured.
- SMTP-based OTP reset worked after valid mail credentials and app password setup.

## 10. Results and Discussion
The project successfully achieved the intended objectives:
- unified workflow for tasks and tickets,
- controlled access with role-based interfaces,
- measurable dashboard insights,
- integrated AI assistance that improves communication quality,
- and practical account recovery with OTP email.

The platform is suitable for small to medium teams and can be extended for production environments with additional operational hardening.

## 11. Limitations
- OTP storage is in-memory; server restart clears pending OTPs.
- JWT secret is currently static in code and should be externalized.
- Role checks are mostly frontend-driven; backend can enforce finer role-level authorization.
- Attachment management is modeled but external storage integration is minimal.
- Test coverage can be increased with automated integration tests.

## 12. Future Enhancements
- Redis-based OTP and session state support
- Refresh token mechanism and token revocation
- Role/permission matrix at method level using Spring Security annotations
- Dockerized deployment and CI/CD pipeline
- Email templating and retry queue (RabbitMQ/Kafka)
- File upload integration with cloud object storage
- Advanced analytics (SLA, productivity trends)
- Mobile app support

## 13. Conclusion
AI-Enabled Smart Task Management System for Corporate Workflows delivers a complete role-based enterprise workflow application with secure authentication, task/ticket management, notifications, activity logs, and AI-based productivity assistance. The system improves operational transparency and task execution discipline while remaining modular and extensible. The implementation demonstrates practical full-stack engineering with modern frameworks and can serve as a strong base for real organizational deployment.

## 14. References
1. Spring Boot Documentation: https://spring.io/projects/spring-boot
2. Spring Security Documentation: https://spring.io/projects/spring-security
3. React Documentation: https://react.dev
4. Tailwind CSS Documentation: https://tailwindcss.com/docs
5. MySQL Documentation: https://dev.mysql.com/doc
6. JWT (RFC 7519): https://datatracker.ietf.org/doc/html/rfc7519
7. Groq API Docs: https://console.groq.com/docs

## 15. Appendix
### Appendix A: Configuration Notes
- Backend profile: `local`
- Database: `AI-Enabled Smart Task Management System for Corporate Workflows_db`
- API default base URL: `http://localhost:8080/api`
- Frontend default URL: `http://localhost:3000`

### Appendix B: Build and Run Commands
Backend:
```bash
cd AI-Enabled Smart Task Management System for Corporate Workflows-api/AI-Enabled Smart Task Management System for Corporate Workflows-api
mvn clean install
mvn spring-boot:run
```

Frontend:
```bash
cd AI-Enabled Smart Task Management System for Corporate Workflows-ui/AI-Enabled Smart Task Management System for Corporate Workflows-ui
npm install
npm start
```

### Appendix C: Suggested Screenshot List for Final Submission
- Login screen
- Forgot password OTP screen
- Super Admin dashboard
- Manager create task screen
- Employee task updates screen
- Ticket raise and reply screens
- Notifications panel
- Activity logs screen
- AI rewrite/deadline feature usage


