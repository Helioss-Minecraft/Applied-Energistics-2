/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.items.storage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;

import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IItemStorageChannel;
import appeng.api.storage.data.IAEItemStack;
import appeng.core.Api;
import appeng.core.definitions.AEItems;
import appeng.util.InventoryAdaptor;

public final class BasicStorageCellItem extends AbstractStorageCell<IAEItemStack> {

    protected final int bytesPerType;
    protected final double idleDrain;

    public BasicStorageCellItem(Properties props, IItemProvider whichCell, int kilobytes, float idleDrain,
            int bytesPerType) {
        super(props, whichCell, kilobytes);
        this.idleDrain = idleDrain;
        this.bytesPerType = bytesPerType;
    }

    @Override
    public int getBytesPerType(ItemStack cellItem) {
        return this.bytesPerType;
    }

    @Override
    public double getIdleDrain() {
        return this.idleDrain;
    }

    @Override
    public IStorageChannel<IAEItemStack> getChannel() {
        return Api.instance().storage().getStorageChannel(IItemStorageChannel.class);
    }

    @Override
    protected void dropEmptyStorageCellCase(final InventoryAdaptor ia, final PlayerEntity player) {
        final ItemStack extraA = ia.addItems(AEItems.EMPTY_STORAGE_CELL.stack());
        if (!extraA.isEmpty()) {
            player.dropItem(extraA, false);
        }
    }
}
