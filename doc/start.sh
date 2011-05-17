#!/bin/sh
screen -d -m -S Octopus

screen -S Octopus java -cp hydra.jar com.eofstudio.hydra.console.standard.Program Port $1
