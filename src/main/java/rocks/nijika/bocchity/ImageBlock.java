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
 * A normal full-collision server block rendered through Polymer.
 *
 * Opaque images use FULL_BLOCK. Images containing transparent pixels use
 * TRANSPARENT_BLOCK + nonOpaque(). The server-side block still has the normal
 * full cube collision shape, while Polymer uses a transparent client-side
 * representation that supports cutout alpha and light passing through it.
 */
public final class ImageBlock extends Block implements PolymerTexturedBlock {
    private final BlockState polymerState;

    public ImageBlock(AbstractBlock.Settings settings, Identifier id, boolean transparent) {
        super(transparent ? settings.nonOpaque() : settings);

        BlockModelType type = transparent
                ? BlockModelType.TRANSPARENT_BLOCK
                : BlockModelType.FULL_BLOCK;

        this.polymerState = PolymerBlockResourceUtils.requestBlock(
                type,
                PolymerBlockModel.of(id.withPath("block/" + id.getPath()))
        );

        if (this.polymerState == null) {
            throw new IllegalStateException("No Polymer " + type + " state available for " + id);
        }
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return this.polymerState;
    }
}
