from pathlib import Path
import json, re, zipfile

ROOT = Path(__file__).resolve().parent
assert (ROOT / 'images').is_dir()
assert 'tasks.named(\'sourcesJar\')' in (ROOT / 'build.gradle').read_text()
assert 'dependsOn(tasks.named(\'generateBocchity\'))' in (ROOT / 'build.gradle').read_text()
assert (ROOT / '.github/workflows/build.yml').is_file()
workflow = (ROOT / '.github/workflows/build.yml').read_text()
for needle in ["gradle clean build --no-daemon", "actions/upload-artifact@v7", "gh release create latest bocchity.jar"]:
    assert needle in workflow, needle
fabric = json.loads((ROOT/'src/main/resources/fabric.mod.json').read_text())
assert fabric['id'] == 'bocchity'
assert fabric['version'] == '${version}'

# Validate the committed project is internally consistent even without downloading Gradle deps.
for rel in [
    'src/main/java/rocks/nijika/bocchity/BocchityMod.java',
    'src/main/java/rocks/nijika/bocchity/ImageBlock.java',
    'src/main/java/rocks/nijika/bocchity/ImageBlockItem.java',
]:
    assert (ROOT/rel).is_file(), rel

java = (ROOT/'src/main/java/rocks/nijika/bocchity/ImageBlock.java').read_text()
assert 'BlockModelType.FULL_BLOCK' in java
assert 'PolymerBlockModel.of' in java
item = (ROOT/'src/main/java/rocks/nijika/bocchity/ImageBlockItem.java').read_text()
assert 'extends PolymerBlockItem' in item

print('Static validation OK')
