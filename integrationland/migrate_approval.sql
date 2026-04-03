-- ══════════════════════════════════════════════════════════
-- MIGRATION: Add approval to existing users
-- Run this ONCE in MySQL after restarting the Spring Boot server
-- ══════════════════════════════════════════════════════════

USE land_soil_system;

-- Step 1: Hibernate will auto-add the column via ddl-auto=update
-- But all existing users will default to approved=0 (denied).
-- Run the following to approve all existing users immediately:

UPDATE users SET approved = 1 WHERE approved = 0;

-- (Optional) If you only want to auto-approve the admin account:
-- UPDATE users SET approved = 1 WHERE role = 'ROLE_ADMIN';

-- Verify:
SELECT user_id, full_name, email, role, approved FROM users ORDER BY user_id;
