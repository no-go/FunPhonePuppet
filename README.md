# Fun Phone Puppet

use your phone as puppet - dein Handy wird zur Handpuppe

![Logo](app/src/main/res/drawable/ic_launcher.png)

These fullscreen app uses four images to simulate a puppet. If you do not move or shake your
phone, the screen shows only one image. Randomly a *blinky* image is shown for a short time.
The other two images will be shown, if you move or shake the phone.

Support me: <a href="https://flattr.com/thing/5195033" target="_blank">![Flattr This](flattr.png)</a>

**Deutsch** : Mit dieser App kannst du durch das Bewegen deines Handys ein Handpuppe
simulieren. Die App reagiert auf Erschütterung. Ist die Erschütterung stärker,
wird an Stelle vom Bild *force1.jpg* das Bild *force2.jpg* genommen. Auf diese
Weise sieht es so aus, als wenn sich der Mund von Bild *normal.jpg* bewegt. Damit
es nicht langweilig wird, gibt es noch Bild *blinky.jpg* was für das zufällige
Blinzeln benutzt wird. Die Bilder werden, wenn sie beim Starten der App
nicht da sind, von der App unter `Documents/click.dummer.funphonepuppet/` automatisch
angelegt. Du kannst die Bilder ändern! Der Bildpunkt oben rechts in *blinky.jpg* wird
als Hintergrundfarbe der App genommen. Die Datei *neehe.mp3* wird bei jeder
Erschütterung gespielt, wenn man in der App in einer Checkbox einen Haken
setzt.

Video: [action.m4v](https://github.com/no-go/FunPhonePuppet/blob/master/action.m4v?raw=true)

The default 4 images are stored under ...

  - normal.jpg : normal - no force (slow or no phone movement)
  - force1.jpg : a low force (you shake your phone a bit)
  - force2.jpg : a hard force (you shake your phone more intensive)
  - blinky.jpg : this image pops up randomly every 0.5 till 5 sec
  - neehe.mp3 : if you check a checkbox in the App, this sound is playing on every shake

... at `Documents/click.dummer.funphonepuppet/` on your phone. The color of the background is the (0,0) upper left pixel
of `blinky.jpg` - Change these files and create new ideas of using these app!

Get a signed APK from here: [FunPhonePuppet.apk](https://github.com/no-go/FunPhonePuppet/blob/master/app/app-release.apk?raw=true)
or search it on [f-Droid](http://f-droid.org)


