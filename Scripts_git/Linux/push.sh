#!/bin/bash

echo "Enter git directory: "
read dir

cd "$dir"

echo "Branch's name: "
read branch

echo "Enter commit message: "
read msg

git add .
git commit -m "$msg"
git push origin $branch

git push github $branch