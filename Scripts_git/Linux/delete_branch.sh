#!/bin/bash

echo "Enter git directory: "
read dir

cd "$dir"

echo "Branch's name: "
read branch

git checkout master

git fetch -p origin
git push origin :$branch
git push origin master

git push github :$branch