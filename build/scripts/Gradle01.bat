@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle01 startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE01_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\Gradle01-0.0.1.jar;%APP_HOME%\lib\mariadb-java-client-2.2.2.jar;%APP_HOME%\lib\cucumber-junit-2.3.1.jar;%APP_HOME%\lib\cucumber-java-2.3.1.jar;%APP_HOME%\lib\cucumber-picocontainer-1.2.5.jar;%APP_HOME%\lib\selenium-java-3.9.1.jar;%APP_HOME%\lib\maven-cucumber-reporting-3.15.0.jar;%APP_HOME%\lib\dbunit-2.5.4.jar;%APP_HOME%\lib\cucumber-core-2.3.1.jar;%APP_HOME%\lib\junit-4.12.jar;%APP_HOME%\lib\cucumber-java-1.2.5.jar;%APP_HOME%\lib\cucumber-jvm-deps-1.0.5.jar;%APP_HOME%\lib\gherkin-2.12.2.jar;%APP_HOME%\lib\picocontainer-2.15.jar;%APP_HOME%\lib\selenium-api-3.9.1.jar;%APP_HOME%\lib\selenium-chrome-driver-3.9.1.jar;%APP_HOME%\lib\selenium-edge-driver-3.9.1.jar;%APP_HOME%\lib\selenium-firefox-driver-3.9.1.jar;%APP_HOME%\lib\selenium-ie-driver-3.9.1.jar;%APP_HOME%\lib\selenium-opera-driver-3.9.1.jar;%APP_HOME%\lib\selenium-remote-driver-3.9.1.jar;%APP_HOME%\lib\selenium-safari-driver-3.9.1.jar;%APP_HOME%\lib\selenium-support-3.9.1.jar;%APP_HOME%\lib\byte-buddy-1.7.9.jar;%APP_HOME%\lib\commons-exec-1.3.jar;%APP_HOME%\lib\commons-codec-1.10.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\gson-2.8.2.jar;%APP_HOME%\lib\guava-23.6-jre.jar;%APP_HOME%\lib\httpclient-4.5.3.jar;%APP_HOME%\lib\httpcore-4.4.6.jar;%APP_HOME%\lib\okhttp-3.9.1.jar;%APP_HOME%\lib\okio-1.13.0.jar;%APP_HOME%\lib\maven-plugin-api-3.3.9.jar;%APP_HOME%\lib\maven-gpg-plugin-1.6.jar;%APP_HOME%\lib\cucumber-reporting-3.15.0.jar;%APP_HOME%\lib\slf4j-api-1.7.21.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\poi-ooxml-3.14.jar;%APP_HOME%\lib\cucumber-html-0.2.6.jar;%APP_HOME%\lib\cucumber-jvm-deps-1.0.6.jar;%APP_HOME%\lib\gherkin-5.0.0.jar;%APP_HOME%\lib\tag-expressions-1.1.1.jar;%APP_HOME%\lib\hamcrest-core-1.3.jar;%APP_HOME%\lib\cucumber-core-1.2.5.jar;%APP_HOME%\lib\jsr305-1.3.9.jar;%APP_HOME%\lib\checker-compat-qual-2.0.0.jar;%APP_HOME%\lib\error_prone_annotations-2.1.3.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.14.jar;%APP_HOME%\lib\maven-model-3.3.9.jar;%APP_HOME%\lib\maven-artifact-3.3.9.jar;%APP_HOME%\lib\org.eclipse.sisu.plexus-0.3.2.jar;%APP_HOME%\lib\maven-project-2.2.1.jar;%APP_HOME%\lib\maven-repository-metadata-2.2.1.jar;%APP_HOME%\lib\plexus-sec-dispatcher-1.4.jar;%APP_HOME%\lib\log4j-api-2.8.2.jar;%APP_HOME%\lib\log4j-core-2.8.2.jar;%APP_HOME%\lib\jackson-databind-2.8.6.jar;%APP_HOME%\lib\velocity-1.7.jar;%APP_HOME%\lib\velocity-tools-1.4.jar;%APP_HOME%\lib\joda-time-2.9.9.jar;%APP_HOME%\lib\commons-lang3-3.6.jar;%APP_HOME%\lib\commons-io-2.5.jar;%APP_HOME%\lib\zip4j-1.3.2.jar;%APP_HOME%\lib\jsoup-1.10.3.jar;%APP_HOME%\lib\owasp-java-html-sanitizer-20170515.1.jar;%APP_HOME%\lib\commons-configuration-1.6.jar;%APP_HOME%\lib\poi-3.14.jar;%APP_HOME%\lib\poi-ooxml-schemas-3.14.jar;%APP_HOME%\lib\curvesapi-1.03.jar;%APP_HOME%\lib\maven-settings-2.2.1.jar;%APP_HOME%\lib\maven-profile-2.2.1.jar;%APP_HOME%\lib\maven-artifact-manager-2.2.1.jar;%APP_HOME%\lib\maven-plugin-registry-2.2.1.jar;%APP_HOME%\lib\plexus-interpolation-1.11.jar;%APP_HOME%\lib\plexus-container-default-1.0-alpha-9-stable-1.jar;%APP_HOME%\lib\plexus-cipher-1.4.jar;%APP_HOME%\lib\jackson-annotations-2.8.0.jar;%APP_HOME%\lib\jackson-core-2.8.6.jar;%APP_HOME%\lib\commons-lang-2.4.jar;%APP_HOME%\lib\commons-digester-1.8.jar;%APP_HOME%\lib\commons-beanutils-core-1.8.0.jar;%APP_HOME%\lib\xmlbeans-2.6.0.jar;%APP_HOME%\lib\wagon-provider-api-1.0-beta-6.jar;%APP_HOME%\lib\backport-util-concurrent-3.1.jar;%APP_HOME%\lib\classworlds-1.1-alpha-2.jar;%APP_HOME%\lib\commons-beanutils-1.7.0.jar;%APP_HOME%\lib\stax-api-1.0.1.jar;%APP_HOME%\lib\cdi-api-1.0.jar;%APP_HOME%\lib\org.eclipse.sisu.inject-0.3.2.jar;%APP_HOME%\lib\plexus-component-annotations-1.5.5.jar;%APP_HOME%\lib\plexus-classworlds-2.5.2.jar;%APP_HOME%\lib\jsr250-api-1.0.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\plexus-utils-3.0.24.jar

@rem Execute Gradle01
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE01_OPTS%  -classpath "%CLASSPATH%" com.ctc.Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable GRADLE01_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%GRADLE01_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
