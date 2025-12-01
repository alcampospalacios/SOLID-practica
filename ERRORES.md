# Análisis de Violaciones de Principios SOLID

## 1. Interface Segregation Principle (ISP) ❌ → ✅ ARREGLADO

**El problema:** Teníamos una interfaz `FileSystemItem` gigante que obligaba a todas las clases a implementar métodos que no tenían sentido para ellas.

**Lo que pasaba:**
- `Directory` tenía que implementar cosas de archivos como `open()`, `close()`, `read()`, `write()`, `getExtension()`... y todos esos métodos simplemente tiraban `UnsupportedOperationException`

- `File` tenía que implementar cosas de directorios como `listFiles()`, `addFile()`, `removeFile()`... y también lanzaban excepciones

**Por qué era un problema:**
- Podías llamar métodos que no hacían nada y petaban en runtime
- Básicamente estábamos forzando a las clases a implementar cosas que no usaban
- Un montón de código inútil lanzando excepciones por todos lados

**La solución:**
Dividimos esa interfaz mas grande en varias más pequeñas:
- `FileSystemItem` (base): lo básico que todos tienen (`getName()`, `getSize()`, etc.)
- `Readable`: para leer (`open()`, `read()`, `close()`, `setPosition()`)
- `Writable`: para escribir (`write()`)
- `Container`: para cosas que contienen otras cosas (`listFiles()`, `addFile()`, `removeFile()`)

Ahora:
- `File` implementa `Readable` y `Writable`
- `Directory` implementa `Container`
- Se acabaron las `UnsupportedOperationException`

---

## 2. Single Responsibility Principle (SRP) ❌ → ✅ ARREGLADO

**Problema #1:** La clase `File` hacía demasiadas cosas.

**Lo que pasaba:**
- `File` gestionaba las operaciones básicas de archivos
- Pero TAMBIÉN tenía métodos para convertir audio:
  - `convertMp3ToWav()`
  - `convertWavToMp3()`

**Por qué era un problema:**
- La clase tenía varias razones para cambiar (operaciones de archivo O conversión de formatos)
- Mezclar I/O con lógica de negocio es mala idea
- Un lío para hacer tests y mantener

**La solución:**
Sacamos toda la lógica de conversión de audio a una clase nueva `AudioConverter`:
- Los métodos de conversión ahora viven en `AudioConverter`
- `File` solo se ocupa de lo suyo: operaciones básicas
- Cada clase con su responsabilidad

---

**Problema #2:** `FileManager` tenía código duplicado haciendo lo mismo de formas diferentes.

**Lo que pasaba:**
- Dos métodos `calculateSize()` que hacían lo mismo pero diferente
- Usaban `instanceof` para ver qué tipo de objeto era
- Y encima la funcionalidad ya existía en `getSize()` de cada clase

**Por qué era un problema:**
- Código repetido que había que mantener sincronizado
- Confuso y redundante
- Violaba el "Don't Repeat Yourself"

**La solución:**
Simplificamos `FileManager`:
- `calculateSize(FileSystemItem)` ahora simplemente hace `fileSystemItem.getSize()`
- `calculateSize(List<FileSystemItem>)` usa streams para sumar
- Adiós `instanceof` y código duplicado

---

## 3. Open/Closed Principle (OCP) ❌ → ✅ ARREGLADO

**El problema:** `FileManager` usaba `instanceof` para decidir cómo procesar cada cosa.

**Lo que pasaba:**
- `FileManager.calculateSize()` checkeaba si era `File` o `Directory`
- Si añadías un nuevo tipo, tenías que tocar este método

**Por qué era un problema:**
- Cada vez que añades algo nuevo tienes que modificar código existente
- Acoplamiento fuerte con clases concretas
- Va en contra de "abierto para extensión, cerrado para modificación"

**La solución:**
Usamos polimorfismo como debe ser:
- Cada clase ya tiene su propio `getSize()` bien implementado
- `FileManager.calculateSize()` simplemente delega a ese método
- Se acabó el `instanceof`
- Puedes añadir nuevos tipos de `FileSystemItem` sin tocar `FileManager`

---

## 4. Dependency Inversion Principle (DIP) ❌ → ✅ ARREGLADO

**Problema #1:** `FileSystemItemBase.setParent()` dependía de una clase concreta.

**Lo que pasaba:**
- Checkeaba que el padre fuera específicamente un `Directory`

**Por qué era un problema:**
- Acoplado a implementaciones concretas
- Difícil de extender y testear

**La solución:**
Ahora depende de la interfaz:
- Verifica que el padre implemente `Container` en vez de ser un `Directory` concreto
- Vale cualquier implementación de `Container`
- Dependemos de abstracciones, no de cosas concretas

---

**Problema #2:** Los métodos de conversión de audio creaban objetos directamente.

**Lo que pasaba:**
- `File.convertMp3ToWav()` hacía `new File(parent, newFileName)`
- `File.convertWavToMp3()` lo mismo

**Por qué era un problema:**
- Acoplado a implementaciones concretas
- Imposible hacer tests con mocks
- No puedes cambiar la implementación sin tocar código

**La solución:**
Movimos la lógica a `AudioConverter`:
- Ahora la responsabilidad de crear archivos está en la capa de servicio
- Más fácil inyectar dependencias si hace falta
- Mejor separación de conceptos

---

## 5. Liskov Substitution Principle (LSP) ❌ → ✅ ARREGLADO

**El problema:** `File` y `Directory` no podían reemplazar a `FileSystemItem` sin petar.

**Lo que pasaba:**
- `Directory.open()`, `close()`, `read()`, `write()` tiraban `UnsupportedOperationException`
- `File.listFiles()`, `addFile()`, `removeFile()` también
- No podías usar polimorfismo sin saber de antemano qué tipo concreto era

**Por qué era un problema:**
- El polimorfismo no era real, no podías confiar en él
- Tenías que hacer checks de tipos antes de llamar métodos
- Excepciones en runtime en vez de errores en compilación

**La solución:**
Con las interfaces segregadas, cada clase solo implementa lo que de verdad soporta:
- `File` implementa `Readable` y `Writable` pero NO `Container`
- `Directory` implementa `Container` pero NO `Readable` ni `Writable`
- Ahora haces `if (item instanceof Container)` y sabes que funciona
- Cero `UnsupportedOperationException`
- Los subtipos pueden sustituir a sus tipos base sin problemas

---
