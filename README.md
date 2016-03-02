#Snake Game v.2 added features:

1. Options screen.
    1. Press ‘o’ at start of game to access options.
        1. snake speed
        2. grid layout – different numbers of squares in grid
        3. game window size
        4. snake color
        5. warp walls
        6. maze walls
        7. extended features: extra maze walls and axe
2. Warp walls.
    1. These allow the snake to pass through the edge walls rather than knocking itself unconscious and ending the game.
3. Maze wall.
    1. This is a grid line that counts as having hit the wall, even when warp walls are turned on.
4. User can set screen size.
    1. Not all sizes will work on all monitors.
    1. Window is not resizeable on the fly, but it is draggable to other parts of the screen.
5. User can set number of grid squares.
    1. Note that I carefully selected numbers of squares that will result in grids with no "partial squares" on screens even when resized.
6. Extended Features:
    1. Add a new maze wall to board every 3 kibbles.
    2. Axe halves the length of the snake.  Appears every 5 kibbles but disappears once the next kibble is eaten.
7. I converted the “createGUI” method to a SnakeGameWindow object – it made sense to me to encapsulate that, since it’s the entity that wraps around all of the display elements on the game board.
    1. SnakeGameWindow has a title bar and resizes with the game board.
8. As a result of creating Axe, Kibble object is now an abstract parent of Mouse and Axe.  These were developed with the assumption that only one of each ever exists at any point in the game.  It shouldn't be too difficult to modify them so that multiple instances of each could be used and have different images and fallback colors assigned to them.
9. I inherited this game from GitHub – there are LOTS of global variables in SnakeGame, and I have not vetted them all to identify which object should really be "in charge" of each.
10. I updated SnakeGameDisplayPanel so text blocks are approximately centered on the display.



## Image credits:
* Mouse.jpg was hand-drawn using GIMP software.
* Mouse2.jpg copied from http://free.clipartof.com/details/57-Free-Cartoon-Gray-Field-Mouse-Clipart-Illustration.  Rescaled and re-centered for use in this game.
* Axe was modified from clip art found at https://openclipart.org/detail/167417/fire-axe-2 – it seems to be open source.
