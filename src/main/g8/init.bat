@echo off
git init -b main
git remote add origin https://github.com/$repository$
git add .
git reset -- "%~f0"
git commit -m "chore: create project from template"
REM delete init.bat, see https://stackoverflow.com/a/20333575/371593
(goto) 2>nul & del "%~f0"
