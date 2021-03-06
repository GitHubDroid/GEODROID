/*
 * Geopaparazzi - Digital field mapping on Android based devices
 * Copyright (C) 2010  HydroloGIS (www.hydrologis.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.geopaparazzi.library.forms;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import eu.geodroid.library.util.MultipleChoiceDialog;
import eu.geodroid.library.util.Utilities;
import eu.geopaparazzi.library.forms.constraints.Constraints;
import eu.geopaparazzi.library.forms.constraints.MandatoryConstraint;
import eu.geopaparazzi.library.forms.constraints.RangeConstraint;
import eu.geopaparazzi.library.forms.views.GBooleanView;
import eu.geopaparazzi.library.forms.views.GComboView;
import eu.geopaparazzi.library.forms.views.GDateView;
import eu.geopaparazzi.library.forms.views.GEditTextView;
import eu.geopaparazzi.library.forms.views.GMapView;
import eu.geopaparazzi.library.forms.views.GMultiComboView;
import eu.geopaparazzi.library.forms.views.GNfcUidView;
import eu.geopaparazzi.library.forms.views.GPictureView;
import eu.geopaparazzi.library.forms.views.GSketchView;
import eu.geopaparazzi.library.forms.views.GTextView;
import eu.geopaparazzi.library.forms.views.GTimeView;
import eu.geopaparazzi.library.forms.views.GView;

/**
 * Utilities methods for form stuff.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 * @since 2.6
 */
@SuppressWarnings("nls")
public class FormUtilities {

    public static final String COLON = ":";
    public static final String UNDERSCORE = "_";

    /**
     * Type for a {@link TextView}.
     */
    public static final String TYPE_LABEL = "label";

    /**
     * Type for a {@link TextView} with line below.
     */
    public static final String TYPE_LABELWITHLINE = "labelwithline";

    /**
     * Type for a {@link EditText} containing generic text.
     */
    public static final String TYPE_STRING = "string";

    /**
     * Type for a {@link EditText} area containing generic text.
     */
    public static final String TYPE_STRINGAREA = "stringarea";

    /**
     * Type for a {@link EditText} containing generic numbers.
     */
    public static final String TYPE_DOUBLE = "double";

    /**
     * Type for a {@link Button} containing date.
     */
    public static final String TYPE_DATE = "date";

    /**
     * Type for a {@link Button} containing time.
     */
    public static final String TYPE_TIME = "time";

    /**
     * Type for a {@link CheckBox}.
     */
    public static final String TYPE_BOOLEAN = "boolean";

    /**
     * Type for a {@link Spinner}.
     */
    public static final String TYPE_STRINGCOMBO = "stringcombo";

    /**
     * Type for a {@link MuSpinner}.
     */
    public static final String TYPE_STRINGMULTIPLECHOICE = "multistringcombo";

    /**
     * Type for a the NFC UID reader.
     */
    public static final String TYPE_NFCUID = "nfcuid";

    /**
     * Type for a hidden widget, which just needs to be kept as it is but not displayed.
     */
    public static final String TYPE_HIDDEN = "hidden";

    /**
     * Type for latitude, which can be substituted by the engine if necessary.
     */
    public static final String TYPE_LATITUDE = "LATITUDE";

    /**
     * Type for longitude, which can be substituted by the engine if necessary.
     */
    public static final String TYPE_LONGITUDE = "LONGITUDE";

    /**
     * Type for a hidden item, the value of which needs to get the name of the element.
     * 
     * <p>This is needed in case of abstraction of forms.</p>
     */
    public static final String TYPE_PRIMARYKEY = "primary_key";

    /**
     * Type for pictures element.
     */
    public static final String TYPE_PICTURES = "pictures";

    /**
     * Type for pictures element.
     */
    public static final String TYPE_SKETCH = "sketch";

    /**
     * Type for map element.
     */
    public static final String TYPE_MAP = "map";

    /**
     * Type for barcode element.
     * 
     * <b>Not in use yet.</b>
     */
    public static final String TYPE_BARCODE = "barcode";

    /**
     * A constraint that defines the item as mandatory.
     */
    public static final String CONSTRAINT_MANDATORY = "mandatory";

    /**
     * A constraint that defines a range for the value.
     */
    public static final String CONSTRAINT_RANGE = "range";

    public static final String ATTR_SECTIONNAME = "sectionname";
    public static final String ATTR_SECTIONOBJECTSTR = "sectionobjectstr";
    public static final String ATTR_FORMS = "forms";
    public static final String ATTR_FORMNAME = "formname";

