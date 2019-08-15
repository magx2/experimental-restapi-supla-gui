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
package pl.grzeslowski.jsupla.gui.api

import org.slf4j.LoggerFactory
import pl.grzeslowski.jsupla.api.Api
import pl.grzeslowski.jsupla.gui.preferences.TokenService
import javax.inject.Inject
import javax.inject.Provider

internal class ApiProvider @Inject constructor(private val tokenService: TokenService) : Provider<Api> {
    private val logger = LoggerFactory.getLogger(ApiProvider::class.java)

    override fun get(): Api {
        val token = tokenService.read()
        logger.trace("New api client for token SHA `{}`", token.hashCode())
        return Api.getInstance(token)
    }
}