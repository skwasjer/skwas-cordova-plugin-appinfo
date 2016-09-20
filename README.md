# skwas-cordova-plugin-appinfo
Cordova Plugin to retrieve App name, version and compile date. Additionally returns some device capabilities (which I perhaps should move to the Device plugin but meh for now!).

## Installation ##

`cordova plugin add skwas-cordova-plugin-appinfo`

or for latest

`cordova plugin add https://github.com/skwasjer/skwas-cordova-plugin-appinfo.git`

## Supported devices ##

Currently Android, iOS and a browser proxy are implemented.

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
Only supported from Honeycomb upwards. iOS will always return true.

### Device supports remote debugging ###
`appInfo.isDebuggable`