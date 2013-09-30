package eu.hydrologis.geodroid.maps.tiles;
public enum MapGeneratorInternal {
	/**
	 * Map tiles are rendered offline.
	 */
	DATABASE_RENDERER,

	/**
	 * Map tiles are downloaded from the Mapnik server.
	 * 
	 * @see <a href="http://wiki.openstreetmap.org/wiki/Mapnik">Mapnik</a>
	 */
	MAPNIK,

	/**
	 * Map tiles are downloaded from the OpenCycleMap server.
	 * 
	 * @see <a href="http://opencyclemap.org/">OpenCycleMap</a>
	 */
	OPENCYCLEMAP;
}