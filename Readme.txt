To run the program type 
	java data.object "filename" "number"
where 
	filename = the input off file whitout its extension
	number = the number of vertices needed to be removed from the file
		

eg. if the input file is cube.off and u need to remove say 100 vertices
the command should be

	java data.object cube 100

For compiling in case u d-cide to change the code for your own reasons, type
	javac -d . *.java

NOTE: ONLY TRIANGLE MESHES CAN BE USED AS INPUT.
	