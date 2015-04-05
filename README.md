# cordova-appinfo-plugin
Cordova Plugin to retrieve App name, version and compile date. Additionally returns some device capabilities (which I perhaps should move to the Device plugin but meh for now!).

## Installation ##

Go to your application root:

`cordova plugin add https://github.com/skwasjer/cordova-appinfo-plugin`

## Supported devices ##

Currently only Android and a browser proxy are implemented. iOS is being worked on and will arrive soon.

## Usage ##

Once the plugin is registered, simply access the info via appInfo.

Available properties at the moment are:

### Application name ###
`appInfo.name`

### Application version ###
`appInfo.version`

### Application compile date ###
`appInfo.compileDate`

### Device supports hardware acceleration ###
`appInfo.isHardwareAccelerated`
(Only supported from Honeycomb upwards)

### Device supports remote debugging ###
`appInfo.isDebuggable`