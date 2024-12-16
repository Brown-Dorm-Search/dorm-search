import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import './styles/Home.css'
import Dropdowns from './Dropdowns'
import MapBox from './Map/MapBox'
import StandardDorm from './StandardDorm'
import Suite, { DormRoomProps } from './Suite'

function Home() {
  const [resultString, setResultString] = useState('');
  const [building, setBuilding] = useState('');
  //used prop.clickedDorm[] to get clicked dorm
  const [filteredDorms, setFilteredDorms] = useState<Array<string>>([]);
  const [clickedDorm, setClickedDorms] = useState<string>('');
  //set filtered dorms to change map highlights.

  const suiteRoom1: DormRoomProps = { roomNumber: "BARBOUR 080 081", roomType: "Double", roomSize: 228 }
  const suiteRoom2: DormRoomProps = { roomNumber: "BARBOUR 080 084", roomType: "Double", roomSize: 199 }
  const suiteRooms = [suiteRoom1, suiteRoom2]


  return (
    <>
      <div>
        <div className="header-bottom">
          <Dropdowns resultStr={resultString} setResultStr={setResultString}></Dropdowns>
        </div>
        <div className="panel-container">
          <div className="panel left">{resultString}</div>
          <div className="panel center"><MapBox filteredDorms={filteredDorms} setFilteredDorms={setFilteredDorms}
            clickedDorm={clickedDorm} setClickedDorm={setClickedDorms}></MapBox></div>
          <div className="panel right">
            <StandardDorm
              roomNumber={'BARBOUR 106'}
              floorPlanLink={'https://brown.edu/Facilities/Facilities_Management/maps/docx/floor_plan/m_100129_1_dwg_base_a_8x11l.pdf'}
              roomType={'Double'}
              bathroomType={'Private'}
              kitchenType={'No'}
              roomSize={379}
              building="BARBOUR">
            </StandardDorm>
            <Suite
              suiteNumber={'BARBOUR 080'}
              floorPlanLink={'https://brown.edu/Facilities/Facilities_Management/maps/docx/floor_plan/m_100129_0_dwg_base_a_8x11l.pdf'}
              capacity={4}
              bathroom={'Private'}
              kitchen={'Yes'}
              commonAreaSize={385}
              dormRooms={suiteRooms}>
            </Suite>
          </div>
        </div>
      </div>
    </>
  )
}

export default Home