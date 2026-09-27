from pathlib import Path
import json

ROOT = Path(__file__).resolve().parent
BUILD = (ROOT / 'build.gradle').read_text()

for folder in ('full_blocks', 'transparent', 'sphere'):
    assert (ROOT / 'images' / folder).is_dir(), folder

for needle in [
    "tasks.register('generateBocchity')",
    "images/full_blocks",
    "images/transparent",
    "images/sphere",
    "tasks.named('sourcesJar')",
    "dependsOn(tasks.named('generateBocchity'))",
    "sphereModel",
]:
    assert needle in BUILD, needle

assert 'images.eachFile' not in BUILD
assert 'outer:' not in BUILD

workflow = (ROOT / '.github/workflows/build.yml').read_text()
for needle in [
    'gradle clean build --no-daemon',
    'actions/upload-artifact@v7',
    'gh release create latest bocchity.jar',
]:
    assert needle in workflow, needle

fabric = json.loads((ROOT / 'src/main/resources/fabric.mod.json').read_text())
assert fabric['id'] == 'bocchity'
assert fabric['version'] == '${version}'

for rel in [
    'src/main/java/rocks/nijika/bocchity/BocchityMod.java',
    'src/main/java/rocks/nijika/bocchity/ImageBlock.java',
    'src/main/java/rocks/nijika/bocchity/ImageBlockItem.java',
]:
    assert (ROOT / rel).is_file(), rel

java = (ROOT / 'src/main/java/rocks/nijika/bocchity/ImageBlock.java').read_text()
assert 'extends Block implements PolymerTexturedBlock' in java
assert 'PolymerBlockResourceUtils.requestBlock' in java

print('Static project validation OK')
