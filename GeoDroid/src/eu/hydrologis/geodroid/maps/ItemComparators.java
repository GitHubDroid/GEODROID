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
package eu.hydrologis.geodroid.maps;

import java.util.Comparator;

import eu.hydrologis.geodroid.util.Bookmark;
import eu.hydrologis.geodroid.util.INote;

/**
 * {@link MapItem} comparators to sort them.
 * 
 * @author Andrea Antonello (www.hydrologis.com)
 */
public class ItemComparators {

    /**
     * Sorts {@link MapItem}s by name. 
     */
    public static class MapItemNameComparator implements Comparator<MapItem> {
        private boolean doInverse = false;
        public MapItemNameComparator() {
        }
        public MapItemNameComparator( boolean doInverse ) {
            this.doInverse = doInverse;
        }
        public int compare( MapItem m1, MapItem m2 ) {
            int compareTo = m1.getName().compareTo(m2.getName());
            if (compareTo == 0) {
                return 0;
            }
            if (doInverse) {
                return -1 * compareTo;
            } else {
                return compareTo;
            }
        }
    }

    /**
     * Sorts {@link MapItem}s by id, which is equivalent to time order. 
     */
    public static class MapItemIdComparator implements Comparator<MapItem> {
        private boolean doInverse = false;
        public MapItemIdComparator() {
        }
        public MapItemIdComparator( boolean doInverse ) {
            this.doInverse = doInverse;
        }
        public int compare( MapItem m1, MapItem m2 ) {
            long id1 = m1.getId();
            long id2 = m2.getId();
            if (id1 < id2) {
                return doInverse ? 1 : -1;
            } else if (id1 > id2) {
                return doInverse ? -1 : 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Sorts {@link Bookmark}s by text. 
     */
    public static class BookmarksComparator implements Comparator<Bookmark> {
        private boolean doInverse = false;
        public BookmarksComparator() {
        }
        public BookmarksComparator( boolean doInverse ) {
            this.doInverse = doInverse;
        }
        public int compare( Bookmark m1, Bookmark m2 ) {
            String id1 = m1.getName();
            String id2 = m2.getName();

            int compareTo = id1.compareTo(id2);

            if (compareTo < 0) {
                return doInverse ? 1 : -1;
            } else if (compareTo > 0) {
                return doInverse ? -1 : 1;
            } else {
                return 0;
            }
        }
    }

    public static class NotesComparator implements Comparator<INote> {
        private boolean doInverse = false;
        public NotesComparator() {
        }
        public NotesComparator( boolean doInverse ) {
            this.doInverse = doInverse;
        }
        public int compare( INote m1, INote m2 ) {
            String id1 = m1.getName();
            String id2 = m2.getName();
            
            int compareTo = id1.compareTo(id2);
            
            if (compareTo < 0) {
                return doInverse ? 1 : -1;
            } else if (compareTo > 0) {
                return doInverse ? -1 : 1;
            } else {
                return 0;
            }
        }
    }

}
