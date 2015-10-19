#!/bin/bash

echo "Enter git directory: "
read dir

cd "$dir"

echo "Enter url of github repository: "
read remote

git remote add github $remote

pause