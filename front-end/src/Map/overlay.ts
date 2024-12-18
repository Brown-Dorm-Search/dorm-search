import { FillLayer } from "react-map-gl";

const propertyName = "holc_grade";
export const all_dorms: FillLayer = {
    id: "all_dorms",
    type: "fill",
    paint: {
        "fill-color": "#964B00",
        "fill-opacity": 0.3,
    },
};

export const selected_dorms: FillLayer = {
    id: "selected_dorms",
    type: "fill",
    paint: {
        "fill-color": "#FDFD96",
        "fill-opacity": 1,
        "fill-outline-color": "#000000",
        "fill-outline-width": 100
        ,

    },
};