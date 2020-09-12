Pitaya [![Build][1]][2] [![Coverage][3]][4] [![Version][5]][6] [![Javadocs][7]][8] [![License][9]][10]
======================================================================================================

A simple general-purpose utility library for Java 6+.


Getting started
---------------

Pitaya binaries are available from Maven central repositories.
Download the [latest JAR][11] or get it directly from your favorite build tool:

Maven
```xml
  <dependency>
    <groupId>org.kocakosm</groupId>
    <artifactId>pitaya</artifactId>
    <version>0.4</version>
  </dependency>
```

Gradle
```groovy
  compile 'org.kocakosm:pitaya:0.4'
```

*Note: module name for the Java Module System is `org.kocakosm.pitaya`.*


Usage
-----

Browse [API docs][12] for the most recent release.


Warnings
--------

Pitaya is developed and tested exclusively with OpenJDK 11 on GNU/Linux systems.
Some features may not work properly in other environments.

Pitaya is not designed to protect against a malicious caller. Don't use it for
communication between trusted and untrusted code.


Contributing
------------

If you would like to contribute code, fork the [repository][13] and send a pull
request. When submitting code, please make sure to follow existing conventions
and style in order to keep the code as readable as possible.

Reporting errors or possible improvements is also a great way to help. Be sure
to not duplicate an existing issue by first browsing the [issue tracker][14].


License
-------

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation, either version 3 of the License, or (at your option) any
later version.

This program is distributed in the hope that it will be useful, but _without any
warranty;_ without even the implied warranty of _merchantability_ or _fitness
for a particular purpose_.

See the [GNU Lesser General Public License][15] for more details.


Contact
-------

If you have any question, feel free to send me an e-mail at kocakosm[@]gmail[dot]com
or ping me on [twitter][16].


 [1]: https://img.shields.io/travis/kocakosm/pitaya.svg
 [2]: https://travis-ci.org/kocakosm/pitaya
 [3]: https://img.shields.io/coveralls/kocakosm/pitaya.svg
 [4]: https://coveralls.io/r/kocakosm/pitaya
 [5]: https://img.shields.io/maven-central/v/org.kocakosm/pitaya.svg
 [6]: https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.kocakosm%22%20AND%20a%3A%22pitaya%22
 [7]: https://javadoc.io/badge/org.kocakosm/pitaya.svg
 [8]: https://javadoc.io/doc/org.kocakosm/pitaya
 [9]: https://img.shields.io/badge/license-LGPL_v3-4383c3.svg
 [10]: https://www.gnu.org/licenses/lgpl.txt
 [11]: https://search.maven.org/remote_content?g=org.kocakosm&a=pitaya&v=LATEST
 [12]: https://www.javadoc.io/doc/org.kocakosm/pitaya
 [13]: https://github.com/kocakosm/pitaya
 [14]: https://github.com/kocakosm/pitaya/issues
 [15]: https://www.gnu.org/licenses/lgpl-3.0-standalone.html
 [16]: https://twitter.com/kocakosm
