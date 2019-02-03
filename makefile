JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        Game.java \
        Direction.java \
        Place.java \
        Artifact.java \
        GameTester.java 

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class
