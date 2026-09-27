# Bocchity image folders

Put images into one of these folders. The folder determines the generated block type — no config file is needed.

```text
images/
├── full_blocks/
├── transparent/
└── sphere/
```

Supported input formats: PNG, JPG and JPEG.

## `full_blocks`

A normal full `1×1×1` cube. The image is the texture on all six faces.

```text
images/full_blocks/nijika.png
→ bocchity:nijika
```

## `transparent`

A full `1×1×1` cube with Polymer cutout transparency. Use a PNG with an alpha channel for the background you want to remove. The server-side collision remains a full cube.

```text
images/transparent/logo.png
→ bocchity:logo
```

## `sphere`

A full `1×1×1` collision block whose client-side model is generated as a rounded voxel sphere. The sphere model uses the image as its texture. If the PNG contains alpha, Polymer's transparent block type is used automatically so transparent pixels remain cut out.

Minecraft vanilla block models are cuboid-based, so the generated sphere is an approximation made from many small cuboid elements rather than a mathematically smooth mesh.

```text
images/sphere/ball.png
→ bocchity:ball
```

Names are taken from the filename and normalized to a valid Minecraft ID. IDs must be unique across all three folders.
