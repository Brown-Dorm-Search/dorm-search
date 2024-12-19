import { FillLayer } from "react-map-gl";

/*
all_dorms fill layer provides color information for
the overlay used by all dorms on campus in the mapbox.
*/
const propertyName = "holc_grade";
export const all_dorms: FillLayer = {
    id: "all_dorms",
    type: "fill",
    paint: {
        "fill-color": "#964B00",
        "fill-opacity": 0.3,
    },
};

/*
selected_dorms fill layer provides color information for
the overlay used by only the dorm buildings that have dorms that 
match the search requeston campus in the mapbox.
*/
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