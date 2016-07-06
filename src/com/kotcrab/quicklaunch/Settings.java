/*
 * Copyright 2016 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kotcrab.quicklaunch;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.Arrays;

/** @author Kotcrab */
public class Settings {
	private static final String PREFIX = "com.kotcrab.quicklaunch";
	private static final String DEBUG_MODE = PREFIX + ".DebugMode";
	private static final String FAVORITE_CONFIGS = PREFIX + ".FavoriteConfigs";

	private final PropertiesComponent properties;

	private boolean debugMode;
	private ArrayList<String> favoritesConfigs;

	public Settings (Project project) {
		properties = PropertiesComponent.getInstance(project);
		load();
	}

	private void load () {
		debugMode = properties.getBoolean(DEBUG_MODE);
		String[] values = properties.getValues(FAVORITE_CONFIGS);
		if(values != null) {
			favoritesConfigs = new ArrayList<String>(Arrays.asList(values));
		} else {
			favoritesConfigs = new ArrayList<String>();
		}
	}

	private void save () {
		properties.setValue(DEBUG_MODE, debugMode);
		properties.setValues(FAVORITE_CONFIGS, favoritesConfigs.toArray(new String[favoritesConfigs.size()]));
	}

	public boolean isDebugMode () {
		return debugMode;
	}

	public void setDebugMode (boolean debugMode) {
		this.debugMode = debugMode;
		save();
	}

	public ArrayList<String> getFavoritesConfigs () {
		return favoritesConfigs;
	}

	public void setFavoritesConfigs (ArrayList<String> favoritesConfigs) {
		this.favoritesConfigs = favoritesConfigs;
		save();
	}
}
