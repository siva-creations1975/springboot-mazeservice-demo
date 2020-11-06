Maze Test
---------


We recognize that you may have questions about these requirements. For the purpose of this test, please make an assumption about the answer and note your assumptions in a readme.txt submitted with your solution. You should be ready to explain any assumptions and technical choices you have made during later interview stages.

There is no stated time limit but allow at least half a day. The important thing is to arrive at a solution that you are satisfied with and comfortable discussing with others.

The code must be representative of what you would produce “on the job”, by that we mean it must be clear, maintainable bug free and efficient.

The zipped project is for intellij and uses maven; you should feel free to use any other tools you are more comfortable with.  There are 2 classes in it and you should feel free to change these in any way you see fit, including deleting them and starting again.

The test is based on exploring any arbitrary maze (one is provided).




User Story 1
------------

As a world famous explorer of Mazes I would like a maze to exist
So that I can explore it

Acceptance Criteria

* A Maze (as defined in Maze1.txt consists of walls 'X', Empty spaces ' ', one and only one Start point 'S' and one and only one exit 'F'
* After a maze has been created the number of walls and empty spaces should be available to me
* After a maze has been created I should be able to put in a co ordinate and know what exists at that point




User Story 2
------------

As a world famous explorer of Mazes I would like to exist in a maze and be able to navigate it
So that I can explore it

Acceptance Criteria

* Given a maze the explorer should be able to drop in to the Start point (facing north)
* An explorer on a maze must be able to move forward
* An explorer on a maze must be able to turn left and right (changing direction the explorer is facing)
* An explorer on a maze must be able to declare what is in front of them
* An explorer on a maze must be able to declare all movement options from their given location
* An explorer on a maze must be able to report a record of where they have been in an understandable fashion


Note: We are looking for your Explorer solution to implement the above Acceptance criteria. We do not expect you to submit a fully autonomous maze solving Explorer implementation. 


Testing
-------

mvn test to run unit and acceptance tests





