# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.10

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /opt/clion-2018.1/bin/cmake/bin/cmake

# The command to remove a file.
RM = /opt/clion-2018.1/bin/cmake/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/pawel/Documents/rozprochy/zad1

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/pawel/Documents/rozprochy/zad1/cmake-build-debug

# Include any dependencies generated for this target.
include CMakeFiles/zad1.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/zad1.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/zad1.dir/flags.make

CMakeFiles/zad1.dir/main.c.o: CMakeFiles/zad1.dir/flags.make
CMakeFiles/zad1.dir/main.c.o: ../main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object CMakeFiles/zad1.dir/main.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/zad1.dir/main.c.o   -c /home/pawel/Documents/rozprochy/zad1/main.c

CMakeFiles/zad1.dir/main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/zad1.dir/main.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/pawel/Documents/rozprochy/zad1/main.c > CMakeFiles/zad1.dir/main.c.i

CMakeFiles/zad1.dir/main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/zad1.dir/main.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/pawel/Documents/rozprochy/zad1/main.c -o CMakeFiles/zad1.dir/main.c.s

CMakeFiles/zad1.dir/main.c.o.requires:

.PHONY : CMakeFiles/zad1.dir/main.c.o.requires

CMakeFiles/zad1.dir/main.c.o.provides: CMakeFiles/zad1.dir/main.c.o.requires
	$(MAKE) -f CMakeFiles/zad1.dir/build.make CMakeFiles/zad1.dir/main.c.o.provides.build
.PHONY : CMakeFiles/zad1.dir/main.c.o.provides

CMakeFiles/zad1.dir/main.c.o.provides.build: CMakeFiles/zad1.dir/main.c.o


CMakeFiles/zad1.dir/token.c.o: CMakeFiles/zad1.dir/flags.make
CMakeFiles/zad1.dir/token.c.o: ../token.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object CMakeFiles/zad1.dir/token.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/zad1.dir/token.c.o   -c /home/pawel/Documents/rozprochy/zad1/token.c

CMakeFiles/zad1.dir/token.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/zad1.dir/token.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/pawel/Documents/rozprochy/zad1/token.c > CMakeFiles/zad1.dir/token.c.i

CMakeFiles/zad1.dir/token.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/zad1.dir/token.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/pawel/Documents/rozprochy/zad1/token.c -o CMakeFiles/zad1.dir/token.c.s

CMakeFiles/zad1.dir/token.c.o.requires:

.PHONY : CMakeFiles/zad1.dir/token.c.o.requires

CMakeFiles/zad1.dir/token.c.o.provides: CMakeFiles/zad1.dir/token.c.o.requires
	$(MAKE) -f CMakeFiles/zad1.dir/build.make CMakeFiles/zad1.dir/token.c.o.provides.build
.PHONY : CMakeFiles/zad1.dir/token.c.o.provides

CMakeFiles/zad1.dir/token.c.o.provides.build: CMakeFiles/zad1.dir/token.c.o


CMakeFiles/zad1.dir/useful.c.o: CMakeFiles/zad1.dir/flags.make
CMakeFiles/zad1.dir/useful.c.o: ../useful.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Building C object CMakeFiles/zad1.dir/useful.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/zad1.dir/useful.c.o   -c /home/pawel/Documents/rozprochy/zad1/useful.c

CMakeFiles/zad1.dir/useful.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/zad1.dir/useful.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/pawel/Documents/rozprochy/zad1/useful.c > CMakeFiles/zad1.dir/useful.c.i

CMakeFiles/zad1.dir/useful.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/zad1.dir/useful.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/pawel/Documents/rozprochy/zad1/useful.c -o CMakeFiles/zad1.dir/useful.c.s

CMakeFiles/zad1.dir/useful.c.o.requires:

.PHONY : CMakeFiles/zad1.dir/useful.c.o.requires

CMakeFiles/zad1.dir/useful.c.o.provides: CMakeFiles/zad1.dir/useful.c.o.requires
	$(MAKE) -f CMakeFiles/zad1.dir/build.make CMakeFiles/zad1.dir/useful.c.o.provides.build
.PHONY : CMakeFiles/zad1.dir/useful.c.o.provides

CMakeFiles/zad1.dir/useful.c.o.provides.build: CMakeFiles/zad1.dir/useful.c.o


CMakeFiles/zad1.dir/tcp.c.o: CMakeFiles/zad1.dir/flags.make
CMakeFiles/zad1.dir/tcp.c.o: ../tcp.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_4) "Building C object CMakeFiles/zad1.dir/tcp.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/zad1.dir/tcp.c.o   -c /home/pawel/Documents/rozprochy/zad1/tcp.c

CMakeFiles/zad1.dir/tcp.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/zad1.dir/tcp.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/pawel/Documents/rozprochy/zad1/tcp.c > CMakeFiles/zad1.dir/tcp.c.i

CMakeFiles/zad1.dir/tcp.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/zad1.dir/tcp.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/pawel/Documents/rozprochy/zad1/tcp.c -o CMakeFiles/zad1.dir/tcp.c.s

CMakeFiles/zad1.dir/tcp.c.o.requires:

.PHONY : CMakeFiles/zad1.dir/tcp.c.o.requires

CMakeFiles/zad1.dir/tcp.c.o.provides: CMakeFiles/zad1.dir/tcp.c.o.requires
	$(MAKE) -f CMakeFiles/zad1.dir/build.make CMakeFiles/zad1.dir/tcp.c.o.provides.build
.PHONY : CMakeFiles/zad1.dir/tcp.c.o.provides

