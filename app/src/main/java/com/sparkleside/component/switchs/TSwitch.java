package com.sparkleside.component.switchs;

/*
 *  This file is part of Robok © 2024.
 *
 *  Robok is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Robok is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with Robok.  If not, see <https://www.gnu.org/licenses/>.
 */ 

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.SwitchCompat;

import com.sparkleside.component.R;

/*
  Android 12 Switch
*/

public class TSwitch extends SwitchCompat {

    public TSwitch(Context context) {
        super(context);
        init(context, null);
    }

    public TSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setPadding(8, 8, 8, 8);
        setText("TSwitch");
        setTextSize(16);
        setThumbDrawable(context.getResources().getDrawable(R.drawable.ui_m3_switch_thumb));
        setTrackDrawable(context.getResources().getDrawable(R.drawable.ui_m3_switch_track));
        setTrackTintList(context.getResources().getColorStateList(R.color.sel_m3_switch_track));
        setThumbTintList(context.getResources().getColorStateList(R.color.sel_m3_switch_thumb));
    }
}