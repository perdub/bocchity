package rocks.nijika.bocchity;

import eu.pb4.polymer.core.api.item.PolymerBlockItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * A Polymer block item that renders as the same generated cube model as its block
 * when the player does not have the Bocchity mod client-side.
 */
public final class ImageBlockItem extends PolymerBlockItem {
    private final int modelData;

    public ImageBlockItem(Block block, Identifier blockId) {
        super(block, new Item.Settings(), Items.BARRIER);
        PolymerModelData model = PolymerResourcePackUtils.requestModel(
                Items.BARRIER,
                blockId.withPath("block/" + blockId.getPath())
        );
        this.modelData = model.value();
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return this.modelData;
    }
}
