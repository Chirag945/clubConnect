# ClubConnect 🎓🎉

A Java + MySQL application that helps students discover and register for university club events based on their interests. The system supports secure registration, interest tagging, event recommendations, and RSVP management.

---

## 🚀 Features

### 👨‍🎓 Student Functionality (Completed)
- Register and log in with secure credentials
- Set personal interests (e.g., tech, art, sports)
- View personalized event suggestions
- RSVP or cancel event participation
- View all registered events
- Data persisted in normalized MySQL schema (3NF)

### 🏛️ Club Functionality (In Progress by Team)
- Register clubs with email/password
- Create and manage events under their name
- View list of registered participants

### 📅 Event Functionality (In Progress by Team)
- Create events with tags, capacity, date/time, and fee
- Automatically match events to interested students
- Enforce capacity and registration limits

---

## ⚙️ Tech Stack

| Component | Technology        |
|----------|-------------------|
| Language | Java (pure OOP)   |
| Database | MySQL             |
| Design   | MVC + DAO pattern |
| Tools    | IntelliJ IDEA, GitHub |

---

## 🧱 System Design

### ✅ OOP Principles Used
- **Encapsulation**: Private fields with controlled access
- **Abstraction**: DAO layer hides SQL logic
- **Inheritance**: Shared structure (e.g., `User` conceptually)
- **Modularity**: DAO, Model, CLI layers clearly separated

### 🗃️ Database Schema (Normalized to 3NF)
            
- **Student**: | **studentID** | name | email | password |
  
- **Club**: | **clubID** | name | email | password |
  
- **Events**: | **eventID** | _clubID_ | title | date | venue | tags | capacity | regFee |
- 
- **Interests**: **|studentID | tags |** 
- 
- **RSVP**: **| studentID | _eventID_ |** status |

---

## CONTRIBUTORS
- Chirag Khatri - f20221091@pilani.bits-pilani.ac.in
- Aashna Ased - f20220536@pilani.bits-pilani.ac.in
- Samyak Savi - f20220635@pilani.bits-pilani.ac.in
