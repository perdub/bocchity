package rocks.nijika.bocchity;

import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;

public final class BocchityMod implements ModInitializer {
    public static final String MOD_ID = "bocchity";

    @Override
    public void onInitialize() {
        // Tell Polymer to include this mod's assets in its server resource pack.
        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();

        // This generated class is produced from images/* during Gradle build.
        GeneratedBocchityBlocks.register();
    }
}
