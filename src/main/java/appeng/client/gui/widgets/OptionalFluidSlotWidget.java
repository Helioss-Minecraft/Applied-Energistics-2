
package appeng.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraftforge.fml.client.gui.GuiUtils;

import appeng.api.storage.data.IAEFluidStack;
import appeng.container.slot.IOptionalSlotHost;
import appeng.util.fluid.IAEFluidTank;

public class OptionalFluidSlotWidget extends FluidSlotWidget {
    private final IOptionalSlotHost containerBus;
    private final int groupNum;
    private final int srcX;
    private final int srcY;

    public OptionalFluidSlotWidget(IAEFluidTank fluids, final IOptionalSlotHost containerBus, int slot, int id,
            int groupNum, int x, int y, int xoffs, int yoffs) {
        super(fluids, slot, id, x + xoffs * 18, y + yoffs * 18);
        this.containerBus = containerBus;
        this.groupNum = groupNum;
        this.srcX = x;
        this.srcY = y;
    }

    @Override
    public boolean isSlotEnabled() {
        if (this.containerBus == null) {
            return false;
        }
        return this.containerBus.isSlotEnabled(this.groupNum);
    }

    @Override
    public IAEFluidStack getFluidStack() {
        if (!this.isSlotEnabled() && super.getFluidStack() != null) {
            this.setFluidStack(null);
        }
        return super.getFluidStack();
    }

    @Override
    public void drawBackground(int guileft, int guitop, int currentZIndex) {
        RenderSystem.enableBlend();
        if (this.isSlotEnabled()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        } else {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.4F);
        }
        GuiUtils.drawTexturedModalRect(guileft + this.getTooltipAreaX() - 1, guitop + this.getTooltipAreaY() - 1,
                this.srcX - 1, this.srcY - 1, this.getTooltipAreaWidth() + 2, this.getTooltipAreaHeight() + 2,
                currentZIndex);
    }
}