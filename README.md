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

## Transparency

For PNG files, Bocchity scans the decoded pixels during the build.

- A fully opaque image uses Polymer `FULL_BLOCK`.
- If at least one pixel has alpha `< 255`, the block uses Polymer `TRANSPARENT_BLOCK` and the server block is marked `nonOpaque()`.
- The original PNG bytes are preserved, so an existing transparent background stays transparent.
- Transparent image blocks still have the normal full `1×1×1` block collision on the server.
- Because Minecraft lighting is calculated per block rather than per texture pixel, the entire transparent image block is non-opaque: light can continue through consecutive transparent image blocks instead of stopping at the first one.

Polymer documents `FULL_BLOCK` as not supporting transparency and `TRANSPARENT_BLOCK` as the block type for cutout textures. 

JPG/JPEG inputs do not contain alpha and therefore remain normal opaque blocks after conversion to PNG.

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

For a PNG with transparent pixels, the alpha background is cut out visually while the block keeps full cube collision. Transparent blocks also allow light to pass through consecutive blocks.

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
