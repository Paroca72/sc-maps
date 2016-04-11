# ScTouchableMap
ScTouchableMap improve the ability to control the user interaction with the map.
This class inherit from the <code>SupportMapFragment</code>.

> **DEPENDENCE**<br />
> Need to Google Play Service.<br />
> For this demo I used the <code>8.4.0</code> version but work also with lower.<br />

#### Getter and Setter
- **get/getSettleCheckDelay()**  -> int value, default: <code>500 milliseconds</code><br />
The delay time to check when the map is settled.<br />
You can tune this setting according to your needs.

#### Methods
- **void setMapListener()**<br />
Attach the generic listener to the map.
- **void setOnCameraChangeListener()**<br />
Attach the camera changing event.

### Overridable methods
Since this class must be extended you can override some methods for your scope.
The same cases is reachable through the listener use.

- **protected void onTouched()**<br />
Called when the user touch the map.
- **protected void onReleased()**<br />
Called when the user release the map.
- **protected void onUnsettled()**<br />
Called when the user move the map or zooming.
- **protected void onSettled()**<br />
Called when the map is settled.

### Example
For an example please take a look the demo section in the project structure.<br />
The follow example limit the map on the Italy area.

**xml**
```xml
<!-- Map -->
<fragment
    android:id="@+id/mapLayout"
    android:name="com.components.maps.demo.ScTouchableMap"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

**java**
```java
// Find the map fragment and cast it
Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.mapLayout);
ScTouchableMap touchableMap = (ScTouchableMap) fragment;

// Listener
touchableMap.setMapListener(new ScTouchableMap.OnMapListener() {
    @Override
    public void onTouched() {
        showMessage("Map is touched!");
    }

    @Override
    public void onReleased() {
        showMessage("Map is released!");
    }

    @Override
    public void onUnsettled() {
        showMessage("Map is unsettled!");
    }

    @Override
    public void onSettled() {
        showMessage("Map is settled!");
    }
});
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
