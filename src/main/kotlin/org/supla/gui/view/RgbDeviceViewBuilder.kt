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

import com.jfoenix.controls.JFXSlider
import javafx.beans.property.BooleanProperty
import javafx.beans.property.DoubleProperty
import javafx.beans.property.Property
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.ColorPicker
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import org.supla.gui.i18n.InternationalizationService
import org.supla.gui.uidevice.UiChannel
import org.supla.gui.uidevice.UiColorAndBrightnessState
import org.supla.gui.uidevice.UiColorState
import org.supla.gui.uidevice.UiDimmerState
import javax.inject.Inject

class RgbDeviceViewBuilder @Inject constructor(private val internationalizationService: InternationalizationService) : DeviceViewBuilder {
    override fun build(channel: UiChannel, updating: BooleanProperty): Node? =
            if (channel.state is UiColorState || channel.state is UiColorAndBrightnessState || channel.state is UiDimmerState) {
                val node = VBox(3.0)
                when {
                    channel.state is UiColorState -> hsvSliders(channel.state.hue, channel.state.saturation, channel.state.value, channel.state.rgb, updating, node)
                    channel.state is UiDimmerState -> dimmerSlider(channel.state.brightness, updating, node)
                    channel.state is UiColorAndBrightnessState -> {
                        hsvSliders(channel.state.hue, channel.state.saturation, channel.state.value, channel.state.rgb, updating, node)
                        dimmerSlider(channel.state.brightness, updating, node)
                    }
                }
                node
            } else {
                null
            }

    private fun dimmerSlider(brightness: DoubleProperty, updating: BooleanProperty, node: VBox) {
        val dimmer = JFXSlider()
        dimmer.valueProperty().bindBidirectional(brightness)
        dimmer.disableProperty().bind(updating)
        node.children.addAll(
                Label(internationalizationService.findMessage("jSuplaGui.tile.brightness")),
                dimmer
        )
    }

    private fun hsvSliders(hue: DoubleProperty,
                           saturation: DoubleProperty,
                           value: DoubleProperty,
                           color: Property<Color>,
                           updating: BooleanProperty,
                           node: VBox) {
        val colorPicker = ColorPicker()
        colorPicker.disableProperty().bind(updating)
        VBox.setVgrow(colorPicker, Priority.ALWAYS)
        colorPicker.maxWidth = Double.MAX_VALUE
        colorPicker.valueProperty().bindBidirectional(color)
        val hueSlider = JFXSlider()
        hueSlider.disableProperty().bind(updating)
        hueSlider.max = 359.0
        hueSlider.valueProperty().bindBidirectional(hue)
        val saturationSlider = JFXSlider()
        saturationSlider.disableProperty().bind(updating)
        saturationSlider.valueProperty().bindBidirectional(saturation)
        val valueSlider = JFXSlider()
        valueSlider.disableProperty().bind(updating)
        valueSlider.valueProperty().bindBidirectional(value)

        node.alignment = Pos.TOP_CENTER
        node.children.addAll(
                colorPicker,
                Label(internationalizationService.findMessage("jSuplaGui.tile.hue")),
                hueSlider,
                Label(internationalizationService.findMessage("jSuplaGui.tile.saturation")),
                saturationSlider,
                Label(internationalizationService.findMessage("jSuplaGui.tile.value")),
                valueSlider
        )
    }
}
