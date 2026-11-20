# cards-core

Android-unabhängige Java-Bibliothek für Kartenidentität, Kartensätze und
reproduzierbares Mischen. Spielregeln, Punktwerte und Bilder gehören in die
jeweiligen Spiele und Oberflächen.

`Card` besteht aus einer stabilen Kennung innerhalb des Kartensatzes, Rang und
Farbe. Die Kennung ist **keine** Bildadresse. `CardSet.of(...)` nimmt beliebige
Karten auf und prüft doppelte Kennungen und Kartenidentitäten. `french52()` erzeugt
die 52 französischen Karten mit den bisherigen Pisti-Kennungen. Die 24 Karten
für Altmışaltı lassen sich als Teilmenge erstellen:

```java
CardSet sixtySix = CardSet.french52().onlyRanks("9", "J", "Q", "K", "T", "A");
Deck deck = new Deck(sixtySix);
deck.createCards();
deck.shuffleDeck(new Random(2026));
Card card = deck.dealTopCardOnDec();
```

Die Bibliothek ist als Gradle-Build `ch.sakru:cards-core:0.1.0` angelegt. Für
die gemeinsame Entwicklung bindet Pisti das Repository als gepinntes Git-
Submodul und Gradle Composite Build ein. Die veröffentlichte Maven-Koordinate
ist vorbereitet; `publishToMavenLocal` veröffentlicht lokal. Auf einem
öffentlichen Paketserver wurde noch nichts veröffentlicht. Der Git-Commit
des Submoduls bestimmt damit derzeit die tatsächlich verwendete Version.

Tests: `./gradlew test` unter Linux/macOS oder `gradlew.bat test` unter Windows
(Gradle 9.5 und eine passende JDK-Installation vorausgesetzt).

Lizenz: Für den Quellcode ist noch keine Wiederverwendungslizenz festgelegt.
Das Repository enthält keine Kartenbilder.
