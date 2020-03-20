# Maven打包例程
## 打包成Jar文件运行
创建Java项目，利用maven install将项目打包成jar，在控制台输入java命令：
```cmd
java -jar 项目名称.jar
```
运行Java代码。

创建maven项目，编写Java代码。
如主程序(Java 11)：
```java
package com.stackstone.study.maven;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HelloMaven {
    public static void main(String[] args) {
        var str = "Hello Maven";
        System.out.println(str);
        InputStream inputStream = HelloMaven.class.getClassLoader().getResourceAsStream("demo.yml");
        Yaml yaml = new Yaml();
        for (Object o : yaml.loadAll(inputStream)) {
            Map map = (Map) o;
            System.out.println(map);
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```
编写pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.stackstone.study.maven</groupId>
    <artifactId>MavenStudy</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--  解决
          不再支持源选项 5。请使用 6 或更高版本。
          不再支持目标选项 1.5。请使用 1.6 或更高版本。
     -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <!-- 解析YML文件 -->
    <dependencies>
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>1.25</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
               <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>1.2.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer
                                        implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.stackstone.study.maven.HelloMaven</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
执行mvn install：

![image](588C5AD6D4AC4D759551ADB64E813E0C)

项目产生target文件：

![image](B1061A73AA244066BC1C630DE757675E)

控制台运行：
```
java -jar MavenStudy-1.0-SNAPSHOT.jar
```
输出如下：

![image](C82864456427426EA0445921D7CC7D26)

