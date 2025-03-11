# To-Do List Application

## Overview
This is a full-stack To-Do List application built using **Java (Spring Boot), React, and MongoDB**. It follows the **Kanban methodology**, allowing users to organize tasks into **To Do, Doing, and Done** columns. Users can create, edit, delete, and move tasks between columns. The application also integrates **OpenAI GPT** for spelling and grammar correction.

## Features
- **Task Management:** Create, edit, delete, and move tasks.
- **Kanban Board:** Tasks are categorized into `To Do`, `Doing`, and `Done`.
- **Database:** Uses **MongoDB** as the NoSQL database.
- **Backend:** Built with **Spring Boot (Java)**.
- **Frontend:** Developed using **React.js**.
- **Testing:**
  - **Unit Tests** to ensure individual components work correctly.
  - **Integration Tests** to verify end-to-end functionality.
- **Exception Handling:** Graceful handling of unexpected errors.
- **OpenAI GPT Integration:** AI-powered spelling and grammar correction for task descriptions.

## Technologies Used
### Backend:
- Java (Spring Boot)
- Spring Data MongoDB
- OpenAI API for text correction
- JUnit, Mockito (Unit & Integration Tests)

### Frontend:
- React.js
- Axios (API calls)

### Database:
- MongoDB (NoSQL)

## Installation
### Prerequisites
- **Java 17+**
- **Node.js 16+**
- **MongoDB** (local or cloud-based)


## API Endpoints
### Task Management
| Method | Endpoint       | Description              |
|--------|---------------|--------------------------|
| GET    | /api/todo        | Fetch all tasks          |
| POST   | /api/todo        | Create a new task        |
| PUT    | /api/todo/{id}   | Update a task            |
| DELETE | /api/todo/{id}   | Delete a task            |