    public static final String TAG_LONGNAME = "longname";
    public static final String TAG_SHORTNAME = "shortname";
    public static final String TAG_FORMS = "forms";
    public static final String TAG_FORMITEMS = "formitems";
    public static final String TAG_KEY = "key";
    public static final String TAG_VALUE = "value";
    public static final String TAG_ISLABEL = "islabel";
    public static final String TAG_VALUES = "values";
    public static final String TAG_ITEMS = "items";
    public static final String TAG_ITEM = "item";
    public static final String TAG_TYPE = "type";
    public static final String TAG_READONLY = "readonly";
    public static final String TAG_SIZE = "size";
    public static final String TAG_URL = "url";

    /**
     * Checks if the type is a special one.
     * 
     * @param typethe type string from the form.
     * @return <code>true</code> if the type is special.
     */
    public static boolean isTypeSpecial( String type ) {
        if (type.equals(TYPE_PRIMARYKEY)) {
            return true;
        } else if (type.equals(TYPE_HIDDEN)) {
            return true;
        }
        return false;
    }

    /**
     * Adds a {@link TextView} to the supplied mainView.
     * 
     * @param context the context.
     * @param mainView the main view to which to add the new widget to.
     * @param key the key identifying the widget.
     * @param value the value to put in the widget.
     * @param type the text type:
     *          <ul>
     *             <li>0: text</li>
     *             <li>1: numeric</li>
     *             <li>2: phone</li>
     *             <li>3: date</li>
     *          </ul>
     * @param lines lines or 0 
     * @param constraintDescription 
     * @param readonly 
     * @return the added view.
     */
    public static GView addEditText( Context context, LinearLayout mainView, String key, String value, int type, int lines,
            String constraintDescription, boolean readonly ) {
        GEditTextView editText = new GEditTextView(context, null, mainView, key, value, type, lines, constraintDescription,
                readonly);
        return editText;
    }

    public static GView addTextView( final Context context, LinearLayout mainView, String value, String size, boolean withLine,
            final String url ) {
        GTextView textView = new GTextView(context, null, mainView, value, size, withLine, url);
        return textView;
    }

    /**
     * Adds a {@link CheckBox} to the supplied mainView.
     * 
     * @param context the context.
     * @param mainView the main view to which to add the new widget to.
     * @param key the key identifying the widget.
     * @param value the value to put in the widget.
     * @param constraintDescription 
     * @param readonly 
     * @return the added view.
     */
    public static GView addBooleanView( Context context, LinearLayout mainView, String key, String value,
            String constraintDescription, boolean readonly ) {
        GBooleanView booleanView = new GBooleanView(context, null, mainView, key, value, constraintDescription, readonly);
        return booleanView;
    }

    /**
     * Adds a {@link Spinner} to the supplied mainView.
     * 
     * @param context the context.
     * @param mainView the main view to which to add the new widget to.
     * @param key the key identifying the widget.
     * @param value the value to put in the widget.
     * @param itemsArray the items to put in the spinner.
     * @param constraintDescription 
     * @return the added view.
     */
    public static GView addComboView( Context context, LinearLayout mainView, String key, String value, String[] itemsArray,
            String constraintDescription ) {
        GComboView comboView = new GComboView(context, null, mainView, key, value, itemsArray, constraintDescription);
        return comboView;
    }

    /**
     * Adds a {@link MultipleChoiceDialog} to the supplied mainView.
     * 
     * @param context the context.
     * @param mainView the main view to which to add the new widget to.
     * @param key the key identifying the widget.
     * @param value the value to put in the widget.
     * @param itemsArray the items to put in the spinner.
     * @param constraintDescription 
     * @return the added view.
     */
    public static GView addMultiSelectionView( final Context context, LinearLayout mainView, String key, String value,
            final String[] itemsArray, String constraintDescription ) {
        GMultiComboView comboView = new GMultiComboView(context, null, mainView, key, value, itemsArray, constraintDescription);
        return comboView;
    }

    public static GView addPictureView( final Context context, LinearLayout mainView, String key, String value,
            String constraintDescription ) {
        GPictureView pictureView = new GPictureView(context, null, mainView, key, value, constraintDescription);
        return pictureView;
    }

    public static GView addSketchView( final Context context, LinearLayout mainView, String key, String value,
            String constraintDescription ) {
        GSketchView sketch = new GSketchView(context, null, mainView, key, value, constraintDescription);
        return sketch;
    }

    /**
     * @param context
     * @param mainView
     * @param key
     * @param value needs to be a relative path to the media image (ex. media/IMG_20120202.png)
     * @param constraintDescription
     * @return
     */
    public static GView addMapView( final Context context, LinearLayout mainView, String key, String value,
            String constraintDescription ) {
        GMapView mapView = new GMapView(context, null, mainView, key, value, constraintDescription);
        return mapView;
    }

