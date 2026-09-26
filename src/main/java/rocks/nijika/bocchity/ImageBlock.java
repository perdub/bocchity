package rocks.nijika.bocchity;

import eu.pb4.polymer.blocks.api.BlockModelType;
import eu.pb4.polymer.blocks.api.PolymerBlockModel;
import eu.pb4.polymer.blocks.api.PolymerBlockResourceUtils;
import eu.pb4.polymer.blocks.api.PolymerTexturedBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;

/** A normal full cube on the server, rendered by Polymer using our generated model. */
public final class ImageBlock extends Block implements PolymerTexturedBlock {
    private final BlockState polymerState;

    public ImageBlock(AbstractBlock.Settings settings, Identifier id) {
        super(settings);
        this.polymerState = PolymerBlockResourceUtils.requestBlock(
                BlockModelType.FULL_BLOCK,
                PolymerBlockModel.of(id.withPath("block/" + id.getPath()))
        );

        if (this.polymerState == null) {
            throw new IllegalStateException("No Polymer FULL_BLOCK state available for " + id);
        }
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state) {
        return this.polymerState;
    }
}
