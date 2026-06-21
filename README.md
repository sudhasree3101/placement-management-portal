Markdown# <p align="center">🎓 PLACEMENT MANAGEMENT PORTAL 🚀</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="SpringBoot"/>
  <img src="https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB" alt="React"/>
  <img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL"/>
</p>

<p align="center">
  <b>An automated ecosystem bridging the gap between ambitious students, corporate recruiters, and Training & Placement Officers (TPOs).</b>
</p>

---

## ⚡ Key Highlights
* **🔒 Secure Core:** Armed with Spring Security and JWT stateless authentication token flows.
* **⚡ Live Updates:** Interactive tracking system for tracking corporate recruitment lifecycles.
* **📊 Insightful Metrics:** Clean dashboards showcasing placement percentages and drive statistics.

---

## 🧩 Portal Architecture

                   ┌─────────────────────────┐
                   │   React Client Engine   │
                   └────────────┬────────────┘
                                │ (REST Endpoints)
                                ▼
                   ┌─────────────────────────┐
                   │   Spring Boot Layer     │
                   └────────────┬────────────┘
                                │ (Data JPA)
                                ▼
                   ┌─────────────────────────┐
                   │    MySQL Storage Hub    │
                   └─────────────────────────┘

---

## 💎 Module Breakdowns

### 🧑‍🎓 Student Workspace
* ✨ **Resume Profiler:** Build out profiles with real-time academic records and resume links.
* ✨ **Instant Apply:** Review active enterprise recruitment operations and apply instantly.
* ✨ **Status Pipeline:** Track applications directly through `Applied`, `Shortlisted`, `Interviewing`, or `Selected`.

### 👔 Placement Panel (Admin)
* ⚙️ **Drive Management:** Create, configure, and publish live hiring campaigns.
* ⚙️ **Verification Gate:** Evaluate and approve student registrations before drive entry.
* ⚙️ **Data Export:** Generate detailed breakdowns of compensation structures and placement velocities.

### 🏢 Corporate Interface
* 💼 **Posting Pipeline:** Set clear minimum requirements like branch boundaries or CGPA limits.
* 💼 **Candidate Extraction:** Filter eligible profiles and export student lists easily.

---

## 🛠️ Technology Stack

| Ecosystem | Technology Used | Use Case |
| :--- | :--- | :--- |
| **Frontend UI** | React.js, Tailwind CSS / Material UI | Component Architecture & Styling |
| **Client Routing** | React Router DOM & Axios | View Transitions & Async HTTP Requests |
| **Backend Core** | Java, Spring Boot | Rest API Development & Business Logic |
| **Security Layer** | Spring Security & JWT | Access Controls & Resource Protection |
| **Persistence** | Spring Data JPA, Hibernate | Object-Relational Database Mapping |
| **Database** | MySQL | Structured Data Storage |

---

## ⚙️ Deployment & Launch Sequence

### 🗄️ 1. Database Creation

Execute the following query inside your local MySQL instance to build your schema:

```sql
CREATE DATABASE placement_portal;
Ensure your backend file src/main/resources/application.properties targets this schema name correctly:Propertiesspring.datasource.url=jdbc:mysql://localhost:3306/placement_portal
spring.jpa.hibernate.ddl-auto=update
☕ 2. Backend Bootup (Spring Boot)Open your terminal in the backend root directory and launch the application:Bashmvn clean install
mvn spring-boot:run
The backend server will start running at: http://localhost:8080⚛️ 3. Frontend Ignition (React)Open a separate, clean terminal window inside your frontend folder directory:Bashnpm install
npm start
The portal interface will automatically load at: http://localhost:3000🛣️ API Routing DirectoryAccess ActionRequest TypeTarget Endpoint RouteClearanceAuthenticationPOST/api/auth/login✨ Public AccessRegistrationPOST/api/students/register✨ Public AccessFetch DrivesGET/api/jobs/active🔑 Student / AdminPost JobPOST/api/jobs/create🛡️ Admin / CorporateTrack ApplicationGET/api/applications/student/{id}🔑 Student👩‍💻 Lead Architect🌐 Live Portfolio Website: portfolio-gamma-ochre-59.vercel.app💼 Professional LinkedIn: linkedin.com/in/sudha-sree-reddy-96a6a8316🐙 Source Code Profile: github.com/sudhasree3101📄 LicenseThis repository is published under the open-source MIT License. Feel free to use and adapt it for educational settings.
