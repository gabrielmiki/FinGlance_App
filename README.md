## FinGlance: Simplified Financial Tracking

FinGlance is a user-friendly application designed to simplify financial tracking, providing a practical tool for managing finances. The app offers users a clear and comprehensive overview of their current portfolio and its evolution over time, empowering them to make informed financial decisions.

### Architecture Overview

FinGlance is built on a layered architecture, ensuring a clean separation of concerns that promotes maintainability, scalability, and testability. The application is structured into three primary layers:

1. **User Interface (UI) Layer**
2. **Domain Layer**
3. **Data Layer**

#### User Interface (UI) Layer

The UI layer is responsible for presenting data to the user and responding to their interactions. It dynamically updates to reflect changes in data, whether they are triggered by user actions (e.g., button clicks) or external inputs (e.g., network responses).

Key components of the UI layer include:

- **UI Elements:** These components render data on the screen, created using traditional views or Jetpack Compose functions.
- **State Holders:** Classes like `ViewModel` that store data, expose it to the UI, and manage the logic related to UI state.

The primary responsibilities of the UI layer are:

- Rendering dynamic and responsive UI components.
- Handling user interactions and inputs.
- Observing state changes and updating the UI accordingly.

#### Domain Layer

The Domain layer acts as an intermediary between the UI and Data layers. It decouples the business logic from both data handling and UI concerns, ensuring that the core functionality of the application remains consistent and manageable.

#### Data Layer

The Data layer contains the application's business logic, which is central to data creation, storage, and modification. This layer is primarily composed of repository classes that handle:

- **Data Exposure:** Providing data to other parts of the application.
- **Data Management:** Centralizing and managing data changes.
- **Conflict Resolution:** Resolving conflicts that arise between multiple data sources.
- **Abstraction:** Abstracting data sources to ensure flexibility and maintainability.
- **Business Logic:** Containing and enforcing the core business rules of the application.

https://github.com/user-attachments/assets/28adae01-246d-48fa-8953-348ca1e4e4f0
