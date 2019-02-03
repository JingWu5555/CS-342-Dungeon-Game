Name: Jing Wu
Title: Dungeon Game

=======Instructions===========
My program basically implements a text based game that takes in inputs on which direction to go.
For the command line arguments, it can take in "quit" or "exit" to quit the game, both
lowercase or uppercase is acceptable. Typing in "go N", "go North", "N" is acceptable as well, because
I made the program case insensitive. So it could take in lower or uppercase. Apparently, downloading the gdf file
makes it weird so I had to make another file in Notepad++ and copy and paste the contents inside and name it as a gdf file.
So before running the program, make sure the gdf file is correct and not downloaded straight from the website,
where you right-clicked and clicked on "save link as" otherwise it will not parse the entire file due to some bug.
When you enter quit as the file name, it quits the program but if its "exit" then it just displays an error
saying "no such file". My program can not take extra spacings, so only inputs like "Use Artifact", "North", "go South" and inputs such as
"     GO NORTH", "Get   Artifact" will cause errors. 
To run the file,first compile it by typing in "make" and then enter. Then, type "java GameTester" and it will
run the program. Then, type the name of the gdf file with its extension.
For example, "MystiCity 31.gdf" , type the correct file or it keeps displaying an error message asking you for another input.