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
type HoverDormState = { feature: any; x: any; y: any } | null;
const MAPBOX_KEY = import.meta.env.VITE_MAPBOX_TOKEN;

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

interface MapboxProps {
    filteredDorms: Array<string>;
    setFilteredDorms: React.Dispatch<React.SetStateAction<Array<string>>>;
    clickedDorm: string;
    setClickedDorm: React.Dispatch<React.SetStateAction<string>>;
}

export default function Mapbox(props: MapboxProps) {
    const [viewState, setViewState] = useState({
        latitude: BrownLocation.lat,
        longitude: BrownLocation.long,
        zoom: initialZoom,
    });
    const [dormOverlay, setDormOverlay] = useState<GeoJSON.FeatureCollection | undefined>(
        undefined
    );
    const [selectOverlay, setSelectOverlay] = useState<GeoJSON.FeatureCollection | undefined>(
        undefined
    );
    const [currentHoverDorm, setHoverDorm] = useState<HoverDormState>(null);
    const [mapLoaded, setMapLoaded] = useState(false);


    useEffect(() => {
        setTimeout(() => {
            setMapLoaded(true);
        }, 1000);
    }, []);
    useEffect(() => { setDormOverlay(geojson); }, []);
    useEffect(() => {
        const filteredFeatures = geojson.features.filter((feature: any) =>
            props.filteredDorms.includes(feature.properties.name)
        );

        const filteredGeoJson = {
            type: geojson.type,
            features: filteredFeatures,
        };

        setSelectOverlay(filteredGeoJson);
    }, []);

    const onHover = useCallback((event: { features: any; point: { x: any; y: any; }; }) => {
        const {
            features,
            point: { x, y }
        } = event;
        const hoveredFeature = features && features[0];
        setHoverDorm(hoveredFeature && { feature: hoveredFeature, x, y });
    }, []);

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
        <div>
            {MAPBOX_KEY ? (
                <Map
                    mapboxAccessToken={MAPBOX_KEY}
                    {...viewState}

                    style={{ width: window.innerWidth / 3, height: window.innerHeight / 2 }}
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
                                left: currentHoverDorm.x,
                                top: currentHoverDorm.y,
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