    /**
     * Adds a {@link DatePicker} to the supplied mainView.
     * 
     * @param fragment the parent {@link Fragment}.
     * @param mainView the main view to which to add the new widget to.
     * @param key the key identifying the widget.
     * @param value the value to put in the widget.
     * @param itemsArray the items to put in the spinner.
     * @param constraintDescription 
     * @param readonly 
     * @return the added view.
     */
    public static GView addDateView( final Fragment fragment, LinearLayout mainView, String key, String value,
            String constraintDescription, boolean readonly ) {
        GDateView dateView = new GDateView(fragment, null, mainView, key, value, constraintDescription, readonly);
        return dateView;
    }

    /**
     * Adds a {@link TimePickerDialog} to the supplied mainView.
     * 
     * @param fragment the parent {@link Fragment}.
     * @param mainView the main view to which to add the new widget to.
     * @param key the key identifying the widget.
     * @param value the value to put in the widget.
     * @param itemsArray the items to put in the spinner.
     * @param constraintDescription 
     * @param readonly 
     * @return the added view.
     */
    public static GView addTimeView( final Fragment fragment, LinearLayout mainView, String key, String value,
            String constraintDescription, boolean readonly ) {
        GTimeView timeView = new GTimeView(fragment, null, mainView, key, value, constraintDescription, readonly);
        return timeView;
    }

    public static GView addNfcUIDView( Activity activity, int requestCode, LinearLayout mainView, String key, String value,
            String constraintDescription ) {
        GNfcUidView nfcuidView = new GNfcUidView(activity, null, requestCode, mainView, key, value, constraintDescription);
        return nfcuidView;
    }

    /**
     * Check an {@link JSONObject object} for constraints and collect them.
     * 
     * @param jsonObject the object to check.
     * @param constraints the {@link Constraints} object to use or <code>null</code>.
     * @return the original {@link Constraints} object or a new created.
     * @throws Exception
     */
    public static Constraints handleConstraints( JSONObject jsonObject, Constraints constraints ) throws Exception {
        if (constraints == null)
            constraints = new Constraints();
        if (jsonObject.has(CONSTRAINT_MANDATORY)) {
            String mandatory = jsonObject.getString(CONSTRAINT_MANDATORY).trim();
            if (mandatory.trim().equals("yes")) {
                constraints.addConstraint(new MandatoryConstraint());
            }
        }
        if (jsonObject.has(CONSTRAINT_RANGE)) {
            String range = jsonObject.getString(CONSTRAINT_RANGE).trim();
            String[] rangeSplit = range.split(",");
            if (rangeSplit.length == 2) {
                boolean lowIncluded = rangeSplit[0].startsWith("[") ? true : false;
                String lowStr = rangeSplit[0].substring(1);
                Double low = Utilities.adapt(lowStr, Double.class);
                boolean highIncluded = rangeSplit[1].endsWith("]") ? true : false;
                String highStr = rangeSplit[1].substring(0, rangeSplit[1].length() - 1);
                Double high = Utilities.adapt(highStr, Double.class);
                constraints.addConstraint(new RangeConstraint(low, lowIncluded, high, highIncluded));
            }
        }
        return constraints;
    }

    /**
     * Updates a form items array with the given kay/value pair.
     * 
     * @param formItemsArray the array to update.
     * @param key the key of the item to update.
     * @param value the new value to use.
     * @throws JSONException
     */
    public static void update( JSONArray formItemsArray, String key, String value ) throws JSONException {
        int length = formItemsArray.length();
        for( int i = 0; i < length; i++ ) {
            JSONObject itemObject = formItemsArray.getJSONObject(i);
            if (itemObject.has(TAG_KEY)) {
                String objKey = itemObject.getString(TAG_KEY).trim();
                if (objKey.equals(key)) {
                    itemObject.put(TAG_VALUE, value);
                }
            }
        }
    }

    /**
     * Update those fields that do not generate widgets.
     * 
     * @param formItemsArray the items array.
     * @param latitude the lat value.
     * @param longitude the long value.
     * @throws JSONException 
     */
    public static void updateExtras( JSONArray formItemsArray, double latitude, double longitude ) throws JSONException {
        int length = formItemsArray.length();
        for( int i = 0; i < length; i++ ) {
            JSONObject itemObject = formItemsArray.getJSONObject(i);
            if (itemObject.has(TAG_KEY)) {
                String objKey = itemObject.getString(TAG_KEY).trim();
                if (objKey.equals(TYPE_LATITUDE)) {
                    itemObject.put(TAG_VALUE, latitude);
                } else if (objKey.equals(TYPE_LONGITUDE)) {
                    itemObject.put(TAG_VALUE, longitude);
                }
            }
        }

    }

}
