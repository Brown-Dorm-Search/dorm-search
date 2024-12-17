import React, { Dispatch, SetStateAction, useState } from 'react';
import "./styles/App.css"
import MultiRangeSlider from "multi-range-slider-react";

export interface DropdownsProps {
  resultStr: string;
  setResultStr: Dispatch<SetStateAction<string>>;
  FilteredDorms: string;
  setFilteredDorms: Dispatch<SetStateAction<string>>;
}



export default function Dropdowns(props: DropdownsProps) {
  const [selectedOptions, setSelectedOptions] = useState({
    Campus_Location: [],
    Floor: [],
    PartOfSuite: [],
    RoomCapacity: [],
    HasBathroom: [],
    HasKitchen: [],
    minRoomSize: [],
    maxRoomSize: [],
  });



  // Handle change for multi-select dropdowns
  const handleChange = (event: React.ChangeEvent<HTMLSelectElement>, dropdown: string) => {
    const selectedValues = Array.from(event.target.selectedOptions, option => option.value);
    setSelectedOptions(prevState => ({
      ...prevState,
      [dropdown]: selectedValues,
    }));
  };
  const handleSearchClick = () => {
    console.log('Search clicked');
    const resultString = Object.entries(selectedOptions)
      .map(([dropdown, value]) => `${dropdown}: ${`${value}` === '' ? 'All' : value}`)
      .join(', ');

    const dormsMap = Object.entries(selectedOptions.Campus_Location)
      .map(([value]) => `${`${value}` === '' ? 'All' : value}`)
      .join(', ');
    console.log(resultString);
    props.setFilteredDorms(dormsMap);
    props.setResultStr(resultString)
  };

  async function fetchSearch() {
    const fetch1 = await fetch(
      `http://localhost:3232/filter?campusLocation=${encodeURIComponent(
        selectedOptions.Campus_Location
      )}&isSuite=${encodeURIComponent(
        selectedOptions.PartOfSuite
      )}&hasKitchen=${encodeURIComponent(
        selectedOptions.HasKitchen
      )}&bathroomType=${encodeURIComponent(
        selectedOptions.HasBathroom
      )}&minRoomSize=${encodeURIComponent(
        selectedOptions.minRoomSize
      )}&maxRoomSize=${encodeURIComponent(
        selectedOptions.maxRoomsize
      )}&roomCapacity=${encodeURIComponent(
        selectedOptions.RoomCapacity
      )}&floorNumber=${encodeURIComponent(
        selectedOptions.Floor
      )}`
    );
    const filterjson = await fetch1.json();
    if (filterjson.result === "success") {
      console.log("search successful");
      props.setResultStr(filterjson);
    } else {
      console.error("Invalid data from API:", filterjson);
    }
  }

  return (
    <div className="dropdown-container">
      <div className="dropdown">
        <label htmlFor="Campus_Location">Campus Location</label>
        <select
          id="Campus Location"
          value={selectedOptions.Campus_Location}
          onChange={(e) => handleChange(e, 'Campus_Location')}
          multiple
        >
          <option value="All">All</option>
          <option value="East Campus">East Campus</option>
          <option value="Grad Center">Grad Center</option>
          <option value="Gregorian Quad">Gregorian Quad</option>
          <option value="Machado House">Machado House</option>
          <option value="Main Green">Main Green</option>
          <option value="Minden Hall">Minden Hall</option>
          <option value="North Campus">North Campus</option>
          <option value="Ruth J. Simmons Quad">Ruth J. Simmons Quad</option>
          <option value="Wriston Quad">Wriston Quad</option>
        </select>
      </div>

      <div className="dropdown">
        <label htmlFor="Floor">Floor</label>
        <select
          id="Floor"
          value={selectedOptions.Floor}
          onChange={(e) => handleChange(e, 'Floor')}
          multiple
        >
          <option value="All">All</option>
          <option value="Floor 1">Floor 1</option>
          <option value="Floor 2">Floor 2</option>
          <option value="Floor 3">Floor 3</option>
          <option value="Floor 4">Floor 4</option>
          <option value="Floor 5">Floor 5</option>
          <option value="Floor 6">Floor 6</option>
          <option value="Floor 7">Floor 7</option>
          <option value="Floor 8">Floor 8</option>

        </select>
      </div>

      <div className="dropdown">
        <label htmlFor="PartOfSuite">Part of Suite</label>
        <select
          id="PartOfSuite"
          value={selectedOptions.PartOfSuite}
          onChange={(e) => handleChange(e, 'PartOfSuite')}
        >
          <option value="All">All</option>
          <option value="Yes">Yes</option>
          <option value="No">No</option>
        </select>
      </div>

      <div className="dropdown">
        <label htmlFor="RoomCapacity">People in Room</label>
        <select
          id="RoomCapacity"
          value={selectedOptions.RoomCapacity}
          onChange={(e) => handleChange(e, 'RoomCapacity')}
          multiple
        >
          <option value="All">All</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
        </select>
      </div>

      <div className="dropdown">
        <label htmlFor="HasBathroom">Has Bathroom</label>
        <select
          id="HasBathroom"
          value={selectedOptions.HasBathroom}
          onChange={(e) => handleChange(e, 'HasBathroom')}
          multiple
        >
          <option value="All">All</option>
          <option value="Private">Private</option>
          <option value="Semi-Private">Semi-Private</option>
          <option value="Communal">Communal</option>
        </select>
      </div>

      <div className="dropdown">
        <label htmlFor="HasKitchen">Has Kitchen</label>
        <select
          id="HasKitchen"
          value={selectedOptions.HasKitchen}
          onChange={(e) => handleChange(e, 'HasKitchen')}
        >
          <option value="All">All</option>
          <option value="Yes">Yes</option>
          <option value="No">No</option>
        </select>
      </div>

      <div className='multi-range-slider-container'>
        <label htmlFor="RoomSize">Room Size</label>
        <MultiRangeSlider
          min={0}
          max={500}
          step={20}
          minValue={selectedOptions.minRoomSize}
          maxValue={selectedOptions.maxRoomSize}
          onInput={(e) => {
            setSelectedOptions(prevState => ({
              ...prevState,
              minRoomSize: e.minValue,
              maxRoomSize: e.maxValue
            }));
          }}
        ></MultiRangeSlider>
        <p>
          Selected range: {selectedOptions.minRoomSize} - {selectedOptions.maxRoomSize}
        </p>
      </div>

      <div className="search-button-container">
        <button className="search-button" onClick={handleSearchClick}>Search</button>
      </div>
    </div>
  );
}


