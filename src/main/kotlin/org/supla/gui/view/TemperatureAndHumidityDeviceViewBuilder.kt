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

import javafx.beans.binding.Bindings
import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import org.supla.gui.i18n.InternationalizationService
import org.supla.gui.uidevice.UiChannel
import org.supla.gui.uidevice.UiHumidityState
import org.supla.gui.uidevice.UiTemperatureAndHumidityState
import org.supla.gui.uidevice.UiTemperatureState
import pl.grzeslowski.jsupla.api.channel.state.Percentage
import java.math.BigDecimal
import java.math.BigDecimal.ROUND_CEILING
import java.util.concurrent.Callable
import javax.inject.Inject

class TemperatureAndHumidityDeviceViewBuilder @Inject constructor(private val internationalizationService: InternationalizationService) : DeviceViewBuilder {
    override fun build(channel: UiChannel, updating: BooleanProperty): Node? =
            if (channel.state is UiTemperatureState || channel.state is UiHumidityState || channel.state is UiTemperatureAndHumidityState) {
                @Suppress("ThrowableNotThrown")
                val left = VBox(3.0)
                val right = VBox(3.0)

                val state = channel.state
                if (state is UiTemperatureState) {
                    addTemperatureLabel(state.temperature, left, right, updating)
                }

                if (state is UiHumidityState) {
                    addHumidityLabel(state.humidity, left, right, updating)
                }

                if (state is UiTemperatureAndHumidityState) {
                    addTemperatureLabel(state.temperature, left, right, updating)
                    addHumidityLabel(state.humidity, left, right, updating)
                }

                val node = HBox(6.0)
                node.alignment = Pos.TOP_CENTER
                node.children.addAll(left, right)
                node
            } else {
                null
            }

    private fun addRow(left: Pane, right: Pane, label: Label, value: Label) {
        label.styleClass.addAll("value")
        label.alignment = Pos.CENTER_RIGHT
        label.maxWidth = Double.MAX_VALUE

        value.styleClass.addAll("value")
        value.alignment = Pos.CENTER_LEFT
        value.maxWidth = Double.MAX_VALUE

        left.children.addAll(label)
        right.children.addAll(value)
    }

    private fun addTemperatureLabel(temperature: SimpleObjectProperty<BigDecimal>, left: Pane, right: Pane, updating: BooleanProperty) {
        val label = Label(internationalizationService.findMessage("jSuplaGui.tile.temperature"))
        val value = Label()
        value.textProperty().bind(
                Bindings.createDoubleBinding(Callable { roundUp(temperature.value).toDouble() }, temperature)
                        .asString()
                        .concat(" °C")
        )
        addDirtyLabelListener(updating, value)
        addRow(left, right, label, value)
    }

    private fun addHumidityLabel(humidity: SimpleObjectProperty<Percentage>, left: Pane, right: Pane, updating: BooleanProperty) {
        val label = Label(internationalizationService.findMessage("jSuplaGui.tile.humidity"))
        val value = Label()
        value.textProperty().bind(
                Bindings.createDoubleBinding(Callable { roundUp(humidity.value.percentage).toDouble() }, humidity)
                        .asString()
                        .concat("%")
        )
        addDirtyLabelListener(updating, value)
        addRow(left, right, label, value)
    }

    private fun roundUp(value: BigDecimal) = value.setScale(2, ROUND_CEILING)
}
