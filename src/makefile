FLAGS = -cp
JC = javac -cp

CLASSPATH = Graph.jar
.SUFFIXES: .java .class
.java.class:
	
	 $(JC) $(JFLAGS) $ Graph.jar: $*.java

CLASSES = Flow/FlowNetwork.java Interface/Part2.java 

run: Interface/Part2.class
	java -cp $(CLASSPATH): Interface/Part2
	make clean

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) Flow/*.class
	$(RM) Interface/*.class





