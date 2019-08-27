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

import griffon.core.injection.Module
import org.codehaus.griffon.runtime.core.injection.AbstractModule
import org.kordamp.jipsy.ServiceProviderFor
import javax.inject.Named


@Named
@ServiceProviderFor(value = [Module::class])
internal class ViewBuilderModule : AbstractModule() {
    override fun doConfigure() {
        bind(ViewBuilder::class.java).to(ViewBuilderImpl::class.java).asSingleton()

        bind(GateDeviceViewBuilder::class.java).to(GateDeviceViewBuilder::class.java)
        bind(LightDeviceViewBuilder::class.java).to(LightDeviceViewBuilder::class.java)
        bind(RgbDeviceViewBuilder::class.java).to(RgbDeviceViewBuilder::class.java)
        bind(RollerShutterDeviceViewBuilder::class.java).to(RollerShutterDeviceViewBuilder::class.java)
        bind(TemperatureAndHumidityDeviceViewBuilder::class.java).to(TemperatureAndHumidityDeviceViewBuilder::class.java)
    }
}
