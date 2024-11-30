import "mapbox-gl/dist/mapbox-gl.css";
import { useEffect, useRef, useState } from "react";
import Map, {
    Layer,
    MapLayerMouseEvent,
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

export default function Mapbox() {
    const [viewState, setViewState] = useState({
        latitude: BrownLocation.lat,
        longitude: BrownLocation.long,
        zoom: initialZoom,
    });

    function selectOverlay(ev: MapLayerMouseEvent) {
        throw new Error("Function not implemented.");
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
                    onClick={(ev: MapLayerMouseEvent) => selectOverlay(ev)}
                >
                    {/* add overly <Source> info here */}
                </Map>
            ) : (
                "No MAXBOX_KEY provided. Cannot load map."
            )}
        </div>
    );
}