# Ellenőrzési technikák dokumentáció
## Statikus analízis
### Hozzáadtam a Sonart a projekthez, létrehoztam egy tokent, illetve a pom.xml-be beleraktam a Sonarhoz szükséges adatokat.
### Az alábbi hibákat javítottam ki a kódban, amik megtalálhatóak a github oldalán a static-analysis-hez tartozó pull requestnél:
#### GraphicDesigner.java
- Make "edges" private or transient.
- Replace the type specification in this constructor call with the diamond operator ("<>").
- Remove the "menuCard" field and declare it as a local variable in the relevant methods.
- Remove the "bQuit" field and declare it as a local variable in the relevant methods.
- Remove the "gameCard" field and declare it as a local variable in the relevant methods.
- Remove the "lWelcome" field and declare it as a local variable in the relevant methods.
- Remove the "pMap" field and declare it as a local variable in the relevant methods.
- Remove the "pInformation" field and declare it as a local variable in the relevant methods.
- Remove the "pButtons" field and declare it as a local variable in the relevant methods.
- Remove the "lMechText" field and declare it as a local variable in the relevant methods.
- Remove the "lSabText" field and declare it as a local variable in the relevant methods.
- Remove the "lActionText" field and declare it as a local variable in the relevant methods.
- Remove the "lMechPoints" field and declare it as a local variable in the relevant methods.
- Remove the "lSabPoints" field and declare it as a local variable in the relevant methods.
- Remove the "lActionPoints" field and declare it as a local variable in the relevant methods.
- Remove the "bMovePipe" field and declare it as a local variable in the relevant methods.
- Remove the "bMove" field and declare it as a local variable in the relevant methods.
- Remove the "bMakeSlipperry" field and declare it as a local variable in the relevant methods.
- Remove the "bMakeSticky" field and declare it as a local variable in the relevant methods.
- Remove the "bBreak" field and declare it as a local variable in the relevant methods.
- Remove the "bRepair" field and declare it as a local variable in the relevant methods.
- Remove the "bPlace" field and declare it as a local variable in the relevant methods.
- Remove the "bPickup" field and declare it as a local variable in the relevant methods.
- Remove the "bChangePumpDirection" field and declare it as a local variable in the relevant methods.
- Remove the "bEndGame" field and declare it as a local variable in the relevant methods.
- Remove the "bEndTurn" field and declare it as a local variable in the relevant methods.
- Use static access with "javax.swing.WindowConstants" for "DISPOSE_ON_CLOSE".
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Remove this unused private "drawNodes" method.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Remove this unused private "drawEdges" method.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Add a default case to this switch.
- Replace this "switch" statement by "if" statements to increase readability.
- Remove this redundant block.
#### Main.java
- Remove this unused import 'display'.
- Remove this unused import 'interfaces'.
- This block of commented-out lines of code should be removed.
#### DisplayCistern.java
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
#### DisplayField.java
- Remove this unused import 'java.awt'.
#### DisplayNode.java
- Change the visibility of this constructor to "protected".
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
#### DisplayPipe.java
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Use isEmpty() to check whether the collection is empty or not.
#### DisplayPump.java
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
#### DisplaySource.java
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
#### Cistern.java
- Refactor this method to reduce its Cognitive Complexity from 17 to the 15 allowed.
- Save and re-use this "Random".
- Replace this use of System.out by a logger.
- Replace this use of System.out by a logger.
#### Field.java
- Make "neighbors" private or transient.
- Make "players" private or transient.
- Change the visibility of this constructor to "protected".
- Replace the type specification in this constructor call with the diamond operator ("<>").
- Replace the type specification in this constructor call with the diamond operator ("<>").
- Rename this method name to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
#### Game.java
- Rename this field "ROUND_BEGIN_ACTION_NUMBER" to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Make this final field static too.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Replace this use of System.out by a logger.
- Replace this use of System.out by a logger.
- A "NullPointerException" could be thrown; "oldEnd" is nullable here.
- Replace this use of System.out by a logger.
#### Graphics.java
- Remove the "menuCard" field and declare it as a local variable in the relevant methods.
- Remove the "gameCard" field and declare it as a local variable in the relevant methods.
- Remove the "lWelcome" field and declare it as a local variable in the relevant methods.
- Remove the "pInformation" field and declare it as a local variable in the relevant methods.
- Remove the "pButtons" field and declare it as a local variable in the relevant methods.
- Remove the "lMechText" field and declare it as a local variable in the relevant methods.
- Remove the "lSabText" field and declare it as a local variable in the relevant methods.
- Remove the "lActionText" field and declare it as a local variable in the relevant methods.
- Remove this unused "moveIC" private field.
- Remove this unused "movePipeIC" private field.
- Remove this unused "changePumpDirectionIC" private field.
- Make "clicked" transient or serializable.
- Use static access with "javax.swing.WindowConstants" for "DISPOSE_ON_CLOSE".
- Replace this use of System.out by a logger.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Replace this use of System.out by a logger.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Replace this use of System.out by a logger.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Use static access with "javax.swing.WindowConstants" for "DISPOSE_ON_CLOSE".
- Replace this use of System.out by a logger.
- Complete the task associated to this TODO comment.
- Replace this use of System.out by a logger.
- Complete the task associated to this TODO comment.
- Replace this use of System.out by a logger.
#### Map.java
- Make this final field static too.
- Make this final field static too.
- Make this final field static too.
- Make this final field static too.
- Make "draggingNode" transient or serializable.
- Make "nodes" transient or serializable.
- Make "edges" transient or serializable.
- A "NullPointerException" could be thrown; "n1" is nullable here.
- Replace this negation and "anyMatch()" with "noneMatch()".
- Replace this negation and "anyMatch()" with "noneMatch()".
#### Mechanic.java
- Rename this method name to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Replace this if-then-else statement by a single return statement.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation. 
- Merge this if statement with the enclosing one.
#### Pipe.java
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
- Use isEmpty() to check whether the collection is empty or not.
- Replace this use of System.out by a logger.
- Replace this use of System.out by a logger.
- Save and re-use this "Random".
- Save and re-use this "Random".
#### Player.java
- Change the visibility of this constructor to "protected".
- Merge this if statement with the enclosing one.
#### PlayerIcon.java
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
- Rename this local variable to match the regular expression '^[a-z][a-zA-Z0-9]*$'.
#### Pump.java
- Save and re-use this "Random".
- Replace this use of System.out by a logger.
- Replace this use of System.out by a logger.
- Remove this expression which always evaluates to "true"
- Remove this expression which always evaluates to "true"
#### Saboteur.java
- Merge this if statement with the enclosing one.
- Add a nested comment explaining why this method is empty, throw an UnsupportedOperationException or complete the implementation.
#### Timer.java
- Replace the type specification in this constructor call with the diamond operator ("<>").
- Replace the type specification in this constructor call with the diamond operator ("<>").
#### Vec2.java
- Make x a static final constant or non-public and provide accessors if needed.
- Make y a static final constant or non-public and provide accessors if needed.
#### GameTest.java
- Remove this 'public' modifier.
#### PipeTest.java
- Rename "pump1" which hides the field declared at line 14.
#### PlayerTest.java
- Remove this 'public' modifier.
#### PumpTest.java
- Remove this 'public' modifier.