BUEN CAMINO ANDROID V1.0
========================

ESTE ES EL PROYECTO ANDROID DE LA APP.
Base web incluida: BUEN CAMINO V10.0.6 PRO - 2D CORREGIDO.
Web publicada que abre normalmente: https://inamark60-hue.github.io/camino-frances/

COMO RECONOCERLO
----------------
Nombre de la carpeta/proyecto:
BUEN-CAMINO-ANDROID-V1.0

Nombre que verás instalada en el móvil:
BUEN CAMINO

Identificador Android:
com.buencamino.app

Versión Android inicial:
1.0

Archivo APK cuando se compile:
app-debug.apk

DONDE ESTA TU V10.0.6 DENTRO DEL PROYECTO
-----------------------------------------
app/src/main/assets/www/

Ahí están index.html, app.js, styles.css, etc.
No sustituye ni modifica tu ZIP estable original.

COMO FUNCIONA
-------------
1. Al abrir la app intenta cargar tu web publicada en GitHub Pages.
2. Mantiene JavaScript, almacenamiento local y geolocalización.
3. Los enlaces externos se abren fuera de la app.
4. Si no puede abrir GitHub Pages, intenta abrir la copia V10.0.6 incluida dentro de la app.

IMPORTANTE
----------
Este ZIP es un PROYECTO Android, no es todavía un APK instalable.
Para tener el APK hay que compilarlo con Android Studio o con GitHub Actions.

OPCION A - GITHUB ACTIONS (sin Android Studio)
----------------------------------------------
1. Crea en GitHub un repositorio NUEVO, por ejemplo: buen-camino-android
2. Sube TODO el contenido de esta carpeta al repositorio nuevo.
3. En GitHub entra en la pestaña Actions.
4. Abre "Construir APK BUEN CAMINO".
5. Pulsa "Run workflow" si no se ha ejecutado automáticamente.
6. Cuando termine en verde, abre la ejecución.
7. Abajo, en Artifacts, descarga: BUEN-CAMINO-ANDROID-V1.0-APK
8. Dentro estará app-debug.apk. Ese es el archivo que puedes instalar en Android.

OPCION B - ANDROID STUDIO
-------------------------
1. Descomprime este ZIP en un ordenador.
2. Abre Android Studio.
3. File > Open y elige la carpeta BUEN-CAMINO-ANDROID-V1.0.
4. Espera a que termine la sincronización Gradle.
5. Build > Build APK(s).
6. El APK quedará en:
   app/build/outputs/apk/debug/app-debug.apk

PARA GOOGLE PLAY
----------------
Después conviene generar una versión firmada AAB (Android App Bundle),
con tu clave de firma definitiva. No uses el APK debug para publicar en Play Store.
