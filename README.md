# Bocchity — Fabric 1.21.1 + Polymer

Bocchity turns every image in `images/` into **one custom Polymer cube block** at build time.

```text
images/nijika.png
       │
       └── Gradle build
                │
                ▼
        bocchity:nijika
                │
                ▼
        one normal 1×1×1 cube
        same image on all 6 faces
```

The image is **not** converted into pixels and it is **not** used to create a wall of blocks.

## Add images

Put `.png`, `.jpg`, or `.jpeg` files into:

```text
images/
```

For example:

```text
images/
├── nijika.png
├── bocchi.jpg
└── ryo.jpeg
```

One input image produces one block with the same base name:

```text
nijika.png  →  bocchity:nijika
bocchi.jpg  →  bocchity:bocchi
ryo.jpeg    →  bocchity:ryo
```

The build generates the blockstate, `cube_all` model, item model, translation, block registration source, and texture.

PNG files are copied without re-encoding. JPG/JPEG files are decoded and converted to a valid PNG texture because Minecraft expects the generated texture file to actually be PNG.

## Local build

This project targets Minecraft **1.21.1** and Java **21**.

Install Gradle 8.10.2 locally and run:

```bash
gradle clean build
```

On Windows the same command is:

```powershell
gradle clean build
```

The generated JAR is:

```text
build/libs/bocchity-1.0.0.jar
```

The project intentionally does not require a local PNG-reading step at runtime: image processing happens only during the Gradle build.

## In Minecraft

Put the JAR into the server's `mods/` directory.

For `images/nijika.png`, the item is:

```mcfunction
/give @s bocchity:nijika
```

Place it like any normal full cube.

The block uses a Polymer `FULL_BLOCK` representation. Polymer documents `FULL_BLOCK` as the full-collision, opaque textured-block type; textured blocks require the Polymer server resource pack to render correctly. The mod registers its assets with Polymer and marks the pack as required.

The block item is also handled through Polymer custom model data so a vanilla client sees the generated cube model in inventory/hand instead of merely seeing the fallback Barrier icon.

## GitHub Actions

`.github/workflows/build.yml` does the following on every push to `main`:

1. installs Java 21;
2. installs Gradle 8.10.2;
3. runs the existing build-time image generator as part of `gradle clean build`;
4. verifies the generated resource tree and resulting JAR;
5. uploads `bocchity.jar` as a GitHub Actions artifact;
6. replaces the prerelease tagged `latest` with the new JAR.

Pull requests also run the complete build, but do not publish the `latest` release.

Once the repository is on GitHub, the release asset has this stable URL:

```text
https://github.com/OWNER/REPOSITORY/releases/download/latest/bocchity.jar
```

Replace `OWNER/REPOSITORY` with the actual repository path.
