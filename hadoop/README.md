# Hadoop Examples

[Apache](http://hadoop.apache.org/) [Hadoop](https://en.wikipedia.org/wiki/Apache_Hadoop) is a framework for performing large-scale distributed computations in a cluster. Different from [MPI](https://en.wikipedia.org/wiki/Message_Passing_Interface) (which we discussed [here](http://github.com/thomasWeise/distributedComputingExamples/tree/master/mpi/)), it is based on Java technologies. It may thus be slower than MPI, but can reap the full benefit of the rich libraries and programming environment of the Java ecosystem (just think about all the things we already did in this course). One of the most well-known ways to use Hadoop is to perform computations following the [MapReduce](https://en.wikipedia.org/wiki/MapReduce) pattern (which is a bit similar to scatter/gather/reduce in MPI).  

## 1. Examples

### 1.1. Word Count

The first example is the `wordCountExample` project, which is based on the famous and well-known [word counting](http://wiki.apache.org/hadoop/WordCount) Map-Reduce example in the version provided by Luca Menichetti [meniluca@gmail.com](mailto:meniluca@gmail.com) under the GNU General Public License version 2. The original version of the example is nicely discussed in [this blog entry](https://nosqlnocry.wordpress.com/2015/03/13/hadoop-mapreduce-wordcount-example-in-java-introduction-to-hadoop-job/). Further, similar explanations are given [here](http://cs.smith.edu/dftwiki/index.php/Hadoop_Tutorial_1_--_Running_WordCount) and [here](http://wiki.apache.org/hadoop/WordCount).


## 2. Building and Deployment

### 2.1. Import Project into Eclipse

If you import one of the example projects in this folder in [Eclipse](http://www.eclipse.org), it may first show you a lot of errors. (I recommend using Eclipse Mars or later.) These projects are Maven projects, so you should "update" them first in Eclipse by doing the following. Let's say you want to import the `wordCountExample` project:

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`wordCountExample`) in the `package view`.
3. In the opening pop-up menu, left-click on `Maven`.
4. In the opening sub-menu, left-click on `Update Project...`.
5. In the opening window...
  1. Make sure the project (`wordCountExample`) is selected.
  2. Make sure that `Update project configuration from pom.xml` is selected.
  3. You can also select `Clean projects`.
  4. Click `OK`.
6. Now the structure of the project in the `package view` should slightly change, the project will be re-compiled, and the errors should disappear.


### 2.2. Build Project in Eclipse


Now you can actually build the imported project(s), i.e., generate a [`jar`](https://en.wikipedia.org/wiki/JAR_%28file_format%29) file that you can pass to Hadoop. Let's say you want to build the `wordCountExample` project.

1. Make sure that you can see the `package view` on the left-hand side of the Eclipse window.
2. Right-click on the project (`wordCountExample`) in the `package view`.
3. In the opening pop-up menu, choose `Run As`.
4. In the opening sub-menu choose `Run Configurations...`.
5. In the opening window, choose `Maven Build`
6. In the new window `Run Configurations` / `Create, manage, and run configurations`, choose `Maven Build` in the small white pane on the left side.
7. Click `New launch configuration` (the first symbol from the left on top of the small white pane).
8. Write a useful name for this configuration in the `Name` field. You can use this configuration again later.
9. In the tab `Main` enter the `Base directory` of the project, this is the folder called `hadoop/wordCountExample` containing the Eclipse/Maven project.
10. Under `Goals`, enter `clean compile package`. This will build a `jar` archive.
11. Click `Apply`
12. Click `Run`
13. The build will start, you will see its status output in the console window.
14. The folder `target` will contain a file `wordCountExample-full.jar` after the build. This is the executable archive with our application.


### 2.3. Building under Linux without Eclipse

Under Linux, you can also simply run `make_linux.sh` in this project's folder to build the servlet without Eclipse, given that you have Maven installed.

### 2.4. Setting Up a Single-Node Hadoop Cluster

In order to test our example, we now need to set up a single-node Hadoop cluster. We therefore follow the guide given at [http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html). Here we provide the installation guide for Hadoop 2.7.2 Linux / Ubuntu.

#### 2.4.1. Download, Unpacking, and Setup

1. Install prerequisites by running `sudo apt-get install ssh rsync`.
2. Go into a base folder where you want to install Hadoop. Let's call this folder 'X'.
3. Download Hadoop from one of the mirrors provided at [http://www.apache.org/dyn/closer.cgi/hadoop/common/](http://www.apache.org/dyn/closer.cgi/hadoop/common/). I choose [http://www-eu.apache.org/dist/hadoop/common/](http://www-eu.apache.org/dist/hadoop/common/) and from there [hadoop-2.7.2](http://www-eu.apache.org/dist/hadoop/common/hadoop-2.7.2/) from where I download [hadoop-2.7.2.tar.gz](http://www-eu.apache.org/dist/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz) into 'X'. If you chose a different Hadoop version, replace `2.7.2.` accordingly in the following steps.
4. Once [hadoop-2.7.2.tar.gz](http://www-eu.apache.org/dist/hadoop/common/hadoop-2.7.2/hadoop-2.7.2.tar.gz) has fully been downloaded, I either can do `Extract Here` in the file explorer or `tar -xf hadoop-2.7.2.tar.gz` in the terminal window to extract the archive. 
5. A new folder named `X/hadoop-2.7.2` should have appeared. If you chose a different Hadoop version, replace `2.7.2.` accordingly in the following steps.
6. In order to run Hadoop, you  must have `JAVA_HOME` set correctly. Open the file `X/etc/hadoop/hadoop-env.sh`. Find the line `export JAVA_HOME=${JAVA_HOME}` and replace it with `export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which javac))))`.

#### 2.4.2. Testing basic Functionality

We can now test whether everything above has turned out well and all is downloaded, unpacked, and set up correctly.

1. In the terminal, enter `X/hadoop-2.7.2/` and execute the command `bin/hadoop`. It should display some help and command line options.
2. We can further test whether Hadoop works by running the single-node example from the [tutorial](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html). Therefore, in your terminal enter

    mkdir input
    cp etc/hadoop/*.xml input
    bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar grep input output 'dfs[a-z.]+'
    cat output/*

The third command should produce a lot of logging output and the last one should say something like `1 dfsadmin`. If that is the case, you are doing well.

#### 2.4.3. Setup for Single-Computer Pseudo-Distributed Execution

For really using Hadoop in a pseudo-distributed fashion on our local computer, we have to do [more](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html#Pseudo-Distributed_Operation):

1. Enter the directory `X/hadoop-2.7.2/etc` in order to create the basic [configuration](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html#Configuration).
2. Open the file `core-site.xml` in the text editor. It should exist, if not, there is something wrong. Try your best by creating it. Remove everything in the file and store the following text, then save and close the file. In other words, the complete contents of the file should become:

    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>fs.defaultFS</name>
            <value>hdfs://localhost:9000</value>
        </property>
    </configuration>


3. Open the file `hdfs-site.xml` in the text editor. It should exist, if not, there is something wrong. Try your best by creating it. Remove everything in the file and store the following text, then save and close the file. In other words, the complete contents of the file should become:

    <?xml version="1.0" encoding="UTF-8"?>
    <?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
    <configuration>
        <property>
            <name>dfs.replication</name>
            <value>1</value>
        </property>
    </configuration>
    

#### 2.4.4. Setup for SSH for Passwordless Connection to Local Host
        
In the terminal, execute `ssh localhost` to test if you can open a [secure shell](https://en.wikipedia.org/wiki/Secure_Shell) connection to your current, local computer [without needing a password](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html#Setup_passphraseless_ssh). It may ask you something like 

    The authenticity of host 'localhost (127.0.0.1)' can't be established.
    ECDSA key fingerprint is SHA256:HZUVFF77GAh5cF/sg8YhjRf1gSGJ9ui5ksdf2GAl5Ha.
    Are you sure you want to continue connecting (yes/no)? 

If it does ask you this, just type `yes` and hit enter (it may then say something like `Warning: Permanently added 'localhost' (ECDSA) to the list of known hosts.`). If it does not ask you this, it does not matter. 

The important thing is the next step: IF it asks you something like `xyz@localhost's password:`, hit `Ctrl-C` and do the things below. Otherwise, you can directly skip to the next point 2.4.5. So, If you were asked for a password, enter the following into your terminal:
    
    ssh-keygen -t dsa -P '' -f ~/.ssh/id_dsa
    cat ~/.ssh/id_dsa.pub >> ~/.ssh/authorized_keys
    chmod 0600 ~/.ssh/authorized_keys 
    
You will get displayed some text such as `Generating public/private dsa key pair.` followed by a couple of other things. After completing the above commands, you should test the result by again executing `ssh localhost`. You will now no longer be asked for a password and directly receive a welcome message, something like `Welcome to Ubuntu 15.10 (GNU/Linux 4.2.0-35-generic x86_64)` or whatever Linux distribution you use. Via a ssh connection, you can, basically, open a terminal to and run commands on a remote computer (which, in this case, is your own, current computer). You can return to the normal (non-ssh) terminal by entering `exit` and pressing return, after which you will be notified that `Connection to localhost closed.`

#### 2.4.6. Running the Hadoop-Provided Map-Reduce Job Locally

We now want to test whether our installation and setup works correctly by further following the steps given in the [tutorial](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/SingleCluster.html#Execution).

1. Format the HDFS file system by entering `bin/hdfs namenode -format` followed by return. You will receive a lot of log output.
2. Start the `NameNode` and `DataNode` daemons by running `sbin/start-dfs.sh`. You may get some logging output messages, which *may* be followed by something like

    The authenticity of host '0.0.0.0 (0.0.0.0)' can't be established.
    ECDSA key fingerprint is SHA256:HZUVFF77GAh5cF/sg8YhjRf1gSGJ9ui5ksdf2GAl5Ha.
    Are you sure you want to continue connecting (yes/no)? 
    
which you would answer with `yes` followed by a hit to the enter button. If, after that, you get a message like `0.0.0.0: packet_write_wait: Connection to 127.0.0.1: Broken pipe`, enter `sbin/stop-dfs.sh`, hit return, and do `sbin/start-dfs.sh` again.
3. In your web browser, open `http://localhost:50070/`. It should display a web page giving an overview about the Hadoop system now running on your local computer.
4. Now we can setup the required stuff for the example jobs (making HDFS directories and copying the input files). Make sure to replace `<userName>` with your user/login name on your current machine.

    bin/hdfs dfs -mkdir /user
    bin/hdfs dfs -mkdir /user/<userName>
    bin/hdfs dfs -put etc/hadoop input
    
5. We can now run the job via
    
    bin/hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar grep input output 'dfs[a-z.]+'
    
6. We obtain the output of the job via

    bin/hdfs dfs -get output output
    cat output/*

7. Like in the local test from point 2.4.2., after a lot of log output, we should sett something like `1 dfsadmin`.
8. Finally, we need to shutdown Hadoop by running `sbin/stop-dfs.sh` 

### 2.5. Running a Compiled Example project

We now want to run one of the provided examples. Let us assume we want to run the `wordCount` example. For other examples, just replace `wordCount` with their names in the following text. I assume that the `distributedComputingExamples` repository is located in a folder `Y` on your machine.

1. Open a terminal and enter your hadoop installation folder. I assume you installed Hadoop version `2.7.2` into a folder named `X`, so you would `cd` into `X/hadoop-2.7.2/`.
2. We want to start with a "clean" file system, so let us repeat some of the setup steps. Don't forget to replace `<userName>` with your local login/user name.

    bin/hdfs namenode -format
    
(answer with `Y` when asked whether to re-format the file system)
    
    sbin/start-dfs.sh
    bin/hdfs dfs -mkdir /user
    bin/hdfs dfs -mkdir /user/<userName>

3. Copy the input data of the example into HDFS. You find this data in the example folder `Y/distributedComputingExamples/wordCount/input`. So you will perform `bin/hdfs dfs -put Y/distributedComputingExamples/hadoop/wordCount/input input`. Make sure to replace `Y` with the proper path. If copying fails, go to "2.6. Troubleshooting".
3. Do `bin/hdfs dfs -ls input` to check if the files have properly been copied.
4. You can now do `bin/hadoop jar Y/distributedComputingExamples/hadoop/wordCount/target/wordCountExample-full.jar input output`. This command will start the main class of the example, which resides in the fat jar `wordCountExample-full.jar`, with the parameters `input` and `output`. `input` here is the input folder, which we previously have copied to the Hadoop file system. `output` is the output folder to be created. If you execute this command, you will see lots of logging information.
5. Do `bin/hdfs dfs -ls output`. You will see output like

    Found 2 items
    -rw-r--r--   1 tweise supergroup          0 2016-04-22 18:48 output/_SUCCESS
    -rw-r--r--   1 tweise supergroup        303 2016-04-22 18:48 output/part-r-00000

7. You can read the results via `bin/hdfs dfs -cat output/part-r-00000 | less` which will result - in the case of the `wordCount` example - in something like

    A       1
    API     4
    Actually        2
    All     1
    Apache  1
    As      2
    Axis    1
    Based   1
    Both    1
    By      1
    C       2
    CSS     2
    Calls   1
    Cascading       1
    Communication   1
    Control 2
    Datagram        1
    Description     2
    Each    1
    Everything      2
    Extensible      1
    Finally 1
    For     3

8. You can download the result file to your local folder via `bin/hdfs dfs -copyToLocal output/part-r-00000 .`. Now you will find the text file with the results in the current folder, i.e., `X/hadoop-2.7.2/`.
9. Finally, shut down the system by calling `sbin/stop-dfs.sh`.

### 2.6 Troubleshooting

#### 2.6.1. "No such file or directory"

Sometimes, you may try to copy some file or folder to HDFS and get an error that no such file or directory exists. Then do the following: 

1. Execute `sbin/start-dfs.sh`
2. Delete the folder `/tmp/hadoop-<userName>`, where `<userName>` is to replaced with your local login/user name.
3. Now perform

    bin/hdfs namenode -format 
    sbin/start-dfs.sh
    bin/hdfs dfs -mkdir /user
    bin/hdfs dfs -mkdir /user/<userName>

4. If you now repeat the operation that failed before, it should succeed.

## 3. Licensing

Some of the examples take some inspiration from the [maven-hadoop-java-wordcount-template](https://github.com/H4ml3t/maven-hadoop-java-wordcount-template) by [H3ml3t](https://github.com/H4ml3t), for which no licensing information is provided. The examples, are entirely differently in several ways, for instance in the way we build fat jars. Anyway, this original project is nicely described in [this blog entry](https://nosqlnocry.wordpress.com/2015/03/13/hadoop-mapreduce-wordcount-example-in-java-introduction-to-hadoop-job/).

Furthermore, the our `wordCountExample` is based on the well-known [word counting example](http://wiki.apache.org/hadoop/WordCount) for Hadoop's map reduce functionality. It is based on the version by provided Luca Menichetti [meniluca@gmail.com](mailto:meniluca@gmail.com) under the GNU General Public License version 2.