import { SetStateAction, useEffect, useState } from 'react'
import './styles/Home.css'
interface dorm {
    clickedDorm: string;
    setClickedDorm: React.Dispatch<React.SetStateAction<string>>;
}
export default function InfoPage(props: dorm) {
    //TODO: put in backend
    const handleClick = () => {
        props.setClickedDorm('');
    };
    return (
        <div>
            <div className="dorm-info-container">
                {props.clickedDorm ? <p>{props.clickedDorm}</p> : <p>No dorm selected</p>}
                {props.clickedDorm === "Buxton House" &&
                    <div><img src="../public/assets/Buxton_House.jpg" alt="Buxton House" />
                        <p> Address is 27 Brown St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                        </p></div>}
                {props.clickedDorm === "Andrews Hall" &&
                    <div><img src="../public/assets/Andrews_Hall.jpeg" alt="Andrews Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Archibald-Bronson" &&
                    <div><img src="../public/assets/Archibald-Bronson.jpeg" alt="Archibald-Bronson" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Barbour Hall" &&
                    <div><img src="../public/assets/Barbour_Hall.jpeg" alt="Barbour Hall" />
                        <p> Address is 100 Charlesfield St, Providence, RI 02906
                            <br />
                            Located in East Campus
                            <br />
                            Built in 1904
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 56
                        </p></div>}
                {props.clickedDorm === "Caswell Hall" &&
                    <div><img src="../public/assets/Caswell_Hall.jpeg" alt="Caswell Hall" />
                        <p> Address is 168 Thayer St, Providence, RI 02906
                            <br />
                            Located in Thayer Street
                            <br />
                            Built in 1903
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 47
                        </p></div>}
                {props.clickedDorm === "Champlin Hall" &&
                    <div><img src="../public/assets/Champlin_Hall.jpeg" alt="Champlin Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Chapin House" &&
                    <div><img src="../public/assets/Chapin_House.jpeg" alt="Chapin House" />
                        <p> Address is 116 Thayer St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 38.3
                        </p></div>}
                {props.clickedDorm === "Chen Family Hall" &&
                    <div><img src="../public/assets/Chen_Family_Hall.jpeg" alt="Chen Family Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Danoff Hall" &&
                    <div><img src="../public/assets/Danoff_Hall.jpeg" alt="Danoff Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Diman House" &&
                    <div><img src="../public/assets/Diman_House.jpeg" alt="Diman House" />
                        <p> Address is 41 Charlesfield St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 29.5
                        </p></div>}
                {props.clickedDorm === "Donovan House" &&
                    <div><img src="../public/assets/Donovan_House.jpeg" alt="Donovan House" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Emery Hall" &&
                    <div><img src="../public/assets/Emery_Hall.jpeg" alt="Emery Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Emery Hall" &&
                    <div><img src="../public/assets/Emery_Hall.jpeg" alt="Emery Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Everett-Poland" &&
                    <div><img src="../public/assets/Everett-Poland.jpeg" alt="Everett-Poland" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Goddard House" &&
                    <div><img src="../public/assets/Goddard_House.jpeg" alt="Goddard House" />
                        <p> Address is 39 Charlesfield St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 39.3
                        </p></div>}
                {props.clickedDorm === "Grad Center A" &&
                    <div><img src="../public/assets/Grad_Center_A.jpeg" alt="Grad Center A" />
                        <p> Address is 40 Charlesfield St, Providence, RI 02906
                            <br />
                            Located in Grad Center
                            <br />
                            Built in 1968
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 36.7
                        </p></div>}
                {props.clickedDorm === "Grad Center B" &&
                    <div><img src="../public/assets/Grad_Center_B.jpeg" alt="Grad Center B" />
                        <p> Address is 44 Charlesfield St, Providence, RI 02906
                            <br />
                            Located in Grad Center
                            <br />
                            Built in 1968
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 38.3
                        </p></div>}
                {props.clickedDorm === "Grad Center C" &&
                    <div><img src="../public/assets/Grad_Center_C.jpeg" alt="Grad Center C" />
                        <p> Address is 82 Thayer St, Providence, RI 02906
                            <br />
                            Located in Grad Center
                            <br />
                            Built in 1968
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 36.7
                        </p></div>}
                {props.clickedDorm === "Grad Center D" &&
                    <div><img src="../public/assets/Grad_Center_D.jpeg" alt="Grad Center D" />
                        <p> Address is 90 Thayer St, Providence, RI 02906
                            <br />
                            Located in Grad Center
                            <br />
                            Built in 1968
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 38
                        </p></div>}
                {props.clickedDorm === "Harambe House" &&
                    <div><img src="../public/assets/Harambe_House.jpeg" alt="Harambe House" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Harkness House" &&
                    <div><img src="../public/assets/Harkness_House.jpeg" alt="Harkness House" />
                        <p> Address is 47 Charlesfield St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 29
                        </p></div>}
                {props.clickedDorm === "Hegeman Hall" &&
                    <div><img src="../public/assets/Hegeman_Hall.jpeg" alt="Hegeman Hall" />
                        <p> Address is 128 George St, Providence, RI 02906
                            <br />
                            Located in Ruth J. Simmons
                            <br />
                            Built in 1991
                            <br />
                            Does have Elevator Access
                            <br />
                            People per Washer: 56.5
                        </p></div>}
                {props.clickedDorm === "Hope College" &&
                    <div><img src="../public/assets/Hope_College.jpeg" alt="Hope College" />
                        <p> Address is 71 Waterman St, Providence, RI 02906
                            <br />
                            Located in Main Green
                            <br />
                            Built in 1822
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 39
                        </p></div>}
                {props.clickedDorm === "Jameson-Mead" &&
                    <div><img src="../public/assets/Jameson-Mead.jpeg" alt="Jameson-Mead" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "King House" &&
                    <div><img src="../public/assets/King_House.jpeg" alt="King House" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Littlefield Hall" &&
                    <div><img src="../public/assets/Littlefield_Hall.jpeg" alt="Littlefield Hall" />
                        <p> Address is 102 George St, Providence, RI 02906
                            <br />
                            Located in Ruth J. Simmons
                            <br />
                            Built in 1926
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 34
                        </p></div>}
                {props.clickedDorm === "Machado House" &&
                    <div><img src="../public/assets/Machado_House.jpeg" alt="Machado House" />
                        <p> Address is 87 Prospect St, Providence, RI 02906
                            <br />
                            Built in 1912
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 425
                        </p></div>}
                {props.clickedDorm === "Marcy House" &&
                    <div><img src="../public/assets/Marcy_House.jpeg" alt="Marcy House" />
                        <p> Address is 115 George St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 58
                        </p></div>}
                {props.clickedDorm === "Metcaf Hall" &&
                    <div><img src="../public/assets/Metcaf_Hall.jpeg" alt="Metcaf Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Miller Hall" &&
                    <div><img src="../public/assets/Miller_Hall.jpeg" alt="Miller Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Minden Hall" &&
                    <div><img src="../public/assets/Minden_Hall.jpeg" alt="Minden Hall" />
                        <p> Address is 121 Waterman St, Providence, RI 02906
                            <br />
                            Located in East Campus
                            <br />
                            Built in 1912
                            <br />
                            Does have Elevator Access
                            <br />
                            People per Washer: 25.3
                        </p></div>}
                {props.clickedDorm === "Morriss Hall" &&
                    <div><img src="../public/assets/Morriss_Hall.jpeg" alt="Morriss Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "New Pembroke 1" &&
                    <div><img src="../public/assets/New_Pembroke_1.jpeg" alt="New Pembroke 1" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "New Pembroke 2" &&
                    <div><img src="../public/assets/New_Pembroke_2.jpeg" alt="New Pembroke 2" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "New Pembroke 3" &&
                    <div><img src="../public/assets/New_Pembroke_3.jpeg" alt="New Pembroke 3" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "New Pembroke 4" &&
                    <div><img src="../public/assets/New_Pembroke_4.jpeg" alt="New Pembroke 4" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "North House" &&
                    <div><img src="../public/assets/North_House.jpeg" alt="North House" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Olney House" &&
                    <div><img src="../public/assets/Olney_House.jpeg" alt="Olney House" />
                        <p> Address is 29 Brown St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 39.3
                        </p></div>}
                {props.clickedDorm === "Perkins Hall" &&
                    <div><img src="../public/assets/Perkins_Hall.jpeg" alt="Perkins Hall" />
                        <p> Address is 154 Power St, Providence, RI 02906
                            <br />
                            Located in East Campus
                            <br />
                            Built in 1969
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 38.6
                        </p></div>}
                {props.clickedDorm === "Sears House" &&
                    <div><img src="../public/assets/Sears_House.jpeg" alt="Sears House" />
                        <p> Address is 113 George St, Providence, RI 02906
                            <br />
                            Located in Wriston Quad
                            <br />
                            Built in 1951
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 23
                        </p></div>}
                {props.clickedDorm === "Slater Hall" &&
                    <div><img src="../public/assets/Slater_Hall.jpeg" alt="Slater Hall" />
                        <p> Address is 70 George St, Providence, RI 02906
                            <br />
                            Located in Main Green
                            <br />
                            Built in 1879
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 28
                        </p></div>}
                {props.clickedDorm === "Vartan Gregorian Quad A" &&
                    <div><img src="../public/assets/Vartan_Gregorian_Quad_A.jpeg" alt="Vartan Gregorian Quad A" />
                        <p> Address is 103 Thayer St, Providence, RI 02906
                            <br />
                            Located in Gregorian Qaud
                            <br />
                            Built in 1991
                            <br />
                            Does have Elevator Access
                            <br />
                            People per Washer: 28.5
                        </p></div>}
                {props.clickedDorm === "Vartan Gregorian Quad B" &&
                    <div><img src="../public/assets/Vartan_Gregorian_Quad_B.jpeg" alt="Vartan Gregorian Quad B" />
                        <p> Address is 101 Thayer St, Providence, RI 02906
                            <br />
                            Located in Gregorian Qaud
                            <br />
                            Built in 1991
                            <br />
                            Does have Elevator Access
                            <br />
                            People per Washer: 28.5
                        </p></div>}
                {props.clickedDorm === "Wayland House" &&
                    <div><img src="../public/assets/Wayland_House.jpg" alt="Wayland House" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "West House" &&
                    <div><img src="../public/assets/West_House.jpeg" alt="West House" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Woolley Hall" &&
                    <div><img src="../public/assets/Woolley_Hall.jpeg" alt="Woolley Hall" />
                        <p> No Information currently, to be updatesd
                        </p></div>}
                {props.clickedDorm === "Young Orchard 2" &&
                    <div><img src="../public/assets/Young_Orchard_2.jpeg" alt="Young Orchard 2" />
                        <p> Address is Young Orchard Ave 2, Providence, RI 02906
                            <br />
                            Located in East Campus
                            <br />
                            Built in 1973
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 28
                        </p></div>}
                {props.clickedDorm === "Young Orchard 4" &&
                    <div><img src="../public/assets/Young_Orchard_4.jpeg" alt="Young Orchard 4" />
                        <p> Address is Young Orchard Ave 4, Providence, RI 02906
                            <br />
                            Located in East Campus
                            <br />
                            Built in 1973
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 28
                        </p></div>}
                {props.clickedDorm === "Young Orchard 10" &&
                    <div><img src="../public/assets/Young_Orchard_10.jpeg" alt="Young Orchard 10" />
                        <p> Address is Young Orchard Ave 2, Providence, RI 02906
                            <br />
                            Located in East Campus
                            <br />
                            Built in 1973
                            <br />
                            Does not have Elevator Access
                            <br />
                            People per Washer: 18.7
                        </p></div>}
                <button className="grey-button" onClick={handleClick}>
                    Back to Map
                </button>
            </div>
        </div>
    );
}