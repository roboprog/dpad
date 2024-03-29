dpad
====

Direction-pad (buttons) demo for Android - press up/down/left/right buttons and move marker on graphics canvas

Demo program was developed and built ON Android using the "Terminal IDE" environment -
https://play.google.com/store/apps/details?id=com.spartacusrex.spartacuside

---

LICENSE:  GPL version 2.x

This is the same license as Terminal IDE, from which I have included library jars for your convenience.

---

Recommended reading:
* src/com/roboprogs/dpad/MainActivity.java
* res/layout/main.xml
* Makefile

(yes, Makefile!  No bloody Ant, Maven nor Gradle)

---

I have yet to test this on devices other than my Nexus 7, and there are one or two "dead files" to remove,
but it should provide you with a short example of getting a graphics canvas,
scaling your drawing to fit the viewport and trapping the events to update the display.


While you can edit and build a program such as this with Terminal IDE,
I recommend that you use an IDE (e.g. - Android Studio) to set up the layout file,
as the layout XML/class documentation is "multi-layered", and hard to follow.
