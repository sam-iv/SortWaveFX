# SortWaveFX

A fairly simple and interactive application for visualising various sorting algorithms, built using JavaFX. 

**Note:** This a reboot of and older project, currently being reengineered from scratch to improve its design and architecture by employing better design practices. The code in the [**main**](https://github.com/sam-iv/SortWaveFX/) branch represents my first attempt at this project. This branch is currently under active development.

## Getting Started

SortWaveFX is built with Apache Maven. Please ensure you have [**JDK21**](https://learn.microsoft.com/en-gb/java/openjdk/download)
(or newer).

1. Clone the repository:
   ```bash
   git clone https://github.com/sam-iv/SortWaveFX.git
   ```
2. Go to the project directory and run the application using Maven:

3. ### For Windows (Powershell)
   ```powershell
   .\mvnw clean javafx:run
   ```
   ### For MacOS/Linux
   ```bash
   ./mvnw clean javafx:run
   ```
## How to Use

<!-- PLACEHOLDER -->
- Select a sorting algorithm
- Adjust size and speed of visualisation
- Watch the algorithm sort and data array.

## Technologies Used

SortWaveFX is built using Java 21 and makes use of the JavaFX framework for UI. The main application package is ``io.github.samiv.sortwavefx``.

- ### Core Language & Framework:
  - **Java 21** (21.0.7) - Core Language for the application
  - **JavaFX** (21.0.8) - Primary Framework for GUI
  - **Maven** (3.9.8) - Build Tool
- ### UI Libraries:
  - **ControlsFX** (11.1.2) - Better UI Controls than JavaFX
  - **FormsFX** (11.6.0) - Library for user-input forms
  - **ValidatorFX** (0.4.0) - Real-time validation feedback to UI components
  - **Ikonli** (12.3.1) - Scalable icon fonts
- ### Additional:
  - **FXGL** (17.2) - Game Engine for rendering and animations.
  - **JUnit 5** (5.10.2) - Unit testing framework
