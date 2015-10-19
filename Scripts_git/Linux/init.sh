#!/bin/bash

echo "Enter url of github repository: "
read remote

git remote add github $remote

pause