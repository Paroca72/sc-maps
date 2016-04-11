# ScLimitMap
Limit the boundaries of the Google map to a fixed area by southWest and northEast couple of coordinates.<br />
If the user move the map over this limits the class will reposition the map within.<br />
This class inherit from the [ScTouchableMap](ScTouchableMap.md) .

#### Getter and Setter
- **get/getMapPadding()**  -> int value, default: <code>20</code><br />
The map borders internal padding.<br />
Adjust this value for a right view of the map when auto-resized.

#### Methods
- **void setBounds(LatLng southWest, LatLng northEast)**<br />
Set the boundaries of the map.

### Example
For an example please take a look the demo section in the project structure.<br />
The follow example limit the map on the Italy area.

**xml**
```xml
<!-- Map -->
<fragment
    android:id="@+id/mapLayout"
    android:name="com.components.maps.demo.ScLimitMap"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**java**
```java
// Find the map fragment and cast it
Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.mapLayout);
ScLimitMap limitMap = (ScLimitMap) fragment;

// Map constraints (ITALY)
limitMap.setBounds(
     new LatLng(36.031332, 6.207275),
     new LatLng(47.234490, 19.973145)
);
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
