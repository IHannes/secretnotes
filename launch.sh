#!/bin/bash

NOTE=$(zenity --title="Title" --text "Insert title of yor Note" --entry --width 450)
PWD=$(zenity --title="Password" --text "Insert your Password here" --entry --width 450)
keytool -genseckey -alias "$NOTE" -keypass "$PWD" -keyalg AES -keysize 256 -keystore notes.pfx -storepass "$PWD" -storetype PKCS12 -v

javac AES.java
javac maxsec.java
javac encdec.java
javac gui.java

rm -rf *.java
java gui
