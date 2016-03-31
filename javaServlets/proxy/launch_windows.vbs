Dim WinScriptHost

Set WinScriptHost = CreateObject("WScript.Shell")

WinScriptHost.Run "java -jar target\proxy-full.jar", 0

Set WinScriptHost = Nothing