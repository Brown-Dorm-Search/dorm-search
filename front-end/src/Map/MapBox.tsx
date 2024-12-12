import "mapbox-gl/dist/mapbox-gl.css";
import * as turf from '@turf/turf';
import { useEffect, useRef, useState } from "react";
import { all_dorms, selected_dorms } from "./overlay";
import geojson from "./Tiledata.json";
import Map, {
    Layer,
    MapLayerMouseEvent,
    MapMouseEvent,
    Marker,
    Popup,
    Source,
    ViewStateChangeEvent,
} from "react-map-gl";

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
                    onClick={(ev: mapboxgl.MapMouseEvent) => clickDorm(ev)}
                >
                    <Source id="selected_dorms" type="geojson" data={selectOverlay}>
                        <Layer {...selected_dorms} />
                        <Source id="all_dorms" type="geojson" data={dormOverlay}>
                            <Layer {...all_dorms}
                            />
                        </Source>
                    </Source>
                </Map>
            ) : (
                "No MAXBOX_KEY provided. Cannot load map."
            )}
        </div>
    );
}