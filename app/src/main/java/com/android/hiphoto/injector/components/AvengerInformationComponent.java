/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.android.hiphoto.injector.components;

import dagger.Component;
import com.android.hiphoto.domain.GetCharacterInformationUsecase;
import com.android.hiphoto.injector.Activity;
import com.android.hiphoto.injector.modules.ActivityModule;
import com.android.hiphoto.injector.modules.AvengerInformationModule;
import com.android.hiphoto.views.activities.AvengerDetailActivity;

@Activity
@Component(dependencies = AppComponent.class, modules = {AvengerInformationModule.class, ActivityModule.class})
public interface AvengerInformationComponent extends ActivityComponent {

    void inject (AvengerDetailActivity detailActivity);

    GetCharacterInformationUsecase getCharacerInformationUsecase();
    //GetCharacterComicsUsecase getCharacterComicsUsecase();
}
