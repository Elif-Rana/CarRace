# CarRace
Here is a simple car race that could be played with two players.
The arrow keys are for the first player (green) and the second one's (red) keys are 'w','a','s','d'.
Black players are played by the computer with randomly selected directions.

## Some rules
- If you hit somewhere or someone you will be push the opposite way that you crash. (This is also valid for auto players.)
- If a black car collides somewhere or someone, there is a penalty for them to wait for a while where they are pushed.
- However, black players still might be hard to catch. :D
- The first one to complete a lap and cross the line wins!

### Tip-off
If you want to change the speed of black cars, you just need to update the integer parameter taken by the `game()` function in `Main.java`.
The speed is decided by "1000 milliseconds (1 sec) divided by that parameter". Higher number slower cars.
```
new CarRace().game(100); 
```

## Enjoy!
