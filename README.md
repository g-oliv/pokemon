# Pokemon Challenge

Ash is catching Pokémon in an infinte 2D map. There is a Pokémon in each square of the map.

Ash starts in a square and catches the first Pokémon. He then moves to a square North, South, East or West from the current square and catches the Pokémon there. If he returns to a square he's visited before, there will not be a Pokémon there anymore. 

Given an input with the sequence of movements, how many Pokémon does Ash catch?

___
### Run the app

1. Clone the repo. 
2. Open the terminal in the root folder of the project
3. Run the command: 

for Unix
```
./mvnw clean install -DskipTests
./mvnw exec:java -Dexec.mainClass="com.pmchallenge.App"
```
 or for Windows
```
./mvnw.cmd clean install -DskipTests
./mvnw.cmd exec:java -Dexec.mainClass="com.pmchallenge.App"
```

#### Requiremnts
Java 17.0.2
