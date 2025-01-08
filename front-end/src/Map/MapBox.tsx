import "mapbox-gl/dist/mapbox-gl.css";
import * as turf from '@turf/turf';
import { useEffect, useMemo, useCallback, useState } from "react";
import { all_dorms, selected_dorms } from "./overlay";
import geojson from "./Tiledata.json";
import Map, {
    Layer,
    MapLayerMouseEvent,
    MapMouseEvent,
    Popup,
    Source,
    ViewStateChangeEvent,
} from "react-map-gl";
import * as mapboxgl from "mapbox-gl";

/**
 * The {@code MapBox} is an ui element that allows users to view
 * a map of the dorms. When searching for dorms, the dorm halls that have 
 * dorms within your search will highlight. When you hover over a building,
 * the building name will appear. When you click on a building, the map will then 
 * be replaced with a image of the building and some of the buildings information,
 * address, etc. This will also open a panel on the right to show what dorms under the filter
 * that are in the selected building. Zooming out is limited. 
 * 
 * When using this in junction with other elements, 'filteredDorms' and 'clickedDorm' 
 * should be available from App.tsx. 'filteredDorms' refers to buildings that highlighted, 
 * and 'clickedDorm' refers to the builidng on the map that has most
 * recently been clicked by the user. 
 */
type HoverDormState = { feature: any; x: any; y: any } | null;
const MAPBOX_KEY = import.meta.env.VITE_MAPBOX_TOKEN;

/**
     * Intial map scale
     */
const BrownLocation: LatLong = {
    lat: 41.827,
    long: -71.4,
};
const initialZoom = 15.1;

export interface LatLong {
    lat: number;
    long: number;
}

if (!MAPBOX_KEY) {
    console.log("key: ", MAPBOX_KEY);
    console.error("Mapbox API key not found. Please add it to your .env file.");
}

/**
     * filteredDorms and clickedDorms both communicate
     * to the parent class, allowing for the Map to appear 
     * different nad be affected by children classes of its parent. 
     */
interface MapboxProps {
    filteredDorms: Array<string>;
    setFilteredDorms: React.Dispatch<React.SetStateAction<Array<string>>>;
    clickedDorm: string;
    setClickedDorm: React.Dispatch<React.SetStateAction<string>>;
}

export default function Mapbox(props: MapboxProps) {
    /**
     * viewState is significant for zoom and moving aroudn the map
     */
    const [viewState, setViewState] = useState({
        latitude: BrownLocation.lat,
        longitude: BrownLocation.long,
        zoom: initialZoom,
    });

    /**
     * makes the dorms have that orange color to distinguish them
     */
    const [dormOverlay, setDormOverlay] = useState<GeoJSON.FeatureCollection | undefined>(
        undefined
    );

    /**
     * works with filtered dorms to change the Overlay 
     */
    const [selectOverlay, setSelectOverlay] = useState<GeoJSON.FeatureCollection | undefined>(
        undefined
    );

    /**
     * Keep track of the Hover box information. 
     * x, y coords of where the box is
     * has feature information of dorm 
     */
    const [currentHoverDorm, setHoverDorm] = useState<HoverDormState>(null);

    /**
     * Makes sure that the map is laoded before overlay layers
     */
    const [mapLoaded, setMapLoaded] = useState(false);

    /**
     * makes sure there is a secodn for the map to laod
     * before the layers are laoded ontop 
     */
    useEffect(() => {
        setTimeout(() => {
            setMapLoaded(true);
        }, 400);
    }, []);

    /**
    * makes the dorm overlay the imported dorm's geojson
    */
    useEffect(() => {
        setDormOverlay(geojson);
    }, []);

    /**
    * only gets the features of the filteredDorms, for searching purposes 
    */
    useEffect(() => {
        const filteredFeatures = geojson.features.filter((feature: any) =>
            props.filteredDorms.includes(feature.properties.Name)
        );

        const filteredGeoJson = {
            type: geojson.type,
            features: filteredFeatures,
        };
        setSelectOverlay(filteredGeoJson);
    }, [props.filteredDorms]);
    /**
    * on Hover constantly updates the currentHoverDorm varible
    * in order to properly get ht eddorm the hoverbox should exist over
    * as well as its information
    */
    const onHover = useCallback((event: { features: any; point: { x: any; y: any; }; }) => {
        const {
            features,
            point: { x, y }
        } = event;
        const hoveredFeature = features && features[0];
        setHoverDorm(hoveredFeature && { feature: hoveredFeature, x, y });
    }, []);

    /**
    * makes sure clickedDorm becomes the dorm that the user clicked.
    * This will send a log and also should allow the parent 
    * class to deal with the click properly.
    */
    function clickDorm(e: mapboxgl.MapMouseEvent) {
        const { lng, lat } = e.lngLat;
        const point = [lng, lat];
        const features = geojson.features;
        let clickedDormName = '';

        for (const feature of features) {
            const polygon = turf.polygon(feature.geometry.coordinates);

            if (turf.booleanPointInPolygon(point, polygon)) {
                clickedDormName = feature.properties?.Name || '';
                break;
            }
        }

        console.log('Clicked Dorm Name:', clickedDormName);
        props.setClickedDorm(clickedDormName);
    }


    return (
        <div style={{ width: '100%', height: '100%' }}>
            {MAPBOX_KEY ? (
                <Map
                    mapboxAccessToken={MAPBOX_KEY}
                    {...viewState}
                    style={{ width: '100%', height: '100%' }}
                    className="mapbox"
                    mapStyle={"mapbox://styles/knewlin713/cm43jrtsp002g01rz80qfdre0"}

                    onMove={(ev: ViewStateChangeEvent) => {
                        if (ev.viewState.latitude > 41.837) {
                            setViewState({
                                ...ev.viewState,
                                latitude: 41.837
                            });
                        } else if (ev.viewState.latitude < 41.821) {
                            setViewState({
                                ...ev.viewState,
                                latitude: 41.817
                            });
                        } else {
                            setViewState(ev.viewState);
                        }
                        if (ev.viewState.longitude > -71.39) {
                            setViewState({
                                ...ev.viewState,
                                longitude: -71.39
                            });
                        } else if (ev.viewState.longitude < -71.41) {
                            setViewState({
                                ...ev.viewState,
                                longitude: -71.41
                            });
                        }
                        if (ev.viewState.zoom < 13) {
                            setViewState({
                                latitude: BrownLocation.lat,
                                longitude: BrownLocation.long,
                                zoom: 13
                            });
                        }
                    }}
                    onClick={(e: MapLayerMouseEvent) => clickDorm(e)}
                    interactiveLayerIds={['all_dorms']}
                    onMouseMove={onHover}
                >
                    {mapLoaded && (
                        <Source id="selected_dorms" type="geojson" data={selectOverlay}>
                            <Layer {...selected_dorms} />
                            <Source id="all_dorms" type="geojson" data={dormOverlay}>
                                <Layer {...all_dorms}
                                />
                            </Source>
                        </Source>)}
                    {currentHoverDorm && (
                        <div className="balh"
                            style={{
                                left: currentHoverDorm.x - 60,
                                top: currentHoverDorm.y + 20,
                                position: 'absolute',
                                backgroundColor: 'white',
                                padding: '10px',
                                border: '1px solid #ccc',
                                zIndex: 1000,
                                fontSize: '16px',
                                color: 'black'
                            }}>
                            <div>Dorm: {currentHoverDorm.feature.properties.Name}</div>
                        </div>)}

                </Map>
            ) : (
                "No MAXBOX_KEY provided. Cannot load map."
            )}
        </div>
    );
}