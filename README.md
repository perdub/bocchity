# Bocchity — Fabric 1.21.1 + Polymer

Это **не пиксель-арт-генератор**.

Проект делает ровно следующее:

```text
images/nijika.png
        ↓ gradlew build
bocchity:nijika
        ↓
обычный куб-блок
        ↓
все 6 граней используют ОДНУ И ТУ ЖЕ исходную картинку
```

## Что положить в проект

Картинки кладутся сюда:

```text
images/
```

Например:

```text
images/
├── nijika.png
├── bocchi.png
└── ryo.png
```

При `build` генератор:

1. копирует исходные изображения в resource pack мода без изменения;
2. генерирует blockstate;
3. генерирует `cube_all` block model;
4. генерирует item model;
5. генерирует Java-код регистрации именно тех блоков, которые есть в `images/`.

Никаких скриптов, читающих PNG во время игры, нет.

## Сборка

Minecraft 1.21.1 для проекта собран под **Java 21**.

На Windows:

```powershell
gradlew.bat build
```

В Linux/macOS:

```bash
./gradlew build
```

Готовый мод будет в:

```text
build/libs/bocchity-1.0.0.jar
```

## Использование на сервере

Положи JAR в `server/mods/`.

После запуска сервер зарегистрирует блоки. Polymer Textured Blocks использует серверный resource pack, поэтому клиенту нужен ресурс-пак Polymer; это позволяет клиентам без самого Bocchity видеть текстуры блоков. Polymer документирует `FULL_BLOCK` как тип для полноразмерных непрозрачных блоков и требует активного server resource pack для Textured Blocks.

Получить блок:

```mcfunction
/give @s bocchity:nijika
```

После этого предмет можно поставить как обычный блок.

Если картинка называется `bocchi.png`, команда будет:

```mcfunction
/give @s bocchity:bocchi
```

Блок попадает в творческий инвентарь во вкладку Building Blocks.

## Важно про картинку

Картинка не режется на части и не раскладывается по нескольким блокам.

Она является **одной текстурой куба** и применяется через `minecraft:block/cube_all` к `up`, `down`, `north`, `south`, `west` и `east`.

Поэтому квадратная картинка обычно выглядит наиболее предсказуемо. Если изображение прямоугольное, Minecraft растянет всю текстуру на квадратную грань блока.

Также `FULL_BLOCK` предназначен для непрозрачных кубов; прозрачность PNG не превращает блок в плоскость или стекло.

## GitHub Actions

The repository includes `.github/workflows/build.yml`.

On every push to `main` it:

1. installs Java 21 and Gradle 8.10.2;
2. runs the existing `generateBocchity` build-time compiler as part of `gradle build`;
3. produces `bocchity.jar`;
4. uploads the JAR as a GitHub Actions artifact;
5. replaces the `latest` pre-release with the newest JAR.

The direct release asset URL after pushing the repository to GitHub is:

```text
https://github.com/OWNER/REPOSITORY/releases/download/latest/bocchity.jar
```

Replace `OWNER/REPOSITORY` with the actual GitHub repository path.
