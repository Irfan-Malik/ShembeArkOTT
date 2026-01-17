@echo off
echo Renaming radio channel images to follow Android naming conventions...

REM Rename all radio images to lowercase with underscores
ren "app\src\main\res\drawable\AM102.png" "am102.png"
ren "app\src\main\res\drawable\AM1600.png" "am1600.png"
ren "app\src\main\res\drawable\AM600.png" "am600.png"
ren "app\src\main\res\drawable\AM640.png" "am640.png"
ren "app\src\main\res\drawable\FM101.png" "fm101.png"
ren "app\src\main\res\drawable\FM104.png" "fm104.png"
ren "app\src\main\res\drawable\FM105.png" "fm105.png"
ren "app\src\main\res\drawable\FM1313.png" "fm1313.png"
ren "app\src\main\res\drawable\FM1510.png" "fm1510.png"
ren "app\src\main\res\drawable\FM90.png" "fm90.png"
ren "app\src\main\res\drawable\FM99.png" "fm99.png"

echo All radio images renamed successfully!
pause 