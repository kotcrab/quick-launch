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

import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Component;

/** @author Kotcrab */
public class RunConfigList extends JBList {

	public RunConfigList () {
		setCellRenderer(new RunConfigTitleListCellRenderer());
		setBorder(new EmptyBorder(3, 3, 3, 3));
	}

	public static class RunConfigTitleListCellRenderer implements ListCellRenderer {
		protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

		public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			RunConfigModel model = (RunConfigModel) value;
			label.setIcon(model.icon);
			return label;
		}
	}
}
