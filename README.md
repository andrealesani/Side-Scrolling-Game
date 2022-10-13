# RPG SideScroller

"RPG SideScroller" is the final project of the course "Advanced Programming for Interactive Systems" at Paris-Saclay
university.

## 📖 Documentation

Documentation can be found [here](https://sidescroller-javadoc.vercel.app/).

Otherwise it can be generated with Javadoc.

## 🏗 Installation

### ✅ Recommended installation

Under [Releases folder](https://github.com/andrealesani/side-scrolling-game/releases/tag/v0.1) you can find the
installation setup for both Windows and macOS versions.

### ⚙️ Compiling and running

🚧 **Requirements**

- [JDK 18](https://jdk.java.net/18/)
- [Maven](https://maven.apache.org/download.cgi)

🔨 **Compiling**

Clone the project, unzip it, and run `mvn clean package` inside the root folder.

This will generate a `target` folder and inside it you will find a file called `sidescroller-1.0-SNAPSHOT-shaded.jar`.

🚀 **Running**

Execute `java -jar sidescroller-1.0-SNAPSHOT-shaded.jar`.

## ✨ Features

The project will consist of a side-scrolling video game. The game will have the following features:

- The player will choose the character and the map they want to play (menu).
- The game will include a variety of enemies with different movements.
- The character will have both melee weapons and a shield to protect itself.
- The player will have both life points and stamina that will be visible in the top left corner of the screen: blocking
  will decrease the stamina and receiving damage from enemies will decrease the life points.
- When reaching 0 life points, a “game over” menu will appear, presenting different options such as “restart” and “quit”.
- A music player will be visible in the top right corner to choose the preferred soundtrack for the game.
- The background will move along with the player (side scrolling).

## 👨‍💻 Contributors

- **Andrea Alesani - M2 EIT HCID**
- **Nicolò Sonnino - M2 EIT HCID**
