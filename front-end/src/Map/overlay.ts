import { FillLayer } from "react-map-gl";

const propertyName = "holc_grade";
export const all_dorms: FillLayer = {
    id: "all_dorms",
    type: "fill",
    paint: {
        "fill-color": "#893101",
        "fill-opacity": 0.4,
    },
};

export const selected_dorms: FillLayer = {
    id: "selected_dorms",
    type: "fill",
    paint: {
        "fill-color": "#FBE106",
        "fill-opacity": 0.4,
    },
};