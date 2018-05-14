# Intellij Plugin 

WIP for https://intellij-support.jetbrains.com/hc/en-us/community/posts/115000422864-Generate-Code-from-Object-state-in-Debugger


## Idea

While looking at a variable in the debugger, I would like to copy code that generates this object. The Code recursively re-creates all the Objects. This would be useful to create a unit-test out of productive data.

## Limitiations:

This would only work with simple beans with matching constructors. A great extra would be to support Lombok Builders.

