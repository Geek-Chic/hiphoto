/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.android.hiphoto.injector.components;

import dagger.Component;
import javax.inject.Singleton;
import com.android.hiphoto.AvengersApplication;
import com.android.hiphoto.injector.AppModule;
import com.android.hiphoto.model.repository.Repository;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {
    AvengersApplication app();
    Repository dataRepository();
}
