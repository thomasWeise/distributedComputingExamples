# Sockets

Here I provide a set of examples on using [sockets](https://en.wikipedia.org/wiki/Network_socket)
with the [`C`](https://en.wikipedia.org/wiki/C_%28programming_language%29) under both [Microsoft Windows](https://en.wikipedia.org/wiki/Windows) (using [MinGW](http://mingw.org/)) and [Linux](https://en.wikipedia.org/wiki/Linux).

## 1. Examples

## 1.1. TCPClient / TCPServer

The simple TCP Client and TCP Server examples do nothing spectacular.
The client sends one byte with value `2` over a TCP connection to the server and terminates.
The server accepts 5 incoming connections, prints the received byte, and then terminates.

## 1.2. UDPClient / UDPServer

The simple UDP Client and UDP Server examples do nothing spectacular, actually, basically the same as the TCP client/server.
The client sends one byte with value `2` in an UDP package to the server and terminates.
The server accepts 5 incoming packages, prints the received byte, and then terminates.

## 2. Compiling / Building

For building, I here use [GCC](https://en.wikipedia.org/wiki/GNU_Compiler_Collection) under both Linux and Windows. You can use other compilers too, if you want.

### 2.1. Linux

The program codes whose file names end with `_linux.c` are the [Linux](https://en.wikipedia.org/wiki/Linux) versions of my socket examples. For an example with name `fileName_linux.c`, you would type

    gcc fileName_linux.c -o fileName_linux
    
You will get an executable named `fileName_linux`.
 
For your convenience, you can build all Linux examples directly by executing `make_linux.sh`. (You may need to `chmod +x make_linux.sh` first.)

### 2.2. Microsoft Windows

The program codes whose file names end with `_windows.c` are the [Microsoft Windows](https://en.wikipedia.org/wiki/Windows) versions of my sockets examples. You can build the examples using [MinGW](http://mingw.org/). For an example with name `fileName_windows.c`, you would type

    gcc fileName_windows.c -o fileName_windows.exe -lws2_32

The "`-lws2_32`" at the end states that we link against [Winsock](https://en.wikipedia.org/wiki/Winsock), the Windows socket implementation. Such a compilation will result in a corresponding `.exe` executable file. 

For your convenience, you can build all Windows examples directly by executing `make_windows_mingw.bat`.

#### 2.2.1. Installing MinGW

To use [GCC](https://en.wikipedia.org/wiki/GNU_Compiler_Collection) under Windows, you will need [MinGW](http://mingw.org/). This is how you get it:

1. download and run the [installer](https://sourceforge.net/projects/mingw/files/Installer/mingw-get-setup.exe/download)
2. in the installer, select the `gcc` and `msys` packages (don't unselect anything selected)
3. install ideally to recommended folder `C:\MinGW`
4. Open the system control and add the following directories to `PATH` environment variables 
   1. `C:\MinGW\msys\1.0\bin`
   2. `C:\MinGW\bin`