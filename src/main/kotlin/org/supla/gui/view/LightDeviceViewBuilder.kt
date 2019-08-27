// Copyright (C) AC SOFTWARE SP. Z O.O.
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
package org.supla.gui.view

import com.jfoenix.controls.JFXToggleButton
import javafx.beans.property.BooleanProperty
import javafx.scene.Node
import org.supla.gui.uidevice.UiChannel
import org.supla.gui.uidevice.UiOnOffState

class LightDeviceViewBuilder : DeviceViewBuilder {
    override fun build(channel: UiChannel, updating: BooleanProperty): Node? =
            if (channel.state is UiOnOffState) {
                val toggle = JFXToggleButton()
                toggle.disableProperty().bind(channel.input)
                toggle.isWrapText = true
                toggle.disableProperty().bind(updating)
                toggle.textProperty().bindBidirectional(channel.caption)
                toggle.selectedProperty().bindBidirectional(channel.state.on)
                toggle
            } else {
                null
            }
}
