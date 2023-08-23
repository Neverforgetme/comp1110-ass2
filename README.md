# COMP1110 Assignment 2
Contributor：

Harry Huang
Yilun Fan
Zec Wang


*This assignment is only for those enrolled in COMP1110 or COMP6710. COMP1140 students should look [here](https://gitlab.cecs.anu.edu.au/comp1110/comp1140-ass2).*

## Academic Honesty and Integrity

Honesty and integrity are of utmost importance. These goals are *not* at odds with being resourceful and working collaboratively. You *should* be resourceful, you should collaborate within your team, and you should discuss the assignment and other aspects of the course with others taking the class. However, *you must never misrepresent the work of others as your own*. If you have taken ideas from elsewhere or used code sourced from elsewhere, you must say so with *utmost clarity*. At each stage of the assignment you will be asked to submit a statement of originality, either as a group or as individuals. This statement is the place for you to declare which ideas or code contained in your submission were sourced from elsewhere.

Please read the ANU's [official position](http://academichonesty.anu.edu.au/) on academic honesty. If you have any questions, please ask me.

Carefully review the [statement of originality](originality.md) which you must complete.  Edit that statement and update it as you complete each state of the assignment, ensuring that when you complete each stage, a truthful statement is committed and pushed to your repo.

## Purpose

In this assignment you will exercise a number of major themes of the course, including software design and implementation, using development tools such as Git and IntelliJ, using JavaFX to build a user interface.   Above all, this assignment will emphasize group work.

## Assignment Deliverables

The assignment is worth 25% of your total assessment, and it will be marked out of 25. So each mark in the assignment corresponds to a mark in your final assessment for the course. Note that for some stages of the assignment you will get a _group_ mark, and for others you will be _individually_ marked. The mark breakdown and the due dates are described on the [deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) page.

Your work will be marked via your tutor accessing git, so it is essential that you carefully follow instructions for setting up and maintaining your group repository. At each deadline you will be marked according to whatever is committed to your repository at the time of the deadline. You will be assessed on how effectively you use git as a development tool.

## Problem Description

The assignment involves implementing in Java, a board game called [IQ-twist](http://www.smartgames.eu/en/smartgames/iq-twist)
made by the games developer [SmartGames](http://www.smartgames.eu/en).

![game](assets/iqtwist.png)

#### Objective 

The game is a puzzle; the objective is to place all eight colored
playing pieces onto a board comprising 32 locations (indents) on which
up to seven colored pegs may be arranged.  The player must place the
pieces such that they fit together correctly on the board, without
overlaps or gaps. Also, each of the pegs must be surrounded by a piece
of the same colour, meaning the piece must have a hole in the
necessary place. In the photo above, a blue peg at upper right is
surrounded by a blue piece, with the peg fitting exactly into a hole
in the blue piece.  The player will need to place the green and red
pieces so that they fit neatly on the green and red pegs, and to
complete the game will need to ensure that all pieces are placed with
no overlaps and no gaps.

A completed game:

![game](assets/a7A7b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0-350.png)


#### Challenges

A game starts with a challenge which involves zero or one pieces and
one or more pegs being placed.  Here is what the game above starts
like, ready to be solved (this happens to be challenge 1 that comes
with the game):

![game](assets/f3C4i6B0j2B0j1C0k3C0l4B0l5C0.png)

Notice that this particular challenge starts with one piece placed and
six pegs placed.  Note that the more constrained the player is, the
fewer options they have, and consequently the solution to the
challenge is, in general, simpler.  For example, many of the 'Wizard'
level challenges that come with the game (e.g. numbers 118-120) have
just three pegs placed, which leaves the player with a large number of
placements to choose from, and thus creates a much more challenging
game.  On the other hand, some of the 'Starter' challenges
(e.g. number 17) have all seven pegs and one piece placed,
significantly reducing the player's options and consequently making
the challenge far easier.

#### Solutions

Each challenge has just one solution.  When comparing solutions, we
ignore piece rotations that take up the same space on the board.  Such
rotations are described as *symmetric*, which is defined in more
detail [below](#strict-symmetry).

The following sequence shows one possible progression of a solution to the game
above (note that the order in which the pieces are played is not important; this
is just one possible ordering).


![game](assets/d2A6f3C4i6B0j2B0j1C0k3C0l4B0l5C0.png)
![game](assets/d2A6e2C3f3C4i6B0j2B0j1C0k3C0l4B0l5C0.png)
![game](assets/d2A6e2C3f3C4g4A7i6B0j2B0j1C0k3C0l4B0l5C0.png)
![game](assets/b6A7d2A6e2C3f3C4g4A7i6B0j2B0j1C0k3C0l4B0l5C0.png)
![game](assets/b6A7c1A3d2A6e2C3f3C4g4A7i6B0j2B0j1C0k3C0l4B0l5C0.png)
![game](assets/a7A7b6A7c1A3d2A6e2C3f3C4g4A7i6B0j2B0j1C0k3C0l4B0l5C0.png)
![game](assets/a7A7b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0.png)

#### Board

The game is played on a board comprised of 32 **locations** arranged
in a 8x4 grid.  In the plastic game, each location consists of a
circular depression in the plastic into which a piece may fit, and in
the center of the depression is a hole (well) into which a **peg** may
be inserted at the start of the game, to form part of the challenge.
Locations are encoded as a digit (`1` to `8`) followed by a letter
(`A` to `D`).  For example, in the game above, the green peg is in position `3C`
and the red peg is in position `6B`.

#### Pieces

The game comprises **8 playing shapes**, each of which is made of
plastic and consists of three, four, or five connected loops (see the
photo above). The pieces fit neatly into the depressions on the board
formed by the 32 locations.  Each of the loops is either filled or has
a hole.  In the game above, the blue piece at the left has four loops,
three of which are filled and one of which has a hole.  When pieces
are placed, the location and orientation must be chosen such that
loops that are filled are not placed on locations that contain pegs.
In the game above, for example, in the first step, a blue piece placed is
carefully positioned so that one of its holes fits over the blue peg at
location `2B`.

Each piece can be **flipped** and **rotated** at 90 degree increments,
allowing for 8 different **orientations** (four rotations and a flip
with four rotations).  The following illustration shows all 64
possible combinations of the 8 pieces and 8 orientations.  The first
four columns show four rotations.  The piece is then flipped and
rotated four more times.  So the fifth image in the top row (`a4`)
illustrates the flip of the left-most image (`a0`).

![rotations](assets/orientations.png)

##### Strict Symmetry

Notice that piece `c` and piece `h` are symmetric, so the flipped
orientations are the identical to the unflipped (for example `c0` is
identical to `c4`, and `h0` is identical to `h4`).  We describe that
as *'strictly [symmetric](https://en.wikipedia.org/wiki/Symmetry)'*.  We ignore the redundant rotations
with higher numberings (e.g. `c4` is ignored because it is redundant
with respect to `c0` and has a higher rotation number).

##### Weak Symmetry

Notice that *if we ignore the holes* , aside from `a`, `d` and `g`,
all pieces exhibit symmetry.  We describe these as *'weakly
[symmetric](https://en.wikipedia.org/wiki/Symmetry)'*, and thus take up exactly the same space on the
board.  We ignore the redundant rotations with higher numberings (e.g.
if a solution could be made with either`e0` or `e7` then we ignore the
solution with `e7` because it is weakly symmetric and has a higher
rotation number).  Other examples include `b0` & `b2`, `c0` & `c2`,
`f0` & `f6`, and `h0` & `h2`, each of which are identical pairs if we
ignore the holes.

#### Pegs

The game has *seven* pegs.  There are two green, two blue and two
yellow pegs, but just one red peg.  The pegs are not placed by the
player during the game, but rather, they one or more pegs is placed on
the board at the start of the game as part of the challenge.  In the
example above, two blue, two yellow, one green and one red peg are
placed to form the challenge, as well as a green piece.  The player
has to place the remaining pieces.  The particular challenge
illustrated above is challenge one in the booklet that comes with the
IQ-Twist game.


#### Legal Piece Placements

For a piece placement to be valid, the following must be true:
* All loops comprising each piece must be placed on valid board
  locations (**no part of a piece may be off the board**).
* All loops comprising each piece must be placed on vacant board
  locations (**pieces may not overlap**).
* No piece may be placed over a peg, except where the peg is the same
  color *and* the location of the peg coincides with a loop that has
  a hole.  For example, in the game above, the blue piece at the left
  is placed such that it fits on the peg at location `1C`.

#### Encoding Game State

Game states are encoded as strings.  Your game will need to be able to
initialize itself using these strings and some of your tasks relate
directly to these strings.

##### Placement Strings

A placement string consists of between one and eight (inclusive)
**piece placements** (pieces `a` to `h`), and between zero and seven
**peg placements**. The placement string may not include any piece
twice, and may not include more pegs than are available (so it may not
include two or more red pegs since there is only one in the game, and
it may not include three or more of the other colored pegs, since
there are only two each of those in the game).  A completed game
must include eight piece placements.  Each piece placement is described
using four characters.  For example, the game described above is
characterized (when complete) by the string
`a7A7b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0`.  Note
that the placement string is ordered (piece `a` first, and piece `l`
last), which is a requirement for valid placement strings.


##### Piece Placement Strings

A piece placement string consists of four characters describing the location 
and orientation of one particular piece on the board:

* The first character identifies **which of the eight shapes** is being placed (`a` to `h`).
* The second character identifies **which column** the left of the piece is in (columns are labelled `1` to `8`).
* The third character identifies **which row** the top of the piece is in (rows are labelled `A` to `D`).
* The fourth character identifies **which orientation** the piece is in (`0` to `3` for four rotations, and then `4` to `7` for four flipped rotations, see the illustration of all 64 piece orientations above).

The image above shows the first and fourth characters for each of the pieces in
each of their orientations (64 in total). For example, at top left, 'a0' describes piece 'a'
at orientation '0'.  Below it, 'b0' describes piece 'b' at orientation '0'.  At
the bottom right 'h7' describes piece 'h' at orientation '7'.  And so on.   A piece
placement string starts and ends with these two characters and has two more in 
between which describe where the piece is placed.

##### Peg Placement Strings

Peg placement strings follow exactly the same format as piece
placement strings, however, pegs use the characters `i` (red), `j`
(blue), `k` (green), and `l` (yellow), and the rotation is always `0`
for a peg placement, since it makes no sense to rotate a peg, which is
round.

##### Example Placement String

The progression of twelve images above shows the progression of the
game `a7A7b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0`,
starting with the starting state `f3C4i6B0j2B0j1C0k3C0l4B0l5C0`, then
adding piece `d` with its left in column `2`, its top in row `A`, and rotated
and flipped to rotation `6`, which is encoded as a piece placement of `d2A6`.
The  resulting placement string is `d2A6f3C4i6B0j2B0j1C0k3C0l4B0l5C0`, etc.

## Legal and Ethical Issues

First, as with any work you do, you must abide by the principles of [honesty and integrity](http://academichonesty.anu.edu.au). I expect you to demonstrate honesty and integrity in everything you do.

In addition to those ground rules, you are to follow the rules one would normally be subject to in a commercial setting. In particular, you may make use of the works of others under two fundamental conditions: a) your use of their work must be clearly acknowledged, and b) your use of their work must be legal (for example, consistent with any copyright and licensing that applies to the given material). *Please understand that violation of these rules is a very serious offence.*  However, as long as you abide by these rules, you are explicitly invited to conduct research and make use of a variety of sources. You are also given an explicit means with which to declare your use of other sources (via originality statements you must complete). It is important to realize that you will be assessed on the basis of your original contributions to the project. While you won't be penalized for correctly attributed use of others' ideas, the work of others will not be considered as part of your contribution. Therefore, these rules allow you to copy another student's work entirely if: a) they gave you permission to do so, and b) you acknowledged that you had done so. Notice, however, that if you were to do this you would have no original contribution and so would receive no marks for the assignment (but you would not have broken any rules either).


## Evaluation Criteria

It is essential that you refer to the [deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) to check that you understand each of the deadlines and what is required.   Your assignment will be marked via git, so all submittable materials will need to be in git and in the *correct* locations, as prescribed by the [deliverables page](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/).


**The mark breakdown is described on the
[deliverables](https://cs.anu.edu.au/courses/comp1110/assessments/deliverables/) page.**

### Part One

In the first part of the assignment you will:
* Implement parts of the text interface to the game (Tasks #2, and #3).
* Implement a simple viewer that allows you to visualize game states (Task #4).

The criteria for the [completion of part one](https://gitlab.cecs.anu.edu.au/comp1110/comp1110/wikis/deliverables#d2c-assignment-2-stage-c-2-marks-group) is as follows:

<a name="p"></a>
**Pass**
* Tasks #2 and #3

<a name="cr"></a>
**Credit**
* Task #4 *(in addition to all tasks required for Pass)*

<a name="d"></a>
**Distinction**
* Task #5 *(in addition to all tasks required for Credit)*


### Part Two

Create a fully working game, using JavaFX to implement a playable graphical version
of the game in a 933x700 window.


Notice that aside from the window size, the details of exactly how the
game looks etc, are **intentionally** left up to you.  The diagrams
above are for illustration purposes only, although you are welcome to
use all of the resources provided in this repo, including the bitmap
images for each of the eight shapes.

The only **firm** requirements are that:

* you use Java and JavaFX,
* the game respects the specification of the game given here,
* the game be easy to play,
* it runs in a 933x700 window, and
* that it is executable on a standard lab machine from a jar file called `game.jar`,

Your game must successfully run from `game.jar` from within another user's (i.e.
your tutor's) account on a standard lab machine (in other words, your game must
not depend on features not self-contained within that jar file and the Java 8 
runtime).

<a name="2p"></a>
**Pass**
* Correctly implements all of the <b>Part One</b> criteria.
* Appropriate use of git (as demonstrated by the history of your repo).
* Completion of Task #6
* Executable on a standard lab computer from a runnable jar file, game.jar, which resides in the root level of your group repo.

<a name="2c"></a>
**Credit**
* _All of the Pass-level criteria, plus the following..._
* Task #7

<a name="2d"></a>
**Distinction**
* _All of the Credit-level criteria, plus the following..._
* Tasks #8 and #9

<a name="2hd"></a>
**High Distinction**
* _All of the Distinction-level criteria, plus the following..._
* Tasks #10 and #11
