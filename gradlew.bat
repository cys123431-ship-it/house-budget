@rem
@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve a possible alternate java command
set JAVA_EXE=java.exe
set JAVA_HOME_EXE=
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\java.exe" set JAVA_HOME_EXE=%JAVA_HOME%\bin\java.exe
)

if defined JAVA_HOME_EXE (
    set JAVA_EXE=%JAVA_HOME_EXE%
)

if not defined JAVA_EXE (
  echo.
  echo ERROR: JAVA_HOME is not set and no 'java' command could be found.
  echo.
  exit /b 1
)

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

"%JAVA_EXE%" -Xmx64m -Xms64m -classpath "%CLASSPATH%" ^
  -Dorg.gradle.appname=%APP_BASE_NAME% org.gradle.wrapper.GradleWrapperMain %*
