-- Create database if it does not exist
CREATE DATABASE IF NOT EXISTS placement_portal;
USE placement_portal;

-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    enabled BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_user_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Students Table
CREATE TABLE IF NOT EXISTS students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    department VARCHAR(100),
    cgpa DOUBLE,
    skills TEXT,
    graduation_year INT,
    resume_url VARCHAR(255),
    placement_status VARCHAR(30) DEFAULT 'UNPLACED',
    user_id BIGINT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_student_email (email),
    INDEX idx_student_status (placement_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Recruiters Table
CREATE TABLE IF NOT EXISTS recruiters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(150) NOT NULL,
    hr_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    website VARCHAR(150),
    industry VARCHAR(100),
    user_id BIGINT UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    INDEX idx_recruiter_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. Placement Drives Table
CREATE TABLE IF NOT EXISTS placement_drives (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(150) NOT NULL,
    job_role VARCHAR(100) NOT NULL,
    package_offered DOUBLE,
    eligibility_criteria TEXT,
    location VARCHAR(150),
    drive_date DATE,
    registration_deadline DATE,
    job_description TEXT,
    INDEX idx_drive_company (company_name),
    INDEX idx_drive_role (job_role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Applications Table
CREATE TABLE IF NOT EXISTS applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    placement_drive_id BIGINT NOT NULL,
    status VARCHAR(30) DEFAULT 'APPLIED',
    applied_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uq_student_drive (student_id, placement_drive_id),
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (placement_drive_id) REFERENCES placement_drives(id) ON DELETE CASCADE,
    INDEX idx_app_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Interview Rounds Table
CREATE TABLE IF NOT EXISTS interview_rounds (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    application_id BIGINT NOT NULL,
    round_name VARCHAR(100) NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    feedback TEXT,
    scheduled_date TIMESTAMP NULL DEFAULT NULL,
    FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE,
    INDEX idx_round_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Notifications Table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    message TEXT NOT NULL,
    recipient_email VARCHAR(100) NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_notif_recipient (recipient_email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Placement Records Table
CREATE TABLE IF NOT EXISTS placement_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    company_name VARCHAR(150) NOT NULL,
    job_role VARCHAR(100) NOT NULL,
    package_amount DOUBLE,
    joining_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    INDEX idx_record_student (student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Seed initial admin user credentials (password is 'admin123' bcrypt hashed)
INSERT INTO users (name, email, password, role, enabled) 
VALUES ('Super Admin', 'admin@placement.com', '$2a$10$wKxI3yN33oZ34xR7.4t21.eLhK7W3d6zD5w0401p0mP2Z4l8XvEqm', 'ADMIN', true)
ON DUPLICATE KEY UPDATE email=email;

-- Seed placement officer user credentials (password is 'officer123' bcrypt hashed)
INSERT INTO users (name, email, password, role, enabled) 
VALUES ('Placement Officer', 'officer@placement.com', '$2a$10$g1kC3J93o.wB25xS7.4t21.eLhK7W3d6zD5w0401p0mP2Z4l8XvEqm', 'PLACEMENT_OFFICER', true)
ON DUPLICATE KEY UPDATE email=email;
