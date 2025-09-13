# Maintenance Timer (Paper plugin)

Inhalt: ein Maven-Projekt mit Source-Code für ein Paper 1.21.8 Plugin, das OPs erlaubt, einen Maintenance-Countdown zu setzen.

**Build (lokal)**
- Benötigt: Java 17, Maven.
- Im Projektordner ausführen:

  ```bash
  mvn clean package
  ```

- Die fertige JAR findest du unter `target/maintenance-timer-1.0.0.jar`. Diese JAR in deinen `plugins/`-Ordner kopieren und Server neustarten.

**Hinweis:** In `pom.xml` ist die Paper-API-Version auf `1.21.8-R0.1-SNAPSHOT` gesetzt. Passe sie an, falls dein Server eine andere Paper-Version nutzt.