CMakeFiles/zad1.dir/tcp.c.o.provides.build: CMakeFiles/zad1.dir/tcp.c.o


CMakeFiles/zad1.dir/communication.c.o: CMakeFiles/zad1.dir/flags.make
CMakeFiles/zad1.dir/communication.c.o: ../communication.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_5) "Building C object CMakeFiles/zad1.dir/communication.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/zad1.dir/communication.c.o   -c /home/pawel/Documents/rozprochy/zad1/communication.c

CMakeFiles/zad1.dir/communication.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/zad1.dir/communication.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/pawel/Documents/rozprochy/zad1/communication.c > CMakeFiles/zad1.dir/communication.c.i

CMakeFiles/zad1.dir/communication.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/zad1.dir/communication.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/pawel/Documents/rozprochy/zad1/communication.c -o CMakeFiles/zad1.dir/communication.c.s

CMakeFiles/zad1.dir/communication.c.o.requires:

.PHONY : CMakeFiles/zad1.dir/communication.c.o.requires

CMakeFiles/zad1.dir/communication.c.o.provides: CMakeFiles/zad1.dir/communication.c.o.requires
	$(MAKE) -f CMakeFiles/zad1.dir/build.make CMakeFiles/zad1.dir/communication.c.o.provides.build
.PHONY : CMakeFiles/zad1.dir/communication.c.o.provides

CMakeFiles/zad1.dir/communication.c.o.provides.build: CMakeFiles/zad1.dir/communication.c.o


CMakeFiles/zad1.dir/input_reading.c.o: CMakeFiles/zad1.dir/flags.make
CMakeFiles/zad1.dir/input_reading.c.o: ../input_reading.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_6) "Building C object CMakeFiles/zad1.dir/input_reading.c.o"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/zad1.dir/input_reading.c.o   -c /home/pawel/Documents/rozprochy/zad1/input_reading.c

CMakeFiles/zad1.dir/input_reading.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/zad1.dir/input_reading.c.i"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /home/pawel/Documents/rozprochy/zad1/input_reading.c > CMakeFiles/zad1.dir/input_reading.c.i

CMakeFiles/zad1.dir/input_reading.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/zad1.dir/input_reading.c.s"
	/usr/bin/cc $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /home/pawel/Documents/rozprochy/zad1/input_reading.c -o CMakeFiles/zad1.dir/input_reading.c.s

CMakeFiles/zad1.dir/input_reading.c.o.requires:

.PHONY : CMakeFiles/zad1.dir/input_reading.c.o.requires

CMakeFiles/zad1.dir/input_reading.c.o.provides: CMakeFiles/zad1.dir/input_reading.c.o.requires
	$(MAKE) -f CMakeFiles/zad1.dir/build.make CMakeFiles/zad1.dir/input_reading.c.o.provides.build
.PHONY : CMakeFiles/zad1.dir/input_reading.c.o.provides

CMakeFiles/zad1.dir/input_reading.c.o.provides.build: CMakeFiles/zad1.dir/input_reading.c.o


# Object files for target zad1
zad1_OBJECTS = \
"CMakeFiles/zad1.dir/main.c.o" \
"CMakeFiles/zad1.dir/token.c.o" \
"CMakeFiles/zad1.dir/useful.c.o" \
"CMakeFiles/zad1.dir/tcp.c.o" \
"CMakeFiles/zad1.dir/communication.c.o" \
"CMakeFiles/zad1.dir/input_reading.c.o"

# External object files for target zad1
zad1_EXTERNAL_OBJECTS =

zad1: CMakeFiles/zad1.dir/main.c.o
zad1: CMakeFiles/zad1.dir/token.c.o
zad1: CMakeFiles/zad1.dir/useful.c.o
zad1: CMakeFiles/zad1.dir/tcp.c.o
zad1: CMakeFiles/zad1.dir/communication.c.o
zad1: CMakeFiles/zad1.dir/input_reading.c.o
zad1: CMakeFiles/zad1.dir/build.make
zad1: CMakeFiles/zad1.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles --progress-num=$(CMAKE_PROGRESS_7) "Linking C executable zad1"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/zad1.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/zad1.dir/build: zad1

.PHONY : CMakeFiles/zad1.dir/build

CMakeFiles/zad1.dir/requires: CMakeFiles/zad1.dir/main.c.o.requires
CMakeFiles/zad1.dir/requires: CMakeFiles/zad1.dir/token.c.o.requires
CMakeFiles/zad1.dir/requires: CMakeFiles/zad1.dir/useful.c.o.requires
CMakeFiles/zad1.dir/requires: CMakeFiles/zad1.dir/tcp.c.o.requires
CMakeFiles/zad1.dir/requires: CMakeFiles/zad1.dir/communication.c.o.requires
CMakeFiles/zad1.dir/requires: CMakeFiles/zad1.dir/input_reading.c.o.requires

.PHONY : CMakeFiles/zad1.dir/requires

CMakeFiles/zad1.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/zad1.dir/cmake_clean.cmake
.PHONY : CMakeFiles/zad1.dir/clean

CMakeFiles/zad1.dir/depend:
	cd /home/pawel/Documents/rozprochy/zad1/cmake-build-debug && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/pawel/Documents/rozprochy/zad1 /home/pawel/Documents/rozprochy/zad1 /home/pawel/Documents/rozprochy/zad1/cmake-build-debug /home/pawel/Documents/rozprochy/zad1/cmake-build-debug /home/pawel/Documents/rozprochy/zad1/cmake-build-debug/CMakeFiles/zad1.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/zad1.dir/depend

