import { SetStateAction, useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
import './styles/App.css'
import Mapbox from './Map/MapBox'



function App() {  //used prop.clickedDorm[] to get clicked dorm
  const [filteredDorms, setFilteredDorms] = useState<Array<string>>([]);
  const [clickedDorm, setClickedDorms] = useState<string>('');
  //set filtered dorms to change map highlights.
  return (
    <>
      <div>
        <Mapbox filteredDorms={filteredDorms} setFilteredDorms={setFilteredDorms}
          clickedDorm={clickedDorm} setClickedDorm={setClickedDorms}></Mapbox>
      </div>
    </>
  )
}

export default App