## 打包后输出到指定文件利用bat脚本启动
项目中创建assembly文件夹，创建dev.xml，内容如下：
```
<assembly xmlns="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/plugins/maven-assembly-plugin/assembly/1.1.0 http://maven.apache.org/xsd/assembly-1.1.0.xsd">
    <id>dist</id>
    <formats>
        <format>dir</format>
    </formats>
    <includeBaseDirectory>false</includeBaseDirectory>
    <fileSets>
        <fileSet>
            <directory>.</directory>
            <outputDirectory>/</outputDirectory>
            <includes>
                <include>README*</include>
            </includes>
        </fileSet>
        <fileSet>
            <directory>./src/main/bin</directory>
            <outputDirectory>bin</outputDirectory>
            <includes>
                <include>**/*</include>
            </includes>
            <fileMode>0755</fileMode>
        </fileSet>
        <fileSet>
            <directory>./src/main/resources</directory>
            <outputDirectory>/conf</outputDirectory>
            <includes>
                <include>**/*</include>

            </includes>
        </fileSet>
    </fileSets>
    <dependencySets>
        <dependencySet>
            <outputDirectory>lib</outputDirectory>
            <excludes>
                <exclude>junit:junit</exclude>
            </excludes>
        </dependencySet>
    </dependencySets>
</assembly>

```
创建bin文件夹，编写bat脚本如下：
```bat
@echo off
@if not "%ECHO%" == ""  echo %ECHO%
@if "%OS%" == "Windows_NT"  setlocal

set ENV_PATH=.\
if "%OS%" == "Windows_NT" set ENV_PATH=%~dp0%

set conf_dir=%ENV_PATH%\..\conf
set CLASSPATH=%conf_dir%\..\lib\*;%CLASSPATH%

echo %CLASSPATH%
pause

set JAVA_MEM_OPTS= -Xms128m -Xmx512m -XX:PermSize=128m
set JAVA_OPTS_EXT= -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dapplication.codeset=UTF-8 -Dfile.encoding=UTF-8
set ADAPTER_OPTS= -DappName=demoMaven

set JAVA_OPTS= %JAVA_MEM_OPTS% %JAVA_OPTS_EXT% %ADAPTER_OPTS%

set CMD_STR= java %JAVA_OPTS% -classpath "%CLASSPATH%" com.demo.HelloWorld
echo start cmd : %CMD_STR%
pause

java %JAVA_OPTS% -classpath "%CLASSPATH%" com.stackstone.study.maven.HelloMaven
```
修改pom.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.stackstone.study.maven</groupId>
    <artifactId>MavenStudy</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--  解决
          不再支持源选项 5。请使用 6 或更高版本。
          不再支持目标选项 1.5。请使用 1.6 或更高版本。
     -->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <!-- 解析YML文件 -->
    <dependencies>
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>1.25</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>2.10</version>
                <executions>
                    <execution>
                        <id>demo</id>
                        <phase>package</phase>
                        <goals>
                            <goal>copy-dependencies</goal>
                        </goals>
                        <configuration>
                            <includeClassifiers>jar-with-dependencies</includeClassifiers>
                            <outputDirectory>${project.basedir}/target/demo/plugin</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <version>2.2.1</version>
                <executions>
                    <execution>
                        <id>assemble</id>
                        <goals>
                            <goal>single</goal>
                        </goals>
                        <phase>package</phase>
                    </execution>
                </executions>
                <configuration>
                    <appendAssemblyId>false</appendAssemblyId>
                    <attach>false</attach>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.0.2</version>
                <configuration>
                    <archive>
                        <manifest>
                            <addClasspath>true</addClasspath>
                            <classpathPrefix>lib/</classpathPrefix>
                            <mainClass>com.stackstone.study.maven.HelloMaven</mainClass>
                        </manifest>
                    </archive>
                    <excludes>
                        <exclude>**/*.properties</exclude>
                        <!--<exclude>**/*.xml</exclude>-->
                        <!--如果加上这个程序中会抛出异常，可以实验一下-->
                        <!--<exclude>**/*.yml</exclude>-->
                        <exclude>static/**</exclude>
                        <exclude>templates/**</exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <!--在列的项目构建profile，如果被激活，会修改构建处理-->
    <profiles>
        <!--根据环境参数或命令行参数激活某个构建处理-->
        <profile>
            <!--构建配置的唯一标识符。即用于命令行激活，也用于在继承时合并具有相同标识符的profile。-->
            <id>dev</id>
            <!--自动触发profile的条件逻辑。Activation是profile的开启钥匙。profile的力量来自于它
                能够在某些特定的环境中自动使用某些特定的值；这些环境通过activation元素指定。
                activation元素并不是激活profile的唯一方式。-->
            <activation>
                <!--profile默认是否激活的标志-->
                <activeByDefault>true</activeByDefault>
                <!--如果Maven检测到某一个属性（其值可以在POM中通过${名称}引用），
                其拥有对应的名称和值，Profile就会被激活。如果值字段是空的，
                那么存在属性名称字段就会激活profile，否则按区分大小写方式匹配属性值字段-->
                <property>
                    <!--激活profile的属性的名称-->
                    <name>env</name>
                    <!--激活profile的属性的值-->
                    <value>!release</value>
                </property>
            </activation>
            <!--构建项目需要的信息-->
            <build>
                <!--使用的插件列表 。-->
                <plugins>
                    <!--plugin元素包含描述插件所需要的信息。-->
                    <plugin>
                        <!--插件在仓库里的artifact ID-->
                        <artifactId>maven-assembly-plugin</artifactId>
                        <!--作为DOM对象的配置-->
                        <configuration>
                            <descriptors>
                                <descriptor>${basedir}/src/main/assembly/dev.xml</descriptor>
                            </descriptors>
                            <finalName>demo-maven</finalName>
                            <outputDirectory>${project.build.directory}</outputDirectory>
                        </configuration>
                    </plugin>
                </plugins>
            </build>
        </profile>
    </profiles>
</project>
```

之后mvn install项目，可以看见项目中产生了target目录如下：

![image](E7F9C2C211E446ACA9D4EDB15C2ECD38)

之后运行startup.bat，控制台输出如下所示：

![image](D44AB20BB0E743D88C31B4FD6EB8002C)

***项目例程地址：***
https://github.com/lt5227/CodeExample/tree/master/MavenStudy
