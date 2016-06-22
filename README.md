# ScUtils
This is a library of utilities classes for the (google) map management.

- **[ScTouchableMap](ScTouchableMap.md)**<br />
Improve the ability to control the user interaction with the map.
- **[ScLimitMap](ScLimitMap.md)**<br />
Limit the boundaries of the map to a fixed area by left, top, right, bottom coordinate.

# Usage

via Gradle:
<br />
Add it in your root build.gradle at the end of repositories
```java
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Add the dependency
```java
dependencies {
    ...
    compile 'com.github.paroca72:sc-maps:1.0.0'
}
```

#License
<pre>
 Copyright 2015 Samuele Carassai

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in  writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,  either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
</pre>
