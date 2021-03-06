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

package appeng.client.gui.widgets;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import appeng.api.config.ActionItems;
import appeng.client.gui.Icon;
import appeng.core.localization.ButtonToolTips;

public class ActionButton extends IconButton {
    private static final Pattern PATTERN_NEW_LINE = Pattern.compile("\\n", Pattern.LITERAL);
    private final Icon icon;

    public ActionButton(final ActionItems action, Consumer<ActionItems> onPress) {
        super(btn -> onPress.accept(action));

        ButtonToolTips displayName;
        ButtonToolTips displayValue;
        switch (action) {
            case WRENCH:
                icon = Icon.WRENCH;
                displayName = ButtonToolTips.PartitionStorage;
                displayValue = ButtonToolTips.PartitionStorageHint;
                break;
            case CLOSE:
                icon = Icon.CLEAR;
                displayName = ButtonToolTips.Clear;
                displayValue = ButtonToolTips.ClearSettings;
                break;
            case STASH:
                icon = Icon.CLEAR;
                displayName = ButtonToolTips.Stash;
                displayValue = ButtonToolTips.StashDesc;
                break;
            case ENCODE:
                icon = Icon.WHITE_ARROW_DOWN;
                displayName = ButtonToolTips.Encode;
                displayValue = ButtonToolTips.EncodeDescription;
                break;
            case ENABLE_SUBSTITUTION:
                icon = Icon.SUBSTITUTION_ENABLED;
                displayName = ButtonToolTips.Substitutions;
                displayValue = ButtonToolTips.SubstitutionsDescEnabled;
                break;
            case DISABLE_SUBSTITUTION:
                icon = Icon.SUBSTITUTION_DISABLED;
                displayName = ButtonToolTips.Substitutions;
                displayValue = ButtonToolTips.SubstitutionsDescDisabled;
                break;
            default:
                throw new IllegalArgumentException("Unknown ActionItem: " + action);
        }

        setMessage(buildMessage(displayName, displayValue));
    }

    @Override
    protected Icon getIcon() {
        return icon;
    }

    private ITextComponent buildMessage(ButtonToolTips displayName, ButtonToolTips displayValue) {
        String name = displayName.text().getString();
        String value = displayValue.text().getString();

        value = PATTERN_NEW_LINE.matcher(value).replaceAll("\n");
        final StringBuilder sb = new StringBuilder(value);

        int i = sb.lastIndexOf("\n");
        if (i <= 0) {
            i = 0;
        }
        while (i + 30 < sb.length() && (i = sb.lastIndexOf(" ", i + 30)) != -1) {
            sb.replace(i, i + 1, "\n");
        }

        return new StringTextComponent(name + '\n' + sb);
    }

}
