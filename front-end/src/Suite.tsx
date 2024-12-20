import React from 'react';
import './styles/Suite.css'; // Import corresponding CSS for styling

// Interface for Dorm Room details
export interface DormRoomProps {
  roomNumber: string;
  roomType: string;
  roomSize: number;
}

// Interface for Suite details
export interface SuiteProps {
  suiteNumber: string;
  floorPlanLink: string;
  capacity: string;
  bathroom: string;
  kitchen: boolean;
  commonAreaSize: number;
  dormRooms: DormRoomProps[]; // List of dorm rooms in the suite
}

// Dorm Room component
export function DormRoom({ roomNumber, roomType, roomSize }: DormRoomProps) {
  function type() {
    if (roomType == "One") {
      return ("Single")
    } else if (roomType == "Two") {
      return ("Double")
    } else if (roomType == "Three") {
      return ("Triple")
    } else if (roomType == "Four") {
      return ("Quad")
    } else {
      return (roomType)
    }
  }
  return (
    <div className="dorm-room">
      <div className="dorm-room-header">
        <span className="dorm-room-number">Room: {roomNumber}</span>
      </div>

      <div className="dorm-room-details">
        <div className="detail">
          <label>Type</label>
          <span>{type()}</span>
        </div>
        <div className="detail">
          <label>Square Feet</label>
          <span>{roomSize}</span>
        </div>
      </div>
    </div>
  );
};

// Suite component
export default function Suite({
  suiteNumber,
  floorPlanLink,
  capacity,
  bathroom,
  kitchen,
  commonAreaSize,
  dormRooms,
}: SuiteProps) {

  function booleanKitchen() {
    if (kitchen) {
      return ("Yes")
    } else {
      return ("No")
    }
  }

  return (
    <div className="suite-info">
      {/* Suite Header */}
      <div className="suite-header">
        <span className="suite-number">Suite: {suiteNumber}</span>
        <a href={floorPlanLink} target="_blank" rel="noopener noreferrer" className="floor-plan-link">
          Floor Plan
        </a>
      </div>

      {/* Suite Details */}
      <div className="suite-details">
        <div className="detail">
          <label>Capacity</label>
          <span>{capacity}</span>
        </div>
        <div className="detail">
          <label>Bathroom</label>
          <span>{bathroom}</span>
        </div>
        <div className="detail">
          <label>Has Kitchen</label>
          <span>{booleanKitchen()}</span>
        </div>
        <div className="detail">
          <label>Common Area</label>
          <span>{commonAreaSize} sqft</span>
        </div>
      </div>

      {/* List of Dorm Rooms */}
      <div className="dorm-rooms">
        {dormRooms.map((dormRoom, index) => (
          <DormRoom
            key={index}
            roomNumber={dormRoom.roomNumber}
            roomType={dormRoom.roomType}
            roomSize={dormRoom.roomSize}
          />
        ))}
      </div>
    </div>
  );
};
