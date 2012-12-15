#!/bin/sh

CLASSES_PATH="./build/classes/test/org/jarexploder/classfile"
DESTINATION="./src/test/java/org/jarexploder/classfile/validclass/"

echo "gradle build ..."
gradle build 1>/dev/null 2>&1
if [ "$?" -ne "0" ]; then
	echo "gradle build failed !"
	exit 1
fi

cp $CLASSES_PATH/AdvancedClass.class $DESTINATION
cp $CLASSES_PATH/SimpleInterface.class $DESTINATION
cp $CLASSES_PATH/SimpleClass.class $DESTINATION

echo "Done."
