package ru.dmn.ae2ao.mixin;

import appeng.block.networking.BlockController;
import appeng.me.GridAccessException;
import appeng.tile.grid.AENetworkPowerTile;
import appeng.tile.networking.TileController;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TileController.class, remap = false)
public abstract class MixinTileController extends AENetworkPowerTile {
    @Shadow
    protected abstract boolean checkController(final BlockPos pos);

    /**
     * @author DomamaN202
     * @reason Zахотел
     */
    @Overwrite
    private void updateMeta() {
        if (!this.getProxy().isReady())
            return;

        BlockController.ControllerBlockState metaState = BlockController.ControllerBlockState.offline;

        try {
            if (this.getProxy().getEnergy().isNetworkPowered())
                metaState = BlockController.ControllerBlockState.online;
        } catch (final GridAccessException ignored) {
        }

        if (this.checkController(this.pos) && this.world.getBlockState(this.pos).getValue(BlockController.CONTROLLER_STATE) != metaState) {
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(BlockController.CONTROLLER_STATE, metaState));
        }
    }
}
