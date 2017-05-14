[![npm version](https://badge.fury.io/js/skwas-cordova-plugin-appinfo.svg)](https://badge.fury.io/js/skwas-cordova-plugin-appinfo)

# skwas-cordova-plugin-appinfo
Cordova Plugin to retrieve application information, like the App name, version, compile date and identifier. Additionally returns some device capabilities (which perhaps should be move to the device plugin).

## Changelog

#### 1.0.0 ####
- Marking v1 since it is fairly stable and not changing much.
- Android/iOS: added build and identifier.

#### 0.4.0
- added Cordova 5.x support

#### 0.3.5
- run plugin in background as per Cordova manual

#### 0.3.4
- added iOS support

#### 0.3.2
- Android support
- first public release

## Installation ##

`cordova plugin add skwas-cordova-plugin-appinfo`

or for latest

`cordova plugin add https://github.com/skwasjer/skwas-cordova-plugin-appinfo.git`


## Supported platforms ##

Android 4 and higher  
iOS 8 and higher (tested with Xcode 7.2.3 and Xcode 8)  
Browser proxy

## Usage ##

This plugin defines a global appInfo object, which provides application properties. Although the object is in the global scope, it is not available until after the deviceready event.

```js
document.addEventListener("deviceready", onDeviceReady, false);
function onDeviceReady() {
    console.log(appInfo.name);
}
```

## Properties ##

### Application name ###
`appInfo.name`

#### Supported platforms ####
- Android (manifest/application@android:label)
- iOS (CFBundleName)

### Application identifier ###
`appInfo.identifier`

#### Supported platforms ####
- Android (manifest/package)
- iOS (CFBundleIdentifier)

### Application version ###
`appInfo.version`

#### Supported platforms ####
- Android (manifest@android:versionName)
- iOS (CFBundleShortVersionString)

### Application build ###
`appInfo.build`

#### Supported platforms ####
- Android (manifest@android:versionCode)
- iOS (CFBundleVersion)

### Application compile date ###
`appInfo.compileDate`

#### Supported platforms ####
- Android
- iOS

### Device supports hardware acceleration ###
`appInfo.isHardwareAccelerated`

#### Supported platforms ####
- Android, Honeycomb upwards
- iOS will always return true

### Device supports remote debugging ###
`appInfo.isDebuggable`

#### Supported platforms ####
- Android
- iOS
