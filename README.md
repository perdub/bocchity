# Bocchity — Fabric 1.21.1 + Polymer

Bocchity turns images in three folders into **one custom Polymer block per image** at build time. No image files are read by the server at runtime.

```text
images/
├── full_blocks/   → normal cube
├── transparent/   → cube with cutout alpha
└── sphere/        → rounded voxel-sphere model
```

The filename becomes the block ID:

```text
images/full_blocks/plush_nijika.png
→ bocchity:plush_nijika
```

Each generated block is still a normal server-side `1×1×1` block with a full cube collision box. Polymer supplies the client-side model and resource-pack assets. Polymer's `PolymerBlockModel` can point at a custom block model, while `BlockModelType` chooses the client-side block-state pool used for the server-side representation. citehttps://polymer.pb4.eu/0.11.x/polymer-blocks/basics/

## Image folders

### `images/full_blocks`

One image becomes a normal cube. The texture is applied to all six faces.

```text
images/full_blocks/nijika.png
→ bocchity:nijika
```

### `images/transparent`

One image becomes a full-collision cube rendered with Polymer's transparent/cutout block type. Use PNG alpha for transparent areas. The block keeps its full cube collision box, while transparent pixels are cut out visually.

```text
images/transparent/logo.png
→ bocchity:logo
```

### `images/sphere`

One image becomes a block with a full `1×1×1` collision box but a rounded, low-poly/voxel-sphere client-side model. If the source image has transparent pixels, transparent rendering is enabled automatically.

```text
images/sphere/ball.png
→ bocchity:ball
```

Vanilla block models are cuboid-based, so the sphere is an approximation made from many small cuboid elements rather than a mathematically smooth mesh. The result is intended to look like a rounded sphere while remaining an ordinary server-side block.

## Supported files

PNG, JPG and JPEG are accepted. PNG files are copied into the generated resource pack unchanged. JPG/JPEG files are decoded and re-encoded as real PNG textures.

IDs are normalized from filenames and must be unique across all three folders.

## Build locally

This project targets Minecraft **1.21.1** and Java **21**.

```bash
gradle clean build
```

The mod JAR is produced in:

```text
build/libs/bocchity-1.0.0.jar
```

## In Minecraft

Put the built JAR into the server's `mods/` folder together with the required Fabric/Polymer dependencies.

For example:

```mcfunction
/give @s bocchity:plush_nijika
```

The item can be placed like a normal block. The client does not need Bocchity itself; Polymer provides the server resource pack.

## GitHub Actions

`.github/workflows/build.yml` builds the existing image compiler on pushes to `main`, verifies the resulting JAR, uploads `bocchity.jar` as an Actions artifact, and updates the `latest` prerelease. Pull requests build the project but do not publish the release.

Stable latest-download URL:

```text
https://github.com/OWNER/REPOSITORY/releases/download/latest/bocchity.jar
```
