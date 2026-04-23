# Integrationland: Render Deployment Guide

This guide provides step-by-step instructions for deploying the Integrationland (Spring Boot) application on [Render](https://render.com/).

## Prerequisites
1.  A **GitHub** or **GitLab** account with your project pushed to a repository.
2.  A **Render** account.
3.  A **MySQL Database** (Render does not provide managed MySQL).
    *   *Recommendation*: Use [Aiven](https://aiven.io/) or [PlanetScale](https://planetscale.com/) for a free/low-cost MySQL instance.

---

## Option A: Deploy via Blueprint (Recommended)
This project includes a `render.yaml` file that automates the setup.

1.  Log in to [Render Dashboard](https://dashboard.render.com/).
2.  Click **New +** and select **Blueprint**.
3.  Connect your repository.
4.  Render will detect the `render.yaml` file.
5.  Fill in the required **Environment Variables** (Database URL, Credentials, etc.).
6.  Click **Apply**. Render will automatically create the Web Service for you.

---

## Option B: Manual Setup
If you prefer to configure the service manually:

1.  Click **New +** and select **Web Service**.
2.  Connect your repository.
3.  Configure the service:
    *   **Name**: `integrationland`
    *   **Runtime**: Select **Docker**.
4.  In the **Environment** tab, add:
    *   `DATABASE_URL`: `jdbc:mysql://[HOST]:[PORT]/[DB_NAME]?useSSL=false`
    *   `DATABASE_USER`: `your_user`
    *   `DATABASE_PASSWORD`: `your_pass`
    *   `MAIL_USERNAME`: `your_email@gmail.com`
    *   `MAIL_PASSWORD`: `your_app_password`
    *   `JAVA_OPTS`: `-Xmx384m -Xms384m` (to fit in the Free Tier RAM)

---

## Important Notes
*   **Java Version**: This project uses **Java 25**. The Dockerfile is configured to use `eclipse-temurin:25`.
*   **Memory Management**: Spring Boot can be memory-heavy. We recommend the `-Xmx384m` flag to ensure it fits within Render's 512MB Free Tier limit.
*   **Database Connections**: Ensure your MySQL provider allows connections from `0.0.0.0/0` or the Render IP range.

---

## Troubleshooting
*   **Out of Memory**: If the logs show `Killed` or `OOM`, you may need to upgrade to a "Starter" plan with 1GB RAM or further tune the JVM.
*   **OTP Email Not Sending**: Verify that you are using a **Gmail App Password**, not your regular password.
