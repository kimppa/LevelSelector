To compile the widgetset, you need to get Google Web Toolkit. Download it 
from http://code.google.com/webtoolkit/download.html (the component is originally
written for GWT 1.7.0) and you need to extract the contents into the
/build/lib/gwt directory.

If you use another platform than OS X, you need to edit the file 
/build/build.xml and change the line containing

	<pathelement path="build/lib/gwt/gwt-dev-mac.jar" />

by replacing "mac" with your current platform ("windows" for Windows and "linux" 
for the various Linux flavors).

Once that's done, you can compile the widgetset with Ant. In Eclipse, right click
on /build/build-widgetset.xml and choose "Run As" > "Ant Build". If you prefer
the command line, go to the /build directory and simply type "ant". If everything
goes according to plan, after a long wait (could be up to a few minutes), you
should have a freshly compiled and working widgetset.

If you want to get rid of all the red in Eclipse, add gwt-user.jar and 
gwt-dev-[platform].jar into your classpath, and they will go away. You won't, 
however, need them for the Toolkit application itself.

The star images provided in the example application are from Wikimedia Commons 
http://commons.wikimedia.org/wiki/Category:Nuvola_icons and are licensed under
GNU Lesser General Public License. 