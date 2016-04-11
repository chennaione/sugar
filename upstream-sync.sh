#!/bin/sh
git checkout -b upstream-merge master

git pull --rebase https://github.com/satyan/sugar.git master

#git branch -d upstream-merge