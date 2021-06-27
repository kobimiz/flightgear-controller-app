# Anomaly Detection Android App
This app provides an easy to use user interface to interact with the Flightgear flying simulator program.

## How it works
The app connects to a server created by flightgear (in a specified port), which is on the same network as the phone, and sends commands to flightgear according to what the user decides.

## Prerequisites
This project was build with Kotlin and Android studio, so both are needed to compile.
Flightgear is also needed.

## How to run
Clone the project from github (branch master), build it using android studio, and run it either using an emulator or an android device. There should also be an instance of flightgear running in the same network. You can tell flightgear to open a server in port 6400 by specifying the option
> --telnet=socket,in,10,127.0.0.1,6400,tcp
in the settings menu.
If you are running the app using an emulator, you need to specify the ip '10.0.2.2'
and the given port.
Else, you need to specify the ip of the device that Flightgear is running on.

## Presentation
The presentation video is hosted on YouTube on 
https://youtu.be/-C1rL9P3wRw
The power point presentation is in the project files (presentation.pptx) 
