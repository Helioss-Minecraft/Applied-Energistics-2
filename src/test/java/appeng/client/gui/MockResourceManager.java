/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2021, TeamAppliedEnergistics, All rights reserved.
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

package appeng.client.gui;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResource;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

import appeng.core.AppEng;

/**
 * Fake resource manager that more or less loads AE2 resource pack resources.
 */
public final class MockResourceManager {
    private MockResourceManager() {
    }

    public static IReloadableResourceManager create() throws IOException {
        IReloadableResourceManager resourceManager = mock(IReloadableResourceManager.class, withSettings().lenient());

        when(resourceManager.getResource(any())).thenAnswer(invoc -> {
            ResourceLocation loc = invoc.getArgument(0);
            return getResource(loc);
        });
        when(resourceManager.getAllResources(any())).thenAnswer(invoc -> {
            ResourceLocation loc = invoc.getArgument(0);
            return Collections.singletonList(getResource(loc));
        });

        when(resourceManager.getResourceNamespaces()).thenReturn(
                ImmutableSet.of("minecraft", AppEng.MOD_ID));

        return resourceManager;
    }

    private static IResource getResource(ResourceLocation loc) throws FileNotFoundException {
        InputStream in = MockResourceManager.class
                .getResourceAsStream("/assets/" + loc.getNamespace() + "/" + loc.getPath());
        if (in == null) {
            throw new FileNotFoundException("Missing resource: " + loc.getPath());
        }

        return new IResource() {
            @Override
            public ResourceLocation getLocation() {
                return loc;
            }

            @Override
            public InputStream getInputStream() {
                return in;
            }

            @Nullable
            @Override
            public <T> T getMetadata(IMetadataSectionSerializer<T> serializer) {
                return null;
            }

            @Override
            public String getPackName() {
                return "ae2";
            }

            @Override
            public void close() throws IOException {
                in.close();
            }
        };
    }
}
