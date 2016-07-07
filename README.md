quick-launch
------------

Quick launch is an Intellij IDEA plugin that lets you favorite run configurations and quickly access them from
new tool window. This greatly help if you constantly need to change active run configuration in your project for example
when you have separate configuration for server and application or a game and editor. 
Using this plugin you can quickly launch configuration

![quick-launch](http://dl.kotcrab.com/github/ql/quick-launch.png)

#### Usage

Press plus icon to open dialog and select run configurations you want to add to favorites. After adding you can double click any configuration to quickly launch it.
You can also press button with debugger icon to toggle launching configuration in debug mode.

There is one limitation to this plugin, since I haven't found a way to get notified when run configuration is changed
after deleting run configuration you will have to delete it from favorites list manually or restart IDE. If you rename
run configuration you will have to readd it to favorites.
