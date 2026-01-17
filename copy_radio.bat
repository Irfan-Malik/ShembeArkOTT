@echo off
echo Copying radio channel images...

REM Copy Music radio channels
copy "d:\Personal\TV2U\ImagesResource\Radio-20250724T115323Z-1-001\Radio\Music\*.png" "app\src\main\res\drawable\"

REM Copy News radio channels
copy "d:\Personal\TV2U\ImagesResource\Radio-20250724T115323Z-1-001\Radio\News\*.png" "app\src\main\res\drawable\"

REM Copy Sport radio channels
copy "d:\Personal\TV2U\ImagesResource\Radio-20250724T115323Z-1-001\Radio\Sport\*.png" "app\src\main\res\drawable\"

echo All radio channel images copied successfully!
pause 