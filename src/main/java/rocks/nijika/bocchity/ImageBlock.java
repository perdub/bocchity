package rocks.nijika.bocchity;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;

/**
 * A normal server block with a full 1x1x1 collision box.
 *
 * The client-side appearance is supplied by Polymer. Sphere blocks are still
 * full blocks on the server; only their client-side model is rounded.
 */
public final class ImageBlock extends Block implements PolymerTexturedBlock {
    private final BlockState polymerState;

    public ImageBlock(AbstractBlock.Settings settings, Identifier id, String kind, boolean transparent) {
        super(transparent ? settings.nonOpaque() : settings);

        BlockModelType type = transparent
                ? BlockModelType.TRANSPARENT_BLOCK
                : BlockModelType.FULL_BLOCK;

        this.polymerState = PolymerBlockResourceUtils.requestBlock(
                type,
                PolymerBlockModel.of(id.withPath("block/" + id.getPath()))
        );

        if (this.polymerState == null) {
            throw new IllegalStateException("No Polymer " + type + " state available for " + id + " (kind=" + kind + ")");
        }
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return this.polymerState;
    }
}
